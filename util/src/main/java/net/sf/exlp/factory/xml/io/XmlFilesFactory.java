package net.sf.exlp.factory.xml.io;

import java.util.List;

import net.sf.exlp.xml.io.File;
import net.sf.exlp.xml.io.Files;

public class XmlFilesFactory
{
	public static Files build()
	{
		Files xml = new Files();

		return xml;
	}
	
	public static Files build(List<File> files)
	{
		Files xml = build();
		if(files!=null){xml.getFile().addAll(files);}
		return xml;
	}
}