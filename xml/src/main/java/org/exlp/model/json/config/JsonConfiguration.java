package org.exlp.model.json.config;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="configuration")
public class JsonConfiguration implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("configurations")
	private List<JsonConfiguration> configurations;
	public List<JsonConfiguration> getConfigurations() {return configurations;}
	public void setConfigurations(List<JsonConfiguration> configurations) {this.configurations = configurations;}

	@JsonProperty("code")
	private String code;
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	@JsonProperty("properties")
	private List<JsonConfigurationProperty> properties;
	public List<JsonConfigurationProperty> getProperties() {return properties;}
	public void setProperties(List<JsonConfigurationProperty> properties) {this.properties = properties;}
	
	
}