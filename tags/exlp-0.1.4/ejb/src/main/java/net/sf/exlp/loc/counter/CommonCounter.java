package net.sf.exlp.loc.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.sf.exlp.loc.BasicFileInfo;

/**
 * A basic implementation of ILineCounter which can be used to process C, C++,
 * and JAVA source files ("common" source files).
 * @author MANISH SHARMA & Ekkehard Blanz
 */
public class CommonCounter extends SimpleCounter implements LineCounter {
    
    
    /** Creates a new instance of CommonCounter.  This counter overwrites the
     * countline method of its parent with one that not only recognizes single
     * line comments but also those that can span several lines.
     */
    public CommonCounter(File f) {
        super(f, "//");
    }
    
    /** Counts net LOCs, blank lines, comment lines and gross LOCs in "common"
     * files.
     *
     * @return BasicFileInfo object containing filename and all counts
     */
    public BasicFileInfo countlines() throws FileNotFoundException, IOException {
        
        int fsource = 0;
        int fcomments = 0;
        int fblanks = 0;
        boolean inComment;
        String data = null;
        BufferedReader br = new BufferedReader(new FileReader(super.inputfile));
        
        inComment = false;
        
        while ((data = br.readLine()) != null) {
            
            if (inComment) {
                if (data.indexOf("*/") == -1) {
                    fcomments++;
                    continue;
                } else if (data.trim().endsWith("*/")) {
                    fcomments++;
                    inComment = false;
                    continue;
                } else {
                    fsource++;
                    inComment = false;
                    continue;
                }
            }
            
            if (data.trim().startsWith(super.CommentHeader)) {
                fcomments++;
                continue;
            }
            
            if (data.trim().equals("")) {
                fblanks++;
                continue;
            }
            
            if (data.trim().startsWith("/*")) {
                if (data.trim().endsWith("*/") || data.indexOf("*/") == -1) {
                    fcomments++;
                } else {
                    fsource++;
                }
            } else {
                fsource++;
            }
            
            if (data.indexOf("/*") != -1 && data.indexOf("*/") == -1) {
                inComment = true;
            }
        }
        br.close();
        
        BasicFileInfo ob = new BasicFileInfo();
        ob.setFblanks(fblanks);
        ob.setFcomments(fcomments);
        ob.setFsourcelines(fsource);
        ob.setFtotal(fblanks + fcomments + fsource);
        
        return ob;
    }
}

