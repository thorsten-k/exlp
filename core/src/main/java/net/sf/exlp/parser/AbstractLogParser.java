package net.sf.exlp.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import net.sf.exlp.event.handler.EhDebug;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class AbstractLogParser
{
	final static Marker fatal = MarkerFactory.getMarker("FATAL");
	final static Logger logger = LoggerFactory.getLogger(AbstractLogParser.class);
	
	protected List<Pattern> pattern;
	protected List<LogParser> childParser;
	protected LogEventHandler leh;
	protected Properties metaInfo;
	protected int allLines,unknownLines,unknownHandling;
	
	public AbstractLogParser(){this(new EhDebug());}
	public AbstractLogParser(LogEventHandler leh)
	{
		this.leh=leh;
		unknownLines=0;
		allLines=0;
		unknownHandling=0;
		childParser = new ArrayList<LogParser>();
		pattern = new ArrayList<Pattern>();
	}
	
	public void addMetaInfo(Properties metaInfo) {this.metaInfo=metaInfo;}
	public int getAllLines() {return allLines;}
	public int getUnknownLines(){return unknownLines;}
	
	public void debugStats()
	{
		debugMe();
		for(LogParser lp : childParser)
		{
			lp.debugStats();
		}
	}
	
	public void debugMe(String name)
	{
		logger.debug(name+": All="+allLines+" UnknownPattern="+unknownLines+" UnknownHandling="+unknownHandling);
	}
	
	public void debugMe(){logger.warn("debugMe() Override this (with a debug(name) in your implementing class");}
	
	public void parseLine(String line){exitCall("parseLine(String)");}
	public void parseLine(String line,String DateiName){parseLine(line);}
	public void parseItem(List<String> item){exitCall("parseItem(List<String>)");}
	public void close(){}
	
	private void exitCall(String call)
	{
		logger.error(fatal,"Forbidden call: "+call);
		logger.error(fatal,"But you can ovveride this in you parser!");
		logger.error(fatal,"System will exit!");
		System.exit(-1);
	}
}
