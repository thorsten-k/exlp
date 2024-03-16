package net.sf.exlp.test.net.jms;

import javax.jms.MessageListener;
import javax.naming.InitialContext;

import org.exlp.test.ExlpEjbBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.net.ejb.ExlpContextFactory;
import net.sf.exlp.util.net.jms.listener.TextMessageRespondDebugListener;
import net.sf.exlp.util.net.jms.ptp.PtpConsumer;

public class TstPtpConsumer
{
	final static Logger logger = LoggerFactory.getLogger(TstPtpConsumer.class);
	
	private PtpConsumer ptp;
	
	public TstPtpConsumer(InitialContext ctx)
	{
		MessageListener tmdl = new TextMessageRespondDebugListener(ctx);
		ptp = new PtpConsumer(ctx,"queue/ErpAsync",tmdl);
	}
	
	public void receive()
	{
		ptp.start();
		try {Thread.sleep(1000*100);} catch (InterruptedException e) {logger.error(""+e);}
		ptp.stop();
	}
	
	public static void main (String[] args) throws Exception
	{
		ExlpEjbBootstrap.init();
		
		logger.debug("Starting main.");	
			
		InitialContext ctx = ExlpContextFactory.getJbossContext("192.168.1.251:1099");
			
		TstPtpConsumer test = new TstPtpConsumer(ctx);
		test.receive();
	}
}