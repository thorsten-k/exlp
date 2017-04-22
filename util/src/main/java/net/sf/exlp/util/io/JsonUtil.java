package net.sf.exlp.util.io;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil
{
	final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper jom() {return HelperDelegate.jom;}
	
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
			logger.info(getCaller());
			try {System.out.println(jom().writerWithDefaultPrettyPrinter().writeValueAsString(json));}
			catch (JsonProcessingException e) {logger.error(e.getMessage());}
		}
	}
	

	
	public static String toString(Object json) throws JsonProcessingException
	{
		return jom().writeValueAsString(json);
	}
	
	public static <T extends Object> T read(String s, Class<T> c) throws JsonParseException, JsonMappingException, IOException 
	{

//		try {
			return jom().readValue(s, c);
//		}
//		catch (JsonParseException e) {throw new UProcessingException(e.getMessage());}
//		catch (JsonMappingException e) {throw new JsonProcessingException(e.getMessage());}
//		catch (IOException e) {throw new JsonProcessingException(e.getMessage());}
	}
	
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
        }
	}
}
