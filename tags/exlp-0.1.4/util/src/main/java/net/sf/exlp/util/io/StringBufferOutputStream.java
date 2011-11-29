package net.sf.exlp.util.io;

import java.io.OutputStream;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringBufferOutputStream extends OutputStream
{
	final static Logger logger = LoggerFactory.getLogger(StringBufferOutputStream.class);
	
	protected StringBuffer sb = new StringBuffer();

	public StringBufferOutputStream()
	{
		logger.warn("This will become deprecated. Try to use a "+StringWriter.class.getSimpleName());
	}

	public void close() {}

	public void flush()
	{
		logger.trace("Flushing ...");
	}

	public void write(byte[] b)
	{
		String str = new String(b);
		sb.append(str);
	}
	
	public void write(byte[] b, int off, int len)
	{
		String str = new String(b, off, len);
		sb.append(str);
	}

	public void write(int b)
	{
		String str = Integer.toString(b);
		sb.append(str);
	}

	public StringBuffer getStringBuffer()
	{
		return sb;
	}
}
