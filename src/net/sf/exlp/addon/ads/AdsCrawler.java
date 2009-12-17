package net.sf.exlp.addon.ads;

import java.util.ArrayList;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

import net.sf.exlp.addon.ads.ejb.AdGroup;
import net.sf.exlp.addon.ads.ejb.AdOu;
import net.sf.exlp.addon.ads.ejb.AdUser;
import net.sf.exlp.io.InputChoiceReader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.Connector;

import org.apache.log4j.Logger;

public class AdsCrawler
{
	static Logger logger = Logger.getLogger(AdsCrawler.class);
	
	public static ArrayList<AdUser> getAllAdUser(String search, DirContext ctx)
	{
		ArrayList<AdUser> alAdu = new ArrayList<AdUser>();
		try
		{
			Attributes matchAttrs = new BasicAttributes (true);
			//matchAttrs.put (new BasicAttribute ("mail"));
			logger.debug("suche: "+search);
			NamingEnumeration<SearchResult> answer = ctx.search(search, null);
			
			while (answer.hasMore())
			{
				logger.debug("antwort2");
				SearchResult sr = (SearchResult)answer.next ();
				AdsRecordInspector.Typ adsTyp = AdsRecordInspector.getTyp(sr);
				logger.trace("Objekt von Typ: "+adsTyp);
				
				switch(adsTyp)
				{
					case USER:	AdUser adu = AdsRecordInspector.getUser(sr);
								alAdu.add(adu);
								break;
					case OU:	AdOu ado = AdsRecordInspector.getAdOu(sr);
								alAdu.addAll(AdsCrawler.getAllAdUser(ado.getDistinguishedName(), ctx));
								break;
					default:	logger.warn("Unknown Handling for: "+adsTyp);
				}	
			}
		}
		catch (NamingException e){logger.error("Problem getting attribute: " + e);}
		catch (Exception e){logger.error("Encountered a really bad error:: " + e);}
		
		return alAdu;
	}
	
	public static ArrayList<AdUser> getAllAdUserForGroup(String search, DirContext ctx)
	{
		ArrayList<AdUser> alAdu = new ArrayList<AdUser>();
		try
		{
			logger.trace("Suche: "+search);
			Attributes att  = ctx.getAttributes(search);
			AdsRecordInspector.Typ adsTyp = AdsRecordInspector.getTyp(att);
			switch(adsTyp)
			{
				case USER:	AdUser adu = AdsRecordInspector.getUser(att);
							alAdu.add(adu);
							break;
				case GROUP:	AdGroup adg = AdsRecordInspector.getAdGroup(att);
							if(adg.getAlMember()!=null)
							{
								for(String s : adg.getAlMember())
								{
									alAdu.addAll(getAllAdUserForGroup(s, ctx));
								}
							}
							break;
				default:	logger.warn("Unknown Handling for: "+adsTyp);
			}
			
		}
		catch (NamingException e){logger.error("Problem getting attribute: " + e);}
		catch (Exception e){logger.error("Encountered a really bad error:: " + e, e);}
		
		return alAdu;
	}
	
	public static ArrayList<AdGroup> getAllGroups(String search, DirContext ctx)
	{
		ArrayList<AdGroup> alGroup = new ArrayList<AdGroup>();
		try
		{
			Attributes matchAttrs = new BasicAttributes (true);
			NamingEnumeration<SearchResult> answer = ctx.search(search, matchAttrs);
			while (answer.hasMore())
			{
				SearchResult sr = (SearchResult)answer.next ();
				AdsRecordInspector.Typ adsTyp = AdsRecordInspector.getTyp(sr);
				logger.trace("Objekt von Typ: "+adsTyp);
				
				switch(adsTyp)
				{
					case GROUP:	AdGroup adg = AdsRecordInspector.getAdGroup(sr);
								alGroup.add(adg);
								break;
					default:	logger.warn("Unknown Handling for: "+adsTyp);
				}	
			}
		}
		catch (NamingException e){logger.error("Problem getting attribute: " + e);}
		catch (Exception e){logger.error("Encountered a really bad error:: " + e);}
		return alGroup;
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String user = "DOMAIN\\NAME";
		String pwd = InputChoiceReader.readLine("Password for "+user+"? ");
		
		InitialDirContext idc = Connector.getDirContext("192.168.1.2",user,pwd);
		
		String search = "CN=-Openfuxml,OU=SVN,OU=Gruppen,OU=AHT Group AG,DC=aht-group,DC=local";
		ArrayList<AdUser> alAu = getAllAdUserForGroup(search, idc);
		for(AdUser adu : alAu)
		{
			logger.debug("\t"+adu);
		}
	}
}
