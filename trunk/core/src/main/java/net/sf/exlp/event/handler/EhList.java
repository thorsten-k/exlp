package net.sf.exlp.event.handler;

import java.util.List;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.interfaces.LogEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhList extends AbstractEventHandler 
{
	final static Logger logger = LoggerFactory.getLogger(EhList.class);
	
	static final long serialVersionUID=1;
	private List<LogEvent> list;
	
	public EhList(List<LogEvent> list)
	{
		this.list=list;
	}

	public boolean handleEvent(LogEvent event)
	{
		count();
		list.add(event);
		return true;
	}
}