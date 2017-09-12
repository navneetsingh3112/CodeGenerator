package in.novopay.codegenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.base.CaseFormat;

import in.novopay.codegenerator.input.InputEntity;
import in.novopay.codegenerator.input.InputEntityFields;

public class CodeGenerator {

	static String TEMPLATES_DIR = "";
	static String OUTPUT_DIR = "";
	static String INPUT_DIR = "";

	public static void main(String[] args) throws IOException {
		constructDirctoryPath(args);
		verifyProgramArgs();
		VelocityContext context = new VelocityContext();
		InputEntity entity = getEntity();
		context.put("entity", entity);
		context.put("StringUtils", new StringUtils());
		context.put("NumberUtils", new NumberUtils());
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties velocityProperties = new Properties();
		velocityProperties.setProperty("file.resource.loader.path", TEMPLATES_DIR);
		velocityEngine.init(velocityProperties);

		String[] inputTemplateFileArray = { "entity.vm", "repo.vm", "dao.vm", "createprocessor.vm",
				"updateprocessor.vm", "listprocessor.vm", "detailsprocessor.vm", "rowMapper.vm",
				"CreateOrUpdateRequestjson.vm", "GetPaginatedListRequestjson.vm", "GetDetailsRequestjson.vm",
				"CreateOrUpdateResponse.vm", "GetDetailsResponsejson.vm", "GetPaginatedListResponsejson.vm",
				"orchestration.vm", "schema.vm", "physicaldeleteprocessor.vm", "logicaldeleteprocessor.vm",
				"DeleteRequestjson.vm", "DeleteResponse.vm" };

		String[] outputDir = { "javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/entity",
				"javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/daoservice",
				"javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/daoservice",
				"javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processor",
				"javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processor",
				"javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processor",
				"javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processor",
				"javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/daoservice", "request",
				"request", "request", "response", "response", "response", "orchestration", "sql",
				"javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processor",
				"javasrc/in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processor", "request",
				"response" };

		String[] outputFileExtentionPrefixArray = { "", "", "", "Create", "Update", "Get", "Get", "", "createOrUpdate",
				"get", "get", "createOrUpdate", "get", "get", "", "V000XXX__", "PhysicalDelete", "LogicalDelete",
				"delete", "delete" };

		String[] outputFileExtentionSuffixArray = { "Entity.java", "Repository.java", "DAOService.java",
				"Processor.java", "Processor.java", "ListProcessor.java", "DetailsProcessor.java", "RowMapper.java",
				"_requestTemplate.json", "List_requestTemplate.json", "Details_requestTemplate.json",
				"_responseTemplate.json", "Details_responseTemplate.json", "List_responseTemplate.json", ".xml", ".sql",
				"Processor.java", "Processor.java", "_requestTemplate.json", "_responseTemplate.json" };

		createOutputDirectories(outputDir);

		if (inputTemplateFileArray.length == outputDir.length
				&& inputTemplateFileArray.length == outputFileExtentionSuffixArray.length) {

			for (int i = 0; i < inputTemplateFileArray.length; i++) {
				if ("PHYSICAL".equalsIgnoreCase(entity.getDeleteMode())
						&& "LogicalDelete".equalsIgnoreCase(outputFileExtentionPrefixArray[i])) {
					continue;
				} else if ("LOGICAL".equalsIgnoreCase(entity.getDeleteMode())
						&& "PhysicalDelete".equalsIgnoreCase(outputFileExtentionPrefixArray[i])) {
					continue;
				}
				Template t = velocityEngine.getTemplate(inputTemplateFileArray[i]);
				StringWriter writer = new StringWriter();
				t.merge(context, writer);
				System.out.println(writer);
				String filePath = "";
				if (outputFileExtentionPrefixArray[i].isEmpty()) {
					filePath = OUTPUT_DIR + "/" + outputDir[i] + "/" + entity.getUpperCamelCaseClassName()
							+ outputFileExtentionSuffixArray[i];
				} else {
					filePath = OUTPUT_DIR + "/" + outputDir[i] + "/" + outputFileExtentionPrefixArray[i]
							+ entity.getUpperCamelCaseClassName() + outputFileExtentionSuffixArray[i];
				}
				PrintWriter pw = new PrintWriter(filePath);
				pw.print(writer.toString());
				pw.close();
			}
		} else {
			System.out.println("Array size is not matching.");
		}

	}

	private static void constructDirctoryPath(String args[]) {
		if (args == null || args.length < 1) {
			System.out.println("Missing config directory file path");
			System.exit(0);
		} else {
			JSONParser parser = new JSONParser();
			try {
				JSONObject obj = (JSONObject) parser.parse(new FileReader(args[0]));
				JSONArray filePaths = (JSONArray) obj.get("file_paths");
				for (Object object : filePaths) {
					JSONObject jsonObj = (JSONObject) object;
					String dirName = (String) jsonObj.get("dir_name");
					if ("INPUT_DIR".equalsIgnoreCase(dirName)) {
						INPUT_DIR = (String) jsonObj.get("path");
					} else if ("OUTPUT_DIR".equalsIgnoreCase(dirName)) {
						OUTPUT_DIR = (String) jsonObj.get("path");
					} else if ("TEMPLATES_DIR".equalsIgnoreCase(dirName)) {
						TEMPLATES_DIR = (String) jsonObj.get("path");
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			} catch (ParseException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	private static void verifyProgramArgs() {
		if (INPUT_DIR.isEmpty()) {
			System.out.println("INPUT_DIR path is missing");
			System.exit(0);
		}
		if (OUTPUT_DIR.isEmpty()) {
			System.out.println("OUTPUT_DIR path is missing");
			System.exit(0);
		}
		if (TEMPLATES_DIR.isEmpty()) {
			System.out.println("TEMPLATES_DIR path is missing");
			System.exit(0);
		}
	}

	public static InputEntity getEntity() {
		JSONParser parser = new JSONParser();
		InputEntity entity = new InputEntity();
		try {
			Object obj = parser.parse(new FileReader(INPUT_DIR + "/input.json"));
			JSONObject jsonObject = (JSONObject) obj;
			String table = (String) jsonObject.get("table");
			entity.setTable(table);
			entity.setLowerCamelCaseClassName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table));
			entity.setUpperCamelCaseClassName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table));
			entity.setLowerSnakeCaseClassName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, table));

			String author = (String) jsonObject.get("author");
			entity.setAuthor(author);

			String userStory = (String) jsonObject.get("userStory");
			entity.setUserStory(userStory);

			String service = (String) jsonObject.get("service");
			entity.setService(service);

			String deleteMode = (String) jsonObject.get("delete_mode");
			entity.setDeleteMode(deleteMode);

			entity.setTableComment((String) jsonObject.get("table_comment"));

			JSONArray dataFields = (JSONArray) jsonObject.get("data_fields");
			Iterator<JSONObject> iterator = dataFields.iterator();
			while (iterator.hasNext()) {
				JSONObject fobj = iterator.next();
				InputEntityFields f = parseIndividualEntity(fobj);
				entity.addDataFields(f);
			}

			JSONArray auditFields = (JSONArray) jsonObject.get("audit_fields");
			if(auditFields != null && ! auditFields.isEmpty()){
				iterator = auditFields.iterator();
				while (iterator.hasNext()) {
					JSONObject fobj = iterator.next();
					InputEntityFields f = parseIndividualEntity(fobj);
					entity.addAuditFields(f);
				}
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return entity;
	}

	private static InputEntityFields parseIndividualEntity(JSONObject fobj) {
		InputEntityFields f = new InputEntityFields();
		f.setName((String) fobj.get("name"));
		f.setLowerCamelCaseName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (String) fobj.get("name")));
		f.setUpperCamelCaseName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, (String) fobj.get("name")));
		f.setJavaType((String) fobj.get("java_type"));
		f.setSqlType((String) fobj.get("sql_type"));
		Long maxLength = (Long) fobj.get("max_length");
		if (maxLength != null) {
			f.setMaxLength(maxLength.intValue());
		}
		Boolean isMandatory = (Boolean) fobj.get("is_mandatory");
		if (isMandatory != null) {
			f.setIsMandatory(isMandatory);
		} else {
			f.setIsMandatory(false);
		}
		f.setLowerSnakeCaseName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, (String) fobj.get("name")));
		f.setComment((String) fobj.get("comment"));
		Boolean isSearchable = (Boolean) fobj.get("is_searchable");
		if (isSearchable != null) {
			f.setIsSearchable(isSearchable);
		} else {
			f.setIsSearchable(false);
		}
		Long minLength = (Long) fobj.get("min_length");
		if (minLength != null) {
			f.setMinLength(minLength.intValue());
		}
		f.setPattern((String) fobj.get("pattern"));

		Boolean isListElement = (Boolean) fobj.get("is_list_element");
		if (isListElement != null) {
			f.setIsListElement(isListElement);
		} else {
			f.setIsListElement(false);
		}
		return f;
	}

	private static void deleteRecursive(File fileOrDirectory) {
		if (fileOrDirectory.exists() && fileOrDirectory.isDirectory()) {
			for (File child : fileOrDirectory.listFiles()) {
				deleteRecursive(child);
			}
		}
		if (fileOrDirectory.exists()) {
			fileOrDirectory.delete();
		}
	}

	private static void createOutputDirectories(String[] outputDirectories) {
		File rootFile = new File(OUTPUT_DIR);
		deleteRecursive(rootFile);
		rootFile.mkdirs();

		for (int i = 0; i < outputDirectories.length; i++) {
			File file = new File(OUTPUT_DIR + "/" + outputDirectories[i]);
			file.mkdirs();
		}
	}

}
