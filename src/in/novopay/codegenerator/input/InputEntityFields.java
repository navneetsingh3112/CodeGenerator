package in.novopay.codegenerator.input;

import org.apache.commons.lang.StringUtils;

public class InputEntityFields {
	private String name;
	private String lowerCamelCaseName;
	private String upperCamelCaseName;

	private Integer maxLength;
	private Integer minLength;
	private String pattern;
	private String lowerSnakeCaseName;
	private Boolean isMandatory;

	private String javaType;
	private String sqlType;

	private String comment;
	private Boolean isSearchable;
	private Boolean isListElement;
	
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

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String addNotNull() {
		if (this.isMandatory) {
			return " NOT NULL";
		} else {
			return "";
		}
	}

	public String getOrDefaultStringLength() {
		if (null != this.maxLength) {
			return this.maxLength.toString();
		} else {
			return "64";
		}
	}

	public String getOrDefaultIntegerLength() {
		if (null != this.maxLength) {
			return this.maxLength.toString();
		} else {
			return "10";
		}
	}

	public String getLowerSnakeCaseName() {
		return lowerSnakeCaseName;
	}

	public void setLowerSnakeCaseName(String lowerSnakeCaseName) {
		this.lowerSnakeCaseName = lowerSnakeCaseName;
	}

	public String addComment() {
		if (StringUtils.isNotBlank(comment)) {
			return " COMMENT '" + comment + "'";
		} else {
			return "";
		}
	}

	public Boolean getIsSearchable() {
		return isSearchable;
	}

	public void setIsSearchable(Boolean isSearchable) {
		this.isSearchable = isSearchable;
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

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Boolean getIsListElement() {
		return isListElement;
	}

	public void setIsListElement(Boolean isListElement) {
		this.isListElement = isListElement;
	}

}
