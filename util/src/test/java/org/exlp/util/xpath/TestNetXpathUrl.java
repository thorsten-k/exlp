package org.exlp.util.xpath;

import java.util.ArrayList;
import java.util.List;

import org.exlp.model.xml.net.Url;
import org.exlp.model.xml.net.Urls;
import org.exlp.test.AbstractExlpTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.xml.xpath.NetXpath;

public class TestNetXpathUrl extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestNetXpathUrl.class);
	
	private static Url url1,url2,url3,url4;
	
	public TestNetXpathUrl()
	{

	}
	
	@BeforeAll
	public static void initUrls()
	{
		url1 = new Url();
    	url1.setCode("code1");
    	url1.setValue("http://url1");
    	
    	url2 = new Url();
    	url2.setCode("code2");
    	url2.setValue("http://url1");
    	
    	url3 = new Url();
    	url3.setCode("code3");
    	url3.setValue("http://url3");
    	
    	url4 = new Url();
    	url4.setCode("code3");
    	url4.setValue("http://url4");
	}
    
	private Urls createUrls()
    {
    	Urls urls = new Urls();
    	urls.getUrl().add(url1);
    	urls.getUrl().add(url2);
    	urls.getUrl().add(url3);
    	urls.getUrl().add(url4);
    	return urls;
    }
	
    @Test
    public void testCode1() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Urls urls = createUrls();
    	Url url = NetXpath.getUrl(urls, "code1");
    	assertJaxbEquals(url1,url);
    }
    
    @Test
    public void testCode2() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Urls urls = createUrls();
    	Url url = NetXpath.getUrl(urls, "code2");
    	assertJaxbEquals(url2,url);
    }

    @Disabled
    @Test //(expected=ExlpXpathNotFoundException.class)
    public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Urls urls = createUrls();
    	NetXpath.getUrl(urls, "code0");
    }
    
    @Disabled
    @Test //(expected=ExlpXpathNotUniqueException.class)
    public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	Urls urls = createUrls();
    	NetXpath.getUrl(urls, "code3");
    }
    
    @Test
    public void testList() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
    	List<Url> list = new ArrayList<Url>();
    	list.add(url1);
    	list.add(url2);
    	list.add(url3);
    	Urls urls = createUrls();
    	Url url = NetXpath.getUrl(urls, "code2");
    	assertJaxbEquals(url2,url);
    }
    
	public static void main(String[] args) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		TestNetXpathUrl test = new TestNetXpathUrl();
		TestNetXpathUrl.initUrls();
		test.testUnique();
    }
}