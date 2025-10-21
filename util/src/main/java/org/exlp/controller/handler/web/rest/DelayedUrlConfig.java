package org.exlp.controller.handler.web.rest;

import org.exlp.interfaces.system.property.ConfigKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelayedUrlConfig
{
	final static Logger logger = LoggerFactory.getLogger(DelayedUrlConfig.class);
	
	public static String getUrl(org.apache.commons.configuration.Configuration config, String key, boolean delay)
	{
		if(delay) {return DelayedUrlConfig.resolve(config,key);}
		else {return DelayedUrlConfig.getUrlNoDelay(config,key);}
	}
	
	public static String getUrlNoDelay(org.apache.commons.configuration.Configuration config, String key)
	{
		String url = config.getString(key);
		
		StringBuffer sb = new StringBuffer();
		sb.append("REST connection to ");
		sb.append(url);
		sb.append(" (").append(key).append(")");
		logger.info(sb.toString());
		return url;
	}
	public static String getUrlNoDelay(org.exlp.interfaces.system.property.Configuration config, String key)
	{
		String url = config.getString(key);
		
		StringBuffer sb = new StringBuffer();
		sb.append("REST connection to ");
		sb.append(url);
		sb.append(" (").append(key).append(")");
		logger.info(sb.toString());
		return url;
	}
	
	public static String resolve(org.apache.commons.configuration.Configuration config) {return resolve(config,ConfigKey.netRestUrlLocal);}
	public static String resolve(org.apache.commons.configuration.Configuration config, String key)
	{
		String url = config.getString(key);
		
		StringBuffer sb = new StringBuffer();
		sb.append("REST connection to ");
		sb.append(url);
		sb.append(" (").append(key).append(")");
		
		boolean delay = false;
		if(!url.contains("localhost"))
		{
			sb.append("  .... delaying 5s");
			delay=true;
		}
		
		if(delay)
		{
			logger.warn(sb.toString());
			try {Thread.sleep(5000);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
		else
		{
			logger.info(sb.toString());
		}
		
		return url;
	}
	
	public static String resolve(org.exlp.interfaces.system.property.Configuration config, String key)
	{
		return DelayedUrlConfig.resolve(config, key, 5000);
	}
	public static String resolve(org.exlp.interfaces.system.property.Configuration config, String key, int delayMs)
	{
		String url = config.getString(key);
		
		StringBuffer sb = new StringBuffer();
		sb.append("REST connection to ");
		sb.append(url);
		sb.append(" (").append(key).append(")");
		
		boolean delay = false;
		if(delayMs>0 && !url.contains("localhost"))
		{
			sb.append("  .... delaying 5s");
			delay=true;
		}
		
		if(delay)
		{
			logger.warn(sb.toString());
			try {Thread.sleep(5000);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
		else
		{
			logger.info(sb.toString());
		}
		
		return url;
	}
}