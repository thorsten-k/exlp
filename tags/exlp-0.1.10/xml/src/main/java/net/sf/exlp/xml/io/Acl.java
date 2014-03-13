
package net.sf.exlp.xml.io;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="permission" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="oi" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="ci" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="io" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "acl")
public class Acl
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "permission")
    protected String permission;
    @XmlAttribute(name = "oi")
    protected Boolean oi;
    @XmlAttribute(name = "ci")
    protected Boolean ci;
    @XmlAttribute(name = "io")
    protected Boolean io;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(long value) {
        this.id = value;
    }

    public boolean isSetId() {
        return (this.id!= null);
    }

    public void unsetId() {
        this.id = null;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    public boolean isSetDescription() {
        return (this.description!= null);
    }

    /**
     * Gets the value of the permission property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Sets the value of the permission property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermission(String value) {
        this.permission = value;
    }

    public boolean isSetPermission() {
        return (this.permission!= null);
    }

    /**
     * Gets the value of the oi property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isOi() {
        return oi;
    }

    /**
     * Sets the value of the oi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOi(boolean value) {
        this.oi = value;
    }

    public boolean isSetOi() {
        return (this.oi!= null);
    }

    public void unsetOi() {
        this.oi = null;
    }

    /**
     * Gets the value of the ci property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isCi() {
        return ci;
    }

    /**
     * Sets the value of the ci property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCi(boolean value) {
        this.ci = value;
    }

    public boolean isSetCi() {
        return (this.ci!= null);
    }

    public void unsetCi() {
        this.ci = null;
    }

    /**
     * Gets the value of the io property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIo() {
        return io;
    }

    /**
     * Sets the value of the io property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIo(boolean value) {
        this.io = value;
    }

    public boolean isSetIo() {
        return (this.io!= null);
    }

    public void unsetIo() {
        this.io = null;
    }

}
