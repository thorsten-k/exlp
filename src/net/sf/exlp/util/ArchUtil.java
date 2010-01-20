package net.sf.exlp.util;

import net.sf.exlp.io.LoggerInit;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

public class ArchUtil
{
	static Logger logger = Logger.getLogger(ArchUtil.class);
	
	private static String fSep = SystemUtils.FILE_SEPARATOR;
	
	public static String getDocDir()
	{
		String docDir = "archNotFound";
		if(SystemUtils.IS_OS_MAC){return SystemUtils.getUserHome().toString()+fSep+"Documents";}
		return docDir;
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		logger.debug(ArchUtil.getDocDir());
	}
}
