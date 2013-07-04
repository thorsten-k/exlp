package net.sf.exlp.shell.cmd;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.architecture.OsArchitectureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdCopy
{
	final static Logger logger = LoggerFactory.getLogger(ShellCmdCopy.class);
	
	public static synchronized String copyFile(String from, String to) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32: sb.append("copy "+from+" "+to);break;
			case OsX:	sb.append("cp "+from+" "+to);break;
			default: OsArchitectureUtil.errorUnsupportedOS("copy fileA to fileB");break;
		}	
		return sb.toString();
	}
}