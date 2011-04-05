package net.sf.exlp.addon.hivis.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@Entity
@Table(name = "ExlpHierarchyCategory")
@NamedQueries
({
//	@NamedQuery(name="fOpenVpnCert",query="SELECT c FROM OpenVpnCert c WHERE serial = :serial")
})
public class HierarchyCategory implements Serializable
{ 
	static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String name;

	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" "+name);
		return sb.toString();
	}
}
