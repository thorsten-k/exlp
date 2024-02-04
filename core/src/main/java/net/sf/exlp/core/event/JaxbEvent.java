package net.sf.exlp.core.event;

import net.sf.exlp.interfaces.LogEvent;

import org.exlp.util.jx.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxbEvent extends AbstractEvent implements LogEvent
{
	final static Logger logger = LoggerFactory.getLogger(JaxbEvent.class);
	
	static final long serialVersionUID=1;
	
	private Object o;

	public JaxbEvent(Object o)
	{
		this.o=o;
	}
	
	public void debug()
	{
		super.debug();
		JaxbUtil.debug(o);
	}
	
	public Object getObject() {return o;}
}