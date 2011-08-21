package net.sf.exlp.test.xml.xpath.io;

import net.sf.exlp.test.AbstractExlpTest;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;
import net.sf.exlp.xml.xpath.IoXpath;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestIoXpathFile extends AbstractExlpTest
{
	static Log logger = LogFactory.getLog(TestIoXpathFile.class);
	
	private static File url1,url2,url3,url4;
	
	public TestIoXpathFile()
	{

	}
	
	@BeforeClass
	public static void initUrls()
	{
		url1 = new File();
    	url1.setCode("code1");
    	url1.setName("f1");
    	
    	url2 = new File();
    	url2.setCode("code2");
    	url2.setName("f1");
    	
    	url3 = new File();
    	url3.setCode("code3");
    	url3.setName("f3");
    	
    	url4 = new File();
    	url4.setCode("code3");
    	url4.setName("f4");
	}
    
	private Dir createDir()
    {
		Dir dir = new Dir();
    	dir.getFile().add(url1);
    	dir.getFile().add(url2);
    	dir.getFile().add(url3);
    	dir.getFile().add(url4);
    	return dir;
    }
	
    @Test
    public void testCode1() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	File file = IoXpath.getFile(dir, url1.getCode());
    	assertJaxbEquals(url1,file);
    }
    
    @Test
    public void testCode2() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	File file = IoXpath.getFile(dir, "code2");
    	assertJaxbEquals(url2,file);
    }

    @Test(expected=ExlpXpathNotFoundException.class)
    public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	IoXpath.getFile(dir, "code0");
    }
    
    @Test(expected=ExlpXpathNotUniqueException.class)
    public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	IoXpath.getFile(dir, "code3");
    }
}