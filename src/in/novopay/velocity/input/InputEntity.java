package in.novopay.velocity.input;

import java.util.ArrayList;


public class InputEntity {
		private String table;
		private String lowerCamelCaseClassName;
		private String upperCamelCaseClassName;
		private String className;
		private String service;
		private String userStory;

		ArrayList<InputEntityFields> fieldList = new ArrayList<>();

		public void addFields(InputEntityFields field) {
			fieldList.add(field);
		}

		public ArrayList<InputEntityFields> getFieldList() {
			return fieldList;
		}

		public String getTable() {
			return table;
		}

		public void setTable(String table) {
			this.table = table;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getService() {
			return service;
		}

		public void setService(String service) {
			this.service = service;
		}

		public String getUserStory() {
			return userStory;
		}

		public void setUserStory(String userStory) {
			this.userStory = userStory;
		}

		public String getLowerCamelCaseClassName() {
			return lowerCamelCaseClassName;
		}

		public void setLowerCamelCaseClassName(String lowerCamelCaseClassName) {
			this.lowerCamelCaseClassName = lowerCamelCaseClassName;
		}

		public String getUpperCamelCaseClassName() {
			return upperCamelCaseClassName;
		}

		public void setUpperCamelCaseClassName(String upperCamelCaseClassName) {
			this.upperCamelCaseClassName = upperCamelCaseClassName;
		}

}
