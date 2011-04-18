package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShellSymbolicLink extends AbstractShellCmd
{
	static Log logger = LogFactory.getLog(ShellSymbolicLink.class);
	
	public ShellSymbolicLink()
	{
		
	}
	
	public String routingTable() throws ExlpUnsupportedOsException
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
