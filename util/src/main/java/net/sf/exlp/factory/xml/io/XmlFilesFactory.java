package net.sf.exlp.factory.xml.io;

import java.util.List;

import org.exlp.model.xml.io.File;
import org.exlp.model.xml.io.Files;

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
	
	public static Files build(File file)
	{
		Files xml = build();
		if(file!=null){xml.getFile().add(file);}
		return xml;
	}
}