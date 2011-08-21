package net.sf.exlp.test.xml.xpath.io;

import net.sf.exlp.test.AbstractExlpTest;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.xpath.IoXpath;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestIoXpathDir extends AbstractExlpTest
{
	static Log logger = LogFactory.getLog(TestIoXpathDir.class);
	
	private static Dir d1,d2,d3,d4;
	
	@BeforeClass
	public static void initUrls()
	{
		d1 = new Dir();
    	d1.setCode("code1");
    	d1.setName("f1");
    	
    	d2 = new Dir();
    	d2.setCode("code2");
    	d2.setName("f2");
    	
    	d3 = new Dir();
    	d3.setCode("code3");
    	d3.setName("f3");
    	
    	d4 = new Dir();
    	d4.setCode("code3");
    	d4.setName("f4");
	}
    
	private Dir createDir()
    {
		Dir dir = new Dir();
    	dir.getDir().add(d1);
    	dir.getDir().add(d2);
    	dir.getDir().add(d3);
    	dir.getDir().add(d4);
    	return dir;
    }
	
    @Test
    public void testCode1() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	Dir test = IoXpath.getDir(dir, d1.getCode());
    	assertJaxbEquals(d1,test);
    }
    
    @Test
    public void testCode2() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Dir dir = createDir();
    	Dir test = IoXpath.getDir(dir, "code2");
    	assertJaxbEquals(d2,test);
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
    	IoXpath.getDir(dir, "code3");
    }
}