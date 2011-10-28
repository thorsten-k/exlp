package net.sf.exlp.event;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractEventHandler implements Serializable,LogEventHandler  
{
	static Log logger = LogFactory.getLog(AbstractEventHandler.class);
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
