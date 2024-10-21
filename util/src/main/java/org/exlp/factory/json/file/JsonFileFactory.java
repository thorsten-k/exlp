package org.exlp.factory.json.file;

import org.exlp.model.json.file.JsonFile;

public class JsonFileFactory
{	
	public static JsonFile build() {return new JsonFile();}
	
	public static JsonFile build(String name)
	{
		JsonFile json = JsonFileFactory.build();
		json.setName(name);
		return json;
	}
	
	public static JsonFile build(java.io.File f)
	{
		JsonFile json = JsonFileFactory.build();
		json.setName(f.getName());
		
		return json;
	}
}
