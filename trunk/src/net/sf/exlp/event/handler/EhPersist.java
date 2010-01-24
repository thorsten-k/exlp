package net.sf.exlp.event.handler;

import java.util.Hashtable;
import java.util.Map;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;
import net.sf.exlp.event.exception.HandlerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EhPersist extends AbstractEventHandler 
{
	static Log logger = LogFactory.getLog(EhPersist.class);
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

	public boolean handleEvent(LogEvent le)
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