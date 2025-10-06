package org.exlp.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsUtil
{
	final static Logger logger = LoggerFactory.getLogger(JsUtil.class);
	
	private static final String magicStart = "@@----";
	private static final String magicEnd = "----@@";
	
	public static String magicField(boolean withMagic, String text)
	{
		if(!withMagic) {return text;}
		StringBuilder sb = new StringBuilder();
		sb.append(magicStart);
		sb.append(text);
		sb.append(magicEnd);
		return sb.toString();
	}
	public static String magicField(String text)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(magicStart);
		sb.append(text);
		sb.append(magicEnd);
		return sb.toString();
	}
	
	public static String unQuote(String text)
	{
		text = text.replaceAll("\""+magicStart,"");
		text = text.replace(magicEnd+"\"","");
		return text;
	}
}
