package net.sf.exlp.util.io.compression;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.exlp.factory.xml.io.XmlDataFactory;
import net.sf.exlp.factory.xml.io.XmlFileFactory;

import org.apache.commons.io.IOUtils;
import org.exlp.model.xml.io.Dir;
import org.exlp.model.xml.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipExtractor
{
	final static Logger logger = LoggerFactory.getLogger(ZipExtractor.class);
	
	public ZipExtractor()
	{
		
	}
	
	public static synchronized Dir extract(java.io.File f, boolean withContent) throws IOException
	{
		return extract(new FileInputStream(f),withContent);
	}
	
	public static synchronized Dir extract(InputStream is, boolean withContent) throws FileNotFoundException, IOException
	{
		Dir xmlDir = new Dir();
		
		ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null)
        {
            File xmlFile = XmlFileFactory.build(entry.getName()); 

            byte[] bytes = IOUtils.toByteArray(zis);
            xmlFile.setSize(Integer.valueOf(bytes.length).longValue());
            if(withContent && bytes.length>0){xmlFile.setData(XmlDataFactory.build(bytes));}
            
            xmlDir.getFile().add(xmlFile);

        }
        return xmlDir;
	}
}
