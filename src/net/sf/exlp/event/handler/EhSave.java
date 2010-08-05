package net.sf.exlp.event.handler;

import java.io.File;

import net.sf.exlp.event.AbstractEventHandler;
import net.sf.exlp.event.LogEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EhSave extends AbstractEventHandler 
{
	static Log logger = LogFactory.getLog(EhSave.class);
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