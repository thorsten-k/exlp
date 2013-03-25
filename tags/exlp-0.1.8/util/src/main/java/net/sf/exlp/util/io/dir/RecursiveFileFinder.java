package net.sf.exlp.util.io.dir;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecursiveFileFinder extends DirectoryWalker<File>
{
	final static Logger logger = LoggerFactory.getLogger(RecursiveFileFinder.class);
	
	private FileFilter filter;
	
	public RecursiveFileFinder(FileFilter filter)
    {
		super();
		this.filter=filter;
    }

	public List<File> find(File startDirectory) throws IOException
	{
		List<File> results = new ArrayList<File>();
		walk(startDirectory, results);
		return results;
    }
	
    protected boolean handleDirectory(File directory, int depth, Collection<File> results)
    {
    	return true;
    }

    protected void handleFile(File file, int depth, Collection<File> results)
    {
    	if(filter.accept(file))
    	{
    		results.add(file);
    	}
    }
}