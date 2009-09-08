package net.sf.exlp.listener;

import java.util.Properties;

public interface LogListener
{
	public void processSingle();
	public void processSingle(String cmd); 
	public void processMulti(String cmd);
	public void addMetaInfo(Properties p);
	public void beenden();
}
