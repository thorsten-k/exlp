package net.sf.exlp.addon.apache.ejb;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import net.sf.exlp.addon.common.data.ejb.ExlpHost;

@Entity
@NamedQueries
({
	@NamedQuery(name="fExlpApache",query="SELECT r FROM ExlpApache r WHERE r.record = :record AND r.url = :url AND r.host.id = :hostid")
})
public class ExlpApache implements Serializable
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private Date record;
	private int size;
	private String url;
	private String req;
	private String code;

	@ManyToOne
	private ExlpHost host;
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}

	public int getSize() {return size;}
	public void setSize(int size) {this.size = size;}

	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}

	public ExlpHost getHost() {return host;}
	public void setHost(ExlpHost host) {this.host = host;}
	
	public String getReq() {return req;}
	public void setReq(String req) {this.req = req;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}
