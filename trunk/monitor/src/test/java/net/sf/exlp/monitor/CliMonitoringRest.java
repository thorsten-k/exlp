package net.sf.exlp.monitor;

import net.sf.ahtutils.interfaces.rest.UtilsMonitoringRest;
import net.sf.ahtutils.xml.monitoring.Transmission;
import net.sf.exlp.test.ExlpMonitorTestBootstrap;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliMonitoringRest
{
	final static Logger logger = LoggerFactory.getLogger(CliMonitoringRest.class);
	
	public static void main (String[] args)
	{
		ExlpMonitorTestBootstrap.init();
		
		 ResteasyClient client = new ResteasyClientBuilder().build();
         ResteasyWebTarget target = client.target("http://example.com/base/uri");

         UtilsMonitoringRest simple = target.proxy(UtilsMonitoringRest.class);
         simple.upload(new Transmission());

	  }
}