package org.exlp.factory.json.config;

import org.exlp.model.json.config.JsonConfiguration;

public class JsonConfigurationFactory
{
	public static JsonConfiguration build() {return new JsonConfiguration();}
	
	public static JsonConfiguration build(String code)
	{
		JsonConfiguration json = JsonConfigurationFactory.build();
		json.setCode(code);
		return json;
	}
}
