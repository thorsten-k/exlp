package net.sf.exlp.io.spawn;

import java.util.Enumeration;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpawnShutdownHook extends Thread
{
	final static Logger logger = LoggerFactory.getLogger(SpawnShutdownHook.class);
	
	Hashtable<Integer,Spawn> htSpawn;
	
	public SpawnShutdownHook(Hashtable<Integer,Spawn> htSpawn)
	{
		this.htSpawn=htSpawn;
	}
	
	public void run()
	{
		logger.info("Shutdown wird eingeleitet");
		Enumeration<Spawn> enu = htSpawn.elements();
		while(enu.hasMoreElements())
		{
			Spawn s = enu.nextElement();
			s.kill();
		}
	}
	
}
