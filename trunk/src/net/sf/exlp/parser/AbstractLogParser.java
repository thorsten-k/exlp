package net.sf.exlp.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractLogParser
{
	static Log logger = LogFactory.getLog(AbstractLogParser.class);
	
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
		logger.info(name+": All="+allLines+" UnknownPattern="+unknownLines+" UnknownHandling="+unknownHandling);
	}
	
	public void debugMe()
	{
		logger.warn("This method should be overriden!!");
	}
	
	public void parseLine(String line)
	{
		logger.fatal("Method \"parseLine\" is not implemented for this parser!");
		System.exit(-1);
	}
	
	public void parseLine(String line,String DateiName)
	{
		parseLine(line);
	}
	
	public void parseItem(ArrayList<String> item)
	{
		logger.fatal("Method \"parseItem\" is not implemented for this parser!");
		System.exit(-1);
	}
}