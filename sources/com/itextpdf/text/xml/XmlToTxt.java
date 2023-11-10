package com.itextpdf.text.xml;

import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandlerComment;
import com.itextpdf.text.xml.simpleparser.SimpleXMLParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class XmlToTxt implements SimpleXMLDocHandler {
    protected StringBuffer buf = new StringBuffer();

    public void endDocument() {
    }

    public void endElement(String str) {
    }

    public void startDocument() {
    }

    public void startElement(String str, Map<String, String> map) {
    }

    public static String parse(InputStream inputStream) throws IOException {
        XmlToTxt xmlToTxt = new XmlToTxt();
        SimpleXMLParser.parse(xmlToTxt, (SimpleXMLDocHandlerComment) null, new InputStreamReader(inputStream), true);
        return xmlToTxt.toString();
    }

    protected XmlToTxt() {
    }

    public String toString() {
        return this.buf.toString();
    }

    public void text(String str) {
        this.buf.append(str);
    }
}
