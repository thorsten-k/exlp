package net.sf.exlp.factory.xml.io;

import java.io.File;
import java.util.Date;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.xml.io.Dir;

public class XmlDirFactory
{
	private Dir q;
	
	public XmlDirFactory(Dir q)
	{
		this.q=q;
	}
	
	public Dir build(File f)
	{
		Dir xml = new Dir();
		if(q.isSetName()){xml.setName(f.getName());}
		if(q.isSetLastModifed()){xml.setLastModifed(DateUtil.getXmlGc4D(new Date(f.lastModified())));}
		return xml;
	}
}