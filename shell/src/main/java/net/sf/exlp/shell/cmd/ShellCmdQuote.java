package net.sf.exlp.shell.cmd;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdQuote
{
	final static Logger logger = LoggerFactory.getLogger(ShellCmdQuote.class);
	
	public static String quote(String cmd) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32: sb.append("\"").append(cmd).append("\"");break;
			case OsX:	sb.append("'").append(cmd).append("'");break;
			case Linux:	sb.append("'").append(cmd).append("'");break;
			default: OsArchitectureUtil.errorUnsupportedOS("quote ...");break;
		}	
		return sb.toString();
	}
}