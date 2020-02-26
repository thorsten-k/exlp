package de.kisner.exlp.test.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.exlp.test.ExlpCoreBootstrap;
import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerTail;
import net.sf.exlp.core.parser.DummyParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

public class CliListenerTail
{
	final static Logger logger = LoggerFactory.getLogger(CliListenerTail.class);
	
	private final LogEventHandler leh;
	private final LogParser lp;
	
	public CliListenerTail()
	{
		leh = new EhDebug();
		lp = new DummyParser(leh);
	}
	
	public void jbossGc()
	{
		LogListener ll = new LogListenerTail(lp,"/Volumes/ramdisk/jboss/standalone/log/gc.log.0.current");
		ll.processSingle();
	}
	
	
	public static void main(String args[])
	{
		ExlpCoreBootstrap.init();
			
		CliListenerTail cli = new CliListenerTail();
		cli.jbossGc();
	}
}
