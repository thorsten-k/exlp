package net.sf.exlp.addon.exim.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.addon.apache.ejb.ExlpApache;
import net.sf.exlp.addon.apache.event.ApacheEvent;
import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.PatternFactory;
import net.sf.exlp.util.DateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EximParser extends AbstractLogParser implements LogParser  
{
	static Log logger = LogFactory.getLog(EximParser.class);

	List<Pattern> lPattern = new ArrayList<Pattern>();
	
	public EximParser(LogEventHandler leh)
	{
		super(leh);
		StringBuffer sb = new StringBuffer();
			sb.append(PatternFactory.ipPattern);
			sb.append(" - ");
			sb.append("[\\w-\"]+");
			sb.append(" \\[(\\d+)/(\\w+)/(\\d+):");
			sb.append("(\\d+):(\\d+):(\\d+)");
			sb.append(" ([+-]\\d+)\\]");
			sb.append(" \"(\\w+) ");
			sb.append("("+PatternFactory.urlPattern+")");
			sb.append("[\\s\\w/]*[\\d\\.\\d]*\"");
			sb.append(" (\\d+) ([\\-\\d+])(.*)");
		lPattern.add(Pattern.compile(PatternFactory.eximDate+" "+PatternFactory.eximTime+"(.*)"));
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
			int year = new Integer(m.group(4));
			int month = DateUtil.getMonth(m.group(3));
			int day = new Integer(m.group(2));
			int hour = new Integer(m.group(5));
			int min = new Integer(m.group(6));
			int sec = new Integer(m.group(7));
						
			ExlpApache apache = new ExlpApache();
			apache.setSize(1);
			apache.setRecord(DateUtil.getDateFromInt(year,month,day,hour,min,sec));
			apache.setReq(m.group(9));
			apache.setUrl(m.group(10));
			apache.setCode(m.group(11));
			apache.setSize(getSize(m.group(12)));
			
			ApacheEvent event = new ApacheEvent(apache,host);
			leh.handleEvent(event);
	//		logger.debug(m.group(0));
		}
		catch (ParseException e) {logger.error(e);}
		catch (NumberFormatException e) {logger.error(e);}
	}
	
	private int getSize(String s)
	{
		if(s.equals("-")){return 0;}
		return new Integer(s);
	}
}