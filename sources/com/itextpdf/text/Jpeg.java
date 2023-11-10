package com.itextpdf.text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Jpeg extends Image {
    public static final byte[] JFIF_ID = {74, 70, 73, 70, 0};
    public static final int M_APP0 = 224;
    public static final int M_APP2 = 226;
    public static final int M_APPD = 237;
    public static final int M_APPE = 238;
    public static final int NOPARAM_MARKER = 2;
    public static final int[] NOPARAM_MARKERS = {208, 209, 210, 211, 212, 213, 214, 215, 216, 1};
    public static final int NOT_A_MARKER = -1;
    public static final byte[] PS_8BIM_RESO = {56, 66, 73, 77, 3, -19};
    public static final int UNSUPPORTED_MARKER = 1;
    public static final int[] UNSUPPORTED_MARKERS = {195, 197, 198, 199, 200, 201, 202, 203, 205, 206, 207};
    public static final int VALID_MARKER = 0;
    public static final int[] VALID_MARKERS = {192, 193, 194};
    private byte[][] icc;

    Jpeg(Image image) {
        super(image);
    }

    public Jpeg(URL url) throws BadElementException, IOException {
        super(url);
        processParameters();
    }

    public Jpeg(byte[] bArr) throws BadElementException, IOException {
        super((URL) null);
        this.rawData = bArr;
        this.originalData = bArr;
        processParameters();
    }

    public Jpeg(byte[] bArr, float f, float f2) throws BadElementException, IOException {
        this(bArr);
        this.scaledWidth = f;
        this.scaledHeight = f2;
    }

    private static final int getShort(InputStream inputStream) throws IOException {
        return (inputStream.read() << 8) + inputStream.read();
    }

    private static final int marker(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = VALID_MARKERS;
            if (i3 >= iArr.length) {
                int i4 = 0;
                while (true) {
                    int[] iArr2 = NOPARAM_MARKERS;
                    if (i4 >= iArr2.length) {
                        while (true) {
                            int[] iArr3 = UNSUPPORTED_MARKERS;
                            if (i2 >= iArr3.length) {
                                return -1;
                            }
                            if (i == iArr3[i2]) {
                                return 1;
                            }
                            i2++;
                        }
                    } else if (i == iArr2[i4]) {
                        return 2;
                    } else {
                        i4++;
                    }
                }
            } else if (i == iArr[i3]) {
                return 0;
            } else {
                i3++;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0212, code lost:
        com.itextpdf.text.Utilities.skip(r3, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0219, code lost:
        if (r3.read() != 8) goto L_0x0295;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x021b, code lost:
        r1.scaledHeight = (float) getShort(r3);
        setTop(r1.scaledHeight);
        r1.scaledWidth = (float) getShort(r3);
        setRight(r1.scaledWidth);
        r1.colorspace = r3.read();
        r1.bpc = 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x023b, code lost:
        if (r3 == null) goto L_0x0240;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x023d, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x0240, code lost:
        r1.plainWidth = getWidth();
        r1.plainHeight = getHeight();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x024e, code lost:
        if (r1.icc == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0250, code lost:
        r0 = 0;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0252, code lost:
        r4 = r1.icc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0255, code lost:
        if (r0 >= r4.length) goto L_0x026a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0259, code lost:
        if (r4[r0] != null) goto L_0x0262;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x025b, code lost:
        r1.icc = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0261, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0262, code lost:
        r3 = r3 + (r4[r0].length - 14);
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x026a, code lost:
        r0 = new byte[r3];
        r3 = 0;
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x026e, code lost:
        r4 = r1.icc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0271, code lost:
        if (r7 >= r4.length) goto L_0x0286;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x0273, code lost:
        java.lang.System.arraycopy(r4[r7], 14, r0, r3, r4[r7].length - 14);
        r3 = r3 + (r1.icc[r7].length - 14);
        r7 = r7 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:?, code lost:
        tagICC(com.itextpdf.text.pdf.ICC_Profile.getInstance(r0, r1.colorspace));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x02a5, code lost:
        throw new com.itextpdf.text.BadElementException(com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage("1.must.have.8.bits.per.component", r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:?, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x02f4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processParameters() throws com.itextpdf.text.BadElementException, java.io.IOException {
        /*
            r16 = this;
            r1 = r16
            r0 = 32
            r1.type = r0
            r0 = 1
            r1.originalType = r0
            byte[] r3 = r1.rawData     // Catch:{ all -> 0x02f0 }
            if (r3 != 0) goto L_0x001e
            java.net.URL r3 = r1.url     // Catch:{ all -> 0x02f0 }
            java.io.InputStream r3 = r3.openStream()     // Catch:{ all -> 0x02f0 }
            java.net.URL r4 = r1.url     // Catch:{ all -> 0x001a }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x001a }
            goto L_0x0027
        L_0x001a:
            r0 = move-exception
            r2 = r3
            goto L_0x02f2
        L_0x001e:
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x02f0 }
            byte[] r4 = r1.rawData     // Catch:{ all -> 0x02f0 }
            r3.<init>(r4)     // Catch:{ all -> 0x02f0 }
            java.lang.String r4 = "Byte array"
        L_0x0027:
            int r5 = r3.read()     // Catch:{ all -> 0x001a }
            r6 = 255(0xff, float:3.57E-43)
            r7 = 0
            if (r5 != r6) goto L_0x02df
            int r5 = r3.read()     // Catch:{ all -> 0x001a }
            r8 = 216(0xd8, float:3.03E-43)
            if (r5 != r8) goto L_0x02df
            r5 = 1
        L_0x0039:
            int r8 = r3.read()     // Catch:{ all -> 0x001a }
            if (r8 < 0) goto L_0x02d0
            if (r8 != r6) goto L_0x02cc
            int r8 = r3.read()     // Catch:{ all -> 0x001a }
            r9 = 16
            r10 = 1056964608(0x3f000000, float:0.5)
            r11 = 1076006748(0x40228f5c, float:2.54)
            r12 = 2
            if (r5 == 0) goto L_0x00c4
            r13 = 224(0xe0, float:3.14E-43)
            if (r8 != r13) goto L_0x00c4
            int r5 = getShort(r3)     // Catch:{ all -> 0x001a }
            if (r5 >= r9) goto L_0x005f
            int r5 = r5 + -2
            com.itextpdf.text.Utilities.skip(r3, r5)     // Catch:{ all -> 0x001a }
            goto L_0x00b2
        L_0x005f:
            byte[] r8 = JFIF_ID     // Catch:{ all -> 0x001a }
            int r8 = r8.length     // Catch:{ all -> 0x001a }
            byte[] r9 = new byte[r8]     // Catch:{ all -> 0x001a }
            int r13 = r3.read(r9)     // Catch:{ all -> 0x001a }
            if (r13 != r8) goto L_0x00b4
            r13 = 0
        L_0x006b:
            if (r13 >= r8) goto L_0x007a
            byte r14 = r9[r13]     // Catch:{ all -> 0x001a }
            byte[] r15 = JFIF_ID     // Catch:{ all -> 0x001a }
            byte r15 = r15[r13]     // Catch:{ all -> 0x001a }
            if (r14 == r15) goto L_0x0077
            r9 = 0
            goto L_0x007b
        L_0x0077:
            int r13 = r13 + 1
            goto L_0x006b
        L_0x007a:
            r9 = 1
        L_0x007b:
            if (r9 != 0) goto L_0x0084
            int r5 = r5 + -2
            int r5 = r5 - r8
            com.itextpdf.text.Utilities.skip(r3, r5)     // Catch:{ all -> 0x001a }
            goto L_0x00b2
        L_0x0084:
            com.itextpdf.text.Utilities.skip(r3, r12)     // Catch:{ all -> 0x001a }
            int r9 = r3.read()     // Catch:{ all -> 0x001a }
            int r13 = getShort(r3)     // Catch:{ all -> 0x001a }
            int r14 = getShort(r3)     // Catch:{ all -> 0x001a }
            if (r9 != r0) goto L_0x009a
            r1.dpiX = r13     // Catch:{ all -> 0x001a }
            r1.dpiY = r14     // Catch:{ all -> 0x001a }
            goto L_0x00aa
        L_0x009a:
            if (r9 != r12) goto L_0x00aa
            float r9 = (float) r13     // Catch:{ all -> 0x001a }
            float r9 = r9 * r11
            float r9 = r9 + r10
            int r9 = (int) r9     // Catch:{ all -> 0x001a }
            r1.dpiX = r9     // Catch:{ all -> 0x001a }
            float r9 = (float) r14     // Catch:{ all -> 0x001a }
            float r9 = r9 * r11
            float r9 = r9 + r10
            int r9 = (int) r9     // Catch:{ all -> 0x001a }
            r1.dpiY = r9     // Catch:{ all -> 0x001a }
        L_0x00aa:
            int r5 = r5 + -2
            int r5 = r5 - r8
            int r5 = r5 + -7
            com.itextpdf.text.Utilities.skip(r3, r5)     // Catch:{ all -> 0x001a }
        L_0x00b2:
            r5 = 0
            goto L_0x0039
        L_0x00b4:
            com.itextpdf.text.BadElementException r2 = new com.itextpdf.text.BadElementException     // Catch:{ all -> 0x001a }
            java.lang.String r5 = "1.corrupted.jfif.marker"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x001a }
            r0[r7] = r4     // Catch:{ all -> 0x001a }
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r5, (java.lang.Object[]) r0)     // Catch:{ all -> 0x001a }
            r2.<init>((java.lang.String) r0)     // Catch:{ all -> 0x001a }
            throw r2     // Catch:{ all -> 0x001a }
        L_0x00c4:
            r13 = 238(0xee, float:3.34E-43)
            java.lang.String r14 = "ISO-8859-1"
            r15 = 12
            if (r8 != r13) goto L_0x00f4
            int r8 = getShort(r3)     // Catch:{ all -> 0x001a }
            int r8 = r8 - r12
            byte[] r9 = new byte[r8]     // Catch:{ all -> 0x001a }
            r10 = 0
        L_0x00d4:
            if (r10 >= r8) goto L_0x00e0
            int r11 = r3.read()     // Catch:{ all -> 0x001a }
            byte r11 = (byte) r11     // Catch:{ all -> 0x001a }
            r9[r10] = r11     // Catch:{ all -> 0x001a }
            int r10 = r10 + 1
            goto L_0x00d4
        L_0x00e0:
            if (r8 < r15) goto L_0x02cd
            java.lang.String r8 = new java.lang.String     // Catch:{ all -> 0x001a }
            r10 = 5
            r8.<init>(r9, r7, r10, r14)     // Catch:{ all -> 0x001a }
            java.lang.String r9 = "Adobe"
            boolean r8 = r8.equals(r9)     // Catch:{ all -> 0x001a }
            if (r8 == 0) goto L_0x02cd
            r1.invert = r0     // Catch:{ all -> 0x001a }
            goto L_0x02cd
        L_0x00f4:
            r13 = 226(0xe2, float:3.17E-43)
            r2 = 14
            if (r8 != r13) goto L_0x013c
            int r8 = getShort(r3)     // Catch:{ all -> 0x001a }
            int r8 = r8 - r12
            byte[] r9 = new byte[r8]     // Catch:{ all -> 0x001a }
            r10 = 0
        L_0x0102:
            if (r10 >= r8) goto L_0x010e
            int r11 = r3.read()     // Catch:{ all -> 0x001a }
            byte r11 = (byte) r11     // Catch:{ all -> 0x001a }
            r9[r10] = r11     // Catch:{ all -> 0x001a }
            int r10 = r10 + 1
            goto L_0x0102
        L_0x010e:
            if (r8 < r2) goto L_0x02cd
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x001a }
            r8 = 11
            r2.<init>(r9, r7, r8, r14)     // Catch:{ all -> 0x001a }
            java.lang.String r8 = "ICC_PROFILE"
            boolean r2 = r2.equals(r8)     // Catch:{ all -> 0x001a }
            if (r2 == 0) goto L_0x02cd
            byte r2 = r9[r15]     // Catch:{ all -> 0x001a }
            r2 = r2 & r6
            r8 = 13
            byte r8 = r9[r8]     // Catch:{ all -> 0x001a }
            r8 = r8 & r6
            if (r2 >= r0) goto L_0x012a
            r2 = 1
        L_0x012a:
            if (r8 >= r0) goto L_0x012d
            r8 = 1
        L_0x012d:
            byte[][] r10 = r1.icc     // Catch:{ all -> 0x001a }
            if (r10 != 0) goto L_0x0135
            byte[][] r8 = new byte[r8][]     // Catch:{ all -> 0x001a }
            r1.icc = r8     // Catch:{ all -> 0x001a }
        L_0x0135:
            byte[][] r8 = r1.icc     // Catch:{ all -> 0x001a }
            int r2 = r2 - r0
            r8[r2] = r9     // Catch:{ all -> 0x001a }
            goto L_0x02cd
        L_0x013c:
            r13 = 237(0xed, float:3.32E-43)
            r14 = 8
            if (r8 != r13) goto L_0x020c
            int r2 = getShort(r3)     // Catch:{ all -> 0x001a }
            int r2 = r2 - r12
            byte[] r8 = new byte[r2]     // Catch:{ all -> 0x001a }
            r13 = 0
        L_0x014a:
            if (r13 >= r2) goto L_0x0156
            int r15 = r3.read()     // Catch:{ all -> 0x001a }
            byte r15 = (byte) r15     // Catch:{ all -> 0x001a }
            r8[r13] = r15     // Catch:{ all -> 0x001a }
            int r13 = r13 + 1
            goto L_0x014a
        L_0x0156:
            r13 = 0
        L_0x0157:
            byte[] r15 = PS_8BIM_RESO     // Catch:{ all -> 0x001a }
            int r15 = r15.length     // Catch:{ all -> 0x001a }
            int r15 = r2 - r15
            if (r13 >= r15) goto L_0x017d
            r15 = 0
        L_0x015f:
            byte[] r7 = PS_8BIM_RESO     // Catch:{ all -> 0x001a }
            int r10 = r7.length     // Catch:{ all -> 0x001a }
            if (r15 >= r10) goto L_0x0173
            int r10 = r13 + r15
            byte r10 = r8[r10]     // Catch:{ all -> 0x001a }
            byte r7 = r7[r15]     // Catch:{ all -> 0x001a }
            if (r10 == r7) goto L_0x016e
            r7 = 0
            goto L_0x0174
        L_0x016e:
            int r15 = r15 + 1
            r10 = 1056964608(0x3f000000, float:0.5)
            goto L_0x015f
        L_0x0173:
            r7 = 1
        L_0x0174:
            if (r7 == 0) goto L_0x0177
            goto L_0x017d
        L_0x0177:
            int r13 = r13 + 1
            r7 = 0
            r10 = 1056964608(0x3f000000, float:0.5)
            goto L_0x0157
        L_0x017d:
            byte[] r7 = PS_8BIM_RESO     // Catch:{ all -> 0x001a }
            int r10 = r7.length     // Catch:{ all -> 0x001a }
            int r13 = r13 + r10
            int r7 = r7.length     // Catch:{ all -> 0x001a }
            int r2 = r2 - r7
            if (r13 >= r2) goto L_0x02cd
            byte r2 = r8[r13]     // Catch:{ all -> 0x001a }
            int r2 = r2 + r0
            byte r2 = (byte) r2     // Catch:{ all -> 0x001a }
            int r7 = r2 % 2
            if (r7 != r0) goto L_0x0190
            int r2 = r2 + 1
            byte r2 = (byte) r2     // Catch:{ all -> 0x001a }
        L_0x0190:
            int r13 = r13 + r2
            byte r2 = r8[r13]     // Catch:{ all -> 0x001a }
            int r2 = r2 << 24
            int r7 = r13 + 1
            byte r7 = r8[r7]     // Catch:{ all -> 0x001a }
            int r7 = r7 << r9
            int r2 = r2 + r7
            int r7 = r13 + 2
            byte r7 = r8[r7]     // Catch:{ all -> 0x001a }
            int r7 = r7 << r14
            int r2 = r2 + r7
            int r7 = r13 + 3
            byte r7 = r8[r7]     // Catch:{ all -> 0x001a }
            int r2 = r2 + r7
            if (r2 == r9) goto L_0x01aa
            goto L_0x02cd
        L_0x01aa:
            int r13 = r13 + 4
            byte r2 = r8[r13]     // Catch:{ all -> 0x001a }
            int r2 = r2 << r14
            int r7 = r13 + 1
            byte r7 = r8[r7]     // Catch:{ all -> 0x001a }
            r7 = r7 & r6
            int r2 = r2 + r7
            int r13 = r13 + 2
            int r13 = r13 + r12
            byte r7 = r8[r13]     // Catch:{ all -> 0x001a }
            int r7 = r7 << r14
            int r9 = r13 + 1
            byte r9 = r8[r9]     // Catch:{ all -> 0x001a }
            r9 = r9 & r6
            int r7 = r7 + r9
            int r13 = r13 + 2
            int r13 = r13 + r12
            byte r9 = r8[r13]     // Catch:{ all -> 0x001a }
            int r9 = r9 << r14
            int r10 = r13 + 1
            byte r10 = r8[r10]     // Catch:{ all -> 0x001a }
            r10 = r10 & r6
            int r9 = r9 + r10
            int r13 = r13 + 2
            int r13 = r13 + r12
            byte r10 = r8[r13]     // Catch:{ all -> 0x001a }
            int r10 = r10 << r14
            int r13 = r13 + 1
            byte r8 = r8[r13]     // Catch:{ all -> 0x001a }
            r8 = r8 & r6
            int r10 = r10 + r8
            if (r7 == r0) goto L_0x01dd
            if (r7 != r12) goto L_0x01f1
        L_0x01dd:
            if (r7 != r12) goto L_0x01e6
            float r2 = (float) r2     // Catch:{ all -> 0x001a }
            float r2 = r2 * r11
            r7 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r7
            int r2 = (int) r2     // Catch:{ all -> 0x001a }
        L_0x01e6:
            int r7 = r1.dpiX     // Catch:{ all -> 0x001a }
            if (r7 == 0) goto L_0x01ef
            int r7 = r1.dpiX     // Catch:{ all -> 0x001a }
            if (r7 == r2) goto L_0x01ef
            goto L_0x01f1
        L_0x01ef:
            r1.dpiX = r2     // Catch:{ all -> 0x001a }
        L_0x01f1:
            if (r10 == r0) goto L_0x01f5
            if (r10 != r12) goto L_0x02cd
        L_0x01f5:
            if (r10 != r12) goto L_0x01fe
            float r2 = (float) r9     // Catch:{ all -> 0x001a }
            float r2 = r2 * r11
            r7 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r7
            int r9 = (int) r2     // Catch:{ all -> 0x001a }
        L_0x01fe:
            int r2 = r1.dpiY     // Catch:{ all -> 0x001a }
            if (r2 == 0) goto L_0x0208
            int r2 = r1.dpiY     // Catch:{ all -> 0x001a }
            if (r2 == r9) goto L_0x0208
            goto L_0x02cd
        L_0x0208:
            r1.dpiY = r9     // Catch:{ all -> 0x001a }
            goto L_0x02cd
        L_0x020c:
            int r5 = marker(r8)     // Catch:{ all -> 0x001a }
            if (r5 != 0) goto L_0x02a6
            com.itextpdf.text.Utilities.skip(r3, r12)     // Catch:{ all -> 0x001a }
            int r5 = r3.read()     // Catch:{ all -> 0x001a }
            if (r5 != r14) goto L_0x0295
            int r0 = getShort(r3)     // Catch:{ all -> 0x001a }
            float r0 = (float) r0     // Catch:{ all -> 0x001a }
            r1.scaledHeight = r0     // Catch:{ all -> 0x001a }
            float r0 = r1.scaledHeight     // Catch:{ all -> 0x001a }
            r1.setTop(r0)     // Catch:{ all -> 0x001a }
            int r0 = getShort(r3)     // Catch:{ all -> 0x001a }
            float r0 = (float) r0     // Catch:{ all -> 0x001a }
            r1.scaledWidth = r0     // Catch:{ all -> 0x001a }
            float r0 = r1.scaledWidth     // Catch:{ all -> 0x001a }
            r1.setRight(r0)     // Catch:{ all -> 0x001a }
            int r0 = r3.read()     // Catch:{ all -> 0x001a }
            r1.colorspace = r0     // Catch:{ all -> 0x001a }
            r1.bpc = r14     // Catch:{ all -> 0x001a }
            if (r3 == 0) goto L_0x0240
            r3.close()
        L_0x0240:
            float r0 = r16.getWidth()
            r1.plainWidth = r0
            float r0 = r16.getHeight()
            r1.plainHeight = r0
            byte[][] r0 = r1.icc
            if (r0 == 0) goto L_0x0294
            r0 = 0
            r3 = 0
        L_0x0252:
            byte[][] r4 = r1.icc
            int r5 = r4.length
            if (r0 >= r5) goto L_0x026a
            r5 = r4[r0]
            if (r5 != 0) goto L_0x0262
            r5 = 0
            r2 = r5
            byte[][] r2 = (byte[][]) r2
            r1.icc = r2
            return
        L_0x0262:
            r4 = r4[r0]
            int r4 = r4.length
            int r4 = r4 - r2
            int r3 = r3 + r4
            int r0 = r0 + 1
            goto L_0x0252
        L_0x026a:
            byte[] r0 = new byte[r3]
            r3 = 0
            r7 = 0
        L_0x026e:
            byte[][] r4 = r1.icc
            int r5 = r4.length
            if (r7 >= r5) goto L_0x0286
            r5 = r4[r7]
            r4 = r4[r7]
            int r4 = r4.length
            int r4 = r4 - r2
            java.lang.System.arraycopy(r5, r2, r0, r3, r4)
            byte[][] r4 = r1.icc
            r4 = r4[r7]
            int r4 = r4.length
            int r4 = r4 - r2
            int r3 = r3 + r4
            int r7 = r7 + 1
            goto L_0x026e
        L_0x0286:
            int r2 = r1.colorspace     // Catch:{ IllegalArgumentException -> 0x028f }
            com.itextpdf.text.pdf.ICC_Profile r0 = com.itextpdf.text.pdf.ICC_Profile.getInstance(r0, r2)     // Catch:{ IllegalArgumentException -> 0x028f }
            r1.tagICC(r0)     // Catch:{ IllegalArgumentException -> 0x028f }
        L_0x028f:
            r2 = 0
            byte[][] r2 = (byte[][]) r2
            r1.icc = r2
        L_0x0294:
            return
        L_0x0295:
            com.itextpdf.text.BadElementException r2 = new com.itextpdf.text.BadElementException     // Catch:{ all -> 0x001a }
            java.lang.String r5 = "1.must.have.8.bits.per.component"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x001a }
            r6 = 0
            r0[r6] = r4     // Catch:{ all -> 0x001a }
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r5, (java.lang.Object[]) r0)     // Catch:{ all -> 0x001a }
            r2.<init>((java.lang.String) r0)     // Catch:{ all -> 0x001a }
            throw r2     // Catch:{ all -> 0x001a }
        L_0x02a6:
            r2 = 0
            if (r5 == r0) goto L_0x02b5
            if (r5 == r12) goto L_0x02b3
            int r5 = getShort(r3)     // Catch:{ all -> 0x001a }
            int r5 = r5 - r12
            com.itextpdf.text.Utilities.skip(r3, r5)     // Catch:{ all -> 0x001a }
        L_0x02b3:
            r5 = 0
            goto L_0x02cd
        L_0x02b5:
            com.itextpdf.text.BadElementException r2 = new com.itextpdf.text.BadElementException     // Catch:{ all -> 0x001a }
            java.lang.String r5 = "1.unsupported.jpeg.marker.2"
            java.lang.Object[] r6 = new java.lang.Object[r12]     // Catch:{ all -> 0x001a }
            r7 = 0
            r6[r7] = r4     // Catch:{ all -> 0x001a }
            java.lang.String r4 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x001a }
            r6[r0] = r4     // Catch:{ all -> 0x001a }
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r5, (java.lang.Object[]) r6)     // Catch:{ all -> 0x001a }
            r2.<init>((java.lang.String) r0)     // Catch:{ all -> 0x001a }
            throw r2     // Catch:{ all -> 0x001a }
        L_0x02cc:
            r2 = 0
        L_0x02cd:
            r7 = 0
            goto L_0x0039
        L_0x02d0:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x001a }
            java.lang.String r2 = "premature.eof.while.reading.jpg"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x001a }
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r2, (java.lang.Object[]) r4)     // Catch:{ all -> 0x001a }
            r0.<init>(r2)     // Catch:{ all -> 0x001a }
            throw r0     // Catch:{ all -> 0x001a }
        L_0x02df:
            com.itextpdf.text.BadElementException r2 = new com.itextpdf.text.BadElementException     // Catch:{ all -> 0x001a }
            java.lang.String r5 = "1.is.not.a.valid.jpeg.file"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x001a }
            r6 = 0
            r0[r6] = r4     // Catch:{ all -> 0x001a }
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r5, (java.lang.Object[]) r0)     // Catch:{ all -> 0x001a }
            r2.<init>((java.lang.String) r0)     // Catch:{ all -> 0x001a }
            throw r2     // Catch:{ all -> 0x001a }
        L_0x02f0:
            r0 = move-exception
            r2 = 0
        L_0x02f2:
            if (r2 == 0) goto L_0x02f7
            r2.close()
        L_0x02f7:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Jpeg.processParameters():void");
    }
}
