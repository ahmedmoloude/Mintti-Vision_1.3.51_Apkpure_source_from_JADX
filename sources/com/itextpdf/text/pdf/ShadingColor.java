package com.itextpdf.text.pdf;

public class ShadingColor extends ExtendedColor {
    private static final long serialVersionUID = 4817929454941328671L;
    PdfShadingPattern shadingPattern;

    public ShadingColor(PdfShadingPattern pdfShadingPattern) {
        super(5, 0.5f, 0.5f, 0.5f);
        this.shadingPattern = pdfShadingPattern;
    }

    public PdfShadingPattern getPdfShadingPattern() {
        return this.shadingPattern;
    }

    public boolean equals(Object obj) {
        return (obj instanceof ShadingColor) && ((ShadingColor) obj).shadingPattern.equals(this.shadingPattern);
    }

    public int hashCode() {
        return this.shadingPattern.hashCode();
    }
}
