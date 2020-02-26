package de.kisner.exlp.test.listener;

import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerHttp;
import net.sf.exlp.core.parser.DummyParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstListenerHttp
{
	final static Logger logger = LoggerFactory.getLogger(TstListenerHttp.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/main/resources/config");
			loggerInit.init();
			
		LogEventHandler leh = new EhDebug();
		LogParser lp = new DummyParser(leh);
		LogListener ll = new LogListenerHttp(lp);
		ll.processSingle("http://www.google.com");
	}
}
