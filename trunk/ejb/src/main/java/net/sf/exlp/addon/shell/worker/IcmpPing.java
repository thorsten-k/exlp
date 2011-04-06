package net.sf.exlp.addon.shell.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.exlp.addon.shell.cmd.ShellCmdPing;
import net.sf.exlp.addon.shell.event.PingEvent;
import net.sf.exlp.addon.shell.parser.PingParser;
import net.sf.exlp.event.LogEvent;
import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhQueue;
import net.sf.exlp.io.spawn.Spawn;
import net.sf.exlp.parser.LogParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IcmpPing
{
	static Log logger = LogFactory.getLog(IcmpPing.class);
	private static final long serialVersionUID = 1;
	
	private String pingAddress;
	private int pings;

	public IcmpPing(String pingAddress, int pings)
	{
		this.pingAddress=pingAddress;
		this.pings=pings;
	}
	
	public void ping(LogEventHandler eh)
	{
		LogParser lp = new PingParser(eh);
		String cmd = ShellCmdPing.ping(pingAddress, pings);
		Spawn spawn = new Spawn(cmd);
		spawn.setLp(lp);
//		spawn.setWriter(new PrintWriter(System.out));
		spawn.cmd();
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
			catch (InterruptedException e) {logger.error(e);}
		}
		return listPings;
	}
}
