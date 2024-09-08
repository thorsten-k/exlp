package org.exlp.model.xml.io;

import java.time.LocalDateTime;
import java.util.Date;

import org.exlp.test.ExlpTestBootstrap;
import org.exlp.util.system.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFile extends AbstractIoXmlTest<File>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFile.class);
	
	public static TestXmlFile instance() {return new TestXmlFile();}
	private TestXmlFile() {super(File.class);}
    
    @Override public File build(boolean wChildren)
    {
    	Date d = DateUtil.toDate(LocalDateTime.of(2012,1,1,10,10,10));
    	
    	File xml = new File();
    	xml.setId(1l);
    	xml.setCode("code");
    	xml.setName("test.txt");
    	xml.setSize(123l);
    	xml.setMime("myMime");
    	xml.setSymbol("mySymbol");
    	xml.setCategory("myCategory");
    	xml.setLastModifed(DateUtil.toXmlGc(d));

    	if(wChildren)
    	{
    		xml.setData(TestXmlData.instance().build(false));
    		xml.getPolicy().add(TestPolicy.instance().build(false));
    		xml.getPolicy().add(TestPolicy.instance().build(false));
    		xml.setHash(TestXmlHash.instance().build(false));
    	}
    	
    	return xml;
    }
    
    public static void main(String[] args)
    {
		ExlpTestBootstrap.init();
		TestXmlFile.instance().saveReferenceXml();
    }
}