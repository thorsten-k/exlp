/*
 * BatCounter.java
 *
 * Created on April 29, 2006, 10:41 AM
 *
 */
package net.sf.exlp.loc.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.sf.exlp.loc.BasicFileInfo;

/**
 * An implementation of ILineCounter which can be used to process .bat source
 * files.
 * @author Ekkehard Blanz
 */
public class BatCounter extends SimpleCounter implements LineCounter {
    
    /** Creates a new instance of BatCounter */
    public BatCounter(File f) {
        super(f, "::");
    }
    
    /** Overwrites the method countlines to count LOCs in .bat files as .bat 
     * files need some special treatment.  It then sets up and returns the
     * BasicFileInfo as its paent would.
     * @return BasicFileInfo object containing filename and all counts
     */
    public BasicFileInfo countlines()
    throws FileNotFoundException, IOException {
        
        int fsource = 0;    // counter for true source lines
        int fcomments = 0;  // counter for comment lines
        int fblanks = 0;    // counter for blank lines
        String data = null; // string to hold a line of code at a time
        
        // set up a new buffer reader reading from inputfile
        BufferedReader br = new BufferedReader(new FileReader(super.inputfile));
        
        // go over the source file a line at a time
        while ((data = br.readLine()) != null) {
            // convert everything to upper case as "rem" is not case sensitive
            data = data.toUpperCase();
            if (data.trim().startsWith(super.CommentHeader) ||
                    data.trim().startsWith("REM") ) {
                // in bat files comments are indicated by a :: or REM
                fcomments++;
            } else if (data.trim().equals("")) {
                // a blank line is just that (may contain blanks and tabs)
                fblanks++;
            } else {
                // what is not a comment or a blank line must be code
                fsource++;
            }
        }
        br.close();
        
        // set up the BasicFileInfo and store the name and all counters in it
        BasicFileInfo ob = new BasicFileInfo();
        ob.setFblanks(fblanks);
        ob.setFcomments(fcomments);
        ob.setFsourcelines(fsource);
        ob.setFtotal(fblanks + fcomments + fsource);
        
        return ob;
        
    }
    
}
