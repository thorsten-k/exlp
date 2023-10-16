package org.exlp.util.io.dir;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.dir.DirChecker;

public class TestDirChecker extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestDirChecker.class);
    
	private File f;
	
	@BeforeEach
	public void init() throws IOException
	{
		f = new File(fTarget,"exlpDirChecker");
		if(f.exists() && f.isFile()){f.delete();}
		if(f.exists() && f.isDirectory()){FileUtils.deleteDirectory(f);}
		Assertions.assertFalse(f.exists());
	}
	
    @Test
    public void testDirIsDir() throws ExlpConfigurationException
    {
    	f.mkdir();
    	Assertions.assertTrue(DirChecker.isFileAnDirectory(f));
    }
    
    @Test
    public void testDirButIsFile() throws ExlpConfigurationException, IOException
    {
    	f.createNewFile();
    	Assertions.assertFalse(DirChecker.isFileAnDirectory(f));
    }
    
    @Test
    public void testDirButDoesNotExist() throws ExlpConfigurationException, IOException
    {
    	Assertions.assertFalse(DirChecker.isFileAnDirectory(f));
    }
    
    @Test
    public void testParentIsDir() throws ExlpConfigurationException, IOException
    {
    	f.mkdir();
    	File child = new File(f,"test");
    	child.createNewFile();
    	Assertions.assertTrue(DirChecker.isParentAnDir(child));
    }
    
    @Test
    public void testParentButIsFile() throws ExlpConfigurationException, IOException
    {
    	f.createNewFile();
    	File child = new File(f,"test");
    	Assertions.assertFalse(DirChecker.isParentAnDir(child));
    }
    
    @Test
    public void testParentDoesNotExist() throws ExlpConfigurationException, IOException
    {
    	File child = new File(f,"test");
    	Assertions.assertFalse(DirChecker.isParentAnDir(child));
    }
}