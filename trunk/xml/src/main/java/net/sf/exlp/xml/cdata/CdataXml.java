package net.sf.exlp.xml.cdata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.resteasy.annotations.Decorator;

import com.sun.xml.bind.v2.runtime.MarshallerImpl;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Decorator(processor = CdataProcessor.class, target = MarshallerImpl.class)
public @interface CdataXml {}