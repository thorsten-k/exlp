package net.sf.exlp.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RecursiveFileFinder extends DirectoryWalker
{
	static Log logger = LogFactory.getLog(RecursiveFileFinder.class);
	
	public RecursiveFileFinder(IOFileFilter dirFilter, IOFileFilter fileFilter)
	{
		super(dirFilter, fileFilter, -1);
	}

	
	public RecursiveFileFinder()
	{
		
	}
	
	public List<File> find(File dir) throws IOException
	{
		List<File> results = new ArrayList<File>();
		walk(dir, results);
		return results;
	}

	@SuppressWarnings("unchecked")
	protected boolean handleDirectory(File dir, int depth, Collection results)
	{
	    return true;
	}

	@SuppressWarnings("unchecked")
	protected void handleFile(File file, int depth, Collection results)
	{
		results.add(file);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		IOFileFilter df = HiddenFileFilter.VISIBLE;
		IOFileFilter ff = FileFilterUtils.suffixFileFilter(".class");
	
		RecursiveFileFinder test = new RecursiveFileFinder(df,ff);
		List<File> l = test.find(new File("."));
		logger.debug("Size "+l.size());
		for(File f : l){logger.debug(f.getAbsolutePath());}
			
	}
}