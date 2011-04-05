package net.sf.exlp.addon.exim.data.ejb;

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
	@NamedQuery(name="fGreylist",query="SELECT g FROM ExlpGreylist g WHERE g.record = :record AND g.from.id = :fromId AND g.rcpt.id = :rcptId"),
	@NamedQuery(name="fGreylistForRcptInInterval",query="SELECT g FROM ExlpGreylist g WHERE g.rcpt.id = :rcptId AND g.record >= :recordFrom AND g.record < :recordTo")
})
public class ExlpGreylist implements Serializable
{ 
	static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	
	private Date record;
	
	@ManyToOne
	private ExlpEmail from;
	
	@ManyToOne
	private ExlpEmail rcpt;
	
	@ManyToOne
	private ExlpHost host;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<
		
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
	
	public ExlpEmail getFrom() {return from;}
	public void setFrom(ExlpEmail from) {this.from = from;}
	
	public ExlpEmail getRcpt() {return rcpt;}
	public void setRcpt(ExlpEmail rcpt) {this.rcpt = rcpt;}
		
	public ExlpHost getHost() {return host;}
	public void setHost(ExlpHost host) {this.host = host;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}