package org.exlp.model.json.file;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonRootName(value="directory")
public class JsonDirectory implements Serializable
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
	
	@JsonProperty("files")
	private List<JsonFile> files;
	public List<JsonFile> getFiles() {return files;}
	public void setgetFilesProperties(List<JsonFile> files) {this.files = files;}
}