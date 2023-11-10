package com.itextpdf.text.pdf.parser;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.xml.XMLUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class TaggedPdfReaderTool {
    protected PrintWriter out;
    protected PdfReader reader;

    public void convertToXml(PdfReader pdfReader, OutputStream outputStream, String str) throws IOException {
        this.reader = pdfReader;
        this.out = new PrintWriter(new OutputStreamWriter(outputStream, str));
        PdfDictionary asDict = pdfReader.getCatalog().getAsDict(PdfName.STRUCTTREEROOT);
        if (asDict != null) {
            inspectChild(asDict.getDirectObject(PdfName.f605K));
            this.out.flush();
            this.out.close();
            return;
        }
        throw new IOException(MessageLocalization.getComposedMessage("no.structtreeroot.found", new Object[0]));
    }

    public void convertToXml(PdfReader pdfReader, OutputStream outputStream) throws IOException {
        convertToXml(pdfReader, outputStream, "UTF-8");
    }

    public void inspectChild(PdfObject pdfObject) throws IOException {
        if (pdfObject != null) {
            if (pdfObject instanceof PdfArray) {
                inspectChildArray((PdfArray) pdfObject);
            } else if (pdfObject instanceof PdfDictionary) {
                inspectChildDictionary((PdfDictionary) pdfObject);
            }
        }
    }

    public void inspectChildArray(PdfArray pdfArray) throws IOException {
        if (pdfArray != null) {
            for (int i = 0; i < pdfArray.size(); i++) {
                inspectChild(pdfArray.getDirectObject(i));
            }
        }
    }

    public void inspectChildDictionary(PdfDictionary pdfDictionary) throws IOException {
        inspectChildDictionary(pdfDictionary, false);
    }

    public void inspectChildDictionary(PdfDictionary pdfDictionary, boolean z) throws IOException {
        PdfDictionary asDict;
        if (pdfDictionary != null) {
            PdfName asName = pdfDictionary.getAsName(PdfName.f642S);
            if (asName != null) {
                String decodeName = PdfName.decodeName(asName.toString());
                String fixTagName = fixTagName(decodeName);
                this.out.print("<");
                this.out.print(fixTagName);
                if (z && (asDict = pdfDictionary.getAsDict(PdfName.f545A)) != null) {
                    for (PdfName next : asDict.getKeys()) {
                        this.out.print(' ');
                        PdfObject pdfObject = PdfReader.getPdfObject(asDict.get(next));
                        this.out.print(xmlName(next));
                        this.out.print("=\"");
                        this.out.print(pdfObject.toString());
                        this.out.print("\"");
                    }
                }
                this.out.print(">");
                PdfObject pdfObject2 = pdfDictionary.get(PdfName.ALT);
                if (!(pdfObject2 == null || pdfObject2.toString() == null)) {
                    this.out.print("<alt><![CDATA[");
                    this.out.print(pdfObject2.toString().replaceAll("[\\000]*", ""));
                    this.out.print("]]></alt>");
                }
                PdfDictionary asDict2 = pdfDictionary.getAsDict(PdfName.f626PG);
                if (asDict2 != null) {
                    parseTag(decodeName, pdfDictionary.getDirectObject(PdfName.f605K), asDict2);
                }
                inspectChild(pdfDictionary.getDirectObject(PdfName.f605K));
                this.out.print("</");
                this.out.print(fixTagName);
                this.out.println(">");
                return;
            }
            inspectChild(pdfDictionary.getDirectObject(PdfName.f605K));
        }
    }

    /* access modifiers changed from: protected */
    public String xmlName(PdfName pdfName) {
        String replaceFirst = pdfName.toString().replaceFirst("/", "");
        return Character.toLowerCase(replaceFirst.charAt(0)) + replaceFirst.substring(1);
    }

    private static String fixTagName(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            boolean z = true;
            boolean z2 = charAt == ':' || (charAt >= 'A' && charAt <= 'Z') || charAt == '_' || ((charAt >= 'a' && charAt <= 'z') || ((charAt >= 192 && charAt <= 214) || ((charAt >= 216 && charAt <= 246) || ((charAt >= 248 && charAt <= 767) || ((charAt >= 880 && charAt <= 893) || ((charAt >= 895 && charAt <= 8191) || ((charAt >= 8204 && charAt <= 8205) || ((charAt >= 8304 && charAt <= 8591) || ((charAt >= 11264 && charAt <= 12271) || ((charAt >= 12289 && charAt <= 55295) || ((charAt >= 63744 && charAt <= 64975) || (charAt >= 65008 && charAt <= 65533))))))))))));
            if (!(charAt == '-' || charAt == '.' || ((charAt >= '0' && charAt <= '9') || charAt == 183 || ((charAt >= 768 && charAt <= 879) || ((charAt >= 8255 && charAt <= 8256) || z2))))) {
                z = false;
            }
            if (i == 0) {
                if (!z2) {
                    charAt = '_';
                }
            } else if (!z) {
                charAt = '-';
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    public void parseTag(String str, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
        if (pdfObject instanceof PdfNumber) {
            MarkedContentRenderFilter markedContentRenderFilter = new MarkedContentRenderFilter(((PdfNumber) pdfObject).intValue());
            FilteredTextRenderListener filteredTextRenderListener = new FilteredTextRenderListener(new SimpleTextExtractionStrategy(), markedContentRenderFilter);
            new PdfContentStreamProcessor(filteredTextRenderListener).processContent(PdfReader.getPageContent(pdfDictionary), pdfDictionary.getAsDict(PdfName.RESOURCES));
            this.out.print(XMLUtil.escapeXML(filteredTextRenderListener.getResultantText(), true));
        } else if (pdfObject instanceof PdfArray) {
            PdfArray pdfArray = (PdfArray) pdfObject;
            int size = pdfArray.size();
            for (int i = 0; i < size; i++) {
                parseTag(str, pdfArray.getPdfObject(i), pdfDictionary);
                if (i < size - 1) {
                    this.out.println();
                }
            }
        } else if (pdfObject instanceof PdfDictionary) {
            PdfDictionary pdfDictionary2 = (PdfDictionary) pdfObject;
            parseTag(str, pdfDictionary2.getDirectObject(PdfName.MCID), pdfDictionary2.getAsDict(PdfName.f626PG));
        }
    }
}
