package net.sf.exlp.test.addon;

import net.sf.exlp.addon.exim.parser.EximParser;
import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerFile;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.util.io.LoggerInit;

public class EximLog
{	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.path("resources/config");
			loggerInit.init();
		
		LogEventHandler leh = new EhDebug();
		LogParser lp = new EximParser(leh);
		LogListener lSingle = new LogListenerFile(args[0],lp);
		lSingle.processSingle();
	}
}