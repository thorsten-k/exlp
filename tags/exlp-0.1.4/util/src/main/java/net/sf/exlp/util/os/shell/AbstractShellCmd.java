package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.os.ArchUtil;
import net.sf.exlp.util.os.ArchUtil.OsArch;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public abstract class AbstractShellCmd
{
	protected final static Marker fatal = MarkerFactory.getMarker("FATAL");
	final static Logger logger = LoggerFactory.getLogger(AbstractShellCmd.class);
	
	protected OsArch arch;
	
	public AbstractShellCmd()
	{
		arch = ArchUtil.getArch();
	}
	
	protected static void errorUnsupportedOS(String cmd) throws ExlpUnsupportedOsException
	{
		logger.error(fatal,"System "+ SystemUtils.OS_NAME + " not supported");
		logger.error(fatal,"We need to now the following command: "+cmd);
		ExlpUnsupportedOsException e = new ExlpUnsupportedOsException("Command ("+cmd+") not supported for :"+SystemUtils.OS_NAME);
		e.printStackTrace();
		throw e;
	}
}
