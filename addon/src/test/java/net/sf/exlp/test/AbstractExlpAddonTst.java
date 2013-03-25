package net.sf.exlp.test;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractExlpAddonTst
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpAddonTst.class);
		
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("exlp-addon.test");
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
}