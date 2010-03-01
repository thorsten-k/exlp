package net.sf.exlp.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExlpContextFactory
{
	static Log logger = LogFactory.getLog(ExlpContextFactory.class);
	
	public static InitialContext getJbossContext(String jndiHost) throws NamingException
	{
		Hashtable<String,String> environment = new Hashtable<String,String>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        environment.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        environment.put(Context.PROVIDER_URL, "jnp://"+jndiHost);
        logger.info("Creating InitialContext to "+jndiHost);
        return new InitialContext(environment);
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
			ht.put (Context.PROVIDER_URL, "ldap://"+host+":389/");
		try {idc = new InitialDirContext(ht);}
		catch (NamingException e) {logger.error(e);}
		return idc;
	}
}