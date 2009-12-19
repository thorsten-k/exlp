package net.sf.exlp.addon.ads;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import net.sf.exlp.io.LoggerInit;

import org.apache.log4j.Logger;

public class AdsAuth
{
	static Logger logger = Logger.getLogger(AdsAuth.class);
	
	private String domain;
	private String ldapHost;
	
	public AdsAuth(String domain, String ldapHost)
	{
		this.domain = domain;
		this.ldapHost = ldapHost;
	}
	
	public boolean authenticate(String user, String pass)
	{
		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, user + "@" + domain);
		env.put(Context.SECURITY_CREDENTIALS, pass);
		env.put(Context.PROVIDER_URL, ldapHost);

		try
		{
			new InitialLdapContext(env, null);
			return true;
		}
		catch (AuthenticationException e){logger.trace(e);}
		catch (NamingException e){logger.error(e);}
		return false;
	}
}
