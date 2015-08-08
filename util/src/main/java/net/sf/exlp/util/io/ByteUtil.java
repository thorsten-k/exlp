package net.sf.exlp.util.io;

import java.nio.ByteBuffer;

import org.apache.commons.codec.net.URLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtil
{
	final static Logger logger = LoggerFactory.getLogger(ByteUtil.class);
	
	public static boolean[] toBooleanArray(byte[] bytes)
	{
		boolean[] bools = new boolean[bytes.length * 8];
		
		/* // Java 7 is required for this ...
	    BitSet bits = BitSet.valueOf(bytes);
	    boolean[] bools = new boolean[bytes.length * 8];
	    for (int i = bits.nextSetBit(0); i != -1; i = bits.nextSetBit(i+1))
	    {
	        bools[i] = true;
	    }
	    */
	    
		// Java 6 workaround
		for (int i = 0; i < bytes.length * 8; i++)
        {
            if ((bytes[i / 8] & (1 << (7 -(i % 8)))) > 0)
            {
            	bools[i] = true;
            }
        }
	    return bools;
	}
	
	public static String toUrlString(byte[] bytes)
	{
		URLCodec dec = new URLCodec();
		return new String(dec.encode(bytes));
	}
	
	public static byte[] concat(byte[]...arrays)
	{
	    int totalLength = 0;
	    for (int i = 0; i < arrays.length; i++)
	    {
	        totalLength += arrays[i].length;
	    }

	    byte[] result = new byte[totalLength];

	    int currentIndex = 0;
	    for (int i = 0; i < arrays.length; i++)
	    {
	        System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
	        currentIndex += arrays[i].length;
	    }

	    return result;
	}
	
	public static byte[] toByteArray(int i)
	{
		return ByteBuffer.allocate(4).putInt(i).array();
	}
	
	public static int toInt(byte[] bytes)
	{
		return ByteBuffer.wrap(bytes).getInt();
	}
	
	public static int toUnsignedInt(byte[] bytes)
	{
		if(bytes.length==1)
		{
			return bytes[0] & 0xFF;
		}
		if(bytes.length==2)
		{
			return ByteBuffer.wrap(bytes).getShort() & 0xffff;
		}
		else
		{
			logger.warn("UNTESTED");
			return ByteBuffer.wrap(bytes).getInt();
		}
		
	}
}