package net.sf.exlp.addon.openvpn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.addon.openvpn.event.OpenVpnCertEvent;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.io.RecursiveFileFinder;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;

public class OpenVpnDirScanner
{	
	static Logger logger = Logger.getLogger(OpenVpnDirScanner.class);
	
	private RecursiveFileFinder rff;
	private OpenVpnCertParser certParser;
	
	public OpenVpnDirScanner()
	{
		IOFileFilter df = HiddenFileFilter.VISIBLE;
		IOFileFilter ff = FileFilterUtils.suffixFileFilter(".pem");
	
		rff = new RecursiveFileFinder(df,ff);
		certParser = new OpenVpnCertParser();
	}
	
	public List<OpenVpnCertEvent> getCertEvents(String dir)
	{
		List<OpenVpnCertEvent> list = new ArrayList<OpenVpnCertEvent>();
		try
		{
			List<File> l;
			l = rff.find(new File(dir));
			logger.debug("Size "+l.size());
			for(File f : l)
			{
				OpenVpnCertEvent cert = certParser.getCert(f);
				list.add(cert);
			}
		}
		catch (IOException e) {logger.error(e);}
		return list;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		OpenVpnDirScanner ods = new OpenVpnDirScanner();
		ods.getCertEvents(args[0]);
	}
}