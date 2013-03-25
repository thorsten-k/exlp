package net.sf.exlp.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIO
{
	final static Logger logger = LoggerFactory.getLogger(FileIO.class);
	
	private static String HASHALGORITHM = "MD5";
	private static int BLOCKLENGTH = 4096;
	
	public static String getHash(File f) throws IOException
    {
    	MessageDigest messagedigest=null;
    	try{ messagedigest = MessageDigest.getInstance(HASHALGORITHM);} 
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
}
