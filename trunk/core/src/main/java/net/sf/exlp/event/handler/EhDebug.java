package net.sf.exlp.event.handler;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.interfaces.LogEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhDebug extends AbstractEventHandler 
{
	final static Logger logger = LoggerFactory.getLogger(EhDebug.class);
	
	static final long serialVersionUID=1;
	
	public EhDebug()
	{

	}

	public boolean handleEvent(LogEvent event)
	{
		count();
		event.debug();
		return true;
	}
}