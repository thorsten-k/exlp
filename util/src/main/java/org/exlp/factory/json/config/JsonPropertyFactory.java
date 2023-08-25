package org.exlp.factory.json.config;

import org.exlp.model.json.config.JsonConfigurationProperty;

public class JsonPropertyFactory
{
	public static JsonConfigurationProperty build() {return new JsonConfigurationProperty();}
	
	public static JsonConfigurationProperty build(String key, String value)
	{
		JsonConfigurationProperty json = JsonPropertyFactory.build();
		json.setKey(key);
		json.setValue(value);
		return json;
	}
}
