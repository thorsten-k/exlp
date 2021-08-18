package net.sf.exlp.core.event;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.listener.AbstractLogListener;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.util.io.ObjectIO;
import net.sf.exlp.util.io.StringUtil;

public abstract class AbstractEvent implements LogEvent,Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractLogListener.class);
	
	static final long serialVersionUID=2;
	
	protected String fileName;
	protected Date record;

	transient protected Hashtable<String,String> pString;
	transient protected Hashtable<String,Integer> pnteger;
	transient protected Hashtable<String,Object> myFacades;
	
	public AbstractEvent()
	{
		initProps();
	}
	
	protected void initProps()
	{
		record = new Date();
//		fileName = record.getTime()+"-"+rnd.nextInt(999999999);
		pString = new Hashtable<>();
		pnteger = new Hashtable<>();
		pString.put("Event",this.getClass().getSimpleName());
	}
	
	public void debug()
	{
		StringBuffer sb = new StringBuffer();
		for (String key : pString.keySet())
		{
			sb.append("S:"+key+"="+pString.get(key)+" ");
		}
		for (String key : pnteger.keySet())
		{
			sb.append("I:"+key+"="+pnteger.get(key)+" ");
		}
		
		logger.debug(StringUtil.stars());
		if(fileName!=null) {logger.debug("File\t"+fileName+"."+this.getClass().getSimpleName());}
		logger.debug("Record\t"+DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM).format(record));
		if(sb.length()>0){logger.debug("Properties  "+sb);}
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