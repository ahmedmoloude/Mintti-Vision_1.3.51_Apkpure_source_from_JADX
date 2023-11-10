package com.itextpdf.text.pdf.events;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class PdfPCellEventForwarder implements PdfPCellEvent {
    protected ArrayList<PdfPCellEvent> events = new ArrayList<>();

    public void addCellEvent(PdfPCellEvent pdfPCellEvent) {
        this.events.add(pdfPCellEvent);
    }

    public void cellLayout(PdfPCell pdfPCell, Rectangle rectangle, PdfContentByte[] pdfContentByteArr) {
        Iterator<PdfPCellEvent> it = this.events.iterator();
        while (it.hasNext()) {
            it.next().cellLayout(pdfPCell, rectangle, pdfContentByteArr);
        }
    }
}
