package net.sf.exlp.util.io;

public class ByteUtil
{
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

}