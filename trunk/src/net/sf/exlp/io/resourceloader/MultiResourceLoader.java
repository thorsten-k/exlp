package net.sf.exlp.io.resourceloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiResourceLoader
{
	static Log logger = LogFactory.getLog(MultiResourceLoader.class);
	
	public enum LoadType {FileIs, Jws};
	private static ArrayList<String> alLoadError;
	private static ArrayList<String> alLoadDebug;
	public static LoadType lastLT;
	public static String lastAbsolutPath;
	public static boolean debug;

	public MultiResourceLoader()
	{
		debug = false;
	}
	
	public synchronized InputStream searchIs(String resourceName) throws FileNotFoundException
	{
		ClassLoader cl = this.getClass().getClassLoader();
		return searchIs(cl, resourceName);
	}
	
	/**
	 * 	 * @deprecated
	 * @throws FileNotFoundException 
	 */
	public synchronized InputStream searchIs(ClassLoader cl, String resourceName) throws FileNotFoundException
	{
		alLoadError = new ArrayList<String>();
		alLoadDebug = new ArrayList<String>();
		InputStream is = null;
		for(LoadType lt : LoadType.values())
		{
			switch (lt)
			{
				case FileIs: 	is=getFileIs(resourceName);break;
				case Jws:		is=getJwsIs(cl,resourceName);break;
			}
			if(is!=null){lastLT=lt;break;}
		}
		if(debug){for(String s : alLoadDebug){logger.debug(s);}}
		if(is==null)
		{
			for(String s : alLoadError){logger.debug(s);}
			throw new FileNotFoundException("Missing File: "+resourceName);
		}
		return is;
	}
	
	private InputStream getJwsIs(ClassLoader cl, String resourceName)
	{
		InputStream is=null;
		resourceName = resourceName.replace(SystemUtils.FILE_SEPARATOR, "/");
		URL url = cl.getResource(resourceName);
		String confInfo = "ClassLoader.getResourceAsStream("+resourceName+")";
		if(url!=null)
		{
			is = cl.getResourceAsStream(resourceName);
			alLoadDebug.add("Found: "+confInfo+" "+url.getPath()+" "+is.getClass().getSimpleName()+".");
		}
		else
		{
			alLoadError.add("Not found: "+confInfo);
		}
		return is;
	}
	
	private InputStream getFileIs(String resourceName)
	{
		InputStream is=null;
		File f = new File(resourceName);
		lastAbsolutPath = f.getAbsolutePath();
		if(f.exists())
		{
			alLoadDebug.add("Gefunden: File("+lastAbsolutPath+")");
			try {is = new FileInputStream(f);}
			catch (FileNotFoundException e) {e.printStackTrace();}
		}
		else
		{
			alLoadError.add("Not found: File("+lastAbsolutPath+")");
			f=null;
		}
		return is;
	}
	
	public static boolean isDebug() {return debug;}
	public static void setDebug(boolean debug) {MultiResourceLoader.debug = debug;}
}
