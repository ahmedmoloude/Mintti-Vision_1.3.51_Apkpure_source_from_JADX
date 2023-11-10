package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.IOException;

public class PdfShadingPattern extends PdfDictionary {
    protected float[] matrix = {1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
    protected PdfName patternName;
    protected PdfIndirectReference patternReference;
    protected PdfShading shading;
    protected PdfWriter writer;

    public PdfShadingPattern(PdfShading pdfShading) {
        this.writer = pdfShading.getWriter();
        put(PdfName.PATTERNTYPE, new PdfNumber(2));
        this.shading = pdfShading;
    }

    /* access modifiers changed from: package-private */
    public PdfName getPatternName() {
        return this.patternName;
    }

    /* access modifiers changed from: package-private */
    public PdfName getShadingName() {
        return this.shading.getShadingName();
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference getPatternReference() {
        if (this.patternReference == null) {
            this.patternReference = this.writer.getPdfIndirectReference();
        }
        return this.patternReference;
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference getShadingReference() {
        return this.shading.getShadingReference();
    }

    /* access modifiers changed from: package-private */
    public void setName(int i) {
        this.patternName = new PdfName("P" + i);
    }

    public void addToBody() throws IOException {
        put(PdfName.SHADING, getShadingReference());
        put(PdfName.MATRIX, new PdfArray(this.matrix));
        this.writer.addToBody((PdfObject) this, getPatternReference());
    }

    public void setMatrix(float[] fArr) {
        if (fArr.length == 6) {
            this.matrix = fArr;
            return;
        }
        throw new RuntimeException(MessageLocalization.getComposedMessage("the.matrix.size.must.be.6", new Object[0]));
    }

    public float[] getMatrix() {
        return this.matrix;
    }

    public PdfShading getShading() {
        return this.shading;
    }

    /* access modifiers changed from: package-private */
    public ColorDetails getColorDetails() {
        return this.shading.getColorDetails();
    }
}
