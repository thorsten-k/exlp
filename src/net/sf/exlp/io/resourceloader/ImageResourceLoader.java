package net.sf.exlp.io.resourceloader;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ImageResourceLoader
{
	static Logger logger = Logger.getLogger(ImageResourceLoader.class);
	
	public ImageResourceLoader()
	{
		
	}
	
	public synchronized Image search(ClassLoader cl, String resourceName, Display display) throws FileNotFoundException
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		InputStream is = mrl.searchIs(resourceName);
		Image img = new Image(display, is);
		return img;
	}
	
	public synchronized java.awt.Image getAwtImage(String resourceName)
    {
		java.awt.Image img=null;
		File f = new File(resourceName);
		if(f.exists())
		{
			img= Toolkit.getDefaultToolkit().getImage(resourceName);
		}
        return img;
    }
}
