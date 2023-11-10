package com.itextpdf.text.pdf;

import java.io.IOException;
import java.io.OutputStream;

public class PdfGState extends PdfDictionary {
    public static final PdfName BM_COLORBURN = new PdfName("ColorBurn");
    public static final PdfName BM_COLORDODGE = new PdfName("ColorDodge");
    public static final PdfName BM_COMPATIBLE = new PdfName("Compatible");
    public static final PdfName BM_DARKEN = new PdfName("Darken");
    public static final PdfName BM_DIFFERENCE = new PdfName("Difference");
    public static final PdfName BM_EXCLUSION = new PdfName("Exclusion");
    public static final PdfName BM_HARDLIGHT = new PdfName("HardLight");
    public static final PdfName BM_LIGHTEN = new PdfName("Lighten");
    public static final PdfName BM_MULTIPLY = new PdfName("Multiply");
    public static final PdfName BM_NORMAL = new PdfName("Normal");
    public static final PdfName BM_OVERLAY = new PdfName("Overlay");
    public static final PdfName BM_SCREEN = new PdfName("Screen");
    public static final PdfName BM_SOFTLIGHT = new PdfName("SoftLight");

    public void setOverPrintStroking(boolean z) {
        put(PdfName.f621OP, z ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
    }

    public void setOverPrintNonStroking(boolean z) {
        put(PdfName.f678op, z ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
    }

    public void setOverPrintMode(int i) {
        put(PdfName.OPM, new PdfNumber(i == 0 ? 0 : 1));
    }

    public void setStrokeOpacity(float f) {
        put(PdfName.f560CA, new PdfNumber(f));
    }

    public void setFillOpacity(float f) {
        put(PdfName.f677ca, new PdfNumber(f));
    }

    public void setAlphaIsShape(boolean z) {
        put(PdfName.AIS, z ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
    }

    public void setTextKnockout(boolean z) {
        put(PdfName.f652TK, z ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
    }

    public void setBlendMode(PdfName pdfName) {
        put(PdfName.f555BM, pdfName);
    }

    public void setRenderingIntent(PdfName pdfName) {
        put(PdfName.f636RI, pdfName);
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        PdfWriter.checkPdfIsoConformance(pdfWriter, 6, this);
        super.toPdf(pdfWriter, outputStream);
    }
}
