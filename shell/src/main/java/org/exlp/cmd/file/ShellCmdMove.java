package org.exlp.cmd.file;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdMove
{
	final static  Logger logger = LoggerFactory.getLogger(ShellCmdMove.class);
	
	public static synchronized String moveFile(String from, String to) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32: sb.append("move "+from+" "+to);break;
			case OsX: sb.append("mv "+from+" "+to);break;
			default: OsArchitectureUtil.errorUnsupportedOS("mv fileA to fileB");break;
		}	
		return sb.toString();
	}
}