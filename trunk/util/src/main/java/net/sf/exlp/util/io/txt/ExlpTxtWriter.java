package net.sf.exlp.util.io.txt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExlpTxtWriter extends AbstractTxtWriter
{
	static Log logger = LogFactory.getLog(ExlpTxtWriter.class);
	
	public ExlpTxtWriter()
	{
		
	}
	
	public void add(String line){txt.add(line);}
}
