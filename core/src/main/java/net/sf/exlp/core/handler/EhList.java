package net.sf.exlp.core.handler;

import java.io.Serializable;
import java.util.List;

import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhList extends AbstractEventHandler implements Serializable,LogEventHandler
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