package net.sf.exlp.test.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.exlp.util.io.log.LoggerInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstApachePattern
{
	final static Logger logger = LoggerFactory.getLogger(TstApachePattern.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.path("resources/config");
			loggerInit.init();
			
		Pattern p = Pattern.compile("/index.php\\?id=([\\d]+)(.*)");
		Matcher m=p.matcher("/index.php?id=408");
		logger.debug("Matches: "+m.matches());
	}
}
