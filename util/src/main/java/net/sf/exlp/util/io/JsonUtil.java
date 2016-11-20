package net.sf.exlp.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil
{
	final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper jom = new ObjectMapper();;
	
	public static synchronized void info(Object json)
	{
		if(logger.isInfoEnabled())
		{
			logger.info(getCaller());
			try {
				System.out.println(jom.writerWithDefaultPrettyPrinter().writeValueAsString(json));
			}
			catch (JsonProcessingException e) {logger.error(e.getMessage());}
		}
	}
	
	public static String toString(Object json) throws JsonProcessingException
	{
		return jom.writeValueAsString(json);
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
}
