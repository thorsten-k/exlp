package net.sf.exlp.factory.txt.io;

import net.sf.exlp.xml.io.File;

public class TxtFileTypeFactory
{
	public static void apply(File file)
	{
		String type = build(file.getName());
		if(type!=null){file.setSymbol(type);}
	}
	
	public static String build(String fileName)
	{
		if(fileName.endsWith(".doc") || fileName.endsWith(".docx")){return "doc";}
		else if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){return "xls";}
		else if(fileName.endsWith(".pdf")){return "pdf";}
		else if(fileName.endsWith(".csv")){return "csv";}
		else if(fileName.endsWith(".xml")){return "xml";}
		return null;
	}
}
