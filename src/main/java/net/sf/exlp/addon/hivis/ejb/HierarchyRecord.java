package net.sf.exlp.addon.hivis.ejb;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@Entity
@Table(name = "ExlpHierarchyRecord")
@NamedQueries
({
//	@NamedQuery(name="fOpenVpnCert",query="SELECT c FROM OpenVpnCert c WHERE serial = :serial")
})
public class HierarchyRecord implements Serializable
{ 
	static final long serialVersionUID=1;
	
	public static enum Interval{Single,Second,Minute,Hour,Day,Week,Month,Year};
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private Interval interval;

	@ManyToOne(fetch=FetchType.LAZY)
	private HierarchyEntity entity;
	
	private Date record;
	
	private int value;
	
	private int valueChilds;

	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public Interval getInterval() {return interval;}
	public void setInterval(Interval interval) {this.interval = interval;}
	
	public HierarchyEntity getEntity() {return entity;}
	public void setEntity(HierarchyEntity entity) {this.entity = entity;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
	
	public int getValue() {return value;}
	public void setValue(int value) {this.value = value;}
	
	public int getValueChilds() {return valueChilds;}
	public void setValueChilds(int valueChilds) {this.valueChilds = valueChilds;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}
