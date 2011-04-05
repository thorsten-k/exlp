package net.sf.exlp.loc;

/**
 * @author #Thorsten Kisner
 */
public class BasicFileInfo
{
    private int fcomments;
    private int fblanks;
    private int fsourcelines;
    private int ftotal;
    private int numberOfFiles;
    


	public BasicFileInfo()
    {
    	fcomments=0;
    	fblanks=0;
    	fsourcelines=0;
    	ftotal=0;
    	numberOfFiles=0;
    }
    
    public int getFcomments(){return fcomments;}
    public void setFcomments(int fcomments) {this.fcomments = fcomments;}
    
    public int getNumberOfFiles() {return numberOfFiles;}
	public void setNumberOfFiles(int numberOfFiles) {this.numberOfFiles = numberOfFiles;}
    
    public int getFblanks() {
        return fblanks;
    }
    
    public void setFblanks(int fblanks) {
        this.fblanks = fblanks;
    }
    
    public int getFsourcelines() {
        return fsourcelines;
    }
    
    public void setFsourcelines(int fsourcelines) {
        this.fsourcelines = fsourcelines;
    }
    
    public int getFtotal() {
        return ftotal;
    }
    
    public void setFtotal(int ftotal) {
        this.ftotal = ftotal;
    }
    
    public void addStats(BasicFileInfo bfi)
    {
    	fcomments=fcomments+bfi.getFcomments();
    	fblanks=fblanks+bfi.getFblanks();
    	fsourcelines=fsourcelines+bfi.getFsourcelines();
    	ftotal=ftotal+bfi.getFtotal();
    	numberOfFiles=numberOfFiles+bfi.getNumberOfFiles();
    }
    
    public String toString()
    {
    	StringBuffer sb = new StringBuffer();
    		sb.append("files="+numberOfFiles+" source="+fsourcelines+" total="+ftotal);
    	return sb.toString();
    }
}
