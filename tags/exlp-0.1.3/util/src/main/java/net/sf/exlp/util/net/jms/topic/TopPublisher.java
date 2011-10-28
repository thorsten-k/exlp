package net.sf.exlp.util.net.jms.topic;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TopPublisher
{
	public static enum Typ{ String, Integer }
	
	static Log logger = LogFactory.getLog(TopPublisher.class);
	
	TopicConnection tc=null;
	TopicSession tSes = null;
	Topic topic= null;
	TopicPublisher tp = null;
	String JndiHost = null;
	String queueName = null;
	InitialContext iniCtx;
	Properties myProperties;
	
	public TopPublisher(InitialContext iniCtx,String queueName)
	{
		this.iniCtx=iniCtx;
		this.queueName=queueName;
		myProperties=null;
		setupPubSub();
	}
	
	public TopPublisher(InitialContext iniCtx,String queueName,Properties myProperties)
	{
		this.iniCtx=iniCtx;
		this.queueName=queueName;
		this.myProperties=myProperties;
		setupPubSub();
	}
	
	public void setupPubSub()
	{
		try
		{
			TopicConnectionFactory tcf = (TopicConnectionFactory)iniCtx.lookup("XAConnectionFactory");
			tc = tcf.createTopicConnection();
			tSes = tc.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			topic = (Topic) iniCtx.lookup(queueName);
	//		System.out.println(topic.getTopicName());
			tc.start();
			tp = tSes.createPublisher(topic);
			//System.out.println("setupPubSub [OK]");
		}
		catch (JMSException e)
		{
			System.out.println(this.getClass().getName()+".setupPubSub: Exception1");
			e.printStackTrace();
		}
		catch (NamingException e)
		{
			System.out.println(this.getClass().getName()+".setupPubSub: Exception2");
			e.printStackTrace();
		}
//		System.out.println(this.getClass().getName()+".setupPubSub: abgeschlossen");
	}
	
	public void sendObject(Serializable myOb, Hashtable<TopPublisher.Typ,Hashtable> p)
	{
		try
		{
			ObjectMessage om = tSes.createObjectMessage();
			om.setObject(myOb);
			if(p != null)
			{
				Hashtable<String,String> hString = p.get(Typ.String);
				if(hString != null)
				{	// String Properties
					for (String propName : hString.keySet())
					{
						om.setStringProperty(propName,hString.get(propName));
					}
				}				
				
				Hashtable<String,Integer> hInteger = p.get(Typ.Integer);
				if(hInteger != null)
				{	// Integer Properties
					for (String propName : hInteger.keySet())
					{
						om.setIntProperty(propName,hInteger.get(propName));
					}
				}
			}
			tp.publish(om);
			logger.trace(" wurde über JMS verschickt");
		}
		catch (JMSException e) {e.printStackTrace();}
	}
	
	public void sendObject(Serializable myOb, Properties myObjectProperties)
	{
		try
		{
			ObjectMessage om = tSes.createObjectMessage();
			om.setObject(myOb);
			if(myObjectProperties != null)
			{
				Enumeration<?> e = myObjectProperties.propertyNames();
				while(e.hasMoreElements())
				{
					String PropName=(String)e.nextElement();
					om.setStringProperty(PropName,myObjectProperties.getProperty(PropName));
				}
			}
			tp.publish(om);
		}
		catch (JMSException e) {e.printStackTrace();}
	}
	
	
	public void sendText(String text)
	{
		try
		{
			TextMessage tm = tSes.createTextMessage(text);
			tp.publish(tm);
//			tp.close();
		}
		catch (JMSException e) {e.printStackTrace();}
	}

	public void stop() throws JMSException
	{
		tc.stop();
		tSes.close();
		tc.close();
	}
}
