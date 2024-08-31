
package org.exlp.model.xml.tool.inventory;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://schemas.microsoft.com/powershell/2004/04}MS"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="RefId" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ms"
})
@XmlRootElement(name = "Obj")
public class Obj
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "MS", required = true)
    protected MS ms;
    @XmlAttribute(name = "RefId")
    protected Integer refId;

    /**
     * Ruft den Wert der ms-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MS }
     *     
     */
    public MS getMS() {
        return ms;
    }

    /**
     * Legt den Wert der ms-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MS }
     *     
     */
    public void setMS(MS value) {
        this.ms = value;
    }

    /**
     * Ruft den Wert der refId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRefId() {
        return refId;
    }

    /**
     * Legt den Wert der refId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRefId(Integer value) {
        this.refId = value;
    }

}
