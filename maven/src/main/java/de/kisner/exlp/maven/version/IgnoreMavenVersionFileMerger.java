package de.kisner.exlp.maven.version;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.logging.Log;
import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;

public class IgnoreMavenVersionFileMerger
{    
	final static Logger logger = LoggerFactory.getLogger(IgnoreMavenVersionFileMerger.class);
	private Log log; public Log getLog() {return log;} public void setLog(Log log) {this.log = log;}

	private MultiResourceLoader mrl;
	private Namespace nsXsi;
	private Namespace ns;
	private Element rules;
	
	public IgnoreMavenVersionFileMerger()
	{
		ns = Namespace.getNamespace("http://mojo.codehaus.org/versions-maven-plugin/rule/2.0.0");
		nsXsi = Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
		
		mrl = MultiResourceLoader.instance();

		
		rules = new Element("rules");
		rules.setNamespace(ns);
	}
	
	public void add(String resourceName) throws FileNotFoundException
	{
		if(!mrl.isAvailable(resourceName))
		{
			resourceName = "/src/main/resources/"+resourceName;
		}
		
		Document d = JDomUtil.load(mrl.searchIs(resourceName));
		
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
		JDomUtil.outputStream(doc, os, Format.getPrettyFormat());
	}
}