package net.sf.exlp.util.io.compression;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;
import net.sf.exlp.test.AbstractExlpTst;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestZipExtractor extends AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(TestZipExtractor.class);
    
	private File f;
	
	@Before
	public void init() throws IOException
	{
		f = new File("src/test/resources/data/binary/zip.zip");
	}
	
    @Test
    public void testInit() throws IOException 
    {
    	Assert.assertTrue(f.isFile());
    	
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