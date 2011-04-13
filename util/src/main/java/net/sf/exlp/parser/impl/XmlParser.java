package net.sf.exlp.parser.impl;

import java.util.List;
import java.util.regex.Matcher;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.parser.LogParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlParser extends AbstractLogParser implements LogParser  
{
	static Log logger = LogFactory.getLog(XmlParser.class);

	
	public XmlParser(LogEventHandler leh)
	{
		super(leh);
	}
	
	@Override
	public void parseItem(List<String> item)
	{
		logger.debug("Item received with "+item.size()+" entries");
	}
	
	public void event(Matcher m)
	{
		logger.debug(m.group(0));
	}
}