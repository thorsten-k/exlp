package net.sf.exlp.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.log4j.Logger;

public class RecursiveFileFinder extends DirectoryWalker
{
	private static Logger logger = Logger.getLogger(RecursiveFileFinder.class);
	
	public RecursiveFileFinder(FileFilter filter)
	{
		super(filter, -1);
	}

	
	public RecursiveFileFinder()
	{
		
	}
	
	public List<File> find(File startDirectory) throws IOException
	{
		List<File> results = new ArrayList<File>();
		walk(startDirectory, results);
		return results;
	}

	@SuppressWarnings("unchecked")
	protected boolean handleDirectory(File directory, int depth, Collection results)
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
		
		RecursiveFileFinder test = new RecursiveFileFinder(new SuffixFileFilter(".java"));
		List<File> l = test.find(new File("."));
		logger.debug("Size "+l.size());
	}
}