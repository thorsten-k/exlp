package net.sf.exlp.interfaces.util;

public interface ConfigKey
{
	String dirTmp = "dir.tmp";
	
	String netRestUrl  = "net.rest.url";
	String netRestUrlLocal = "net.rest.url.local";
	String netRestUrlProduction = "net.rest.url.production";
	String netRestUrlImport  = "net.rest.url.import";
	String netRestUrlExport  = "net.rest.url.export";
	
	String netRestImportUrl = "net.rest.import.url";
	String netRestImportUser = "net.rest.import.user";
	String netRestImportPassword = "net.rest.import.user";
	
	String netRestUser = "net.rest.user";
	String netRestProductionUrl = "net.rest.production.url";
	String netRestLocalUrl = "net.rest.local.url";

	String netRestLocalPassword = "net.rest.local.password";
	String netRestPasswordLocal = "net.rest.local.password";
	
	String netRestProductionPassword = "net.rest.production.password";
	String netRestPasswordProduction = "net.rest.production.password";
	
	String netRestTestingUrl = "net.rest.testing.url";
	
	String netMqttUrl	= "net.mqtt.url";
	String netMqttTopic	= "net.mqtt.topic";
	
	String netRestPort = "net.rest.port";
	
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