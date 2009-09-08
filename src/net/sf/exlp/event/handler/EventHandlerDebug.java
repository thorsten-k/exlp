package net.sf.exlp.event.handler;


import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.log4j.Logger;



public class EventHandlerDebug extends AbstractEventHandler 
{
	static Logger logger = Logger.getLogger(EventHandlerDebug.class);
	static final long serialVersionUID=1;
	
	public EventHandlerDebug()
	{

	}

	public boolean handleEvent(LogEvent pe)
	{
		count();
		pe.debug();
		return true;
	}

}
