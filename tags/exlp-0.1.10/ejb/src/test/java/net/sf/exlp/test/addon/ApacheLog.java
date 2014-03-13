package net.sf.exlp.test.addon;

import net.sf.exlp.addon.apache.parser.ApacheParser;
import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerFile;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.util.io.LoggerInit;

public class ApacheLog
{	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		LogEventHandler leh = new EhDebug();
		LogParser lp = new ApacheParser(leh);
		LogListener lSingle = new LogListenerFile(args[0],lp);
		lSingle.processSingle();
	}
}