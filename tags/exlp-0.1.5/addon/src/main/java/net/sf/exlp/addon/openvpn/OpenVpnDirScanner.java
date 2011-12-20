package net.sf.exlp.addon.openvpn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.addon.openvpn.event.OpenVpnCertEvent;
import net.sf.exlp.addon.openvpn.parser.OpenVpnCertParser;
import net.sf.exlp.util.io.RecursiveFileFinder;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenVpnDirScanner
{	
	final static Logger logger = LoggerFactory.getLogger(OpenVpnDirScanner.class);
	
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
		catch (IOException e) {logger.error("",e);}
		return list;
	}
}