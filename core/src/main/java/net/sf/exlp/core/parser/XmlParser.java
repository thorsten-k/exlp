package net.sf.exlp.core.parser;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.event.JDomEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.util.xml.JDomUtil;

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
			sb.append(s+System.lineSeparator());
		}
		Document doc = null;
		try
		{
			doc = JDomUtil.txtToDoc(sb.toString());
			JDomEvent e = new JDomEvent(doc);
			leh.handleEvent(e);
		}
		catch (JDOMException e){logger.warn(e.getMessage());}		
		
	}
}