package net.sf.exlp.util.io.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipFolder
{
	final static Logger logger = LoggerFactory.getLogger(ZipFolder.class);
	
	private List<String> fileList;
	private File sourceFolder;
	
	public ZipFolder()
    {
    	fileList = new ArrayList<String>();
    }
	
    public void zip(File sourceFolder, File zipFile)
    {
    	this.sourceFolder=sourceFolder;
    	generateFileList(sourceFolder);
    	generateZip(zipFile);
    }
    
    public void generateFileList(File node)
    {
    	if(node.isFile())
    	{
    		fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
    	}
		
    	if(node.isDirectory())
    	{
    		String[] subNote = node.list();
    		for(String filename : subNote)
    		{
    			generateFileList(new File(node, filename));
    		}
    	}
    }
   
	public void generateZip(File zipFile)
    {
		byte[] buffer = new byte[1024];
    	
		try
		{
    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);
    		
    	logger.info("Output to ZIP : "+zipFile.getAbsolutePath());
    		
    	for(String file : fileList)
    	{
    		logger.warn("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
               
        	FileInputStream in = new FileInputStream(sourceFolder.getAbsolutePath() + File.separator + file);
       	   
        	int len;
        	while ((len = in.read(buffer)) > 0)
        	{
        		zos.write(buffer, 0, len);
        	}
               
        	in.close();
    	}
    		
    	zos.closeEntry();
    	zos.close();
          
    	System.out.println("Done");
    }
     catch(IOException ex){
       ex.printStackTrace();   
    }
   }
    

    private String generateZipEntry(String file)
    {
    	return file.substring(sourceFolder.getAbsolutePath().length()+1, file.length());
    }
}
