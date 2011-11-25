package net.sf.exlp.util.net.jms.ptp;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExceptionListener extends Thread implements ExceptionListener
{
	final static Logger logger = LoggerFactory.getLogger(TestExceptionListener.class);
	
	PtpConsumer ptpC;
	
	public TestExceptionListener(PtpConsumer ptpC)
	{
		this.ptpC=ptpC;
		logger.debug("initialisiert");
	}
	
	public void onException(JMSException e)
	{
		logger.warn("Ne Exception ...");
		run();
	}
	
	public void run()
	{
		int waiting=3;
		logger.warn("Trying to reconnect in "+waiting+" Minute");
		synchronized(this)
		{
			try{this.wait(waiting*60*1000);}
			catch (InterruptedException e) {}
		}
		logger.warn("Reconnecting Deaktiviert (too many open files/sockets)");
//		ptpC.start();
	}
}
