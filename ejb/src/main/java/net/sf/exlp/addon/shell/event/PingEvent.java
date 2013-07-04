package net.sf.exlp.addon.shell.event;

import java.io.Serializable;

import net.sf.exlp.event.AbstractEvent;
import net.sf.exlp.interfaces.LogEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingEvent extends AbstractEvent implements LogEvent,Serializable
{
	final static Logger logger = LoggerFactory.getLogger(PingEvent.class);
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