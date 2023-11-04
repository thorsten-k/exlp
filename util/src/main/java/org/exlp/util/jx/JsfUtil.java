package org.exlp.util.jx;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsfUtil
{
	final static Logger logger = LoggerFactory.getLogger(JsfUtil.class);
	
	public static void pushJsToHead(FacesContext context, String library, String name) throws AbortProcessingException
	{		
		UIOutput js = new UIOutput();
		js.setRendererType("javax.faces.resource.Script");
		js.getAttributes().put("library", library);
		js.getAttributes().put("name", name);
		
		context.getViewRoot().addComponentResource(context, js, "head");
		logger.info("Added " +name +" to head.");
	}
}