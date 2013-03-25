package net.sf.exlp.util.jms.listener;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.sf.exlp.event.LogEvent;
import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventDebugListener implements MessageListener
{
	final static Logger logger = LoggerFactory.getLogger(EventDebugListener.class);
	
	LogEventHandler ehi;
	
	public EventDebugListener()
	{	
		ehi = new EhDebug();
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void onMessage(Message msg)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			Enumeration<String> enu = msg.getPropertyNames();
			while(enu.hasMoreElements())
			{
				String prop=(String)enu.nextElement();
				sb.append(prop+"="+msg.getStringProperty(prop)+" ");
			}
			logger.debug("Neue Nachricht mit Properties: "+sb);
			logger.debug("\tJMSMessageID="+msg.getJMSMessageID());
		}
		catch (JMSException e) {e.printStackTrace();}	
//		try
		{	
			LogEvent event;
			try
			{
				event = (LogEvent)((ObjectMessage)msg).getObject();
			}
			catch (JMSException e)
			{
				e.printStackTrace();
			}
//			ehi.handleEvent(event);
		}
//		catch (Throwable t){t.printStackTrace();}
	}
}
