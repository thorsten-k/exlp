/*
 * VisualBasicCounter.java
 *
 * Created on April 29, 2006, 9:48 AM
 *
 */
package net.sf.exlp.loc.counter;

import java.io.File;


/**
 * An implementation of ILineCounter which can be used to process Visual Basic
 * source files.
 * @author Ekkehard Blanz
 */
public class VisualBasicCounter extends SimpleCounter implements LineCounter {
    
    /** Creates a new instance of VisualBasicCounter */
    public VisualBasicCounter(File f) {
        super(f, "'");
    }
}
