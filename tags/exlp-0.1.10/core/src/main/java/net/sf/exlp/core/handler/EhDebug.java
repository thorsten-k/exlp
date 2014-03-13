package net.sf.exlp.core.handler;

import java.io.Serializable;

import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhDebug extends AbstractEventHandler implements Serializable,LogEventHandler
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