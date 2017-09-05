package in.novopay.velocity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.base.CaseFormat;

import in.novopay.velocity.input.InputEntity;
import in.novopay.velocity.input.InputEntityFields;

public class Main {

	static String ROOT_DIR = "";

	static String TEMPLATES_DIR = "";
	static String OUTPUT_DIR = "";
	static String INPUT_DIR = "";

	public static void main(String[] args) throws IOException {
		constructDirctoryPath(args);
		VelocityContext context = new VelocityContext();
		InputEntity entity = getEntity();
		context.put("entity", entity);
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties velocityProperties = new Properties();
		velocityProperties.setProperty("file.resource.loader.path", TEMPLATES_DIR);
		velocityEngine.init(velocityProperties);

		String[] inputTemplateFileArray = { "entity.vm", "repo.vm", "dao.vm", "schema.vm", "createprocessor.vm",
				"updateprocessor.vm", "listprocessor.vm", "detailsprocessor.vm" };

		String[] outputDir = { "in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/entity",
				"in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/repository",
				"in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/daoservice", "sql",
				"in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processors",
				"in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processors",
				"in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processors",
				"in/novopay/" + entity.getService() + "/" + entity.getUserStory() + "/processors" };

		String[] outputFileExtentionSuffixArray = { "Entity.java", "Repository.java", "DAOService.java", ".sql",
				"Processor.java", "Processor.java", "ListProcessor.java", "DetailsProcessor.java" };
		String[] outputFileExtentionPrefixArray = { "", "", "", "", "Create", "Update", "Get", "Get" };

		createOutputDirectories(outputDir);

		if (inputTemplateFileArray.length == outputDir.length
				&& inputTemplateFileArray.length == outputFileExtentionSuffixArray.length) {

			for (int i = 0; i < inputTemplateFileArray.length; i++) {
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
			System.out.println("Missing root directory path");
			System.exit(0);
		} else {
			ROOT_DIR = args[0];
			TEMPLATES_DIR = ROOT_DIR + "/templates";
			OUTPUT_DIR = ROOT_DIR + "/output";
			INPUT_DIR = ROOT_DIR + "/input";
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

			String author = (String) jsonObject.get("author");
			entity.setAuthor(author);

			String userStory = (String) jsonObject.get("userStory");
			entity.setUserStory(userStory);

			String service = (String) jsonObject.get("service");
			entity.setService(service);

			JSONArray fields = (JSONArray) jsonObject.get("fields");
			Iterator<JSONObject> iterator = fields.iterator();
			while (iterator.hasNext()) {
				JSONObject fobj = iterator.next();
				InputEntityFields f = new InputEntityFields();
				f.setName((String) fobj.get("name"));
				f.setLowerCamelCaseName(
						CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (String) fobj.get("name")));
				f.setUpperCamelCaseName(
						CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, (String) fobj.get("name")));
				f.setType((String) fobj.get("type"));
				Long length = (Long) fobj.get("length");
				if (length != null) {
					f.setLength(length.intValue());
				}
				Boolean notNull = (Boolean) fobj.get("notNull");
				if (notNull != null) {
					f.setNotNull(notNull);
				} else {
					f.setNotNull(false);
				}
				f.setLowerSnakeCaseName(
						CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, (String) fobj.get("name")));
				entity.addFields(f);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return entity;
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
