package com.itextpdf.text.pdf.events;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;
import com.itextpdf.text.pdf.PdfPTableEventAfterSplit;
import com.itextpdf.text.pdf.PdfPTableEventSplit;
import java.util.ArrayList;
import java.util.Iterator;

public class PdfPTableEventForwarder implements PdfPTableEventAfterSplit {
    protected ArrayList<PdfPTableEvent> events = new ArrayList<>();

    public void addTableEvent(PdfPTableEvent pdfPTableEvent) {
        this.events.add(pdfPTableEvent);
    }

    public void tableLayout(PdfPTable pdfPTable, float[][] fArr, float[] fArr2, int i, int i2, PdfContentByte[] pdfContentByteArr) {
        Iterator<PdfPTableEvent> it = this.events.iterator();
        while (it.hasNext()) {
            it.next().tableLayout(pdfPTable, fArr, fArr2, i, i2, pdfContentByteArr);
        }
    }

    public void splitTable(PdfPTable pdfPTable) {
        Iterator<PdfPTableEvent> it = this.events.iterator();
        while (it.hasNext()) {
            PdfPTableEvent next = it.next();
            if (next instanceof PdfPTableEventSplit) {
                ((PdfPTableEventSplit) next).splitTable(pdfPTable);
            }
        }
    }

    public void afterSplitTable(PdfPTable pdfPTable, PdfPRow pdfPRow, int i) {
        Iterator<PdfPTableEvent> it = this.events.iterator();
        while (it.hasNext()) {
            PdfPTableEvent next = it.next();
            if (next instanceof PdfPTableEventAfterSplit) {
                ((PdfPTableEventAfterSplit) next).afterSplitTable(pdfPTable, pdfPRow, i);
            }
        }
    }
}
