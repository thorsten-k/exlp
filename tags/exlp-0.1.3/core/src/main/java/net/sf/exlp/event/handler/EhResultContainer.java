package net.sf.exlp.event.handler;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EhResultContainer extends AbstractEventHandler 
{
	static Log logger = LogFactory.getLog(EhResultContainer.class);
	static final long serialVersionUID=1;
	
	private ArrayList<LogEvent> alResults;

	public EhResultContainer()
	{
		alResults = new ArrayList<LogEvent>();
	}

	public boolean handleEvent(LogEvent event)
	{
		count();
		alResults.add(event);
		return true;
	}
	
	public ArrayList<LogEvent> getAlResults() {return alResults;}
	
	public LogEvent getSingleResult()
	{
		if(alResults.size()>0){return alResults.get(0);}
		else
		{
			throw new NoSuchElementException("No "+LogEvent.class.getSimpleName()+" in container.");
		}
	}
}