package in.novopay.codegenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

import in.novopay.codegenerator.input.Entity;
import in.novopay.codegenerator.input.Field;
import in.novopay.codegenerator.input.RelationShip;
import in.novopay.codegenerator.input.UserStoryDefinition;

public class CodeGenerator {

	static String TEMPLATES_DIR = "";
	static String OUTPUT_DIR = "";
	static String INPUT_DIR = "";

	static String[] inputTemplateFileArray = { "entity.vm", "repo.vm", "dao.vm", "createprocessor.vm",
			"updateprocessor.vm", "listprocessor.vm", "detailsprocessor.vm", "ListExtractor.vm", "DetailsExtractor.vm",
			"CreateOrUpdateRequestjson.vm", "GetPaginatedListRequestjson.vm", "GetDetailsRequestjson.vm",
			"CreateOrUpdateResponse.vm", "GetDetailsResponsejson.vm", "GetPaginatedListResponsejson.vm",
			"orchestration.vm", "schema.vm", "physicaldeleteprocessor.vm", "logicaldeleteprocessor.vm",
			"DeleteRequestjson.vm", "DeleteResponse.vm" };

	static String[] outputDir = { "javasrc/in/novopay/" + "%s" + "/" + "%s" + "/entity",
			"javasrc/in/novopay/" + "%s" + "/" + "%s" + "/daoservice",
			"javasrc/in/novopay/" + "%s" + "/" + "%s" + "/daoservice",
			"javasrc/in/novopay/" + "%s" + "/" + "%s" + "/processor",
			"javasrc/in/novopay/" + "%s" + "/" + "%s" + "/processor",
			"javasrc/in/novopay/" + "%s" + "/" + "%s" + "/processor",
			"javasrc/in/novopay/" + "%s" + "/" + "%s" + "/processor",
			"javasrc/in/novopay/" + "%s" + "/" + "%s" + "/daoservice",
			"javasrc/in/novopay/" + "%s" + "/" + "%s" + "/daoservice", "request", "request", "request", "response",
			"response", "response", "orchestration", "sql", "javasrc/in/novopay/" + "%s" + "/" + "%s" + "/processor",
			"javasrc/in/novopay/" + "%s" + "/" + "%s" + "/processor", "request", "response" };

	static String[] outputFileExtentionPrefixArray = { "", "", "", "Create", "Update", "Get", "Get", "","",
			"createOrUpdate", "get", "get", "createOrUpdate", "get", "get", "", "V000XXX__", "PhysicalDelete",
			"LogicalDelete", "delete", "delete" };

	static String[] outputFileExtentionSuffixArray = { "Entity.java", "Repository.java", "DAOService.java",
			"Processor.java", "Processor.java", "ListProcessor.java", "DetailsProcessor.java", "ListExtractor.java","DetailsExtractor.java",
			"_requestTemplate.json", "List_requestTemplate.json", "Details_requestTemplate.json",
			"_responseTemplate.json", "Details_responseTemplate.json", "List_responseTemplate.json", ".xml", ".sql",
			"Processor.java", "Processor.java", "_requestTemplate.json", "_responseTemplate.json" };

	public static void main(String[] args) throws IOException {
		constructDirctoryPath(args);
		verifyProgramArgs();
		generateFiles();
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

	private static void generateFiles() throws FileNotFoundException {
		UserStoryDefinition usd = getUserStroyDefinition();
		VelocityContext context = new VelocityContext();
		context.put("usd", usd);
		context.put("StringUtils", new StringUtils());
		context.put("NumberUtils", new NumberUtils());
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties velocityProperties = new Properties();
		velocityProperties.setProperty("file.resource.loader.path", TEMPLATES_DIR);
		velocityEngine.init(velocityProperties);
		deleteRecursive(new File(OUTPUT_DIR));
		for (int i = 0; i < inputTemplateFileArray.length; i++) {
			if (checkInputTemplateFileArray(inputTemplateFileArray[i])) {
				List<Entity> entityList = usd.getEntityList();
				for (Entity entity : entityList) {
					if ("PHYSICAL".equalsIgnoreCase(usd.getDeleteMode())
							&& "LogicalDelete".equalsIgnoreCase(outputFileExtentionPrefixArray[i])) {
						continue;
					} else if ("LOGICAL".equalsIgnoreCase(usd.getDeleteMode())
							&& "PhysicalDelete".equalsIgnoreCase(outputFileExtentionPrefixArray[i])) {
						continue;
					}
					writeFilesForEachEntity(velocityEngine, context, inputTemplateFileArray[i], outputDir[i],
							outputFileExtentionPrefixArray[i], outputFileExtentionSuffixArray[i], entity, usd);
				}
			} else {
				writeFilesForUserStory(velocityEngine, context, inputTemplateFileArray[i], outputDir[i],
						outputFileExtentionPrefixArray[i], outputFileExtentionSuffixArray[i], usd);
			}
		}
	}

	private static void writeFilesForEachEntity(VelocityEngine velocityEngine, VelocityContext context,
			String templateFileName, String outputDir, String outputFilePrefix, String outputFileSuffix, Entity entity,
			UserStoryDefinition usd) throws FileNotFoundException {
		context.put("entity", entity);
		Template t = velocityEngine.getTemplate(templateFileName);
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		String dir = String.format(outputDir, usd.getService(), usd.getLowerCamelCaseName().toLowerCase());
		createOutputDirectory(dir);
		String filePath = OUTPUT_DIR + "/" + dir + "/" + outputFilePrefix + entity.getUpperCamelCaseEntityName()
				+ outputFileSuffix;
		PrintWriter pw = new PrintWriter(filePath);
		pw.print(writer.toString());
		pw.close();
		context.remove("entity");
	}

	private static void writeFilesForUserStory(VelocityEngine velocityEngine, VelocityContext context,
			String templateFileName, String outputDir, String outputFilePrefix, String outputFileSuffix,
			UserStoryDefinition usd) throws FileNotFoundException {
		Template t = velocityEngine.getTemplate(templateFileName);
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		String dir = String.format(outputDir, usd.getService(), usd.getLowerCamelCaseName().toLowerCase());
		createOutputDirectory(dir);
		String filePath = OUTPUT_DIR + "/" + dir + "/" + outputFilePrefix + usd.getUpperCamelCaseName()
				+ outputFileSuffix;
		PrintWriter pw = new PrintWriter(filePath);
		pw.print(writer.toString());
		pw.close();
		context.remove("entity");
	}

	private static UserStoryDefinition getUserStroyDefinition() {
		UserStoryDefinition usd = new UserStoryDefinition();
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(INPUT_DIR + "/input.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject userStory = (JSONObject) jsonObject.get("userStory");
			String author = (String) userStory.get("author");
			usd.setAuthor(author);

			String name = (String) userStory.get("name");
			usd.setName(name);
			usd.setLowerName(name.toLowerCase());
			usd.setLowerCamelCaseName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name));
			usd.setUpperCamelCaseName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name));
			usd.setFlatCaseName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name).toLowerCase());
			
			String service = (String) userStory.get("service");
			usd.setService(service);

			JSONArray entities = (JSONArray) jsonObject.get("entities");
			for (Object entityObj : entities) {
				Entity e = getEntity(entityObj);
				usd.addEntity(e);
			}
			JSONArray relationship = (JSONArray) jsonObject.get("relationships");
			if (relationship != null && !relationship.isEmpty()) {
				for (Object rsp : relationship) {
					RelationShip e = getRelationShip(rsp);
					usd.addRelationShip(e);
				}
				processRelationShip(usd);
			}
			String deleteMode = (String) userStory.get("delete_mode");
			usd.setDeleteMode(deleteMode);

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return usd;
	}

	private static Entity getEntity(Object obj) {
		Entity entity = new Entity();
		JSONObject jsonObject = (JSONObject) obj;
		String entityStr = (String) jsonObject.get("entity");
		entity.setEntity(entityStr);
		entity.setLowerCamelCaseEntityName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entityStr));
		entity.setUpperCamelCaseEntityName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, entityStr));
		entity.setLowerSnakeCaseEntityName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, entityStr));

		entity.setTableComment((String) jsonObject.get("table_comment"));

		JSONArray dataFields = (JSONArray) jsonObject.get("fields");
		Iterator<JSONObject> iterator = dataFields.iterator();
		while (iterator.hasNext()) {
			JSONObject fobj = iterator.next();
			Field f = parseIndividualEntity(fobj);
			entity.addDataFields(f);
		}
		Boolean isAuditFieldRequired = (Boolean) jsonObject.get("audit_fields_required");
		if (isAuditFieldRequired != null && isAuditFieldRequired == true) {
			try {
				JSONParser parser = new JSONParser();
				Object auditObj = parser.parse(new FileReader(INPUT_DIR + "/audit.json"));
				JSONArray auditJsonObj = (JSONArray) auditObj;
				iterator = auditJsonObj.iterator();
				while (iterator.hasNext()) {
					JSONObject fobj = iterator.next();
					Field f = parseIndividualEntity(fobj);
					entity.addAuditFields(f);
				}

			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}

	private static Field parseIndividualEntity(JSONObject fobj) {
		Field f = new Field();
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
			f.setMandatory(isMandatory);
		} else {
			f.setMandatory(false);
		}
		f.setLowerSnakeCaseName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, (String) fobj.get("name")));
		f.setComment((String) fobj.get("comment"));
		Boolean isSearchable = (Boolean) fobj.get("is_searchable");
		if (isSearchable != null) {
			f.setSearchable(isSearchable);
		} else {
			f.setSearchable(false);
		}
		Long minLength = (Long) fobj.get("min_length");
		if (minLength != null) {
			f.setMinLength(minLength.intValue());
		}
		f.setPattern((String) fobj.get("pattern"));

		Boolean isListElement = (Boolean) fobj.get("is_list_element");
		if (isListElement != null) {
			f.setListElement(isListElement);
		} else {
			f.setListElement(false);
		}
		Boolean isPrimaryKey = (Boolean) fobj.get("is_primaryKey");
		if (isPrimaryKey != null) {
			f.setPrimaryKey(isPrimaryKey);
		} else {
			f.setPrimaryKey(false);
		}
		Boolean isSortable = (Boolean) fobj.get("is_sortable");
		if (isSortable != null) {
			f.setSortable(isSortable);
		} else {
			f.setSortable(false);
		}
		f.setMapTo((String) fobj.get("map_to"));
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

	private static RelationShip getRelationShip(Object obj) {
		JSONObject jsob = (JSONObject) obj;
		RelationShip rsp = new RelationShip();
		rsp.setPrimaryEntity((String) jsob.get("primary_entity"));
		rsp.setSecondaryEntity((String) jsob.get("secondary_entity"));
		rsp.setType((String) jsob.get("type"));
		return rsp;
	}

	private static void createOutputDirectory(String outputDirectory) {
		File rootFile = new File(OUTPUT_DIR);
		rootFile.mkdirs();
		File file = new File(OUTPUT_DIR + "/" + outputDirectory);
		file.mkdirs();
	}

	private static boolean checkInputTemplateFileArray(String inputTemplateFileName) {
		String[] str = { "entity.vm", "schema.vm", "updateprocessor.vm", "createprocessor.vm", "repo.vm" };
		for (int i = 0; i < str.length; i++) {
			if (inputTemplateFileName.equalsIgnoreCase(str[i])) {
				return true;
			}
		}
		return false;
	}

	private static void processRelationShip(UserStoryDefinition usd) {
		List<RelationShip> rspList = usd.getRelationshipList();
		for (RelationShip relationShip : rspList) {
			String type = relationShip.getType();
			if ("one-to-one".equalsIgnoreCase(type)) {
				establishOneToOneRelationShip(usd, relationShip);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void establishOneToOneRelationShip(UserStoryDefinition usd, RelationShip relationShip) {
		Entity secondaryEntity = usd.getEntity(relationShip.getSecondaryEntity());
		Entity primaryEntity = usd.getEntity(relationShip.getPrimaryEntity());
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", primaryEntity.getLowerSnakeCaseEntityName() + "_id");
		jsonObj.put("max_length", 10L);
		jsonObj.put("java_type", "Integer");
		jsonObj.put("sql_type", "INT");
		jsonObj.put("comment", "foreign key relationship");
		jsonObj.put("is_mandatory", true);
		Field f = parseIndividualEntity(jsonObj);
		secondaryEntity.addDataFields(f);
	}
}
