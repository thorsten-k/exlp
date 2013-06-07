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
	private static String tmpProperty = "java.io.tmpdir";
	
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
			default : buildDefault();
		}
		return osTmpDir;
	}
	
	private void buildDefault() throws IOException
	{
		String tempDir = System.getProperty(tmpProperty);
		if(tempDir==null){throw new IOException("Sorry, but on the  current architecture "+ArchUtil.getArch()+" "+tmpProperty+" is not available");}
		build(tempDir+fs+appId);
	}
	
	private void buildOsX() throws IOException
	{
		build(System.getProperty("user.home")+fs+"Library"+fs+"Caches"+fs+appId);
	}
	
	private void build(String dirName) throws IOException
	{
		osTmpDir = new File(dirName);
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