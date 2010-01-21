package net.sf.exlp.test.io;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestCommonsLog
{
	private Log log = LogFactory.getLog(TestCommonsLog.class);
	
	public TestCommonsLog()
	{
		
	}
	
	public void test()
	{
		log.warn("Test");
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestCommonsLog test = new TestCommonsLog();
		test.test();
	}
}
