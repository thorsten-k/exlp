package net.sf.exlp.listener;

import java.util.Properties;


import net.sf.exlp.parser.LogParser;

import org.apache.log4j.Logger;


public abstract class AbstractLogListener
{	
	static Logger logger = Logger.getLogger(AbstractLogListener.class);
	
	public Properties metaInfo;
	protected LogParser lp;
	
	public AbstractLogListener()
	{
		
	}
	
	public AbstractLogListener(LogParser lp)
	{
		this.lp=lp;
	}
	
	public void exit(String msg)
	{
		logger.fatal(msg);
		System.exit(-1);
	}
	
	public void addMetaInfo(Properties metaInfo)
	{
		this.metaInfo=metaInfo;
	}
	
	protected void debug()
	{
		logger.debug("Gesamtzeilen="+lp.getAllLines()+" und davon unbekannt "+lp.getUnknownLines());
	}
	
	public void processSingle()
	{
		logger.fatal("processSingle falscher Aufruf.");
		System.exit(-1);
	}
	public void processSingle(String cmd)
	{
		logger.fatal("processSingle(cmd) falscher Aufruf.");
		System.exit(-1);
	}
	
	public void processMulti(String cmd)
	{
		logger.fatal("processSingle falscher Aufruf.");
		System.exit(-1);
	}
	
	public void beenden() 
	{
		logger.debug(".beenden (unn�tig zu implementieren)");
	}
	
}