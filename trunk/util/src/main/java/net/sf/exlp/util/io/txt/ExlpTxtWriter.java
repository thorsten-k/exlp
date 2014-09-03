package net.sf.exlp.util.io.txt;

import java.util.List;

import net.sf.exlp.interfaces.util.TextWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExlpTxtWriter extends AbstractTxtWriter implements TextWriter
{
	final static Logger logger = LoggerFactory.getLogger(ExlpTxtWriter.class);
	
	public ExlpTxtWriter()
	{
		
	}
	
	public void add(String line){if(line!=null){txt.add(line);}}
	public void add(List<String> lines){txt.addAll(lines);}
}
