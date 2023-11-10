package com.itextpdf.text.xml.xmp;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.xml.XmlDomWriter;
import com.itextpdf.xmp.XMPConst;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Deprecated
public class XmpReader {
    public static final String EXTRASPACE = "                                                                                                   \n";
    public static final String XPACKET_PI_BEGIN = "<?xpacket begin=\"﻿\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>\n";
    public static final String XPACKET_PI_END_W = "<?xpacket end=\"w\"?>";
    private Document domDocument;

    public XmpReader(byte[] bArr) throws SAXException, IOException {
        try {
            DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
            newInstance.setNamespaceAware(true);
            this.domDocument = newInstance.newDocumentBuilder().parse(new ByteArrayInputStream(bArr));
        } catch (ParserConfigurationException e) {
            throw new ExceptionConverter(e);
        }
    }

    public boolean replaceNode(String str, String str2, String str3) {
        NodeList elementsByTagNameNS = this.domDocument.getElementsByTagNameNS(str, str2);
        if (elementsByTagNameNS.getLength() == 0) {
            return false;
        }
        for (int i = 0; i < elementsByTagNameNS.getLength(); i++) {
            setNodeText(this.domDocument, elementsByTagNameNS.item(i), str3);
        }
        return true;
    }

    public boolean replaceDescriptionAttribute(String str, String str2, String str3) {
        NodeList elementsByTagNameNS = this.domDocument.getElementsByTagNameNS(XMPConst.NS_RDF, "Description");
        if (elementsByTagNameNS.getLength() == 0) {
            return false;
        }
        for (int i = 0; i < elementsByTagNameNS.getLength(); i++) {
            Node namedItemNS = elementsByTagNameNS.item(i).getAttributes().getNamedItemNS(str, str2);
            if (namedItemNS != null) {
                namedItemNS.setNodeValue(str3);
                return true;
            }
        }
        return false;
    }

    public boolean add(String str, String str2, String str3, String str4) {
        NodeList elementsByTagName = this.domDocument.getElementsByTagName(str);
        if (elementsByTagName.getLength() == 0) {
            return false;
        }
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Node item = elementsByTagName.item(i);
            NamedNodeMap attributes = item.getAttributes();
            for (int i2 = 0; i2 < attributes.getLength(); i2++) {
                Node item2 = attributes.item(i2);
                if (str2.equals(item2.getNodeValue())) {
                    String localName = item2.getLocalName();
                    Element createElementNS = this.domDocument.createElementNS(str2, str3);
                    createElementNS.setPrefix(localName);
                    createElementNS.appendChild(this.domDocument.createTextNode(str4));
                    item.appendChild(createElementNS);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setNodeText(Document document, Node node, String str) {
        if (node == null) {
            return false;
        }
        while (true) {
            Node firstChild = node.getFirstChild();
            if (firstChild != null) {
                node.removeChild(firstChild);
            } else {
                node.appendChild(document.createTextNode(str));
                return true;
            }
        }
    }

    public byte[] serializeDoc() throws IOException {
        XmlDomWriter xmlDomWriter = new XmlDomWriter();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlDomWriter.setOutput(byteArrayOutputStream, (String) null);
        byteArrayOutputStream.write(XPACKET_PI_BEGIN.getBytes("UTF-8"));
        byteArrayOutputStream.flush();
        xmlDomWriter.write(this.domDocument.getElementsByTagName("x:xmpmeta").item(0));
        byteArrayOutputStream.flush();
        for (int i = 0; i < 20; i++) {
            byteArrayOutputStream.write(EXTRASPACE.getBytes());
        }
        byteArrayOutputStream.write(XPACKET_PI_END_W.getBytes());
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
}
