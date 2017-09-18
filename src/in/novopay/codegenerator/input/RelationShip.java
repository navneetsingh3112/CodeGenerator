package in.novopay.codegenerator.input;

public class RelationShip {
	
	private String primaryEntity;
	private String secondaryEntity;
	private String type;
	
	public String getPrimaryEntity() {
		return primaryEntity;
	}
	public void setPrimaryEntity(String primaryEntity) {
		this.primaryEntity = primaryEntity;
	}
	public String getSecondaryEntity() {
		return secondaryEntity;
	}
	public void setSecondaryEntity(String secondaryEntity) {
		this.secondaryEntity = secondaryEntity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
