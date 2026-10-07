package org.exlp.interfaces.util;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public interface JaxbInterface
{
	public <T extends Object> T load(Class<T> c, String resourceName) throws FileNotFoundException;
	public void save(Path p, Object jaxb, boolean formatted);
}