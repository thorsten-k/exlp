package net.sf.exlp.shell.spawn.ping;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.interfaces.util.PatternLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(PingParser.class);
	
	Date jetzt;
	
	public PingParser(LogEventHandler leh)
	{
		super(leh);
		pattern.add(Pattern.compile("([0-9]*) bytes from ("+PatternLibrary.ipPattern+"): icmp_seq=([0-9]*) ttl=([0-9]*) time=([0-9\\.]*)(.*)"));
		
		//Windows
		pattern.add(Pattern.compile("Antwort von ("+PatternLibrary.ipPattern+"): Bytes=([0-9]*) Zeit=([0-9]*)ms TTL=([0-9]*)(.*)"));
		pattern.add(Pattern.compile("Reply from ("+PatternLibrary.ipPattern+"): bytes=([0-9]*) time=([0-9]*)ms TTL=([0-9]*)(.*)"));
	}

	public void parseLine(String line)
	{
		logger.trace(line);
		jetzt = new Date();
		boolean matched = false;
		for(int i=0;i<pattern.size();i++)
		{
			Matcher m=pattern.get(i).matcher(line);
			if(m.matches())
			{
				matched=true;
				switch(i)
				{
					case 0: event(m.group(1),m.group(2),m.group(5));break;
					case 1: event(m.group(2),m.group(1),m.group(3));break;
					case 2: event(m.group(2),m.group(1),m.group(3));break;
				}
				i=pattern.size();
			}
		}
		if(!matched){logger.warn("Unknown Pattern: "+line);}
	}

	public void parseItem(ArrayList<String> item)
	{
		for(String line : item)
		{
			parseLine(line);
		}
	}
	
	public void event(String anzBytes, String host, String time)
	{
		logger.debug("event");
		double dTime = Double.parseDouble(time);
		PingEvent event = new PingEvent(host,dTime);
		leh.handleEvent(event);
	}
}
