/*
 * Created on 11.10.2004
 */
package net.sf.exlp.util.jms.ptp;

import java.io.Serializable;
import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kisner
 */
public class PtpConsumer implements MessageListener
{
	static Log logger = LogFactory.getLog(PtpConsumer.class);
	private static enum Typ {Listener, Responder};
	
	private Typ typ;
	
	InitialContext ctx;
	MessageListener ml;
	String queueName,messageSelector;
	
	QueueConnection conn;
	QueueSession session;
	Queue queue;
	QueueReceiver recv;
	MessageResponder mRes;
	
	public PtpConsumer(InitialContext ctx,MessageListener ml,String queueName,String messageSelector)
	{
		this.ctx=ctx;
		this.queueName=queueName;
		this.ml=ml;
		this.messageSelector=messageSelector;
		typ = Typ.Listener;
		start();
	}
	
	public PtpConsumer(InitialContext ctx,MessageResponder mRes,String queueName,String messageSelector)
	{
		this.ctx=ctx;
		this.queueName=queueName;
		this.messageSelector=messageSelector;
		this.mRes=mRes;
		typ = Typ.Responder;
		start();
	}
	
	public void start()
	{
		try
		{
			Object tmp = ctx.lookup("XAConnectionFactory");
			QueueConnectionFactory tcf = (QueueConnectionFactory)tmp;
			conn = tcf.createQueueConnection();
			queue = (Queue) ctx.lookup(queueName);
			session = conn.createQueueSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			
			conn.setExceptionListener(new TestExceptionListener(this));
			conn.start();
			
			recv = session.createReceiver(queue,messageSelector);
			switch (typ)
			{
				case Listener: recv.setMessageListener(ml);break;
				case Responder: recv.setMessageListener(this);break; 
			}
			
		}
		catch (JMSException e){	logger.error(e.getLocalizedMessage());}
		catch (NamingException e) {e.printStackTrace();}
		logger.info("initialisiert: (queue="+queueName+" selector="+messageSelector+")");
	}

	public void onMessage(Message msg)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			Enumeration<String> enu = (Enumeration<String>)msg.getPropertyNames();
			while(enu.hasMoreElements())
			{
				String prop=(String)enu.nextElement();
				sb.append(prop+"="+msg.getStringProperty(prop)+" ");
			}
			logger.debug("onMessage mit Properties: "+sb);
		}
		catch (JMSException e) {e.printStackTrace();}	
		try
		{	
			Object oReq = ((ObjectMessage)msg).getObject();
			Serializable answer = mRes.respondObject(oReq);
			ObjectMessage om = session.createObjectMessage();
			int priority = 4;
			om.setObject(answer);
			om.setIntProperty("PRIORITY",priority);
			Queue replyQ = (Queue)msg.getJMSReplyTo();
			QueueSender qSend = session.createSender(replyQ);
			qSend.send(replyQ, om);
			qSend.close();
		}
		catch (Throwable t){t.printStackTrace();}
	}
	
	public void kill()
	{
		
	}
	
	public void stop() throws JMSException
	{
		logger.debug("QueueReceiver wird geschlossen");
		recv.close();
		logger.debug("QueueConnection wird gestopt");
		conn.stop();
		logger.debug("QueueSession wird geschlossen");
		session.close();
		logger.debug("QueueConnection wird geschlossen");
		conn.close();
	}
}
