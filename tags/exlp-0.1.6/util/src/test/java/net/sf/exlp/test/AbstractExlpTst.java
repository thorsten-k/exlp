package net.sf.exlp.test;

import java.io.File;
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

public class AbstractExlpTst
{
	final static Logger logger = LoggerFactory.getLogger(AbstractExlpTst.class);
	
	private static NsPrefixMapperInterface nsPrefixMapper;
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
	
	protected void save(Object xml, File f)
	{
		logger.debug("Saving Reference XML");
		JaxbUtil.debug2(this.getClass(),xml, getNsPrefixMapper());
    	JaxbUtil.save(f, xml, getNsPrefixMapper(), true);
	}
}