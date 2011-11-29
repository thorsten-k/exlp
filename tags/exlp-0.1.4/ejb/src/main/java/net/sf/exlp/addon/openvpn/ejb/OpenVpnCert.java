package net.sf.exlp.addon.openvpn.ejb;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "OpenVpnCert")
@NamedQueries
({
	@NamedQuery(name="fOpenVpnCert",query="SELECT c FROM OpenVpnCert c WHERE serial = :serial")
})
public class OpenVpnCert implements Serializable
{ 
	static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private int serial;
	private Date notBefore,notAfter;
	private String dn;

	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public int getSerial() {return serial;	}
	public void setSerial(int serial) {this.serial = serial;}
	
	public Date getNotAfter() {return notAfter;}
	public void setNotAfter(Date notAfter) {this.notAfter = notAfter;}
	
	public Date getNotBefore() {return notBefore;}
	public void setNotBefore(Date notBefore) {	this.notBefore = notBefore;}
	
	public String getDn() {return dn;}
	public void setDn(String dn) {this.dn = dn;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" "+serial);
			sb.append(" "+dn);
		return sb.toString();
	}
}
