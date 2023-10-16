package org.exlp.util.io.dir;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.dir.RecursiveFileFinder;

public class TestRecursiveFileFinder extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestRecursiveFileFinder.class);
    
	private File f;
	
	@BeforeEach
	public void init() throws IOException
	{
		f = new File(fTarget,"exlpRecursivFileFinder");
		if(f.exists() && f.isFile()){f.delete();}
		if(f.exists() && f.isDirectory()){FileUtils.deleteDirectory(f);}
		Assertions.assertFalse(f.exists());
		f.mkdir();
		Assertions.assertTrue(f.exists());
		Assertions.assertTrue(f.isDirectory());
	}
	
    @Test
    public void find() throws IOException 
    {
    	File sub = new File(f,"sub");
    	sub.mkdir();
    	File f1 = new File(sub,"MyTest.java");
    	f1.createNewFile();
    	
    	RecursiveFileFinder finder = new RecursiveFileFinder(FileFilterUtils.suffixFileFilter(".java"));
    	List<File> actual = finder.find(f);
    	Assertions.assertEquals(1, actual.size());
    	Assertions.assertEquals(f1.getAbsolutePath(), actual.get(0).getAbsolutePath());
    }
    
    @Test
    public void findWithOthers() throws IOException 
    {
    	File sub = new File(f,"sub");
    	sub.mkdir();
    	File f1 = new File(sub,"MyTest.java");f1.createNewFile();
    	File f2 = new File(sub,"MyTest.java2");f2.createNewFile();
    	
    	RecursiveFileFinder finder = new RecursiveFileFinder(FileFilterUtils.suffixFileFilter(".java"));
    	List<File> actual = finder.find(f);
    	Assertions.assertEquals(1, actual.size());
    }
}