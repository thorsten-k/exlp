package net.sf.exlp.util.io;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil
{
	final static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static String stars(){return stars(80);}
	
	public static String stars(int number)
    {
		return StringUtils.repeat("*", number);
    }
}