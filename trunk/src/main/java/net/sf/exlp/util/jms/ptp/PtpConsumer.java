package net.sf.exlp.util.jms.ptp;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.exlp.util.jms.listener.MessageRespondListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PtpConsumer
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
	
	public PtpConsumer(InitialContext ctx,String queueName,MessageListener ml)
	{
		this(ctx,queueName,ml,"");
	}
	
	public PtpConsumer(InitialContext ctx,String queueName,MessageListener ml,String messageSelector)
	{
		this.ctx=ctx;
		this.queueName=queueName;
		this.ml=ml;
		this.messageSelector=messageSelector;
		typ = Typ.Listener;
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
			QueueConnectionFactory tcf = (QueueConnectionFactory)ctx.lookup("ConnectionFactory");;
			conn = tcf.createQueueConnection();
			queue = (Queue) ctx.lookup(queueName);
			session = conn.createQueueSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			
//			conn.setExceptionListener(new TestExceptionListener(this));
			
			if(ml instanceof MessageRespondListener)
			{
				((MessageRespondListener)ml).setSession(session);
			}
			
			conn.start();
			
			recv = session.createReceiver(queue,messageSelector);
			recv.setMessageListener(ml);
			logger.debug("PtpConsumer startet: queue="+queueName+" selector="+messageSelector);
		}
		catch (JMSException e){logger.error(e);}
		catch (NamingException e){logger.error(e);}
	}
	
	public void stop()
	{
		try
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
		catch (JMSException e) {logger.error(e);}
	}
}
