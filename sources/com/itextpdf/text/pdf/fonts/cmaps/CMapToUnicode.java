package com.itextpdf.text.pdf.fonts.cmaps;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.xml.xmp.XmpWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import kotlin.UByte;

public class CMapToUnicode extends AbstractCMap {
    private Map<Integer, String> doubleByteMappings = new HashMap();
    private Map<Integer, String> singleByteMappings = new HashMap();

    public boolean hasOneByteMappings() {
        return !this.singleByteMappings.isEmpty();
    }

    public boolean hasTwoByteMappings() {
        return !this.doubleByteMappings.isEmpty();
    }

    public String lookup(byte[] bArr, int i, int i2) {
        if (i2 == 1) {
            return this.singleByteMappings.get(Integer.valueOf(bArr[i] & UByte.MAX_VALUE));
        } else if (i2 != 2) {
            return null;
        } else {
            return this.doubleByteMappings.get(Integer.valueOf(((bArr[i] & UByte.MAX_VALUE) << 8) + (bArr[i + 1] & UByte.MAX_VALUE)));
        }
    }

    public Map<Integer, Integer> createReverseMapping() throws IOException {
        HashMap hashMap = new HashMap();
        for (Map.Entry next : this.singleByteMappings.entrySet()) {
            hashMap.put(Integer.valueOf(convertToInt((String) next.getValue())), next.getKey());
        }
        for (Map.Entry next2 : this.doubleByteMappings.entrySet()) {
            hashMap.put(Integer.valueOf(convertToInt((String) next2.getValue())), next2.getKey());
        }
        return hashMap;
    }

    public Map<Integer, Integer> createDirectMapping() throws IOException {
        HashMap hashMap = new HashMap();
        for (Map.Entry next : this.singleByteMappings.entrySet()) {
            hashMap.put(next.getKey(), Integer.valueOf(convertToInt((String) next.getValue())));
        }
        for (Map.Entry next2 : this.doubleByteMappings.entrySet()) {
            hashMap.put(next2.getKey(), Integer.valueOf(convertToInt((String) next2.getValue())));
        }
        return hashMap;
    }

    private int convertToInt(String str) throws IOException {
        byte[] bytes = str.getBytes(XmpWriter.UTF16BE);
        int i = 0;
        for (int i2 = 0; i2 < bytes.length - 1; i2++) {
            i = (i + (bytes[i2] & UByte.MAX_VALUE)) << 8;
        }
        return i + (bytes[bytes.length - 1] & UByte.MAX_VALUE);
    }

    /* access modifiers changed from: package-private */
    public void addChar(int i, String str) {
        this.doubleByteMappings.put(Integer.valueOf(i), str);
    }

    /* access modifiers changed from: package-private */
    public void addChar(PdfString pdfString, PdfObject pdfObject) {
        try {
            byte[] bytes = pdfString.getBytes();
            String createStringFromBytes = createStringFromBytes(pdfObject.getBytes());
            if (bytes.length == 1) {
                this.singleByteMappings.put(Integer.valueOf(bytes[0] & UByte.MAX_VALUE), createStringFromBytes);
            } else if (bytes.length == 2) {
                this.doubleByteMappings.put(Integer.valueOf((bytes[1] & UByte.MAX_VALUE) | ((bytes[0] & UByte.MAX_VALUE) << 8)), createStringFromBytes);
            } else {
                throw new IOException(MessageLocalization.getComposedMessage("mapping.code.should.be.1.or.two.bytes.and.not.1", bytes.length));
            }
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    private String createStringFromBytes(byte[] bArr) throws IOException {
        if (bArr.length == 1) {
            return new String(bArr);
        }
        return new String(bArr, XmpWriter.UTF16BE);
    }

    public static CMapToUnicode getIdentity() {
        CMapToUnicode cMapToUnicode = new CMapToUnicode();
        for (int i = 0; i < 65537; i++) {
            cMapToUnicode.addChar(i, Utilities.convertFromUtf32(i));
        }
        return cMapToUnicode;
    }
}
