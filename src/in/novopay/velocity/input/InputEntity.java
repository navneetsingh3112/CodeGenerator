package in.novopay.velocity.input;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class InputEntity {
	private String table;
	private String author;
	private String lowerCamelCaseClassName;
	private String upperCamelCaseClassName;
	private String lowerTrainCaseClassName;
	private String service;
	private String userStory;
	private String tableComment;
	private String fetchApiKey;
	private String displayName;

	ArrayList<InputEntityFields> fieldList = new ArrayList<>();
	ArrayList<InputEntityButtons> buttonList = new ArrayList<>();
	ArrayList<ActionButton> listViewActionButtonList = new ArrayList<ActionButton>();
	
	public void addListViewActionButton(ActionButton actionButton) {
		this.listViewActionButtonList.add(actionButton);
	}
	
	public ArrayList<ActionButton> getListViewActionButtonList() {
		return this.listViewActionButtonList;
	}

	public void addButton(InputEntityButtons button) {
		buttonList.add(button);
	}

	public ArrayList<InputEntityButtons> getButtonList() {
		return buttonList;
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

	public String getLowerTrainCaseClassName() {
		return lowerTrainCaseClassName;
	}

	public void setLowerTrainCaseClassName(String lowerTrainCaseClassName) {
		this.lowerTrainCaseClassName = lowerTrainCaseClassName;
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

	public String getFetchApiKey() {
		return fetchApiKey;
	}

	public void setFetchApiKey(String fetchApiKey) {
		this.fetchApiKey = fetchApiKey;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
