package in.novopay.velocity.input;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;

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
	private String parentApiKey;
	private Boolean isSearchable;
	private Boolean isSortable;
	private Boolean isNotEditable;
	private String masterDataType;
	private String masterDataSubType;
	private Boolean isCodeIncludedInDisplay;
	private String inputApi;
	private Boolean populateFromMasterData;
	private String inputApiListKey;
	private String inputApiDetailsKey;
	private String inputApiIdKey;
	private String inputApiCodeKey;
	private String inputApiValueKey;
	private Boolean isListElement;
	private String minDate;
	private String maxDate;
	private String searchType;
	private ArrayList<DataList> dataList = new ArrayList<>();

	public String getParentApiKey() {
		return parentApiKey;
	}

	public void setParentApiKey(String parentApiKey) {
		this.parentApiKey = parentApiKey;
	}

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

	public Boolean getIsNotEditable() {
		return isNotEditable;
	}

	public void setIsNotEditable(Boolean isNotEditable) {
		this.isNotEditable = isNotEditable;
	}

	public String getMasterDataType() {
		return masterDataType;
	}

	public void setMasterDataType(String masterDataType) {
		this.masterDataType = masterDataType;
	}

	public String getMasterDataSubType() {
		return masterDataSubType;
	}

	public void setMasterDataSubType(String masterDataSubType) {
		this.masterDataSubType = masterDataSubType;
	}

	public String getInputApi() {
		return inputApi;
	}

	public void setInputApi(String inputApi) {
		this.inputApi = inputApi;
	}

	public Boolean getPopulateFromMasterData() {
		return populateFromMasterData;
	}

	public void setPopulateFromMasterData(Boolean populateFromMasterData) {
		this.populateFromMasterData = populateFromMasterData;
	}

	public void addFields(DataList list) {
		dataList.add(list);
	}

	public ArrayList<DataList> getDataList() {
		return dataList;
	}

	public Boolean getIsCodeIncludedInDisplay() {
		return isCodeIncludedInDisplay;
	}

	public void setIsCodeIncludedInDisplay(Boolean isCodeIncludedInDisplay) {
		this.isCodeIncludedInDisplay = isCodeIncludedInDisplay;
	}

	public String getInputApiListKey() {
		return inputApiListKey;
	}

	public void setInputApiListKey(String inputApiListKey) {
		this.inputApiListKey = inputApiListKey;
	}

	public String getInputApiDetailsKey() {
		return inputApiDetailsKey;
	}

	public void setInputApiDetailsKey(String inputApiDetailsKey) {
		this.inputApiDetailsKey = inputApiDetailsKey;
	}

	public String getInputApiIdKey() {
		return inputApiIdKey;
	}

	public void setInputApiIdKey(String inputApiIdKey) {
		this.inputApiIdKey = inputApiIdKey;
	}

	public String getInputApiCodeKey() {
		return inputApiCodeKey;
	}

	public void setInputApiCodeKey(String inputApiCodeKey) {
		this.inputApiCodeKey = inputApiCodeKey;
	}

	public String getInputApiValueKey() {
		return inputApiValueKey;
	}

	public void setInputApiValueKey(String inputApiValueKey) {
		this.inputApiValueKey = inputApiValueKey;
	}

	public Boolean getIsListElement() {
		return isListElement;
	}

	public void setIsListElement(Boolean isListElement) {
		this.isListElement = isListElement;
	}

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
}
