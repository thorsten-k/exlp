package net.sf.exlp.factory.xml.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;

import org.apache.commons.io.IOUtils;

import net.sf.exlp.xml.io.File;

public class XmlFileFactory
{
	public static File build(java.io.File f) throws FileNotFoundException, IOException
	{
		return build(f.getName(),IOUtils.toByteArray(new FileInputStream(f)));
	}
	
	public static File build(String name)
	{
		File xml = new File();
		xml.setName(name);
		return xml;
	}
	
	public static File build(String name, byte[] bytes)
	{
		File xml = new File();
		xml.setName(name);
		xml.setData(XmlDataFactory.build(bytes));
		return xml;
	}
	
	public static File build(byte[] bytes)
	{
		File xml = new File();
		xml.setData(XmlDataFactory.build(bytes));
		return xml;
	}
	
	public static File build(ZipEntry zip)
	{
		File xml = new File();
		xml.setName(zip.getName());
		return xml;
	}
}
