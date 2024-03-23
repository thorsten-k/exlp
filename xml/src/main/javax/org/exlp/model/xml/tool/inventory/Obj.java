
package org.exlp.model.xml.tool.inventory;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the ms property.
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
     * Sets the value of the ms property.
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
     * Gets the value of the refId property.
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
     * Sets the value of the refId property.
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
