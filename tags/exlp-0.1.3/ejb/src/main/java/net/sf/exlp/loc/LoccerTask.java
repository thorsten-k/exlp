package net.sf.exlp.loc;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import net.sf.exlp.loc.CounterSelector.Lang;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

public class LoccerTask extends Task
{
	private Vector<FileSet> filesets = new Vector<FileSet>();
	private Loccer loccer; 
	
	public LoccerTask()
	{
		loccer = new Loccer();
	}
	
	public LoccerTask(String dir)
	{
		try
		{
			loccer = new Loccer(dir);
			printStats(loccer.getSummary());
		}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public static void main(String args[])
	{
		new LoccerTask("/Users/thorsten/Documents/workspace/3.5.0/MWI");
	}
	
    public void execute() throws BuildException
    {
        for(FileSet fs : filesets)
        {
        	for(String s : fs.getDirectoryScanner().getIncludedFiles())
        	{
        		File f = new File(fs.getDir(),s);
        		try {loccer.countLoc(f);}
        		catch (IOException e) {e.printStackTrace();}
        	}
        }
        printStats(loccer.getSummary());
    }
    
    private void printStats(Map<Lang,BasicFileInfo> stats)
    {
    	for(Lang lang : stats.keySet())
    	{
        	BasicFileInfo bfi = stats.get(lang); 
    		System.out.println(lang.toString()+": "+bfi);
    	}
    }
    
    public void addFileset(FileSet fileset) {filesets.add(fileset);}
    
}
