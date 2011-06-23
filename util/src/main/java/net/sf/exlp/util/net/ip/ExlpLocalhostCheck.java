package net.sf.exlp.util.net.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExlpLocalhostCheck
{
	static Log logger = LogFactory.getLog(ExlpLocalhostCheck.class);
	
	public static boolean checkServer(String serverName)
	{
		boolean test=false;
		try
		{
			if(serverName.equals(InetAddress.getLocalHost().getHostName())){test=true;}
		}
		catch (UnknownHostException e) {e.printStackTrace();}
		return test;
	}
	
	public static String getHostName()
	{
		String hostName=null;
		try
		{
			hostName=InetAddress.getLocalHost().getHostName().toLowerCase();
		}
		catch (UnknownHostException e)
		{
			logger.fatal("Can not resolve localhost: "+e);
			logger.fatal(e);
			logger.fatal("Application will be shut down immedeatly!");
			System.exit(-1);
		}
		return hostName;
	}
	
	public static String getHostIP()
	{
		String hostIP=null;
		try
		{
			hostIP=InetAddress.getLocalHost().getHostAddress();
			if(hostIP.indexOf("127.0.")==0)
			{
				logger.warn("Hostname for "+ExlpLocalhostCheck.getHostName()+" points to Loopback-Interface "+hostIP);
				logger.warn("Correct this. (on linux system in /etc/hosts)");
			}
		}
		catch (UnknownHostException e)
		{
			logger.fatal("Kann getLocalHost nicht aufloesen: "+e);
		}
		return hostIP;
	}
	
	public static ArrayList<String> getAllHostnames()
	{
		ArrayList<String> result = new ArrayList<String>();
		String localHost;
		try
		{
			localHost = InetAddress.getLocalHost().getHostName();
			for (InetAddress ia : InetAddress.getAllByName(localHost)) 
			{
				result.add(ia.getHostAddress());	
			}
		}
		catch (UnknownHostException e) {e.printStackTrace();} 
		return result;
	}
	
	public static void main(String args[]) throws UnknownHostException
	{
		
	}
}
