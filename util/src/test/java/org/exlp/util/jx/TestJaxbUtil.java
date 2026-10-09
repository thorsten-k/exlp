package org.exlp.util.jx;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.exlp.model.xml.io.Dir;
import org.exlp.model.xml.net.Database;
import org.exlp.test.ExlpBootstrap;
import org.jdom2.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Verifies the general serialization/deserialization behaviour of {@link JaxbUtil}. The namespace
 * prefix handling is covered separately by {@link TestJaxbUtilNsPrefix}.
 */
public class TestJaxbUtil
{
	final static Logger logger = LoggerFactory.getLogger(TestJaxbUtil.class);

	private static final String NAMESPACE = "http://exlp.sf.net/io";

	@AfterEach
	public void resetMapper() {JaxbUtil.setNsPrefixMapper(null);}

	private Dir build()
	{
		Dir dir = new Dir();
		dir.setCode("test");
		dir.getFile().add(new org.exlp.model.xml.io.File());
		return dir;
	}

	@Test
	public void toStringHasXmlPreamble()
	{
		String xml = JaxbUtil.toString(build());

		Assertions.assertTrue(xml.startsWith("<?xml version"), xml);
	}

	@Test
	public void toStringContainsRootAndAttributes()
	{
		String xml = JaxbUtil.toString(build());
		logger.debug(xml);

		Assertions.assertTrue(xml.contains("code=\"test\""), xml);
		Assertions.assertTrue(xml.contains(NAMESPACE), xml);
		Assertions.assertTrue(xml.contains("file"), xml);
	}

	@Test
	public void toBytesRoundTrip()
	{
		Dir actual = JaxbUtil.load(JaxbUtil.toBytes(build()), Dir.class);

		Assertions.assertNotNull(actual);
		Assertions.assertEquals("test", actual.getCode());
		Assertions.assertEquals(1, actual.getFile().size());
	}

	@Test
	public void toInputStreamRoundTrip() throws Exception
	{
		InputStream is = JaxbUtil.toInputStream(build(), true);

		Dir actual = JaxbUtil.loadJAXB(is, Dir.class);

		Assertions.assertNotNull(actual);
		Assertions.assertEquals("test", actual.getCode());
	}

	@Test
	public void copyRoundTrip()
	{
		Dir actual = JaxbUtil.copy(build(), Dir.class);

		Assertions.assertNotNull(actual);
		Assertions.assertEquals("test", actual.getCode());
		Assertions.assertEquals(1, actual.getFile().size());
	}

	@Test
	public void loadJaxbFromInputStream()
	{
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+"<dir xmlns=\""+NAMESPACE+"\" code=\"inStream\"/>";

		Dir actual = JaxbUtil.loadJAXB(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)), Dir.class);

		Assertions.assertNotNull(actual);
		Assertions.assertEquals("inStream", actual.getCode());
	}

	@Test
	public void loadJaxbFromResourceName() throws Exception
	{
		Database db = JaxbUtil.loadJAXB("data/xml/net/Database.xml", Database.class);

		Assertions.assertNotNull(db);
		Assertions.assertEquals("myUser", db.getUser());
		Assertions.assertEquals("myDb", db.getDatabase());
	}

	@Test
	public void loadJaxbFromFile(@TempDir Path tmp) throws Exception
	{
		File f = tmp.resolve("dir.xml").toFile();
		JaxbUtil.save(f, build(), true);

		Dir actual = JaxbUtil.loadJAXB(f, Dir.class);

		Assertions.assertNotNull(actual);
		Assertions.assertEquals("test", actual.getCode());
	}

	@Test
	public void saveAndLoadRoundTrip(@TempDir Path tmp) throws Exception
	{
		File f = tmp.resolve("roundtrip.xml").toFile();
		JaxbUtil.save(f, build(), true);
		Assertions.assertTrue(f.exists());

		Dir actual = JaxbUtil.loadJAXB(f, Dir.class);

		Assertions.assertEquals("test", actual.getCode());
		Assertions.assertEquals(1, actual.getFile().size());
	}

	@Test
	public void saveAndLoadGzipRoundTrip(@TempDir Path tmp) throws Exception
	{
		File f = tmp.resolve("roundtrip.xml.gz").toFile();
		JaxbUtil.save(f, build(), true);
		Assertions.assertTrue(f.exists());

		Dir actual = JaxbUtil.loadJAXB(f, Dir.class);

		Assertions.assertEquals("test", actual.getCode());
	}

	@Test
	public void savePathOverload(@TempDir Path tmp) throws Exception
	{
		Path p = tmp.resolve("dir.xml");
		JaxbUtil.instance().save(p, build());

		File f = p.toFile();
		Assertions.assertTrue(f.exists());

		Dir actual = JaxbUtil.loadJAXB(f, Dir.class);
		Assertions.assertEquals("test", actual.getCode());
	}

	@Test
	public void toDocumentRootElement()
	{
		Document doc = JaxbUtil.toDocument(build());

		Assertions.assertNotNull(doc);
		Assertions.assertEquals("dir", doc.getRootElement().getName());
	}

	@Test
	public void toW3CDocumentRootElement()
	{
		org.w3c.dom.Document doc = JaxbUtil.toW3CDocument(build());

		Assertions.assertNotNull(doc);
		Assertions.assertNotNull(doc.getDocumentElement());

		// The DocumentBuilderFactory of JaxbUtil is not namespace aware, therefore the local name is
		// null and the (possibly prefixed) node name carries the element name.
		String root = doc.getDocumentElement().getNodeName();
		Assertions.assertTrue(root.equals("dir") || root.endsWith(":dir"), root);
	}

	@Test
	public void logMethodsDoNotThrow()
	{
		Assertions.assertDoesNotThrow(() -> JaxbUtil.debug(build()));
		Assertions.assertDoesNotThrow(() -> JaxbUtil.info(build()));
		Assertions.assertDoesNotThrow(() -> JaxbUtil.warn(build()));
		Assertions.assertDoesNotThrow(() -> JaxbUtil.error(build()));
	}

	public static void main(String[] args)
	{
		ExlpBootstrap.init();

		new TestJaxbUtil().toStringContainsRootAndAttributes();
	}
}
