package in.novopay.accounting.internalaccount.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "internal_account_template")
public class InternalAccountTemplateEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String description;
	private String name;
	private String createdBy;
	private Date createdOn;


	public void setDescription(String description){
		this.description=description;
	}
	public String getDescription(){
		return description;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy=createdBy;
	}
	public String getCreatedBy(){
		return createdBy;
	}

	public void setCreatedOn(Date createdOn){
		this.createdOn=createdOn;
	}
	public Date getCreatedOn(){
		return createdOn;
	}
}