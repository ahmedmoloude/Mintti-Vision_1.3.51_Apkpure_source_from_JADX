package com.itextpdf.text.xml.xmp;

import com.itextpdf.text.Version;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.xmp.XMPConst;
import com.itextpdf.xmp.XMPException;
import com.itextpdf.xmp.XMPMeta;
import com.itextpdf.xmp.XMPMetaFactory;
import com.itextpdf.xmp.XMPUtils;
import com.itextpdf.xmp.options.PropertyOptions;
import com.itextpdf.xmp.options.SerializeOptions;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class XmpWriter {
    public static final String UTF16 = "UTF-16";
    public static final String UTF16BE = "UTF-16BE";
    public static final String UTF16LE = "UTF-16LE";
    public static final String UTF8 = "UTF-8";
    protected OutputStream outputStream;
    protected SerializeOptions serializeOptions;
    protected XMPMeta xmpMeta;

    public XmpWriter(OutputStream outputStream2, String str, int i) throws IOException {
        this.outputStream = outputStream2;
        this.serializeOptions = new SerializeOptions();
        if (UTF16BE.equals(str) || UTF16.equals(str)) {
            this.serializeOptions.setEncodeUTF16BE(true);
        } else if (UTF16LE.equals(str)) {
            this.serializeOptions.setEncodeUTF16LE(true);
        }
        this.serializeOptions.setPadding(i);
        XMPMeta create = XMPMetaFactory.create();
        this.xmpMeta = create;
        create.setObjectName(XMPConst.TAG_XMPMETA);
        this.xmpMeta.setObjectName("");
        try {
            this.xmpMeta.setProperty("http://purl.org/dc/elements/1.1/", DublinCoreProperties.FORMAT, "application/pdf");
            this.xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", PdfProperties.PRODUCER, Version.getInstance().getVersion());
        } catch (XMPException unused) {
        }
    }

    public XmpWriter(OutputStream outputStream2) throws IOException {
        this(outputStream2, "UTF-8", 2000);
    }

    public XmpWriter(OutputStream outputStream2, PdfDictionary pdfDictionary) throws IOException {
        this(outputStream2);
        if (pdfDictionary != null) {
            for (PdfName next : pdfDictionary.getKeys()) {
                PdfObject pdfObject = pdfDictionary.get(next);
                if (pdfObject != null && pdfObject.isString()) {
                    try {
                        addDocInfoProperty(next, ((PdfString) pdfObject).toUnicodeString());
                    } catch (XMPException e) {
                        throw new IOException(e.getMessage());
                    }
                }
            }
        }
    }

    public XmpWriter(OutputStream outputStream2, Map<String, String> map) throws IOException {
        this(outputStream2);
        if (map != null) {
            for (Map.Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                String str2 = (String) next.getValue();
                if (str2 != null) {
                    try {
                        addDocInfoProperty(str, str2);
                    } catch (XMPException e) {
                        throw new IOException(e.getMessage());
                    }
                }
            }
        }
    }

    public XMPMeta getXmpMeta() {
        return this.xmpMeta;
    }

    public void setReadOnly() {
        this.serializeOptions.setReadOnlyPacket(true);
    }

    public void setAbout(String str) {
        this.xmpMeta.setObjectName(str);
    }

    @Deprecated
    public void addRdfDescription(String str, String str2) throws IOException {
        try {
            XMPUtils.appendProperties(XMPMetaFactory.parseFromString("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><rdf:Description rdf:about=\"" + this.xmpMeta.getObjectName() + "\" " + str + ">" + str2 + "</rdf:Description></rdf:RDF>\n"), this.xmpMeta, true, true);
        } catch (XMPException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Deprecated
    public void addRdfDescription(XmpSchema xmpSchema) throws IOException {
        try {
            XMPUtils.appendProperties(XMPMetaFactory.parseFromString("<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><rdf:Description rdf:about=\"" + this.xmpMeta.getObjectName() + "\" " + xmpSchema.getXmlns() + ">" + xmpSchema.toString() + "</rdf:Description></rdf:RDF>\n"), this.xmpMeta, true, true);
        } catch (XMPException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void setProperty(String str, String str2, Object obj) throws XMPException {
        this.xmpMeta.setProperty(str, str2, obj);
    }

    public void appendArrayItem(String str, String str2, String str3) throws XMPException {
        this.xmpMeta.appendArrayItem(str, str2, new PropertyOptions(512), str3, (PropertyOptions) null);
    }

    public void appendOrderedArrayItem(String str, String str2, String str3) throws XMPException {
        this.xmpMeta.appendArrayItem(str, str2, new PropertyOptions(1024), str3, (PropertyOptions) null);
    }

    public void appendAlternateArrayItem(String str, String str2, String str3) throws XMPException {
        this.xmpMeta.appendArrayItem(str, str2, new PropertyOptions(2048), str3, (PropertyOptions) null);
    }

    public void serialize(OutputStream outputStream2) throws XMPException {
        XMPMetaFactory.serialize(this.xmpMeta, outputStream2, this.serializeOptions);
    }

    public void close() throws IOException {
        OutputStream outputStream2 = this.outputStream;
        if (outputStream2 != null) {
            try {
                XMPMetaFactory.serialize(this.xmpMeta, outputStream2, this.serializeOptions);
                this.outputStream = null;
            } catch (XMPException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    public void addDocInfoProperty(Object obj, String str) throws XMPException {
        if (obj instanceof String) {
            obj = new PdfName((String) obj);
        }
        if (PdfName.TITLE.equals(obj)) {
            this.xmpMeta.setLocalizedText("http://purl.org/dc/elements/1.1/", "title", "x-default", "x-default", str);
        } else if (PdfName.AUTHOR.equals(obj)) {
            this.xmpMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", DublinCoreProperties.CREATOR, new PropertyOptions(1024), str, (PropertyOptions) null);
        } else if (PdfName.SUBJECT.equals(obj)) {
            this.xmpMeta.setLocalizedText("http://purl.org/dc/elements/1.1/", DublinCoreProperties.DESCRIPTION, "x-default", "x-default", str);
        } else if (PdfName.KEYWORDS.equals(obj)) {
            for (String str2 : str.split(",|;")) {
                if (str2.trim().length() > 0) {
                    this.xmpMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", "subject", new PropertyOptions(512), str2.trim(), (PropertyOptions) null);
                }
            }
            this.xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", PdfProperties.KEYWORDS, str);
        } else if (PdfName.PRODUCER.equals(obj)) {
            this.xmpMeta.setProperty("http://ns.adobe.com/pdf/1.3/", PdfProperties.PRODUCER, str);
        } else if (PdfName.CREATOR.equals(obj)) {
            this.xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", XmpBasicProperties.CREATORTOOL, str);
        } else if (PdfName.CREATIONDATE.equals(obj)) {
            this.xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", XmpBasicProperties.CREATEDATE, PdfDate.getW3CDate(str));
        } else if (PdfName.MODDATE.equals(obj)) {
            this.xmpMeta.setProperty("http://ns.adobe.com/xap/1.0/", XmpBasicProperties.MODIFYDATE, PdfDate.getW3CDate(str));
        }
    }
}
