package org.exlp.util.jx;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="dir", namespace="http://exlp.sf.net/io")
public class JxNsFixture
{
	private String value = "test";

	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
}
