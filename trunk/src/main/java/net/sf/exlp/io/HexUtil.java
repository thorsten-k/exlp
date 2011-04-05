package net.sf.exlp.io;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HexUtil
{
	static Log logger = LogFactory.getLog(HexUtil.class);
	
	static final byte[] HEX_CHAR_TABLE = {
	    (byte)'0', (byte)'1', (byte)'2', (byte)'3',
	    (byte)'4', (byte)'5', (byte)'6', (byte)'7',
	    (byte)'8', (byte)'9', (byte)'a', (byte)'b',
	    (byte)'c', (byte)'d', (byte)'e', (byte)'f'
	  };    

	public static String getHexString(byte[] raw) throws UnsupportedEncodingException 
	{
		byte[] hex = new byte[2 * raw.length];
		int index = 0;

		for (byte b : raw)
		{
			int v = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[v >>> 4];
			hex[index++] = HEX_CHAR_TABLE[v & 0xF];
		}
		return new String(hex, "ASCII");
	}

	public static void main(String args[]) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
	    byte[] byteArray = {(byte)0, (byte)255, (byte)111};

	    System.out.println(HexUtil.getHexString(byteArray));
	}
}
