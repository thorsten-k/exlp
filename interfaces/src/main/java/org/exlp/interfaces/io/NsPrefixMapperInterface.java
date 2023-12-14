package org.exlp.interfaces.io;

public interface NsPrefixMapperInterface
{
	String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix);
	String[] getPreDeclaredNamespaceUris();
}
