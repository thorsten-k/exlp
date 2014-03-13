package net.sf.exlp.util.io;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelativePathFactory
{
	final static Logger logger = LoggerFactory.getLogger(RelativePathFactory.class);
	
	public static enum PathSeparator {CURRENT,UNIX,WINDOWS}
	
	private PathSeparator pathSeparator;
	private boolean quoteSpaces;
	private File fFixed;
	
	public RelativePathFactory(File fFixed){this(fFixed,PathSeparator.CURRENT);}
	public RelativePathFactory(PathSeparator pathSeparator){this(null,pathSeparator);}
	public RelativePathFactory(File fFixed,PathSeparator pathSeparator){this(fFixed,pathSeparator,true);}
	public RelativePathFactory(PathSeparator pathSeparator, boolean quoteSpaces){this(null,pathSeparator,quoteSpaces);}
	public RelativePathFactory(File fFixed,PathSeparator pathSeparator, boolean quoteSpaces)
	{
		this.fFixed=fFixed;
		this.pathSeparator=pathSeparator;
		this.quoteSpaces=quoteSpaces;
	}

	public String relativate(File fullRelative)
	{
		if(fFixed==null){throw new NullPointerException("You have not set fFixed in constructor");}
		return relativate(fFixed, fullRelative);
	}
	public String relativate(File fullFixed, File fullRelative)
	{
		return relativate(fullFixed.getAbsolutePath(), fullRelative.getAbsolutePath());
	}

	public String relativate(String fullFixed, String fullRelative)
	{
		String fNormalized = FilenameUtils.normalize(fullFixed, true);
		String rNormalized = FilenameUtils.normalize(fullRelative, true);
		
		if(fNormalized.equals(rNormalized)){return ".";}
		
		String relative = getRelative(fNormalized,  rNormalized);
		
		if(quoteSpaces){relative = quoteSpaces(relative);}
		
		switch(pathSeparator)
		{
			case CURRENT:	relative = FilenameUtils.separatorsToSystem(relative);break;
			case UNIX:		relative = FilenameUtils.separatorsToUnix(relative);break;
			case WINDOWS:	relative = FilenameUtils.separatorsToWindows(relative);break;
		}
		
		return relative;
	}
	
	private String getRelative(String fNormalized, String rNormalized)
	{
		logger.trace("fNormalized: "+fNormalized);
		logger.trace("rNormalized: "+rNormalized);
		
		String result = rNormalized;
		int index = rNormalized.indexOf(fNormalized);
		
		if(index==0)
		{
			result = rNormalized.substring(fNormalized.length(), rNormalized.length());
			if(result.startsWith("/")){result = result.substring(1,result.length());}
		}		
		return result;
	}
	
	private String quoteSpaces(String path)
	{
		if(path.contains(" "))
		{
			path = "\""+path+"\"";
		}
		return path;
	}
	
	public File getfFixed() {return fFixed;}
}
