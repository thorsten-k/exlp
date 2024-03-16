package net.sf.exlp.test.addon;

import org.exlp.test.ExlpEjbBootstrap;

import net.sf.exlp.addon.apache.parser.ApacheParser;
import net.sf.exlp.core.handler.EhDebug;
import net.sf.exlp.core.listener.LogListenerFile;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

public class ApacheLog
{	
	public static void main (String[] args) throws Exception
	{
		ExlpEjbBootstrap.init();
		
		LogEventHandler leh = new EhDebug();
		LogParser lp = new ApacheParser(leh);
		LogListener lSingle = new LogListenerFile(args[0],lp);
		lSingle.processSingle();
	}
}