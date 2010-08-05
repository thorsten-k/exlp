package net.sf.exlp.test.listener;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerTail;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.impl.TestParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestListenerTail
{
	static Log logger = LogFactory.getLog(TestListenerTail.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		LogEventHandler leh = new EhDebug();
		LogParser lp = new TestParser(leh);
		LogListener ll = new LogListenerTail("/tmp/exim.log",lp);
		ll.processSingle();
	}
}
