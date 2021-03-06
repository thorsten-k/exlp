package net.sf.exlp.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteCount
{
	final static Logger logger = LoggerFactory.getLogger(ByteCount.class);
	
	public static String si(long bytes)
	{
		return human(bytes,true);
	}
	
	public static String binary(long bytes)
	{
		return human(bytes,false);
	}
	
	public static String human(Long bytes, Boolean si)
	{
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}