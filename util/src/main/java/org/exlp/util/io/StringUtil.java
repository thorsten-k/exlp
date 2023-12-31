package org.exlp.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
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
	
	public static void writeFile(File f, String txt)
	{
		logger.trace("Writing Txt to "+f.getAbsolutePath());
		try
		{
			OutputStream os = new FileOutputStream(f);
			IOUtils.write(txt, os, "UTF-8");
			os.flush();
			os.close();
		}
		catch (IOException e) {logger.error("",e);}
	}
	
	public static String readFile(File f)
	{
		logger.trace("Reading Txt from "+f.getAbsolutePath());
		try
		{
			InputStream is = new FileInputStream(f);
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF-8");
			String theString = writer.toString();
			return theString;
		}
		catch (FileNotFoundException e) {logger.error(e.getMessage());}
		catch (IOException e) {logger.error(e.getMessage());}
		return null;
	}

}