package org.exlp.maven.reachability;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Reflection registration of one class; the union of all configured annotations that match the class.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder({"type","allDeclaredConstructors","allDeclaredFields","allDeclaredMethods"})
public class ReflectionRegistration
{
	private String type;
	@JsonIgnore private boolean classRegistered;
	private boolean allDeclaredConstructors;
	private boolean allDeclaredFields;
	private boolean allDeclaredMethods;

	public ReflectionRegistration() {}
	public ReflectionRegistration(String type) {this.type = type;}

	public void apply(AnnotationRegistration annotation)
	{
		if(annotation.isRegisterClass()) {classRegistered = true;}
		if(annotation.isRegisterConstructors()) {allDeclaredConstructors = true;}
		if(annotation.isRegisterFields()) {allDeclaredFields = true;}
		if(annotation.isRegisterMethods()) {allDeclaredMethods = true;}
	}

	@JsonIgnore
	public boolean isRegistered()
	{
		return classRegistered || allDeclaredConstructors || allDeclaredFields || allDeclaredMethods;
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
