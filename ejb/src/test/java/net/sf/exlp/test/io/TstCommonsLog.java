package net.sf.exlp.test.io;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstCommonsLog
{
	final static Logger logger = LoggerFactory.getLogger(TstCommonsLog.class);
	
	public TstCommonsLog()
	{
		
	}
	
	public void test()
	{
		logger.warn("Test");
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.path("resources/config");
			loggerInit.init();
		
		TstCommonsLog test = new TstCommonsLog();
		test.test();
	}
}
