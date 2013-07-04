package net.sf.exlp.event.handler;

import java.util.concurrent.LinkedBlockingQueue;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.interfaces.LogEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhQueue extends AbstractEventHandler 
{
	final static Logger logger = LoggerFactory.getLogger(EhQueue.class);
	
	static final long serialVersionUID=1;
	private LinkedBlockingQueue<LogEvent> queue;
	
	public EhQueue(LinkedBlockingQueue<LogEvent> queue)
	{
		this.queue=queue;
	}

	public boolean handleEvent(LogEvent event)
	{
		count();
		try{queue.put(event);}
		catch (InterruptedException e) {logger.error(""+e);return false;}
		return true;
	}
}