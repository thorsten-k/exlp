package net.sf.exlp.interfaces;

public interface LogEventHandler
{
	public boolean handleEvent(LogEvent event);
	public void close();
	public long getEventCounter();
}
