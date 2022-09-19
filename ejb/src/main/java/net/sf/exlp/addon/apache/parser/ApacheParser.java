package net.sf.exlp.addon.apache.parser;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.addon.apache.ejb.ExlpApache;
import net.sf.exlp.addon.apache.event.ApacheEvent;
import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.interfaces.util.PatternLibrary;
import net.sf.exlp.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApacheParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(ApacheParser.class);

	private final static int maxP=1;
	Pattern p[] = new Pattern[maxP];
	
	public ApacheParser(LogEventHandler leh)
	{
		super(leh);
		int i=0;
		StringBuffer sb = new StringBuffer();
			sb.append(PatternLibrary.ipPattern);
			sb.append(" - ");
			sb.append("[\\w-\"]+");
			sb.append(" \\[(\\d+)/(\\w+)/(\\d+):");
			sb.append("(\\d+):(\\d+):(\\d+)");
			sb.append(" ([+-]\\d+)\\]");
			sb.append(" \"(\\w+) ");
			sb.append("("+PatternLibrary.urlPattern+")");
			sb.append("[\\s\\w/]*[\\d\\.\\d]*\"");
			sb.append(" (\\d+) ([\\-\\d+])(.*)");
		p[i] = Pattern.compile(sb.toString());i++;
		logger.debug(p[0].toString());
		logger.warn("You have to check the pattern first, iPPattern ()");
		System.exit(-1);
	}

	public void parseLine(String line)
	{
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<maxP;i++)
		{
			Matcher m=p[i].matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: event(m);break;		
				}
				i=maxP;
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	public void event(Matcher m)
	{
		ExlpHost host = new ExlpHost();
		host.setIp(m.group(1));
		
		try
		{
			int year = Integer.valueOf(m.group(4));
			int month = DateUtil.getMonth(m.group(3));
			int day = Integer.valueOf(m.group(2));
			int hour = Integer.valueOf(m.group(5));
			int min = Integer.valueOf(m.group(6));
			int sec = Integer.valueOf(m.group(7));
			LocalDateTime ldt = LocalDateTime.of(year,month,day,hour,min,sec);
			
			ExlpApache apache = new ExlpApache();
			apache.setSize(1);
			apache.setRecord(DateUtil.toDate(ldt));
			apache.setReq(m.group(9));
			apache.setUrl(m.group(10));
			apache.setCode(m.group(11));
			apache.setSize(getSize(m.group(12)));
			
			ApacheEvent event = new ApacheEvent(apache,host);
			leh.handleEvent(event);
		}
		catch (ParseException e) {logger.error("",e);}
		catch (NumberFormatException e) {logger.error("",e);}
	}
	
	private int getSize(String s)
	{
		if(s.equals("-")){return 0;}
		return Integer.valueOf(s);
	}
}