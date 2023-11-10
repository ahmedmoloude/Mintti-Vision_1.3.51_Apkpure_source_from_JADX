package com.itextpdf.awt;

import com.itextpdf.text.pdf.PdfContentByte;
import java.awt.print.PrinterGraphics;
import java.awt.print.PrinterJob;

public class PdfPrinterGraphics2D extends PdfGraphics2D implements PrinterGraphics {
    private PrinterJob printerJob;

    public PdfPrinterGraphics2D(PdfContentByte pdfContentByte, float f, float f2, PrinterJob printerJob2) {
        super(pdfContentByte, f, f2);
        this.printerJob = printerJob2;
    }

    public PdfPrinterGraphics2D(PdfContentByte pdfContentByte, float f, float f2, boolean z, PrinterJob printerJob2) {
        super(pdfContentByte, f, f2, z);
        this.printerJob = printerJob2;
    }

    public PdfPrinterGraphics2D(PdfContentByte pdfContentByte, float f, float f2, FontMapper fontMapper, PrinterJob printerJob2) {
        super(pdfContentByte, f, f2, fontMapper, false, false, 0.0f);
        this.printerJob = printerJob2;
    }

    public PdfPrinterGraphics2D(PdfContentByte pdfContentByte, float f, float f2, FontMapper fontMapper, boolean z, boolean z2, float f3, PrinterJob printerJob2) {
        super(pdfContentByte, f, f2, fontMapper, z, z2, f3);
        this.printerJob = printerJob2;
    }

    public PrinterJob getPrinterJob() {
        return this.printerJob;
    }
}
