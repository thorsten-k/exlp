package net.sf.exlp.util.net.ads.data;


public class AdUser implements java.io.Serializable
{ 
	static final long serialVersionUID=1;
	
	private int id;
	private String vorName, nachName;
	private String account;
	private String eMail;
	private String info;
	
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public String getVorName() {return vorName;}
	public void setVorName(String vorName) {this.vorName = vorName;}
	
	public String getNachName() {return nachName;}
	public void setNachName(String nachName) {this.nachName = nachName;}
	
	public String getEMail() {return eMail;}
	public void setEMail(String mail) {eMail = mail;}

	public String getInfo() {return info;}
	public void setInfo(String info) {this.info = info;}
	
	public String getAccount() {return account;}
	public void setAccount(String account) {this.account = account;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(AdUser.class.getSimpleName());
			sb.append(" "+vorName);
			sb.append(" "+nachName);
			sb.append(" ("+eMail+")");
			sb.append(" ("+account+")");
			sb.append(" ("+info+")");
		return sb.toString();
	}
}
