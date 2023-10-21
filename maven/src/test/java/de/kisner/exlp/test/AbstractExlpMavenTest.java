package de.kisner.exlp.test;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.LoggerInit;

public class AbstractExlpMavenTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpMavenTest.class);
	
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
	protected static void setfTarget(File fTarget) {AbstractExlpMavenTest.fTarget = fTarget;}
	
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