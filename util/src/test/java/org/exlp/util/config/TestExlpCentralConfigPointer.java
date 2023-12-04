package org.exlp.util.config;

import java.io.FileNotFoundException;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.model.xml.io.Dir;
import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.xml.JaxbUtil;

public class TestExlpCentralConfigPointer extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExlpCentralConfigPointer.class);
    
	@Disabled
    @Test
    public void testFile() throws FileNotFoundException
    {
		try
		{
			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance(ExlpBootstrap.System.exlp).jaxb(JaxbUtil.instance());
			ConfigLoader.addFile(ccp.toFile("client"));	
		}
		catch (ExlpConfigurationException e) {logger.debug("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" "+e.getMessage());}
//		ConfigLoader.addString(configFile);
		
		Configuration config = ConfigLoader.init();					
    }
    
	public void jaxb() throws FileNotFoundException
	{
		Dir dir = JaxbUtil.loadJAXB("/here/your/exlp.xml", Dir.class);
		JaxbUtil.info(dir);
	}
	
	public static void main(String[] args) throws ExlpConfigurationException, FileNotFoundException
    {
		ExlpBootstrap.init();
		
		TestExlpCentralConfigPointer cli = new TestExlpCentralConfigPointer();
		cli.testFile();
    }
}