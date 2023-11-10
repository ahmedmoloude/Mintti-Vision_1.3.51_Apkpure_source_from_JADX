package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.security.XmlLocator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XfaXmlLocator implements XmlLocator {
    private String encoding;
    private PdfStamper stamper;
    private XfaForm xfaForm;

    public XfaXmlLocator(PdfStamper pdfStamper) throws DocumentException, IOException {
        this.stamper = pdfStamper;
        try {
            createXfaForm();
        } catch (ParserConfigurationException e) {
            throw new DocumentException((Exception) e);
        } catch (SAXException e2) {
            throw new DocumentException((Exception) e2);
        }
    }

    /* access modifiers changed from: protected */
    public void createXfaForm() throws ParserConfigurationException, SAXException, IOException {
        this.xfaForm = new XfaForm(this.stamper.getReader());
    }

    public Document getDocument() {
        return this.xfaForm.getDomDocument();
    }

    public void setDocument(Document document) throws IOException, DocumentException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(byteArrayOutputStream));
            this.stamper.getReader().getAcroForm().put(PdfName.XFA, this.stamper.getWriter().addToBody(new PdfStream(byteArrayOutputStream.toByteArray())).getIndirectReference());
        } catch (TransformerConfigurationException e) {
            throw new DocumentException((Exception) e);
        } catch (TransformerException e2) {
            throw new DocumentException((Exception) e2);
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String str) {
        this.encoding = str;
    }
}
