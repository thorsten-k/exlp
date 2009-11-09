package net.sf.exlp.event;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class AbstractEvent implements LogEvent,Serializable
{
	static final long serialVersionUID=2;
	
	static Logger logger = Logger.getLogger(AbstractEvent.class);
	
	protected  String fileName;
	protected  Date record;
	transient protected Hashtable<String,String> propStr;
	transient protected Hashtable<String,Integer> propInt;
	transient protected Hashtable<String,Object> myFacades;
	
	public AbstractEvent()
	{
		initProps();
	}
	
	protected void initProps()
	{
		record = new Date();
		fileName = record.getTime()+"";
		propStr = new Hashtable<String,String>();
		propInt = new Hashtable<String,Integer>();
		propStr.put("Event",this.getClass().getSimpleName());
	}
	
	public void debug()
	{
		StringBuffer sb = new StringBuffer();
		for (String propName : propStr.keySet())
		{
			sb.append("S:"+propName+"="+propStr.get(propName)+" ");
		}
		for (String propName : propInt.keySet())
		{
			sb.append("I:"+propName+"="+propInt.get(propName)+" ");
		}
		
		logger.debug("** D E B U G ******************************************");
		logger.debug("** File\t"+fileName+"."+this.getClass().getSimpleName());
		logger.debug("** Crte\t"+DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM).format(record));
		if(sb.length()>0){logger.debug("** Props\t"+sb);}
	}
	
	public boolean persist(Map<String,?> mapFacades){logger.error("Event Handling \"persist\" not implemented!");return false;}
}