package net.sf.exlp.shell.cmd;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.architecture.OsArchitectureUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellRoutingTable
{
	final static Logger logger = LoggerFactory.getLogger(ShellRoutingTable.class);
	
	public static synchronized String cmd() throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32: sb.append("route print");break;
			case OsX:	sb.append("netstat -r");break;
			default:	OsArchitectureUtil.errorUnsupportedOS("print routingtable");break;
		}	
		return sb.toString();
	}

}
