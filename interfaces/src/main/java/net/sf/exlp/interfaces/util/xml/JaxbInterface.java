package net.sf.exlp.interfaces.util.xml;

import java.io.FileNotFoundException;

public interface JaxbInterface
{
	public <T extends Object> T load(Class<T> c, String resourceName) throws FileNotFoundException;
}