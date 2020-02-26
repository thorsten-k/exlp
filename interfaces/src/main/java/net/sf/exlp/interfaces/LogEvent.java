package net.sf.exlp.interfaces;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public interface LogEvent extends Serializable
{
	void debug();
	Date getRecord();
	boolean persist(Map<String,Object> facades);
	boolean save(File dir);
}