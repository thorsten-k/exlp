package net.sf.exlp.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamUtil
{
	final static Logger logger = LoggerFactory.getLogger(StreamUtil.class);
		
	public static List<InputStream> clone(InputStream is, int number) throws IOException
	{
		List<InputStream> list = new ArrayList<InputStream>();
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		IOUtils.copy(is, os);
		byte[] bytes = os.toByteArray();
		os.close();
		
		for(int i=0;i<number;i++)
		{
			list.add(new ByteArrayInputStream(bytes));
		}
		return list;
	}
}