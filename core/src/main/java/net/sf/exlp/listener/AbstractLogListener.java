package net.sf.exlp.listener;

import java.util.Properties;

import net.sf.exlp.parser.LogParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public abstract class AbstractLogListener
{	
	final static Marker fatal = MarkerFactory.getMarker("FATAL");
	final static Logger logger = LoggerFactory.getLogger(AbstractLogListener.class);
	
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
		logger.error(fatal,msg);
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
		logger.error(fatal,"Forbidden call: "+call);
		logger.error(fatal,"But you can ovveride this!");
		logger.error(fatal,"System will exit!");
		System.exit(-1);
	}
}