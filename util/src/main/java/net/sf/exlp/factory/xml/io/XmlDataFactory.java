package net.sf.exlp.factory.xml.io;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.exlp.model.xml.io.Data;

public class XmlDataFactory
{	
	public static Data build(byte[] bytes)
	{
		Data xml = new Data();
		xml.setValue(bytes);
		return xml;
	}
	
	public static Data build(InputStream is) throws IOException
	{
		Data xml = new Data();
		xml.setValue(IOUtils.toByteArray(is));
		return xml;
	}
}