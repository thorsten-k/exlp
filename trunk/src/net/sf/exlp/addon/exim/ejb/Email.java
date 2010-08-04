package net.sf.exlp.addon.exim.ejb;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Email implements Serializable
{ 
	static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	
	public String email;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<
		
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" "+email);
		return sb.toString();
	}
}
