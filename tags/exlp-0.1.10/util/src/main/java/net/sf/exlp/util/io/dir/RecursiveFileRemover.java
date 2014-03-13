package net.sf.exlp.util.io.dir;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;

public class RecursiveFileRemover extends DirectoryWalker<File>
{
	private FileFilter filter;
	
	public RecursiveFileRemover(FileFilter filter)
    {
		super();
		this.filter=filter;
    }

	public List<File> clean(File startDirectory) throws IOException
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
    		file.delete();
    		results.add(file);
    	}
    }
}