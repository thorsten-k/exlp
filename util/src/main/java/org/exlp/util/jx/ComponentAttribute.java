package org.exlp.util.jx;

import java.util.Map;
import java.util.Objects;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentAttribute
{
	final static Logger logger = LoggerFactory.getLogger(ComponentAttribute.class);
	
	public static <E extends Enum<E>> String toString(FacesContext ctx, UIComponent component, E attribute)
	{
		Object o = ComponentAttribute.toObject(ctx, component, attribute);
		if(Objects.isNull(o)) {return null;}
		else {return o.toString();}
	}
	
	private static <E extends Enum<E>> Object toObject(FacesContext ctx, UIComponent component, E attribute) {return ComponentAttribute.toObject(ctx, component, attribute.toString(), null);}
	private static Object toObject(FacesContext ctx, UIComponent component, String attribute, Object defaultValue)
	{
		Object value = null;
		if(component.getAttributes().containsKey(attribute))
		{
			value = component.getAttributes().get(attribute);
			logger.trace("Value from "+component.getClass().getSimpleName()+"."+Map.class.getSimpleName()+": "+value);
		}
		else
		{
			ValueExpression ve = component.getValueExpression(attribute);
			if(Objects.nonNull(ve))
			{
				value=ve.getValue(ctx.getELContext());
			}
			logger.trace("Value from "+ValueExpression.class.getSimpleName()+": "+value);
		}
		if(Objects.isNull(value)) {value=defaultValue;}
		logger.trace("Value Final: "+value);
		return value;
	}
}