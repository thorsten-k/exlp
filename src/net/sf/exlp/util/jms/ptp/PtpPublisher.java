/*
 * Created on 11.10.2004
 */
package net.sf.exlp.util.jms.ptp;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueRequestor;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kisner
 */
public class PtpPublisher
{
	static Log logger = LogFactory.getLog(PtpPublisher.class);
	public static enum Typ{ String, Integer }
	
	QueueConnection qCon;
	QueueSession qSes;
	Queue queue;
	QueueBrowser qb;
	String queueName;
	Context ctx;
	
	public PtpPublisher(Context ctx,String queueName)
	{
		this.ctx=ctx;
		this.queueName=queueName;
		setupPTP();
		logger.info("initialisiert");
	}
	
	public void setupPTP()
	{
		try
		{
			Object tmp = ctx.lookup("XAConnectionFactory");
			QueueConnectionFactory qcf = (QueueConnectionFactory)tmp;
			qCon = qcf.createQueueConnection();
			queue = (Queue) ctx.lookup(queueName);
			qSes = qCon.createQueueSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			qCon.start();
			qb = qSes.createBrowser(queue);
		}
		catch (JMSException e){e.printStackTrace();}
		catch (NamingException e) {e.printStackTrace();}
	}
	
	public int getQueueElements()
	{
		int j=0;
		try
		{
			Enumeration<?> enu = qb.getEnumeration();
			while(enu.hasMoreElements()){enu.nextElement();j++;}
		}
		catch (JMSException e) {e.printStackTrace();}
		return j;
	}
	
	public void sendText(String text) throws JMSException, NamingException
	{
		QueueSender send = qSes.createSender(queue);
		TextMessage tm = qSes.createTextMessage(text);
		send.send(tm);
		System.out.println("Gesendet: "+tm.getText());
		send.close();
	}
	
	public void sendObject(Serializable myOb){sendObject(myOb, null,null);}
	public void sendObject(Serializable myOb,Hashtable<String,String> propStr){sendObject(myOb, propStr,null);}
//	public void sendObject(Serializable myOb,Hashtable<String,Integer> propInt){sendObject(myOb, null,propInt);}
	
	public void sendObject(Serializable myOb, Hashtable<String,String> propStr, Hashtable<String,Integer> propInt)
	{
		try
		{
			QueueSender send = qSes.createSender(queue);
			ObjectMessage om = qSes.createObjectMessage();
			int priority = 4;
			om.setObject(myOb);
			om.setIntProperty("PRIORITY",priority);
			if(propStr != null)
			{	// String Properties
				for (String propName : propStr.keySet())
				{
					om.setStringProperty(propName,propStr.get(propName));
				}
			}				
			if(propInt != null)
			{	// Integer Properties
				for (String propName : propInt.keySet())
				{
					om.setIntProperty(propName,propInt.get(propName));
				}
			}
			send.send(om, DeliveryMode.PERSISTENT,priority,0);
			send.close();
		}
		catch (JMSException e) {e.printStackTrace();}
	}
		
	public Object requestAnswer(Serializable myOb, Hashtable<String,String> propStr, Hashtable<String,Integer> propInt)
	{
		Object oResponse=null;
		try
		{
			QueueRequestor qReq = new QueueRequestor(qSes,queue);
			ObjectMessage omRequest = qSes.createObjectMessage();
			int priority = 4;
			omRequest.setObject(myOb);
			omRequest.setIntProperty("PRIORITY",priority);

			if(propStr != null)
			{	// String Properties
				for (String propName : propStr.keySet())
				{
					omRequest.setStringProperty(propName,propStr.get(propName));
				}
			}
			
			if(propInt != null)
			{	// Integer Properties
				for (String propName : propInt.keySet())
				{
					omRequest.setIntProperty(propName,propInt.get(propName));
				}
			}

			Message msgAnswer= qReq.request(omRequest);
			logger.debug("Get Response: "+msgAnswer.getClass().getCanonicalName()+" "+msgAnswer.getJMSTimestamp());
			ObjectMessage omResponse = (ObjectMessage)msgAnswer;
			logger.debug("Cast to ObjectMessage works");
			oResponse = (Object)omResponse.getObject();
			logger.debug("Cast to Object fails");
			qReq.close();
		}
		catch (JMSException e) {e.printStackTrace();}
		return oResponse;
	}
	
	public Object requestAnswer(Serializable myOb) {return requestAnswer(myOb, null,null);}
	
	public void stop()
	{
		try
		{
			qCon.stop();
			qSes.close();
			qCon.close();
		}
		catch (JMSException e){	e.printStackTrace();}
	}

}
