package de.kisner.exlp.maven.version;

import java.io.FileNotFoundException;

import org.exlp.maven.IgnoreMavenVersionFileMerger;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.exlp.test.AbstractExlpMavenTest;
import de.kisner.exlp.test.ExlpMavenTestBootstrap;
import net.sf.exlp.exception.ExlpConfigurationException;

public class TestVersionFileMerger extends AbstractExlpMavenTest
{
	final static Logger logger = LoggerFactory.getLogger(TestVersionFileMerger.class);
	
	
	
	@Test
	public void buildPackage() throws ExlpConfigurationException
	{
		
	}
	
	public static void main(String args[]) throws FileNotFoundException
	{
		ExlpMavenTestBootstrap.init();
		
		IgnoreMavenVersionFileMerger imvfm = new IgnoreMavenVersionFileMerger();
		imvfm.add("exlp/maven/exlp-versions.xml");
		imvfm.output(System.out);
	}
}