package net.sf.exlp.io.spawn;

import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SpawnShutdownHook extends Thread
{
	static Log logger = LogFactory.getLog(SpawnShutdownHook.class);
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
