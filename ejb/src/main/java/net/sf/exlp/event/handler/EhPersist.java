package net.sf.exlp.event.handler;

import java.util.Hashtable;
import java.util.Map;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.interfaces.LogEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhPersist extends AbstractEventHandler 
{
	final static Logger logger = LoggerFactory.getLogger(EhPersist.class);
	
	static final long serialVersionUID=1;
		
	Map<String,Object> facades;
	
	public EhPersist()
	{
		facades = new Hashtable<String,Object>();
	}
	
	public EhPersist(Map<String,Object> facades)
	{
		this.facades = facades;
		logger.debug("inited with "+facades.size());
	}

	public synchronized boolean handleEvent(LogEvent le)
	{
		count();
		boolean result = le.persist(facades);
		return result;
	}
	
	public void addFacade(Class<?> c,Object facade)
	{
		facades.put(c.getSimpleName(), facade);
	}
}