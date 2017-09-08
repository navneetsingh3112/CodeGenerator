package in.novopay.velocity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;

import in.novopay.velocity.input.InputEntityButtons;
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

		// -----------------------------------
		// --------------TODO-----------------
		// -----------------------------------
		String[] inputTemplateFileArray = { 
				"CreateComponent.vm",
				"CreateTypeScript.vm",
				"ListComponent.vm",
				"ListTypeScript.vm",
				"ViewComponent.vm",
				"ViewTypeScript.vm",
				"EditComponent.vm",
				"EditTypeScript.vm",
				"Module.vm",
				"Translate.vm",
				"ResourceFactoryConstants.vm"};

		String[] outputDir = { 
				"app/" + entity.getLowerTrainCaseClassName() + "/create",
				"app/" + entity.getLowerTrainCaseClassName() + "/create",
				"app/" + entity.getLowerTrainCaseClassName() + "/list",
				"app/" + entity.getLowerTrainCaseClassName() + "/list",
				"app/" + entity.getLowerTrainCaseClassName() + "/view",
				"app/" + entity.getLowerTrainCaseClassName() + "/view",
				"app/" + entity.getLowerTrainCaseClassName() + "/edit",
				"app/" + entity.getLowerTrainCaseClassName() + "/edit",
				"app/" + entity.getLowerTrainCaseClassName(),
				"app/" + entity.getLowerTrainCaseClassName(),
				"app/" + entity.getLowerTrainCaseClassName()};

		String[] outputFileExtentionArray = { 
				"-create.component.html",
				"-create.component.ts",
				"-list.component.html",
				"-list.component.ts",
				"-view.component.html",
				"-view.component.ts",
				"-edit.component.html",
				"-edit.component.ts",
				".module.ts",
				".translate.ts",
				"-resource-factory.constants.ts"};
		// -----------------------------------
		// -----------------------------------
		
		createOutputDirectories(outputDir);

		if (inputTemplateFileArray.length == outputDir.length
				&& inputTemplateFileArray.length == outputFileExtentionArray.length) {

			for (int i = 0; i < inputTemplateFileArray.length; i++) {
				Template t = velocityEngine.getTemplate(inputTemplateFileArray[i]);
				StringWriter writer = new StringWriter();
				t.merge(context, writer);
				System.out.println(writer);
				PrintWriter pw = new PrintWriter(
						OUTPUT_DIR + "/" + outputDir[i] + "/" + entity.getLowerTrainCaseClassName() + outputFileExtentionArray[i]);
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
			entity.setLowerTrainCaseClassName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, table));

			String author = (String) jsonObject.get("author");
			entity.setAuthor(author);
			
			String userStory = (String) jsonObject.get("userStory");
			entity.setUserStory(userStory);

			String service = (String) jsonObject.get("service");
			entity.setService(service);
			
			entity.setTableComment((String) jsonObject.get("table_comment"));
			entity.setFetchApiKey((String) jsonObject.get("fetch_api_key"));

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
				f.setLowerTrainCaseName(
						CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, (String) fobj.get("name")));
				f.setJavaType((String) fobj.get("java_type"));
				f.setWebType((String) fobj.get("web_type"));
				f.setSqlType((String) fobj.get("sql_type"));
				Long length = (Long) fobj.get("length");
				if (length != null) {
					f.setLength(length.intValue());
				}
				Boolean isMandatory = (Boolean) fobj.get("is_mandatory");
				if (isMandatory != null) {
					f.setIsMandatory(isMandatory);
				} else {
					f.setIsMandatory(false);
				}
				f.setComment((String) fobj.get("comment"));

				f.setPlaceholder((String) fobj.get("placeholder"));
				f.setDisplayName((String) fobj.get("display_name"));
				f.setMinLength((Long) fobj.get("min_length"));
				f.setMaxLength((Long) fobj.get("max_length"));
				f.setValidationPattern((String) fobj.get("validation_pattern"));
				f.setApiKey((String) fobj.get("api_key"));
				f.setSearchable((Boolean) fobj.get("is_searchable"));
				f.setSortable((Boolean) fobj.get("is_sortable"));
				f.setEditable((Boolean) fobj.get("is_editable"));
				entity.addFields(f);
			}

			JSONArray buttons = (JSONArray) jsonObject.get("buttons");
			Iterator<JSONObject> iterator1 = buttons.iterator();
			while (iterator1.hasNext()) {
				JSONObject fobj = iterator1.next();

				InputEntityButtons b = new InputEntityButtons();
				b.setName((String) fobj.get("name"));
				b.setLowerCamelCaseName(
						CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (String) fobj.get("name")));
				b.setUpperCamelCaseName(
						CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, (String) fobj.get("name")));
				b.setLowerTrainCaseName(
						CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, (String) fobj.get("name")));
				entity.addButton(b);
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
