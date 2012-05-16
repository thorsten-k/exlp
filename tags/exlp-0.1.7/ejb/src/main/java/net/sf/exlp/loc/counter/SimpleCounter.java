/*
 * SimpleCounter.java
 *
 * Created on May 07, 2006, 9:48 AM
 */
package net.sf.exlp.loc.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.sf.exlp.loc.BasicFileInfo;

/**
 * This counter can be used directly for all languages that don't allow comments
 * to span several lines and use a particular string to indicate the beginning
 * of a comment.
 * @author Ekkehard Blanz
 */
public class SimpleCounter implements LineCounter {
        
    protected File inputfile;
    protected String CommentHeader;
    
    /** Creates a new instance of SimpleCounter.
     * @param f File to be counted
     * @param Header String containing the comment header for a given language
     */
    public SimpleCounter(File f, String Header) {
        this.inputfile = f;
        this.CommentHeader = Header;
    }
    
    
    /** Counts net LOCs, blank lines, comment lines and gross LOCs in source
     * files.
     * @return BasicFileInfo object containing filename and all counts
     */
    public BasicFileInfo countlines()
    throws FileNotFoundException, IOException {
        
        int fsource = 0;    // counter for true source lines
        int fcomments = 0;  // counter for comment lines
        int fblanks = 0;    // counter for blank lines
        String data = null; // string to hold a line of code at a time
        
        // set up a new buffer reader reading from inputfile
        BufferedReader br = new BufferedReader(new FileReader(this.inputfile));
        
        // go over the source file a line at a time
        while ((data = br.readLine()) != null) {
            if (data.trim().startsWith(this.CommentHeader)) {
                // comments are indicated by Comment Header
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
