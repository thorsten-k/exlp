package org.exlp.model.json.config;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="property")
public class JsonConfigurationProperty implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("key")
	private String key;
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}

	@JsonProperty("value")
	private String value;
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
}