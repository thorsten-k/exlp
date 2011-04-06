package net.sf.exlp.loc;

/**
 * Class chooses the specific Counter
 *
 * @author Thorsten Kisner
 * extended from jloc (MANISH SHARMA & Ekkehard Blanz)
 */
public class CounterSelector
{ 
	public static enum Lang{donotcount,unknown,xml,jsp,css,java,cpp,sql,vb,bat,make,matlab,fortran,pascal,perl};
    
	String fileName;
	
    public CounterSelector()
    {

    }
    
    public Lang getLang(String fileName)
    {
        Lang result = Lang.unknown;

        if (fileName.endsWith(".java")) {result = Lang.java;}
        else if (fileName.endsWith(".cpp") ||
                fileName.endsWith(".hpp") ||
                fileName.endsWith(".c") ||
                fileName.endsWith(".h") ) {result = Lang.cpp;}
        else if (fileName.endsWith(".sql") ) {result = Lang.sql;}
        else if (fileName.endsWith(".xml") ||
        		fileName.endsWith(".jrxml")) {result = Lang.xml;}
        else if (fileName.endsWith(".css") ) {result = Lang.css;}
        else if (fileName.endsWith(".jsf") || 
        		fileName.endsWith(".jsp") ||
        		fileName.endsWith(".jspx")) {result = Lang.jsp;}
        else if (fileName.endsWith(".bas") ||
               fileName.endsWith(".cls") ||
               fileName.endsWith(".ctl") ) {result = Lang.vb;}
        else if (fileName.endsWith(".bat") ){result = Lang.bat;}
        else if (fileName.endsWith("Makefile") ||
                fileName.endsWith(".mak")){result = Lang.make;}
        else if (fileName.endsWith(".m") ){result = Lang.matlab;}
        else if (fileName.endsWith(".ftn") ||
                fileName.endsWith(".for")){result = Lang.fortran;}
        else if (fileName.endsWith(".pas") ){result = Lang.pascal;}
        else if (fileName.endsWith(".pl") ){result = Lang.perl;}
        else if (fileName.equals("entries") ||
        		fileName.equals("all-wcprops") ||
        		fileName.endsWith("svn-base"))
        		{result = Lang.donotcount;}
        
        if(result==Lang.unknown){System.out.println(result+": "+fileName);}
        
        return result;
    }
    
    /** Method to find out whether the filename has an extension.
     */
    public boolean StemOnly() {
        int intSlash;
        int intDot;
        
        intSlash = fileName.lastIndexOf('\\');
        if (-1 == intSlash) {
            intSlash = fileName.lastIndexOf('/');
        }
        
        intDot = fileName.lastIndexOf('.');
        
        return intDot <= intSlash;
    }
    
    /** Method to add an extension to a filename.
     * @param Extension String containing the extension
     */
    public void AddExtension(String Extension) {
        if (Extension.startsWith(".")) {
            fileName += Extension;
        } else {
            fileName = fileName + "." + Extension;
        }
    }
    
    /** Method to abbreviate the root of a long filename with ellipses.
     * @param strRoot String containing the root
     */
    public void AbbrevRoot(String strRoot){
        
        if (strRoot != null) {
            // spare the screen real estate
            fileName = fileName.replace(strRoot, "...");
        }
        
    }
      
}
