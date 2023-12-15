
package org.exlp.model.xml.identity;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="serial" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="notAfter" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *       &lt;attribute name="notBefore" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *       &lt;attribute name="cn" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="email" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "certificate")
public class Certificate
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "serial")
    protected Integer serial;
    @XmlAttribute(name = "notAfter")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar notAfter;
    @XmlAttribute(name = "notBefore")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar notBefore;
    @XmlAttribute(name = "cn")
    protected String cn;
    @XmlAttribute(name = "email")
    protected String email;

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der serial-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSerial() {
        return serial;
    }

    /**
     * Legt den Wert der serial-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSerial(Integer value) {
        this.serial = value;
    }

    /**
     * Ruft den Wert der notAfter-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNotAfter() {
        return notAfter;
    }

    /**
     * Legt den Wert der notAfter-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNotAfter(XMLGregorianCalendar value) {
        this.notAfter = value;
    }

    /**
     * Ruft den Wert der notBefore-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNotBefore() {
        return notBefore;
    }

    /**
     * Legt den Wert der notBefore-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNotBefore(XMLGregorianCalendar value) {
        this.notBefore = value;
    }

    /**
     * Ruft den Wert der cn-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCn() {
        return cn;
    }

    /**
     * Legt den Wert der cn-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCn(String value) {
        this.cn = value;
    }

    /**
     * Ruft den Wert der email-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Legt den Wert der email-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

}
