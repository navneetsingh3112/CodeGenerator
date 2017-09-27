package in.novopay.codegenerator.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Entity {
	private String entity;
	private String lowerCamelCaseEntityName;
	private String upperCamelCaseEntityName;
	private String lowerSnakeCaseEntityName;
	private String tableComment;
	private List<Field> dataFieldList = new ArrayList<>();
	private Map<String,Field> dataFieldMap = new HashMap<>();
	private List<Field> auditFieldList = new ArrayList<>();
	private String entityAliasName;
	
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getLowerCamelCaseEntityName() {
		return lowerCamelCaseEntityName;
	}

	public void setLowerCamelCaseEntityName(String lowerCamelCaseEntityName) {
		this.lowerCamelCaseEntityName = lowerCamelCaseEntityName;
	}

	public String getUpperCamelCaseEntityName() {
		return upperCamelCaseEntityName;
	}

	public void setUpperCamelCaseEntityName(String upperCamelCaseEntityName) {
		this.upperCamelCaseEntityName = upperCamelCaseEntityName;
	}

	public String getLowerSnakeCaseEntityName() {
		return lowerSnakeCaseEntityName;
	}

	public void setLowerSnakeCaseEntityName(String lowerSnakeCaseEntityName) {
		this.lowerSnakeCaseEntityName = lowerSnakeCaseEntityName;
	}

	public String getStringComment() {
		return tableComment;
	}

	public String getTableComment() {
		if (StringUtils.isNotBlank(tableComment)) {
			return " COMMENT '" + tableComment + "'";
		} else {
			return "";
		}
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment.replaceAll("'", "''");
	}

	public List<Field> getDataFieldList() {
		return dataFieldList;
	}

	public void setDataFieldList(List<Field> dataFieldList) {
		this.dataFieldList = dataFieldList;
	}

	public List<Field> getAuditFieldList() {
		return auditFieldList;
	}

	public void setAuditFieldList(List<Field> auditFieldList) {
		this.auditFieldList = auditFieldList;
	}

	public void addDataFields(Field field) {
		dataFieldMap.put(field.getName(), field);
		dataFieldList.add(field);
	}

	public void addAuditFields(Field field) {
		auditFieldList.add(field);
	}

	public String getEntityAliasName() {
		return entityAliasName;
	}

	public void setEntityAliasName(String entityAliasName) {
		this.entityAliasName = entityAliasName;
	}

}
