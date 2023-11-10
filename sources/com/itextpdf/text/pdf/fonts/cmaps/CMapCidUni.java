package com.itextpdf.text.pdf.fonts.cmaps;

import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.IntHashtable;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;

public class CMapCidUni extends AbstractCMap {
    private IntHashtable map = new IntHashtable(65537);

    /* access modifiers changed from: package-private */
    public void addChar(PdfString pdfString, PdfObject pdfObject) {
        int i;
        if (pdfObject instanceof PdfNumber) {
            String decodeStringToUnicode = decodeStringToUnicode(pdfString);
            if (Utilities.isSurrogatePair(decodeStringToUnicode, 0)) {
                i = Utilities.convertToUtf32(decodeStringToUnicode, 0);
            } else {
                i = decodeStringToUnicode.charAt(0);
            }
            this.map.put(((PdfNumber) pdfObject).intValue(), i);
        }
    }

    public int lookup(int i) {
        return this.map.get(i);
    }
}
