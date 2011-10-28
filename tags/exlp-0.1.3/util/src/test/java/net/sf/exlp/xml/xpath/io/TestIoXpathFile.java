package net.sf.exlp.xml.xpath.io;

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
	
	private static File f1,f2,f3,f4;
	
	@BeforeClass
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
    	assertJaxbEquals(f2,file);
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