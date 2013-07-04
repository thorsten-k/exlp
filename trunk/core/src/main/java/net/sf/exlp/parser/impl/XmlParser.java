package net.sf.exlp.parser.impl;

import java.util.List;

import net.sf.exlp.event.impl.JDomEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.parser.AbstractLogParser;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.lang.SystemUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(XmlParser.class);

	public XmlParser(LogEventHandler leh)
	{
		super(leh);
	}
	
	@Override
	public void parseItem(List<String> item)
	{
		
		StringBuffer sb = new StringBuffer();
		for(String s : item)
		{
			sb.append(s+SystemUtils.LINE_SEPARATOR);
		}
		Document doc = null;
		try
		{
			doc = JDomUtil.txtToDoc(sb.toString());
			JDomEvent e = new JDomEvent(doc);
			leh.handleEvent(e);
		}
		catch (JDOMException e){}		
		
	}
}