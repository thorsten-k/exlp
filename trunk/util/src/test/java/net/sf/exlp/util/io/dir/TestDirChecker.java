package net.sf.exlp.util.io.dir;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.test.AbstractExlpTst;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDirChecker extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestDirChecker.class);
    
	private File f;
	
	@Before
	public void init() throws IOException
	{
		f = new File(fTarget,"exlpDirChecker");
		if(f.exists() && f.isFile()){f.delete();}
		if(f.exists() && f.isDirectory()){FileUtils.deleteDirectory(f);}
		Assert.assertFalse(f.exists());
	}
	
    @Test
    public void testDirIsDir() throws ExlpConfigurationException
    {
    	f.mkdir();
    	Assert.assertTrue(DirChecker.isFileAnDirectory(f));
    }
    
    @Test
    public void testDirButIsFile() throws ExlpConfigurationException, IOException
    {
    	f.createNewFile();
    	Assert.assertFalse(DirChecker.isFileAnDirectory(f));
    }
    
    @Test
    public void testDirButDoesNotExist() throws ExlpConfigurationException, IOException
    {
    	Assert.assertFalse(DirChecker.isFileAnDirectory(f));
    }
    
    @Test
    public void testParentIsDir() throws ExlpConfigurationException, IOException
    {
    	f.mkdir();
    	File child = new File(f,"test");
    	child.createNewFile();
    	Assert.assertTrue(DirChecker.isParentAnDir(child));
    }
    
    @Test
    public void testParentButIsFile() throws ExlpConfigurationException, IOException
    {
    	f.createNewFile();
    	File child = new File(f,"test");
    	Assert.assertFalse(DirChecker.isParentAnDir(child));
    }
    
    @Test
    public void testParentDoesNotExist() throws ExlpConfigurationException, IOException
    {
    	File child = new File(f,"test");
    	Assert.assertFalse(DirChecker.isParentAnDir(child));
    }
}