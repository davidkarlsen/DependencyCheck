package org.codesecure.dependencycheck.data.nvdcve.generated;
/*
 * This file is part of DependencyCheck.
 *
 * DependencyCheck is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * DependencyCheck is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * DependencyCheck. If not, see http://www.gnu.org/licenses/.
 *
 * Copyright (c) 2012 Jeremy Long. All Rights Reserved.
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2012.10.21 at 11:58:46 AM EDT
//
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The logical-test element appears as a child of a platform element, and may
 * also be nested to create more complex logical tests. The content consists of
 * one or more elements: fact-ref, and logical-test children are permitted. The
 * operator to be applied, and optional negation of the test, are given as
 * attributes.
 *
 * <p>Java class for LogicalTestType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained
 * within this class.
 *
 * <pre>
 * &lt;complexType name="LogicalTestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="logical-test" type="{http://cpe.mitre.org/language/2.0}LogicalTestType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fact-ref" type="{http://cpe.mitre.org/language/2.0}FactRefType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="operator" use="required" type="{http://cpe.mitre.org/language/2.0}operatorEnumeration" />
 *       &lt;attribute name="negate" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogicalTestType", namespace = "http://cpe.mitre.org/language/2.0", propOrder = {
    "logicalTests",
    "factReves"
})
@XmlRootElement(name = "logical-test", namespace = "http://cpe.mitre.org/language/2.0")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class LogicalTest {

    @XmlElement(name = "logical-test")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected List<LogicalTest> logicalTests;
    @XmlElement(name = "fact-ref")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected List<FactRefType> factReves;
    @XmlAttribute(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected OperatorEnumeration operator;
    @XmlAttribute(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected boolean negate;

    /**
     * Gets the value of the logicalTests property.
     *
     * <p> This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the logicalTests property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getLogicalTests().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link LogicalTest }
     *
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public List<LogicalTest> getLogicalTests() {
        if (logicalTests == null) {
            logicalTests = new ArrayList<LogicalTest>();
        }
        return this.logicalTests;
    }

    /**
     * Gets the value of the factReves property.
     *
     * <p> This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the factReves property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getFactReves().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link FactRefType }
     *
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public List<FactRefType> getFactReves() {
        if (factReves == null) {
            factReves = new ArrayList<FactRefType>();
        }
        return this.factReves;
    }

    /**
     * Gets the value of the operator property.
     *
     * @return possible object is
     *     {@link OperatorEnumeration }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public OperatorEnumeration getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     *
     * @param value allowed object is
     *     {@link OperatorEnumeration }
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setOperator(OperatorEnumeration value) {
        this.operator = value;
    }

    /**
     * Gets the value of the negate property.
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public boolean isNegate() {
        return negate;
    }

    /**
     * Sets the value of the negate property.
     *
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2012-10-21T11:58:46-04:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setNegate(boolean value) {
        this.negate = value;
    }
}