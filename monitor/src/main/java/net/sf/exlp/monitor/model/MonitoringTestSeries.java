package net.sf.exlp.monitor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MonitoringTestSeries
{
	// **********   Fields   *************** /
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	 
	// **********   Constructor   *********** /
	public MonitoringTestSeries(String name)
	{
		this.name = name;
	}
	 
	// ********   Getter/Setter   *********** //
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	 
	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}
	
	// ***********   Methods   ************** //
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append("(").append(id).append(")");
			sb.append(" ").append(name);
		return sb.toString();
	}
}