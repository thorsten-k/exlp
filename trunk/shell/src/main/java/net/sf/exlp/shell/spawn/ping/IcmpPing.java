package net.sf.exlp.shell.spawn.ping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.exlp.core.handler.EhQueue;
import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.shell.cmd.ShellCmdPing;
import net.sf.exlp.shell.spawn.Spawn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcmpPing
{
	final static Logger logger = LoggerFactory.getLogger(IcmpPing.class);
	
	private String pingAddress;
	private int pings;

	public IcmpPing(String pingAddress, int pings)
	{
		this.pingAddress=pingAddress;
		this.pings=pings;
	}
	
	public void ping(LogEventHandler eh)
	{
		try
		{
			String cmd = ShellCmdPing.ping(pingAddress, pings);
			
			LogParser lp = new PingParser(eh);
			
			Spawn spawn = new Spawn(cmd);
			spawn.setLp(lp);
//			spawn.setWriter(new PrintWriter(System.out));
			spawn.cmd();

		}
		catch (ExlpUnsupportedOsException e) {logger.error(""+e);}
	}
	
	public List<PingEvent> ping()
	{
		LinkedBlockingQueue<LogEvent> queue = new LinkedBlockingQueue<LogEvent>();
		LogEventHandler eh = new EhQueue(queue);
		ping(eh);
		List<PingEvent> listPings = new ArrayList<PingEvent>();
		while(!queue.isEmpty())
		{
			try
			{
				PingEvent event = (PingEvent)queue.take();
				listPings.add(event);
			}
			catch (InterruptedException e) {logger.error(""+e);}
		}
		return listPings;
	}
}
