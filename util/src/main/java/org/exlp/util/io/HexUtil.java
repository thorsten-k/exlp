package org.exlp.util.io;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HexUtil
{
	final static Logger logger = LoggerFactory.getLogger(HexUtil.class);
	
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
}