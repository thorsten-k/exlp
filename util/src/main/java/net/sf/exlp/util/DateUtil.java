package net.sf.exlp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil
{
	final static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	public static boolean ignoreTimeZone=false;
	
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
	
	public static XMLGregorianCalendar toXmlGc(Date d) {return toXmlGc(d,DateUtil.ignoreTimeZone);}
    public static XMLGregorianCalendar toXmlGc(Date d, boolean ignoreTimeZone)
    {
        XMLGregorianCalendar xmlGc=null;
        try
        {
            GregorianCalendar gc = new DateTime(d).toGregorianCalendar();
            if(ignoreTimeZone){gc.setTimeZone(TimeZone.getTimeZone("GMT"));}
            xmlGc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        }
        catch (DatatypeConfigurationException e)
        {
            logger.warn(e.getMessage()+", but using fallback");
            xmlGc = getXmlGc4D(d);
        }
        return xmlGc;
    }
	
	public synchronized static XMLGregorianCalendar getXmlGc4D(Date d){return getXmlGc4D(d,false);}
	public synchronized static XMLGregorianCalendar getXmlGc4D(Date d, boolean withMilli)
	{
		// http://stackoverflow.com/questions/835889/java-util-date-to-xmlgregoriancalendar
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
			if(withMilli){xmlGC.setMillisecond(gc.get(GregorianCalendar.MILLISECOND));}
		}
		catch (DatatypeConfigurationException e) {logger.error("XML", e);}
		return xmlGC;
	}
	
	public synchronized static Date getDate4XmlGc(XMLGregorianCalendar xmlGC)
	{
		return xmlGC.toGregorianCalendar().getTime();
	}
	
	public synchronized static Date getDateFromInt(int year, int month, int day)
	{
		return getDateFromInt(year, month, day, 0, 0, 0);
	}
	
	public static Date getDateFromString(String year, String month, String day, String hour, String min, String sec)
	{
		return getDateFromString(year,month,day,hour,min,sec,0+"");
	}
	public static Date getDateFromString(String year, String month, String day, String hour, String min, String sec, String ms)
	{
		return getDateFromInt(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day), Integer.valueOf(hour), Integer.valueOf(min), Integer.valueOf(sec), Integer.valueOf(ms));
	}
	public static Date getDateFromInt(int year, int month, int day, int hour, int min, int sec)
	{
		GregorianCalendar gc = new GregorianCalendar();
			gc.set(GregorianCalendar.YEAR, year);
			gc.set(GregorianCalendar.MONTH, month-1);
			gc.set(GregorianCalendar.DAY_OF_MONTH, day);
			gc.set(GregorianCalendar.HOUR_OF_DAY, hour);
			gc.set(GregorianCalendar.MINUTE, min);
			gc.set(GregorianCalendar.SECOND, sec);
			gc.set(GregorianCalendar.MILLISECOND, 0);
		return getDateFromInt(year,month,day,hour,min,sec,0);
	}
	public static Date getDateFromInt(int year, int month, int day, int hour, int min, int sec, int ms)
	{
		GregorianCalendar gc = new GregorianCalendar();
			gc.set(GregorianCalendar.YEAR, year);
			gc.set(GregorianCalendar.MONTH, month-1);
			gc.set(GregorianCalendar.DAY_OF_MONTH, day);
			gc.set(GregorianCalendar.HOUR_OF_DAY, hour);
			gc.set(GregorianCalendar.MINUTE, min);
			gc.set(GregorianCalendar.SECOND, sec);
			gc.set(GregorianCalendar.MILLISECOND, ms);
		return gc.getTime();
	}
	
	public synchronized static int getQuarter(XMLGregorianCalendar xmlGC){return getQuarter(DateUtil.getDate4XmlGc(xmlGC));}
	public synchronized static int getQuarter(Date d)
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		int month = gc.get(GregorianCalendar.MONTH)+1;
		if(month<4){return 1;}
		else if(month<7){return 2;}
		else if(month<10){return 3;}
		else if(month<13){return 4;}
		else {return -1;}
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
	
	public synchronized static int getCurrentYear()
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		return gc.get(GregorianCalendar.YEAR);
	}
	
	public synchronized static Date withoutMillis(Date d)
	{
		MutableDateTime mdt = new MutableDateTime(d);
		mdt.setMillisOfSecond(0);
		return mdt.toDate();
	}
	
	public static Date midnightBeginOfMonth(Date date)
	{
		DateTime dt = new DateTime(date);
		return dt.withDayOfMonth(1).withTimeAtStartOfDay().toDate();
	}
	
	public static Date midnightEndOfMonth(Date date)
	{
		DateTime dt = new DateTime(date);
		return dt.withDayOfMonth(1).withTimeAtStartOfDay().plusMonths(1).toDate();
	}
	
	public static Date toDate(LocalDate localDate)
	{
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date toDate(LocalDateTime localDateTime)
	{
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static LocalDate toLocalDate(Date date)
	{
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static LocalDateTime toLocalDateTime(Date date)
	{
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public static List<YearMonth> toMonthCoverage(LocalDate ldStart, LocalDate ldEnd)
	{
		YearMonth ymStart = YearMonth.from(ldStart);
		YearMonth ymEnd = YearMonth.from(ldEnd);
		
		List<YearMonth> list = new ArrayList<>();
		
		long months = ChronoUnit.MONTHS.between(ymStart,ymEnd)+1;
		logger.info(ymStart +" "+ymEnd+" months:"+months);
		for(int i=0;i<months;i++)
		{
			list.add(ymStart.plusMonths(i));
		}
		
		return list;
	}
}
