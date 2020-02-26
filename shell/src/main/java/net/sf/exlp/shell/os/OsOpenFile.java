package net.sf.exlp.shell.os;

import net.sf.exlp.exception.UnknownArchitectureException;
import net.sf.exlp.shell.os.OsArchitectureUtil.OsArch;
import net.sf.exlp.shell.spawn.Spawn;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OsOpenFile
{
	final static Logger logger = LoggerFactory.getLogger(OsOpenFile.class);
	
	protected static OsArch arch;
	
	public static void setArch()
	{
		arch = OsArchitectureUtil.getArch();
	}
	
	public static void open(String filePath)
	{
		if(arch==null){setArch();}
		StringBuffer sb = new StringBuffer();
		try
		{
			switch(arch)
			{
				case Win32: sb.append("rundll32 url.dll,FileProtocolHandler "+"\""+filePath+"\"");break;
				case OsX:	sb.append("open "+filePath);break;
				default:	throw new UnknownArchitectureException();
			}
			Spawn spawn = new Spawn(sb.toString());
			spawn.cmd();
			logger.trace(spawn.getExitValue()+" "+sb.toString());
		}
		catch (UnknownArchitectureException e)
		{
			logger.warn("We want to open the file "+filePath);
			logger.warn("But don't know how "+SystemUtils.OS_NAME+" handles this.");
		}
	}
}
