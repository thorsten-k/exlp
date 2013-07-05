package net.sf.exlp.core.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhResultContainer extends AbstractEventHandler implements Serializable,LogEventHandler 
{
	final static Logger logger = LoggerFactory.getLogger(EhResultContainer.class);
	
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