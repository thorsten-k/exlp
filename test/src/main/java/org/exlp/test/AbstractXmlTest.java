package org.exlp.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FilenameUtils;
import org.exlp.util.jx.JaxbUtil;
import org.exlp.util.system.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractXmlTest<T extends Object>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlTest.class);
	
	private File xmlFile;
	
	private Class<T> cJaxb;
	
	public AbstractXmlTest(Class<T> cXml, Path pPrefix, Path pSuffix)
	{
		this.cJaxb=cXml;
		if(Objects.nonNull(cXml))
		{
			try
			{
				File f = new File(".");
		        String s = FilenameUtils.normalizeNoEndSeparator(f.getAbsolutePath());

		        Path path = Paths.get(s, "src","test","resources");
		        path = path.resolve(pPrefix);
		        if(Objects.nonNull(pSuffix)) {path = path.resolve(pSuffix);}
		       
		        path = path.resolve(Paths.get(cJaxb.getSimpleName()+".xml"));
		        logger.info("Path: "+path.toString());

		        xmlFile = path.toFile();
			}
			catch (IllegalArgumentException e) {e.printStackTrace();}
			catch (SecurityException e) {e.printStackTrace();}
		}
	}
	
	protected static XMLGregorianCalendar getDefaultXmlDate()
	{
		LocalDateTime ldt = LocalDateTime.of(2011,11,11,11,11,11);
		return DateUtil.toXmlGc(ldt);
	}
	
	protected void saveReferenceXml()
	{
		Object xml = this.build(true);
		logger.debug("Saving Reference XML "+xmlFile.getAbsolutePath());
		JaxbUtil.info(xml);
    	JaxbUtil.save(xmlFile,xml,true);
	}
    
    //TODO declare as abstract
    protected T build(boolean withChilds){return null;}
}