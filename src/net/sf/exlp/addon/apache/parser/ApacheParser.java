package net.sf.exlp.addon.apache.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.PatternFactory;

import org.apache.log4j.Logger;

public class ApacheParser extends AbstractLogParser implements LogParser  
{
	private static Logger logger = Logger.getLogger(ApacheParser.class);

	private final static int maxP=1;
	Pattern p[] = new Pattern[maxP];
	
	public ApacheParser(LogEventHandler ehi)
	{
		super(ehi);
		int i=0;
		StringBuffer sb = new StringBuffer();
			sb.append(PatternFactory.ipPattern);
			sb.append(" - ");
			sb.append("[a-z-]+");
			sb.append(" \\[(\\d+)/(\\w+)/(\\d+):");
			sb.append("(.*)");
		p[i] = Pattern.compile(sb.toString());i++;
	}

	public void parseLine(String line)
	{
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<maxP;i++)
		{
			Matcher m=p[i].matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: event(m);break;		
				}
				i=maxP;
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			logger.debug(line);
			logger.debug("********************");
			unknownLines++;
		}
	}
	
	public void event(Matcher m)
	{
		logger.debug(m.group(0));
	}
}

