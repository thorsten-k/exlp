package org.exlp.util.jk;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="dir", namespace="http://exlp.sf.net/io")
public class JkNsFixture
{
	private String value = "test";

	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
}
