package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.p018io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.fonts.cmaps.CMapParserEx;
import com.itextpdf.text.pdf.fonts.cmaps.CMapToUnicode;
import com.itextpdf.text.pdf.fonts.cmaps.CidLocationFromByte;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DocumentFont extends BaseFont {
    private static final int[] stdEnc = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 33, 34, 35, 36, 37, 38, 8217, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 8216, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 161, 162, 163, 8260, 165, 402, 167, 164, 39, 8220, 171, 8249, 8250, 64257, 64258, 0, 8211, 8224, 8225, 183, 0, 182, 8226, 8218, 8222, 8221, 187, 8230, 8240, 0, 191, 0, 96, 180, 710, 732, 175, 728, 729, 168, 0, 730, 184, 0, 733, 731, 711, 8212, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 198, 0, 170, 0, 0, 0, 0, 321, 216, 338, 186, 0, 0, 0, 0, 0, 230, 0, 0, 0, 305, 0, 0, 322, 248, 339, 223, 0, 0, 0, 0};
    private float ascender;
    private IntHashtable byte2uni;
    private float capHeight;
    protected String cjkEncoding;
    private BaseFont cjkMirror;
    protected int defaultWidth;
    private float descender;
    private IntHashtable diffmap;
    private PdfDictionary font;
    private String fontName;
    private float fontWeight;
    private IntHashtable hMetrics;
    protected boolean isType0;
    private float italicAngle;
    private float llx;
    private float lly;
    private HashMap<Integer, int[]> metrics;
    private PRIndirectReference refFont;
    private IntHashtable uni2byte;
    protected String uniMap;
    private float urx;
    private float ury;

    public int[] getCharBBox(int i) {
        return null;
    }

    public PdfStream getFullFontStream() {
        return null;
    }

    public int getKerning(int i, int i2) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int[] getRawCharBBox(int i, String str) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getRawWidth(int i, String str) {
        return 0;
    }

    public boolean hasKernPairs() {
        return false;
    }

    public boolean setKerning(int i, int i2, int i3) {
        return false;
    }

    public void setPostscriptFontName(String str) {
    }

    /* access modifiers changed from: package-private */
    public void writeFont(PdfWriter pdfWriter, PdfIndirectReference pdfIndirectReference, Object[] objArr) throws DocumentException, IOException {
    }

    DocumentFont(PdfDictionary pdfDictionary) {
        this.metrics = new HashMap<>();
        this.uni2byte = new IntHashtable();
        this.byte2uni = new IntHashtable();
        this.ascender = 800.0f;
        this.capHeight = 700.0f;
        this.descender = -200.0f;
        this.italicAngle = 0.0f;
        this.fontWeight = 0.0f;
        this.llx = -50.0f;
        this.lly = -200.0f;
        this.urx = 100.0f;
        this.ury = 900.0f;
        this.isType0 = false;
        this.defaultWidth = 1000;
        this.refFont = null;
        this.font = pdfDictionary;
        init();
    }

    DocumentFont(PRIndirectReference pRIndirectReference) {
        this.metrics = new HashMap<>();
        this.uni2byte = new IntHashtable();
        this.byte2uni = new IntHashtable();
        this.ascender = 800.0f;
        this.capHeight = 700.0f;
        this.descender = -200.0f;
        this.italicAngle = 0.0f;
        this.fontWeight = 0.0f;
        this.llx = -50.0f;
        this.lly = -200.0f;
        this.urx = 100.0f;
        this.ury = 900.0f;
        this.isType0 = false;
        this.defaultWidth = 1000;
        this.refFont = pRIndirectReference;
        this.font = (PdfDictionary) PdfReader.getPdfObject((PdfObject) pRIndirectReference);
        init();
    }

    DocumentFont(PRIndirectReference pRIndirectReference, PdfDictionary pdfDictionary) {
        this.metrics = new HashMap<>();
        this.uni2byte = new IntHashtable();
        this.byte2uni = new IntHashtable();
        this.ascender = 800.0f;
        this.capHeight = 700.0f;
        this.descender = -200.0f;
        this.italicAngle = 0.0f;
        this.fontWeight = 0.0f;
        this.llx = -50.0f;
        this.lly = -200.0f;
        this.urx = 100.0f;
        this.ury = 900.0f;
        this.isType0 = false;
        this.defaultWidth = 1000;
        this.refFont = pRIndirectReference;
        PdfDictionary pdfDictionary2 = (PdfDictionary) PdfReader.getPdfObject((PdfObject) pRIndirectReference);
        this.font = pdfDictionary2;
        if (pdfDictionary2.get(PdfName.ENCODING) == null && pdfDictionary != null) {
            for (PdfName pdfName : pdfDictionary.getKeys()) {
                this.font.put(PdfName.ENCODING, pdfDictionary.get(pdfName));
            }
        }
        init();
    }

    public PdfDictionary getFontDictionary() {
        return this.font;
    }

    private void init() {
        this.encoding = "";
        this.fontSpecific = false;
        this.fontType = 4;
        PdfName asName = this.font.getAsName(PdfName.BASEFONT);
        this.fontName = asName != null ? PdfName.decodeName(asName.toString()) : "Unspecified Font Name";
        PdfName asName2 = this.font.getAsName(PdfName.SUBTYPE);
        if (PdfName.TYPE1.equals(asName2) || PdfName.TRUETYPE.equals(asName2)) {
            doType1TT();
        } else if (PdfName.TYPE3.equals(asName2)) {
            fillEncoding((PdfName) null);
            fillDiffMap(this.font.getAsDict(PdfName.ENCODING), (CMapToUnicode) null);
            fillWidths();
        } else {
            PdfName asName3 = this.font.getAsName(PdfName.ENCODING);
            if (asName3 != null) {
                String decodeName = PdfName.decodeName(asName3.toString());
                String GetCompatibleFont = CJKFont.GetCompatibleFont(decodeName);
                if (GetCompatibleFont != null) {
                    try {
                        BaseFont createFont = BaseFont.createFont(GetCompatibleFont, decodeName, false);
                        this.cjkMirror = createFont;
                        this.cjkEncoding = decodeName;
                        this.uniMap = ((CJKFont) createFont).getUniMap();
                    } catch (Exception e) {
                        throw new ExceptionConverter(e);
                    }
                }
                if (PdfName.TYPE0.equals(asName2)) {
                    this.isType0 = true;
                    if (decodeName.equals(BaseFont.IDENTITY_H) || this.cjkMirror == null) {
                        processType0(this.font);
                        return;
                    }
                    PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObjectRelease(((PdfArray) PdfReader.getPdfObjectRelease(this.font.get(PdfName.DESCENDANTFONTS))).getPdfObject(0));
                    PdfNumber pdfNumber = (PdfNumber) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.f577DW));
                    if (pdfNumber != null) {
                        this.defaultWidth = pdfNumber.intValue();
                    }
                    this.hMetrics = readWidths((PdfArray) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.f668W)));
                    fillFontDesc((PdfDictionary) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.FONTDESCRIPTOR)));
                }
            }
        }
    }

    private void processType0(PdfDictionary pdfDictionary) {
        try {
            PdfObject pdfObjectRelease = PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.TOUNICODE));
            PdfDictionary pdfDictionary2 = (PdfDictionary) PdfReader.getPdfObjectRelease(((PdfArray) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.DESCENDANTFONTS))).getPdfObject(0));
            PdfNumber pdfNumber = (PdfNumber) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.f577DW));
            int i = 1000;
            if (pdfNumber != null) {
                i = pdfNumber.intValue();
            }
            IntHashtable readWidths = readWidths((PdfArray) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.f668W)));
            fillFontDesc((PdfDictionary) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.FONTDESCRIPTOR)));
            if (pdfObjectRelease instanceof PRStream) {
                fillMetrics(PdfReader.getStreamBytes((PRStream) pdfObjectRelease), readWidths, i);
            } else if (new PdfName(BaseFont.IDENTITY_H).equals(pdfObjectRelease)) {
                fillMetricsIdentity(readWidths, i);
            }
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    private IntHashtable readWidths(PdfArray pdfArray) {
        IntHashtable intHashtable = new IntHashtable();
        if (pdfArray == null) {
            return intHashtable;
        }
        int i = 0;
        while (i < pdfArray.size()) {
            int intValue = ((PdfNumber) PdfReader.getPdfObjectRelease(pdfArray.getPdfObject(i))).intValue();
            int i2 = i + 1;
            PdfObject pdfObjectRelease = PdfReader.getPdfObjectRelease(pdfArray.getPdfObject(i2));
            if (pdfObjectRelease.isArray()) {
                PdfArray pdfArray2 = (PdfArray) pdfObjectRelease;
                int i3 = 0;
                while (i3 < pdfArray2.size()) {
                    intHashtable.put(intValue, ((PdfNumber) PdfReader.getPdfObjectRelease(pdfArray2.getPdfObject(i3))).intValue());
                    i3++;
                    intValue++;
                }
            } else {
                int intValue2 = ((PdfNumber) pdfObjectRelease).intValue();
                i2++;
                int intValue3 = ((PdfNumber) PdfReader.getPdfObjectRelease(pdfArray.getPdfObject(i2))).intValue();
                while (intValue <= intValue2) {
                    intHashtable.put(intValue, intValue3);
                    intValue++;
                }
            }
            i = i2 + 1;
        }
        return intHashtable;
    }

    private String decodeString(PdfString pdfString) {
        if (pdfString.isHexWriting()) {
            return PdfEncodings.convertToString(pdfString.getBytes(), "UnicodeBigUnmarked");
        }
        return pdfString.toUnicodeString();
    }

    private void fillMetricsIdentity(IntHashtable intHashtable, int i) {
        for (int i2 = 0; i2 < 65536; i2++) {
            this.metrics.put(Integer.valueOf(i2), new int[]{i2, intHashtable.containsKey(i2) ? intHashtable.get(i2) : i});
        }
    }

    private void fillMetrics(byte[] bArr, IntHashtable intHashtable, int i) {
        IntHashtable intHashtable2 = intHashtable;
        try {
            PdfContentParser pdfContentParser = new PdfContentParser(new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(bArr))));
            int i2 = 50;
            boolean z = true;
            int i3 = 0;
            while (true) {
                if (z || i3 > 0) {
                    try {
                        PdfObject readPRObject = pdfContentParser.readPRObject();
                        if (readPRObject != null) {
                            if (readPRObject.type() == 200) {
                                if (readPRObject.toString().equals("begin")) {
                                    i3++;
                                    z = false;
                                } else if (readPRObject.toString().equals("end")) {
                                    i3--;
                                } else if (readPRObject.toString().equals("beginbfchar")) {
                                    while (true) {
                                        PdfObject readPRObject2 = pdfContentParser.readPRObject();
                                        if (readPRObject2.toString().equals("endbfchar")) {
                                            break;
                                        }
                                        String decodeString = decodeString((PdfString) readPRObject2);
                                        String decodeString2 = decodeString((PdfString) pdfContentParser.readPRObject());
                                        if (decodeString2.length() == 1) {
                                            int charAt = decodeString.charAt(0);
                                            this.metrics.put(Integer.valueOf(decodeString2.charAt(decodeString2.length() - 1)), new int[]{charAt, intHashtable2.containsKey(charAt) ? intHashtable2.get(charAt) : i});
                                        }
                                    }
                                } else if (readPRObject.toString().equals("beginbfrange")) {
                                    while (true) {
                                        PdfObject readPRObject3 = pdfContentParser.readPRObject();
                                        if (readPRObject3.toString().equals("endbfrange")) {
                                            break;
                                        }
                                        String decodeString3 = decodeString((PdfString) readPRObject3);
                                        String decodeString4 = decodeString((PdfString) pdfContentParser.readPRObject());
                                        int charAt2 = decodeString3.charAt(0);
                                        char charAt3 = decodeString4.charAt(0);
                                        PdfObject readPRObject4 = pdfContentParser.readPRObject();
                                        if (readPRObject4.isString()) {
                                            String decodeString5 = decodeString((PdfString) readPRObject4);
                                            if (decodeString5.length() == 1) {
                                                int charAt4 = decodeString5.charAt(decodeString5.length() - 1);
                                                while (charAt2 <= charAt3) {
                                                    this.metrics.put(Integer.valueOf(charAt4), new int[]{charAt2, intHashtable2.containsKey(charAt2) ? intHashtable2.get(charAt2) : i});
                                                    charAt2++;
                                                    charAt4++;
                                                }
                                            }
                                        } else {
                                            PdfArray pdfArray = (PdfArray) readPRObject4;
                                            int i4 = 0;
                                            while (i4 < pdfArray.size()) {
                                                String decodeString6 = decodeString(pdfArray.getAsString(i4));
                                                if (decodeString6.length() == 1) {
                                                    this.metrics.put(Integer.valueOf(decodeString6.charAt(decodeString6.length() - 1)), new int[]{charAt2, intHashtable2.containsKey(charAt2) ? intHashtable2.get(charAt2) : i});
                                                }
                                                i4++;
                                                charAt2++;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            return;
                        }
                    } catch (Exception unused) {
                        i2--;
                        if (i2 < 0) {
                            return;
                        }
                    }
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    private void doType1TT() {
        PdfObject pdfObject = PdfReader.getPdfObject(this.font.get(PdfName.ENCODING));
        if (pdfObject == null) {
            PdfName asName = this.font.getAsName(PdfName.BASEFONT);
            if (!BuiltinFonts14.containsKey(this.fontName) || (!PdfName.SYMBOL.equals(asName) && !PdfName.ZAPFDINGBATS.equals(asName))) {
                fillEncoding((PdfName) null);
            } else {
                fillEncoding(asName);
            }
            try {
                CMapToUnicode processToUnicode = processToUnicode();
                if (processToUnicode != null) {
                    for (Map.Entry next : processToUnicode.createReverseMapping().entrySet()) {
                        this.uni2byte.put(((Integer) next.getKey()).intValue(), ((Integer) next.getValue()).intValue());
                        this.byte2uni.put(((Integer) next.getValue()).intValue(), ((Integer) next.getKey()).intValue());
                    }
                }
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
        } else if (pdfObject.isName()) {
            fillEncoding((PdfName) pdfObject);
        } else if (pdfObject.isDictionary()) {
            PdfDictionary pdfDictionary = (PdfDictionary) pdfObject;
            PdfObject pdfObject2 = PdfReader.getPdfObject(pdfDictionary.get(PdfName.BASEENCODING));
            if (pdfObject2 == null) {
                fillEncoding((PdfName) null);
            } else {
                fillEncoding((PdfName) pdfObject2);
            }
            fillDiffMap(pdfDictionary, (CMapToUnicode) null);
        }
        if (BuiltinFonts14.containsKey(this.fontName)) {
            try {
                BaseFont createFont = BaseFont.createFont(this.fontName, "Cp1252", false);
                int[] orderedKeys = this.uni2byte.toOrderedKeys();
                for (int i = 0; i < orderedKeys.length; i++) {
                    int i2 = this.uni2byte.get(orderedKeys[i]);
                    this.widths[i2] = createFont.getRawWidth(i2, GlyphList.unicodeToName(orderedKeys[i]));
                }
                IntHashtable intHashtable = this.diffmap;
                if (intHashtable != null) {
                    int[] orderedKeys2 = intHashtable.toOrderedKeys();
                    for (int i3 = 0; i3 < orderedKeys2.length; i3++) {
                        int i4 = this.diffmap.get(orderedKeys2[i3]);
                        this.widths[i4] = createFont.getRawWidth(i4, GlyphList.unicodeToName(orderedKeys2[i3]));
                    }
                    this.diffmap = null;
                }
                this.ascender = createFont.getFontDescriptor(1, 1000.0f);
                this.capHeight = createFont.getFontDescriptor(2, 1000.0f);
                this.descender = createFont.getFontDescriptor(3, 1000.0f);
                this.italicAngle = createFont.getFontDescriptor(4, 1000.0f);
                this.fontWeight = createFont.getFontDescriptor(23, 1000.0f);
                this.llx = createFont.getFontDescriptor(5, 1000.0f);
                this.lly = createFont.getFontDescriptor(6, 1000.0f);
                this.urx = createFont.getFontDescriptor(7, 1000.0f);
                this.ury = createFont.getFontDescriptor(8, 1000.0f);
            } catch (Exception e2) {
                throw new ExceptionConverter(e2);
            }
        }
        fillWidths();
        fillFontDesc(this.font.getAsDict(PdfName.FONTDESCRIPTOR));
    }

    private void fillWidths() {
        PdfArray asArray = this.font.getAsArray(PdfName.WIDTHS);
        PdfNumber asNumber = this.font.getAsNumber(PdfName.FIRSTCHAR);
        PdfNumber asNumber2 = this.font.getAsNumber(PdfName.LASTCHAR);
        if (asNumber != null && asNumber2 != null && asArray != null) {
            int intValue = asNumber.intValue();
            int size = asArray.size() + intValue;
            if (this.widths.length < size) {
                int[] iArr = new int[size];
                System.arraycopy(this.widths, 0, iArr, 0, intValue);
                this.widths = iArr;
            }
            for (int i = 0; i < asArray.size(); i++) {
                this.widths[intValue + i] = asArray.getAsNumber(i).intValue();
            }
        }
    }

    private void fillDiffMap(PdfDictionary pdfDictionary, CMapToUnicode cMapToUnicode) {
        PdfArray asArray = pdfDictionary.getAsArray(PdfName.DIFFERENCES);
        if (asArray != null) {
            this.diffmap = new IntHashtable();
            int i = 0;
            for (int i2 = 0; i2 < asArray.size(); i2++) {
                PdfObject pdfObject = asArray.getPdfObject(i2);
                if (pdfObject.isNumber()) {
                    i = ((PdfNumber) pdfObject).intValue();
                } else {
                    int[] nameToUnicode = GlyphList.nameToUnicode(PdfName.decodeName(((PdfName) pdfObject).toString()));
                    if (nameToUnicode == null || nameToUnicode.length <= 0) {
                        if (cMapToUnicode == null && (cMapToUnicode = processToUnicode()) == null) {
                            cMapToUnicode = new CMapToUnicode();
                        }
                        String lookup = cMapToUnicode.lookup(new byte[]{(byte) i}, 0, 1);
                        if (lookup != null && lookup.length() == 1) {
                            this.uni2byte.put(lookup.charAt(0), i);
                            this.byte2uni.put(i, lookup.charAt(0));
                            this.diffmap.put(lookup.charAt(0), i);
                        }
                    } else {
                        this.uni2byte.put(nameToUnicode[0], i);
                        this.byte2uni.put(i, nameToUnicode[0]);
                        this.diffmap.put(nameToUnicode[0], i);
                    }
                    i++;
                }
            }
        }
    }

    private CMapToUnicode processToUnicode() {
        PdfObject pdfObjectRelease = PdfReader.getPdfObjectRelease(this.font.get(PdfName.TOUNICODE));
        if (!(pdfObjectRelease instanceof PRStream)) {
            return null;
        }
        try {
            CidLocationFromByte cidLocationFromByte = new CidLocationFromByte(PdfReader.getStreamBytes((PRStream) pdfObjectRelease));
            CMapToUnicode cMapToUnicode = new CMapToUnicode();
            CMapParserEx.parseCid("", cMapToUnicode, cidLocationFromByte);
            return cMapToUnicode;
        } catch (Exception unused) {
            return null;
        }
    }

    private void fillFontDesc(PdfDictionary pdfDictionary) {
        if (pdfDictionary != null) {
            PdfNumber asNumber = pdfDictionary.getAsNumber(PdfName.ASCENT);
            if (asNumber != null) {
                this.ascender = asNumber.floatValue();
            }
            PdfNumber asNumber2 = pdfDictionary.getAsNumber(PdfName.CAPHEIGHT);
            if (asNumber2 != null) {
                this.capHeight = asNumber2.floatValue();
            }
            PdfNumber asNumber3 = pdfDictionary.getAsNumber(PdfName.DESCENT);
            if (asNumber3 != null) {
                this.descender = asNumber3.floatValue();
            }
            PdfNumber asNumber4 = pdfDictionary.getAsNumber(PdfName.ITALICANGLE);
            if (asNumber4 != null) {
                this.italicAngle = asNumber4.floatValue();
            }
            PdfNumber asNumber5 = pdfDictionary.getAsNumber(PdfName.FONTWEIGHT);
            if (asNumber5 != null) {
                this.fontWeight = asNumber5.floatValue();
            }
            PdfArray asArray = pdfDictionary.getAsArray(PdfName.FONTBBOX);
            if (asArray != null) {
                this.llx = asArray.getAsNumber(0).floatValue();
                this.lly = asArray.getAsNumber(1).floatValue();
                this.urx = asArray.getAsNumber(2).floatValue();
                float floatValue = asArray.getAsNumber(3).floatValue();
                this.ury = floatValue;
                float f = this.llx;
                float f2 = this.urx;
                if (f > f2) {
                    this.llx = f2;
                    this.urx = f;
                }
                float f3 = this.lly;
                if (f3 > floatValue) {
                    this.lly = floatValue;
                    this.ury = f3;
                }
            }
            float max = Math.max(this.ury, this.ascender);
            float min = Math.min(this.lly, this.descender);
            float f4 = max * 1000.0f;
            float f5 = max - min;
            this.ascender = f4 / f5;
            this.descender = (min * 1000.0f) / f5;
        }
    }

    private void fillEncoding(PdfName pdfName) {
        String str;
        int i = 0;
        if (pdfName == null && isSymbolic()) {
            while (i < 256) {
                this.uni2byte.put(i, i);
                this.byte2uni.put(i, i);
                i++;
            }
        } else if (PdfName.MAC_ROMAN_ENCODING.equals(pdfName) || PdfName.WIN_ANSI_ENCODING.equals(pdfName) || PdfName.SYMBOL.equals(pdfName) || PdfName.ZAPFDINGBATS.equals(pdfName)) {
            byte[] bArr = new byte[256];
            for (int i2 = 0; i2 < 256; i2++) {
                bArr[i2] = (byte) i2;
            }
            if (PdfName.MAC_ROMAN_ENCODING.equals(pdfName)) {
                str = BaseFont.MACROMAN;
            } else if (PdfName.SYMBOL.equals(pdfName)) {
                str = "Symbol";
            } else {
                str = PdfName.ZAPFDINGBATS.equals(pdfName) ? "ZapfDingbats" : "Cp1252";
            }
            char[] charArray = PdfEncodings.convertToString(bArr, str).toCharArray();
            while (i < 256) {
                this.uni2byte.put(charArray[i], i);
                this.byte2uni.put(i, charArray[i]);
                i++;
            }
            this.encoding = str;
        } else {
            while (i < 256) {
                IntHashtable intHashtable = this.uni2byte;
                int[] iArr = stdEnc;
                intHashtable.put(iArr[i], i);
                this.byte2uni.put(i, iArr[i]);
                i++;
            }
        }
    }

    public String[][] getFamilyFontName() {
        return getFullFontName();
    }

    public float getFontDescriptor(int i, float f) {
        float f2;
        BaseFont baseFont = this.cjkMirror;
        if (baseFont != null) {
            return baseFont.getFontDescriptor(i, f);
        }
        if (i == 12) {
            f2 = this.urx - this.llx;
        } else if (i != 23) {
            switch (i) {
                case 1:
                case 9:
                    f2 = this.ascender;
                    break;
                case 2:
                    f2 = this.capHeight;
                    break;
                case 3:
                case 10:
                    f2 = this.descender;
                    break;
                case 4:
                    return this.italicAngle;
                case 5:
                    f2 = this.llx;
                    break;
                case 6:
                    f2 = this.lly;
                    break;
                case 7:
                    f2 = this.urx;
                    break;
                case 8:
                    f2 = this.ury;
                    break;
                default:
                    return 0.0f;
            }
        } else {
            f2 = this.fontWeight;
        }
        return (f2 * f) / 1000.0f;
    }

    public String[][] getFullFontName() {
        return new String[][]{new String[]{"", "", "", this.fontName}};
    }

    public String[][] getAllNameEntries() {
        return new String[][]{new String[]{"4", "", "", "", this.fontName}};
    }

    public String getPostscriptFontName() {
        return this.fontName;
    }

    public int getWidth(int i) {
        BaseFont baseFont;
        if (!this.isType0) {
            BaseFont baseFont2 = this.cjkMirror;
            if (baseFont2 != null) {
                return baseFont2.getWidth(i);
            }
            return super.getWidth(i);
        } else if (this.hMetrics == null || (baseFont = this.cjkMirror) == null || baseFont.isVertical()) {
            int[] iArr = this.metrics.get(Integer.valueOf(i));
            if (iArr != null) {
                return iArr[1];
            }
            return 0;
        } else {
            int i2 = this.hMetrics.get(this.cjkMirror.getCidCode(i));
            if (i2 > 0) {
                return i2;
            }
            return this.defaultWidth;
        }
    }

    public int getWidth(String str) {
        BaseFont baseFont;
        int i;
        if (this.isType0) {
            int i2 = 0;
            if (this.hMetrics == null || (baseFont = this.cjkMirror) == null || baseFont.isVertical()) {
                char[] charArray = str.toCharArray();
                int length = charArray.length;
                int i3 = 0;
                while (i2 < length) {
                    int[] iArr = this.metrics.get(Integer.valueOf(charArray[i2]));
                    if (iArr != null) {
                        i3 += iArr[1];
                    }
                    i2++;
                }
                return i3;
            } else if (((CJKFont) this.cjkMirror).isIdentity()) {
                int i4 = 0;
                while (i2 < str.length()) {
                    i4 += getWidth((int) str.charAt(i2));
                    i2++;
                }
                return i4;
            } else {
                int i5 = 0;
                while (i2 < str.length()) {
                    if (Utilities.isSurrogatePair(str, i2)) {
                        i = Utilities.convertToUtf32(str, i2);
                        i2++;
                    } else {
                        i = str.charAt(i2);
                    }
                    i5 += getWidth(i);
                    i2++;
                }
                return i5;
            }
        } else {
            BaseFont baseFont2 = this.cjkMirror;
            if (baseFont2 != null) {
                return baseFont2.getWidth(str);
            }
            return super.getWidth(str);
        }
    }

    public byte[] convertToBytes(String str) {
        BaseFont baseFont = this.cjkMirror;
        if (baseFont != null) {
            return baseFont.convertToBytes(str);
        }
        if (this.isType0) {
            int i = r0 * 2;
            byte[] bArr = new byte[i];
            int i2 = 0;
            for (char valueOf : str.toCharArray()) {
                int[] iArr = this.metrics.get(Integer.valueOf(valueOf));
                if (iArr != null) {
                    int i3 = iArr[0];
                    int i4 = i2 + 1;
                    bArr[i2] = (byte) (i3 / 256);
                    i2 = i4 + 1;
                    bArr[i4] = (byte) i3;
                }
            }
            if (i2 == i) {
                return bArr;
            }
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            return bArr2;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        byte[] bArr3 = new byte[length];
        int i5 = 0;
        for (int i6 = 0; i6 < charArray.length; i6++) {
            if (this.uni2byte.containsKey(charArray[i6])) {
                bArr3[i5] = (byte) this.uni2byte.get(charArray[i6]);
                i5++;
            }
        }
        if (i5 == length) {
            return bArr3;
        }
        byte[] bArr4 = new byte[i5];
        System.arraycopy(bArr3, 0, bArr4, 0, i5);
        return bArr4;
    }

    /* access modifiers changed from: package-private */
    public byte[] convertToBytes(int i) {
        BaseFont baseFont = this.cjkMirror;
        if (baseFont != null) {
            return baseFont.convertToBytes(i);
        }
        if (this.isType0) {
            int[] iArr = this.metrics.get(Integer.valueOf(i));
            if (iArr == null) {
                return new byte[0];
            }
            int i2 = iArr[0];
            return new byte[]{(byte) (i2 / 256), (byte) i2};
        } else if (!this.uni2byte.containsKey(i)) {
            return new byte[0];
        } else {
            return new byte[]{(byte) this.uni2byte.get(i)};
        }
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference getIndirectReference() {
        PRIndirectReference pRIndirectReference = this.refFont;
        if (pRIndirectReference != null) {
            return pRIndirectReference;
        }
        throw new IllegalArgumentException("Font reuse not allowed with direct font objects.");
    }

    public boolean charExists(int i) {
        BaseFont baseFont = this.cjkMirror;
        if (baseFont != null) {
            return baseFont.charExists(i);
        }
        if (this.isType0) {
            return this.metrics.containsKey(Integer.valueOf(i));
        }
        return super.charExists(i);
    }

    public double[] getFontMatrix() {
        if (this.font.getAsArray(PdfName.FONTMATRIX) != null) {
            return this.font.getAsArray(PdfName.FONTMATRIX).asDoubleArray();
        }
        return DEFAULT_FONT_MATRIX;
    }

    public boolean isVertical() {
        BaseFont baseFont = this.cjkMirror;
        if (baseFont != null) {
            return baseFont.isVertical();
        }
        return super.isVertical();
    }

    /* access modifiers changed from: package-private */
    public IntHashtable getUni2Byte() {
        return this.uni2byte;
    }

    /* access modifiers changed from: package-private */
    public IntHashtable getByte2Uni() {
        return this.byte2uni;
    }

    /* access modifiers changed from: package-private */
    public IntHashtable getDiffmap() {
        return this.diffmap;
    }

    /* access modifiers changed from: package-private */
    public boolean isSymbolic() {
        PdfNumber asNumber;
        PdfDictionary asDict = this.font.getAsDict(PdfName.FONTDESCRIPTOR);
        if (asDict == null || (asNumber = asDict.getAsNumber(PdfName.FLAGS)) == null || (asNumber.intValue() & 4) == 0) {
            return false;
        }
        return true;
    }
}
