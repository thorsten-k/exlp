package net.sf.exlp.test.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.interfaces.util.PatternLibrary;
import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstMacAddressPattern
{
	final static Logger logger = LoggerFactory.getLogger(TstMacAddressPattern.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.path("resources/config");
			loggerInit.init();
			
		Pattern p = Pattern.compile(PatternLibrary.macPatter+"(.*)");
		Matcher m=p.matcher("00:AB:12:AC:3F:EE");
		logger.debug("Matches: "+m.matches());
	}
}
