package org.exlp.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Merges versions-maven-plugin ignore rule files into one rule set.
 * The resources are read through the class loader and processed with JDOM.
 */
public class IgnoreMavenVersionFileMerger
{    
	final static Logger logger = LoggerFactory.getLogger(IgnoreMavenVersionFileMerger.class);
	private Log log; public Log getLog() {return log;} public void setLog(Log log) {this.log = log;}

	private ClassLoader classLoader;
	private Namespace nsXsi;
	private Namespace ns;
	private Element rules;
	
	public IgnoreMavenVersionFileMerger()
	{
		ns = Namespace.getNamespace("http://mojo.codehaus.org/versions-maven-plugin/rule/2.0.0");
		nsXsi = Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
		
		classLoader = this.getClass().getClassLoader();

		
		rules = new Element("rules");
		rules.setNamespace(ns);
	}
	
	public void add(String resourceName) throws FileNotFoundException
	{
		InputStream is = search(resourceName);
		if(is==null)
		{
			resourceName = "/src/main/resources/"+resourceName;
			is = search(resourceName);
		}
		if(is==null)
		{
			throw new FileNotFoundException("Missing File: "+resourceName);
		}
		
		Document d = load(is);
		
		Element r = d.getRootElement().getChild("rules",ns);

		List<Element> list = new ArrayList<Element>();
		list.addAll(r.getChildren());
		for(Element e : list)
		{
			e.detach();
			rules.addContent(e);
		}
	}
	
	public void output(OutputStream os)
	{
		Element root = new Element("ruleset");
		root.setAttribute("comparisonMethod", "maven");
	
		root.setNamespace(ns);
		root.addNamespaceDeclaration(nsXsi);
		root.addContent(new Comment("Do not modify this file, it is auto generated!"));
		
		root.addContent(rules);
		
		Attribute sl = new Attribute("schemaLocation", "http://mojo.codehaus.org/versions-maven-plugin/rule/2.0.0 http://mojo.codehaus.org/versions-maven-plugin/xsd/rule-2.0.0.xsd");
		sl.setNamespace(nsXsi);
		root.getAttributes().add(sl);
		
		Document doc = new Document();
		doc.setRootElement(root);
		
		try
		{
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
			outputter.output(doc, osw);
			osw.close();
		}
		catch (IOException e) {logger.error("",e);}
	}
	
	private InputStream search(String resourceName)
	{
		File f = new File(resourceName);
		if(f.exists())
		{
			try {return new FileInputStream(f);}
			catch (FileNotFoundException e) {return null;}
		}
		return classLoader.getResourceAsStream(resourceName.replace(File.separator, "/"));
	}
	
	private Document load(InputStream is)
	{
		try
		{
			SAXBuilder sax = new SAXBuilder();
			sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			return sax.build(new InputStreamReader(is,"UTF-8"));
		}
		catch (JDOMException e) {throw new IllegalStateException(e.getMessage(),e);}
		catch (IOException e) {throw new IllegalStateException(e.getMessage(),e);}
	}
}