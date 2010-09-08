package net.sf.exlp.test.net.jms;

import javax.jms.MessageListener;
import javax.naming.InitialContext;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.data.facade.ExlpContextFactory;
import net.sf.exlp.util.jms.listener.TextMessageRespondDebugListener;
import net.sf.exlp.util.jms.ptp.PtpConsumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPtpConsumer
{
	static Log logger = LogFactory.getLog(TestPtpConsumer.class);
	
	private PtpConsumer ptp;
	
	public TestPtpConsumer(InitialContext ctx)
	{
		MessageListener tmdl = new TextMessageRespondDebugListener(ctx);
		ptp = new PtpConsumer(ctx,"queue/ErpAsync",tmdl);
	}
	
	public void receive()
	{
		ptp.start();
		try {Thread.sleep(1000*100);} catch (InterruptedException e) {logger.error(e);}
		ptp.stop();
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
				
		InitialContext ctx = ExlpContextFactory.getJbossContext("192.168.1.251:1099");
			
		TestPtpConsumer test = new TestPtpConsumer(ctx);
		test.receive();
	}
}