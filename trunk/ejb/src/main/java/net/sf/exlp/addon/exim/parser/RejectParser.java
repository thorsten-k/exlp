package net.sf.exlp.addon.exim.parser;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.addon.exim.data.ejb.ExlpEmail;
import net.sf.exlp.addon.exim.event.EximGreylistEvent;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.PatternLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RejectParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(RejectParser.class);
	
	private Date record;
	private ExlpHost host;
	
	private String emailFrom;
		
	public RejectParser(LogEventHandler leh)
	{
		super(leh);
		
		pattern.add(Pattern.compile("temporarily rejected RCPT <("+PatternLibrary.email+")>: GreyListed: please try again later(.*)"));
		pattern.add(Pattern.compile("temporarily rejected RCPT <"+PatternLibrary.email+">: Could not complete sender verify(.*)"));
		pattern.add(Pattern.compile("rejected RCPT <"+PatternLibrary.email+">: Previous \\(cached\\) callout verification failure(.*)"));
		pattern.add(Pattern.compile("rejected RCPT <"+PatternLibrary.email+">: Sender verify failed(.*)"));
		pattern.add(Pattern.compile("rejected RCPT "+PatternLibrary.email+": Sender verify failed(.*)"));
		pattern.add(Pattern.compile("rejected RCPT <"+PatternLibrary.email+">: relay not permitted(.*)"));
		pattern.add(Pattern.compile("rejected RCPT <"+PatternLibrary.email+">: response to \"RCPT TO:<"+PatternLibrary.email+">\" from 192.168.1.4 \\[192.168.1.4\\] was: 550 5.1.1 User unknown(.*)"));
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
					case 0: grey(m.group(1));break;
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
		clear();
	}
	
	private void clear()
	{
		host = new ExlpHost();
		record=null;
	}
	
	private void grey(String to)
	{
		ExlpEmail from = new ExlpEmail();from.setEmail(emailFrom);
		ExlpEmail rcpt = new ExlpEmail();rcpt.setEmail(to); 
		
		LogEvent e = new EximGreylistEvent(from,rcpt,host,record);
		leh.handleEvent(e);
	}
	
	@Override
	public void debugMe(){super.debugMe(this.getClass().getSimpleName());}
	
	public void setEmailFrom(String emailFrom) {this.emailFrom = emailFrom;}
	public void setRecord(Date record) {this.record = record;}
	
	public void setHost(ExlpHost host) {this.host = host;}
//	public void setHostName(String hostName) {this.hostName = hostName;}
//	public void setDnsName(String dnsName) {this.dnsName = dnsName;}
//	public void setIpAddress(String ipAddress) {this.ipAddress = ipAddress;}

}