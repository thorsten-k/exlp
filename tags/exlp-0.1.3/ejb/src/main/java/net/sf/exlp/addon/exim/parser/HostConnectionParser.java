package net.sf.exlp.addon.exim.parser;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.PatternFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HostConnectionParser extends AbstractLogParser implements LogParser  
{
	static Log logger = LogFactory.getLog(HostConnectionParser.class);
	
	private RejectParser rejectParser;
	
	private Date record;
	private ExlpHost host;
		
	public HostConnectionParser(LogEventHandler leh)
	{
		super(leh);
		rejectParser = new RejectParser(leh);childParser.add(rejectParser);
		clear();
		
		pattern.add(Pattern.compile("F=<("+PatternFactory.email+")> (.*)"));		
		pattern.add(Pattern.compile("U="+PatternFactory.hostPattern+" F=<("+PatternFactory.email+")> (.*)"));
		pattern.add(Pattern.compile("U=("+PatternFactory.hostPattern+") F=<> (.*)"));
		
		pattern.add(Pattern.compile("sender verify fail for <("+PatternFactory.email+")>: (.*)"));
		pattern.add(Pattern.compile("sender verify defer for <("+PatternFactory.email+")>: (.*)"));
	}

	public void parseLine(String line)
	{
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<pattern.size();i++)
		{
			Matcher m=pattern.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: reject(m.group(1),m.group(2));break;
					case 1: reject(m.group(1),m.group(2));break;
					case 2: reject(m.group(1),m.group(2));break;
					case 3: senderFail(m.group(1),m.group(2));break;
					case 4: senderDefer(m.group(1),m.group(2));break;
					default: unknownHandling++;break;
				}
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	private void clear()
	{
		record = null;
		host = new ExlpHost();
	}
	
	private void reject(String emailFrom, String line)
	{
		rejectParser.setRecord(record);
		rejectParser.setHost(host);
		rejectParser.setEmailFrom(emailFrom);
		rejectParser.parseLine(line);
	}
	
	private void senderFail(String emailFrom, String line)
	{
		unknownHandling++;
	}
	
	private void senderDefer(String emailFrom, String line)
	{
		unknownHandling++;
	}
	
	@Override
	public void debugMe(){super.debugMe(this.getClass().getSimpleName());}
	
	public void setRecord(Date record) {this.record = record;}
	
	public void setHost(ExlpHost host) {this.host = host;}
}