/*
 * SqlCounter.java
 *
 * Created on April 29, 2006, 11:22 AM
 *
 */
package net.sf.exlp.loc.counter;


import java.io.File;


/**
 * An implementation of ILineCounter which can be used to process SQL source
 * files.
 * @author Ekkehard Blanz
 */
public class SqlCounter extends CommonCounter implements LineCounter {
    
    /** Creates a new instance of SqlCounter */
    public SqlCounter(File f) {
        super(f);
        super.CommentHeader = "--";
    }
}
