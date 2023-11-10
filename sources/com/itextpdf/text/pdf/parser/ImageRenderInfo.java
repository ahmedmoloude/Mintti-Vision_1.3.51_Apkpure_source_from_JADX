package com.itextpdf.text.pdf.parser;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfIndirectReference;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import java.io.IOException;

public class ImageRenderInfo {
    private final PdfDictionary colorSpaceDictionary;

    /* renamed from: gs */
    private final GraphicsState f743gs;
    private PdfImageObject imageObject = null;
    private final InlineImageInfo inlineImageInfo;
    private final PdfIndirectReference ref;

    private ImageRenderInfo(GraphicsState graphicsState, PdfIndirectReference pdfIndirectReference, PdfDictionary pdfDictionary) {
        this.f743gs = graphicsState;
        this.ref = pdfIndirectReference;
        this.inlineImageInfo = null;
        this.colorSpaceDictionary = pdfDictionary;
    }

    private ImageRenderInfo(GraphicsState graphicsState, InlineImageInfo inlineImageInfo2, PdfDictionary pdfDictionary) {
        this.f743gs = graphicsState;
        this.ref = null;
        this.inlineImageInfo = inlineImageInfo2;
        this.colorSpaceDictionary = pdfDictionary;
    }

    public static ImageRenderInfo createForXObject(GraphicsState graphicsState, PdfIndirectReference pdfIndirectReference, PdfDictionary pdfDictionary) {
        return new ImageRenderInfo(graphicsState, pdfIndirectReference, pdfDictionary);
    }

    protected static ImageRenderInfo createForEmbeddedImage(GraphicsState graphicsState, InlineImageInfo inlineImageInfo2, PdfDictionary pdfDictionary) {
        return new ImageRenderInfo(graphicsState, inlineImageInfo2, pdfDictionary);
    }

    public PdfImageObject getImage() throws IOException {
        prepareImageObject();
        return this.imageObject;
    }

    private void prepareImageObject() throws IOException {
        if (this.imageObject == null) {
            PdfIndirectReference pdfIndirectReference = this.ref;
            if (pdfIndirectReference != null) {
                this.imageObject = new PdfImageObject((PRStream) PdfReader.getPdfObject((PdfObject) pdfIndirectReference), this.colorSpaceDictionary);
                return;
            }
            InlineImageInfo inlineImageInfo2 = this.inlineImageInfo;
            if (inlineImageInfo2 != null) {
                this.imageObject = new PdfImageObject(inlineImageInfo2.getImageDictionary(), this.inlineImageInfo.getSamples(), this.colorSpaceDictionary);
            }
        }
    }

    public Vector getStartPoint() {
        return new Vector(0.0f, 0.0f, 1.0f).cross(this.f743gs.ctm);
    }

    public Matrix getImageCTM() {
        return this.f743gs.ctm;
    }

    public float getArea() {
        return this.f743gs.ctm.getDeterminant();
    }

    public PdfIndirectReference getRef() {
        return this.ref;
    }

    public BaseColor getCurrentFillColor() {
        return this.f743gs.fillColor;
    }
}
