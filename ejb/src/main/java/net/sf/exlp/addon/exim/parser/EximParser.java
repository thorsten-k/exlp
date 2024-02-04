package net.sf.exlp.addon.exim.parser;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.exlp.util.system.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.interfaces.util.PatternLibrary;

public class EximParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(EximParser.class);

	private HostConnectionParser hostConnectionParser;
	private TransmissionParser transmissionParser;
	
	public static String hBracket = "\\(("+PatternLibrary.hostPattern+")\\)";
	public static String iBracket = "\\[("+PatternLibrary.ipPattern+")\\]";
	public static String dHiBracket = "\\(\\[("+PatternLibrary.ipPattern+")\\]\\)";
	public static String dIhBracket = "\\[\\(("+PatternLibrary.ipPattern+")\\)\\]";
	
	public EximParser(LogEventHandler leh)
	{
		super(leh);
		hostConnectionParser = new HostConnectionParser(leh);childParser.add(hostConnectionParser);
		transmissionParser = new TransmissionParser(leh);childParser.add(transmissionParser);
		
		String prefix = PatternLibrary.eximPrefix;
		
		pattern.add(Pattern.compile(prefix+PatternLibrary.eximId+" (.*)"));
		
		pattern.add(Pattern.compile(prefix+"H="+hBracket+" "+iBracket+" (.*)"));
		pattern.add(Pattern.compile(prefix+"H=("+PatternLibrary.hostPattern+") "+iBracket+" (.*)"));
		pattern.add(Pattern.compile(prefix+"H=("+PatternLibrary.hostPattern+") "+hBracket+" "+iBracket+" (.*)"));
		pattern.add(Pattern.compile(prefix+"H=("+PatternLibrary.hostPattern+") "+dHiBracket+" "+iBracket+" (.*)"));
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
				LocalDateTime record = DateUtil.ldtOf(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6));;
				ExlpHost host;
				switch(i)
				{
					case 0: transmissionParser.setRecord(DateUtil.toDate(record));
							transmissionParser.setEximId(getEximId(7, m));
							transmissionParser.parseLine(m.group(10));
							break;
					case 1: host = new ExlpHost();
							host.setName(m.group(7));
							host.setIp(m.group(8));
							hostConnectionParser.setHost(host);
							hostConnectionParser.setRecord(DateUtil.toDate(record));
							hostConnectionParser.parseLine(m.group(9));
							break;
					case 2:	host = new ExlpHost();
							host.setDns(m.group(7));
							host.setIp(m.group(8));
							hostConnectionParser.setHost(host);
							hostConnectionParser.setRecord(DateUtil.toDate(record));
							hostConnectionParser.parseLine(m.group(9));
							break;
					case 3:	host = new ExlpHost();
							host.setDns(m.group(7));
							host.setName(m.group(8));
							host.setIp(m.group(9));
							hostConnectionParser.setHost(host);
							hostConnectionParser.setRecord(DateUtil.toDate(record));
							hostConnectionParser.parseLine(m.group(10));
							break;
					case 4:	host = new ExlpHost();
							host.setDns(m.group(7));
							host.setName(m.group(8));
							host.setIp(m.group(9));
							hostConnectionParser.setHost(host);
							hostConnectionParser.setRecord(DateUtil.toDate(record));
							hostConnectionParser.parseLine(m.group(10));
							break;
					case 5:	host = new ExlpHost();
							host.setName(m.group(7));
							host.setIp(m.group(8));
							hostConnectionParser.setHost(host);
							hostConnectionParser.setRecord(DateUtil.toDate(record));
							hostConnectionParser.parseLine(m.group(9));
							break;
					case 6:	host = new ExlpHost();
							host.setIp(m.group(7));
							hostConnectionParser.setHost(host);
							hostConnectionParser.setRecord(DateUtil.toDate(record));
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
	
	@Override public void debugMe(){super.debugMe(this.getClass().getSimpleName());}
}