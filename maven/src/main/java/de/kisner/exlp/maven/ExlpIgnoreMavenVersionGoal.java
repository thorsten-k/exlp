package de.kisner.exlp.maven;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name="mvnVersionIgnore")
public class ExlpIgnoreMavenVersionGoal extends AbstractMojo
{    

	@Parameter
    private List<String> files;
    
    
    public void execute() throws MojoExecutionException
    {
    	BasicConfigurator.configure();
    	Logger.getRootLogger().setLevel(Level.ERROR);
    	 
    	getLog().info("Generating maven-version-ignore with "+files.size()+" files");

    }
    
    
}