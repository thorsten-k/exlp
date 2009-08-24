package net.sf.exlp.loc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import net.sf.exlp.loc.CounterSelector.Lang;
import net.sf.exlp.loc.counter.AllCounter;
import net.sf.exlp.loc.counter.BatCounter;
import net.sf.exlp.loc.counter.CommonCounter;
import net.sf.exlp.loc.counter.MakefileCounter;
import net.sf.exlp.loc.counter.MatlabCounter;
import net.sf.exlp.loc.counter.SimpleCounter;
import net.sf.exlp.loc.counter.SqlCounter;
import net.sf.exlp.loc.counter.UnknownCounter;
import net.sf.exlp.loc.counter.VisualBasicCounter;

/** Main class to execute the internal LOC counting logic for JLOC.
 *
 * @author Thorsten Kisner
 *
 */
public class Loccer
{
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	Map<Lang,ArrayList<BasicFileInfo>> mapStatistics;
    CounterSelector cSel;
    
    public Loccer()
    {
    	cSel = new CounterSelector();
    	mapStatistics = new Hashtable<Lang,ArrayList<BasicFileInfo>>();
    }

    public Loccer(String startDir) throws IOException
    {
        this();
        scan(new File(startDir));
    }
    
    // >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<
    
    public void scan(File s) throws IOException
    {
        File[] arr = s.listFiles();
        if (arr != null)
        {
            for (File fs : arr)
            {
                if (fs.isDirectory()) {scan(fs);}
                else if (fs.isFile()) {countLoc(fs);}
            }
        } else {// Parameter passed is a single File
            // count the LOCs right away
            countLoc(s);
        }
    }
    
    public void countLoc(File f) throws IOException
    {  
        Lang fLang = cSel.getLang(f.getName());
        SimpleCounter ob = null;  // instantiation of CommonCounter or children
        
        // set the ob object up with the right source-specific counter
        switch(fLang)
        {
        	case java: ob = new CommonCounter(f);break;
        	case cpp:  ob = new CommonCounter(f);break;
        	case sql:  ob = new SqlCounter(f);break;
        	case vb:   ob = new VisualBasicCounter(f);break;
        	case bat:  ob = new BatCounter(f);break;
        	case make: ob = new MakefileCounter(f);break;
        	case perl: ob = new MakefileCounter(f);break;
        	case matlab: ob = new MatlabCounter(f);break;
        	case xml: ob = new AllCounter(f);break;
        	case jsp: ob = new AllCounter(f);break;
        	case css: ob = new AllCounter(f);break;
        	case unknown: ob = new UnknownCounter(f);break;
        }
        
        if (ob != null)
        {
            BasicFileInfo bfi = ob.countlines();
            bfi.setNumberOfFiles(1);
            addStat(fLang, bfi);
        }
    }
    
    private void addStat(Lang lang, BasicFileInfo stats)
    {
    	if(!mapStatistics.containsKey(lang)){mapStatistics.put(lang, new ArrayList<BasicFileInfo>());}
    	mapStatistics.get(lang).add(stats);
    }
    
    // >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<
    
    public Map<Lang,BasicFileInfo> getSummary()
    {
    	Map<Lang,BasicFileInfo> result = new Hashtable<Lang,BasicFileInfo>();
    	for(Lang lang : mapStatistics.keySet())
    	{
    		BasicFileInfo bfiLang = new BasicFileInfo();
    		for(BasicFileInfo bfi : mapStatistics.get(lang))
    		{
    			bfiLang.addStats(bfi);
    		}
    		result.put(lang, bfiLang);
    	}
    	return result;
    }
}