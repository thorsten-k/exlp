package net.sf.exlp.test.listener;

import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerTail;
import net.sf.exlp.core.parser.DummyParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstListenerTail
{
	final static Logger logger = LoggerFactory.getLogger(TstListenerTail.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		LogEventHandler leh = new EhDebug();
		LogParser lp = new DummyParser(leh);
		LogListener ll = new LogListenerTail("/tmp/exim.log",lp);
		ll.processSingle();
	}
}
