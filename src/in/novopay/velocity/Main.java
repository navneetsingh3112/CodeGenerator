package in.novopay.velocity;

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

	static String TEMPLATES_DIR = "/Users/navaneet/office/eclipse-workspace/platformv3/CodeGeneration/src/in/novopay/velocity/templates";
	static String OUTPUT_DIR = "/Users/navaneet/office/eclipse-workspace/platformv3/CodeGeneration/src/in/novopay/velocity/output/";
	static String INPUT_DIR = "/Users/navaneet/office/eclipse-workspace/platformv3/CodeGeneration/src/in/novopay/velocity/input";
	
	public static void main(String[] args) throws IOException {
		VelocityContext context = new VelocityContext();
		InputEntity entity = getEntity();
		context.put("entity", entity);
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties velocityProperties = new Properties();
		velocityProperties.setProperty("file.resource.loader.path",TEMPLATES_DIR);
		velocityEngine.init(velocityProperties);

		Template t = velocityEngine.getTemplate("entity.vm");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		System.out.println(writer);
		PrintWriter pw = new PrintWriter(OUTPUT_DIR+ entity.getClassName() + "Entity.java","UTF-8");
		pw.print(writer.toString());
		pw.close();

		t = velocityEngine.getTemplate("repo.vm");
		writer = new StringWriter();
		t.merge(context, writer);
		System.out.println(writer);
		pw = new PrintWriter(OUTPUT_DIR+ entity.getClassName() + "Repository.java");
		pw.print(writer.toString());
		pw.close();

		t = velocityEngine.getTemplate("dao.vm");
		writer = new StringWriter();
		t.merge(context, writer);
		System.out.println(writer);
		pw = new PrintWriter(OUTPUT_DIR+ entity.getClassName() + "DAOService.java");
		pw.print(writer.toString());
		pw.close();

	}

	public static InputEntity getEntity() {
		JSONParser parser = new JSONParser();
		InputEntity entity = new InputEntity();
		try {
			Object obj = parser.parse(new FileReader(INPUT_DIR+"/input.json"));
			JSONObject jsonObject = (JSONObject) obj;
			String table = (String) jsonObject.get("table");
			entity.setTable(table);
			entity.setClassName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table));
			entity.setLowerCamelCaseClassName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table));
			entity.setUpperCamelCaseClassName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table));

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
				entity.addFields(f);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return entity;
	}

}
