package in.novopay.codegenerator.input;

import org.apache.commons.lang.StringUtils;

public class Field {
	private String name;
	private String lowerCamelCaseName;
	private String upperCamelCaseName;
	private String lowerSnakeCaseName;
	private Integer maxLength;
	private Integer minLength;
	private String javaType;
	private String sqlType;
	private String pattern;
	private String comment;
	private boolean isMandatory;
	private boolean isSearchable;
	private boolean isListElement;
	private boolean isSortable;
	private String mapTo;
	private boolean isPrimaryKey;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLowerCamelCaseName() {
		return lowerCamelCaseName;
	}

	public void setLowerCamelCaseName(String lowerCamelCaseName) {
		this.lowerCamelCaseName = lowerCamelCaseName;
	}

	public String getUpperCamelCaseName() {
		return upperCamelCaseName;
	}

	public void setUpperCamelCaseName(String upperCamelCaseName) {
		this.upperCamelCaseName = upperCamelCaseName;
	}

	public String getLowerSnakeCaseName() {
		return lowerSnakeCaseName;
	}

	public void setLowerSnakeCaseName(String lowerSnakeCaseName) {
		this.lowerSnakeCaseName = lowerSnakeCaseName;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getComment() {
		if (StringUtils.isNotBlank(comment)) {
			return " COMMENT '" + comment + "'";
		} else {
			return "";
		}

	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getNotNullConstraint() {
		if (this.isMandatory) {
			return " NOT NULL";
		} else {
			return "";
		}
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public boolean isSearchable() {
		return isSearchable;
	}

	public void setSearchable(boolean isSearchable) {
		this.isSearchable = isSearchable;
	}

	public boolean isListElement() {
		return isListElement;
	}

	public void setListElement(boolean isListElement) {
		this.isListElement = isListElement;
	}

	public boolean isSortable() {
		return isSortable;
	}

	public void setSortable(boolean isSortable) {
		this.isSortable = isSortable;
	}

	public String getMapTo() {
		return mapTo;
	}

	public void setMapTo(String mapTo) {
		this.mapTo = mapTo;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getSQLPrimaryKeyString() {
		if (this.isPrimaryKey) {
			return " AUTO_INCREMENT ";
		} else {
			return "";
		}
	}
}
