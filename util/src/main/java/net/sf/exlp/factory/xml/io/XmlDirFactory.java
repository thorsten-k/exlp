package net.sf.exlp.factory.xml.io;

import java.io.File;
import java.util.Date;
import java.util.Objects;

import org.exlp.model.xml.io.Dir;

import net.sf.exlp.util.DateUtil;

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
		if(Objects.nonNull(q.getName())) {xml.setName(f.getName());}
		if(Objects.nonNull(q.getLastModifed())) {xml.setLastModifed(DateUtil.toXmlGc(new Date(f.lastModified())));}
		return xml;
	}
}