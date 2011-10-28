package net.sf.exlp.loc.counter;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.exlp.loc.BasicFileInfo;

/**
 * @author Thorsten Kisner
 */
public interface LineCounter
{
	BasicFileInfo countlines() throws FileNotFoundException, IOException;
}
