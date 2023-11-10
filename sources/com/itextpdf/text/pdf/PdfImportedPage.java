package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.IOException;

public class PdfImportedPage extends PdfTemplate {
    int pageNumber;
    PdfReaderInstance readerInstance;
    int rotation;
    protected boolean toCopy = true;

    public PdfImportedPage getFromReader() {
        return this;
    }

    PdfImportedPage(PdfReaderInstance pdfReaderInstance, PdfWriter pdfWriter, int i) {
        this.readerInstance = pdfReaderInstance;
        this.pageNumber = i;
        this.writer = pdfWriter;
        this.rotation = pdfReaderInstance.getReader().getPageRotation(i);
        this.bBox = pdfReaderInstance.getReader().getPageSize(i);
        setMatrix(1.0f, 0.0f, 0.0f, 1.0f, -this.bBox.getLeft(), -this.bBox.getBottom());
        this.type = 2;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void addImage(Image image, float f, float f2, float f3, float f4, float f5, float f6) throws DocumentException {
        throwError();
    }

    public void addTemplate(PdfTemplate pdfTemplate, float f, float f2, float f3, float f4, float f5, float f6) {
        throwError();
    }

    public PdfContentByte getDuplicate() {
        throwError();
        return null;
    }

    public PdfStream getFormXObject(int i) throws IOException {
        return this.readerInstance.getFormXObject(this.pageNumber, i);
    }

    public void setColorFill(PdfSpotColor pdfSpotColor, float f) {
        throwError();
    }

    public void setColorStroke(PdfSpotColor pdfSpotColor, float f) {
        throwError();
    }

    /* access modifiers changed from: package-private */
    public PdfObject getResources() {
        return this.readerInstance.getResources(this.pageNumber);
    }

    public void setFontAndSize(BaseFont baseFont, float f) {
        throwError();
    }

    public void setGroup(PdfTransparencyGroup pdfTransparencyGroup) {
        throwError();
    }

    /* access modifiers changed from: package-private */
    public void throwError() {
        throw new RuntimeException(MessageLocalization.getComposedMessage("content.can.not.be.added.to.a.pdfimportedpage", new Object[0]));
    }

    /* access modifiers changed from: package-private */
    public PdfReaderInstance getPdfReaderInstance() {
        return this.readerInstance;
    }

    public boolean isToCopy() {
        return this.toCopy;
    }

    public void setCopied() {
        this.toCopy = false;
    }
}
