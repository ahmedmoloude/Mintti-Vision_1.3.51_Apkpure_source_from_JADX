package com.itextpdf.text.pdf;

class PdfResources extends PdfDictionary {
    PdfResources() {
    }

    /* access modifiers changed from: package-private */
    public void add(PdfName pdfName, PdfDictionary pdfDictionary) {
        if (pdfDictionary.size() != 0) {
            PdfDictionary asDict = getAsDict(pdfName);
            if (asDict == null) {
                put(pdfName, pdfDictionary);
            } else {
                asDict.putAll(pdfDictionary);
            }
        }
    }
}
