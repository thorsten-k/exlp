package net.sf.exlp.interfaces;

import java.util.Properties;

public interface LogListener
{
	void processSingle();
	void processSingle(String cmd); 
	void processMulti(String cmd);
	void addMetaInfo(Properties p);
	void close();
}