package in.novopay.velocity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
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

	static String TEMPLATES_DIR = "";
	static String OUTPUT_DIR = "";
	static String INPUT_DIR = "";

	static String ANDROID_ROOT_PATH = "AndroidApp";
	static String SCREEN_JAVA_FILES_BASE_PATH = ANDROID_ROOT_PATH + "/storm/NovopayPlatformServices/app/src/main/java/in/novopay/services";
	static String SCREEN_XML_FILES_BASE_PATH = ANDROID_ROOT_PATH + "/storm/NovopayPlatformServices/app/src/main/res/layout";
	static String API_JAVA_FILES_BASE_PATH = ANDROID_ROOT_PATH + "/storm/NovopayPlatformServices/network/src/main/java/in/novopay/network";

	public static void main(String[] args) throws IOException {
		constructDirctoryPath(args);
		verifyProgramArgs();
		VelocityContext context = new VelocityContext();
		InputEntity entity = getEntity();
		context.put("entity", entity);
		context.put("StringUtils", new StringUtils());
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties velocityProperties = new Properties();
		velocityProperties.setProperty("file.resource.loader.path", TEMPLATES_DIR);
		velocityEngine.init(velocityProperties);

		String[] inputTemplateFileArray = {
				"ListEntityActivityJava.vm",
				"ListEntityPresenterJava.vm",
				"ListEntityViewJava.vm",
				"ListEntityActivityXML.vm",
				"ListEntityAdapterJava.vm",
				"ItemViewListEntityXML.vm",
				"CreateEntityActivityJava.vm",
				"CreateEntityPresenterJava.vm",
				"CreateEntityViewJava.vm",
				"CreateEntityActivityXML.vm",
				"ViewEntityActivityJava.vm",
				"ViewEntityPresenterJava.vm",
				"ViewEntityViewJava.vm",
				"ViewEntityActivityXML.vm",
				"GetEntityListRequestJava.vm",
				"GetEntityListResponseJava.vm",
				"GetEntityDetailsRequestJava.vm",
				"GetEntityDetailsResponseJava.vm",
				"CreateOrUpdateEntityRequestJava.vm",
				"CreateOrUpdateEntityResponseJava.vm",
				"DeleteEntityRequestJava.vm",
				"DeleteEntityResponseJava.vm",
				"EntityCommon.vm"
		};

		String[] outputDir = {
				SCREEN_JAVA_FILES_BASE_PATH + "/activities",
				SCREEN_JAVA_FILES_BASE_PATH + "/presenters",
				SCREEN_JAVA_FILES_BASE_PATH + "/views",
				SCREEN_XML_FILES_BASE_PATH,
				SCREEN_JAVA_FILES_BASE_PATH + "/adapters",
				SCREEN_XML_FILES_BASE_PATH,
				SCREEN_JAVA_FILES_BASE_PATH + "/activities",
				SCREEN_JAVA_FILES_BASE_PATH + "/presenters",
				SCREEN_JAVA_FILES_BASE_PATH + "/views",
				SCREEN_XML_FILES_BASE_PATH,
				SCREEN_JAVA_FILES_BASE_PATH + "/activities",
				SCREEN_JAVA_FILES_BASE_PATH + "/presenters",
				SCREEN_JAVA_FILES_BASE_PATH + "/views",
				SCREEN_XML_FILES_BASE_PATH,
				API_JAVA_FILES_BASE_PATH + "/requests",
				API_JAVA_FILES_BASE_PATH + "/responses",
				API_JAVA_FILES_BASE_PATH + "/requests",
				API_JAVA_FILES_BASE_PATH + "/responses",
				API_JAVA_FILES_BASE_PATH + "/requests",
				API_JAVA_FILES_BASE_PATH + "/responses",
				API_JAVA_FILES_BASE_PATH + "/requests",
				API_JAVA_FILES_BASE_PATH + "/responses",
				ANDROID_ROOT_PATH
		};

		String[] outputFileExtentionPrefixArray = {
				"List",
				"List",
				"List",
				"activity_list_",
				"List",
				"item_view_list_",
				"Create",
				"Create",
				"Create",
				"activity_create_",
				"View",
				"View",
				"View",
				"activity_view_",
				"Get",
				"Get",
				"Get",
				"Get",
				"CreateOrUpdate",
				"CreateOrUpdate",
				"Delete",
				"Delete",
				""
			  };

		String[] outputFileExtentionSuffixArray = {
				"Activity.java",
				"Presenter.java",
				"View.java",
				".xml",
				"Adapter.java",
				".xml",
				"Activity.java",
				"Presenter.java",
				"View.java",
				".xml",
				"Activity.java",
				"Presenter.java",
				"View.java",
				".xml",
				"ListRequest.java",
				"ListResponse.java",
				"DetailsRequest.java",
				"DetailsResponse.java",
				"Request.java",
				"Response.java",
				"Request.java",
				"Response.java",
				"Common.txt"
		};

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
					if(outputFileExtentionSuffixArray[i].contains(".xml")){
						filePath = OUTPUT_DIR + "/" + outputDir[i] + "/" + outputFileExtentionPrefixArray[i]
								+ entity.getTable() + outputFileExtentionSuffixArray[i];
					} else {
						filePath = OUTPUT_DIR + "/" + outputDir[i] + "/" + outputFileExtentionPrefixArray[i]
								+ entity.getUpperCamelCaseClassName() + outputFileExtentionSuffixArray[i];
					}
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

			String tableDisplayName = (String) jsonObject.get("table_display_Name");
			entity.setTableDisplayName(tableDisplayName);
			
			String author = (String) jsonObject.get("author");
			entity.setAuthor(author);

			String userStory = (String) jsonObject.get("userStory");
			entity.setUserStory(userStory);

			String service = (String) jsonObject.get("service");
			entity.setService(service);

			String serviceDisplayName = (String) jsonObject.get("service_display_name");
			entity.setServiceDisplayName(serviceDisplayName);
			
			entity.setTableComment((String) jsonObject.get("table_comment"));

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
				f.setLowerSnakeCaseName(
						CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, (String) fobj.get("name")));
				f.setComment((String) fobj.get("comment"));
				Boolean isSearchable = (Boolean) fobj.get("is_searchable");
				if (isSearchable != null) {
					f.setIsSearchable(isSearchable);
				} else {
					f.setIsSearchable(false);
				}
				f.setDisplayName((String) fobj.get("display_name"));
				entity.addFields(f);
				
				Boolean isPrimary = (Boolean) fobj.get("is_primary");
				if(isPrimary != null){
					f.setIsPrimary(isPrimary);
					if(isPrimary){
						entity.setPrimaryUIField(f);
					}
				}
				
				Boolean isSecondary = (Boolean) fobj.get("is_secondary");
				if(isSecondary != null){
					f.setIsSecondary(isSecondary);
					if(isSecondary){
						entity.addSecondaryUIField(f);
					}
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
			entity.setDate(sdf.format(new Date()));
			
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
