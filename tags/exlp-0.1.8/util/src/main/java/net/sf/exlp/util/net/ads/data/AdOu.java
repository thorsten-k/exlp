package net.sf.exlp.util.net.ads.data;


public class AdOu implements java.io.Serializable
{ 
	static final long serialVersionUID=1;
	
	private int id;
	private String distinguishedName;
	
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
