package org.exlp.test;

import java.io.File;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
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
		if(!LoggerBootstrap.isLog4jInited()){initLogger();}
		String dirTarget = System.getProperty("targetDir");
		if(dirTarget==null){dirTarget="target";}
		setfTarget(new File(dirTarget));
		logger.debug("Using targeDir "+fTarget.getAbsolutePath());
	}
	protected static void setfTarget(File fTarget) {AbstractExlpShellTest.fTarget = fTarget;}
	
	@BeforeAll
    public static void initLogger()
	{
		if(!LoggerBootstrap.isLog4jInited())
		{
			LoggerBootstrap.instance("shell.log4j2.xml").path("exlp/system/io/log").init();
		}
    }
	
	@Test public void dummy(){}
}