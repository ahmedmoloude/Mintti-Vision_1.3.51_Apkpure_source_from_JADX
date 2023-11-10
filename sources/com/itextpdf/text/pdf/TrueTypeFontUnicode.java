package com.itextpdf.text.pdf;

import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.DocWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.fonts.otf.GlyphSubstitutionTableReader;
import com.itextpdf.text.pdf.fonts.otf.Language;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.ByteCompanionObject;

class TrueTypeFontUnicode extends TrueTypeFont implements Comparator<int[]> {
    private static final List<Language> SUPPORTED_LANGUAGES_FOR_OTF = Arrays.asList(new Language[]{Language.BENGALI});
    private static final byte[] rotbits = {ByteCompanionObject.MIN_VALUE, 64, DocWriter.SPACE, BidiOrder.f527S, 8, 4, 2, 1};
    private Map<String, Glyph> glyphSubstitutionMap;
    private Language supportedLanguage;

    /* access modifiers changed from: package-private */
    public byte[] convertToBytes(int i) {
        return null;
    }

    public byte[] convertToBytes(String str) {
        return null;
    }

    TrueTypeFontUnicode(String str, String str2, boolean z, byte[] bArr, boolean z2) throws DocumentException, IOException {
        String baseName = getBaseName(str);
        String tTCName = getTTCName(baseName);
        if (baseName.length() < str.length()) {
            this.style = str.substring(baseName.length());
        }
        this.encoding = str2;
        this.embedded = z;
        this.fileName = tTCName;
        this.ttcIndex = "";
        if (tTCName.length() < baseName.length()) {
            this.ttcIndex = baseName.substring(tTCName.length() + 1);
        }
        this.fontType = 3;
        if ((this.fileName.toLowerCase().endsWith(".ttf") || this.fileName.toLowerCase().endsWith(".otf") || this.fileName.toLowerCase().endsWith(".ttc")) && ((str2.equals(BaseFont.IDENTITY_H) || str2.equals(BaseFont.IDENTITY_V)) && z)) {
            process(bArr, z2);
            if (this.os_2.fsType != 2) {
                if ((this.cmap31 == null && !this.fontSpecific) || (this.cmap10 == null && this.fontSpecific)) {
                    this.directTextToByte = true;
                }
                if (this.fontSpecific) {
                    this.fontSpecific = false;
                    String str3 = this.encoding;
                    this.encoding = "";
                    createEncoding();
                    this.encoding = str3;
                    this.fontSpecific = true;
                }
                this.vertical = str2.endsWith(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
                return;
            }
            throw new DocumentException(MessageLocalization.getComposedMessage("1.cannot.be.embedded.due.to.licensing.restrictions", this.fileName + this.style));
        }
        throw new DocumentException(MessageLocalization.getComposedMessage("1.2.is.not.a.ttf.font.file", this.fileName, this.style));
    }

    /* access modifiers changed from: package-private */
    public void process(byte[] bArr, boolean z) throws DocumentException, IOException {
        super.process(bArr, z);
    }

    public int getWidth(int i) {
        if (this.vertical) {
            return 1000;
        }
        if (!this.fontSpecific) {
            return getRawWidth(i, this.encoding);
        }
        int i2 = 65280 & i;
        if (i2 == 0 || i2 == 61440) {
            return getRawWidth(i & 255, (String) null);
        }
        return 0;
    }

    public int getWidth(String str) {
        int i;
        if (this.vertical) {
            return str.length() * 1000;
        }
        int i2 = 0;
        if (this.fontSpecific) {
            char[] charArray = str.toCharArray();
            int length = charArray.length;
            i = 0;
            while (i2 < length) {
                char c = charArray[i2];
                char c2 = 65280 & c;
                if (c2 == 0 || c2 == 61440) {
                    i += getRawWidth(c & 255, (String) null);
                }
                i2++;
            }
        } else {
            int length2 = str.length();
            i = 0;
            while (i2 < length2) {
                if (Utilities.isSurrogatePair(str, i2)) {
                    i += getRawWidth(Utilities.convertToUtf32(str, i2), this.encoding);
                    i2++;
                } else {
                    i += getRawWidth(str.charAt(i2), this.encoding);
                }
                i2++;
            }
        }
        return i;
    }

    public PdfStream getToUnicode(Object[] objArr) {
        if (objArr.length == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer("/CIDInit /ProcSet findresource begin\n12 dict begin\nbegincmap\n/CIDSystemInfo\n<< /Registry (TTX+0)\n/Ordering (T42UV)\n/Supplement 0\n>> def\n/CMapName /TTX+0 def\n/CMapType 2 def\n1 begincodespacerange\n<0000><FFFF>\nendcodespacerange\n");
        int i = 0;
        for (int i2 = 0; i2 < objArr.length; i2++) {
            if (i == 0) {
                if (i2 != 0) {
                    stringBuffer.append("endbfrange\n");
                }
                i = Math.min(100, objArr.length - i2);
                stringBuffer.append(i);
                stringBuffer.append(" beginbfrange\n");
            }
            i--;
            int[] iArr = objArr[i2];
            String hex = toHex(iArr[0]);
            stringBuffer.append(hex);
            stringBuffer.append(hex);
            stringBuffer.append(toHex(iArr[2]));
            stringBuffer.append(10);
        }
        stringBuffer.append("endbfrange\nendcmap\nCMapName currentdict /CMap defineresource pop\nend end\n");
        PdfStream pdfStream = new PdfStream(PdfEncodings.convertToBytes(stringBuffer.toString(), (String) null));
        pdfStream.flateCompress(this.compressionLevel);
        return pdfStream;
    }

    private static String toHex4(int i) {
        String str = "0000" + Integer.toHexString(i);
        return str.substring(str.length() - 4);
    }

    static String toHex(int i) {
        if (i < 65536) {
            return "<" + toHex4(i) + ">";
        }
        int i2 = i - 65536;
        return "[<" + toHex4((i2 / 1024) + 55296) + toHex4((i2 % 1024) + 56320) + ">]";
    }

    public PdfDictionary getCIDFontType2(PdfIndirectReference pdfIndirectReference, String str, Object[] objArr) {
        PdfDictionary pdfDictionary = new PdfDictionary(PdfName.FONT);
        if (this.cff) {
            pdfDictionary.put(PdfName.SUBTYPE, PdfName.CIDFONTTYPE0);
            pdfDictionary.put(PdfName.BASEFONT, new PdfName(str + this.fontName + "-" + this.encoding));
        } else {
            pdfDictionary.put(PdfName.SUBTYPE, PdfName.CIDFONTTYPE2);
            pdfDictionary.put(PdfName.BASEFONT, new PdfName(str + this.fontName));
        }
        pdfDictionary.put(PdfName.FONTDESCRIPTOR, pdfIndirectReference);
        if (!this.cff) {
            pdfDictionary.put(PdfName.CIDTOGIDMAP, PdfName.IDENTITY);
        }
        PdfDictionary pdfDictionary2 = new PdfDictionary();
        pdfDictionary2.put(PdfName.REGISTRY, new PdfString("Adobe"));
        pdfDictionary2.put(PdfName.ORDERING, new PdfString("Identity"));
        pdfDictionary2.put(PdfName.SUPPLEMENT, new PdfNumber(0));
        pdfDictionary.put(PdfName.CIDSYSTEMINFO, pdfDictionary2);
        if (!this.vertical) {
            pdfDictionary.put(PdfName.f577DW, new PdfNumber(1000));
            StringBuffer stringBuffer = new StringBuffer("[");
            int i = -10;
            boolean z = true;
            for (int[] iArr : objArr) {
                if (iArr[1] != 1000) {
                    int i2 = iArr[0];
                    if (i2 == i + 1) {
                        stringBuffer.append(' ');
                        stringBuffer.append(iArr[1]);
                    } else {
                        if (!z) {
                            stringBuffer.append(']');
                        }
                        stringBuffer.append(i2);
                        stringBuffer.append('[');
                        stringBuffer.append(iArr[1]);
                        z = false;
                    }
                    i = i2;
                }
            }
            if (stringBuffer.length() > 1) {
                stringBuffer.append("]]");
                pdfDictionary.put(PdfName.f668W, new PdfLiteral(stringBuffer.toString()));
            }
        }
        return pdfDictionary;
    }

    public PdfDictionary getFontBaseType(PdfIndirectReference pdfIndirectReference, String str, PdfIndirectReference pdfIndirectReference2) {
        PdfDictionary pdfDictionary = new PdfDictionary(PdfName.FONT);
        pdfDictionary.put(PdfName.SUBTYPE, PdfName.TYPE0);
        if (this.cff) {
            PdfName pdfName = PdfName.BASEFONT;
            pdfDictionary.put(pdfName, new PdfName(str + this.fontName + "-" + this.encoding));
        } else {
            PdfName pdfName2 = PdfName.BASEFONT;
            pdfDictionary.put(pdfName2, new PdfName(str + this.fontName));
        }
        pdfDictionary.put(PdfName.ENCODING, new PdfName(this.encoding));
        pdfDictionary.put(PdfName.DESCENDANTFONTS, new PdfArray((PdfObject) pdfIndirectReference));
        if (pdfIndirectReference2 != null) {
            pdfDictionary.put(PdfName.TOUNICODE, pdfIndirectReference2);
        }
        return pdfDictionary;
    }

    public int GetCharFromGlyphId(int i) {
        if (this.glyphIdToChar == null) {
            int[] iArr = new int[this.maxGlyphId];
            HashMap hashMap = null;
            if (this.cmapExt != null) {
                hashMap = this.cmapExt;
            } else if (this.cmap31 != null) {
                hashMap = this.cmap31;
            }
            if (hashMap != null) {
                for (Map.Entry entry : hashMap.entrySet()) {
                    iArr[((int[]) entry.getValue())[0]] = ((Integer) entry.getKey()).intValue();
                }
            }
            this.glyphIdToChar = iArr;
        }
        return this.glyphIdToChar[i];
    }

    public int compare(int[] iArr, int[] iArr2) {
        int i = iArr[0];
        int i2 = iArr2[0];
        if (i < i2) {
            return -1;
        }
        if (i == i2) {
            return 0;
        }
        return 1;
    }

    /* access modifiers changed from: package-private */
    public void writeFont(PdfWriter pdfWriter, PdfIndirectReference pdfIndirectReference, Object[] objArr) throws DocumentException, IOException {
        pdfWriter.getTtfUnicodeWriter().writeFont(this, pdfIndirectReference, objArr, rotbits);
    }

    public PdfStream getFullFontStream() throws IOException, DocumentException {
        if (this.cff) {
            return new BaseFont.StreamFont(readCffFont(), "CIDFontType0C", this.compressionLevel);
        }
        return super.getFullFontStream();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
        r5 = com.itextpdf.text.pdf.languages.ArabicLigaturizer.getReverseMapping((char) r5);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int[] getMetricsTT(int r5) {
        /*
            r4 = this;
            java.util.HashMap r0 = r4.cmapExt
            if (r0 == 0) goto L_0x0011
            java.util.HashMap r0 = r4.cmapExt
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Object r5 = r0.get(r5)
            int[] r5 = (int[]) r5
            return r5
        L_0x0011:
            boolean r0 = r4.fontSpecific
            if (r0 == 0) goto L_0x0018
            java.util.HashMap r0 = r4.cmap10
            goto L_0x001a
        L_0x0018:
            java.util.HashMap r0 = r4.cmap31
        L_0x001a:
            r1 = 0
            if (r0 != 0) goto L_0x001e
            return r1
        L_0x001e:
            boolean r2 = r4.fontSpecific
            if (r2 == 0) goto L_0x003a
            r2 = r5 & -256(0xffffffffffffff00, float:NaN)
            if (r2 == 0) goto L_0x002d
            r3 = 61440(0xf000, float:8.6096E-41)
            if (r2 != r3) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            return r1
        L_0x002d:
            r5 = r5 & 255(0xff, float:3.57E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Object r5 = r0.get(r5)
            int[] r5 = (int[]) r5
            return r5
        L_0x003a:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            java.lang.Object r1 = r0.get(r1)
            int[] r1 = (int[]) r1
            if (r1 != 0) goto L_0x005c
            char r5 = (char) r5
            java.lang.Character r5 = com.itextpdf.text.pdf.languages.ArabicLigaturizer.getReverseMapping(r5)
            if (r5 == 0) goto L_0x005c
            char r5 = r5.charValue()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Object r5 = r0.get(r5)
            r1 = r5
            int[] r1 = (int[]) r1
        L_0x005c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.TrueTypeFontUnicode.getMetricsTT(int):int[]");
    }

    public boolean charExists(int i) {
        return getMetricsTT(i) != null;
    }

    public boolean setCharAdvance(int i, int i2) {
        int[] metricsTT = getMetricsTT(i);
        if (metricsTT == null) {
            return false;
        }
        metricsTT[1] = i2;
        return true;
    }

    public int[] getCharBBox(int i) {
        int[] metricsTT;
        if (this.bboxes == null || (metricsTT = getMetricsTT(i)) == null) {
            return null;
        }
        return this.bboxes[metricsTT[0]];
    }

    /* access modifiers changed from: protected */
    public Map<String, Glyph> getGlyphSubstitutionMap() {
        return this.glyphSubstitutionMap;
    }

    /* access modifiers changed from: package-private */
    public Language getSupportedLanguage() {
        return this.supportedLanguage;
    }

    private void readGsubTable() throws IOException {
        if (this.tables.get("GSUB") != null) {
            HashMap hashMap = new HashMap(this.cmap31.size());
            for (Integer num : this.cmap31.keySet()) {
                hashMap.put(Integer.valueOf(((int[]) this.cmap31.get(num))[0]), Character.valueOf((char) num.intValue()));
            }
            GlyphSubstitutionTableReader glyphSubstitutionTableReader = new GlyphSubstitutionTableReader(this.f698rf, ((int[]) this.tables.get("GSUB"))[0], hashMap, this.glyphWidthsByIndex);
            try {
                glyphSubstitutionTableReader.read();
                Language supportedLanguage2 = glyphSubstitutionTableReader.getSupportedLanguage();
                this.supportedLanguage = supportedLanguage2;
                if (SUPPORTED_LANGUAGES_FOR_OTF.contains(supportedLanguage2)) {
                    this.glyphSubstitutionMap = glyphSubstitutionTableReader.getGlyphSubstitutionMap();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
