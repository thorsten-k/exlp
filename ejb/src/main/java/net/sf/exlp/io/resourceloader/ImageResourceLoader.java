package net.sf.exlp.io.resourceloader;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageResourceLoader
{
	final static Logger logger = LoggerFactory.getLogger(ImageResourceLoader.class);
	
	public ImageResourceLoader()
	{
		
	}
	
	public synchronized Image search(ClassLoader cl, String resourceName, Display display) throws FileNotFoundException
	{
		MultiResourceLoader mrl = MultiResourceLoader.instance();
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
