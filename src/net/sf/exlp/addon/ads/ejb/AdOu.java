package net.sf.exlp.addon.ads.ejb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdOu implements java.io.Serializable
{ 
	static final long serialVersionUID=1;
	
	private int id;
	private String distinguishedName;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public String getDistinguishedName() {return distinguishedName;}
	public void setDistinguishedName(String distinguishedName) {this.distinguishedName = distinguishedName;}
	
	public static String debug(AdOu ado)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("distinguishedName: "+ado.getDistinguishedName());
		return sb.toString();
	}
}
