package net.sf.exlp.io.arch;

import net.sf.exlp.io.spawn.Spawn;
import net.sf.exlp.util.os.ArchUtil;
import net.sf.exlp.util.os.ArchUtil.OsArch;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchOpen
{
	final static Logger logger = LoggerFactory.getLogger(ArchOpen.class);
	
	protected static OsArch arch;
	
	public static void setArch()
	{
		arch = ArchUtil.getArch();
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
