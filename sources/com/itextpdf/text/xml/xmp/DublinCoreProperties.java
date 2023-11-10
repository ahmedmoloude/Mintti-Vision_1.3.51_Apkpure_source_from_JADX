package com.itextpdf.text.xml.xmp;

import com.itextpdf.xmp.XMPException;
import com.itextpdf.xmp.XMPMeta;
import com.itextpdf.xmp.XMPUtils;
import com.itextpdf.xmp.options.PropertyOptions;

public class DublinCoreProperties {
    public static final String CONTRIBUTOR = "contributor";
    public static final String COVERAGE = "coverage";
    public static final String CREATOR = "creator";
    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";
    public static final String FORMAT = "format";
    public static final String IDENTIFIER = "identifier";
    public static final String LANGUAGE = "language";
    public static final String PUBLISHER = "publisher";
    public static final String RELATION = "relation";
    public static final String RIGHTS = "rights";
    public static final String SOURCE = "source";
    public static final String SUBJECT = "subject";
    public static final String TITLE = "title";
    public static final String TYPE = "type";

    public static void addTitle(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", "title", new PropertyOptions(2048), str, (PropertyOptions) null);
    }

    public static void setTitle(XMPMeta xMPMeta, String str, String str2, String str3) throws XMPException {
        xMPMeta.setLocalizedText("http://purl.org/dc/elements/1.1/", "title", str2, str3, str);
    }

    public static void addDescription(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", DESCRIPTION, new PropertyOptions(2048), str, (PropertyOptions) null);
    }

    public static void setDescription(XMPMeta xMPMeta, String str, String str2, String str3) throws XMPException {
        xMPMeta.setLocalizedText("http://purl.org/dc/elements/1.1/", DESCRIPTION, str2, str3, str);
    }

    public static void addSubject(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", "subject", new PropertyOptions(512), str, (PropertyOptions) null);
    }

    public static void setSubject(XMPMeta xMPMeta, String[] strArr) throws XMPException {
        XMPUtils.removeProperties(xMPMeta, "http://purl.org/dc/elements/1.1/", "subject", true, true);
        for (String appendArrayItem : strArr) {
            xMPMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", "subject", new PropertyOptions(512), appendArrayItem, (PropertyOptions) null);
        }
    }

    public static void addAuthor(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", CREATOR, new PropertyOptions(1024), str, (PropertyOptions) null);
    }

    public static void setAuthor(XMPMeta xMPMeta, String[] strArr) throws XMPException {
        XMPUtils.removeProperties(xMPMeta, "http://purl.org/dc/elements/1.1/", CREATOR, true, true);
        for (String appendArrayItem : strArr) {
            xMPMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", CREATOR, new PropertyOptions(1024), appendArrayItem, (PropertyOptions) null);
        }
    }

    public static void addPublisher(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", PUBLISHER, new PropertyOptions(1024), str, (PropertyOptions) null);
    }

    public static void setPublisher(XMPMeta xMPMeta, String[] strArr) throws XMPException {
        XMPUtils.removeProperties(xMPMeta, "http://purl.org/dc/elements/1.1/", PUBLISHER, true, true);
        for (String appendArrayItem : strArr) {
            xMPMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", PUBLISHER, new PropertyOptions(1024), appendArrayItem, (PropertyOptions) null);
        }
    }
}
