package net.sf.exlp.util.io.txt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpTxtWriter extends AbstractTxtWriter
{
	final static Logger logger = LoggerFactory.getLogger(ExlpTxtWriter.class);
	
	public ExlpTxtWriter()
	{
		
	}
	
	public void add(String line){txt.add(line);}
	public void add(List<String> lines){txt.addAll(lines);}
}
