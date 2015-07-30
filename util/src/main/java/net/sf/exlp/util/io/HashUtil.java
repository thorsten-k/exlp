package net.sf.exlp.util.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashUtil
{
	final static Logger logger = LoggerFactory.getLogger(HashUtil.class);
	
	private static String AlgorithmMD5 = "MD5";
	private static String AlgorithmSHA1 = "SHA-1";
	
	private static int BLOCKLENGTH = 4096;
	
	public static String hash(String input){return hash(input.getBytes());}
	public static String hash(byte[] input)
    {
    	MessageDigest messagedigest=null;
    	try{ messagedigest = MessageDigest.getInstance(AlgorithmMD5);} 
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
	
	public static String hash(File f) throws IOException
    {
    	MessageDigest messagedigest=null;
    	try{messagedigest = MessageDigest.getInstance(AlgorithmMD5);} 
    	catch (NoSuchAlgorithmException e){e.printStackTrace();}
    	
    	byte md[] = new byte[BLOCKLENGTH];  

		FileInputStream in  = new FileInputStream(f);
		for(int n=0; (n = in.read(md))>-1;)
		{
			messagedigest.update(md,0,n);
		}
		in.close();

    	return new String(Hex.encodeHex(messagedigest.digest()));
    }
	
	public static byte[] sha1Byte(byte[] input)
    {
    	MessageDigest messagedigest=null;
    	try{ messagedigest = MessageDigest.getInstance(AlgorithmSHA1);} 
    	catch (NoSuchAlgorithmException e){e.printStackTrace();}
    	
    	byte md[] = new byte[BLOCKLENGTH];  
    	try 
    	{
    		ByteArrayInputStream in  = new ByteArrayInputStream(input);
    		for(int n=0; (n=in.read(md))>-1;)
    		{
    			messagedigest.update(md, 0, n);
    		}
    	}
    	catch(IOException e) {logger.error("",e);}
    	return messagedigest.digest();
    }
}
