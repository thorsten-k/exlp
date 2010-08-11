package net.sf.exlp.addon.dirsize.data.ejb;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries
({
//	@NamedQuery(name="fExlpApache",query="SELECT r FROM ExlpApache r WHERE r.record = :record AND r.url = :url AND r.host.id = :hostid")
})
public class ExlpDirectorySize implements Serializable
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private ExlpDirectory directory;
	
	private long size;

	private Date record;
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public ExlpDirectory getDirectory() {return directory;}
	public void setDirectory(ExlpDirectory directory) {this.directory = directory;}
	
	public long getSize() {return size;}
	public void setSize(long size) {this.size = size;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}
