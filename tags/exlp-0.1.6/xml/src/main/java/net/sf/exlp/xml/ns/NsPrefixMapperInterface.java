package net.sf.exlp.xml.ns;

public interface NsPrefixMapperInterface
{
	String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix);
	String[] getPreDeclaredNamespaceUris();
}
