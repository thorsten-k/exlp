package net.sf.exlp.io.config;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigFileChecker
{
	static Log logger = LogFactory.getLog(ConfigFileChecker.class);
	
	private boolean info,exit;
	
	public ConfigFileChecker(boolean info, boolean exit)
	{
		this.info=info;
		this.exit=exit;
	}
	
	public void checkDir(String id, String dirName)
	{
		File ftpDir = new File(dirName);
		if(!ftpDir.exists() || !ftpDir.isDirectory())
		{
			logger.fatal("Directory "+dirName+" does not exist!");
			System.exit(-1);
		}
		logger.info(id+": "+dirName);
	}
	
}
