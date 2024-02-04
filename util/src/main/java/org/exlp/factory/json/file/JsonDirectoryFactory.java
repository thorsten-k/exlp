package org.exlp.factory.json.file;

import org.exlp.model.json.file.JsonDirectory;

public class JsonDirectoryFactory
{	
	public static JsonDirectory build() {return new JsonDirectory();}
	
	public static JsonDirectory build(String name)
	{
		JsonDirectory json = JsonDirectoryFactory.build();
		json.setName(name);
		return json;
	}
}
