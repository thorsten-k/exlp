package net.sf.exlp.factory.xml.config;

import org.exlp.model.xml.config.Parameter;

public class XmlParameterFactory
{
	public static Parameter build(String key, String description,boolean required)
	{
		Parameter xml = new Parameter();
		xml.setKey(key);
		xml.setDescription(description);
		xml.setRequired(required);
		return xml;
	}
}
