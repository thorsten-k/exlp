package de.kisner.exlp.test;

import java.io.File;

import net.sf.exlp.util.io.LoggerInit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractExlpMavenTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpMavenTest.class);
	
	protected static File fTarget;
	
	@BeforeClass
	public static void initFile()
	{
		if(!LoggerInit.isLog4jInited()){initLogger();}
		String dirTarget = System.getProperty("targetDir");
		if(dirTarget==null){dirTarget="target";}
		setfTarget(new File(dirTarget));
		logger.debug("Using targeDir "+fTarget.getAbsolutePath());
	}
	protected static void setfTarget(File fTarget) {AbstractExlpMavenTest.fTarget = fTarget;}
	
	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("config.exlp-shell.test");
			loggerInit.init();
		}
    }
	
	@Test public void dummy(){}
}