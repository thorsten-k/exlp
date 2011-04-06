package net.sf.exlp.io.arch;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EnvironmentParameter
{
	static Log logger = LogFactory.getLog(EnvironmentParameter.class);
	
	private Hashtable<String,String> htEnvPar;

	public EnvironmentParameter()
	{
		htEnvPar = new Hashtable<String,String>();
		htEnvPar.putAll(System.getenv());
	}
	
	public String[] get()
	{
		String[] sEP = new String[htEnvPar.size()];
		int index=0;
		for(String key : htEnvPar.keySet())
		{
			sEP[index]=key+"="+htEnvPar.get(key);
			index++;
		}
		return sEP;
	}
	
	public static void debug(Map<String,String> envMap)
	{
		Hashtable<String,String> htEnv = new Hashtable<String,String>();
		htEnv.putAll(System.getenv());
		for(String key : htEnv.keySet())
		{
			logger.debug(key+"="+htEnv.get(key));
		}
	}
	
	public void debug()
	{
		logger.debug("Environment has "+htEnvPar.size()+" elements:");
		for( String s : get())
		{
			logger.debug("\t"+s);
		}
	}
	
	public String get(Object key) {return htEnvPar.get(key);}
	public String put(String key, String value) {return htEnvPar.put(key, value);}
}
