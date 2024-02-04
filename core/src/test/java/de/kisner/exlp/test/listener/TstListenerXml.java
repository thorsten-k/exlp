package de.kisner.exlp.test.listener;

import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerXml;
import net.sf.exlp.core.parser.DummyParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

import org.exlp.util.io.log.LoggerInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstListenerXml
{
	final static Logger logger = LoggerFactory.getLogger(TstListenerXml.class);
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.path("resources/config");
			loggerInit.init();
			
		LogEventHandler leh = new EhDebug();
		LogParser lp = new DummyParser(leh);
		LogListener ll = new LogListenerXml("../openFuXML/resources/data/timeline.xml",lp);
		ll.processMulti("/wikiinjection/a");
	}
}
