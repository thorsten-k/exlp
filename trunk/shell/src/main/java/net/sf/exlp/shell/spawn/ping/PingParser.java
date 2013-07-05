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
	
	private final static int maxChainPattern=2;
	Pattern myChainPattern[] = new Pattern[maxChainPattern];
	Date jetzt;
	
	public PingParser(LogEventHandler leh)
	{
		super(leh);
		int i=0;
		myChainPattern[i] = Pattern.compile("([0-9]*) bytes from ("+PatternLibrary.ipPattern+"): icmp_seq=([0-9]*) ttl=([0-9]*) time=([0-9\\.]*)(.*)");i++;
		myChainPattern[i] = Pattern.compile("Antwort von ("+PatternLibrary.ipPattern+"): Bytes=([0-9]*) Zeit=([0-9]*)ms TTL=([0-9]*)(.*)");i++;
	}

	public void parseLine(String line)
	{
		logger.debug(line);
		jetzt = new Date();
		for(int i=0;i<maxChainPattern;i++)
		{
			Matcher m=myChainPattern[i].matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: event(m.group(1),m.group(2),m.group(5));break;
					case 1: event(m.group(2),m.group(1),m.group(3));break;
				}
				i=maxChainPattern;
			}
		}
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
		double dTime = new Double(time);
		PingEvent event = new PingEvent(host,dTime);
		leh.handleEvent(event);
	}
}
