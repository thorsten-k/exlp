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

public class EximParser extends AbstractLogParser implements LogParser  
{
	static Log logger = LogFactory.getLog(EximParser.class);

	private HostConnectionParser hostConnectionParser;
	private TransmissionParser transmissionParser;
	
	public static String hBracket = "\\(("+PatternFactory.hostPattern+")\\)";
	public static String iBracket = "\\[("+PatternFactory.ipPattern+")\\]";
	public static String dHiBracket = "\\(\\[("+PatternFactory.ipPattern+")\\]\\)";
	public static String dIhBracket = "\\[\\(("+PatternFactory.ipPattern+")\\)\\]";
	
	public EximParser(LogEventHandler leh)
	{
		super(leh);
		hostConnectionParser = new HostConnectionParser(leh);childParser.add(hostConnectionParser);
		transmissionParser = new TransmissionParser(leh);childParser.add(transmissionParser);
		
		String prefix = PatternFactory.eximPrefix;
		
		pattern.add(Pattern.compile(prefix+PatternFactory.eximId+" (.*)"));
		
		pattern.add(Pattern.compile(prefix+"H="+hBracket+" "+iBracket+" (.*)"));
		pattern.add(Pattern.compile(prefix+"H=("+PatternFactory.hostPattern+") "+iBracket+" (.*)"));
		pattern.add(Pattern.compile(prefix+"H=("+PatternFactory.hostPattern+") "+hBracket+" "+iBracket+" (.*)"));
		pattern.add(Pattern.compile(prefix+"H=("+PatternFactory.hostPattern+") "+dHiBracket+" "+iBracket+" (.*)"));
		pattern.add(Pattern.compile(prefix+"H="+dHiBracket+" "+iBracket+" (.*)"));
		pattern.add(Pattern.compile(prefix+"H="+iBracket+" (.*)"));
		
		pattern.add(Pattern.compile(prefix+"unexpected disconnection while reading SMTP command from (.*)"));
		pattern.add(Pattern.compile(prefix+"no IP address found for host (.*)"));
		pattern.add(Pattern.compile(prefix+"no host name found for IP address (.*)"));
		pattern.add(Pattern.compile(prefix+"lowest numbered MX record points to (.*)"));
		pattern.add(Pattern.compile(prefix+"SMTP protocol synchronization error (.*)"));
		pattern.add(Pattern.compile(prefix+"SMTP connection from (.*)"));
		pattern.add(Pattern.compile(prefix+"SMTP command timeout on connection from (.*)"));
		pattern.add(Pattern.compile(prefix+"SMTP command timeout on TLS connection from (.*)"));
		pattern.add(Pattern.compile(prefix+"SMTP data timeout (.*)"));
		pattern.add(Pattern.compile(prefix+"SMTP call from (.*)"));
		pattern.add(Pattern.compile(prefix+"TLS error on connection from (.*)"));
		pattern.add(Pattern.compile(prefix+"TLS send error on connection from (.*)"));
		pattern.add(Pattern.compile(prefix+"TLS recv error on connection from (.*)"));
		pattern.add(Pattern.compile(prefix+"rejected EHLO from (.*)"));
		pattern.add(Pattern.compile(prefix+"rejected HELO from (.*)"));
		pattern.add(Pattern.compile(prefix+"Start queue run: pid=(.*)"));
		pattern.add(Pattern.compile(prefix+"End queue run: pid=(.*)"));
		pattern.add(Pattern.compile(prefix+"exim 4.63 daemon started: (.*)"));
		pattern.add(Pattern.compile(prefix+"CNAME loop for (.*)"));
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
				Date record = new Date();
				switch(i)
				{
					case 0: transmissionParser.setRecord(record);
							transmissionParser.setEximId(getEximId(7, m));
							transmissionParser.parseLine(m.group(10));
							break;
					case 1: hostConnectionParser.setHostName(m.group(7));
							hostConnectionParser.setIpAddress(m.group(8));
							hostConnectionParser.setRecord(record);
							hostConnectionParser.parseLine(m.group(9));
							break;
					case 2:	hostConnectionParser.setDnsName(m.group(7));
							hostConnectionParser.setIpAddress(m.group(8));
							hostConnectionParser.setRecord(record);
							hostConnectionParser.parseLine(m.group(9));
							break;
					case 3:	hostConnectionParser.setDnsName(m.group(7));
							hostConnectionParser.setHostName(m.group(8));
							hostConnectionParser.setIpAddress(m.group(9));
							hostConnectionParser.setRecord(record);
							hostConnectionParser.parseLine(m.group(10));
							break;
					case 4:	hostConnectionParser.setDnsName(m.group(7));
							hostConnectionParser.setHostName(m.group(8));
							hostConnectionParser.setIpAddress(m.group(9));
							hostConnectionParser.setRecord(record);
							hostConnectionParser.parseLine(m.group(10));
							break;
					case 5:	hostConnectionParser.setHostName(m.group(7));
							hostConnectionParser.setIpAddress(m.group(8));
							hostConnectionParser.setRecord(record);
							hostConnectionParser.parseLine(m.group(9));
							break;
					case 6:	hostConnectionParser.setIpAddress(m.group(7));
							hostConnectionParser.setRecord(record);
							hostConnectionParser.parseLine(m.group(8));
							break;
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
	
	private String getEximId(int start, Matcher m)
	{
		StringBuffer sb = new StringBuffer();
			sb.append(m.group(start));
			sb.append("-");
			sb.append(m.group(start+1));
			sb.append("-");
			sb.append(m.group(start+2));
		return sb.toString();
	}
	
	@Override
	public void debugMe(){super.debugMe(this.getClass().getSimpleName());}
}