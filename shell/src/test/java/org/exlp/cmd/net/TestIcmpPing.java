package org.exlp.cmd.net;

import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.core.handler.EhList;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.shell.spawn.ping.IcmpPing;

import org.exlp.test.AbstractExlpShellTest;
import org.exlp.test.ExlpShellBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestIcmpPing extends AbstractExlpShellTest
{
	final static Logger logger = LoggerFactory.getLogger(TestIcmpPing.class);
   
	public static void main(String[] args)
	{
		ExlpShellBootstrap.init();
		
		List<LogEvent> list = new ArrayList<LogEvent>();
		LogEventHandler leh = new EhList(list);
		IcmpPing ping = new IcmpPing("www.google.de",10);
		ping.ping(leh);
		logger.info("Wating for size ");
		logger.info("Size"+list.size());
	}
}