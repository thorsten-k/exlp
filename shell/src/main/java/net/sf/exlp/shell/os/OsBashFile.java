package net.sf.exlp.shell.os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpUnsupportedOsException;

public class OsBashFile
{
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
	
	public static String fileExtention() throws ExlpUnsupportedOsException
	{
		switch(OsArchitectureUtil.getArch())
		{
			case Linux: return "sh";
			case OsX: return "sh";
			case Win32: return "bat";
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)"); return "";
		}
	}
}