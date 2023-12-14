package org.exlp.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.exlp.util.jx.ExlpNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpBootstrap.class);
	
	public enum System{exlp}
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.path("exlp/test/config");
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());
	}
}