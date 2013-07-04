package net.sf.exlp.interfaces;

import java.io.File;
import java.util.Date;
import java.util.Map;

public interface LogEvent
{
	void debug();
	Date getRecord();
	boolean persist(Map<String,Object> facades);
	boolean save(File dir);
}