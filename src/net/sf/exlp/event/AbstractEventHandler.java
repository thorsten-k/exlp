package net.sf.exlp.event;

import java.io.Serializable;


import org.apache.log4j.Logger;

public abstract class AbstractEventHandler implements Serializable,LogEventHandler  
{
	static Logger logger = Logger.getLogger(AbstractEventHandler.class);
	
	protected long eventCounter;
	
	public AbstractEventHandler()
	{
		eventCounter=0;
	}
	
	public void close(){logger.error("close Nicht implementiert");}
	
	protected void count(){eventCounter++;}
	public long getEventCounter() {return eventCounter;}
}
