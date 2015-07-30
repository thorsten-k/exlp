package net.sf.exlp.util.io;

import org.apache.commons.codec.EncoderException;
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
}