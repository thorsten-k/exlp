package net.sf.exlp.listener;

import java.util.Properties;

import net.sf.exlp.parser.LogParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractLogListener
{	
	static Log logger = LogFactory.getLog(AbstractLogListener.class);
	
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
	
	protected void debugStats()
	{
		lp.debugStats();
	}
	
	public void processSingle(){exitCall("processSingle()");}
	public void processSingle(String cmd){exitCall("processSingle(String)");}
	public void processMulti(String cmd){exitCall("processMulti(String)");}
	public void close(){exitCall("close()");}
	
	private void exitCall(String call)
	{
		logger.fatal("Forbidden call: "+call);
		logger.fatal("But you can ovveride this!");
		logger.fatal("System will exit!");
		System.exit(-1);
	}
}