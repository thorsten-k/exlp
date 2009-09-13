package net.sf.exlp.event.handler;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.log4j.Logger;

public class EhDebug extends AbstractEventHandler 
{
	static Logger logger = Logger.getLogger(EhDebug.class);
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