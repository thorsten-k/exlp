package net.sf.exlp.test.io;

import org.exlp.test.ExlpEjbBootstrap;
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
		ExlpEjbBootstrap.init();
		
		TstCommonsLog test = new TstCommonsLog();
		test.test();
	}
}
