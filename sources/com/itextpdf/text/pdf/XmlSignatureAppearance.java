package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.security.XmlLocator;
import com.itextpdf.text.pdf.security.XpathConstructor;
import java.io.IOException;
import java.security.cert.Certificate;
import java.util.Calendar;

public class XmlSignatureAppearance {
    private String description;
    private String mimeType = "text/xml";
    private Certificate signCertificate;
    private Calendar signDate;
    private PdfStamper stamper;
    private PdfStamperImp writer;
    private XmlLocator xmlLocator;
    private XpathConstructor xpathConstructor;

    XmlSignatureAppearance(PdfStamperImp pdfStamperImp) {
        this.writer = pdfStamperImp;
    }

    public PdfStamperImp getWriter() {
        return this.writer;
    }

    public PdfStamper getStamper() {
        return this.stamper;
    }

    public void setStamper(PdfStamper pdfStamper) {
        this.stamper = pdfStamper;
    }

    public void setCertificate(Certificate certificate) {
        this.signCertificate = certificate;
    }

    public Certificate getCertificate() {
        return this.signCertificate;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getDescription() {
        return this.description;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String str) {
        this.mimeType = str;
    }

    public Calendar getSignDate() {
        if (this.signDate == null) {
            this.signDate = Calendar.getInstance();
        }
        return this.signDate;
    }

    public void setSignDate(Calendar calendar) {
        this.signDate = calendar;
    }

    public XmlLocator getXmlLocator() {
        return this.xmlLocator;
    }

    public void setXmlLocator(XmlLocator xmlLocator2) {
        this.xmlLocator = xmlLocator2;
    }

    public XpathConstructor getXpathConstructor() {
        return this.xpathConstructor;
    }

    public void setXpathConstructor(XpathConstructor xpathConstructor2) {
        this.xpathConstructor = xpathConstructor2;
    }

    public void close() throws IOException, DocumentException {
        this.writer.close(this.stamper.getMoreInfo());
    }
}
