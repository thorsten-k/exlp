package net.sf.exlp.event;

import java.util.Date;

public interface LogEvent
{
	public void debug();
	
	public Date getRecord();
}