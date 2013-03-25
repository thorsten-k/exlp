package net.sf.exlp.event.impl;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDomEvent extends AbstractEvent implements LogEvent
{
	final static Logger logger = LoggerFactory.getLogger(JDomEvent.class);
	
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