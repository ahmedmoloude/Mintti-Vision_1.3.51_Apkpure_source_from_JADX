package com.itextpdf.text.xml.xmp;

import com.itextpdf.xmp.XMPException;
import com.itextpdf.xmp.XMPMeta;

public class PdfProperties {
    public static final String KEYWORDS = "Keywords";
    public static final String PART = "part";
    public static final String PRODUCER = "Producer";
    public static final String VERSION = "PDFVersion";

    public static void setKeywords(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.setProperty("http://ns.adobe.com/pdf/1.3/", KEYWORDS, str);
    }

    public static void setProducer(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.setProperty("http://ns.adobe.com/pdf/1.3/", PRODUCER, str);
    }

    public static void setVersion(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.setProperty("http://ns.adobe.com/pdf/1.3/", VERSION, str);
    }
}
