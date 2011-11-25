package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellCmdPing extends AbstractShellCmd
{
	final static Logger logger = LoggerFactory.getLogger(ShellCmdPing.class);
	
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
