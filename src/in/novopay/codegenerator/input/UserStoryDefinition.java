package in.novopay.codegenerator.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.CaseFormat;

public class UserStoryDefinition {
	private String name;
	private String lowerName;
	private String lowerCamelCaseName;
	private String upperCamelCaseName;
	private String flatCaseName;
	private String service;
	private String author;
	private String primaryEntity;

	private List<Entity> entityList = new ArrayList<>();
	private List<RelationShip> relationshipList = new ArrayList<>();
	private Map<String, Entity> entityMap = new HashMap<>();
	private String deleteMode;

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

	public void setPrimaryEntity(String primaryEntity) {
		this.primaryEntity = primaryEntity;
	}

	public String getPrimaryEntity() {
		return primaryEntity;
	}

	public Entity getPrimaryEntityFromMap() {
		return this.entityMap.get(primaryEntity);
	}

	public String getDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(String deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void addEntity(Entity entity) {
		entityList.add(entity);
		entityMap.put(entity.getEntity(), entity);
	}

	public void addRelationShip(RelationShip relationShip) {
		relationshipList.add(relationShip);
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

	public String columnListForDetails() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < entityList.size(); i++) {
			List<Field> dataFieldList = entityList.get(i).getDataFieldList();
			for (Field field : dataFieldList) {
				if (sb.length() != 0) {
					sb.append(" , ");
				}
				sb.append(entityList.get(i).getEntityAliasName() + "." + field.getLowerSnakeCaseName() + " "
						+ entityList.get(i).getEntityAliasName() + "_" + field.getLowerSnakeCaseName());
			}
		}
		return sb.toString();
	}

	public String columnListForList() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < entityList.size(); i++) {
			List<Field> dataFieldList = entityList.get(i).getDataFieldList();
			for (Field field : dataFieldList) {
				if (!field.isListElement()) {
					continue;
				}
				if (sb.length() != 0) {
					sb.append(" , ");
				}
				sb.append(entityList.get(i).getEntityAliasName() + "." + field.getLowerSnakeCaseName() + " "
						+ entityList.get(i).getEntityAliasName() + "_" + field.getLowerSnakeCaseName());
			}
		}
		return sb.toString();
	}

	public String getJoinStatement() {
		StringBuilder sb = new StringBuilder();
		sb.append(getPrimaryEntity() + " " + getPrimaryEntityFromMap().getEntityAliasName());
		for (int i = 0; i < relationshipList.size(); i++) {
			Entity pEntity = entityMap.get(relationshipList.get(i).getPrimaryEntity());
			Entity sEntity = entityMap.get(relationshipList.get(i).getSecondaryEntity());
			String relationShipType = relationshipList.get(i).getType();
			sb.append(getJoinFromRelationShipType(relationShipType));
			sb.append(sEntity.getEntity() + " " + sEntity.getEntityAliasName());
			sb.append(" ON ");
			sb.append(pEntity.getEntityAliasName() + ".id = " + sEntity.getEntityAliasName() + "." + pEntity.getEntity()
					+ "_id");
		}
		return sb.toString();
	}

	public Map<String, String> getSearchableMap() {
		Map<String, String> keyAliasMap = new HashMap<>();
		for (int i = 0; i < entityList.size(); i++) {
			List<Field> dataFieldList = entityList.get(i).getDataFieldList();
			for (Field field : dataFieldList) {
				if (field.isSearchable()) {
					String key = field.getMapTo() == null ? field.getLowerSnakeCaseName()
							: field.getMapTo().toLowerCase();
					String value = entityList.get(i).getEntityAliasName() + "." + field.getLowerSnakeCaseName();
					keyAliasMap.put(key, value);
				}
			}
		}
		return keyAliasMap;
	}

	public Map<String, String> getSortableMap() {
		Map<String, String> keyAliasMap = new HashMap<>();
		for (int i = 0; i < entityList.size(); i++) {
			List<Field> dataFieldList = entityList.get(i).getDataFieldList();
			for (Field field : dataFieldList) {
				if (field.isSortable()) {
					String key = field.getMapTo() == null ? field.getLowerSnakeCaseName()
							: field.getMapTo().toLowerCase();
					String value = entityList.get(i).getEntityAliasName() + "." + field.getLowerSnakeCaseName();
					keyAliasMap.put(key, value);
				}
			}
		}
		return keyAliasMap;
	}

	public Map<Entity,Map<String, String>> getMapForResultSetList() {
		Map<Entity,Map<String, String>> entityKeyAliasMap = new HashMap<>();
		for (int i = 0; i < entityList.size(); i++) {
			Map<String, String> keyAliasMap = new HashMap<>();
			List<Field> dataFieldList = entityList.get(i).getDataFieldList();
			for (Field field : dataFieldList) {
				if (field.isListElement()) {
					String key = field.getLowerSnakeCaseName();
					String value = entityList.get(i).getEntityAliasName() + "_" + field.getLowerSnakeCaseName();
					keyAliasMap.put(key, value);
				}
			}
			entityKeyAliasMap.put(entityList.get(i), keyAliasMap);
		}
		return entityKeyAliasMap;
	}

	public Map<String, String> getMapForResultSetDetails() {
		Map<String, String> keyAliasMap = new HashMap<>();
		for (int i = 0; i < entityList.size(); i++) {
			List<Field> dataFieldList = entityList.get(i).getDataFieldList();
			for (Field field : dataFieldList) {
				String key = field.getMapTo() == null ? field.getLowerSnakeCaseName() : field.getMapTo().toLowerCase();
				String value = entityList.get(i).getEntityAliasName() + "_" + field.getLowerSnakeCaseName();
				keyAliasMap.put(key, value);
			}
		}
		return keyAliasMap;
	}

	public Field getPrimaryEntityPrimaryKeyField() {
		Entity pEntity = getPrimaryEntityFromMap();
		for (Field f : pEntity.getDataFieldList()) {
			if (f.isPrimaryKey()) {
				return f;
			}
		}
		return null;
	}

	public String getFieldMaptoOrName(Field f) {
		if (f.getMapTo() != null) {
			return f.getMapTo();
		} else {
			return f.getLowerSnakeCaseName();
		}
	}

	public String convertSnakeCaeToLowerCamelCase(String snakeCase) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, snakeCase);
	}

	public String getUpdateQueryForLogicalDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(getJoinStatement());
		sb.append(" SET ");
		StringBuilder setSb = new StringBuilder();
		for (Entity entity : entityList) {
			if(setSb.length() > 0){
				setSb.append(" , ");
			}
			setSb.append(entity.getEntityAliasName()+".is_deleted = true ");
		}
		sb.append(setSb);
		sb.append(" WHERE "+getPrimaryEntityFromMap().getEntityAliasName()+"."+getPrimaryEntityPrimaryKeyField().getLowerSnakeCaseName()+" = ?1");
		return sb.toString();
	}

	private String getJoinFromRelationShipType(String relationShipType) {
		if ("one-to-one".equalsIgnoreCase(relationShipType)) {
			return " JOIN ";
		}
		if ("one-to-many".equalsIgnoreCase(relationShipType)) {
			return " JOIN ";
		}
		if ("many-to-one".equalsIgnoreCase(relationShipType)) {
			return " JOIN ";
		}
		return null;
	}
}
