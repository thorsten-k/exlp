/*
 * Created on 11.10.2004
 *
 */
package net.sf.exlp.util.jms.topic;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kisner
 */
public class TopConsumer
{
	static Log logger = LogFactory.getLog(TopConsumer.class);
	
	TopicConnection tc;
	TopicSession session;
	Topic topic;
	TopicSubscriber ts;
	String JndiHost,topicName;
	MessageListener ml;
	InitialContext iniCtx;
	String messageSelector;
	
	public TopConsumer(InitialContext iniCtx,MessageListener ml,String topicName)
	{
		this.iniCtx=iniCtx;
		this.ml=ml;
		this.topicName=topicName;
		messageSelector=null;
		logger.debug("TopConsumer initialisiert");
	}
	
	public TopConsumer(InitialContext iniCtx,MessageListener ml,String topicName,String messageSelector)
	{
		this.iniCtx=iniCtx;
		this.ml=ml;
		this.topicName=topicName;
		this.messageSelector=messageSelector;
		logger.debug("TopConsumer initialisiert");
	}
	
	public void start()
	{
		try
		{
			Object tmp = iniCtx.lookup("XAConnectionFactory");
			TopicConnectionFactory tcf = (TopicConnectionFactory)tmp;
			tc = tcf.createTopicConnection();
			//tc = tcf.createTopicConnection("john","needle");
			session = tc.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			topic = (Topic) iniCtx.lookup(topicName);
		//	ts = session.createDurableSubscriber(topic,"chap");
			ts = session.createSubscriber(topic,messageSelector,true);

			ts.setMessageListener(ml);
			tc.start();
		}
		catch (JMSException e){	e.printStackTrace();}
		catch (NamingException e) {e.printStackTrace();}
	}
		
	public void stop()
	{
		try
		{
			tc.stop();
			session.close();
			tc.close();
		}
		catch (JMSException e) {e.printStackTrace();}
	}	
}
