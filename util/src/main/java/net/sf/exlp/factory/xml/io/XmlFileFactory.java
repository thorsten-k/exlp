package net.sf.exlp.factory.xml.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.zip.ZipEntry;

import org.apache.commons.io.IOUtils;
import org.exlp.model.xml.io.File;

import net.sf.exlp.util.DateUtil;

public class XmlFileFactory
{
	private File q;
	
	public XmlFileFactory(File q)
	{
		this.q=q;
	}
	
	public org.exlp.model.xml.io.File build(java.io.File f)
	{
		org.exlp.model.xml.io.File xml = new org.exlp.model.xml.io.File();
		if(Objects.nonNull(q.getName())) {xml.setName(f.getName());}
		if(Objects.nonNull(q.getSize())) {xml.setSize(f.length());}
		if(Objects.nonNull(q.getLastModifed())) {xml.setLastModifed(DateUtil.toXmlGc(new Date(f.lastModified())));}
		return xml;
	}
	
	public static File build()
	{
		return new File();
	}
	
	public static File copy(java.io.File f) throws FileNotFoundException, IOException
	{
		return build(f.getName(),IOUtils.toByteArray(new FileInputStream(f)));
	}
	
	public static File build(String name)
	{
		File xml = build();
		xml.setName(name);
		return xml;
	}
	
	public static File build(String name, byte[] bytes)
	{
		File xml = build();
		xml.setName(name);
		xml.setData(XmlDataFactory.build(bytes));
		return xml;
	}
	
	public static File build(byte[] bytes)
	{
		File xml = build();
		xml.setData(XmlDataFactory.build(bytes));
		return xml;
	}
	
	public static File build(ZipEntry zip)
	{
		File xml = build();
		xml.setName(zip.getName());
		return xml;
	}
}