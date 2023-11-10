package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;

class PdfFont implements Comparable<PdfFont> {
    private BaseFont font;
    protected float hScale = 1.0f;
    private float size;

    PdfFont(BaseFont baseFont, float f) {
        this.size = f;
        this.font = baseFont;
    }

    public int compareTo(PdfFont pdfFont) {
        if (pdfFont == null) {
            return -1;
        }
        try {
            if (this.font != pdfFont.font) {
                return 1;
            }
            return size() != pdfFont.size() ? 2 : 0;
        } catch (ClassCastException unused) {
            return -2;
        }
    }

    /* access modifiers changed from: package-private */
    public float size() {
        return this.size;
    }

    /* access modifiers changed from: package-private */
    public float width() {
        return width(32);
    }

    /* access modifiers changed from: package-private */
    public float width(int i) {
        return this.font.getWidthPoint(i, this.size) * this.hScale;
    }

    /* access modifiers changed from: package-private */
    public float width(String str) {
        return this.font.getWidthPoint(str, this.size) * this.hScale;
    }

    /* access modifiers changed from: package-private */
    public BaseFont getFont() {
        return this.font;
    }

    static PdfFont getDefaultFont() {
        try {
            return new PdfFont(BaseFont.createFont("Helvetica", "Cp1252", false), 12.0f);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: package-private */
    public void setHorizontalScaling(float f) {
        this.hScale = f;
    }

    /* access modifiers changed from: package-private */
    public float getHorizontalScaling() {
        return this.hScale;
    }
}
