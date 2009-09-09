package net.sf.exlp.event;

import java.util.Map;

public interface LogEvent
{
	void debug();
	boolean persist(Map<String,?> facades);
}