package net.sf.exlp.interfaces.util;

public interface ConfigKey
{
	String dirTmp = "dir.tmp";
	
	String netRestUrl  = "net.rest.url";
	String netRestUrlProduction = "net.rest.url.production";
	String netRestUrlImport  = "net.rest.url.import";
	String netRestUrlExport  = "net.rest.url.export";
	
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