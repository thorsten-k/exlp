package net.sf.exlp.util.io;

import org.apache.commons.lang3.StringUtils;
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
	
	public static String slash2Camel(String text) {return toCamel("/",text);}
	public static String dash2Camel(String text) {return toCamel("-",text);}
	
	private static String toCamel(String symbol, String text)
	{
		StringBuilder sb = new StringBuilder();
		for(String s : text.split(symbol))
		{
			sb.append(s.substring(0,1).toUpperCase());
			sb.append(s.substring(1,s.length()));
		}
		return sb.toString();
	}
	
	public static String insertAtNaturalPosition(String value, int oneBasedPosition, String insert)
	{
		StringBuffer sbCode = new StringBuffer();
		sbCode.append(value.substring(0,oneBasedPosition-1));
		sbCode.append(insert);
		sbCode.append(value.substring(oneBasedPosition-1,value.length()));
		return sbCode.toString();
	}
}