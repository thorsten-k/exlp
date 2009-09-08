package net.sf.exlp.parser;

import java.util.ArrayList;
import java.util.Properties;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;

import org.apache.log4j.Logger;


public class AbstractLogParser
{
	static Logger logger = Logger.getLogger(AbstractLogParser.class);
	
	protected LogEventHandler ehi;
	protected Properties metaInfo;
	protected int allLines,unknownLines;
	
	public AbstractLogParser(){this(new EhDebug());}
	public AbstractLogParser(LogEventHandler ehi)
	{
		this.ehi=ehi;
		unknownLines=0;
		allLines=0;
	}
	
	public void addMetaInfo(Properties metaInfo) {this.metaInfo=metaInfo;}
	public int getAllLines() {return allLines;}
	public int getUnknownLines(){return unknownLines;}
	
	public void parseLine(String line)
	{
		logger.fatal("parseLine falscher Aufruf.");
		System.exit(-1);
	}
	
	public void parseLine(String line,String DateiName)
	{
		parseLine(line);
	}
	
	public void parseItem(ArrayList<String> item)
	{
		logger.fatal("parseItem falscher Aufruf.");
		System.exit(-1);
	}
}
