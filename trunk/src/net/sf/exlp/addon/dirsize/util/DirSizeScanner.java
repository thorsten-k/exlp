package net.sf.exlp.addon.dirsize.util;

import java.io.File;
import java.util.Date;

import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectory;
import net.sf.exlp.addon.dirsize.data.ejb.ExlpDirectorySize;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirSizeScanner
{
	static Log logger = LogFactory.getLog(DirSizeScanner.class);
	
	public DirSizeScanner()
	{
	
	}
	
	public ExlpDirectorySize getDirSize(ExlpDirectory dir)
	{
		ExlpDirectorySize eds = new ExlpDirectorySize();
		eds.setRecord(new Date());
		eds.setDirectory(dir);
		eds.setSize(getSize(dir.getPath()));
		return eds;
	}
	
	private long getSize(String pathToDir)
	{
		File f = new File(pathToDir);
		return FileUtils.sizeOfDirectory(f);

	}
}
