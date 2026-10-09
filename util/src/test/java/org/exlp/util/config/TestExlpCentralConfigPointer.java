package org.exlp.util.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import org.exlp.model.xml.io.Dir;
import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.jx.JaxbUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpConfigurationException;

/**
 * Verifies the resolution of the ExLP central configuration pointer ({@code $HOME/.m2/exlp.xml}).
 *
 * The pointer file and the referenced configuration file are provided through the
 * {@link ExlpCentralConfigPointer#pointer(java.io.File)} seam into a JUnit {@link TempDir}, so the test
 * is fully self-contained and does not depend on the configuration of the executing machine.
 */
public class TestExlpCentralConfigPointer extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExlpCentralConfigPointer.class);

	private static final String APP = "exlp";
	private static final String CONF = "client";

	private Path tmp;
	private File fPointer;
	private File fConfig;

	@BeforeEach
	public void init(@TempDir Path tmp) throws IOException
	{
		this.tmp = tmp;
		fPointer = tmp.resolve("exlp.xml").toFile();
		fConfig = tmp.resolve("client.properties").toFile();
		Assertions.assertTrue(fConfig.createNewFile(), "Could not create "+fConfig.getAbsolutePath());
		logger.debug("Pointer file: "+fPointer.getAbsolutePath());
	}

	private ExlpCentralConfigPointer pointer(String appCode)
	{
		return ExlpCentralConfigPointer.instance(appCode).jaxb(JaxbUtil.instance()).pointer(fPointer);
	}

	private void savePointer(Dir root)
	{
		JaxbUtil.save(fPointer, root, true);
	}

	private Dir appWithFile(String fileCode, String fileName)
	{
		org.exlp.model.xml.io.File file = new org.exlp.model.xml.io.File();
		file.setCode(fileCode);
		file.setName(fileName);

		Dir app = new Dir();
		app.setCode(APP);
		app.getFile().add(file);

		Dir root = new Dir();
		root.getDir().add(app);
		return root;
	}

	private Dir appWithoutFile()
	{
		Dir app = new Dir();
		app.setCode(APP);

		Dir root = new Dir();
		root.getDir().add(app);
		return root;
	}

	@Test
	public void resolvesConfiguredFile() throws ExlpConfigurationException
	{
		savePointer(appWithFile(CONF, fConfig.getAbsolutePath()));

		File actual = pointer(APP).toFile(CONF);

		Assertions.assertEquals(fConfig.getAbsolutePath(), actual.getAbsolutePath());
	}

	@Test
	public void toPathReturnsConfiguredPath() throws ExlpConfigurationException
	{
		savePointer(appWithFile(CONF, fConfig.getAbsolutePath()));

		Path actual = pointer(APP).toPath(CONF);

		Assertions.assertNotNull(actual);
		Assertions.assertEquals(fConfig.toPath(), actual);
	}

	@Test
	public void createsPointerWhenMissing() throws ExlpConfigurationException, FileNotFoundException
	{
		Assertions.assertFalse(fPointer.exists());

		ExlpCentralConfigPointer ccp = pointer(APP);
		Assertions.assertThrows(ExlpConfigurationException.class, () -> ccp.toFile(CONF));

		Assertions.assertTrue(fPointer.exists(), "Dummy pointer file was not created");
		Dir root = JaxbUtil.loadJAXB(fPointer, Dir.class);
		Assertions.assertEquals(1, root.getDir().size());

		Dir app = root.getDir().get(0);
		Assertions.assertEquals(APP, app.getCode());
		Assertions.assertEquals(1, app.getFile().size());
		Assertions.assertEquals(CONF, app.getFile().get(0).getCode());
		Assertions.assertEquals("/change/me", app.getFile().get(0).getName());
	}

	@Test
	public void appendsDummyDirWhenAppMissing() throws ExlpConfigurationException, FileNotFoundException
	{
		savePointer(new Dir());

		ExlpCentralConfigPointer ccp = pointer(APP);
		Assertions.assertThrows(ExlpConfigurationException.class, () -> ccp.toFile(CONF));

		Dir root = JaxbUtil.loadJAXB(fPointer, Dir.class);
		Assertions.assertEquals(1, root.getDir().size());
		Assertions.assertEquals(APP, root.getDir().get(0).getCode());
		Assertions.assertEquals(CONF, root.getDir().get(0).getFile().get(0).getCode());
	}

	@Test
	public void failsWhenFileCodeMissing()
	{
		savePointer(appWithoutFile());

		ExlpCentralConfigPointer ccp = pointer(APP);
		Assertions.assertThrows(ExlpConfigurationException.class, () -> ccp.toFile(CONF));
	}

	@Test
	public void failsWhenReferencedFileDoesNotExist()
	{
		String missing = tmp.resolve("does-not-exist.properties").toString();
		savePointer(appWithFile(CONF, missing));

		ExlpCentralConfigPointer ccp = pointer(APP);
		ExlpConfigurationException e =
				Assertions.assertThrows(ExlpConfigurationException.class, () -> ccp.toFile(CONF));

		Assertions.assertTrue(e.getMessage().contains("does not exist for app="+APP), e.getMessage());
		Assertions.assertTrue(e.getMessage().contains("code="+CONF), e.getMessage());
	}

	@Test
	public void failsWhenAppCodeNotUnique()
	{
		Dir app1 = new Dir();
		app1.setCode(APP);
		Dir app2 = new Dir();
		app2.setCode(APP);

		Dir root = new Dir();
		root.getDir().add(app1);
		root.getDir().add(app2);
		savePointer(root);

		ExlpCentralConfigPointer ccp = pointer(APP);
		Assertions.assertThrows(ExlpConfigurationException.class, () -> ccp.toFile(CONF));
	}

	@Test
	public void failsWhenFileCodeNotUnique()
	{
		Dir app = new Dir();
		app.setCode(APP);

		org.exlp.model.xml.io.File f1 = new org.exlp.model.xml.io.File();
		f1.setCode(CONF);
		f1.setName(fConfig.getAbsolutePath());
		org.exlp.model.xml.io.File f2 = new org.exlp.model.xml.io.File();
		f2.setCode(CONF);
		f2.setName(fConfig.getAbsolutePath());
		app.getFile().add(f1);
		app.getFile().add(f2);

		Dir root = new Dir();
		root.getDir().add(app);
		savePointer(root);

		ExlpCentralConfigPointer ccp = pointer(APP);
		Assertions.assertThrows(ExlpConfigurationException.class, () -> ccp.toFile(CONF));
	}

	@Test
	public void toPathReturnsNullWhenNotAvailable()
	{
		savePointer(appWithoutFile());

		Assertions.assertNull(pointer(APP).toPath(CONF));
	}

	@Test
	public void instanceUsesEnumAppCode() throws ExlpConfigurationException, FileNotFoundException
	{
		ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance(ExlpBootstrap.System.exlp)
				.jaxb(JaxbUtil.instance())
				.pointer(fPointer);

		Assertions.assertThrows(ExlpConfigurationException.class, () -> ccp.toFile(CONF));

		Dir root = JaxbUtil.loadJAXB(fPointer, Dir.class);
		Assertions.assertEquals(ExlpBootstrap.System.exlp.toString(), root.getDir().get(0).getCode());
	}

	@Test
	public void instanceUsesStringAppCode()
	{
		Assertions.assertNotNull(ExlpCentralConfigPointer.instance(APP));
	}
}
