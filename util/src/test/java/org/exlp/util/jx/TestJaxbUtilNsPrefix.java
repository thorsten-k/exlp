package org.exlp.util.jx;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.exlp.test.CapturingAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestJaxbUtilNsPrefix
{
	private static final String NAMESPACE = "http://exlp.sf.net/io";

	@AfterEach
	public void resetMapper() {JaxbUtil.setNsPrefixMapper(null);}

	@Test
	public void appliesPrefixesOfConfiguredMapper()
	{
		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());

		String xml = JaxbUtil.toString(new JxNsFixture());

		Assertions.assertTrue(xml.contains("xmlns:io=\""+NAMESPACE+"\""), xml);
	}

	@Test
	public void writesWithoutCustomPrefixesForRejectedMapper()
	{
		JaxbUtil.setNsPrefixMapper(new ForeignNsPrefixMapper());
		CapturingAppender appender = CapturingAppender.attach(JaxbUtil.class);
		try
		{
			String xml = JaxbUtil.toString(new JxNsFixture());

			Assertions.assertFalse(xml.contains("xmlns:io="), xml);
			String warning = "Namespace prefix mapper "+ForeignNsPrefixMapper.class.getName()+" rejected by the JAXB RI";
			Assertions.assertTrue(appender.messages().contains(warning), appender.messages().toString());
		}
		finally
		{
			appender.detach(JaxbUtil.class);
		}
	}

	@Test
	public void writesRiDefaultsWithoutMapper()
	{
		String xml = JaxbUtil.toString(new JxNsFixture());

		Assertions.assertFalse(xml.contains("xmlns:io="), xml);
	}

	@Test
	public void appliesOneConfigurationToEveryMethod() throws Exception
	{
		JaxbUtil.setNsPrefixMapper(new ExlpNsPrefixMapper());
		JxNsFixture xml = new JxNsFixture();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JaxbUtil.output(os, xml, true);

		Assertions.assertTrue(JaxbUtil.toString(xml).contains("xmlns:io="));
		Assertions.assertTrue(new String(JaxbUtil.toBytes(xml), StandardCharsets.UTF_8).contains("xmlns:io="));
		Assertions.assertTrue(new String(os.toByteArray(), StandardCharsets.UTF_8).contains("xmlns:io="));
		Assertions.assertEquals("io", JaxbUtil.toDocument(xml).getRootElement().getNamespacePrefix());
		Assertions.assertTrue(serialize(JaxbUtil.toW3CDocument(xml)).contains("xmlns:io="));
	}

	private static String serialize(org.w3c.dom.Document doc) throws Exception
	{
		StringWriter sw = new StringWriter();
		TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(sw));
		return sw.toString();
	}
}
