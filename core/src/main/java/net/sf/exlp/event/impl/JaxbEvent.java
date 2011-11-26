package net.sf.exlp.event.impl;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxbEvent extends AbstractEvent implements LogEvent
{
	final static Logger logger = LoggerFactory.getLogger(JaxbEvent.class);
	
	static final long serialVersionUID=1;
	
	private Object o;

	public JaxbEvent(Object o)
	{
		this.o=o;
	}
	
	public void debug()
	{
		super.debug();
		JaxbUtil.debug(o);
	}
	
	public Object getObject() {return o;}
}