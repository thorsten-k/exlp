package net.sf.exlp.monitor.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
public class MonitoringTestSeries
{
	    private Integer id;
	    private String name;
	    private String email;
	 
	    public MonitoringTestSeries() {
	 
	    }
	 
	    public MonitoringTestSeries(Integer id, String name, String email) {
	        this.id = id;
	        this.name = name;
	        this.email = email;
	    }
	 
	    @Id
	    public Integer getId() {
	        return this.id;
	    }
	 
	    public void setId(Integer id) {
	        this.id = id;
	    }
	 
	    public String getName() {
	        return this.name;
	    }
	 
	    public void setName(String name) {
	        this.name = name;
	    }
	 
	    public String getEmail() {
	        return email;
	    }
	 
	    public void setEmail(String email) {
	        this.email = email;
	    }
}
