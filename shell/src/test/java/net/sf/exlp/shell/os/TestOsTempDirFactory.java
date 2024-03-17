package net.sf.exlp.shell.os;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.exlp.test.AbstractExlpShellTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOsTempDirFactory extends AbstractExlpShellTest
{
	final static Logger logger = LoggerFactory.getLogger(TestOsTempDirFactory.class);
   
	@BeforeEach
	public void init()
	{
		Random rnd = new Random();
		tdf = new OsTempDirFactory(""+rnd.nextInt());
	}
	
	@AfterEach
	public void close() throws IOException
	{
		delete(tdf.build());
	}
	
	@Test
	public void test() throws IOException
	{
		File tmp = tdf.build();
		logger.debug("TMP: "+tmp.getAbsolutePath());
		Assertions.assertTrue(tmp.exists());
	}
	
	@Test
	public void twice() throws IOException
	{
		File expected = tdf.build();
		File actual = tdf.build();
		Assertions.assertEquals(expected.getAbsolutePath(), actual.getAbsolutePath());
	}
	
	@Test
	public void delete() throws IOException
	{
		File tmp = tdf.build();
		delete(tmp);
		Assertions.assertFalse(tmp.exists());
		delete(tmp);
	}
	
	private void delete(File f)
	{
		if(f.exists()){f.delete();}
	}
	
	private OsTempDirFactory tdf;
}