package net.sf.exlp.factory.txt;

import java.util.ArrayList;
import java.util.List;

import org.exlp.model.xml.config.Parameter;
import org.exlp.model.xml.config.Parameters;

public class TxtConfigurationParamterFactory
{
	public static List<String> build(Parameters paramters)
	{
		List<String> required = new ArrayList<String>();
		List<String> optional = new ArrayList<String>();
		for(Parameter p : paramters.getParameter())
		{
			StringBuffer sb = new StringBuffer();
			sb.append("\t");
			sb.append(p.getKey());
			sb.append("=").append(p.getValue());
			sb.append(" (").append(p.getDescription()).append(")");
			if(p.isRequired()){required.add(sb.toString());}
			else{optional.add(sb.toString());}
		}
		
		List<String> result = new ArrayList<String>();
		result.add("Required Paramter");
		result.addAll(required);
		
		if(optional.size()>0)
		{
			result.add("Optional Paramter");
			result.addAll(optional);
		}
		
		return result;
	}
}
