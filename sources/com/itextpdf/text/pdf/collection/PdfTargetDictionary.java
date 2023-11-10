package com.itextpdf.text.pdf.collection;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;

public class PdfTargetDictionary extends PdfDictionary {
    public PdfTargetDictionary(PdfTargetDictionary pdfTargetDictionary) {
        put(PdfName.f632R, PdfName.f623P);
        if (pdfTargetDictionary != null) {
            setAdditionalPath(pdfTargetDictionary);
        }
    }

    public PdfTargetDictionary(boolean z) {
        if (z) {
            put(PdfName.f632R, PdfName.f557C);
        } else {
            put(PdfName.f632R, PdfName.f623P);
        }
    }

    public void setEmbeddedFileName(String str) {
        put(PdfName.f610N, new PdfString(str, (String) null));
    }

    public void setFileAttachmentPagename(String str) {
        put(PdfName.f623P, new PdfString(str, (String) null));
    }

    public void setFileAttachmentPage(int i) {
        put(PdfName.f623P, new PdfNumber(i));
    }

    public void setFileAttachmentName(String str) {
        put(PdfName.f545A, new PdfString(str, PdfObject.TEXT_UNICODE));
    }

    public void setFileAttachmentIndex(int i) {
        put(PdfName.f545A, new PdfNumber(i));
    }

    public void setAdditionalPath(PdfTargetDictionary pdfTargetDictionary) {
        put(PdfName.f647T, pdfTargetDictionary);
    }
}
