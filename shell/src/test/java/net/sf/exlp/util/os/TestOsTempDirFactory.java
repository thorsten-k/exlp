package net.sf.exlp.util.os;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import net.sf.exlp.shell.util.OsTempDirFactory;
import net.sf.exlp.test.AbstractExlpShellTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOsTempDirFactory extends AbstractExlpShellTest
{
	final static Logger logger = LoggerFactory.getLogger(TestOsTempDirFactory.class);
   
	@Before
	public void init()
	{
		Random rnd = new Random();
		tdf = new OsTempDirFactory(""+rnd.nextInt());
	}
	
	@After
	public void close() throws IOException
	{
		delete(tdf.build());
	}
	
	@Test
	public void test() throws IOException
	{
		File tmp = tdf.build();
		logger.debug("TMP: "+tmp.getAbsolutePath());
		Assert.assertTrue(tmp.exists());
	}
	
	@Test
	public void twice() throws IOException
	{
		File expected = tdf.build();
		File actual = tdf.build();
		Assert.assertEquals(expected.getAbsolutePath(), actual.getAbsolutePath());
	}
	
	@Test
	public void delete() throws IOException
	{
		File tmp = tdf.build();
		delete(tmp);
		Assert.assertFalse(tmp.exists());
		delete(tmp);
	}
	
	private void delete(File f)
	{
		if(f.exists()){f.delete();}
	}
	
	private OsTempDirFactory tdf;
}