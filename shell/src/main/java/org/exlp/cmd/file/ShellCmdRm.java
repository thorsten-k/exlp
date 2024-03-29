package org.exlp.cmd.file;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdRm
{
	final static Logger logger = LoggerFactory.getLogger(ShellCmdRm.class);
	
	public static synchronized String rmDir(String dir, boolean onlySubEntries) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:  sb.append(rmDirWin(dir, onlySubEntries));break;
			case OsX: 	 sb.append(rmDirOsx(dir, onlySubEntries));break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	private static String rmDirWin(String dir, boolean onlySubEntries)
	{
		StringBuffer sb = new StringBuffer();
		if(onlySubEntries)
		{
			sb.append("del");
			sb.append(" /Q");
			sb.append(" /S");
		}
		else
		{
			sb.append("rmdir");
		}
		sb.append(" ").append(dir);
		return sb.toString();
	}
	
	private static String rmDirOsx(String dir, boolean onlySubEntries)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("rm -rf ");
		sb.append(dir);
		if(onlySubEntries)
		{
			sb.append("/*");
		}
		return sb.toString();
	}
}