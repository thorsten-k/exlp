package org.exlp.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A class loader that simulates a runtime which does not carry a single class. It hides the configured
 * class and can define a copy of an existing class from the class file of the application class loader,
 * so that the copy cannot see the hidden class (e.g. the namespace prefix mapper type of the other JAXB
 * variant, FR-002).
 */
public class HidingClassLoader extends ClassLoader
{
	private final String hiddenClassName;

	public HidingClassLoader(ClassLoader parent, String hiddenClassName)
	{
		super(parent);
		this.hiddenClassName = hiddenClassName;
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		if(name.equals(hiddenClassName)) {throw new ClassNotFoundException(name);}
		return super.loadClass(name, resolve);
	}

	/**
	 * Defines a copy of the given class in this loader. Every type the copy references resolves through
	 * this loader, thus through the parent except for the hidden class.
	 */
	public Class<?> defineFrom(Class<?> source) throws IOException
	{
		String resource = source.getName().replace('.','/')+".class";
		try(InputStream is = source.getClassLoader().getResourceAsStream(resource))
		{
			if(is == null) {throw new IOException("Resource not found: "+resource);}
			byte[] bytes = read(is);
			return defineClass(source.getName(), bytes, 0, bytes.length);
		}
	}

	private static byte[] read(InputStream is) throws IOException
	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n;
		while((n = is.read(buffer)) != -1) {os.write(buffer, 0, n);}
		return os.toByteArray();
	}
}
