package net.sf.exlp.factory.txt;

import org.apache.commons.lang.SystemUtils;

public class TxtFileNameFactory
{
	public static String build(String prefix, String packageName)
	{
		String[] pack = packageName.split("\\.");
    	StringBuffer sb = new StringBuffer();
    	sb.append(prefix).append(SystemUtils.FILE_SEPARATOR);
    	for(String s : pack)
    	{
    		sb.append(s).append(SystemUtils.FILE_SEPARATOR);
    	}
    	return sb.substring(0,sb.length()-1);
	}
	
	public static String build(String prefix, String packageName, String fileSuffix)
	{
		String[] pack = packageName.split("\\.");
    	StringBuffer sb = new StringBuffer();
    	sb.append(prefix).append(SystemUtils.FILE_SEPARATOR);
    	for(String s : pack)
    	{
    		sb.append(s).append(SystemUtils.FILE_SEPARATOR);
    	}
    	return sb.substring(0,sb.length()-1)+"."+fileSuffix;
	}
}
