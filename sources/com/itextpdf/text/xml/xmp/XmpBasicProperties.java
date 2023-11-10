package com.itextpdf.text.xml.xmp;

import com.itextpdf.xmp.XMPException;
import com.itextpdf.xmp.XMPMeta;
import com.itextpdf.xmp.XMPUtils;
import com.itextpdf.xmp.options.PropertyOptions;

public class XmpBasicProperties {
    public static final String ADVISORY = "Advisory";
    public static final String BASEURL = "BaseURL";
    public static final String CREATEDATE = "CreateDate";
    public static final String CREATORTOOL = "CreatorTool";
    public static final String IDENTIFIER = "Identifier";
    public static final String METADATADATE = "MetadataDate";
    public static final String MODIFYDATE = "ModifyDate";
    public static final String NICKNAME = "Nickname";
    public static final String THUMBNAILS = "Thumbnails";

    public static void setCreatorTool(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.setProperty("http://ns.adobe.com/xap/1.0/", CREATORTOOL, str);
    }

    public static void setCreateDate(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.setProperty("http://ns.adobe.com/xap/1.0/", CREATEDATE, str);
    }

    public static void setModDate(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.setProperty("http://ns.adobe.com/xap/1.0/", MODIFYDATE, str);
    }

    public static void setMetaDataDate(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.setProperty("http://ns.adobe.com/xap/1.0/", METADATADATE, str);
    }

    public static void setIdentifiers(XMPMeta xMPMeta, String[] strArr) throws XMPException {
        XMPUtils.removeProperties(xMPMeta, "http://purl.org/dc/elements/1.1/", IDENTIFIER, true, true);
        for (String appendArrayItem : strArr) {
            xMPMeta.appendArrayItem("http://purl.org/dc/elements/1.1/", IDENTIFIER, new PropertyOptions(512), appendArrayItem, (PropertyOptions) null);
        }
    }

    public static void setNickname(XMPMeta xMPMeta, String str) throws XMPException {
        xMPMeta.setProperty("http://ns.adobe.com/xap/1.0/", NICKNAME, str);
    }
}
