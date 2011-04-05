package net.sf.exlp.event.handler;

import java.util.List;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EhList extends AbstractEventHandler 
{
	static Log logger = LogFactory.getLog(EhList.class);
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