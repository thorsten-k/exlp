package net.sf.exlp.util;

import net.sf.exlp.io.LoggerInit;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ArchUtil
{
	static Log logger = LogFactory.getLog(ArchUtil.class);
	
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
