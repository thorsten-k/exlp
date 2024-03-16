package org.exlp.maven.goal;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name="test")
public class ExlpMavenTestGoal extends AbstractMojo
{
	final static Logger logger = LoggerFactory.getLogger(ExlpMavenTestGoal.class);
    
    public void execute() throws MojoExecutionException
    {
    	logger.info("mvn net.sf.exlp:exlp-maven:0.1.18-SNAPSHOT:test");
    	logger.info("");
    	logger.trace("Trace");
    	logger.debug("Debug");
    	logger.info("Info");
    	logger.warn("Warn");
    	logger.error("Error");
    }
}