package net.sf.exlp.util.jms.ptp;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExceptionListener extends Thread implements ExceptionListener
{
	static Log logger = LogFactory.getLog(TestExceptionListener.class);
	
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
