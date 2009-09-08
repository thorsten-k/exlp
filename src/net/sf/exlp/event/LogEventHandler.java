package net.sf.exlp.event;


public interface LogEventHandler
{
	public boolean handleEvent(LogEvent event);
	public void close();
	public long getEventCounter();
}
