package org.exlp.util.comparator.xml;

import java.util.Comparator;

import org.exlp.model.xml.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileComparator
{
	final static Logger logger = LoggerFactory.getLogger(FileComparator.class);
	
	public enum Type {Modification};
	
	public FileComparator()
	{
		
	}
	
	public static Comparator<File> factory(Type ct)
	{
		Comparator<File> c = null;
		FileComparator tsic = new FileComparator();
		switch (ct)
		{
			case Modification: c = tsic.new FileModificationComparator();break;
		}

		return c;
	}
	
	private class FileModificationComparator implements Comparator<File>
	{
		public int compare(File a, File b)
		{
		    int diff = a.getLastModifed().compare(b.getLastModifed());
		    if(diff==0)
		    {
		    	diff = a.getName().compareTo(b.getName());
		    }
		    return diff;
	    }
	}
}