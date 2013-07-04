package net.sf.exlp.addon.exim.event;

import net.sf.exlp.addon.apache.ejb.ExlpApache;
import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.interfaces.LogEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EximTransmissionEvent extends AbstractEvent implements LogEvent
{
	final static Logger logger = LoggerFactory.getLogger(EximTransmissionEvent.class);
	static final long serialVersionUID=1;
	
	public EximTransmissionEvent(ExlpApache apache, ExlpHost host)
	{

	}
	
	public void debug()
	{
		super.debug();

	}
	
}