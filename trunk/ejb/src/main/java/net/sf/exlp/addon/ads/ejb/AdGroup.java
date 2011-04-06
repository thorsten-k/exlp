package net.sf.exlp.addon.ads.ejb;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdGroup implements Serializable
{ 
	static final long serialVersionUID=1;
	
	public int id;
	public String name, beschreibung;
	public String distinguishedName, info;
	public ArrayList<String> alMember;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getBeschreibung() {return beschreibung;}
	public void setBeschreibung(String beschreibung) {this.beschreibung = beschreibung;}
	
	public String getDistinguishedName() {return distinguishedName;}
	public void setDistinguishedName(String distinguishedName) {this.distinguishedName = distinguishedName;}
	
	public String getInfo() {return info;}
	public void setInfo(String info) {this.info = info;}
	
	public ArrayList<String> getAlMember() {return alMember;}
	public void setAlMember(ArrayList<String> alMember) {this.alMember = alMember;}
	
	public void addMember(String dn)
	{
		if(alMember==null){alMember = new ArrayList<String>();}
		alMember.add(dn);
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(AdGroup.class.getSimpleName());
			sb.append(": "+name);
			sb.append(" ("+beschreibung+")");
		return sb.toString();
	}
}
