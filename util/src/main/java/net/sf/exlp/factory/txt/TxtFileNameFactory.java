package net.sf.exlp.factory.txt;

import java.io.File;

public class TxtFileNameFactory
{
	public static String build(String prefix, String packageName)
	{
		String[] pack = packageName.split("\\.");
    	StringBuffer sb = new StringBuffer();
    	sb.append(prefix).append(File.separator);
    	for(String s : pack)
    	{
    		sb.append(s).append(File.separator);
    	}
    	return sb.substring(0,sb.length()-1);
	}
	
	public static String build(String prefix, String packageName, String fileSuffix)
	{
		String[] pack = packageName.split("\\.");
    	StringBuffer sb = new StringBuffer();
    	sb.append(prefix).append(File.separator);
    	for(String s : pack)
    	{
    		sb.append(s).append(File.separator);
    	}
    	return sb.substring(0,sb.length()-1)+"."+fileSuffix;
	}
}
