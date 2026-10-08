package org.exlp.maven.reachability;

/**
 * Configuration of one annotation type and the registration elements it selects.
 */
public class AnnotationRegistration
{
	private String type;
	private boolean registerClass = true;
	private boolean registerConstructors = true;
	private boolean registerFields = true;
	private boolean registerMethods = true;

	public AnnotationRegistration() {}

	public String getType() {return type;}
	public void setType(String type) {this.type = type;}

	public boolean isRegisterClass() {return registerClass;}
	public void setRegisterClass(boolean registerClass) {this.registerClass = registerClass;}

	public boolean isRegisterConstructors() {return registerConstructors;}
	public void setRegisterConstructors(boolean registerConstructors) {this.registerConstructors = registerConstructors;}

	public boolean isRegisterFields() {return registerFields;}
	public void setRegisterFields(boolean registerFields) {this.registerFields = registerFields;}

	public boolean isRegisterMethods() {return registerMethods;}
	public void setRegisterMethods(boolean registerMethods) {this.registerMethods = registerMethods;}
}
