package in.novopay.velocity.input;

public class InputEntityFields {
	private String name;
	private String lowerCamelCaseName;
	private String upperCamelCaseName;
	private String type;
	private Integer length;
	private Boolean notNull;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Boolean getNotNull() {
		return notNull;
	}

	public void setNotNull(Boolean notNull) {
		this.notNull = notNull;
	}

	public String addNotNull(){
        if(this.notNull) return " NOT NULL";
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
	
}
