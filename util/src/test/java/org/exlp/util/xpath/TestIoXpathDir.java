package org.exlp.util.xpath;

import org.exlp.model.xml.io.Dir;
import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.xml.xpath.IoXpath;

public class TestIoXpathDir extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestIoXpathDir.class);
	
	private static Dir d1,d2,d3,d4;
	
	@BeforeAll
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
    	IoXpath.getDir(dir, "code3");
    }
}