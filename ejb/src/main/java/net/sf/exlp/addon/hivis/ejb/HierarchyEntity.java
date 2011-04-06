package net.sf.exlp.addon.hivis.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ExlpHierarchyEntity")
@NamedQueries
({
//	@NamedQuery(name="fOpenVpnCert",query="SELECT c FROM OpenVpnCert c WHERE serial = :serial")
})
public class HierarchyEntity implements Serializable
{ 
	static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch=FetchType.LAZY)
	private HierarchyCategory category;
	
	@OneToOne(fetch=FetchType.LAZY)
	private HierarchyEntity parent;

	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public HierarchyCategory getCategory() {return category;}
	public void setCategory(HierarchyCategory category) {this.category = category;}
	
	public HierarchyEntity getParent() {return parent;}
	public void setParent(HierarchyEntity parent) {this.parent = parent;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}
