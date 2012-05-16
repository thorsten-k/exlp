package net.sf.exlp.util.net.jms.listener;

import javax.jms.MessageListener;
import javax.jms.QueueSession;

public interface MessageRespondListener extends MessageListener
{
	void setSession(QueueSession session);
}
