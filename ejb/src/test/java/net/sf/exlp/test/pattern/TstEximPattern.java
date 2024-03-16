package net.sf.exlp.test.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.exlp.test.ExlpEjbBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.interfaces.util.PatternLibrary;

public class TstEximPattern
{
	final static Logger logger = LoggerFactory.getLogger(TstEximPattern.class);
	
	public static void main(String args[])
	{
		ExlpEjbBootstrap.init();
			
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