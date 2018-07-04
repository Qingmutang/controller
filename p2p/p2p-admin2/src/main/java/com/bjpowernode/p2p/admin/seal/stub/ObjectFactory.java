
package com.bjpowernode.p2p.admin.seal.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bjpowernode.p2p.admin.seal.stub package. 
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

    private final static QName _PdfSeal_QNAME = new QName("http://impl.service.seal.bjpowernode.com/", "pdfSeal");
    private final static QName _PdfSealResponse_QNAME = new QName("http://impl.service.seal.bjpowernode.com/", "pdfSealResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bjpowernode.p2p.admin.seal.stub
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PdfSeal }
     * 
     */
    public PdfSeal createPdfSeal() {
        return new PdfSeal();
    }

    /**
     * Create an instance of {@link PdfSealResponse }
     * 
     */
    public PdfSealResponse createPdfSealResponse() {
        return new PdfSealResponse();
    }

    /**
     * Create an instance of {@link BidInfoVO }
     * 
     */
    public BidInfoVO createBidInfoVO() {
        return new BidInfoVO();
    }

    /**
     * Create an instance of {@link CreditorVO }
     * 
     */
    public CreditorVO createCreditorVO() {
        return new CreditorVO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PdfSeal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.seal.bjpowernode.com/", name = "pdfSeal")
    public JAXBElement<PdfSeal> createPdfSeal(PdfSeal value) {
        return new JAXBElement<PdfSeal>(_PdfSeal_QNAME, PdfSeal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PdfSealResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.seal.bjpowernode.com/", name = "pdfSealResponse")
    public JAXBElement<PdfSealResponse> createPdfSealResponse(PdfSealResponse value) {
        return new JAXBElement<PdfSealResponse>(_PdfSealResponse_QNAME, PdfSealResponse.class, null, value);
    }

}
