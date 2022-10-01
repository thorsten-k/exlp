package de.kisner.exlp.test.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.exlp.test.ExlpCoreBootstrap;
import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerHttp;
import net.sf.exlp.core.parser.DummyParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

public class CliListenerHttp
{
	final static Logger logger = LoggerFactory.getLogger(CliListenerHttp.class);
	
	public static void main(String args[])
	{
		ExlpCoreBootstrap.init();
			
		LogEventHandler leh = new EhDebug();
		LogParser lp = new DummyParser(leh);
		LogListener ll = new LogListenerHttp(lp);
		ll.processSingle("http://www.google.com");
	}
}
