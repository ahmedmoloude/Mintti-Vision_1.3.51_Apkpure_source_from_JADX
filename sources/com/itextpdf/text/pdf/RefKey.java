package com.itextpdf.text.pdf;

public class RefKey {
    int gen;
    int num;

    RefKey(int i, int i2) {
        this.num = i;
        this.gen = i2;
    }

    public RefKey(PdfIndirectReference pdfIndirectReference) {
        this.num = pdfIndirectReference.getNumber();
        this.gen = pdfIndirectReference.getGeneration();
    }

    RefKey(PRIndirectReference pRIndirectReference) {
        this.num = pRIndirectReference.getNumber();
        this.gen = pRIndirectReference.getGeneration();
    }

    public int hashCode() {
        return (this.gen << 16) + this.num;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RefKey)) {
            return false;
        }
        RefKey refKey = (RefKey) obj;
        if (this.gen == refKey.gen && this.num == refKey.num) {
            return true;
        }
        return false;
    }

    public String toString() {
        return Integer.toString(this.num) + ' ' + this.gen;
    }
}
