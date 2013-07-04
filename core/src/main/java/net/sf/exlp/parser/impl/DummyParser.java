package net.sf.exlp.parser.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.parser.AbstractLogParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(DummyParser.class);

	private final static int maxP=1;
	Pattern p[] = new Pattern[maxP];
	
	public DummyParser(LogEventHandler leh)
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
	public void parseItem(List<String> item)
	{
		logger.debug("Item received with "+item.size()+" entries");
		for(String s : item)
		{
			logger.debug(s);
		}
	}
	
	public void event(Matcher m)
	{
		logger.debug(m.group(0));
	}
}