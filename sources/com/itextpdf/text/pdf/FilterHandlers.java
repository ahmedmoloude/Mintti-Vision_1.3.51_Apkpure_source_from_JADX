package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.exceptions.UnsupportedPdfException;
import com.itextpdf.text.pdf.codec.TIFFFaxDecoder;
import com.itextpdf.text.pdf.codec.TIFFFaxDecompressor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import kotlin.UByte;

public final class FilterHandlers {
    private static final Map<PdfName, FilterHandler> defaults;

    public interface FilterHandler {
        byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException;
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(PdfName.FLATEDECODE, new Filter_FLATEDECODE());
        hashMap.put(PdfName.f585FL, new Filter_FLATEDECODE());
        hashMap.put(PdfName.ASCIIHEXDECODE, new Filter_ASCIIHEXDECODE());
        hashMap.put(PdfName.AHX, new Filter_ASCIIHEXDECODE());
        hashMap.put(PdfName.ASCII85DECODE, new Filter_ASCII85DECODE());
        hashMap.put(PdfName.A85, new Filter_ASCII85DECODE());
        hashMap.put(PdfName.LZWDECODE, new Filter_LZWDECODE());
        hashMap.put(PdfName.CCITTFAXDECODE, new Filter_CCITTFAXDECODE());
        hashMap.put(PdfName.CRYPT, new Filter_DoNothing());
        hashMap.put(PdfName.RUNLENGTHDECODE, new Filter_RUNLENGTHDECODE());
        defaults = Collections.unmodifiableMap(hashMap);
    }

    public static Map<PdfName, FilterHandler> getDefaultFilterHandlers() {
        return defaults;
    }

    private static class Filter_FLATEDECODE implements FilterHandler {
        private Filter_FLATEDECODE() {
        }

        public byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
            return PdfReader.decodePredictor(PdfReader.FlateDecode(bArr), pdfObject);
        }
    }

    private static class Filter_ASCIIHEXDECODE implements FilterHandler {
        private Filter_ASCIIHEXDECODE() {
        }

        public byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
            return PdfReader.ASCIIHexDecode(bArr);
        }
    }

    private static class Filter_ASCII85DECODE implements FilterHandler {
        private Filter_ASCII85DECODE() {
        }

        public byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
            return PdfReader.ASCII85Decode(bArr);
        }
    }

    private static class Filter_LZWDECODE implements FilterHandler {
        private Filter_LZWDECODE() {
        }

        public byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
            return PdfReader.decodePredictor(PdfReader.LZWDecode(bArr), pdfObject);
        }
    }

    private static class Filter_CCITTFAXDECODE implements FilterHandler {
        private Filter_CCITTFAXDECODE() {
        }

        public byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
            int i;
            boolean z;
            boolean z2;
            PdfNumber pdfNumber = (PdfNumber) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.WIDTH));
            PdfNumber pdfNumber2 = (PdfNumber) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.HEIGHT));
            if (pdfNumber == null || pdfNumber2 == null) {
                throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("filter.ccittfaxdecode.is.only.supported.for.images", new Object[0]));
            }
            int intValue = pdfNumber.intValue();
            int intValue2 = pdfNumber2.intValue();
            PdfDictionary pdfDictionary2 = pdfObject instanceof PdfDictionary ? (PdfDictionary) pdfObject : null;
            if (pdfDictionary2 != null) {
                PdfNumber asNumber = pdfDictionary2.getAsNumber(PdfName.f605K);
                i = asNumber != null ? asNumber.intValue() : 0;
                PdfBoolean asBoolean = pdfDictionary2.getAsBoolean(PdfName.BLACKIS1);
                boolean booleanValue = asBoolean != null ? asBoolean.booleanValue() : false;
                PdfBoolean asBoolean2 = pdfDictionary2.getAsBoolean(PdfName.ENCODEDBYTEALIGN);
                if (asBoolean2 != null) {
                    z = asBoolean2.booleanValue();
                    z2 = booleanValue;
                } else {
                    z2 = booleanValue;
                    z = false;
                }
            } else {
                z = false;
                i = 0;
                z2 = false;
            }
            int i2 = ((intValue + 7) / 8) * intValue2;
            byte[] bArr2 = new byte[i2];
            TIFFFaxDecompressor tIFFFaxDecompressor = new TIFFFaxDecompressor();
            if (i == 0 || i > 0) {
                int i3 = (z ? 4 : 0) | (i > 0 ? 1 : 0);
                tIFFFaxDecompressor.SetOptions(1, 3, i3, 0);
                tIFFFaxDecompressor.decodeRaw(bArr2, bArr, intValue, intValue2);
                if (tIFFFaxDecompressor.fails > 0) {
                    byte[] bArr3 = new byte[i2];
                    int i4 = tIFFFaxDecompressor.fails;
                    tIFFFaxDecompressor.SetOptions(1, 2, i3, 0);
                    tIFFFaxDecompressor.decodeRaw(bArr3, bArr, intValue, intValue2);
                    if (tIFFFaxDecompressor.fails < i4) {
                        bArr2 = bArr3;
                    }
                }
            } else {
                new TIFFFaxDecoder(1, intValue, intValue2).decodeT6(bArr2, bArr, 0, intValue2, 0);
            }
            if (!z2) {
                int length = bArr2.length;
                for (int i5 = 0; i5 < length; i5++) {
                    bArr2[i5] = (byte) (bArr2[i5] ^ UByte.MAX_VALUE);
                }
            }
            return bArr2;
        }
    }

    private static class Filter_DoNothing implements FilterHandler {
        public byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
            return bArr;
        }

        private Filter_DoNothing() {
        }
    }

    private static class Filter_RUNLENGTHDECODE implements FilterHandler {
        private Filter_RUNLENGTHDECODE() {
        }

        public byte[] decode(byte[] bArr, PdfName pdfName, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
            byte b;
            int i;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i2 = 0;
            while (i2 < bArr.length && (b = bArr[i2]) != Byte.MIN_VALUE) {
                if (b < 0 || b > Byte.MAX_VALUE) {
                    i = i2 + 1;
                    for (int i3 = 0; i3 < 1 - b; i3++) {
                        byteArrayOutputStream.write(bArr[i]);
                    }
                } else {
                    int i4 = b + 1;
                    byteArrayOutputStream.write(bArr, i2, i4);
                    i = i2 + i4;
                }
                i2 = i + 1;
            }
            return byteArrayOutputStream.toByteArray();
        }
    }
}
