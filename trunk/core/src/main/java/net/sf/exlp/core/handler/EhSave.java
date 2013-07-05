package net.sf.exlp.core.handler;

import java.io.File;
import java.io.Serializable;

import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhSave extends AbstractEventHandler implements Serializable,LogEventHandler
{
	final static Logger logger = LoggerFactory.getLogger(EhSave.class);
	
	static final long serialVersionUID=1;
	
	private File dir;
	
	public EhSave(File dir)
	{
		this.dir = dir;
		logger.debug("inited with baseDir="+dir.getAbsolutePath());
	}

	public boolean handleEvent(LogEvent le)
	{
		count();
		return le.save(dir);
	}
}