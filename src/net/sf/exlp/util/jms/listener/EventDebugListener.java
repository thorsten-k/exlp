package net.sf.exlp.util.jms.listener;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import net.sf.exlp.event.LogEvent;
import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EventDebugListener implements MessageListener
{
	static Log logger = LogFactory.getLog(EventDebugListener.class);
	
	LogEventHandler ehi;
	
	public EventDebugListener()
	{	
		ehi = new EhDebug();
	}
	
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
		try
		{	
			LogEvent event = (LogEvent)((ObjectMessage)msg).getObject();
			ehi.handleEvent(event);
		}
		catch (Throwable t){t.printStackTrace();}
	}
}
