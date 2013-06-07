package net.sf.exlp.util.os;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class OsTempDirFactory
{
	final static Marker fatal = MarkerFactory.getMarker("FATAL");
	final static Logger logger = LoggerFactory.getLogger(OsTempDirFactory.class);
	
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	private String appId;
	
	@SuppressWarnings("unused")
	private List<String> subDirectories;
	private File osTmpDir;
	
	public OsTempDirFactory(String appId)
	{
		this.appId=appId;
	}

	public File build() throws IOException
	{
		switch(ArchUtil.getArch())
		{
			case OsX: buildOsX();break;
			default : throw new IOException("Sorry, the current architecture "+ArchUtil.getArch()+" is not supported");
		}
		return osTmpDir;
	}
	
	private void buildOsX() throws IOException
	{
		osTmpDir = new File(System.getProperty("user.home")+fs+"Library"+fs+"Caches"+fs+appId);
		if(osTmpDir.exists() && !osTmpDir.isDirectory())
		{
			throw new IOException("TMP directory exists, but is not a directory: "+osTmpDir.getAbsolutePath());
		}
		if(!osTmpDir.exists()){osTmpDir.mkdir();}
	}
	
	public void subDir()
	{
		
	}
}