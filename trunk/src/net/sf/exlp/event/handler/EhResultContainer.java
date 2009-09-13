package net.sf.exlp.event.handler;

import java.util.ArrayList;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.List;

public class EhResultContainer extends AbstractEventHandler 
{
	static Logger logger = Logger.getLogger(EhResultContainer.class);
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
}