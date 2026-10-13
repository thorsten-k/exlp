package org.exlp.util.jx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.exlp.interfaces.io.NsPrefixMapperInterface;
import org.exlp.test.HidingClassLoader;
import org.jdom2.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a runtime which does not carry the namespace prefix mapper type of the other JAXB
 * variant (FR-002) still applies a configured {@link NsPrefixMapperInterface}. The {@link JaxbUtil}
 * copy loaded by the {@link HidingClassLoader} cannot see the jakarta RI type, so the former hard
 * {@code instanceof} raised a {@code NoClassDefFoundError}.
 */
public class TestJaxbUtilForeignRiAbsent
{
	private static final String FOREIGN_RI_MAPPER = "org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper";
	private static final String NAMESPACE = "http://exlp.sf.net/io";

	@Test
	public void writesPrefixesWhenForeignRiIsAbsent() throws Throwable
	{
		HidingClassLoader cl = new HidingClassLoader(getClass().getClassLoader(), FOREIGN_RI_MAPPER);
		Class<?> util = cl.defineFrom(JaxbUtil.class);

		invoke(util.getMethod("setNsPrefixMapper", NsPrefixMapperInterface.class), null, new ExlpNsPrefixMapper());

		String xml = (String)invoke(util.getMethod("toString", Object.class), null, new JxNsFixture());
		Assertions.assertTrue(xml.contains("xmlns:io=\""+NAMESPACE+"\""), xml);

		Document doc = (Document)invoke(util.getMethod("toDocument", Object.class), null, new JxNsFixture());
		Assertions.assertEquals("io", doc.getRootElement().getNamespacePrefix());
	}

	private static Object invoke(Method method, Object target, Object... args) throws Throwable
	{
		try {return method.invoke(target, args);}
		catch (InvocationTargetException e) {throw e.getCause();}
	}
}
