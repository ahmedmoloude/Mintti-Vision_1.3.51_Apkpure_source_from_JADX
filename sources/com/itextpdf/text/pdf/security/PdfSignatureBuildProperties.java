package com.itextpdf.text.pdf.security;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;

public class PdfSignatureBuildProperties extends PdfDictionary {
    public void setSignatureCreator(String str) {
        getPdfSignatureAppProperty().setSignatureCreator(str);
    }

    private PdfSignatureAppDictionary getPdfSignatureAppProperty() {
        PdfSignatureAppDictionary pdfSignatureAppDictionary = (PdfSignatureAppDictionary) getAsDict(PdfName.APP);
        if (pdfSignatureAppDictionary != null) {
            return pdfSignatureAppDictionary;
        }
        PdfSignatureAppDictionary pdfSignatureAppDictionary2 = new PdfSignatureAppDictionary();
        put(PdfName.APP, pdfSignatureAppDictionary2);
        return pdfSignatureAppDictionary2;
    }
}
