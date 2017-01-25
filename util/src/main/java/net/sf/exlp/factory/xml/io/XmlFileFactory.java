package net.sf.exlp.factory.xml.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;

import org.apache.commons.io.IOUtils;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.xml.io.File;

public class XmlFileFactory
{
	private File q;
	
	public XmlFileFactory(File q)
	{
		this.q=q;
	}
	
	public net.sf.exlp.xml.io.File build(java.io.File f)
	{
		net.sf.exlp.xml.io.File file = new net.sf.exlp.xml.io.File();
		if(q.isSetName()){file.setName(f.getName());}
		if(q.isSetSize()){file.setSize(f.length());}
		if(q.isSetLastModifed()){file.setLastModifed(DateUtil.getXmlGc4D(new Date(f.lastModified())));}
		return file;
	}
	
	public static File copy(java.io.File f) throws FileNotFoundException, IOException
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
