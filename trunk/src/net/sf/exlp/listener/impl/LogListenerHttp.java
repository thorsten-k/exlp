package net.sf.exlp.listener.impl;

import java.io.IOException;

import net.sf.exlp.listener.AbstractLogListener;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.parser.LogParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


public class LogListenerHttp extends AbstractLogListener implements LogListener
{
	static Log logger = LogFactory.getLog(LogListenerHttp.class);

	private HttpClient httpclient;
	
	public LogListenerHttp(LogParser lp)
	{
		super(lp);
		httpclient = new DefaultHttpClient();
	}
	
	@Override
	public void processSingle(String url)
	{
		try
		{
			HttpGet httpget = new HttpGet(url); 

	        logger.debug("executing request " + httpget.getURI());
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        String responseBody;
			responseBody = httpclient.execute(httpget, responseHandler);
			String[] poList = responseBody.split("\r|\n|\r\n");
			for(String line : poList)
			{
				lp.parseLine(line);
			}
		}
		catch (ClientProtocolException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}         
	}
	
	@Override
	public void close()
	{
		httpclient.getConnectionManager().shutdown();    
	}
}