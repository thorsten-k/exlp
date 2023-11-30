package org.exlp.util.io;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil
{
	final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	public static ObjectMapper jom() {return HelperDelegate.jom;}
	
	private ObjectMapper jom;
	
	private static boolean logCaller = true;
	
	public static JsonUtil instance() {return new JsonUtil();}
	private JsonUtil()
	{
		jom = new ObjectMapper();
    	jom.setSerializationInclusion(Include.NON_NULL);
    	jom.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

	}
	
	public String toFormattedString(Object json)
	{
		try
		{
			String s = jom.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			return s.replace("\r\n","\n");
		}
		catch (JsonProcessingException e) {e.printStackTrace(); return e.getLocalizedMessage();}
	}
	
	public String toCompactString(Object json)
	{
		try
		{
			return jom.writeValueAsString(json);
		}
		catch (JsonProcessingException e) {e.printStackTrace(); return e.getLocalizedMessage();}
	}
	
	public static void deactivateCaller() {logCaller=false;}
	
	public static void trace(Object json)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace(getCaller());
			try {System.out.println(jom().writerWithDefaultPrettyPrinter().writeValueAsString(json));}
			catch (JsonProcessingException e) {logger.error(e.getMessage());}
		}
	}
	
	public static void debug(Object json)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug(getCaller());
			try {System.out.println(jom().writerWithDefaultPrettyPrinter().writeValueAsString(json));}
			catch (JsonProcessingException e) {logger.error(e.getMessage());}
		}
	}
	
	public static void info(Object json)
	{
		if(logger.isInfoEnabled())
		{
			if(logCaller) {logger.info(getCaller());}
			try {System.out.println(jom().writerWithDefaultPrettyPrinter().writeValueAsString(json));}
			catch (JsonProcessingException e) {logger.error(e.getMessage());}
		}
	}

	public static String toStringSilent(Object json)
	{
		try {
			return toString(json, false);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String toString(Object json) throws JsonProcessingException
	{
		return toString(json, false);
	}
	
	public static String toString(Object json, Boolean prettyPrint) throws JsonProcessingException
	{
		if (prettyPrint)
		{
			return jom().writerWithDefaultPrettyPrinter().writeValueAsString(json);
		}
		else
		{
			return jom().writeValueAsString(json);
		}
	}
	
	public static String toPrettyString(Object json) throws JsonProcessingException
	{
		return toString(json, true);
	}
	
	public static byte[] toBytes(Object json) throws JsonProcessingException
	{
		return jom().writeValueAsBytes(json);
	}
	
//	@Deprecated
//	private static <T extends Object> T read(String s, Class<T> c) throws JsonParseException, JsonMappingException, IOException 
//	{
//		return jom().readValue(s, c);
//	}
	
	public static <T extends Object> T read(Class<T> c, String s) throws JsonParseException, JsonMappingException, IOException 
	{
		return jom().readValue(s, c);
	}
	
	public static <T extends Object> T read(Class<T> c, byte[] bytes) throws JsonParseException, JsonMappingException, IOException 
	{
		return jom().readValue(bytes, c);
	}
	
	public static void write(Object json, File f) throws JsonGenerationException, JsonMappingException, IOException
	{
		ObjectWriter writer = jom().writer(new DefaultPrettyPrinter());
		writer.writeValue(f, json);
	}
	
	public static <T extends Object> T read(Class<T> c, File f) throws JsonGenerationException, JsonMappingException, IOException
	{
		return jom().readValue(f, c);
	}
	
//	public static <T extends Object> List<T> readList(Class[]<T> c, File f) throws JsonGenerationException, JsonMappingException, IOException
//	{
//		return Arrays.asList(jom().readValue(f, MyClass[].class))
//	}
	
	private static synchronized String getCaller()
	{
		int index;
		logger.trace("StackTraceSize"+Thread.currentThread().getStackTrace().length);
		StackTraceElement[] steList = Thread.currentThread().getStackTrace();
		if(steList.length==4){index=3;}
		else{index=4;}
		
		StackTraceElement ste = Thread.currentThread().getStackTrace()[index];
		
		StringBuffer sb = new StringBuffer();
		sb.append("Output invoked by: ");
		sb.append(ste.getClassName());
		sb.append(".").append(ste.getMethodName());
		sb.append("()");
		sb.append("-").append(ste.getLineNumber());
		sb.append(" (").append(ste.getFileName()).append(")");
		return sb.toString();
	}
	
	private static class HelperDelegate
	{ 
        private static ObjectMapper jom = new ObjectMapper();
        static
        {
        	jom.setSerializationInclusion(Include.NON_NULL);
        	jom.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        }
	}
}
