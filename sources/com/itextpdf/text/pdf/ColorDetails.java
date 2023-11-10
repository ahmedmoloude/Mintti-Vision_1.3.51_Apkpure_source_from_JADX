package com.itextpdf.text.pdf;

class ColorDetails {
    ICachedColorSpace colorSpace;
    PdfName colorSpaceName;
    PdfIndirectReference indirectReference;

    ColorDetails(PdfName pdfName, PdfIndirectReference pdfIndirectReference, ICachedColorSpace iCachedColorSpace) {
        this.colorSpaceName = pdfName;
        this.indirectReference = pdfIndirectReference;
        this.colorSpace = iCachedColorSpace;
    }

    public PdfIndirectReference getIndirectReference() {
        return this.indirectReference;
    }

    /* access modifiers changed from: package-private */
    public PdfName getColorSpaceName() {
        return this.colorSpaceName;
    }

    public PdfObject getPdfObject(PdfWriter pdfWriter) {
        return this.colorSpace.getPdfObject(pdfWriter);
    }
}
