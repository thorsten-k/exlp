
package net.sf.exlp.xml.net;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.sf.exlp.xml.net package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.sf.exlp.xml.net
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Urls }
     * 
     */
    public Urls createUrls() {
        return new Urls();
    }

    /**
     * Create an instance of {@link Url }
     * 
     */
    public Url createUrl() {
        return new Url();
    }

    /**
     * Create an instance of {@link Host }
     * 
     */
    public Host createHost() {
        return new Host();
    }

    /**
     * Create an instance of {@link Database }
     * 
     */
    public Database createDatabase() {
        return new Database();
    }

}
