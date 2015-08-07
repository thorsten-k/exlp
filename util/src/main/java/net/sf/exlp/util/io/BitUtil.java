package net.sf.exlp.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitUtil
{
	final static Logger logger = LoggerFactory.getLogger(BitUtil.class);
	
	public static String toString (byte[] bytes)
	{
		boolean[] bits = ByteUtil.toBooleanArray(bytes);
		
		StringBuffer sb = new StringBuffer();
        for(int i=0; i<bits.length; i++)
        {
           sb.append(bits[i] ? 1:0);
        }
        return sb.toString();
	}
	
}