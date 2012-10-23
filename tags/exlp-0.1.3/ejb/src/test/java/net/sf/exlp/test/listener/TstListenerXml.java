package net.sf.exlp.test.listener;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerXml;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.parser.impl.DummyParser;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TstListenerXml
{
	static Log logger = LogFactory.getLog(TstListenerXml.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		LogEventHandler leh = new EhDebug();
		LogParser lp = new DummyParser(leh);
		LogListener ll = new LogListenerXml("../openFuXML/resources/data/timeline.xml",lp);
		ll.processMulti("/wikiinjection/a");
	}
}