package net.sf.exlp.util.io;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil
{
	final static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static String stars(){return stars(80);}
	public static String stars(int number)
    {
		return StringUtils.repeat("*", number);
    }
	
	public static String tab(int number)
	{
		return StringUtils.repeat("\t", number);
	}
	
	
	public static String dash2Dot(String text){return text.replaceAll("/", ".");}
	public static String dash2Camel(String text)
	{
		StringBuffer sb = new StringBuffer();
		for(String s : text.split("/"))
		{
			sb.append(s.substring(0,1).toUpperCase());
			sb.append(s.substring(1,s.length()));
		}
		return sb.toString();
	}
}