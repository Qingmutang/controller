
package com.bjpowernode.p2p.admin.seal.stub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>creditorVO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="creditorVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applyNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="applyPurpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auditLoanMoney" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="auditLoanTerm" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="bidInfoVOList" type="{http://impl.service.seal.bjpowernode.com/}bidInfoVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="borrowerIdcard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="borrowerPresentAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="borrowerRealname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="borrowerSex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="collectFinishTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "creditorVO", propOrder = {
    "applyNo",
    "applyPurpose",
    "auditLoanMoney",
    "auditLoanTerm",
    "bidInfoVOList",
    "borrowerIdcard",
    "borrowerPresentAddress",
    "borrowerRealname",
    "borrowerSex",
    "collectFinishTime"
})
public class CreditorVO {

    protected String applyNo;
    protected String applyPurpose;
    protected BigDecimal auditLoanMoney;
    protected Integer auditLoanTerm;
    @XmlElement(nillable = true)
    protected List<BidInfoVO> bidInfoVOList;
    protected String borrowerIdcard;
    protected String borrowerPresentAddress;
    protected String borrowerRealname;
    protected String borrowerSex;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar collectFinishTime;

    /**
     * 获取applyNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplyNo() {
        return applyNo;
    }

    /**
     * 设置applyNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplyNo(String value) {
        this.applyNo = value;
    }

    /**
     * 获取applyPurpose属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplyPurpose() {
        return applyPurpose;
    }

    /**
     * 设置applyPurpose属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplyPurpose(String value) {
        this.applyPurpose = value;
    }

    /**
     * 获取auditLoanMoney属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAuditLoanMoney() {
        return auditLoanMoney;
    }

    /**
     * 设置auditLoanMoney属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAuditLoanMoney(BigDecimal value) {
        this.auditLoanMoney = value;
    }

    /**
     * 获取auditLoanTerm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAuditLoanTerm() {
        return auditLoanTerm;
    }

    /**
     * 设置auditLoanTerm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAuditLoanTerm(Integer value) {
        this.auditLoanTerm = value;
    }

    /**
     * Gets the value of the bidInfoVOList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bidInfoVOList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBidInfoVOList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BidInfoVO }
     * 
     * 
     */
    public List<BidInfoVO> getBidInfoVOList() {
        if (bidInfoVOList == null) {
            bidInfoVOList = new ArrayList<BidInfoVO>();
        }
        return this.bidInfoVOList;
    }

    /**
     * 获取borrowerIdcard属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorrowerIdcard() {
        return borrowerIdcard;
    }

    /**
     * 设置borrowerIdcard属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorrowerIdcard(String value) {
        this.borrowerIdcard = value;
    }

    /**
     * 获取borrowerPresentAddress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorrowerPresentAddress() {
        return borrowerPresentAddress;
    }

    /**
     * 设置borrowerPresentAddress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorrowerPresentAddress(String value) {
        this.borrowerPresentAddress = value;
    }

    /**
     * 获取borrowerRealname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorrowerRealname() {
        return borrowerRealname;
    }

    /**
     * 设置borrowerRealname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorrowerRealname(String value) {
        this.borrowerRealname = value;
    }

    /**
     * 获取borrowerSex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorrowerSex() {
        return borrowerSex;
    }

    /**
     * 设置borrowerSex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorrowerSex(String value) {
        this.borrowerSex = value;
    }

    /**
     * 获取collectFinishTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCollectFinishTime() {
        return collectFinishTime;
    }

    /**
     * 设置collectFinishTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCollectFinishTime(XMLGregorianCalendar value) {
        this.collectFinishTime = value;
    }

}
