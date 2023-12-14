package org.exlp.util.jx;



import org.exlp.interfaces.io.NsPrefixMapperInterface;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class ExlpNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
        if("http://exlp.sf.net/io".equals(namespaceUri) ){return "io";}
        if("http://exlp.sf.net/net".equals(namespaceUri) ){return "net";}
        if("http://exlp.sf.net/identity".equals(namespaceUri) ){return "id";}
  
        return suggestion;
    }

    public String[] getPreDeclaredNamespaceUris()
    {
    	String[] result = new String[3];
    	result[2] = "http://www.openfuxml.org/chart";
    	result = new String[0];
        return result;
    }
}