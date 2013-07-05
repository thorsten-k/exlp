package net.sf.exlp.test.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.interfaces.util.PatternLibrary;
import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstEximPattern
{
	final static Logger logger = LoggerFactory.getLogger(TstEximPattern.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
//		String hBracket = EximParser.hBracket;
//		String iBracket = EximParser.iBracket;
			
		Pattern p = Pattern.compile(PatternLibrary.eximPrefix+"H=[\\(]?[\\[]?("+PatternLibrary.hostPattern+")[\\]]?[\\)]? \\(("+PatternLibrary.hostPattern+")\\)(.*)");
		Matcher m=p.matcher("2010-08-03 05:33:10 H=c-76-119-39-200.hsd1.ma.comcast.net (kyweyk) [76.119.39.200] F=<apiersanti@ig.com.br> temporarily rejected RCPT <vanWissen@aht-group.com>: GreyListed: please try again later");
		logger.debug("matches: "+m.matches());
		if(m.matches())
		{
			for(int i=0;i<m.groupCount();i++)
			{
				logger.debug(m.group(i));
			}
		}
	}
}