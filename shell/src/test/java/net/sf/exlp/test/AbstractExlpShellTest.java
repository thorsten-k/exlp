package net.sf.exlp.test;

import java.io.File;

import org.exlp.util.io.log.LoggerInit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractExlpShellTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpShellTest.class);
	
	protected static File fTarget;
	
	@BeforeAll
	public static void initFile()
	{
		if(!LoggerInit.isLog4jInited()){initLogger();}
		String dirTarget = System.getProperty("targetDir");
		if(dirTarget==null){dirTarget="target";}
		setfTarget(new File(dirTarget));
		logger.debug("Using targeDir "+fTarget.getAbsolutePath());
	}
	protected static void setfTarget(File fTarget) {AbstractExlpShellTest.fTarget = fTarget;}
	
	@BeforeAll
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.path("config.exlp-shell.test");
			loggerInit.init();
		}
    }
	
	@Test public void dummy(){}
}