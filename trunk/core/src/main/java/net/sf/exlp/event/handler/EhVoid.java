package net.sf.exlp.event.handler;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.interfaces.LogEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhVoid extends AbstractEventHandler 
{
	final static Logger logger = LoggerFactory.getLogger(EhVoid.class);
	
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