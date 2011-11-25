package net.sf.exlp.util.net.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpLocalhostCheck
{
	final static Logger logger = LoggerFactory.getLogger(ExlpLocalhostCheck.class);
	
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
			logger.error("Can not resolve localhost: "+e);
			logger.error("",e);
			logger.error("Application will be shut down immedeatly!");
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
			logger.error("Kann getLocalHost nicht aufloesen: "+e);
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
