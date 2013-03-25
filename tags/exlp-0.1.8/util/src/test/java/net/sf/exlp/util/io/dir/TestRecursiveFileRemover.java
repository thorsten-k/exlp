package net.sf.exlp.util.io.dir;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.test.AbstractExlpTst;
import net.sf.exlp.util.exception.ExlpConfigurationException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRecursiveFileRemover extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestRecursiveFileRemover.class);
    
	private File f;
	
	@Before
	public void init() throws IOException
	{
		f = new File(fTarget,"exlpRecursivFileRemover");
		if(f.exists() && f.isFile()){f.delete();}
		if(f.exists() && f.isDirectory()){FileUtils.deleteDirectory(f);}
		Assert.assertFalse(f.exists());
	}
	
    @Test
    public void testDirIsDir() throws ExlpConfigurationException
    {
    	
    }
}