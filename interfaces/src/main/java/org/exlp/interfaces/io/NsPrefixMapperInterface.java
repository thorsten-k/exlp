package org.exlp.interfaces.io;

public interface NsPrefixMapperInterface
{
	public enum Implementation{comSun,orgGlassfish}
	
	String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix);
	String[] getPreDeclaredNamespaceUris();
}
