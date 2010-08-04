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

public class TransmissionParser extends AbstractLogParser implements LogParser  
{
	static Log logger = LogFactory.getLog(TransmissionParser.class);

	static List<Pattern> lPattern = new ArrayList<Pattern>();
	
	public static String hBracket = "\\("+PatternFactory.hostPattern+"\\)";
	public static String iBracket = "\\[("+PatternFactory.ipPattern+")\\]";
	public static String dHiBracket = "\\(\\[("+PatternFactory.ipPattern+")\\]\\)";
	public static String dIhBracket = "\\[\\(("+PatternFactory.ipPattern+")\\)\\]";
	
	private Date record;
	private String eximId;
	
	public TransmissionParser(LogEventHandler leh)
	{
		super(leh);
		String prefix = PatternFactory.eximPrefix;
		
		lPattern.add(Pattern.compile(prefix+PatternFactory.eximId+" (.*)"));
		
		lPattern.add(Pattern.compile(prefix+"H="+hBracket+" "+iBracket+" (.*)"));
		lPattern.add(Pattern.compile(prefix+"H="+PatternFactory.hostPattern+" "+iBracket+" (.*)"));
		lPattern.add(Pattern.compile(prefix+"H="+PatternFactory.hostPattern+" "+hBracket+" "+iBracket+" (.*)"));
		lPattern.add(Pattern.compile(prefix+"H="+PatternFactory.hostPattern+" "+dHiBracket+" "+iBracket+" (.*)"));
		lPattern.add(Pattern.compile(prefix+"H="+dHiBracket+" "+iBracket+" (.*)"));
		lPattern.add(Pattern.compile(prefix+"H="+hBracket+" (.*)"));
		lPattern.add(Pattern.compile(prefix+"H="+iBracket+" "+hBracket+" (.*)"));
		lPattern.add(Pattern.compile(prefix+"H="+iBracket+" (.*)"));
		
		
		lPattern.add(Pattern.compile(prefix+"unexpected disconnection while reading SMTP command from (.*)"));
		lPattern.add(Pattern.compile(prefix+"no IP address found for host (.*)"));
		lPattern.add(Pattern.compile(prefix+"no host name found for IP address (.*)"));
		lPattern.add(Pattern.compile(prefix+"lowest numbered MX record points to (.*)"));
		lPattern.add(Pattern.compile(prefix+"SMTP protocol synchronization error (.*)"));
		lPattern.add(Pattern.compile(prefix+"SMTP connection from (.*)"));
		lPattern.add(Pattern.compile(prefix+"SMTP command timeout on connection from (.*)"));
		lPattern.add(Pattern.compile(prefix+"SMTP command timeout on TLS connection from (.*)"));
		lPattern.add(Pattern.compile(prefix+"SMTP data timeout (.*)"));
		lPattern.add(Pattern.compile(prefix+"SMTP call from (.*)"));
		lPattern.add(Pattern.compile(prefix+"TLS error on connection from (.*)"));
		lPattern.add(Pattern.compile(prefix+"TLS send error on connection from (.*)"));
		lPattern.add(Pattern.compile(prefix+"TLS recv error on connection from (.*)"));
		lPattern.add(Pattern.compile(prefix+"rejected EHLO from (.*)"));
		lPattern.add(Pattern.compile(prefix+"rejected HELO from (.*)"));
		lPattern.add(Pattern.compile(prefix+"Start queue run: pid=(.*)"));
		lPattern.add(Pattern.compile(prefix+"End queue run: pid=(.*)"));
		lPattern.add(Pattern.compile(prefix+"exim 4.63 daemon started: (.*)"));
		lPattern.add(Pattern.compile(prefix+"CNAME loop for (.*)"));
		
	}
	
	

	public void parseLine(String line)
	{
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<lPattern.size();i++)
		{
			Matcher m=lPattern.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					
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
	
	public void setRecord(Date record) {this.record = record;}
	public void setEximId(String eximId) {this.eximId = eximId;}

}