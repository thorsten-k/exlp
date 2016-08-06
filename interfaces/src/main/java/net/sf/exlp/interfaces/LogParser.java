package net.sf.exlp.interfaces;

import java.util.List;
import java.util.Properties;

public interface LogParser
{
	void parseLine(String Line);
	void parseLine(String line,String dateiName);
	void parseItem(List<String> item);
	void addMetaInfo(Properties p);
	void close();
	void debugStats();
}