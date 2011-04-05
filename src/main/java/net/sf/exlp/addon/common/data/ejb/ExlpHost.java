package net.sf.exlp.addon.common.data.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries
({
	@NamedQuery(name="fExlpHost",query="SELECT r FROM ExlpHost r WHERE r.ip = :ip")
})
public class ExlpHost implements Serializable
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String ip,name,dns;
	
	@ManyToOne
	private ExlpCountry country;
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
		
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getIp() {return ip;}
	public void setIp(String ip) {this.ip = ip;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getDns() {return dns;}
	public void setDns(String dns) {this.dns = dns;}
	
	public ExlpCountry getCountry() {return country;}
	public void setCountry(ExlpCountry country) {this.country = country;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" ip="+ip);
		return sb.toString();
	}
}
