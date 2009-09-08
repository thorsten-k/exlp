package net.sf.exlp.parser;

import java.util.ArrayList;
import java.util.Properties;

public interface LogParser
{
	public void parseLine(String Line);
	public void parseLine(String line,String dateiName);
	public void parseItem(ArrayList<String> item);
	public void addMetaInfo(Properties p);
	public int getAllLines();
	public int getUnknownLines();
}
