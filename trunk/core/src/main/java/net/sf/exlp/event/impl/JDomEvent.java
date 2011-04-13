package net.sf.exlp.event.impl;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;

public class JDomEvent extends AbstractEvent implements LogEvent
{
	static Log logger = LogFactory.getLog(JDomEvent.class);
	static final long serialVersionUID=1;
	
	private Document doc;

	public JDomEvent(Document doc)
	{
		this.doc=doc;
	}
	
	public void debug()
	{
		super.debug();
		JDomUtil.debug(doc);
	}
	
	public Document getDoc() {return doc;}
}