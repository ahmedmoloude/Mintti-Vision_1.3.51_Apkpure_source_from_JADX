package com.itextpdf.text.pdf.codec;

import com.itextpdf.text.Image;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.ByteBuffer;
import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfLiteral;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import kotlin.UByte;

public class PngImage {
    public static final String IDAT = "IDAT";
    public static final String IEND = "IEND";
    public static final String IHDR = "IHDR";
    public static final String PLTE = "PLTE";
    public static final int[] PNGID = {137, 80, 78, 71, 13, 10, 26, 10};
    private static final int PNG_FILTER_AVERAGE = 3;
    private static final int PNG_FILTER_NONE = 0;
    private static final int PNG_FILTER_PAETH = 4;
    private static final int PNG_FILTER_SUB = 1;
    private static final int PNG_FILTER_UP = 2;
    private static final int TRANSFERSIZE = 4096;
    public static final String cHRM = "cHRM";
    public static final String gAMA = "gAMA";
    public static final String iCCP = "iCCP";
    private static final PdfName[] intents = {PdfName.PERCEPTUAL, PdfName.RELATIVECOLORIMETRIC, PdfName.SATURATION, PdfName.ABSOLUTECOLORIMETRIC};
    public static final String pHYs = "pHYs";
    public static final String sRGB = "sRGB";
    public static final String tRNS = "tRNS";
    float XYRatio;
    PdfDictionary additional = new PdfDictionary();
    int bitDepth;
    int bytesPerPixel;
    byte[] colorTable;
    int colorType;
    int compressionMethod;
    DataInputStream dataStream;
    int dpiX;
    int dpiY;
    int filterMethod;
    float gamma = 1.0f;
    boolean genBWMask;
    boolean hasCHRM = false;
    int height;
    ICC_Profile icc_profile;
    NewByteArrayOutputStream idat = new NewByteArrayOutputStream();
    byte[] image;
    int inputBands;
    PdfName intent;
    int interlaceMethod;

    /* renamed from: is */
    InputStream f710is;
    boolean palShades;
    byte[] smask;
    byte[] trans;
    int transBlue = -1;
    int transGreen = -1;
    int transRedGray = -1;
    int width;

    /* renamed from: xB */
    float f711xB;

    /* renamed from: xG */
    float f712xG;

    /* renamed from: xR */
    float f713xR;

    /* renamed from: xW */
    float f714xW;

    /* renamed from: yB */
    float f715yB;

    /* renamed from: yG */
    float f716yG;

    /* renamed from: yR */
    float f717yR;

    /* renamed from: yW */
    float f718yW;

    PngImage(InputStream inputStream) {
        this.f710is = inputStream;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.text.Image getImage(java.net.URL r2) throws java.io.IOException {
        /*
            java.io.InputStream r0 = r2.openStream()     // Catch:{ all -> 0x0013 }
            com.itextpdf.text.Image r1 = getImage((java.io.InputStream) r0)     // Catch:{ all -> 0x0011 }
            r1.setUrl(r2)     // Catch:{ all -> 0x0011 }
            if (r0 == 0) goto L_0x0010
            r0.close()
        L_0x0010:
            return r1
        L_0x0011:
            r2 = move-exception
            goto L_0x0015
        L_0x0013:
            r2 = move-exception
            r0 = 0
        L_0x0015:
            if (r0 == 0) goto L_0x001a
            r0.close()
        L_0x001a:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.PngImage.getImage(java.net.URL):com.itextpdf.text.Image");
    }

    public static Image getImage(InputStream inputStream) throws IOException {
        return new PngImage(inputStream).getImage();
    }

    public static Image getImage(String str) throws IOException {
        return getImage(Utilities.toURL(str));
    }

    public static Image getImage(byte[] bArr) throws IOException {
        Image image2 = getImage((InputStream) new ByteArrayInputStream(bArr));
        image2.setOriginalData(bArr);
        return image2;
    }

    /* access modifiers changed from: package-private */
    public boolean checkMarker(String str) {
        if (str.length() != 4) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            char charAt = str.charAt(i);
            if ((charAt < 'a' || charAt > 'z') && (charAt < 'A' || charAt > 'Z')) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void readPng() throws IOException {
        int i = 0;
        while (true) {
            int[] iArr = PNGID;
            if (i >= iArr.length) {
                int i2 = 4096;
                byte[] bArr = new byte[4096];
                while (true) {
                    int i3 = getInt(this.f710is);
                    String string = getString(this.f710is);
                    if (i3 >= 0 && checkMarker(string)) {
                        if (IDAT.equals(string)) {
                            while (i3 != 0) {
                                int read = this.f710is.read(bArr, 0, Math.min(i3, i2));
                                if (read >= 0) {
                                    this.idat.write(bArr, 0, read);
                                    i3 -= read;
                                } else {
                                    return;
                                }
                            }
                            continue;
                        } else if (tRNS.equals(string)) {
                            int i4 = this.colorType;
                            if (i4 != 0) {
                                if (i4 != 2) {
                                    if (i4 == 3 && i3 > 0) {
                                        this.trans = new byte[i3];
                                        for (int i5 = 0; i5 < i3; i5++) {
                                            this.trans[i5] = (byte) this.f710is.read();
                                        }
                                        i3 = 0;
                                    }
                                } else if (i3 >= 6) {
                                    i3 -= 6;
                                    int word = getWord(this.f710is);
                                    int word2 = getWord(this.f710is);
                                    int word3 = getWord(this.f710is);
                                    if (this.bitDepth == 16) {
                                        this.transRedGray = word;
                                        this.transGreen = word2;
                                        this.transBlue = word3;
                                    } else {
                                        this.additional.put(PdfName.MASK, new PdfLiteral("[" + word + " " + word + " " + word2 + " " + word2 + " " + word3 + " " + word3 + "]"));
                                    }
                                }
                            } else if (i3 >= 2) {
                                i3 -= 2;
                                int word4 = getWord(this.f710is);
                                if (this.bitDepth == 16) {
                                    this.transRedGray = word4;
                                } else {
                                    this.additional.put(PdfName.MASK, new PdfLiteral("[" + word4 + " " + word4 + "]"));
                                }
                            }
                            Utilities.skip(this.f710is, i3);
                        } else if (IHDR.equals(string)) {
                            this.width = getInt(this.f710is);
                            this.height = getInt(this.f710is);
                            this.bitDepth = this.f710is.read();
                            this.colorType = this.f710is.read();
                            this.compressionMethod = this.f710is.read();
                            this.filterMethod = this.f710is.read();
                            this.interlaceMethod = this.f710is.read();
                        } else {
                            boolean z = true;
                            if (PLTE.equals(string)) {
                                if (this.colorType == 3) {
                                    PdfArray pdfArray = new PdfArray();
                                    pdfArray.add((PdfObject) PdfName.INDEXED);
                                    pdfArray.add(getColorspace());
                                    pdfArray.add((PdfObject) new PdfNumber((i3 / 3) - 1));
                                    ByteBuffer byteBuffer = new ByteBuffer();
                                    while (true) {
                                        int i6 = i3 - 1;
                                        if (i3 <= 0) {
                                            break;
                                        }
                                        byteBuffer.append_i(this.f710is.read());
                                        i3 = i6;
                                    }
                                    byte[] byteArray = byteBuffer.toByteArray();
                                    this.colorTable = byteArray;
                                    pdfArray.add((PdfObject) new PdfString(byteArray));
                                    this.additional.put(PdfName.COLORSPACE, pdfArray);
                                } else {
                                    Utilities.skip(this.f710is, i3);
                                }
                            } else if (pHYs.equals(string)) {
                                int i7 = getInt(this.f710is);
                                int i8 = getInt(this.f710is);
                                if (this.f710is.read() == 1) {
                                    this.dpiX = (int) ((((float) i7) * 0.0254f) + 0.5f);
                                    this.dpiY = (int) ((((float) i8) * 0.0254f) + 0.5f);
                                } else if (i8 != 0) {
                                    this.XYRatio = ((float) i7) / ((float) i8);
                                }
                            } else if (cHRM.equals(string)) {
                                this.f714xW = ((float) getInt(this.f710is)) / 100000.0f;
                                this.f718yW = ((float) getInt(this.f710is)) / 100000.0f;
                                this.f713xR = ((float) getInt(this.f710is)) / 100000.0f;
                                this.f717yR = ((float) getInt(this.f710is)) / 100000.0f;
                                this.f712xG = ((float) getInt(this.f710is)) / 100000.0f;
                                this.f716yG = ((float) getInt(this.f710is)) / 100000.0f;
                                this.f711xB = ((float) getInt(this.f710is)) / 100000.0f;
                                this.f715yB = ((float) getInt(this.f710is)) / 100000.0f;
                                if (Math.abs(this.f714xW) < 1.0E-4f || Math.abs(this.f718yW) < 1.0E-4f || Math.abs(this.f713xR) < 1.0E-4f || Math.abs(this.f717yR) < 1.0E-4f || Math.abs(this.f712xG) < 1.0E-4f || Math.abs(this.f716yG) < 1.0E-4f || Math.abs(this.f711xB) < 1.0E-4f || Math.abs(this.f715yB) < 1.0E-4f) {
                                    z = false;
                                }
                                this.hasCHRM = z;
                            } else if (sRGB.equals(string)) {
                                this.intent = intents[this.f710is.read()];
                                this.gamma = 2.2f;
                                this.f714xW = 0.3127f;
                                this.f718yW = 0.329f;
                                this.f713xR = 0.64f;
                                this.f717yR = 0.33f;
                                this.f712xG = 0.3f;
                                this.f716yG = 0.6f;
                                this.f711xB = 0.15f;
                                this.f715yB = 0.06f;
                                this.hasCHRM = true;
                            } else if (gAMA.equals(string)) {
                                int i9 = getInt(this.f710is);
                                if (i9 != 0) {
                                    this.gamma = 100000.0f / ((float) i9);
                                    if (!this.hasCHRM) {
                                        this.f714xW = 0.3127f;
                                        this.f718yW = 0.329f;
                                        this.f713xR = 0.64f;
                                        this.f717yR = 0.33f;
                                        this.f712xG = 0.3f;
                                        this.f716yG = 0.6f;
                                        this.f711xB = 0.15f;
                                        this.f715yB = 0.06f;
                                        this.hasCHRM = true;
                                    }
                                }
                            } else if (iCCP.equals(string)) {
                                do {
                                    i3--;
                                } while (this.f710is.read() != 0);
                                this.f710is.read();
                                int i10 = i3 - 1;
                                byte[] bArr2 = new byte[i10];
                                int i11 = 0;
                                while (i10 > 0) {
                                    int read2 = this.f710is.read(bArr2, i11, i10);
                                    if (read2 >= 0) {
                                        i11 += read2;
                                        i10 -= read2;
                                    } else {
                                        throw new IOException(MessageLocalization.getComposedMessage("premature.end.of.file", new Object[0]));
                                    }
                                }
                                try {
                                    this.icc_profile = ICC_Profile.getInstance(PdfReader.FlateDecode(bArr2, true));
                                } catch (RuntimeException unused) {
                                    this.icc_profile = null;
                                }
                            } else if (!IEND.equals(string)) {
                                Utilities.skip(this.f710is, i3);
                            } else {
                                return;
                            }
                        }
                        Utilities.skip(this.f710is, 4);
                        i2 = 4096;
                    }
                }
                throw new IOException(MessageLocalization.getComposedMessage("corrupted.png.file", new Object[0]));
            } else if (iArr[i] == this.f710is.read()) {
                i++;
            } else {
                throw new IOException(MessageLocalization.getComposedMessage("file.is.not.a.valid.png", new Object[0]));
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: com.itextpdf.text.pdf.PdfLiteral} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: com.itextpdf.text.pdf.PdfLiteral} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: com.itextpdf.text.pdf.PdfArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: com.itextpdf.text.pdf.PdfLiteral} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.PdfObject getColorspace() {
        /*
            r19 = this;
            r0 = r19
            com.itextpdf.text.pdf.ICC_Profile r1 = r0.icc_profile
            if (r1 == 0) goto L_0x0012
            int r1 = r0.colorType
            r1 = r1 & 2
            if (r1 != 0) goto L_0x000f
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.DEVICEGRAY
            return r1
        L_0x000f:
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.DEVICERGB
            return r1
        L_0x0012:
            float r1 = r0.gamma
            r2 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x002a
            boolean r1 = r0.hasCHRM
            if (r1 != 0) goto L_0x002a
            int r1 = r0.colorType
            r1 = r1 & 2
            if (r1 != 0) goto L_0x0027
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.DEVICEGRAY
            return r1
        L_0x0027:
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.DEVICERGB
            return r1
        L_0x002a:
            com.itextpdf.text.pdf.PdfArray r1 = new com.itextpdf.text.pdf.PdfArray
            r1.<init>()
            com.itextpdf.text.pdf.PdfDictionary r3 = new com.itextpdf.text.pdf.PdfDictionary
            r3.<init>()
            int r4 = r0.colorType
            r4 = r4 & 2
            java.lang.String r5 = "[1 1 1]"
            if (r4 != 0) goto L_0x0065
            float r4 = r0.gamma
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x0045
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.DEVICEGRAY
            return r1
        L_0x0045:
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.CALGRAY
            r1.add((com.itextpdf.text.pdf.PdfObject) r2)
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.GAMMA
            com.itextpdf.text.pdf.PdfNumber r4 = new com.itextpdf.text.pdf.PdfNumber
            float r6 = r0.gamma
            r4.<init>((float) r6)
            r3.put(r2, r4)
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.WHITEPOINT
            com.itextpdf.text.pdf.PdfLiteral r4 = new com.itextpdf.text.pdf.PdfLiteral
            r4.<init>((java.lang.String) r5)
            r3.put(r2, r4)
            r1.add((com.itextpdf.text.pdf.PdfObject) r3)
            goto L_0x018c
        L_0x0065:
            com.itextpdf.text.pdf.PdfLiteral r4 = new com.itextpdf.text.pdf.PdfLiteral
            r4.<init>((java.lang.String) r5)
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.CALRGB
            r1.add((com.itextpdf.text.pdf.PdfObject) r5)
            float r5 = r0.gamma
            int r5 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r5 == 0) goto L_0x008f
            com.itextpdf.text.pdf.PdfArray r5 = new com.itextpdf.text.pdf.PdfArray
            r5.<init>()
            com.itextpdf.text.pdf.PdfNumber r6 = new com.itextpdf.text.pdf.PdfNumber
            float r7 = r0.gamma
            r6.<init>((float) r7)
            r5.add((com.itextpdf.text.pdf.PdfObject) r6)
            r5.add((com.itextpdf.text.pdf.PdfObject) r6)
            r5.add((com.itextpdf.text.pdf.PdfObject) r6)
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.GAMMA
            r3.put(r6, r5)
        L_0x008f:
            boolean r5 = r0.hasCHRM
            if (r5 == 0) goto L_0x0184
            float r4 = r0.f718yW
            float r5 = r0.f712xG
            float r6 = r0.f711xB
            float r7 = r5 - r6
            float r8 = r0.f717yR
            float r7 = r7 * r8
            float r9 = r0.f713xR
            float r10 = r9 - r6
            float r11 = r0.f716yG
            float r10 = r10 * r11
            float r7 = r7 - r10
            float r10 = r9 - r5
            float r12 = r0.f715yB
            float r10 = r10 * r12
            float r7 = r7 + r10
            float r7 = r7 * r4
            float r10 = r5 - r6
            float r10 = r10 * r4
            float r13 = r0.f714xW
            float r14 = r13 - r6
            float r14 = r14 * r11
            float r10 = r10 - r14
            float r14 = r13 - r5
            float r14 = r14 * r12
            float r10 = r10 + r14
            float r10 = r10 * r8
            float r10 = r10 / r7
            float r14 = r10 * r9
            float r14 = r14 / r8
            float r15 = r2 - r9
            float r15 = r15 / r8
            float r15 = r15 - r2
            float r15 = r15 * r10
            float r2 = -r11
            float r17 = r9 - r6
            float r17 = r17 * r4
            float r18 = r13 - r6
            float r18 = r18 * r8
            float r17 = r17 - r18
            float r8 = r13 - r9
            float r8 = r8 * r12
            float r17 = r17 + r8
            float r2 = r2 * r17
            float r2 = r2 / r7
            float r8 = r2 * r5
            float r8 = r8 / r11
            r16 = 1065353216(0x3f800000, float:1.0)
            float r17 = r16 - r5
            float r17 = r17 / r11
            float r17 = r17 - r16
            float r0 = r2 * r17
            float r17 = r9 - r5
            float r17 = r17 * r4
            float r5 = r13 - r5
            float r5 = r5 * r4
            float r17 = r17 - r5
            float r13 = r13 - r9
            float r13 = r13 * r11
            float r17 = r17 + r13
            float r17 = r17 * r12
            float r4 = r17 / r7
            float r5 = r4 * r6
            float r5 = r5 / r12
            r7 = 1065353216(0x3f800000, float:1.0)
            float r6 = r7 - r6
            float r6 = r6 / r12
            float r6 = r6 - r7
            float r6 = r6 * r4
            float r7 = r14 + r8
            float r7 = r7 + r5
            float r9 = r15 + r0
            float r9 = r9 + r6
            com.itextpdf.text.pdf.PdfArray r11 = new com.itextpdf.text.pdf.PdfArray
            r11.<init>()
            com.itextpdf.text.pdf.PdfNumber r12 = new com.itextpdf.text.pdf.PdfNumber
            r12.<init>((float) r7)
            r11.add((com.itextpdf.text.pdf.PdfObject) r12)
            com.itextpdf.text.pdf.PdfNumber r7 = new com.itextpdf.text.pdf.PdfNumber
            r12 = 1065353216(0x3f800000, float:1.0)
            r7.<init>((float) r12)
            r11.add((com.itextpdf.text.pdf.PdfObject) r7)
            com.itextpdf.text.pdf.PdfNumber r7 = new com.itextpdf.text.pdf.PdfNumber
            r7.<init>((float) r9)
            r11.add((com.itextpdf.text.pdf.PdfObject) r7)
            com.itextpdf.text.pdf.PdfArray r7 = new com.itextpdf.text.pdf.PdfArray
            r7.<init>()
            com.itextpdf.text.pdf.PdfNumber r9 = new com.itextpdf.text.pdf.PdfNumber
            r9.<init>((float) r14)
            r7.add((com.itextpdf.text.pdf.PdfObject) r9)
            com.itextpdf.text.pdf.PdfNumber r9 = new com.itextpdf.text.pdf.PdfNumber
            r9.<init>((float) r10)
            r7.add((com.itextpdf.text.pdf.PdfObject) r9)
            com.itextpdf.text.pdf.PdfNumber r9 = new com.itextpdf.text.pdf.PdfNumber
            r9.<init>((float) r15)
            r7.add((com.itextpdf.text.pdf.PdfObject) r9)
            com.itextpdf.text.pdf.PdfNumber r9 = new com.itextpdf.text.pdf.PdfNumber
            r9.<init>((float) r8)
            r7.add((com.itextpdf.text.pdf.PdfObject) r9)
            com.itextpdf.text.pdf.PdfNumber r8 = new com.itextpdf.text.pdf.PdfNumber
            r8.<init>((float) r2)
            r7.add((com.itextpdf.text.pdf.PdfObject) r8)
            com.itextpdf.text.pdf.PdfNumber r2 = new com.itextpdf.text.pdf.PdfNumber
            r2.<init>((float) r0)
            r7.add((com.itextpdf.text.pdf.PdfObject) r2)
            com.itextpdf.text.pdf.PdfNumber r0 = new com.itextpdf.text.pdf.PdfNumber
            r0.<init>((float) r5)
            r7.add((com.itextpdf.text.pdf.PdfObject) r0)
            com.itextpdf.text.pdf.PdfNumber r0 = new com.itextpdf.text.pdf.PdfNumber
            r0.<init>((float) r4)
            r7.add((com.itextpdf.text.pdf.PdfObject) r0)
            com.itextpdf.text.pdf.PdfNumber r0 = new com.itextpdf.text.pdf.PdfNumber
            r0.<init>((float) r6)
            r7.add((com.itextpdf.text.pdf.PdfObject) r0)
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.MATRIX
            r3.put(r0, r7)
            r4 = r11
        L_0x0184:
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.WHITEPOINT
            r3.put(r0, r4)
            r1.add((com.itextpdf.text.pdf.PdfObject) r3)
        L_0x018c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.PngImage.getColorspace():com.itextpdf.text.pdf.PdfObject");
    }

    /* JADX WARNING: type inference failed for: r0v9, types: [com.itextpdf.text.Image] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.Image getImage() throws java.io.IOException {
        /*
            r13 = this;
            r13.readPng()
            r0 = 0
            r13.palShades = r0     // Catch:{ Exception -> 0x019f }
            byte[] r1 = r13.trans     // Catch:{ Exception -> 0x019f }
            r2 = 1
            if (r1 == 0) goto L_0x0027
            r1 = 0
            r3 = 0
            r4 = 0
        L_0x000e:
            byte[] r5 = r13.trans     // Catch:{ Exception -> 0x019f }
            int r6 = r5.length     // Catch:{ Exception -> 0x019f }
            if (r1 >= r6) goto L_0x0029
            byte r5 = r5[r1]     // Catch:{ Exception -> 0x019f }
            r6 = 255(0xff, float:3.57E-43)
            r5 = r5 & r6
            if (r5 != 0) goto L_0x001d
            int r3 = r3 + 1
            r4 = r1
        L_0x001d:
            if (r5 == 0) goto L_0x0024
            if (r5 == r6) goto L_0x0024
            r13.palShades = r2     // Catch:{ Exception -> 0x019f }
            goto L_0x0029
        L_0x0024:
            int r1 = r1 + 1
            goto L_0x000e
        L_0x0027:
            r3 = 0
            r4 = 0
        L_0x0029:
            int r1 = r13.colorType     // Catch:{ Exception -> 0x019f }
            r5 = 4
            r1 = r1 & r5
            if (r1 == 0) goto L_0x0031
            r13.palShades = r2     // Catch:{ Exception -> 0x019f }
        L_0x0031:
            boolean r1 = r13.palShades     // Catch:{ Exception -> 0x019f }
            if (r1 != 0) goto L_0x003d
            if (r3 > r2) goto L_0x003b
            int r6 = r13.transRedGray     // Catch:{ Exception -> 0x019f }
            if (r6 < 0) goto L_0x003d
        L_0x003b:
            r6 = 1
            goto L_0x003e
        L_0x003d:
            r6 = 0
        L_0x003e:
            r13.genBWMask = r6     // Catch:{ Exception -> 0x019f }
            if (r1 != 0) goto L_0x0070
            if (r6 != 0) goto L_0x0070
            if (r3 != r2) goto L_0x0070
            com.itextpdf.text.pdf.PdfDictionary r1 = r13.additional     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.MASK     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfLiteral r6 = new com.itextpdf.text.pdf.PdfLiteral     // Catch:{ Exception -> 0x019f }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x019f }
            r7.<init>()     // Catch:{ Exception -> 0x019f }
            java.lang.String r8 = "["
            r7.append(r8)     // Catch:{ Exception -> 0x019f }
            r7.append(r4)     // Catch:{ Exception -> 0x019f }
            java.lang.String r8 = " "
            r7.append(r8)     // Catch:{ Exception -> 0x019f }
            r7.append(r4)     // Catch:{ Exception -> 0x019f }
            java.lang.String r4 = "]"
            r7.append(r4)     // Catch:{ Exception -> 0x019f }
            java.lang.String r4 = r7.toString()     // Catch:{ Exception -> 0x019f }
            r6.<init>((java.lang.String) r4)     // Catch:{ Exception -> 0x019f }
            r1.put(r3, r6)     // Catch:{ Exception -> 0x019f }
        L_0x0070:
            int r1 = r13.interlaceMethod     // Catch:{ Exception -> 0x019f }
            r3 = 16
            if (r1 == r2) goto L_0x0087
            int r1 = r13.bitDepth     // Catch:{ Exception -> 0x019f }
            if (r1 == r3) goto L_0x0087
            int r1 = r13.colorType     // Catch:{ Exception -> 0x019f }
            r1 = r1 & r5
            if (r1 != 0) goto L_0x0087
            boolean r1 = r13.palShades     // Catch:{ Exception -> 0x019f }
            if (r1 != 0) goto L_0x0087
            boolean r1 = r13.genBWMask     // Catch:{ Exception -> 0x019f }
            if (r1 == 0) goto L_0x0088
        L_0x0087:
            r0 = 1
        L_0x0088:
            int r1 = r13.colorType     // Catch:{ Exception -> 0x019f }
            r4 = 2
            r6 = 3
            if (r1 == 0) goto L_0x00a4
            r7 = 6
            if (r1 == r7) goto L_0x00a1
            if (r1 == r4) goto L_0x009e
            if (r1 == r6) goto L_0x009b
            if (r1 == r5) goto L_0x0098
            goto L_0x00a6
        L_0x0098:
            r13.inputBands = r4     // Catch:{ Exception -> 0x019f }
            goto L_0x00a6
        L_0x009b:
            r13.inputBands = r2     // Catch:{ Exception -> 0x019f }
            goto L_0x00a6
        L_0x009e:
            r13.inputBands = r6     // Catch:{ Exception -> 0x019f }
            goto L_0x00a6
        L_0x00a1:
            r13.inputBands = r5     // Catch:{ Exception -> 0x019f }
            goto L_0x00a6
        L_0x00a4:
            r13.inputBands = r2     // Catch:{ Exception -> 0x019f }
        L_0x00a6:
            if (r0 == 0) goto L_0x00ab
            r13.decodeIdat()     // Catch:{ Exception -> 0x019f }
        L_0x00ab:
            int r0 = r13.inputBands     // Catch:{ Exception -> 0x019f }
            int r1 = r13.colorType     // Catch:{ Exception -> 0x019f }
            r5 = r1 & 4
            if (r5 == 0) goto L_0x00b5
            int r0 = r0 + -1
        L_0x00b5:
            r10 = r0
            int r0 = r13.bitDepth     // Catch:{ Exception -> 0x019f }
            r5 = 8
            if (r0 != r3) goto L_0x00bf
            r11 = 8
            goto L_0x00c0
        L_0x00bf:
            r11 = r0
        L_0x00c0:
            byte[] r0 = r13.image     // Catch:{ Exception -> 0x019f }
            if (r0 == 0) goto L_0x00dc
            if (r1 != r6) goto L_0x00d3
            com.itextpdf.text.ImgRaw r0 = new com.itextpdf.text.ImgRaw     // Catch:{ Exception -> 0x019f }
            int r8 = r13.width     // Catch:{ Exception -> 0x019f }
            int r9 = r13.height     // Catch:{ Exception -> 0x019f }
            byte[] r12 = r13.image     // Catch:{ Exception -> 0x019f }
            r7 = r0
            r7.<init>(r8, r9, r10, r11, r12)     // Catch:{ Exception -> 0x019f }
            goto L_0x0131
        L_0x00d3:
            int r1 = r13.width     // Catch:{ Exception -> 0x019f }
            int r3 = r13.height     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.Image r0 = com.itextpdf.text.Image.getInstance(r1, r3, r10, r11, r0)     // Catch:{ Exception -> 0x019f }
            goto L_0x0131
        L_0x00dc:
            com.itextpdf.text.ImgRaw r0 = new com.itextpdf.text.ImgRaw     // Catch:{ Exception -> 0x019f }
            int r8 = r13.width     // Catch:{ Exception -> 0x019f }
            int r9 = r13.height     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.codec.PngImage$NewByteArrayOutputStream r1 = r13.idat     // Catch:{ Exception -> 0x019f }
            byte[] r12 = r1.toByteArray()     // Catch:{ Exception -> 0x019f }
            r7 = r0
            r7.<init>(r8, r9, r10, r11, r12)     // Catch:{ Exception -> 0x019f }
            r0.setDeflated(r2)     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfDictionary r1 = new com.itextpdf.text.pdf.PdfDictionary     // Catch:{ Exception -> 0x019f }
            r1.<init>()     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.BITSPERCOMPONENT     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfNumber r7 = new com.itextpdf.text.pdf.PdfNumber     // Catch:{ Exception -> 0x019f }
            int r8 = r13.bitDepth     // Catch:{ Exception -> 0x019f }
            r7.<init>((int) r8)     // Catch:{ Exception -> 0x019f }
            r1.put(r3, r7)     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.PREDICTOR     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfNumber r7 = new com.itextpdf.text.pdf.PdfNumber     // Catch:{ Exception -> 0x019f }
            r8 = 15
            r7.<init>((int) r8)     // Catch:{ Exception -> 0x019f }
            r1.put(r3, r7)     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.COLUMNS     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfNumber r7 = new com.itextpdf.text.pdf.PdfNumber     // Catch:{ Exception -> 0x019f }
            int r8 = r13.width     // Catch:{ Exception -> 0x019f }
            r7.<init>((int) r8)     // Catch:{ Exception -> 0x019f }
            r1.put(r3, r7)     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.COLORS     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfNumber r7 = new com.itextpdf.text.pdf.PdfNumber     // Catch:{ Exception -> 0x019f }
            int r8 = r13.colorType     // Catch:{ Exception -> 0x019f }
            if (r8 == r6) goto L_0x0123
            r8 = r8 & r4
            if (r8 != 0) goto L_0x0124
        L_0x0123:
            r6 = 1
        L_0x0124:
            r7.<init>((int) r6)     // Catch:{ Exception -> 0x019f }
            r1.put(r3, r7)     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfDictionary r3 = r13.additional     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.DECODEPARMS     // Catch:{ Exception -> 0x019f }
            r3.put(r6, r1)     // Catch:{ Exception -> 0x019f }
        L_0x0131:
            com.itextpdf.text.pdf.PdfDictionary r1 = r13.additional     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.COLORSPACE     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfObject r1 = r1.get(r3)     // Catch:{ Exception -> 0x019f }
            if (r1 != 0) goto L_0x0146
            com.itextpdf.text.pdf.PdfDictionary r1 = r13.additional     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.COLORSPACE     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfObject r6 = r13.getColorspace()     // Catch:{ Exception -> 0x019f }
            r1.put(r3, r6)     // Catch:{ Exception -> 0x019f }
        L_0x0146:
            com.itextpdf.text.pdf.PdfName r1 = r13.intent     // Catch:{ Exception -> 0x019f }
            if (r1 == 0) goto L_0x0153
            com.itextpdf.text.pdf.PdfDictionary r1 = r13.additional     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.INTENT     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.pdf.PdfName r6 = r13.intent     // Catch:{ Exception -> 0x019f }
            r1.put(r3, r6)     // Catch:{ Exception -> 0x019f }
        L_0x0153:
            com.itextpdf.text.pdf.PdfDictionary r1 = r13.additional     // Catch:{ Exception -> 0x019f }
            int r1 = r1.size()     // Catch:{ Exception -> 0x019f }
            if (r1 <= 0) goto L_0x0160
            com.itextpdf.text.pdf.PdfDictionary r1 = r13.additional     // Catch:{ Exception -> 0x019f }
            r0.setAdditional(r1)     // Catch:{ Exception -> 0x019f }
        L_0x0160:
            com.itextpdf.text.pdf.ICC_Profile r1 = r13.icc_profile     // Catch:{ Exception -> 0x019f }
            if (r1 == 0) goto L_0x0167
            r0.tagICC(r1)     // Catch:{ Exception -> 0x019f }
        L_0x0167:
            boolean r1 = r13.palShades     // Catch:{ Exception -> 0x019f }
            if (r1 == 0) goto L_0x017b
            int r1 = r13.width     // Catch:{ Exception -> 0x019f }
            int r3 = r13.height     // Catch:{ Exception -> 0x019f }
            byte[] r6 = r13.smask     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.Image r1 = com.itextpdf.text.Image.getInstance(r1, r3, r2, r5, r6)     // Catch:{ Exception -> 0x019f }
            r1.makeMask()     // Catch:{ Exception -> 0x019f }
            r0.setImageMask(r1)     // Catch:{ Exception -> 0x019f }
        L_0x017b:
            boolean r1 = r13.genBWMask     // Catch:{ Exception -> 0x019f }
            if (r1 == 0) goto L_0x018f
            int r1 = r13.width     // Catch:{ Exception -> 0x019f }
            int r3 = r13.height     // Catch:{ Exception -> 0x019f }
            byte[] r5 = r13.smask     // Catch:{ Exception -> 0x019f }
            com.itextpdf.text.Image r1 = com.itextpdf.text.Image.getInstance(r1, r3, r2, r2, r5)     // Catch:{ Exception -> 0x019f }
            r1.makeMask()     // Catch:{ Exception -> 0x019f }
            r0.setImageMask(r1)     // Catch:{ Exception -> 0x019f }
        L_0x018f:
            int r1 = r13.dpiX     // Catch:{ Exception -> 0x019f }
            int r2 = r13.dpiY     // Catch:{ Exception -> 0x019f }
            r0.setDpi(r1, r2)     // Catch:{ Exception -> 0x019f }
            float r1 = r13.XYRatio     // Catch:{ Exception -> 0x019f }
            r0.setXYRatio(r1)     // Catch:{ Exception -> 0x019f }
            r0.setOriginalType(r4)     // Catch:{ Exception -> 0x019f }
            return r0
        L_0x019f:
            r0 = move-exception
            com.itextpdf.text.ExceptionConverter r1 = new com.itextpdf.text.ExceptionConverter
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.PngImage.getImage():com.itextpdf.text.Image");
    }

    /* access modifiers changed from: package-private */
    public void decodeIdat() {
        int i = this.bitDepth;
        int i2 = i == 16 ? 8 : i;
        int i3 = -1;
        int i4 = i == 16 ? 2 : 1;
        this.bytesPerPixel = i4;
        int i5 = this.colorType;
        if (i5 == 0) {
            i3 = (((i2 * this.width) + 7) / 8) * this.height;
        } else if (i5 == 6) {
            i3 = this.width * 3 * this.height;
            this.bytesPerPixel = i4 * 4;
        } else if (i5 == 2) {
            i3 = this.width * 3 * this.height;
            this.bytesPerPixel = i4 * 3;
        } else if (i5 == 3) {
            if (this.interlaceMethod == 1) {
                i3 = (((i2 * this.width) + 7) / 8) * this.height;
            }
            this.bytesPerPixel = 1;
        } else if (i5 == 4) {
            i3 = this.width * this.height;
            this.bytesPerPixel = i4 * 2;
        }
        if (i3 >= 0) {
            this.image = new byte[i3];
        }
        if (this.palShades) {
            this.smask = new byte[(this.width * this.height)];
        } else if (this.genBWMask) {
            this.smask = new byte[(((this.width + 7) / 8) * this.height)];
        }
        this.dataStream = new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(this.idat.getBuf(), 0, this.idat.size()), new Inflater()));
        if (this.interlaceMethod != 1) {
            decodePass(0, 0, 1, 1, this.width, this.height);
            return;
        }
        decodePass(0, 0, 8, 8, (this.width + 7) / 8, (this.height + 7) / 8);
        decodePass(4, 0, 8, 8, (this.width + 3) / 8, (this.height + 7) / 8);
        decodePass(0, 4, 4, 8, (this.width + 3) / 4, (this.height + 3) / 8);
        decodePass(2, 0, 4, 4, (this.width + 1) / 4, (this.height + 3) / 4);
        decodePass(0, 2, 2, 4, (this.width + 1) / 2, (this.height + 1) / 4);
        decodePass(1, 0, 2, 2, this.width / 2, (this.height + 1) / 2);
        decodePass(0, 1, 1, 2, this.width, this.height / 2);
    }

    /* access modifiers changed from: package-private */
    public void decodePass(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int i8 = i6;
        if (i5 != 0 && i8 != 0) {
            int i9 = (((this.inputBands * i5) * this.bitDepth) + 7) / 8;
            int i10 = i2;
            byte[] bArr = new byte[i9];
            byte[] bArr2 = new byte[i9];
            int i11 = 0;
            while (i11 < i8) {
                try {
                    i7 = this.dataStream.read();
                    try {
                        this.dataStream.readFully(bArr, 0, i9);
                    } catch (Exception unused) {
                    }
                } catch (Exception unused2) {
                    i7 = 0;
                }
                if (i7 != 0) {
                    if (i7 == 1) {
                        decodeSubFilter(bArr, i9, this.bytesPerPixel);
                    } else if (i7 == 2) {
                        decodeUpFilter(bArr, bArr2, i9);
                    } else if (i7 == 3) {
                        decodeAverageFilter(bArr, bArr2, i9, this.bytesPerPixel);
                    } else if (i7 == 4) {
                        decodePaethFilter(bArr, bArr2, i9, this.bytesPerPixel);
                    } else {
                        throw new RuntimeException(MessageLocalization.getComposedMessage("png.filter.unknown", new Object[0]));
                    }
                }
                processPixels(bArr, i, i3, i10, i5);
                i11++;
                i10 += i4;
                byte[] bArr3 = bArr2;
                bArr2 = bArr;
                bArr = bArr3;
            }
        }
    }

    /* JADX WARNING: type inference failed for: r3v4, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r2v19, types: [byte] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ce  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processPixels(byte[] r27, int r28, int r29, int r30, int r31) {
        /*
            r26 = this;
            r0 = r26
            r1 = r31
            int[] r10 = r26.getPixel(r27)
            int r2 = r0.colorType
            r11 = 4
            r12 = 3
            r13 = 2
            r14 = 1
            r15 = 0
            if (r2 == 0) goto L_0x0020
            r3 = 6
            if (r2 == r3) goto L_0x001d
            if (r2 == r13) goto L_0x001d
            if (r2 == r12) goto L_0x0020
            if (r2 == r11) goto L_0x0020
            r16 = 0
            goto L_0x0022
        L_0x001d:
            r16 = 3
            goto L_0x0022
        L_0x0020:
            r16 = 1
        L_0x0022:
            byte[] r2 = r0.image
            r9 = 16
            r17 = 8
            if (r2 == 0) goto L_0x0063
            int r2 = r0.width
            int r2 = r2 * r16
            int r3 = r0.bitDepth
            if (r3 != r9) goto L_0x0034
            r3 = 8
        L_0x0034:
            int r2 = r2 * r3
            int r2 = r2 + 7
            int r18 = r2 / 8
            r19 = r28
            r8 = 0
        L_0x003d:
            if (r8 >= r1) goto L_0x0063
            byte[] r2 = r0.image
            int r3 = r0.inputBands
            int r4 = r3 * r8
            int r7 = r0.bitDepth
            r3 = r10
            r5 = r16
            r6 = r19
            r20 = r7
            r7 = r30
            r21 = r8
            r8 = r20
            r12 = 16
            r9 = r18
            setPixel(r2, r3, r4, r5, r6, r7, r8, r9)
            int r19 = r19 + r29
            int r8 = r21 + 1
            r9 = 16
            r12 = 3
            goto L_0x003d
        L_0x0063:
            r12 = 16
            boolean r2 = r0.palShades
            if (r2 == 0) goto L_0x00ce
            int r2 = r0.colorType
            r2 = r2 & r11
            if (r2 == 0) goto L_0x00a2
            int r2 = r0.bitDepth
            if (r2 != r12) goto L_0x0084
            r2 = 0
        L_0x0073:
            if (r2 >= r1) goto L_0x0084
            int r3 = r0.inputBands
            int r3 = r3 * r2
            int r3 = r3 + r16
            r4 = r10[r3]
            int r4 = r4 >>> 8
            r10[r3] = r4
            int r2 = r2 + 1
            goto L_0x0073
        L_0x0084:
            int r11 = r0.width
            r12 = r28
        L_0x0088:
            if (r15 >= r1) goto L_0x018c
            byte[] r2 = r0.smask
            int r3 = r0.inputBands
            int r3 = r3 * r15
            int r4 = r3 + r16
            r5 = 1
            r8 = 8
            r3 = r10
            r6 = r12
            r7 = r30
            r9 = r11
            setPixel(r2, r3, r4, r5, r6, r7, r8, r9)
            int r12 = r12 + r29
            int r15 = r15 + 1
            goto L_0x0088
        L_0x00a2:
            int r11 = r0.width
            int[] r12 = new int[r14]
            r13 = r28
            r14 = 0
        L_0x00a9:
            if (r14 >= r1) goto L_0x018c
            r2 = r10[r14]
            byte[] r3 = r0.trans
            int r4 = r3.length
            if (r2 >= r4) goto L_0x00b7
            byte r2 = r3[r2]
            r12[r15] = r2
            goto L_0x00bb
        L_0x00b7:
            r2 = 255(0xff, float:3.57E-43)
            r12[r15] = r2
        L_0x00bb:
            byte[] r2 = r0.smask
            r4 = 0
            r5 = 1
            r8 = 8
            r3 = r12
            r6 = r13
            r7 = r30
            r9 = r11
            setPixel(r2, r3, r4, r5, r6, r7, r8, r9)
            int r13 = r13 + r29
            int r14 = r14 + 1
            goto L_0x00a9
        L_0x00ce:
            boolean r2 = r0.genBWMask
            if (r2 == 0) goto L_0x018c
            int r2 = r0.colorType
            if (r2 == 0) goto L_0x015a
            if (r2 == r13) goto L_0x0114
            r3 = 3
            if (r2 == r3) goto L_0x00dd
            goto L_0x018c
        L_0x00dd:
            int r2 = r0.width
            int r2 = r2 + 7
            int r2 = r2 / 8
            int[] r3 = new int[r14]
            r4 = r28
            r5 = 0
        L_0x00e8:
            if (r5 >= r1) goto L_0x018c
            r6 = r10[r5]
            byte[] r7 = r0.trans
            int r8 = r7.length
            if (r6 >= r8) goto L_0x00f7
            byte r6 = r7[r6]
            if (r6 != 0) goto L_0x00f7
            r6 = 1
            goto L_0x00f8
        L_0x00f7:
            r6 = 0
        L_0x00f8:
            r3[r15] = r6
            byte[] r6 = r0.smask
            r20 = 0
            r21 = 1
            r24 = 1
            r18 = r6
            r19 = r3
            r22 = r4
            r23 = r30
            r25 = r2
            setPixel(r18, r19, r20, r21, r22, r23, r24, r25)
            int r4 = r4 + r29
            int r5 = r5 + 1
            goto L_0x00e8
        L_0x0114:
            int r2 = r0.width
            int r2 = r2 + 7
            int r2 = r2 / 8
            int[] r3 = new int[r14]
            r4 = r28
            r5 = 0
        L_0x011f:
            if (r5 >= r1) goto L_0x018c
            int r6 = r0.inputBands
            int r6 = r6 * r5
            r7 = r10[r6]
            int r8 = r0.transRedGray
            if (r7 != r8) goto L_0x013d
            int r7 = r6 + 1
            r7 = r10[r7]
            int r8 = r0.transGreen
            if (r7 != r8) goto L_0x013d
            int r6 = r6 + 2
            r6 = r10[r6]
            int r7 = r0.transBlue
            if (r6 != r7) goto L_0x013d
            r6 = 1
            goto L_0x013e
        L_0x013d:
            r6 = 0
        L_0x013e:
            r3[r15] = r6
            byte[] r6 = r0.smask
            r20 = 0
            r21 = 1
            r24 = 1
            r18 = r6
            r19 = r3
            r22 = r4
            r23 = r30
            r25 = r2
            setPixel(r18, r19, r20, r21, r22, r23, r24, r25)
            int r4 = r4 + r29
            int r5 = r5 + 1
            goto L_0x011f
        L_0x015a:
            int r2 = r0.width
            int r2 = r2 + 7
            int r2 = r2 / 8
            int[] r3 = new int[r14]
            r4 = r28
            r5 = 0
        L_0x0165:
            if (r5 >= r1) goto L_0x018c
            r6 = r10[r5]
            int r7 = r0.transRedGray
            if (r6 != r7) goto L_0x016f
            r6 = 1
            goto L_0x0170
        L_0x016f:
            r6 = 0
        L_0x0170:
            r3[r15] = r6
            byte[] r6 = r0.smask
            r20 = 0
            r21 = 1
            r24 = 1
            r18 = r6
            r19 = r3
            r22 = r4
            r23 = r30
            r25 = r2
            setPixel(r18, r19, r20, r21, r22, r23, r24, r25)
            int r4 = r4 + r29
            int r5 = r5 + 1
            goto L_0x0165
        L_0x018c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.PngImage.processPixels(byte[], int, int, int, int):void");
    }

    static int getPixel(byte[] bArr, int i, int i2, int i3, int i4) {
        if (i3 == 8) {
            return bArr[(i4 * i2) + i] & UByte.MAX_VALUE;
        }
        int i5 = i4 * i2;
        int i6 = 8 / i3;
        return (bArr[i5 + (i / i6)] >> ((8 - ((i % i6) * i3)) - i3)) & ((1 << i3) - 1);
    }

    static void setPixel(byte[] bArr, int[] iArr, int i, int i2, int i3, int i4, int i5, int i6) {
        int i7 = 0;
        if (i5 == 8) {
            int i8 = (i6 * i4) + (i3 * i2);
            while (i7 < i2) {
                bArr[i8 + i7] = (byte) iArr[i7 + i];
                i7++;
            }
        } else if (i5 == 16) {
            int i9 = (i6 * i4) + (i3 * i2);
            while (i7 < i2) {
                bArr[i9 + i7] = (byte) (iArr[i7 + i] >>> 8);
                i7++;
            }
        } else {
            int i10 = 8 / i5;
            int i11 = (i6 * i4) + (i3 / i10);
            bArr[i11] = (byte) ((iArr[i] << ((8 - ((i3 % i10) * i5)) - i5)) | bArr[i11]);
        }
    }

    /* access modifiers changed from: package-private */
    public int[] getPixel(byte[] bArr) {
        int i = this.bitDepth;
        int i2 = 0;
        if (i == 8) {
            int length = bArr.length;
            int[] iArr = new int[length];
            while (i2 < length) {
                iArr[i2] = bArr[i2] & UByte.MAX_VALUE;
                i2++;
            }
            return iArr;
        } else if (i != 16) {
            int[] iArr2 = new int[((bArr.length * 8) / i)];
            int i3 = 8 / i;
            int i4 = (1 << i) - 1;
            int i5 = 0;
            while (i2 < bArr.length) {
                int i6 = i3 - 1;
                while (i6 >= 0) {
                    iArr2[i5] = (bArr[i2] >>> (this.bitDepth * i6)) & i4;
                    i6--;
                    i5++;
                }
                i2++;
            }
            return iArr2;
        } else {
            int length2 = bArr.length / 2;
            int[] iArr3 = new int[length2];
            while (i2 < length2) {
                int i7 = i2 * 2;
                iArr3[i2] = ((bArr[i7] & UByte.MAX_VALUE) << 8) + (bArr[i7 + 1] & UByte.MAX_VALUE);
                i2++;
            }
            return iArr3;
        }
    }

    private static void decodeSubFilter(byte[] bArr, int i, int i2) {
        for (int i3 = i2; i3 < i; i3++) {
            bArr[i3] = (byte) ((bArr[i3] & UByte.MAX_VALUE) + (bArr[i3 - i2] & UByte.MAX_VALUE));
        }
    }

    private static void decodeUpFilter(byte[] bArr, byte[] bArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) ((bArr[i2] & UByte.MAX_VALUE) + (bArr2[i2] & UByte.MAX_VALUE));
        }
    }

    private static void decodeAverageFilter(byte[] bArr, byte[] bArr2, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) ((bArr[i3] & UByte.MAX_VALUE) + ((bArr2[i3] & UByte.MAX_VALUE) / 2));
        }
        for (int i4 = i2; i4 < i; i4++) {
            bArr[i4] = (byte) ((bArr[i4] & UByte.MAX_VALUE) + (((bArr[i4 - i2] & UByte.MAX_VALUE) + (bArr2[i4] & UByte.MAX_VALUE)) / 2));
        }
    }

    private static int paethPredictor(int i, int i2, int i3) {
        int i4 = (i + i2) - i3;
        int abs = Math.abs(i4 - i);
        int abs2 = Math.abs(i4 - i2);
        int abs3 = Math.abs(i4 - i3);
        if (abs > abs2 || abs > abs3) {
            return abs2 <= abs3 ? i2 : i3;
        }
        return i;
    }

    private static void decodePaethFilter(byte[] bArr, byte[] bArr2, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) ((bArr[i3] & UByte.MAX_VALUE) + (bArr2[i3] & UByte.MAX_VALUE));
        }
        for (int i4 = i2; i4 < i; i4++) {
            int i5 = i4 - i2;
            bArr[i4] = (byte) ((bArr[i4] & UByte.MAX_VALUE) + paethPredictor(bArr[i5] & UByte.MAX_VALUE, bArr2[i4] & UByte.MAX_VALUE, bArr2[i5] & UByte.MAX_VALUE));
        }
    }

    static class NewByteArrayOutputStream extends ByteArrayOutputStream {
        NewByteArrayOutputStream() {
        }

        public byte[] getBuf() {
            return this.buf;
        }
    }

    public static final int getInt(InputStream inputStream) throws IOException {
        return (inputStream.read() << 24) + (inputStream.read() << 16) + (inputStream.read() << 8) + inputStream.read();
    }

    public static final int getWord(InputStream inputStream) throws IOException {
        return (inputStream.read() << 8) + inputStream.read();
    }

    public static final String getString(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            stringBuffer.append((char) inputStream.read());
        }
        return stringBuffer.toString();
    }
}
