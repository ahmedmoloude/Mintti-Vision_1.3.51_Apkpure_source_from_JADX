package com.itextpdf.text.pdf.fonts.cmaps;

import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;
import java.util.HashMap;

public class CMapCidByte extends AbstractCMap {
    private final byte[] EMPTY = new byte[0];
    private HashMap<Integer, byte[]> map = new HashMap<>();

    /* access modifiers changed from: package-private */
    public void addChar(PdfString pdfString, PdfObject pdfObject) {
        if (pdfObject instanceof PdfNumber) {
            this.map.put(Integer.valueOf(((PdfNumber) pdfObject).intValue()), decodeStringToByte(pdfString));
        }
    }

    public byte[] lookup(int i) {
        byte[] bArr = this.map.get(Integer.valueOf(i));
        return bArr == null ? this.EMPTY : bArr;
    }
}
