package net.sf.exlp.addon.apache;

import java.util.Hashtable;

import net.sf.exlp.addon.apache.parser.ApacheParser;
import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhPersist;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerFile;
import net.sf.exlp.parser.LogParser;

public class ApacheLog
{	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		LogEventHandler leh = new EhPersist(new Hashtable<String,Object>());
		LogParser lp = new ApacheParser(leh);
		LogListener lSingle = new LogListenerFile(args[0],lp);
		lSingle.processSingle();
	}
}