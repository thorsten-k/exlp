package net.sf.exlp.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpMonitorTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpMonitorTestBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("exlp-monitor.test");
			loggerInit.init();
	}
}