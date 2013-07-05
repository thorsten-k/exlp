package net.sf.exlp.addon.exim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.addon.exim.data.facade.exlp.ExlpEximFacade;
import net.sf.exlp.interfaces.util.PatternLibrary;
import net.sf.exlp.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EximRafDateSeeker
{
	final static Logger logger = LoggerFactory.getLogger(EximRafDateSeeker.class);
	
	private ExlpEximFacade fExim;
	private Date lastEximDbRecord;
	private RandomAccessFile raf;
	private Pattern p;
	
	public EximRafDateSeeker(ExlpEximFacade fExim)
	{
		this.fExim=fExim;
		p=Pattern.compile(PatternLibrary.eximDate+" "+PatternLibrary.eximTime+"(.*)");
	}
	
	public long seek(String fileName)
	{
		long pos = 0;
		lastEximDbRecord = fExim.fLastLogDate();
		logger.debug("Last: "+lastEximDbRecord);
//		lastEximDbRecord = DateUtil.getDateFromInt(2010,8,24);
//		logger.debug("But using: "+lastEximDbRecord);
		
		try
		{
			File f = new File(fileName);
			raf = new RandomAccessFile(f,"r");
			logger.debug("Lenght: "+raf.length());
			pos = search(0,1000000);
			logger.debug("Found pos = "+pos);
			raf.seek(pos);
			raf.readLine();
			logger.debug(raf.readLine());
		}
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
		return pos;
	}
	
	private long search(long start, long steps) throws IOException
	{
		boolean isBefore=seekerBeforeDate(start);
		while(start<raf.length() && isBefore)
		{
			raf.seek(start);
			start=start+steps;
			isBefore = seekerBeforeDate(start);
		}
		if((start-(2*steps))>0){start = start - (2*steps);}
		else if((start-steps)>0){start = start - steps;}
//		logger.debug("correcting to "+start);
		if(isBefore || steps <1111){return start;}
		else{return search(start, steps/10);}
	}
	
	private boolean seekerBeforeDate(long pos) throws IOException
	{
		raf.seek(pos);
		raf.readLine();
		Matcher m=p.matcher(raf.readLine());
		if(m.matches())
		{
			Date lineTime = DateUtil.getDateFromString(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6));
//			logger.debug(pos+" "+lineTime);
			if(lineTime.before(lastEximDbRecord)){return true;}
		}
		return false;
	}
}
