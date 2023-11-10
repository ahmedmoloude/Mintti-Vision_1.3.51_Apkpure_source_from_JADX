package com.itextpdf.text.pdf.fonts.cmaps;

import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfEncodings;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;
import kotlin.UByte;

public abstract class AbstractCMap {
    private String cmapName;
    private String ordering;
    private String registry;
    private int supplement;

    /* access modifiers changed from: package-private */
    public abstract void addChar(PdfString pdfString, PdfObject pdfObject);

    public String getName() {
        return this.cmapName;
    }

    /* access modifiers changed from: package-private */
    public void setName(String str) {
        this.cmapName = str;
    }

    public String getOrdering() {
        return this.ordering;
    }

    /* access modifiers changed from: package-private */
    public void setOrdering(String str) {
        this.ordering = str;
    }

    public String getRegistry() {
        return this.registry;
    }

    /* access modifiers changed from: package-private */
    public void setRegistry(String str) {
        this.registry = str;
    }

    public int getSupplement() {
        return this.supplement;
    }

    /* access modifiers changed from: package-private */
    public void setSupplement(int i) {
        this.supplement = i;
    }

    /* access modifiers changed from: package-private */
    public void addRange(PdfString pdfString, PdfString pdfString2, PdfObject pdfObject) {
        byte[] decodeStringToByte = decodeStringToByte(pdfString);
        byte[] decodeStringToByte2 = decodeStringToByte(pdfString2);
        if (decodeStringToByte.length != decodeStringToByte2.length || decodeStringToByte.length == 0) {
            throw new IllegalArgumentException("Invalid map.");
        }
        byte[] bArr = null;
        boolean z = pdfObject instanceof PdfString;
        if (z) {
            bArr = decodeStringToByte((PdfString) pdfObject);
        }
        int byteArrayToInt = byteArrayToInt(decodeStringToByte);
        int byteArrayToInt2 = byteArrayToInt(decodeStringToByte2);
        for (int i = byteArrayToInt; i <= byteArrayToInt2; i++) {
            intToByteArray(i, decodeStringToByte);
            PdfString pdfString3 = new PdfString(decodeStringToByte);
            pdfString3.setHexWriting(true);
            if (pdfObject instanceof PdfArray) {
                addChar(pdfString3, ((PdfArray) pdfObject).getPdfObject(i - byteArrayToInt));
            } else if (pdfObject instanceof PdfNumber) {
                addChar(pdfString3, new PdfNumber((((PdfNumber) pdfObject).intValue() + i) - byteArrayToInt));
            } else if (z) {
                PdfString pdfString4 = new PdfString(bArr);
                pdfString4.setHexWriting(true);
                int length = bArr.length - 1;
                bArr[length] = (byte) (bArr[length] + 1);
                addChar(pdfString3, pdfString4);
            }
        }
    }

    private static void intToByteArray(int i, byte[] bArr) {
        for (int length = bArr.length - 1; length >= 0; length--) {
            bArr[length] = (byte) i;
            i >>>= 8;
        }
    }

    private static int byteArrayToInt(byte[] bArr) {
        byte b = 0;
        for (byte b2 : bArr) {
            b = (b << 8) | (b2 & UByte.MAX_VALUE);
        }
        return b;
    }

    public static byte[] decodeStringToByte(PdfString pdfString) {
        byte[] bytes = pdfString.getBytes();
        byte[] bArr = new byte[bytes.length];
        System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        return bArr;
    }

    public String decodeStringToUnicode(PdfString pdfString) {
        if (pdfString.isHexWriting()) {
            return PdfEncodings.convertToString(pdfString.getBytes(), "UnicodeBigUnmarked");
        }
        return pdfString.toUnicodeString();
    }
}
