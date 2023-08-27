package net.sf.exlp.factory.xml.io;

import org.exlp.model.xml.io.Hash;

public class XmlHashFactory
{
	public static Hash build(String hashValue)
	{
		Hash xml = new Hash();
		xml.setValue(hashValue);
		return xml;
	}
}
