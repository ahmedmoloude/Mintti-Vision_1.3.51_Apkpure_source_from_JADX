package com.itextpdf.text.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;

public class PdfPageEventHelper implements PdfPageEvent {
    public void onChapter(PdfWriter pdfWriter, Document document, float f, Paragraph paragraph) {
    }

    public void onChapterEnd(PdfWriter pdfWriter, Document document, float f) {
    }

    public void onCloseDocument(PdfWriter pdfWriter, Document document) {
    }

    public void onEndPage(PdfWriter pdfWriter, Document document) {
    }

    public void onGenericTag(PdfWriter pdfWriter, Document document, Rectangle rectangle, String str) {
    }

    public void onOpenDocument(PdfWriter pdfWriter, Document document) {
    }

    public void onParagraph(PdfWriter pdfWriter, Document document, float f) {
    }

    public void onParagraphEnd(PdfWriter pdfWriter, Document document, float f) {
    }

    public void onSection(PdfWriter pdfWriter, Document document, float f, int i, Paragraph paragraph) {
    }

    public void onSectionEnd(PdfWriter pdfWriter, Document document, float f) {
    }

    public void onStartPage(PdfWriter pdfWriter, Document document) {
    }
}
