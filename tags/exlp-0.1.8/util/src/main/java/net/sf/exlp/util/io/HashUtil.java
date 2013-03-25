package net.sf.exlp.util.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashUtil
{
	final static Logger logger = LoggerFactory.getLogger(HashUtil.class);
	
	private static String HASHALGORITHM = "MD5";
	private static int BLOCKLENGTH = 4096;
	
	public static String hash(String input){return hash(input.getBytes());}
	public static String hash(byte[] input)
    {
    	MessageDigest messagedigest=null;
    	try{ messagedigest = MessageDigest.getInstance(HASHALGORITHM);} 
    	catch (NoSuchAlgorithmException e){e.printStackTrace();}
    	
    	byte md[] = new byte[BLOCKLENGTH];  
    	try 
    	{
    		ByteArrayInputStream in  = new ByteArrayInputStream(input);
    		for ( int n = 0; (n = in.read(md)) > -1; )
    		{
    			messagedigest.update( md, 0, n );
    		}
    	}
    	catch(IOException e) {logger.error("",e);}
    	return new String(Hex.encodeHex(messagedigest.digest()));
    } 
}
