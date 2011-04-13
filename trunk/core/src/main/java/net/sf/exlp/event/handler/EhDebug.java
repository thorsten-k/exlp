package net.sf.exlp.event.handler;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EhDebug extends AbstractEventHandler 
{
	static Log logger = LogFactory.getLog(EhDebug.class);
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