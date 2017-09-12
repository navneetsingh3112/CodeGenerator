package in.novopay.codegenerator.input;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class InputEntity {
	private String table;
	private String author;
	private String lowerCamelCaseClassName;
	private String upperCamelCaseClassName;
	private String service;
	private String userStory;
	private String tableComment;
	private String lowerSnakeCaseClassName;
	private String deleteMode;
	
	List<InputEntityFields> dataFieldList = new ArrayList<>();
	List<InputEntityFields> auditFieldList = new ArrayList<>();
	
	public void addDataFields(InputEntityFields field) {
		dataFieldList.add(field);
	}

	public void addAuditFields(InputEntityFields field) {
		auditFieldList.add(field);
	}

	public List<InputEntityFields> getDataFieldList() {
		return dataFieldList;
	}

	public void setDataFieldList(List<InputEntityFields> dataFieldList) {
		this.dataFieldList = dataFieldList;
	}

	public List<InputEntityFields> getAuditFieldList() {
		return auditFieldList;
	}

	public void setAuditFieldList(List<InputEntityFields> auditFieldList) {
		this.auditFieldList = auditFieldList;
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

	public String getDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(String deleteMode) {
		this.deleteMode = deleteMode;
	}

}
