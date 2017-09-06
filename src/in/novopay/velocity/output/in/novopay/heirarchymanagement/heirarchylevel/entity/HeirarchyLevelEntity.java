package in.novopay.heirarchymanagement.heirarchylevel.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/***
 * 
 * @author Manoj
 *
 */

@Entity
@Table(name = "heirarchy_level")
public class HeirarchyLevelEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String levelName;
	private String levelCode;
	private String description;
	private String departmentName;
	private String heirarchyLevelType;

	public void setLevelName(String levelName){
		this.levelName=levelName;
	}
	public String getLevelName(){
		return levelName;
	}
	public void setLevelCode(String levelCode){
		this.levelCode=levelCode;
	}
	public String getLevelCode(){
		return levelCode;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public String getDescription(){
		return description;
	}
	public void setDepartmentName(String departmentName){
		this.departmentName=departmentName;
	}
	public String getDepartmentName(){
		return departmentName;
	}
	public void setHeirarchyLevelType(String heirarchyLevelType){
		this.heirarchyLevelType=heirarchyLevelType;
	}
	public String getHeirarchyLevelType(){
		return heirarchyLevelType;
	}
}