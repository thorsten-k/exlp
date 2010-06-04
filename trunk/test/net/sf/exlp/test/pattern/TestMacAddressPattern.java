package net.sf.exlp.test.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.parser.PatternFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestMacAddressPattern
{
	static Log logger = LogFactory.getLog(TestMacAddressPattern.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		Pattern p = Pattern.compile(PatternFactory.macPatter+"(.*)");
		Matcher m=p.matcher("00:AB:12:AC:3F:EE");
		logger.debug(m.matches());
	}
}
