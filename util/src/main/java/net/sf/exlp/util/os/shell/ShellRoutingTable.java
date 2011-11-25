package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellRoutingTable extends AbstractShellCmd
{
	final static Logger logger = LoggerFactory.getLogger(ShellRoutingTable.class);
	
	public ShellRoutingTable()
	{
		
	}
	
	public String cmd() throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(arch)
		{
			case Win32: sb.append("route print");break;
			case OsX:	sb.append("netstat -r");break;
			default:	errorUnsupportedOS("print routingtable");break;
		}	
		return sb.toString();
	}

}
