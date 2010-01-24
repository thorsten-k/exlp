package net.sf.exlp.event;

import java.util.Date;
import java.util.Map;

public interface LogEvent
{
	void debug();
	Date getRecord();
	boolean persist(Map<String,?> facades);
}