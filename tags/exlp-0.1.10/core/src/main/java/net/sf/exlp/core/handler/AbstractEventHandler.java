package net.sf.exlp.core.handler;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventHandler implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractEventHandler.class);
	
	static final long serialVersionUID=1;
	
	protected long eventCounter;
	
	public AbstractEventHandler()
	{
		eventCounter=0;
	}
	
	public void close(){logger.error("close Nicht implementiert");}
	
	protected void count(){eventCounter++;}
	public long getEventCounter() {return eventCounter;}
}
