package org.exlp.util.xpath;

import org.exlp.model.xml.io.Dir;
import org.exlp.model.xml.io.File;
import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.xml.xpath.IoXpath;

public class TestIoXpathFile extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestIoXpathFile.class);
	
	private static File f1,f2,f3,f4;
	
	@BeforeAll
	public static void initUrls()
	{
		f1 = new File();
    	f1.setCode("code1");
    	f1.setName("f1");
    	
    	f2 = new File();
    	f2.setCode("code2");
    	f2.setName("f1");
    	
    	f3 = new File();
    	f3.setCode("code3");
    	f3.setName("f3");
    	
    	f4 = new File();
    	f4.setCode("code3");
    	f4.setName("f4");
	}
    
	private Dir createDir()
    {
		Dir dir = new Dir();
    	dir.getFile().add(f1);
    	dir.getFile().add(f2);
    	dir.getFile().add(f3);
    	dir.getFile().add(f4);
    	return dir;
    }
	
    @Test
    public void testCode1() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	File file = IoXpath.getFile(dir, f1.getCode());
    	assertJaxbEquals(f1,file);
    }
    
    @Test
    public void testCode2() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	File file = IoXpath.getFile(dir, "code2");
    	super.assertJaxbEquals(f2,file);
    }

    @Disabled
    @Test //(expected=ExlpXpathNotFoundException.class)
    public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	IoXpath.getFile(dir, "code0");
    }
    
    @Disabled
    @Test //(expected=ExlpXpathNotUniqueException.class)
    public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	IoXpath.getFile(dir, "code3");
    }
}