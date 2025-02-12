package org.exlp.interfaces.system.property;

public interface Configuration
{
	String getString(String key);
	String getString(String key, String fallback);
	
	int getInt(String key);
	int getInt(String key, int fallback);
	
	Boolean getBoolean(String key);
	Boolean getBoolean(String key, Boolean fallback);
}