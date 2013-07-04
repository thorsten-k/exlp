package net.sf.exlp.util.jms.listener;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.exlp.event.handler.EhDebug;
import net.sf.exlp.interfaces.LogEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextMessageDebugListener implements MessageListener
{
	final static Logger logger = LoggerFactory.getLogger(TextMessageDebugListener.class);
	
	LogEventHandler ehi;
	
	public TextMessageDebugListener()
	{	
		ehi = new EhDebug();
	}
	
	@SuppressWarnings("unchecked")
	public void onMessage(Message msg)
	{
		try
		{
			TextMessage tm = (TextMessage)msg;
			logger.debug("New Message: "+tm.getText());
			
			StringBuffer sb = new StringBuffer();
			sb.append("JMSMessageID="+msg.getJMSMessageID());
			sb.append(" Properties: ");
			Enumeration<String> enu = msg.getPropertyNames();
			while(enu.hasMoreElements())
			{
				String prop=(String)enu.nextElement();
				sb.append(" "+prop+"="+msg.getStringProperty(prop));
			}
			logger.debug("\t"+sb.toString());
		}
		catch (JMSException e) {e.printStackTrace();}	
	}
}
