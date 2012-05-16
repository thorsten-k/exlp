/*
 * VisualBasicCounter.java
 *
 * Created on April 29, 2006, 9:48 AM
 *
 */
package net.sf.exlp.loc.counter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.exlp.loc.BasicFileInfo;


/**
 * An implementation of ILineCounter which can be used to process Visual Basic
 * source files.
 * @author Ekkehard Blanz
 */
public class UnknownCounter extends SimpleCounter implements LineCounter {
    
    /** Creates a new instance of VisualBasicCounter */
    public UnknownCounter(File f)
    {
        super(f, "'");
    }
    
    public BasicFileInfo countlines() throws FileNotFoundException, IOException
    {
    	return new BasicFileInfo();
    }
}
