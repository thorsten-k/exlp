package net.sf.exlp.factory.xml.io;

import java.util.zip.ZipEntry;

import net.sf.exlp.xml.io.File;

public class XmlFileFactory
{
	public static File build(String name)
	{
		File xml = new File();
		xml.setName(name);
		return xml;
	}
	
	public static File build(ZipEntry zip)
	{
		File xml = new File();
		xml.setName(zip.getName());
		return xml;
	}
}
