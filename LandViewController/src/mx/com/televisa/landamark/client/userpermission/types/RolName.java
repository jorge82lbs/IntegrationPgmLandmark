
package mx.com.televisa.landamark.client.userpermission.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RolName complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RolName">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RolName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}DescriptionName"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RolName", propOrder = { "rolName" })
public class RolName {

    @XmlElement(name = "RolName", required = true)
    protected String rolName;
    @XmlAttribute(name = "DescriptionName", namespace = "http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos")
    protected String descriptionName;

    /**
     * Gets the value of the rolName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRolName() {
        return rolName;
    }

    /**
     * Sets the value of the rolName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRolName(String value) {
        this.rolName = value;
    }

    /**
     * Gets the value of the descriptionName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescriptionName() {
        return descriptionName;
    }

    /**
     * Sets the value of the descriptionName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescriptionName(String value) {
        this.descriptionName = value;
    }

}
