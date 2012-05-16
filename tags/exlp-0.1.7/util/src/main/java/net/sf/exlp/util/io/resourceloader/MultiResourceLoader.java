package net.sf.exlp.util.io.resourceloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiResourceLoader
{
	final static Logger logger = LoggerFactory.getLogger(MultiResourceLoader.class);
	
	public enum LoadType {FileIs, Jws};
	private ArrayList<String> alLoadError;
	private ArrayList<String> alLoadDebug;
	public String lastAbsolutPath;
	public boolean debugInfo,debugError;
	
	private List<String> paths;

	public MultiResourceLoader()
	{
		debugInfo = false;
		debugError = true;
		paths = new ArrayList<String>();
	}
	
	public void addPath(String... path)
	{
		for(String s : path){paths.add(s);}
	}
	
	public boolean isAvailable(String resourceName)
	{
		boolean available=false;
		try
		{
			searchIs(resourceName);
			available = true;
		}
		catch (FileNotFoundException e) {}
		return available;
	}
	
	public synchronized InputStream searchIs(String resourceName) throws FileNotFoundException
	{
		ClassLoader cl = this.getClass().getClassLoader();
		return searchIs(cl, resourceName);
	}
	
	public synchronized InputStream searchIs(ClassLoader cl, String resourceName) throws FileNotFoundException
	{
		alLoadError = new ArrayList<String>();
		alLoadDebug = new ArrayList<String>();
		InputStream is = null;
		if(paths.size()==0){paths.add("");}
		
		for(String path : paths)
		{
			String resourcePath;
			if(path.length()==0){resourcePath=resourceName;}
			else{resourcePath=path+SystemUtils.FILE_SEPARATOR+resourceName;}
			for(LoadType lt : LoadType.values())
			{
				switch (lt)
				{
					case FileIs: 	is=getFileIs(resourcePath);break;
					case Jws:		is=getJwsIs(cl,resourcePath);break;
				}
				if(is!=null){break;}
			}
			if(is!=null){break;}
		}
		
		if(debugInfo){for(String s : alLoadDebug){logger.debug(s);}}
		if(is==null)
		{
			boolean altPaths = (paths.size()>0 && paths.get(0).length()>0);
			StringBuffer sb = new StringBuffer();
			for(String path : paths){sb.append(path+",");}
			sb = new StringBuffer(sb.substring(0, sb.length()-1));
			
			if(debugError)
			{
				for(String s : alLoadError){logger.debug(s);}
				if(altPaths){logger.debug("... in paths: "+sb);}
			}
			
			StringBuffer sbError = new StringBuffer();
			sbError.append("Missing File: ").append(resourceName);
			if(altPaths){sbError.append("in paths: "+sb);}
			
			throw new FileNotFoundException(sbError.toString());
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
	
	public boolean isDebugInfo() {return debugInfo;}
	public void setDebugInfo(boolean debug) {this.debugInfo = debug;}
	
	public boolean isDebugError() {return debugError;}
	public void setDebugError(boolean debugError) {this.debugError = debugError;}
}