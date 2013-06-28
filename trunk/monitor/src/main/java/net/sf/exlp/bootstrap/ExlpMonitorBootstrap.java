package net.sf.exlp.bootstrap;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ExlpMonitorBootstrap
{
	public static EntityManagerFactory buildEmf(boolean overwrite)
	{
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:file:target/dbj");
		
		if(overwrite){properties.put("hibernate.hbm2ddl.auto", "create-drop");}
		else{properties.put("hibernate.hbm2ddl.auto", "create-update");}
		
		return Persistence.createEntityManagerFactory("exlp", properties);
	}
}
