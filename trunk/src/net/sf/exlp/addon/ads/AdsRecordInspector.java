package net.sf.exlp.addon.ads;

import java.util.ArrayList;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import net.sf.exlp.addon.ads.ejb.AdGroup;
import net.sf.exlp.addon.ads.ejb.AdOu;
import net.sf.exlp.addon.ads.ejb.AdUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class AdsRecordInspector
{
	static Log logger = LogFactory.getLog(AdsRecordInspector.class);
	
	public static enum Typ{ Unknown, USER, OU, GROUP, CONTACT }
	
	public static Typ getTyp(SearchResult sr){return getTyp(sr.getAttributes());}
	public static Typ getTyp(Attributes att)
	{
		Typ adTyp = Typ.Unknown;
		ArrayList<String> alDebug = new ArrayList<String>();
		
		NamingEnumeration ne = att.getAll();
		try
		{
			while (ne.hasMore())
			{
				Object obj2 = ne.next ();
				String s = obj2.toString ();
				alDebug.add(s);
				if (s.startsWith ("objectClass:"))
				{
					int index = s.indexOf(':') + 2;
					String oc = s.substring(index);
					if(oc.equals("top, group")){return Typ.GROUP;}
					if(oc.equals("top, person, organizationalPerson, user")){return Typ.USER;}
					if(oc.equals("top, organizationalUnit")){return Typ.OU;}
					if(oc.equals("top, person, organizationalPerson, contact")){return Typ.CONTACT;}
				}
			}
		}
		catch (NamingException e) {e.printStackTrace();}
		if(adTyp.equals(Typ.Unknown)){for(String l : alDebug){logger.debug(l);}}
		return adTyp;
	}
	
	
	public static AdGroup getAdGroup(SearchResult sr){return getAdGroup(sr.getAttributes());}
	public static AdGroup getAdGroup(Attributes att)
	{
		AdGroup adg = new AdGroup();
		NamingEnumeration ne = att.getAll();
		try
		{
			while (ne.hasMore())
			{
				String s = ne.next().toString ();
				if (s.startsWith ("distinguishedName:")){adg.setDistinguishedName(s.substring(s.indexOf(':')+2));}
				else if (s.startsWith ("info:")){adg.setInfo(s.substring(s.indexOf(':')+2));}
				else if (s.startsWith ("description:")){adg.setBeschreibung(s.substring(s.indexOf(':')+2));}
				else if (s.startsWith ("cn:")){adg.setName(s.substring(s.indexOf(':')+2));}
				else if (s.startsWith ("member:"))
				{
					String member = s.substring(s.indexOf(':')+2);
					String[] mb = member.split("CN");
					for(String m : mb)
					{
						if(m.length()>0)
						{
							m=m.trim();
							if(m.endsWith(",")){m=m.substring(0,m.length()-1);}
							adg.addMember("CN"+m);
						}
					}
				}
				logger.trace(s);
			}
		}
		catch (NamingException e) {e.printStackTrace();}
		return adg;
	}
	
	public static AdOu getAdOu(SearchResult sr)
	{
		AdOu ado = new AdOu();
		Attributes att = sr.getAttributes();
		NamingEnumeration ne = att.getAll();
		try
		{
			while (ne.hasMore())
			{
				Object obj2 = ne.next ();
				String s = obj2.toString ();
				if (s.startsWith ("distinguishedName:")){ado.setDistinguishedName(s.substring(s.indexOf(':')+2));}
				
			}
		}
		catch (NamingException e) {e.printStackTrace();}
		return ado;
	}
	
	public static AdUser getUser(SearchResult sr){return getUser(sr.getAttributes());}
	public static AdUser getUser(Attributes att)
	{
		AdUser adu = new AdUser();
		NamingEnumeration ne = att.getAll();
		try
		{
			while (ne.hasMore())
			{
				String s = ne.next ().toString();
				logger.trace(s);
				if (s.startsWith ("mail:")){adu.setEMail(s.substring(s.indexOf(':')+2));}
				else if (s.startsWith ("info:")){adu.setInfo(s.substring(s.indexOf(':')+2));}
				else if (s.startsWith ("sAMAccountName:")){adu.setAccount(s.substring(s.indexOf(':')+2));}
				else if (s.startsWith ("name:"))
				{
					String name =s.substring(s.indexOf(':')+2);
					int index = name.indexOf(',');
					adu.setNachName(name.substring(0, index));
					adu.setVorName(name.substring(index+2));
				}
			}
		}
		catch (NamingException e) {e.printStackTrace();}
		return adu;
	}
}
