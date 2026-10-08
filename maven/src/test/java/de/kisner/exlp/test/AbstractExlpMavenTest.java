package de.kisner.exlp.test;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractExlpMavenTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpMavenTest.class);
	
	protected static File fTarget;
	
	private static boolean log4jInited = false;
	public static boolean isLog4jInited() {return log4jInited;}
	
	@BeforeAll
	public static void initFile()
	{
		if(!isLog4jInited()){initLogger();}
		String dirTarget = System.getProperty("targetDir");
		if(dirTarget==null){dirTarget="target";}
		setfTarget(new File(dirTarget));
		logger.debug("Using targeDir "+fTarget.getAbsolutePath());
	}
	protected static void setfTarget(File fTarget) {AbstractExlpMavenTest.fTarget = fTarget;}
	
	@BeforeAll
    public static void initLogger()
	{
		if(!isLog4jInited())
		{
			ExlpMavenTestBootstrap.init();
			log4jInited = true;
		}
    }
	
	@Test public void dummy(){}
}