package net.sf.exlp.addon.exim.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.PatternFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RejectParser extends AbstractLogParser implements LogParser  
{
	static Log logger = LogFactory.getLog(RejectParser.class);
	
	private Date record;
	private String hostName,dnsName,ipAddress,emailFrom;
	

	public static String response = "rejected RCPT <"+PatternFactory.email+">: response to \"RCPT TO:<"+PatternFactory.email+">\" from 192.168.1.4 \\[192.168.1.4\\] was: 550 5.1.1 User unknown";
	
	public RejectParser(LogEventHandler leh)
	{
		super(leh);
		
		pattern.add(Pattern.compile("temporarily rejected RCPT <"+PatternFactory.email+">: GreyListed: please try again later(.*)"));
		pattern.add(Pattern.compile("temporarily rejected RCPT <"+PatternFactory.email+">: Could not complete sender verify(.*)"));
		pattern.add(Pattern.compile("rejected RCPT <"+PatternFactory.email+">: Previous \\(cached\\) callout verification failure(.*)"));
		pattern.add(Pattern.compile("rejected RCPT <"+PatternFactory.email+">: Sender verify failed(.*)"));
		pattern.add(Pattern.compile("rejected RCPT "+PatternFactory.email+": Sender verify failed(.*)"));
		pattern.add(Pattern.compile("rejected RCPT <"+PatternFactory.email+">: relay not permitted(.*)"));
		pattern.add(Pattern.compile("rejected RCPT <"+PatternFactory.email+">: response to \"RCPT TO:<"+PatternFactory.email+">\" from 192.168.1.4 \\[192.168.1.4\\] was: 550 5.1.1 User unknown(.*)"));
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
					case 0: grey();break;
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
	
	private void grey()
	{
		unknownHandling++;
	}
	
	@Override
	public void debugMe(){super.debugMe(this.getClass().getSimpleName());}
	
	public void setEmailFrom(String emailFrom) {this.emailFrom = emailFrom;}
	public void setRecord(Date record) {this.record = record;}
	public void setHostName(String hostName) {this.hostName = hostName;}
	public void setDnsName(String dnsName) {this.dnsName = dnsName;}
	public void setIpAddress(String ipAddress) {this.ipAddress = ipAddress;}

}