package net.sf.exlp.util;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

import org.apache.log4j.Logger;

public class Connector
{
	static Logger logger = Logger.getLogger(Connector.class);
	
	public static InitialContext getContext(String jndiHost) throws CommunicationException
	{
		InitialContext ctx=null;
		try
		{
			Properties p = new Properties();
			p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory"); 
		    p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces"); 
			p.put(Context.PROVIDER_URL, (jndiHost));
			ctx = new InitialContext(p);
		}
		catch (CommunicationException e){throw new CommunicationException("Connection to "+jndiHost+" failed");}
		catch (NamingException e)
		{
			logger.fatal("Connector to "+jndiHost+" failed with NamingException",e);//System.exit(-1);
		}
		
		return ctx;
	}

	public static InitialDirContext getDirContext(String host, String user, String pwd)
	{
		InitialDirContext idc=null;
		
		Hashtable<String,String> ht = new Hashtable<String,String>(11);
			ht.put ("java.naming.ldap.version", "3");
			ht.put (Context.REFERRAL, "follow");
			ht.put (Context.SECURITY_AUTHENTICATION, "simple");
			ht.put (Context.SECURITY_PRINCIPAL, user);
			ht.put (Context.SECURITY_CREDENTIALS, pwd);
			ht.put (Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
			ht.put (Context.PROVIDER_URL, "ldap://"+host+":389/"); //replace activeDirectoryServerName.domainName
		try {idc = new InitialDirContext(ht);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idc;
	}
}
