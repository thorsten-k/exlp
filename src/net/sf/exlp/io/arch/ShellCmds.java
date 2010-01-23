package net.sf.exlp.io.arch;

import net.sf.exlp.io.arch.ArchUtil.OsArch;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShellCmds
{
	static Log logger = LogFactory.getLog(ShellCmds.class);
	
	protected static OsArch arch;
	
	public static void setArch()
	{
		arch = ArchUtil.setArch();
	}
	
	protected static void errorUnsupportedOS(String cmd)
	{
		logger.fatal("System "+ SystemUtils.OS_NAME + " not supported");
		logger.fatal("We need to now the following command: "+cmd);
		logger.fatal("System will exit");
		System.exit(-1);
	}
	
	public static String routingTable()
	{
		if(arch==null){setArch();}
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
