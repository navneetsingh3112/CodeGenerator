package in.novopay.velocity.input;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class InputEntity {
	private String table;
	private String tableDisplayName;
	private String author;
	private String lowerCamelCaseClassName;
	private String upperCamelCaseClassName;
	private String service;
	private String serviceDisplayName;
	private String userStory;
	private String tableComment;
	private String lowerSnakeCaseClassName;
	private String date;
	private InputEntityFields primaryUIField;
	private ArrayList<InputEntityFields> secondaryUIFieldList = new ArrayList<>();
	private ArrayList<InputEntityFields> fieldList = new ArrayList<>();
	
	public InputEntityFields getPrimaryUIField() {
		return primaryUIField;
	}

	public void setPrimaryUIField(InputEntityFields primaryUIField) {
		this.primaryUIField = primaryUIField;
	}

	public ArrayList<InputEntityFields> getSecondaryUIFieldList() {
		return secondaryUIFieldList;
	}

	public void addSecondaryUIField(InputEntityFields secondaryUIField) {
		secondaryUIFieldList.add(secondaryUIField);
	}

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	
	public String addTableComment(){
		if(StringUtils.isNotBlank(tableComment)) return " COMMENT = '"+ tableComment + "'";
        else return "";
	}

	public String getLowerSnakeCaseClassName() {
		return lowerSnakeCaseClassName;
	}

	public void setLowerSnakeCaseClassName(String lowerSnakeCaseClassName) {
		this.lowerSnakeCaseClassName = lowerSnakeCaseClassName;
	}

	public String getTableDisplayName() {
		return tableDisplayName;
	}

	public void setTableDisplayName(String tableDisplayName) {
		this.tableDisplayName = tableDisplayName;
	}

	public String getServiceDisplayName() {
		return serviceDisplayName;
	}

	public void setServiceDisplayName(String serviceDisplayName) {
		this.serviceDisplayName = serviceDisplayName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
