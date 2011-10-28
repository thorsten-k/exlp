package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShellCmdPing extends AbstractShellCmd
{
	static Log logger = LogFactory.getLog(ShellCmdPing.class);
	
	public ShellCmdPing()
	{
		
	}
	
	public String ping(String host, int anzahl) throws ExlpUnsupportedOsException
	{
		StringBuffer sb = new StringBuffer();
		switch(arch)
		{
			case Win32: sb.append("ping -n "+anzahl+" "+host);break;
			case OsX:	sb.append("ping -c "+anzahl+" "+host);break;
			case Linux:	sb.append("ping -c "+anzahl+" "+host);break;
			default:	errorUnsupportedOS("ping -number target");break;
		}	
		return sb.toString();
	}
}
