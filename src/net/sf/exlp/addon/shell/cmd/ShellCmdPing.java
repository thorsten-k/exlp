package net.sf.exlp.addon.shell.cmd;

import net.sf.exlp.io.arch.ShellCmds;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShellCmdPing extends ShellCmds
{
	static Log logger = LogFactory.getLog(ShellCmdPing.class);
	
	public static String ping(String host, int anzahl)
	{
		if(arch==null){setArch();}
		StringBuffer sb = new StringBuffer();
		switch(arch)
		{
			case Win32: sb.append("ping -n "+anzahl+" ");
						sb.append(host);break;
			case OsX:	sb.append("ping -c "+anzahl+" ");
						sb.append(host);break;
			default:	errorUnsupportedOS("ping -number target");break;
		}	
		return sb.toString();
	}
}
