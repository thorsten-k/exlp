/*
 * MakefileCounter.java
 *
 * Created on April 29, 2006, 3:25 PM
 *
 */
package net.sf.exlp.loc.counter;

import java.io.File;


/**
 * An implementation of ILineCounter which can be used to process Makefiles.
 * @author Ekkehard Blanz
 */
public class MakefileCounter extends SimpleCounter implements LineCounter {
    
    /** Creates a new instance of MakefileCounter */
    public MakefileCounter(File f) {
        super(f, "#");
    }
}
