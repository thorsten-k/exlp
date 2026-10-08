package org.exlp.maven.reachability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Root of the generated reachability metadata; contains Reflection registrations only.
 */
@JsonPropertyOrder({"reflection"})
public class ReachabilityMetadata
{
	private List<ReflectionRegistration> reflection;

	public ReachabilityMetadata() {reflection = new ArrayList<ReflectionRegistration>();}
	public ReachabilityMetadata(Collection<ReflectionRegistration> reflection) {this.reflection = new ArrayList<ReflectionRegistration>(reflection);}

	public List<ReflectionRegistration> getReflection() {return reflection;}
	public void setReflection(List<ReflectionRegistration> reflection) {this.reflection = reflection;}
}
