package net.sf.exlp.util.net.jms.ptp;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.jms.BytesMessage;
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
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PtpPublisher
{
	static Log logger = LogFactory.getLog(PtpPublisher.class);
	
	private QueueConnection connection;
	private QueueSession session;
	private Queue queue;
	private QueueSender sender;
	private QueueRequestor requestor;
	private QueueBrowser browser;
	private String queueName;
	private Context ctx;
	
	private Map<String,String> propStr;
	private Map<String,Integer> propInt;
	private int priority;

	private boolean requestMode;
	
	public PtpPublisher(Context ctx,String queueName)
	{
		this.ctx=ctx;
		this.queueName=queueName;
		requestMode = false;
		setupPTP();
		propStr = new Hashtable<String,String>();
		propInt = new Hashtable<String,Integer>();
		clearProperties();
	}
	
	// Management Methods
	public void setupPTP()
	{
		try
		{
			QueueConnectionFactory qcf = (QueueConnectionFactory)ctx.lookup("ConnectionFactory");
			connection = qcf.createQueueConnection();
			queue = (Queue) ctx.lookup(queueName);
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			
			if(requestMode){requestor = new QueueRequestor(session, queue);}
			else {sender = session.createSender(queue);}
			
			browser = session.createBrowser(queue);
			connection.start();
			logger.debug("PtpProducer startet: queue="+queueName);
		}
		catch (JMSException e){e.printStackTrace();}
		catch (NamingException e) {e.printStackTrace();}
	}
	
	public void stop()
	{
		try
		{
			sender.close();
			connection.stop();
			session.close();
			connection.close();
		}
		catch (JMSException e){	e.printStackTrace();}
	}
		
	public int getQueueElements()
	{
		int j=0;
		try
		{
			Enumeration<?> enu = browser.getEnumeration();
			while(enu.hasMoreElements()){enu.nextElement();j++;}
		}
		catch (JMSException e) {e.printStackTrace();}
		return j;
	}
	
	public void clearProperties()
	{
		priority = 4;
		propStr.clear();
		propInt.clear();
	}
	
	public void setProperty(String key, int value)
	{
		propInt.put(key, value);
	}
	public void setProperty(String key, String value)
	{
		propStr.put(key, value);
	}
	
	private Message send(Message msg) throws JMSException
	{
		Message answer = null;
		for (String propName : propStr.keySet()){msg.setStringProperty(propName,propStr.get(propName));}			
		for (String propName : propInt.keySet()){msg.setIntProperty(propName,propInt.get(propName));}
		
		msg.setIntProperty("PRIORITY",priority);
		
		if(requestMode){answer = requestor.request(msg);}
		else {sender.send(msg, DeliveryMode.PERSISTENT,priority,0);}
		return answer;
	}
	
	// Text Messages
	public void sendText(String text) throws JMSException
	{
		TextMessage tm = session.createTextMessage(text);
		send(tm);
		logger.trace("Sending to Queue ("+queue.getQueueName()+"): "+tm.getText());
	}
	
	public String requestText(String text) throws JMSException
	{
		if(!requestMode)
		{
			requestMode=true;
			stop();
			setupPTP();
		}
		TextMessage tm = session.createTextMessage(text);
		TextMessage answer = (TextMessage)send(tm);
		logger.trace("Sending to Queue ("+queue.getQueueName()+"): "+tm.getText());
		return answer.getText();
	}
	
	// Object Messages
	public void sendObject(Serializable myOb) throws JMSException
	{
		ObjectMessage om = session.createObjectMessage();
		om.setObject(myOb);
		send(om);
	}
		
	public Object requestAnswer(Serializable myOb, Hashtable<String,String> propStr, Hashtable<String,Integer> propInt)
	{
		Object oResponse=null;
		try
		{
			QueueRequestor qReq = new QueueRequestor(session,queue);
			ObjectMessage omRequest = session.createObjectMessage();
			
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
	
	// JAXB Messages
	public void sendJaxb(Object jaxb, NsPrefixMapperInterface nsPrefixMapper) throws JMSException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JaxbUtil.output(baos, jaxb, nsPrefixMapper, true);
		
		BytesMessage byteMsg = session.createBytesMessage();
		byteMsg.writeBytes(baos.toByteArray());
		
		send(byteMsg);
	}
	
	// Getter & Setter
	public int getPriority() {return priority;}
	public void setPriority(int priority) {this.priority = priority;}
}
