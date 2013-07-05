package net.sf.exlp.addon.exim.parser;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.interfaces.util.PatternLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class TransmissionParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(TransmissionParser.class);
	
	private Date record;
	private String eximId;
	
	public TransmissionParser(LogEventHandler leh)
	{
		super(leh);
		
		pattern.add(Pattern.compile("Message is frozen(.*)"));
		pattern.add(Pattern.compile("Frozen(.*)"));
		pattern.add(Pattern.compile("Completed(.*)"));
		pattern.add(Pattern.compile("<= (.*)"));
		pattern.add(Pattern.compile("=> (.*)"));
		pattern.add(Pattern.compile("-> (.*)"));
		pattern.add(Pattern.compile("== (.*)"));
		pattern.add(Pattern.compile("\\*\\* (.*)"));
		pattern.add(Pattern.compile("Unfrozen by errmsg timer(.*)"));
		pattern.add(Pattern.compile("cancelled by timeout_frozen_after(.*)"));
		pattern.add(Pattern.compile("Frozen \\(delivery error message\\)(.*)"));
		pattern.add(Pattern.compile("Spool file is locked \\(another process is handling this message\\)(.*)"));
		pattern.add(Pattern.compile("lowest numbered MX record points to local host: "+PatternLibrary.hostPattern+"(.*)"));
		
		pattern.add(Pattern.compile(PatternLibrary.hostPattern+" "+EximParser.iBracket+" Connection timed out(.*)"));
		pattern.add(Pattern.compile(PatternLibrary.hostPattern+" "+EximParser.iBracket+" Connection refused(.*)"));
		pattern.add(Pattern.compile(PatternLibrary.hostPattern+" "+EximParser.iBracket+" No route to host(.*)"));
		pattern.add(Pattern.compile("Remote host "+PatternLibrary.hostPattern+" "+EximParser.iBracket+" after initial connection: Connection timed out(.*)"));
		pattern.add(Pattern.compile("Remote host "+PatternLibrary.hostPattern+" "+EximParser.iBracket+" closed connection in response to initial connection(.*)"));
		pattern.add(Pattern.compile("SMTP timeout while connected to "+PatternLibrary.hostPattern+" "+EximParser.iBracket+" closed connection in response to initial connection(.*)"));
		pattern.add(Pattern.compile("SMTP timeout while connected to "+PatternLibrary.hostPattern+" "+EximParser.iBracket+" after initial connection: Connection timed out(.*)"));
		pattern.add(Pattern.compile(PatternLibrary.email+": error ignored(.*)"));
		
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
					case 0: frozen();break;
					case 1: frozen();break;
					default: unknownHandling++;break;
				}
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
//			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	private void frozen()
	{
		unknownHandling++;
	}
	
	private void completed()
	{
		unknownHandling++;
	}
	
	@Override
	public void debugMe(){super.debugMe(this.getClass().getSimpleName());}
	
	public void setRecord(Date record) {this.record = record;}
	public void setEximId(String eximId) {this.eximId = eximId;}

}