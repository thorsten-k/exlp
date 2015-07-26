package net.sf.exlp.factory.xml.io;

import net.sf.exlp.xml.io.Data;

public class XmlDataFactory
{	
	public static Data build(byte[] bytes)
	{
		Data xml = new Data();
		xml.setValue(bytes);
		return xml;
	}
}
