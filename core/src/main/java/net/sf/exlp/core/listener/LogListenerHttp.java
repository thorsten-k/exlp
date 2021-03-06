package net.sf.exlp.core.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogListenerHttp extends AbstractLogListener implements LogListener
{
	final static Logger logger = LoggerFactory.getLogger(LogListenerHttp.class);

	private HttpClient httpclient;
	
	public LogListenerHttp(LogParser lp)
	{
		super(lp);
		httpclient = new DefaultHttpClient();
	}
	
	@Override
	public void processSingle(String url)
	{
		List<String> result = getBody(url);
		for(String line : result)
		{
			lp.parseLine(line);
		}
		lp.close();
	}
	
	@Override
	public void processMulti(String url)
	{
		List<String> result = getBody(url);
		lp.parseItem(result);
		lp.close();
	}
	
	private List<String> getBody(String url)
	{
		List<String> result = new ArrayList<String>();
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
				result.add(line);
			}
		}
		catch (ClientProtocolException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
		return result;
	}
	
	@Override
	public void close()
	{
		httpclient.getConnectionManager().shutdown();    
	}
}