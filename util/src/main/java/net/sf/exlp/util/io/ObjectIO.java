package net.sf.exlp.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectIO
{
	final static Logger logger = LoggerFactory.getLogger(ObjectIO.class);
	
	private static String HASHALGORITHM = "MD5";
	private static int BLOCKLENGTH = 4096;
	
	public ObjectIO()
	{
		
	}
	
	public static byte[] hash(Serializable s)
    {
    	byte[] bHashFile;
    	bHashFile = new byte[128];
    	MessageDigest messagedigest=null;
    	
    	try{ messagedigest = MessageDigest.getInstance(HASHALGORITHM);} 
    	catch (NoSuchAlgorithmException e){e.printStackTrace();}
    	
	    try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out     = new ObjectOutputStream(baos);
			out.writeObject(s);
			out.flush();
			byte [] b = baos.toByteArray();
			baos.close();
			out.close();		   	

	    	logger.trace("Anz. bytes: " + b.length);

	    	for ( int i = 0; (i*BLOCKLENGTH < BLOCKLENGTH); i++)
			{
	           if ( (b.length -i*BLOCKLENGTH) > BLOCKLENGTH)   	  
	        	  messagedigest.update( b, i*BLOCKLENGTH, BLOCKLENGTH );
	           else
	        	  messagedigest.update( b, i*BLOCKLENGTH, b.length-i*BLOCKLENGTH );
	        }
		}
		catch (IOException e1) {e1.printStackTrace();}
    	
 
    	bHashFile = messagedigest.digest();
    	logger.debug(new String(bHashFile));
    	return bHashFile;
    }
	
	public static byte[] getHash(String s)
    {
    	
    	MessageDigest messagedigest=null;
    	try{ messagedigest = MessageDigest.getInstance(HASHALGORITHM);} 
    	catch (NoSuchAlgorithmException e){e.printStackTrace();}
    	
    	byte md[] = new byte[BLOCKLENGTH];  
    	try 
    	{
    		ByteArrayInputStream in  = new ByteArrayInputStream(s.getBytes());
    		for ( int n = 0; (n = in.read(md)) > -1; )
    			{messagedigest.update( md, 0, n );}
    	}
    	catch(IOException e) {logger.error("",e);}
    	byte[] bHashFile = new byte[128];
    	bHashFile = messagedigest.digest();
    	return bHashFile;
    } 
	
	public Object socketChallengeResponse(Object out, String serverIP, int serverPort, boolean debug)
	{
		Object incommingObject = null;

		StringBuffer sb = new StringBuffer();
		sb.append("Socket ("+serverIP+":"+serverPort+"):");
        try
        {
	        	Socket s = new Socket(serverIP, serverPort);
	        	ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				oos.writeObject(out);
	        	oos.flush();
	        	incommingObject = ois.readObject();
	        	oos.close();ois.close();s.close();
        }
        catch (ConnectException e){sb.append(" ConnectException");}
        catch (SocketException e){sb.append(" SocketException");}
        catch (UnknownHostException e){sb.append(" UnknownHostException");}
        catch (IOException e){sb.append(" IOException");}
        catch (ClassNotFoundException e){sb.append(" ClassNotFoundException");}
        if(debug){logger.debug(debug+" "+sb);}
		return incommingObject;
	}
	
	public static Object copy(Serializable o)
	{
		Object deepCopy=null;
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(o);
			oos.close();

			byte[] buffer = bos.toByteArray();
			
			ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
			ObjectInputStream ois = new ObjectInputStream(bis);
			
			deepCopy = (Object)ois.readObject();
		}
		catch (IOException e) {e.printStackTrace();}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		return deepCopy;
	}
	
	public static int getSize(Serializable o)
	{
		byte[] buffer=null;
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(o);
			oos.close();

			buffer = bos.toByteArray();
		}
		catch (IOException e) {e.printStackTrace();}
		return buffer.length;
	}
	
	@Deprecated
	public static byte[] loadByte(File f)
	{
		return FileIO.loadByte(f);
	}
	
	public synchronized static void saveByte(byte[] b,File f)
	{
		try
	    {
	    	FileOutputStream fos = new FileOutputStream(f);
	    	ByteArrayOutputStream out = new ByteArrayOutputStream();
	    	out.write(b, 0, b.length);
	    	out.writeTo(fos);
	    	fos.flush();
	    	fos.close();
	    }
	    catch( IOException e ){e.printStackTrace();}
	}
	
	public static boolean save(String filePath,Object o)
	{
		boolean success=false;
		try
		{
			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(o);
			oos.close();
			fos.close();
			success=true;
		}
		catch (IOException e) {logger.error("",e);}
		return success;
	}
	
	public static Object load(File f)
	{
		Object o=null;
		try
	    {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			o = ois.readObject();
			ois.close();
			fis.close();
	    }
		catch (EOFException e) {logger.error(e.getMessage());}
		catch (IOException e) {e.printStackTrace();}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		
		return o;
	}
	
	public static Object load(String fileName)
	{
		File f = new File(fileName);
		return load(f);
	}
		
	public static boolean extractZip(File archive, File destDir)
	{
		if (!destDir.exists()) {destDir.mkdir();}
		try
		{
			ZipFile zipFile = new ZipFile(archive);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			byte[] buffer = new byte[16384];
			int len;
			while (entries.hasMoreElements())
			{
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String entryFileName = entry.getName();
				logger.debug("Entpacke: "+entryFileName);
				File dir = buildDirectoryHierarchyFor(entryFileName, destDir);
				if (!dir.exists()) {dir.mkdirs();}

				if (!entry.isDirectory())
				{
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(new File(destDir, entryFileName)));

					BufferedInputStream bis = new BufferedInputStream(zipFile
							.getInputStream(entry));

					while ((len = bis.read(buffer)) > 0)
					{
						bos.write(buffer, 0, len);
					}

					bos.flush();
					bos.close();
					bis.close();
				}
			}
		}
		catch (ZipException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}	
		return true;
	}
	
	private static File buildDirectoryHierarchyFor(String entryName, File destDir)
	{
		int lastIndex = entryName.lastIndexOf('/');
		String internalPathToEntry = entryName.substring(0, lastIndex + 1);
		return new File(destDir, internalPathToEntry);
	}
	
	public static void addFileToZip(File dir,ZipOutputStream zos,String prefix)
	{
		byte[] buffer = new byte[8192];
		int len = 0;
		
		for(File f : dir.listFiles())
		{
			logger.debug("Zippe: "+f.getAbsolutePath()+ " prefix="+prefix);
			if(f.isDirectory())
			{
				addFileToZip(f,zos,prefix+f.getName()+"/");
			}
			else
			{
	 			try
	 			{
	 				ZipEntry entry = new ZipEntry(prefix+f.getName());
					zos.putNextEntry(entry);
					FileInputStream fis = new FileInputStream(f);
					while ((len = fis.read(buffer)) > 0)
					{
						zos.write(buffer, 0, len);
					}
					fis.close();
				}
	 			catch (FileNotFoundException e) {e.printStackTrace();}
	 			catch (IOException e) {e.printStackTrace();}
			}
		}
	}
	
	public static void streamCop(InputStream fromIs, OutputStream toOs)
	{
		try
		{
			byte[] buffer = new byte[ 0xFFFF ];
			for ( int len; (len = fromIs.read(buffer)) != -1; )
			{
				toOs.write( buffer, 0, len );
			}
		}
	    catch( IOException e ) {logger.error("",e);}
	    finally
	    {
	      if ( fromIs != null )
	        try { fromIs.close(); } catch ( IOException e ) {logger.error("",e);}
	      if ( toOs != null )
	        try { toOs.close(); } catch ( IOException e ) {logger.error("",e);}
	    }
	  }
	
	public static void writeToFile(String fileName, InputStream iStream) throws IOException
	{
	    File f = new File(fileName);

	    BufferedOutputStream bos = null;
	    try
	    {
	    	bos = new BufferedOutputStream(new FileOutputStream(f));
	    	byte[] buffer = new byte[32 * 1024];
	    	int bytesRead = 0;
	    	while ((bytesRead = iStream.read(buffer)) != -1)
	    	{
	    		bos.write(buffer, 0, bytesRead);
	    	}
	    }
	    catch (Exception e)
	    {
	    	logger.error(e.toString());
	    	throw new IOException(e.toString());
	    }
	    finally {close(iStream, bos);}
	  }
	
	protected static void close(InputStream is, OutputStream os) throws IOException
	{
		try {if (is != null) {is.close();}}
		finally {if (os != null) {os.close();}}
	}
	
	public static String printByteStream(byte[] b, boolean spaces)
    {
	    StringBuffer sb = new StringBuffer();
	    for (int j = 0; j < b.length; j++)
	    {
          String s1 = Integer.toHexString(b[j] & 0xFF);
  		  if(spaces){sb.append((s1.length()==1) ? "0" + s1 + " " : s1 + " ");}
  		  else{sb.append((s1.length()==1) ? "0" + s1 : s1);}
	    }
	    return sb.toString();	    	
    }
	
	public static void deleteDir(File dir)
	{
		if(!dir.isDirectory()){logger.warn("Dir "+dir.getAbsolutePath()+" is not a directory");}
		else
		{
			for(File f : dir.listFiles())
			{
				if(f.isDirectory()){deleteDir(f);}
				else{f.delete();}
			}
		}
		dir.delete();
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
	
		String s1 = "Hallo";
		String s2 = "Hallo";
		
		logger.debug(printByteStream(hash(s1),true));
		logger.debug(printByteStream(hash(s2),true));

	}
}
