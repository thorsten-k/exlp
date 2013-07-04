package net.sf.exlp.interfaces;

import java.util.List;
import java.util.Properties;

public interface LogParser
{
	public void parseLine(String Line);
	public void parseLine(String line,String dateiName);
	public void parseItem(List<String> item);
	public void addMetaInfo(Properties p);
	public void close();
	
	public void debugStats();
}
