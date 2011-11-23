package net.sf.exlp.util.os.shell;

import net.sf.exlp.util.exception.ExlpUnsupportedOsException;
import net.sf.exlp.util.os.ArchUtil;
import net.sf.exlp.util.os.ArchUtil.OsArch;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractShellCmd
{
	static Log logger = LogFactory.getLog(AbstractShellCmd.class);
	
	protected OsArch arch;
	
	public AbstractShellCmd()
	{
		arch = ArchUtil.setArch();
	}
	
	protected void errorUnsupportedOS(String cmd) throws ExlpUnsupportedOsException
	{
		logger.fatal("System "+ SystemUtils.OS_NAME + " not supported");
		logger.fatal("We need to now the following command: "+cmd);
		ExlpUnsupportedOsException e = new ExlpUnsupportedOsException("Command ("+cmd+") not supported for :"+SystemUtils.OS_NAME);
		e.printStackTrace();
		throw e;
	}
}
