package org.exlp.maven.goal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.exlp.maven.IgnoreMavenVersionFileMerger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name="mvnVersionIgnore")
public class ExlpIgnoreMavenVersionGoal extends AbstractMojo
{
	final static Logger logger = LoggerFactory.getLogger(ExlpIgnoreMavenVersionGoal.class);
	
	@Parameter
    private List<String> files;
    
	@Parameter
    private String saveTo;
    
    public void execute() throws MojoExecutionException
    {
    	logger.info("Generating maven-version-ignore with "+files.size()+" files to "+saveTo);
    	
    	IgnoreMavenVersionFileMerger merger = new IgnoreMavenVersionFileMerger();
    	
    	try
    	{
    		for(String s : files)
        	{
        		merger.add(s);
        	}
    		File f = new File(saveTo);
			merger.output(new FileOutputStream(f));
		}
    	catch (FileNotFoundException e) {throw new MojoExecutionException(e.getMessage());}

    }
}