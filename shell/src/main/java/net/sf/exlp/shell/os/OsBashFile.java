package net.sf.exlp.shell.os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class OsBashFile
{
	final static Marker fatal = MarkerFactory.getMarker("FATAL");
	final static Logger logger = LoggerFactory.getLogger(OsBashFile.class);
	
	private final static String prefixBinBash = "#!/bin/sh";
	
	public static String prefix()
	{
		String prefix = null;
		switch(OsArchitectureUtil.getArch())
		{
			case Linux: prefix = prefixBinBash;break;
			case OsX: prefix = prefixBinBash;break;
			default: break;
		}
		
		return prefix;
	}
	
	public static String comment(String comment)
	{
		StringBuffer sb = new StringBuffer();
		
		switch(OsArchitectureUtil.getArch())
		{
			case Linux: sb.append("# ");break;
			case OsX: sb.append("# ");break;
			default: break;
		}
		sb.append(comment);
		
		return sb.toString();
	}
}