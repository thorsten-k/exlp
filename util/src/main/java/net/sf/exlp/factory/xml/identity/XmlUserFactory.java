package net.sf.exlp.factory.xml.identity;

import org.exlp.model.xml.identity.User;

public class XmlUserFactory
{
	public static User build(String account, String password)
	{
		User xml = new User();
		xml.setAccount(account);
		xml.setPassword(password);
		return xml;
	}
}
