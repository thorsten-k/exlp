package net.sf.exlp.util.xml;

public interface NsPrefixMapperInterface
{
	String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix);
	String[] getPreDeclaredNamespaceUris();
}
