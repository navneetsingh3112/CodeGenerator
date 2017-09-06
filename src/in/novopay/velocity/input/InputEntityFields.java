package in.novopay.velocity.input;

import org.apache.commons.lang.StringUtils;

public class InputEntityFields {
	private String name;
	private String lowerCamelCaseName;
	private String upperCamelCaseName;
	private String lowerTrainCaseName;
	private Integer length;
	private Boolean isMandatory;
	private String javaType;
	private String webType;
	private String sqlType;
	private String comment;
	private String placeholder;
	private String displayName;
	private Long minLength;
	private Long maxLength;
	private String validationPattern;
	private String apiKey;
	private Boolean isSearchable;
	private Boolean isSortable;
	private Boolean isEditable;
	
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

	public String getLowerTrainCaseName() {
		return lowerTrainCaseName;
	}

	public void setLowerTrainCaseName(String lowerTrainCaseName) {
		this.lowerTrainCaseName = lowerTrainCaseName;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
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

	public String getWebType() {
		return webType;
	}

	public void setWebType(String webType) {
		this.webType = webType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	public String addNotNull(){
        if(this.isMandatory) return " NOT NULL";
        else return "";
    }
	
	public String getOrDefaultStringLength(){
        if(null != this.length) return this.length.toString();
        else return "64";
    }
	
	public String getOrDefaultIntegerLength(){
        if(null != this.length) return this.length.toString();
        else return "10";
    }
	
	public String addComment(){
		if(StringUtils.isNotBlank(comment)) return " COMMENT '"+ comment + "'";
        else return "";
	}

	public Boolean getMandatory() {
		return isMandatory;
	}

	public void setMandatory(Boolean mandatory) {
		isMandatory = mandatory;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Long getMinLength() {
		return minLength;
	}

	public void setMinLength(Long minLength) {
		this.minLength = minLength;
	}

	public Long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}

	public String getValidationPattern() {
		return validationPattern;
	}

	public void setValidationPattern(String validationPattern) {
		this.validationPattern = validationPattern;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Boolean getSearchable() {
		return isSearchable;
	}

	public void setSearchable(Boolean searchable) {
		isSearchable = searchable;
	}

	public Boolean getSortable() {
		return isSortable;
	}

	public void setSortable(Boolean sortable) {
		isSortable = sortable;
	}

	public Boolean getEditable() {
		return isEditable;
	}

	public void setEditable(Boolean editable) {
		isEditable = editable;
	}
}
