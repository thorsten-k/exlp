package net.sf.exlp.interfaces;

import java.util.Properties;

public interface LogListener
{
	public void processSingle();
	public void processSingle(String cmd); 
	public void processMulti(String cmd);
	public void addMetaInfo(Properties p);
	public void close();
}
