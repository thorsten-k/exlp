package net.sf.exlp.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpShellTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(ExlpShellTestBootstrap.class);
	
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("exlp-shell.test");
			loggerInit.init();
	}
}