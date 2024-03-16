package net.sf.exlp.test.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.exlp.test.ExlpEjbBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.interfaces.util.PatternLibrary;

public class TstMacAddressPattern
{
	final static Logger logger = LoggerFactory.getLogger(TstMacAddressPattern.class);
	
	public static void main(String args[])
	{
		ExlpEjbBootstrap.init();
			
		Pattern p = Pattern.compile(PatternLibrary.macPatter+"(.*)");
		Matcher m=p.matcher("00:AB:12:AC:3F:EE");
		logger.debug("Matches: "+m.matches());
	}
}
