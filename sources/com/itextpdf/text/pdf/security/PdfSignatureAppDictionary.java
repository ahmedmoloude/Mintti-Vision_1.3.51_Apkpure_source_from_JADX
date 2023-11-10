package com.itextpdf.text.pdf.security;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;

public class PdfSignatureAppDictionary extends PdfDictionary {
    public void setSignatureCreator(String str) {
        put(PdfName.NAME, new PdfString(str, PdfObject.TEXT_UNICODE));
    }
}
