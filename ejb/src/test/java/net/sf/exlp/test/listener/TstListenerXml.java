package net.sf.exlp.test.listener;

import net.sf.exlp.event.handler.EhDebug;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerXml;
import net.sf.exlp.parser.impl.DummyParser;
import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstListenerXml
{
	final static Logger logger = LoggerFactory.getLogger(TstListenerXml.class);
	
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
