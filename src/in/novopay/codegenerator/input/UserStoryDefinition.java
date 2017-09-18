package in.novopay.codegenerator.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class UserStoryDefinition {
	private String name;
	private String lowerName;
	private String lowerCamelCaseName;
	private String upperCamelCaseName;
	private String flatCaseName;
	private String service;
	private String author;

	private List<Entity> entityList = new ArrayList<>();
	private List<RelationShip> relationshipList = new ArrayList<>();
	private Map<String, Entity> entityMap = new HashMap<>();
	private String deleteMode;

	public void addEntity(Entity entity) {
		entityList.add(entity);
		entityMap.put(entity.getEntity(), entity);
	}

	public void addRelationShip(RelationShip relationShip) {
		relationshipList.add(relationShip);
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}

	public List<RelationShip> getRelationshipList() {
		return relationshipList;
	}

	public void setRelationshipList(List<RelationShip> relationshipList) {
		this.relationshipList = relationshipList;
	}

	public Entity getEntity(String entity) {
		return entityMap.get(entity);
	}

	public Entity getPrimaryEntity(RelationShip rsp) {
		return entityMap.get(rsp.getPrimaryEntity());
	}

	public Entity getSecondaryEntity(RelationShip rsp) {
		return entityMap.get(rsp.getSecondaryEntity());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
	
	public String getLowerName() {
		return lowerName;
	}

	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
	}

	public String getFlatCaseName() {
		return flatCaseName;
	}

	public void setFlatCaseName(String flatCaseName) {
		this.flatCaseName = flatCaseName;
	}

	public String relationShipBetweenEntities(String entityA, String entityB) {
		for (RelationShip rsp : relationshipList) {
			if ((StringUtils.equalsIgnoreCase(rsp.getPrimaryEntity(), entityA)
					|| StringUtils.equalsIgnoreCase(rsp.getPrimaryEntity(), entityB))
					&& (StringUtils.equalsIgnoreCase(rsp.getSecondaryEntity(), entityA)
							|| StringUtils.equalsIgnoreCase(rsp.getSecondaryEntity(), entityB)))
				return rsp.getType();
		}
		return null;
	}

	public String getDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(String deleteMode) {
		this.deleteMode = deleteMode;
	}

	public Entity getPrimaryEntity() {
		for (RelationShip rsp : relationshipList) {
			if (rsp.getPrimaryEntity() != null) {
				return entityMap.get(rsp.getPrimaryEntity());
			}
		}
		return null;
	}
}
