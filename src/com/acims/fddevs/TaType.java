//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2007.06.17 at 10:47:59 PM MST
//


package com.acims.fddevs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for taType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="taType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.saurabh-mittal.com/NewXMLSchema}state"/>
 *         &lt;element ref="{http://www.saurabh-mittal.com/NewXMLSchema}Timeout"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "taType", propOrder = {
    "state",
    "timeout"
})
public class TaType {
    
    @XmlElement(required = true)
    protected String state;
    @XmlElement(name = "Timeout")
    protected double timeout;
    
    
    /**
     * Gets the value of the state property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getState() {
        return state;
    }
    
    /**
     * Sets the value of the state property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setState(String value) {
        this.state = value;
    }
    
    /**
     * Gets the value of the timeout property.
     *
     */
    public double getTimeout() {
        return timeout;
    }
    
    /**
     * Sets the value of the timeout property.
     *
     */
    public void setTimeout(double value) {
        this.timeout = value;
    }
    
}
