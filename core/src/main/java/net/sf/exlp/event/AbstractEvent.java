package net.sf.exlp.event;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.listener.AbstractLogListener;
import net.sf.exlp.util.io.ObjectIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEvent implements LogEvent,Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractLogListener.class);
	
	static final long serialVersionUID=2;
	
	protected String fileName;
	protected Date record;

	transient protected Hashtable<String,String> propStr;
	transient protected Hashtable<String,Integer> propInt;
	transient protected Hashtable<String,Object> myFacades;
	
	transient private Random rnd;
	
	public AbstractEvent()
	{
		initProps();
	}
	
	protected void initProps()
	{
		rnd = new Random();
		record = new Date();
		fileName = record.getTime()+"-"+rnd.nextInt(999999999);
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
	
	public boolean save(File dir)
	{
		File f = new File(dir,fileName);
		ObjectIO.save(f.getAbsolutePath(), (Object)this);
		return true;
	}
	
	public boolean persist(Map<String,Object> mapFacades){logger.error("Event Handling \"persist\" not implemented!");return false;}
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
}
