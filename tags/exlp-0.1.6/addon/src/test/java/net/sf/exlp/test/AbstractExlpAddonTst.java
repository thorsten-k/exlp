package net.sf.exlp.test;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractExlpAddonTst
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpAddonTst.class);
	
	private static NsPrefixMapperInterface nsPrefixMapper;
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/exlp-addon");
		loggerInit.init();
    }
	
	protected void assertJaxbEquals(Object ref, Object test)
	{
		Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
	}
	
	protected static XMLGregorianCalendar getXmlDate()
	{
		Date d = DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11);
		return DateUtil.getXmlGc4D(d);
	}
	
	protected static NsPrefixMapperInterface getNsPrefixMapper()
	{
		if(nsPrefixMapper==null){nsPrefixMapper = new ExlpNsPrefixMapper();}
		return nsPrefixMapper;
	}
}