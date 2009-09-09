package net.sf.exlp.event.handler;

import java.util.Map;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.log4j.Logger;

public class EhPersist extends AbstractEventHandler 
{
	static Logger logger = Logger.getLogger(EhPersist.class);
	static final long serialVersionUID=1;
		
	Map<String,Object> facades;
	
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
}