package net.sf.exlp.addon.shell.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.addon.shell.event.PingEvent;
import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.PatternFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PingParser extends AbstractLogParser implements LogParser  
{
	static Log logger = LogFactory.getLog(PingParser.class);
	
	private final static int maxChainPattern=2;
	Pattern myChainPattern[] = new Pattern[maxChainPattern];
	Date jetzt;
	
	public PingParser(LogEventHandler leh)
	{
		super(leh);
		int i=0;
		myChainPattern[i] = Pattern.compile("([0-9]*) bytes from ("+PatternFactory.ipPattern+"): icmp_seq=([0-9]*) ttl=([0-9]*) time=([0-9\\.]*)(.*)");i++;
		myChainPattern[i] = Pattern.compile("Antwort von ("+PatternFactory.ipPattern+"): Bytes=([0-9]*) Zeit=([0-9]*)ms TTL=([0-9]*)(.*)");i++;
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
					case 0: event(m.group(1),m.group(2),m.group(6));break;
					case 1: event(m.group(2),m.group(2),m.group(3));break;
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
		//int iAnzBytes = new Integer(anzBytes);
		double dTime = new Double(time);
		PingEvent event = new PingEvent(host,dTime);
		leh.handleEvent(event);
	}
}

