package net.sf.exlp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtil
{
	static Logger logger = Logger.getLogger(DateUtil.class);
	
	public static GregorianCalendar getGC4D(Date d)
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		return gc;
	}
	
	public static String mtjsms() {return mtjsms(getGC4D(new Date()));}
	
	public static String mtjsms(Date date)
	{
		return mtjsms(getGC4D(date));
	}
	
	public static String mtjsms(GregorianCalendar gc)
	{
		return DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM).format(gc.getTime());
	}
	
	public static String sms(Date d){return sms(getGC4D(d));}
	public static String sms(GregorianCalendar gc)
	{
		return DateFormat.getTimeInstance(DateFormat.MEDIUM).format(gc.getTime());
	}
	
	public static String tmj(Date d){return tmj(getGC4D(d));}
	public static String tmj(Calendar c)
	{
		SimpleDateFormat df = new SimpleDateFormat( "d.M.y" );
		return df.format(c.getTime());
	}
	
	public static String tm(Date d){return tm(getGC4D(d));}
	public static String tm(Calendar c)
	{
		SimpleDateFormat df = new SimpleDateFormat( "d.M" );
		return df.format(c.getTime());
	}
	
	public static String dayName(Date d){return dayName(getGC4D(d),0);}
	public static String dayName(Date d, int digits){return dayName(getGC4D(d),digits);}
	public static String dayName(Calendar c){return dayName(c, 0);}
	public static String dayName(Calendar c, int digits)
	{
		GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(c.getTime());
		String dayName;
		// This ony works for Java6 !!
		// dayName=gc.getDisplayName(GregorianCalendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMAN);
		
		int dayNumber = gc.get(GregorianCalendar.DAY_OF_WEEK);
		switch (dayNumber)
		{
			case 1: dayName="Sonntag";break;
			case 2: dayName="Montag";break;
			case 3: dayName="Dienstag";break;
			case 4: dayName="Mittwoch";break;
			case 5: dayName="Donnerstag";break;
			case 6: dayName="Freitag";break;
			case 7: dayName="Samstag";break;
			default: dayName="unbekannt";logger.warn("DAY_OF_WEEK "+dayNumber+" unknown");break;
		}
		// Java <6 workaround end
		int endIndex=dayName.length();
		if(digits>0){endIndex=digits;}
		return dayName.substring(0, endIndex);
	}
	
	public static String sm(Date d){return sm(getGC4D(d));}
	public static String sm(Calendar c)
	{
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
	}
	
	public static String timediffTSM(Date t1, Date t2)
	{
		long diff = (t2.getTime() - t1.getTime())/(1000*60);
		double day = Math.floor(diff/(60*24));
		double hour = Math.floor((diff - (day*24*60))/60);
		double min = Math.floor(diff - (day*24*60) - (hour*60));
		
		StringBuffer sb = new StringBuffer();
			if(day>0){sb.append((int)day+"d ");}
			if(hour>0 || day>0){sb.append((int)hour+"h ");}
			sb.append((int)min+"min");
		return sb.toString();
	}
	
	public synchronized static XMLGregorianCalendar getXmlGc4D(Date d)
	{
		XMLGregorianCalendar xmlGC = null;
		try
		{
			GregorianCalendar gc = getGC4D(d);
			DatatypeFactory dtf = DatatypeFactory.newInstance();
			xmlGC = dtf.newXMLGregorianCalendar();
			xmlGC.setYear(gc.get(GregorianCalendar.YEAR));
			xmlGC.setMonth(gc.get(GregorianCalendar.MONTH)+1);
			xmlGC.setDay(gc.get(GregorianCalendar.DAY_OF_MONTH));
			xmlGC.setHour(gc.get(GregorianCalendar.HOUR_OF_DAY));
			xmlGC.setMinute(gc.get(GregorianCalendar.MINUTE));
			xmlGC.setSecond(gc.get(GregorianCalendar.SECOND));
		}
		catch (DatatypeConfigurationException e) {logger.error(e);}
		return xmlGC;
	}
	
	public synchronized static Date getDate4XmlGc(XMLGregorianCalendar xmlGC)
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(GregorianCalendar.YEAR, xmlGC.getYear());
		gc.set(GregorianCalendar.MONTH, xmlGC.getMonth());
		gc.set(GregorianCalendar.DAY_OF_MONTH, xmlGC.getDay());
		return gc.getTime();
	}
	
	public synchronized static Date getDateFromInt(int year, int month, int day)
	{
		return getDateFromInt(year, month, day, 0, 0, 0);
	}
	
	public synchronized static Date getDateFromInt(int year, int month, int day, int hour, int min, int sec)
	{
		GregorianCalendar gc = new GregorianCalendar();
			gc.set(GregorianCalendar.YEAR, year);
			gc.set(GregorianCalendar.MONTH, month-1);
			gc.set(GregorianCalendar.DAY_OF_MONTH, day);
			gc.set(GregorianCalendar.HOUR_OF_DAY, hour);
			gc.set(GregorianCalendar.MINUTE, min);
			gc.set(GregorianCalendar.SECOND, sec);
			gc.set(GregorianCalendar.MILLISECOND, 0);
		return gc.getTime();
	}
	
	public synchronized static int getMonth(String s) throws ParseException
	{
		if(s.equals("Jan")){return 1;}
		else if(s.equals("Feb")){return 2;}
		else if(s.equals("Mar")){return 3;}
		else if(s.equals("Apr")){return 4;}
		else if(s.equals("May")){return 5;}
		else if(s.equals("Jun")){return 6;}
		else if(s.equals("Jul")){return 7;}
		else if(s.equals("Aug")){return 8;}
		else if(s.equals("Sep")){return 9;}
		else if(s.equals("Oct")){return 10;}
		else if(s.equals("Nov")){return 11;}
		else if(s.equals("Dec")){return 12;}
		throw new ParseException("No month found for: "+s,0);
	}
	
	public synchronized static long getDiff(Calendar c1, Calendar c2)
	{
		long time = c1.getTime().getTime() - c2.getTime().getTime();  // Differenz in ms
		long days = Math.round( (double)time / (24. * 60.*60.*1000.) );     // Differenz in Tagen
		return days;
	}
}