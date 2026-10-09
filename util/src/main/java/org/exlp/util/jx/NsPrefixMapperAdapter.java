package org.exlp.util.jx;

import org.exlp.interfaces.io.NsPrefixMapperInterface;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Adapts a configured {@link NsPrefixMapperInterface} to the namespace prefix mapper type of the
 * javax JAXB RI (FR-002, Assumption 4).
 */
public class NsPrefixMapperAdapter extends NamespacePrefixMapper
{
	private final NsPrefixMapperInterface delegate;

	public NsPrefixMapperAdapter(NsPrefixMapperInterface delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
	{
		return delegate.getPreferredPrefix(namespaceUri, suggestion, requirePrefix);
	}

	@Override
	public String[] getPreDeclaredNamespaceUris()
	{
		return delegate.getPreDeclaredNamespaceUris();
	}
}
