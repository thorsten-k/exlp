package net.sf.exlp.shell.cmd;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.architecture.OsArchitectureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdPing
{
	final static  Logger logger = LoggerFactory.getLogger(ShellCmdPing.class);
	
	public static synchronized String ping(String host, int anzahl) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32: sb.append("ping -n "+anzahl+" "+host);break;
			case OsX:	sb.append("ping -c "+anzahl+" "+host);break;
			case Linux:	sb.append("ping -c "+anzahl+" "+host);break;
			default:	OsArchitectureUtil.errorUnsupportedOS("ping -number target");break;
		}	
		return sb.toString();
	}
}
