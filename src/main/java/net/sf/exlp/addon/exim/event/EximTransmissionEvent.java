package net.sf.exlp.addon.exim.event;

import net.sf.exlp.addon.apache.ejb.ExlpApache;
import net.sf.exlp.addon.common.data.ejb.ExlpHost;
import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EximTransmissionEvent extends AbstractEvent implements LogEvent
{
	static Log logger = LogFactory.getLog(EximTransmissionEvent.class);
	static final long serialVersionUID=1;
	
	
	public EximTransmissionEvent(ExlpApache apache, ExlpHost host)
	{

	}
	
	public void debug()
	{
		super.debug();

	}
	
}