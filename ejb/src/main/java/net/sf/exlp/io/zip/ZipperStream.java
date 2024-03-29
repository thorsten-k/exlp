package net.sf.exlp.io.zip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipperStream
{
	final static Logger logger = LoggerFactory.getLogger(ZipperStream.class);
	
	private ByteArrayOutputStream resultOs;
	private ZipOutputStream zipOs;
	private MultiResourceLoader mrl;
	
	public ZipperStream()
	{
		resultOs = new ByteArrayOutputStream();
		zipOs = new ZipOutputStream(resultOs);
		mrl = MultiResourceLoader.instance();
	}
	
	public void add(String name, byte[] data)
	{
		try
		{
			zipOs.putNextEntry(new ZipEntry(name));
			zipOs.write(data);
		}
		catch (IOException e) {logger.error(""+e);}
	}
	
	public void addFile(String zipName, String fileName)
	{
		try
		{
			zipOs.putNextEntry(new ZipEntry(zipName));
			InputStream is = mrl.searchIs(fileName);
			IOUtils.copy(is, zipOs);
		}
		catch (FileNotFoundException e) {logger.error(""+e);}
		catch (IOException e) {logger.error(""+e);}
	}
	
	public OutputStream getZipStream()
	{
		try {zipOs.close();}
		catch (IOException e) {logger.error(""+e);}
		return resultOs;
	}
	
	public static void writeFile(String fileName,OutputStream os)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(new File(fileName));
			ByteArrayOutputStream baos = (ByteArrayOutputStream)os;
			fos.write(baos.toByteArray());
			fos.close();
		}
		catch (FileNotFoundException e) {logger.error(""+e);}
		catch (IOException e) {logger.error(""+e);}
	}
}
