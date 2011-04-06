package net.sf.exlp.event.handler;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EhVoid extends AbstractEventHandler 
{
	static Log logger = LogFactory.getLog(EhVoid.class);
	static final long serialVersionUID=1;
	
	public EhVoid()
	{

	}

	public boolean handleEvent(LogEvent pe)
	{
		count();
		return true;
	}
}