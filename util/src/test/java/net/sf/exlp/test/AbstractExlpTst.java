package net.sf.exlp.test;

import java.io.File;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.ExlpNsPrefixMapper;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpTst.class);
	
	protected static File fTarget;
	
	@BeforeClass
	public static void initFile()
	{
		if(!LoggerInit.isLog4jInited()){initLogger();}
		String dirTarget = System.getProperty("targetDir");
		if(dirTarget==null){dirTarget="target";}
		setfTarget(new File(dirTarget));
		logger.debug("Using targeDir "+fTarget.getAbsolutePath());
	}
	protected static void setfTarget(File fTarget) {AbstractExlpTst.fTarget = fTarget;}
	
	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("exlp-util.test");
			loggerInit.init();
		}
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());
	}
	
	protected void assertJaxbEquals(Object ref, Object test)
	{
		Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
	}
	
	protected static XMLGregorianCalendar getXmlDate()
	{
		Date d = DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11);
		return DateUtil.toXmlGc(d);
	}
	
	protected void save(Object xml, File f)
	{
		logger.debug("Saving Reference XML");
		JaxbUtil.debug(xml);
    	JaxbUtil.save(f, xml, true);
	}
}