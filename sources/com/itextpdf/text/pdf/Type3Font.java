package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.html.HtmlTags;
import java.io.IOException;
import java.util.HashMap;

public class Type3Font extends BaseFont {
    private HashMap<Integer, Type3Glyph> char2glyph;
    private boolean colorized;
    private float llx;
    private float lly;
    private PageResources pageResources;
    private float urx;
    private float ury;
    private boolean[] usedSlot;
    private IntHashtable widths3;
    private PdfWriter writer;

    public int[] getCharBBox(int i) {
        return null;
    }

    public float getFontDescriptor(int i, float f) {
        return 0.0f;
    }

    public PdfStream getFullFontStream() {
        return null;
    }

    public int getKerning(int i, int i2) {
        return 0;
    }

    public String getPostscriptFontName() {
        return "";
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

    public boolean setCharAdvance(int i, int i2) {
        return false;
    }

    public boolean setKerning(int i, int i2, int i3) {
        return false;
    }

    public void setPostscriptFontName(String str) {
    }

    public Type3Font(PdfWriter pdfWriter, char[] cArr, boolean z) {
        this(pdfWriter, z);
    }

    public Type3Font(PdfWriter pdfWriter, boolean z) {
        this.widths3 = new IntHashtable();
        this.char2glyph = new HashMap<>();
        this.llx = Float.NaN;
        this.pageResources = new PageResources();
        this.writer = pdfWriter;
        this.colorized = z;
        this.fontType = 5;
        this.usedSlot = new boolean[256];
    }

    public PdfContentByte defineGlyph(char c, float f, float f2, float f3, float f4, float f5) {
        char c2 = c;
        float f6 = f2;
        float f7 = f3;
        float f8 = f4;
        float f9 = f5;
        if (c2 == 0 || c2 > 255) {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.char.1.doesn.t.belong.in.this.type3.font", (int) c));
        }
        this.usedSlot[c2] = true;
        Integer valueOf = Integer.valueOf(c);
        Type3Glyph type3Glyph = this.char2glyph.get(valueOf);
        if (type3Glyph != null) {
            return type3Glyph;
        }
        this.widths3.put(c, (int) f);
        if (!this.colorized) {
            if (Float.isNaN(this.llx)) {
                this.llx = f6;
                this.lly = f7;
                this.urx = f8;
                this.ury = f9;
            } else {
                this.llx = Math.min(this.llx, f2);
                this.lly = Math.min(this.lly, f7);
                this.urx = Math.max(this.urx, f8);
                this.ury = Math.max(this.ury, f9);
            }
        }
        Type3Glyph type3Glyph2 = new Type3Glyph(this.writer, this.pageResources, f, f2, f3, f4, f5, this.colorized);
        this.char2glyph.put(valueOf, type3Glyph2);
        return type3Glyph2;
    }

    public String[][] getFamilyFontName() {
        return getFullFontName();
    }

    public String[][] getFullFontName() {
        return new String[][]{new String[]{"", "", "", ""}};
    }

    public String[][] getAllNameEntries() {
        return new String[][]{new String[]{"4", "", "", "", ""}};
    }

    /* access modifiers changed from: package-private */
    public void writeFont(PdfWriter pdfWriter, PdfIndirectReference pdfIndirectReference, Object[] objArr) throws DocumentException, IOException {
        boolean[] zArr;
        if (this.writer == pdfWriter) {
            int i = 0;
            while (true) {
                zArr = this.usedSlot;
                if (i < zArr.length && !zArr[i]) {
                    i++;
                }
            }
            if (i != zArr.length) {
                int length = zArr.length - 1;
                while (length >= i && !this.usedSlot[length]) {
                    length--;
                }
                int i2 = (length - i) + 1;
                int[] iArr = new int[i2];
                int[] iArr2 = new int[i2];
                int i3 = i;
                int i4 = 0;
                int i5 = 0;
                while (i3 <= length) {
                    if (this.usedSlot[i3]) {
                        iArr2[i4] = i3;
                        iArr[i5] = this.widths3.get(i3);
                        i4++;
                    }
                    i3++;
                    i5++;
                }
                PdfArray pdfArray = new PdfArray();
                PdfDictionary pdfDictionary = new PdfDictionary();
                int i6 = -1;
                for (int i7 = 0; i7 < i4; i7++) {
                    int i8 = iArr2[i7];
                    if (i8 > i6) {
                        pdfArray.add((PdfObject) new PdfNumber(i8));
                        i6 = i8;
                    }
                    i6++;
                    int i9 = iArr2[i7];
                    String unicodeToName = GlyphList.unicodeToName(i9);
                    if (unicodeToName == null) {
                        unicodeToName = HtmlTags.f475A + i9;
                    }
                    PdfName pdfName = new PdfName(unicodeToName);
                    pdfArray.add((PdfObject) pdfName);
                    PdfStream pdfStream = new PdfStream(this.char2glyph.get(Integer.valueOf(i9)).toPdf((PdfWriter) null));
                    pdfStream.flateCompress(this.compressionLevel);
                    pdfDictionary.put(pdfName, pdfWriter.addToBody(pdfStream).getIndirectReference());
                }
                PdfDictionary pdfDictionary2 = new PdfDictionary(PdfName.FONT);
                pdfDictionary2.put(PdfName.SUBTYPE, PdfName.TYPE3);
                if (this.colorized) {
                    pdfDictionary2.put(PdfName.FONTBBOX, new PdfRectangle(0.0f, 0.0f, 0.0f, 0.0f));
                } else {
                    pdfDictionary2.put(PdfName.FONTBBOX, new PdfRectangle(this.llx, this.lly, this.urx, this.ury));
                }
                pdfDictionary2.put(PdfName.FONTMATRIX, new PdfArray(new float[]{0.001f, 0.0f, 0.0f, 0.001f, 0.0f, 0.0f}));
                pdfDictionary2.put(PdfName.CHARPROCS, pdfWriter.addToBody(pdfDictionary).getIndirectReference());
                PdfDictionary pdfDictionary3 = new PdfDictionary();
                pdfDictionary3.put(PdfName.DIFFERENCES, pdfArray);
                pdfDictionary2.put(PdfName.ENCODING, pdfWriter.addToBody(pdfDictionary3).getIndirectReference());
                pdfDictionary2.put(PdfName.FIRSTCHAR, new PdfNumber(i));
                pdfDictionary2.put(PdfName.LASTCHAR, new PdfNumber(length));
                pdfDictionary2.put(PdfName.WIDTHS, pdfWriter.addToBody(new PdfArray(iArr)).getIndirectReference());
                if (this.pageResources.hasResources()) {
                    pdfDictionary2.put(PdfName.RESOURCES, pdfWriter.addToBody(this.pageResources.getResources()).getIndirectReference());
                }
                pdfWriter.addToBody((PdfObject) pdfDictionary2, pdfIndirectReference);
                return;
            }
            throw new DocumentException(MessageLocalization.getComposedMessage("no.glyphs.defined.for.type3.font", new Object[0]));
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("type3.font.used.with.the.wrong.pdfwriter", new Object[0]));
    }

    public byte[] convertToBytes(String str) {
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        byte[] bArr = new byte[length];
        int i = 0;
        for (char c : charArray) {
            if (charExists(c)) {
                bArr[i] = (byte) c;
                i++;
            }
        }
        if (length == i) {
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    /* access modifiers changed from: package-private */
    public byte[] convertToBytes(int i) {
        if (!charExists(i)) {
            return new byte[0];
        }
        return new byte[]{(byte) i};
    }

    public int getWidth(int i) {
        if (this.widths3.containsKey(i)) {
            return this.widths3.get(i);
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.char.1.is.not.defined.in.a.type3.font", i));
    }

    public int getWidth(String str) {
        char[] charArray = str.toCharArray();
        int i = 0;
        for (char width : charArray) {
            i += getWidth((int) width);
        }
        return i;
    }

    public boolean charExists(int i) {
        if (i <= 0 || i >= 256) {
            return false;
        }
        return this.usedSlot[i];
    }
}
