package net.sf.exlp.xml.cdata;

import com.sun.xml.bind.v2.runtime.MarshallerImpl;

import org.jboss.resteasy.annotations.DecorateTypes;
import org.jboss.resteasy.spi.interception.DecoratorProcessor;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.PropertyException;

import java.lang.annotation.Annotation;

@DecorateTypes({"text/*+xml", "application/*+xml"})
public class CdataProcessor implements DecoratorProcessor<MarshallerImpl, CdataXml>
{
 
	@SuppressWarnings("rawtypes")
    @Override
    public MarshallerImpl decorate(MarshallerImpl target, CdataXml annotation, Class type, Annotation[] annotations, MediaType mediaType)
    {
        try
        {
            target.setProperty("com.sun.xml.bind.marshaller.CharacterEscapeHandler", new CdataXmlEscapeHandler("UTF-8"));
        }
        catch (PropertyException e)
        {
           throw new RuntimeException("Could not decorate marshaller!", e);
        }
        return target;
    }
}