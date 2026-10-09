package org.exlp.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;

public class CapturingAppender extends AbstractAppender
{
	private final List<String> messages;

	private CapturingAppender()
	{
		super("ExlpTestCapture", null, null, true, new Property[0]);
		this.messages = new ArrayList<>();
	}

	public static CapturingAppender attach(Class<?> c)
	{
		LoggerContext context = (LoggerContext)LogManager.getContext(false);
		CapturingAppender appender = new CapturingAppender();
		appender.start();
		Logger logger = context.getLogger(c.getName());
		logger.addAppender(appender);
		logger.setLevel(Level.WARN);
		return appender;
	}

	public void detach(Class<?> c)
	{
		LoggerContext context = (LoggerContext)LogManager.getContext(false);
		context.getLogger(c.getName()).removeAppender(this);
		this.stop();
	}

	public List<String> messages() {return messages;}

	@Override public void append(LogEvent event) {messages.add(event.getMessage().getFormattedMessage());}
}
