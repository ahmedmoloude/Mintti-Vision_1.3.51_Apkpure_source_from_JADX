package com.itextpdf.text.pdf;

import java.io.IOException;

public class PdfRendition extends PdfDictionary {
    PdfRendition(String str, PdfFileSpecification pdfFileSpecification, String str2) throws IOException {
        put(PdfName.f642S, new PdfName("MR"));
        PdfName pdfName = PdfName.f610N;
        put(pdfName, new PdfString("Rendition for " + str));
        put(PdfName.f557C, new PdfMediaClipData(str, pdfFileSpecification, str2));
    }
}
