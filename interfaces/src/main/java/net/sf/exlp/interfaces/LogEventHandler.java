package net.sf.exlp.interfaces;

public interface LogEventHandler
{
	boolean handleEvent(LogEvent event);
	void close();
	long getEventCounter();
}