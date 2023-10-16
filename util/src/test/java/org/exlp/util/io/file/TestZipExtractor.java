package org.exlp.util.io.file;

import java.io.File;
import java.io.IOException;

import org.exlp.model.xml.io.Dir;
import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.compression.ZipExtractor;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestZipExtractor extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestZipExtractor.class);
    
	private File f;
	
	@BeforeEach
	public void init() throws IOException
	{
		f = new File("src/test/resources/data/binary/zip.zip");
	}
	
    @Test
    public void testInit() throws IOException 
    {
    	Assertions.assertTrue(f.isFile());
    	
    }
   
    @Test
    public void testFileNotFound() throws IOException 
    {
    	Dir dir = ZipExtractor.extract(f,false);
    	JaxbUtil.info(dir);
    }
    
    @Test
    public void withContent() throws IOException 
    {
    	Dir dir = ZipExtractor.extract(f,true);
    	JaxbUtil.info(dir);
    }
}