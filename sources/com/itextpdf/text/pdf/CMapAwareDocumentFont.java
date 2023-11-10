package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.fonts.cmaps.CMapByteCid;
import com.itextpdf.text.pdf.fonts.cmaps.CMapCache;
import com.itextpdf.text.pdf.fonts.cmaps.CMapCidUni;
import com.itextpdf.text.pdf.fonts.cmaps.CMapParserEx;
import com.itextpdf.text.pdf.fonts.cmaps.CMapSequence;
import com.itextpdf.text.pdf.fonts.cmaps.CMapToUnicode;
import com.itextpdf.text.pdf.fonts.cmaps.CidLocationFromByte;
import com.itextpdf.text.pdf.fonts.cmaps.IdentityToUnicode;
import java.io.IOException;
import java.util.Map;
import kotlin.UByte;

public class CMapAwareDocumentFont extends DocumentFont {
    private CMapByteCid byteCid;
    private CMapCidUni cidUni;
    private char[] cidbyte2uni;
    private PdfDictionary fontDic;
    private int spaceWidth;
    private CMapToUnicode toUnicodeCmap;
    private Map<Integer, Integer> uni2cid;

    public CMapAwareDocumentFont(PdfDictionary pdfDictionary) {
        super(pdfDictionary);
        this.fontDic = pdfDictionary;
        initFont();
    }

    public CMapAwareDocumentFont(PRIndirectReference pRIndirectReference) {
        super(pRIndirectReference);
        this.fontDic = (PdfDictionary) PdfReader.getPdfObjectRelease((PdfObject) pRIndirectReference);
        initFont();
    }

    private void initFont() {
        processToUnicode();
        try {
            processUni2Byte();
            int width = super.getWidth(32);
            this.spaceWidth = width;
            if (width == 0) {
                this.spaceWidth = computeAverageWidth();
            }
            if (this.cjkEncoding != null) {
                this.byteCid = CMapCache.getCachedCMapByteCid(this.cjkEncoding);
                this.cidUni = CMapCache.getCachedCMapCidUni(this.uniMap);
            }
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    private void processToUnicode() {
        PdfDictionary asDict;
        PdfString asString;
        CMapToUnicode GetMapFromOrdering;
        PdfObject pdfObjectRelease = PdfReader.getPdfObjectRelease(this.fontDic.get(PdfName.TOUNICODE));
        if (pdfObjectRelease instanceof PRStream) {
            try {
                CidLocationFromByte cidLocationFromByte = new CidLocationFromByte(PdfReader.getStreamBytes((PRStream) pdfObjectRelease));
                CMapToUnicode cMapToUnicode = new CMapToUnicode();
                this.toUnicodeCmap = cMapToUnicode;
                CMapParserEx.parseCid("", cMapToUnicode, cidLocationFromByte);
                this.uni2cid = this.toUnicodeCmap.createReverseMapping();
            } catch (IOException unused) {
                this.toUnicodeCmap = null;
                this.uni2cid = null;
            }
        } else if (this.isType0) {
            try {
                PdfName asName = this.fontDic.getAsName(PdfName.ENCODING);
                if (asName != null && PdfName.decodeName(asName.toString()).equals(BaseFont.IDENTITY_H) && (asDict = ((PdfDictionary) PdfReader.getPdfObjectRelease(((PdfArray) PdfReader.getPdfObjectRelease(this.fontDic.get(PdfName.DESCENDANTFONTS))).getPdfObject(0))).getAsDict(PdfName.CIDSYSTEMINFO)) != null && (asString = asDict.getAsString(PdfName.ORDERING)) != null && (GetMapFromOrdering = IdentityToUnicode.GetMapFromOrdering(asString.toUnicodeString())) != null) {
                    this.toUnicodeCmap = GetMapFromOrdering;
                    this.uni2cid = GetMapFromOrdering.createReverseMapping();
                }
            } catch (IOException unused2) {
                this.toUnicodeCmap = null;
                this.uni2cid = null;
            }
        }
    }

    private void processUni2Byte() throws IOException {
        IntHashtable byte2Uni = getByte2Uni();
        int[] orderedKeys = byte2Uni.toOrderedKeys();
        if (orderedKeys.length != 0) {
            this.cidbyte2uni = new char[256];
            for (int i : orderedKeys) {
                this.cidbyte2uni[i] = (char) byte2Uni.get(i);
            }
            CMapToUnicode cMapToUnicode = this.toUnicodeCmap;
            if (cMapToUnicode != null) {
                for (Map.Entry next : cMapToUnicode.createDirectMapping().entrySet()) {
                    if (((Integer) next.getKey()).intValue() < 256) {
                        this.cidbyte2uni[((Integer) next.getKey()).intValue()] = (char) ((Integer) next.getValue()).intValue();
                    }
                }
            }
            IntHashtable diffmap = getDiffmap();
            if (diffmap != null) {
                int[] orderedKeys2 = diffmap.toOrderedKeys();
                for (int i2 = 0; i2 < orderedKeys2.length; i2++) {
                    int i3 = diffmap.get(orderedKeys2[i2]);
                    if (i3 < 256) {
                        this.cidbyte2uni[i3] = (char) orderedKeys2[i2];
                    }
                }
            }
        }
    }

    private int computeAverageWidth() {
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < this.widths.length; i3++) {
            if (this.widths[i3] != 0) {
                i2 += this.widths[i3];
                i++;
            }
        }
        if (i != 0) {
            return i2 / i;
        }
        return 0;
    }

    public int getWidth(int i) {
        if (i != 32) {
            return super.getWidth(i);
        }
        int i2 = this.spaceWidth;
        return i2 != 0 ? i2 : this.defaultWidth;
    }

    private String decodeSingleCID(byte[] bArr, int i, int i2) {
        CMapToUnicode cMapToUnicode = this.toUnicodeCmap;
        if (cMapToUnicode != null) {
            int i3 = i + i2;
            if (i3 <= bArr.length) {
                String lookup = cMapToUnicode.lookup(bArr, i, i2);
                if (lookup != null) {
                    return lookup;
                }
                if (i2 != 1 || this.cidbyte2uni == null) {
                    return null;
                }
            } else {
                throw new ArrayIndexOutOfBoundsException(MessageLocalization.getComposedMessage("invalid.index.1", i3));
            }
        }
        if (i2 != 1) {
            throw new Error("Multi-byte glyphs not implemented yet");
        } else if (this.cidbyte2uni == null) {
            return "";
        } else {
            return new String(this.cidbyte2uni, bArr[i] & UByte.MAX_VALUE, 1);
        }
    }

    public String decode(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        if (this.toUnicodeCmap != null || this.byteCid == null) {
            int i3 = i;
            while (true) {
                int i4 = i + i2;
                if (i3 >= i4) {
                    break;
                }
                String decodeSingleCID = decodeSingleCID(bArr, i3, 1);
                if (decodeSingleCID == null && i3 < i4 - 1) {
                    decodeSingleCID = decodeSingleCID(bArr, i3, 2);
                    i3++;
                }
                if (decodeSingleCID != null) {
                    sb.append(decodeSingleCID);
                }
                i3++;
            }
        } else {
            String decodeSequence = this.byteCid.decodeSequence(new CMapSequence(bArr, i, i2));
            for (int i5 = 0; i5 < decodeSequence.length(); i5++) {
                int lookup = this.cidUni.lookup(decodeSequence.charAt(i5));
                if (lookup > 0) {
                    sb.append(Utilities.convertFromUtf32(lookup));
                }
            }
        }
        return sb.toString();
    }

    public String encode(byte[] bArr, int i, int i2) {
        return decode(bArr, i, i2);
    }
}
