package org.exlp.util.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.exlp.model.json.config.JsonConfigurationProperty;
import org.exlp.test.AbstractExlpTest;
import org.exlp.test.ExlpBootstrap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.exlp.util.xml.JDomUtil;

public class TestJdomUtil extends AbstractExlpTest
{
	final static Logger logger = LoggerFactory.getLogger(TestJdomUtil.class);
    
    public void withJson() throws IOException
    {
    	JsonConfigurationProperty json = new JsonConfigurationProperty();
    	json.setValue("value");
    	
	    Element html = new Element("html");
		html.setAttribute("lang","en");
		
		ObjectMapper jom = new ObjectMapper();
    	jom.setSerializationInclusion(Include.NON_NULL);
    	jom.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		
		StringWriter w = new StringWriter();
		w.write(jom.writerWithDefaultPrettyPrinter().writeValueAsString(json));
		html.setText(w.toString().replace("\r\n","\n"));
		
		logger.info(w.toString());
	
	    Document doc = new Document(html);
	    doc.setDocType(new org.jdom2.DocType("html"));
		
	    Path path = Paths.get("./target");
		JDomUtil.instance().info(doc);
		
		FileWriter fw = new FileWriter(path.resolve("test.txt").toFile());
		fw.write(w.toString());
		fw.close();
//		JDomUtil.instance().omitDeclaration(true).write(doc,path.resolve("test.html"));
    }
	
    public static void main(String[] args) throws Exception
	{
		ExlpBootstrap.init();
		
		TestJdomUtil test = new TestJdomUtil();
		test.withJson();
	}
}