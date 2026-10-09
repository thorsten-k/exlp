package org.exlp.util.jx;

import org.exlp.interfaces.io.NsPrefixMapperInterface;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

/**
 * A mapper that carries the RI type of the other JAXB variant; the javax RI rejects it (FR-002, AC-FR-002-02).
 */
public class ForeignNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
	@Override public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {return "io";}
	@Override public String[] getPreDeclaredNamespaceUris() {return new String[0];}
}
