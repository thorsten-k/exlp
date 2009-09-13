package net.sf.exlp.event.handler;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.log4j.Logger;

public class EhVoid extends AbstractEventHandler 
{
	static Logger logger = Logger.getLogger(EhVoid.class);
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