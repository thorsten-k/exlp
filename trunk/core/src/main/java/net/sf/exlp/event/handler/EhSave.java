package net.sf.exlp.event.handler;

import java.io.File;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.interfaces.LogEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhSave extends AbstractEventHandler 
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