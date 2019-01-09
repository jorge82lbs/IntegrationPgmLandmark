
package mx.com.televisa.landamark.client.auth.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for autenticarUsuarioResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="autenticarUsuarioResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanDasComerAutenticar}ProcessResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autenticarUsuarioResponse", propOrder = { "_return" })
public class AutenticarUsuarioResponse {

    @XmlElement(name = "return", namespace = "")
    protected ProcessResponse _return;

    /**
     * Gets the value of the return property.
     *
     * @return
     *     possible object is
     *     {@link ProcessResponse }
     *
     */
    public ProcessResponse getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value
     *     allowed object is
     *     {@link ProcessResponse }
     *
     */
    public void setReturn(ProcessResponse value) {
        this._return = value;
    }

}
