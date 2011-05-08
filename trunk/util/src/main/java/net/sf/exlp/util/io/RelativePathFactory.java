package net.sf.exlp.util.io;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RelativePathFactory
{
	static Log logger = LogFactory.getLog(RelativePathFactory.class);
	
	private boolean quoteSpaces;
	
	public RelativePathFactory(){this(true);}
	public RelativePathFactory(boolean quoteSpaces)
	{
		this.quoteSpaces=quoteSpaces;
	}

	public String relativate(File fullFixed, File fullRelative)
	{
		return relativate(fullFixed.getAbsolutePath(), fullRelative.getAbsolutePath());
	}
	
	public String relativate(String fullFixed, String fullRelative)
	{
		String fNormalized = FilenameUtils.normalize(fullFixed, true);
		String rNormalized = FilenameUtils.normalize(fullRelative, true);
		 
		String relative = getRelative(fNormalized,  rNormalized);
		
		if(quoteSpaces){relative = quoteSpaces(relative);}
		relative = FilenameUtils.separatorsToSystem(relative);
		
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
}
