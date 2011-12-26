package net.sf.exlp.util.io.dir;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.exlp.test.AbstractExlpTst;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRecursiveFileFinder extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestRecursiveFileFinder.class);
    
	private File f;
	
	@Before
	public void init() throws IOException
	{
		f = new File(fTarget,"exlpRecursivFileFinder");
		if(f.exists() && f.isFile()){f.delete();}
		if(f.exists() && f.isDirectory()){FileUtils.deleteDirectory(f);}
		Assert.assertFalse(f.exists());
		f.mkdir();
		Assert.assertTrue(f.exists());
		Assert.assertTrue(f.isDirectory());
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
    	Assert.assertEquals(1, actual.size());
    	Assert.assertEquals(f1.getAbsolutePath(), actual.get(0).getAbsolutePath());
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
    	Assert.assertEquals(1, actual.size());
    }
}