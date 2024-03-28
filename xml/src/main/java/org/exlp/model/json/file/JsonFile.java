package org.exlp.model.json.file;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ByteArraySerializer;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="file")
public class JsonFile implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private Long id;
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	@JsonProperty("parent")
	private JsonDirectory parent;
	public JsonDirectory getParent() {return parent;}
	public void setParent(JsonDirectory parent) {this.parent = parent;}

	@JsonProperty("name")
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	@JsonProperty("hash")
	private String hash;
	public String getHash() {return hash;}
	public void setHash(String hash) {this.hash = hash;}
	
	@JsonSerialize(using = ByteArraySerializer.class)
	@JsonProperty("data")
	private byte[] data;
	public byte[] getData() {return data;}
	public void setData(byte[] data) {this.data = data;}
}