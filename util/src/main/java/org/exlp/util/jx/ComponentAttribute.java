package org.exlp.util.jx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentAttribute
{
	final static Logger logger = LoggerFactory.getLogger(ComponentAttribute.class);
	
	private static boolean debugOnInfo = false;
	public static void debugOnInfo(boolean value) {ComponentAttribute.debugOnInfo=value;}
	
	public static String toString(FacesContext ctx, UIComponent component, Serializable key) {return ComponentAttribute.toString(ctx,component,key,null);}
	public static String toString(FacesContext ctx, UIComponent component, Serializable key, String fallback) {return ComponentAttribute.toObject(ctx,component,key,fallback);}
	
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T toObject(FacesContext ctx, UIComponent component, Serializable key, T fallback)
	{
		Object o = ComponentAttribute.fromValueExpression(ctx, component, key);
		if(Objects.isNull(o)) {return fallback;}
		else
		{
//			if(!o.getClass().equals(c)) {throw new RuntimeException("The Object "+o.getClass().getSimpleName()+" is not a class of" +c.getClass().getSimpleName());}
			return (T)o;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> List<T> toObjects(FacesContext ctx, UIComponent component, Serializable key)
	{
		List<T> values = new ArrayList<>();
		
		Object o = ComponentAttribute.fromValueExpression(ctx, component, key);
		if(Objects.nonNull(o))
		{
			logger.trace("Object "+o.getClass().getName());
			List<Object> objects = (List<Object>)o;
			logger.trace("Objects "+o.getClass().getName());
			for(Object i : objects)
			{
				values.add((T)i);
			}
			
//			if(!o.getClass().equals(c)) {throw new RuntimeException("The Object "+o.getClass().getSimpleName()+" is not a class of" +c.getClass().getSimpleName());}
		}
		logger.trace("Values "+values.getClass().getName()+" "+values.size());
		return values;
	}
	
	private static Object fromValueExpression(FacesContext ctx, UIComponent component, Serializable key)
	{
		ValueExpression ve = component.getValueExpression(key.toString());
		if(Objects.nonNull(ve))
		{
			Object value=ve.getValue(ctx.getELContext());
			if(debugOnInfo) {logger.info("Value from "+ValueExpression.class.getSimpleName()+": "+value);}
			return value;
		}
		else
		{
			if(debugOnInfo) {logger.info("No VE for key, you should use getter/setter");}
			return null;
		}
	}
}