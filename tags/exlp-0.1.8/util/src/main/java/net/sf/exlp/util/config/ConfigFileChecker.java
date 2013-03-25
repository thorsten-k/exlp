package net.sf.exlp.util.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class ConfigFileChecker
{
	final static Marker fatal = MarkerFactory.getMarker("FATAL");
	final static Logger logger = LoggerFactory.getLogger(ConfigFileChecker.class);
	
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
			logger.error(fatal,"Directory "+dirName+" does not exist!");
			System.exit(-1);
		}
		logger.info(id+": "+dirName);
	}
	
}
