package org.exlp.maven.reachability;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Reflection registration of one class selected by a configured annotation; it registers all declared
 * constructors, fields, and methods of the class.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder({"type","allDeclaredConstructors","allDeclaredFields","allDeclaredMethods"})
public class ReflectionRegistration
{
	private String type;
	private boolean allDeclaredConstructors;
	private boolean allDeclaredFields;
	private boolean allDeclaredMethods;

	public ReflectionRegistration() {}

	public ReflectionRegistration(String type)
	{
		this.type = type;
		this.allDeclaredConstructors = true;
		this.allDeclaredFields = true;
		this.allDeclaredMethods = true;
	}

	public String getType() {return type;}
	public void setType(String type) {this.type = type;}

	public boolean isAllDeclaredConstructors() {return allDeclaredConstructors;}
	public void setAllDeclaredConstructors(boolean allDeclaredConstructors) {this.allDeclaredConstructors = allDeclaredConstructors;}

	public boolean isAllDeclaredFields() {return allDeclaredFields;}
	public void setAllDeclaredFields(boolean allDeclaredFields) {this.allDeclaredFields = allDeclaredFields;}

	public boolean isAllDeclaredMethods() {return allDeclaredMethods;}
	public void setAllDeclaredMethods(boolean allDeclaredMethods) {this.allDeclaredMethods = allDeclaredMethods;}
}
