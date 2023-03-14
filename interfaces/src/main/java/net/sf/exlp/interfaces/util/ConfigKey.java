package net.sf.exlp.interfaces.util;

public interface ConfigKey
{
	String dirTmp = "dir.tmp";
		
	String netRestUser = "net.rest.user";
	String netRestUserImport = "net.rest.import.user";
	
	String netRestDelay = "net.rest.delay";
	String netRestUrlLocal = "net.rest.local.url";
	String netRestUrlTesting = "net.rest.testing.url";
	String netRestUrlImport = "net.rest.import.url";
	String netRestUrlExport  = "net.rest.export.url";
	String netRestUrlProduction = "net.rest.production.url";
	
	String netRestPasswordLocal = "net.rest.local.password";
	String netRestPasswordTesting = "net.rest.testing.password";
	String netRestPasswordImport = "net.rest.import.password";
	String netRestPasswordProduction = "net.rest.production.password";
	
	String netMqttUrl	= "net.mqtt.url";
	String netMqttTopic	= "net.mqtt.topic";
	
	String netEjbHost = "net.ejb.host";
	String netEjbPort = "net.ejb.port";
	String netEjbUser = "net.ejb.user";
	String netEjbPwd  = "net.ejb.pwd";
	
	String netDbHost   = "net.db.host";
	String netDbPort   = "net.db.port";
	String netDbName   = "net.db.name";
	String netDbScheme = "net.db.scheme";
	String netDbUser   = "net.db.user";
	String netDbPwd    = "net.db.pwd";
	
	String netImapHost = "net.imap.host";
	String netImapPort = "net.imap.port";
	String netImapUser = "net.imap.user";
	String netImapPwd  = "net.imap.pwd";
	
	String netSmtpHost = "net.smtp.host";
	String netSmtpPort = "net.smtp.port";
	String netSmtpUser = "net.smtp.auth.user";
	String netSmtpPwd  = "net.smtp.auth.pwd";
	String netSmtpHelo = "net.smtp.helo";
}