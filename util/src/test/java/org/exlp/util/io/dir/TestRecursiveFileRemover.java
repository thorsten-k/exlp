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

public class TestRecursiveFileRemover extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestRecursiveFileRemover.class);
    
	private File f;
	
	@BeforeEach
	public void init() throws IOException
	{
		f = new File(fTarget,"exlpRecursivFileRemover");
		if(f.exists() && f.isFile()){f.delete();}
		if(f.exists() && f.isDirectory()){FileUtils.deleteDirectory(f);}
		Assertions.assertFalse(f.exists());
	}
	
    @Test
    public void testDirIsDir() throws ExlpConfigurationException
    {
    	
    }
}