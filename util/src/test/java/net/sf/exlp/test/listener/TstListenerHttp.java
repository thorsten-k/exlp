package net.sf.exlp.test.listener;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerHttp;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.impl.DummyParser;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TstListenerHttp
{
	static Log logger = LogFactory.getLog(TstListenerHttp.class);
	
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
