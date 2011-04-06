/*
 * MatlabCounter.java
 *
 * Created on May 07, 2006, 9:48 AM
 *
 */
package net.sf.exlp.loc.counter;

import java.io.File;


/**
 * An implementation of ILineCounter which can be used to process Matlab
 * source files.
 * @author Ekkehard Blanz
 */
public class MatlabCounter extends SimpleCounter implements LineCounter {
    
    /** Creates a new instance of MatlabCounter */
    public MatlabCounter(File f) {
        super(f, "%");
    }
}
