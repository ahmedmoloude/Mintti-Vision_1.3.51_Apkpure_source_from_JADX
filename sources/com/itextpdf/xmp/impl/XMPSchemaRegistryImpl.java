package com.itextpdf.xmp.impl;

import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.itextpdf.text.xml.xmp.DublinCoreSchema;
import com.itextpdf.text.xml.xmp.PdfProperties;
import com.itextpdf.text.xml.xmp.PdfSchema;
import com.itextpdf.text.xml.xmp.XmpBasicProperties;
import com.itextpdf.text.xml.xmp.XmpBasicSchema;
import com.itextpdf.text.xml.xmp.XmpMMSchema;
import com.itextpdf.xmp.XMPConst;
import com.itextpdf.xmp.XMPException;
import com.itextpdf.xmp.XMPSchemaRegistry;
import com.itextpdf.xmp.options.AliasOptions;
import com.itextpdf.xmp.properties.XMPAliasInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public final class XMPSchemaRegistryImpl implements XMPSchemaRegistry, XMPConst {
    private Map aliasMap = new HashMap();
    private Map namespaceToPrefixMap = new HashMap();

    /* renamed from: p */
    private Pattern f780p = Pattern.compile("[/*?\\[\\]]");
    private Map prefixToNamespaceMap = new HashMap();

    public XMPSchemaRegistryImpl() {
        try {
            registerStandardNamespaces();
            registerStandardAliases();
        } catch (XMPException unused) {
            throw new RuntimeException("The XMPSchemaRegistry cannot be initialized!");
        }
    }

    public synchronized String registerNamespace(String str, String str2) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPrefix(str2);
        if (str2.charAt(str2.length() - 1) != ':') {
            str2 = str2 + ':';
        }
        if (Utils.isXMLNameNS(str2.substring(0, str2.length() - 1))) {
            String str3 = (String) this.namespaceToPrefixMap.get(str);
            String str4 = (String) this.prefixToNamespaceMap.get(str2);
            if (str3 != null) {
                return str3;
            }
            if (str4 != null) {
                String str5 = str2;
                int i = 1;
                while (this.prefixToNamespaceMap.containsKey(str5)) {
                    str5 = str2.substring(0, str2.length() - 1) + "_" + i + "_:";
                    i++;
                }
                str2 = str5;
            }
            this.prefixToNamespaceMap.put(str2, str);
            this.namespaceToPrefixMap.put(str, str2);
            return str2;
        }
        throw new XMPException("The prefix is a bad XML name", 201);
    }

    public synchronized void deleteNamespace(String str) {
        String namespacePrefix = getNamespacePrefix(str);
        if (namespacePrefix != null) {
            this.namespaceToPrefixMap.remove(str);
            this.prefixToNamespaceMap.remove(namespacePrefix);
        }
    }

    public synchronized String getNamespacePrefix(String str) {
        return (String) this.namespaceToPrefixMap.get(str);
    }

    public synchronized String getNamespaceURI(String str) {
        if (str != null) {
            if (!str.endsWith(":")) {
                str = str + ":";
            }
        }
        return (String) this.prefixToNamespaceMap.get(str);
    }

    public synchronized Map getNamespaces() {
        return Collections.unmodifiableMap(new TreeMap(this.namespaceToPrefixMap));
    }

    public synchronized Map getPrefixes() {
        return Collections.unmodifiableMap(new TreeMap(this.prefixToNamespaceMap));
    }

    private void registerStandardNamespaces() throws XMPException {
        registerNamespace(XMPConst.NS_XML, "xml");
        registerNamespace(XMPConst.NS_RDF, "rdf");
        registerNamespace("http://purl.org/dc/elements/1.1/", DublinCoreSchema.DEFAULT_XPATH_ID);
        registerNamespace(XMPConst.NS_IPTCCORE, "Iptc4xmpCore");
        registerNamespace(XMPConst.NS_IPTCEXT, "Iptc4xmpExt");
        registerNamespace(XMPConst.NS_DICOM, "DICOM");
        registerNamespace(XMPConst.NS_PLUS, "plus");
        registerNamespace(XMPConst.NS_X, "x");
        registerNamespace(XMPConst.NS_IX, "iX");
        registerNamespace("http://ns.adobe.com/xap/1.0/", XmpBasicSchema.DEFAULT_XPATH_ID);
        registerNamespace(XMPConst.NS_XMP_RIGHTS, "xmpRights");
        registerNamespace("http://ns.adobe.com/xap/1.0/mm/", XmpMMSchema.DEFAULT_XPATH_ID);
        registerNamespace(XMPConst.NS_XMP_BJ, "xmpBJ");
        registerNamespace(XMPConst.NS_XMP_NOTE, "xmpNote");
        registerNamespace("http://ns.adobe.com/pdf/1.3/", PdfSchema.DEFAULT_XPATH_ID);
        registerNamespace(XMPConst.NS_PDFX, "pdfx");
        registerNamespace(XMPConst.NS_PDFX_ID, "pdfxid");
        registerNamespace(XMPConst.NS_PDFA_SCHEMA, "pdfaSchema");
        registerNamespace(XMPConst.NS_PDFA_PROPERTY, "pdfaProperty");
        registerNamespace(XMPConst.NS_PDFA_TYPE, "pdfaType");
        registerNamespace(XMPConst.NS_PDFA_FIELD, "pdfaField");
        registerNamespace(XMPConst.NS_PDFA_ID, "pdfaid");
        registerNamespace(XMPConst.NS_PDFUA_ID, "pdfuaid");
        registerNamespace(XMPConst.NS_PDFA_EXTENSION, "pdfaExtension");
        registerNamespace(XMPConst.NS_PHOTOSHOP, "photoshop");
        registerNamespace(XMPConst.NS_PSALBUM, "album");
        registerNamespace(XMPConst.NS_EXIF, "exif");
        registerNamespace(XMPConst.NS_EXIFX, "exifEX");
        registerNamespace(XMPConst.NS_EXIF_AUX, "aux");
        registerNamespace(XMPConst.NS_TIFF, "tiff");
        registerNamespace(XMPConst.NS_PNG, "png");
        registerNamespace(XMPConst.NS_JPEG, "jpeg");
        registerNamespace(XMPConst.NS_JP2K, "jp2k");
        registerNamespace(XMPConst.NS_CAMERARAW, "crs");
        registerNamespace(XMPConst.NS_ADOBESTOCKPHOTO, "bmsp");
        registerNamespace(XMPConst.NS_CREATOR_ATOM, "creatorAtom");
        registerNamespace(XMPConst.NS_ASF, "asf");
        registerNamespace(XMPConst.NS_WAV, "wav");
        registerNamespace(XMPConst.NS_BWF, "bext");
        registerNamespace(XMPConst.NS_RIFFINFO, "riffinfo");
        registerNamespace(XMPConst.NS_SCRIPT, "xmpScript");
        registerNamespace(XMPConst.NS_TXMP, "txmp");
        registerNamespace(XMPConst.NS_SWF, "swf");
        registerNamespace(XMPConst.NS_DM, "xmpDM");
        registerNamespace(XMPConst.NS_TRANSIENT, "xmpx");
        registerNamespace(XMPConst.TYPE_TEXT, "xmpT");
        registerNamespace(XMPConst.TYPE_PAGEDFILE, "xmpTPg");
        registerNamespace(XMPConst.TYPE_GRAPHICS, "xmpG");
        registerNamespace(XMPConst.TYPE_IMAGE, "xmpGImg");
        registerNamespace(XMPConst.TYPE_FONT, "stFnt");
        registerNamespace(XMPConst.TYPE_DIMENSIONS, "stDim");
        registerNamespace(XMPConst.TYPE_RESOURCEEVENT, "stEvt");
        registerNamespace(XMPConst.TYPE_RESOURCEREF, "stRef");
        registerNamespace(XMPConst.TYPE_ST_VERSION, "stVer");
        registerNamespace(XMPConst.TYPE_ST_JOB, "stJob");
        registerNamespace(XMPConst.TYPE_MANIFESTITEM, "stMfs");
        registerNamespace(XMPConst.TYPE_IDENTIFIERQUAL, "xmpidq");
    }

    public synchronized XMPAliasInfo resolveAlias(String str, String str2) {
        String namespacePrefix = getNamespacePrefix(str);
        if (namespacePrefix == null) {
            return null;
        }
        Map map = this.aliasMap;
        return (XMPAliasInfo) map.get(namespacePrefix + str2);
    }

    public synchronized XMPAliasInfo findAlias(String str) {
        return (XMPAliasInfo) this.aliasMap.get(str);
    }

    public synchronized XMPAliasInfo[] findAliases(String str) {
        ArrayList arrayList;
        String namespacePrefix = getNamespacePrefix(str);
        arrayList = new ArrayList();
        if (namespacePrefix != null) {
            for (String str2 : this.aliasMap.keySet()) {
                if (str2.startsWith(namespacePrefix)) {
                    arrayList.add(findAlias(str2));
                }
            }
        }
        return (XMPAliasInfo[]) arrayList.toArray(new XMPAliasInfo[arrayList.size()]);
    }

    /* access modifiers changed from: package-private */
    public synchronized void registerAlias(String str, String str2, String str3, String str4, AliasOptions aliasOptions) throws XMPException {
        ParameterAsserts.assertSchemaNS(str);
        ParameterAsserts.assertPropName(str2);
        ParameterAsserts.assertSchemaNS(str3);
        ParameterAsserts.assertPropName(str4);
        final AliasOptions aliasOptions2 = aliasOptions != null ? new AliasOptions(XMPNodeUtils.verifySetOptions(aliasOptions.toPropertyOptions(), (Object) null).getOptions()) : new AliasOptions();
        if (this.f780p.matcher(str2).find() || this.f780p.matcher(str4).find()) {
            throw new XMPException("Alias and actual property names must be simple", 102);
        }
        String namespacePrefix = getNamespacePrefix(str);
        final String namespacePrefix2 = getNamespacePrefix(str3);
        if (namespacePrefix == null) {
            throw new XMPException("Alias namespace is not registered", 101);
        } else if (namespacePrefix2 != null) {
            String str5 = namespacePrefix + str2;
            if (!this.aliasMap.containsKey(str5)) {
                if (!this.aliasMap.containsKey(namespacePrefix2 + str4)) {
                    final String str6 = str3;
                    final String str7 = str4;
                    this.aliasMap.put(str5, new XMPAliasInfo() {
                        public String getNamespace() {
                            return str6;
                        }

                        public String getPrefix() {
                            return namespacePrefix2;
                        }

                        public String getPropName() {
                            return str7;
                        }

                        public AliasOptions getAliasForm() {
                            return aliasOptions2;
                        }

                        public String toString() {
                            return namespacePrefix2 + str7 + " NS(" + str6 + "), FORM (" + getAliasForm() + ")";
                        }
                    });
                } else {
                    throw new XMPException("Actual property is already an alias, use the base property", 4);
                }
            } else {
                throw new XMPException("Alias is already existing", 4);
            }
        } else {
            throw new XMPException("Actual namespace is not registered", 101);
        }
    }

    public synchronized Map getAliases() {
        return Collections.unmodifiableMap(new TreeMap(this.aliasMap));
    }

    private void registerStandardAliases() throws XMPException {
        AliasOptions arrayOrdered = new AliasOptions().setArrayOrdered(true);
        AliasOptions arrayAltText = new AliasOptions().setArrayAltText(true);
        registerAlias("http://ns.adobe.com/xap/1.0/", "Author", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.CREATOR, arrayOrdered);
        registerAlias("http://ns.adobe.com/xap/1.0/", "Authors", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.CREATOR, (AliasOptions) null);
        registerAlias("http://ns.adobe.com/xap/1.0/", "Description", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.DESCRIPTION, (AliasOptions) null);
        registerAlias("http://ns.adobe.com/xap/1.0/", "Format", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.FORMAT, (AliasOptions) null);
        registerAlias("http://ns.adobe.com/xap/1.0/", PdfProperties.KEYWORDS, "http://purl.org/dc/elements/1.1/", "subject", (AliasOptions) null);
        registerAlias("http://ns.adobe.com/xap/1.0/", "Locale", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.LANGUAGE, (AliasOptions) null);
        registerAlias("http://ns.adobe.com/xap/1.0/", "Title", "http://purl.org/dc/elements/1.1/", "title", (AliasOptions) null);
        registerAlias(XMPConst.NS_XMP_RIGHTS, ExifInterface.TAG_COPYRIGHT, "http://purl.org/dc/elements/1.1/", DublinCoreProperties.RIGHTS, (AliasOptions) null);
        registerAlias("http://ns.adobe.com/pdf/1.3/", "Author", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.CREATOR, arrayOrdered);
        registerAlias("http://ns.adobe.com/pdf/1.3/", XmpBasicProperties.BASEURL, "http://ns.adobe.com/xap/1.0/", XmpBasicProperties.BASEURL, (AliasOptions) null);
        registerAlias("http://ns.adobe.com/pdf/1.3/", "CreationDate", "http://ns.adobe.com/xap/1.0/", XmpBasicProperties.CREATEDATE, (AliasOptions) null);
        registerAlias("http://ns.adobe.com/pdf/1.3/", "Creator", "http://ns.adobe.com/xap/1.0/", XmpBasicProperties.CREATORTOOL, (AliasOptions) null);
        registerAlias("http://ns.adobe.com/pdf/1.3/", "ModDate", "http://ns.adobe.com/xap/1.0/", XmpBasicProperties.MODIFYDATE, (AliasOptions) null);
        AliasOptions aliasOptions = arrayAltText;
        registerAlias("http://ns.adobe.com/pdf/1.3/", "Subject", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.DESCRIPTION, aliasOptions);
        registerAlias("http://ns.adobe.com/pdf/1.3/", "Title", "http://purl.org/dc/elements/1.1/", "title", aliasOptions);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Author", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.CREATOR, arrayOrdered);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Caption", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.DESCRIPTION, aliasOptions);
        registerAlias(XMPConst.NS_PHOTOSHOP, ExifInterface.TAG_COPYRIGHT, "http://purl.org/dc/elements/1.1/", DublinCoreProperties.RIGHTS, aliasOptions);
        registerAlias(XMPConst.NS_PHOTOSHOP, PdfProperties.KEYWORDS, "http://purl.org/dc/elements/1.1/", "subject", (AliasOptions) null);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Marked", XMPConst.NS_XMP_RIGHTS, "Marked", (AliasOptions) null);
        registerAlias(XMPConst.NS_PHOTOSHOP, "Title", "http://purl.org/dc/elements/1.1/", "title", arrayAltText);
        registerAlias(XMPConst.NS_PHOTOSHOP, "WebStatement", XMPConst.NS_XMP_RIGHTS, "WebStatement", (AliasOptions) null);
        registerAlias(XMPConst.NS_TIFF, ExifInterface.TAG_ARTIST, "http://purl.org/dc/elements/1.1/", DublinCoreProperties.CREATOR, arrayOrdered);
        registerAlias(XMPConst.NS_TIFF, ExifInterface.TAG_COPYRIGHT, "http://purl.org/dc/elements/1.1/", DublinCoreProperties.RIGHTS, (AliasOptions) null);
        registerAlias(XMPConst.NS_TIFF, ExifInterface.TAG_DATETIME, "http://ns.adobe.com/xap/1.0/", XmpBasicProperties.MODIFYDATE, (AliasOptions) null);
        registerAlias(XMPConst.NS_TIFF, ExifInterface.TAG_IMAGE_DESCRIPTION, "http://purl.org/dc/elements/1.1/", DublinCoreProperties.DESCRIPTION, (AliasOptions) null);
        registerAlias(XMPConst.NS_TIFF, ExifInterface.TAG_SOFTWARE, "http://ns.adobe.com/xap/1.0/", XmpBasicProperties.CREATORTOOL, (AliasOptions) null);
        registerAlias(XMPConst.NS_PNG, "Author", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.CREATOR, arrayOrdered);
        registerAlias(XMPConst.NS_PNG, ExifInterface.TAG_COPYRIGHT, "http://purl.org/dc/elements/1.1/", DublinCoreProperties.RIGHTS, arrayAltText);
        registerAlias(XMPConst.NS_PNG, "CreationTime", "http://ns.adobe.com/xap/1.0/", XmpBasicProperties.CREATEDATE, (AliasOptions) null);
        registerAlias(XMPConst.NS_PNG, "Description", "http://purl.org/dc/elements/1.1/", DublinCoreProperties.DESCRIPTION, arrayAltText);
        registerAlias(XMPConst.NS_PNG, "ModificationTime", "http://ns.adobe.com/xap/1.0/", XmpBasicProperties.MODIFYDATE, (AliasOptions) null);
        registerAlias(XMPConst.NS_PNG, ExifInterface.TAG_SOFTWARE, "http://ns.adobe.com/xap/1.0/", XmpBasicProperties.CREATORTOOL, (AliasOptions) null);
        registerAlias(XMPConst.NS_PNG, "Title", "http://purl.org/dc/elements/1.1/", "title", arrayAltText);
    }
}
