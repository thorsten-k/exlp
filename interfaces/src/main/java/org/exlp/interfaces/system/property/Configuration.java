package org.exlp.interfaces.system.property;

public interface Configuration
{
	boolean containsKey(String key);
	
	String getString(String key);
	String getString(String key, String fallback);
	
	int getInt(String key);
	int getInt(String key, int fallback);
	
	Integer getInteger(String key);
	Integer getInteger(String key, Integer fallback);
	
	Boolean getBoolean(String key);
	Boolean getBoolean(String key, Boolean fallback);
}