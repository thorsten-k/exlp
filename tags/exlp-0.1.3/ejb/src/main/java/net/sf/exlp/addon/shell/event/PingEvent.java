package net.sf.exlp.addon.shell.event;

import java.io.Serializable;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PingEvent extends AbstractEvent implements LogEvent,Serializable
{
	static Log logger = LogFactory.getLog(PingEvent.class);
	static final long serialVersionUID=1;
	
	private String hostIp;
	private double time;
	
	public PingEvent(String hostIp, double time)
	{
		this.hostIp=hostIp;
		this.time=time;
	}

	public void debug()
	{
		super.debug();
		StringBuffer sb = new StringBuffer();
			sb.append("\t");
			sb.append(" -> ");
			sb.append(hostIp);
			sb.append(" ... "+time);
		logger.debug(sb.toString());		
	}

	public double getTime() {return time;}
	public String getHostIp() {return hostIp;}
}