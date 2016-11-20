package net.sf.exlp.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil
{
	final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper jom;
	
	public static synchronized void info(Object json)
	{
		if(logger.isInfoEnabled())
		{
			logger.info(getCaller());
			try {
				System.out.println(getJom().writeValueAsString(json));
			}
			catch (JsonProcessingException e) {logger.error(e.getMessage());}
		}
	}
	
	private static ObjectMapper getJom()
	{
		if(jom==null)
		{
			jom = new ObjectMapper();
			jom.enable(SerializationFeature.INDENT_OUTPUT);
		}
		return jom;
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
