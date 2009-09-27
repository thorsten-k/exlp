package net.sf.exlp.parser.impl;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.LogParser;

import org.apache.log4j.Logger;

public class TestParser extends AbstractLogParser implements LogParser  
{
	private static Logger logger = Logger.getLogger(TestParser.class);

	private final static int maxP=1;
	Pattern p[] = new Pattern[maxP];
	
	public TestParser(LogEventHandler leh)
	{
		super(leh);
		int i=0;
		p[i] = Pattern.compile("(.*)");i++;
		logger.debug(p[0].toString());
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
			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	@Override
	public void parseItem(ArrayList<String> item)
	{
		logger.debug("Item received with "+item.size()+" entries");
	}
	
	public void event(Matcher m)
	{
		logger.debug(m.group(0));
	}
}