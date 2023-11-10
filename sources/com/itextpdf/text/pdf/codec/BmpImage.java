package com.itextpdf.text.pdf.codec;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.ImgRaw;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.BidiOrder;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class BmpImage {
    private static final int BI_BITFIELDS = 3;
    private static final int BI_RGB = 0;
    private static final int BI_RLE4 = 2;
    private static final int BI_RLE8 = 1;
    private static final int LCS_CALIBRATED_RGB = 0;
    private static final int LCS_CMYK = 2;
    private static final int LCS_sRGB = 1;
    private static final int VERSION_2_1_BIT = 0;
    private static final int VERSION_2_24_BIT = 3;
    private static final int VERSION_2_4_BIT = 1;
    private static final int VERSION_2_8_BIT = 2;
    private static final int VERSION_3_1_BIT = 4;
    private static final int VERSION_3_24_BIT = 7;
    private static final int VERSION_3_4_BIT = 5;
    private static final int VERSION_3_8_BIT = 6;
    private static final int VERSION_3_NT_16_BIT = 8;
    private static final int VERSION_3_NT_32_BIT = 9;
    private static final int VERSION_4_16_BIT = 13;
    private static final int VERSION_4_1_BIT = 10;
    private static final int VERSION_4_24_BIT = 14;
    private static final int VERSION_4_32_BIT = 15;
    private static final int VERSION_4_4_BIT = 11;
    private static final int VERSION_4_8_BIT = 12;
    private int alphaMask;
    private long bitmapFileSize;
    private long bitmapOffset;
    private int bitsPerPixel;
    private int blueMask;
    private long compression;
    private int greenMask;
    int height;
    private long imageSize;
    private int imageType;
    private InputStream inputStream;
    private boolean isBottomUp;
    private int numBands;
    private byte[] palette;
    public HashMap<String, Object> properties = new HashMap<>();
    private int redMask;
    int width;
    private long xPelsPerMeter;
    private long yPelsPerMeter;

    private int findMask(int i) {
        for (int i2 = 0; i2 < 32 && (i & 1) != 1; i2++) {
            i >>>= 1;
        }
        return i;
    }

    private int findShift(int i) {
        int i2 = 0;
        while (i2 < 32 && (i & 1) != 1) {
            i >>>= 1;
            i2++;
        }
        return i2;
    }

    BmpImage(InputStream inputStream2, boolean z, int i) throws IOException {
        this.bitmapFileSize = (long) i;
        this.bitmapOffset = 0;
        process(inputStream2, z);
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
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.BmpImage.getImage(java.net.URL):com.itextpdf.text.Image");
    }

    public static Image getImage(InputStream inputStream2) throws IOException {
        return getImage(inputStream2, false, 0);
    }

    public static Image getImage(InputStream inputStream2, boolean z, int i) throws IOException {
        BmpImage bmpImage = new BmpImage(inputStream2, z, i);
        try {
            Image image = bmpImage.getImage();
            image.setDpi((int) ((((double) bmpImage.xPelsPerMeter) * 0.0254d) + 0.5d), (int) ((((double) bmpImage.yPelsPerMeter) * 0.0254d) + 0.5d));
            image.setOriginalType(4);
            return image;
        } catch (BadElementException e) {
            throw new ExceptionConverter(e);
        }
    }

    public static Image getImage(String str) throws IOException {
        return getImage(Utilities.toURL(str));
    }

    public static Image getImage(byte[] bArr) throws IOException {
        Image image = getImage((InputStream) new ByteArrayInputStream(bArr));
        image.setOriginalData(bArr);
        return image;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x02f9, code lost:
        r1 = ((int) r11) * 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x02fc, code lost:
        r0.bitmapOffset = r2 + ((long) r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void process(java.io.InputStream r33, boolean r34) throws java.io.IOException {
        /*
            r32 = this;
            r0 = r32
            r1 = r33
            if (r34 != 0) goto L_0x0013
            boolean r2 = r1 instanceof java.io.BufferedInputStream
            if (r2 == 0) goto L_0x000b
            goto L_0x0013
        L_0x000b:
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream
            r2.<init>(r1)
            r0.inputStream = r2
            goto L_0x0015
        L_0x0013:
            r0.inputStream = r1
        L_0x0015:
            r1 = 0
            if (r34 != 0) goto L_0x0055
            java.io.InputStream r2 = r0.inputStream
            int r2 = r0.readUnsignedByte(r2)
            r3 = 66
            if (r2 != r3) goto L_0x0047
            java.io.InputStream r2 = r0.inputStream
            int r2 = r0.readUnsignedByte(r2)
            r3 = 77
            if (r2 != r3) goto L_0x0047
            java.io.InputStream r2 = r0.inputStream
            long r2 = r0.readDWord(r2)
            r0.bitmapFileSize = r2
            java.io.InputStream r2 = r0.inputStream
            r0.readWord(r2)
            java.io.InputStream r2 = r0.inputStream
            r0.readWord(r2)
            java.io.InputStream r2 = r0.inputStream
            long r2 = r0.readDWord(r2)
            r0.bitmapOffset = r2
            goto L_0x0055
        L_0x0047:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r3 = "invalid.magic.value.for.bmp.file"
            java.lang.String r1 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r1)
            r2.<init>(r1)
            throw r2
        L_0x0055:
            java.io.InputStream r2 = r0.inputStream
            long r2 = r0.readDWord(r2)
            r4 = 12
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x0072
            java.io.InputStream r4 = r0.inputStream
            int r4 = r0.readWord(r4)
            r0.width = r4
            java.io.InputStream r4 = r0.inputStream
            int r4 = r0.readWord(r4)
            r0.height = r4
            goto L_0x0082
        L_0x0072:
            java.io.InputStream r4 = r0.inputStream
            int r4 = r0.readLong(r4)
            r0.width = r4
            java.io.InputStream r4 = r0.inputStream
            int r4 = r0.readLong(r4)
            r0.height = r4
        L_0x0082:
            java.io.InputStream r4 = r0.inputStream
            int r4 = r0.readWord(r4)
            java.io.InputStream r5 = r0.inputStream
            int r5 = r0.readWord(r5)
            r0.bitsPerPixel = r5
            java.util.HashMap<java.lang.String, java.lang.Object> r5 = r0.properties
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.String r7 = "color_planes"
            r5.put(r7, r4)
            java.util.HashMap<java.lang.String, java.lang.Object> r4 = r0.properties
            int r5 = r0.bitsPerPixel
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.String r7 = "bits_per_pixel"
            r4.put(r7, r5)
            r4 = 3
            r0.numBands = r4
            long r7 = r0.bitmapOffset
            r9 = 0
            int r5 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r5 != 0) goto L_0x00b5
            r0.bitmapOffset = r2
        L_0x00b5:
            r7 = 24
            r11 = 14
            java.lang.String r14 = "bmp_version"
            r15 = 8
            r5 = 2
            r9 = 1
            r10 = 4
            if (r6 != 0) goto L_0x010d
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            java.lang.String r8 = "BMP v. 2.x"
            r6.put(r14, r8)
            int r6 = r0.bitsPerPixel
            if (r6 != r9) goto L_0x00d0
            r0.imageType = r1
            goto L_0x00de
        L_0x00d0:
            if (r6 != r10) goto L_0x00d5
            r0.imageType = r9
            goto L_0x00de
        L_0x00d5:
            if (r6 != r15) goto L_0x00da
            r0.imageType = r5
            goto L_0x00de
        L_0x00da:
            if (r6 != r7) goto L_0x00de
            r0.imageType = r4
        L_0x00de:
            long r6 = r0.bitmapOffset
            long r11 = r6 - r11
            long r11 = r11 - r2
            r16 = 3
            long r11 = r11 / r16
            int r8 = (int) r11
            int r8 = r8 * 3
            int r11 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r11 != 0) goto L_0x0108
            int r6 = r0.imageType
            if (r6 == 0) goto L_0x0102
            if (r6 == r9) goto L_0x00ff
            if (r6 == r5) goto L_0x00fc
            if (r6 == r4) goto L_0x00fa
            r6 = r8
            goto L_0x0103
        L_0x00fa:
            r6 = 0
            goto L_0x0103
        L_0x00fc:
            r6 = 768(0x300, float:1.076E-42)
            goto L_0x0103
        L_0x00ff:
            r6 = 48
            goto L_0x0103
        L_0x0102:
            r6 = 6
        L_0x0103:
            long r7 = (long) r6
            long r2 = r2 + r7
            r0.bitmapOffset = r2
            r8 = r6
        L_0x0108:
            r0.readPalette(r8)
            goto L_0x0582
        L_0x010d:
            java.io.InputStream r6 = r0.inputStream
            long r11 = r0.readDWord(r6)
            r0.compression = r11
            java.io.InputStream r6 = r0.inputStream
            long r11 = r0.readDWord(r6)
            r0.imageSize = r11
            java.io.InputStream r6 = r0.inputStream
            int r6 = r0.readLong(r6)
            long r11 = (long) r6
            r0.xPelsPerMeter = r11
            java.io.InputStream r6 = r0.inputStream
            int r6 = r0.readLong(r6)
            long r11 = (long) r6
            r0.yPelsPerMeter = r11
            java.io.InputStream r6 = r0.inputStream
            long r11 = r0.readDWord(r6)
            java.io.InputStream r6 = r0.inputStream
            long r20 = r0.readDWord(r6)
            long r7 = r0.compression
            int r8 = (int) r7
            java.lang.String r7 = "compression"
            if (r8 == 0) goto L_0x0161
            if (r8 == r9) goto L_0x0159
            if (r8 == r5) goto L_0x0151
            if (r8 == r4) goto L_0x0149
            goto L_0x0168
        L_0x0149:
            java.util.HashMap<java.lang.String, java.lang.Object> r8 = r0.properties
            java.lang.String r6 = "BI_BITFIELDS"
            r8.put(r7, r6)
            goto L_0x0168
        L_0x0151:
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            java.lang.String r8 = "BI_RLE4"
            r6.put(r7, r8)
            goto L_0x0168
        L_0x0159:
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            java.lang.String r8 = "BI_RLE8"
            r6.put(r7, r8)
            goto L_0x0168
        L_0x0161:
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            java.lang.String r8 = "BI_RGB"
            r6.put(r7, r8)
        L_0x0168:
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            long r7 = r0.xPelsPerMeter
            java.lang.Long r7 = java.lang.Long.valueOf(r7)
            java.lang.String r8 = "x_pixels_per_meter"
            r6.put(r8, r7)
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            long r7 = r0.yPelsPerMeter
            java.lang.Long r7 = java.lang.Long.valueOf(r7)
            java.lang.String r8 = "y_pixels_per_meter"
            r6.put(r8, r7)
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            java.lang.Long r7 = java.lang.Long.valueOf(r11)
            java.lang.String r8 = "colors_used"
            r6.put(r8, r7)
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            java.lang.Long r7 = java.lang.Long.valueOf(r20)
            java.lang.String r8 = "colors_important"
            r6.put(r8, r7)
            r6 = 40
            r20 = 52
            java.lang.String r8 = "alpha_mask"
            r22 = 56
            java.lang.String r1 = "blue_mask"
            java.lang.String r4 = "green_mask"
            java.lang.String r5 = "red_mask"
            int r24 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r24 == 0) goto L_0x03ca
            int r6 = (r2 > r20 ? 1 : (r2 == r20 ? 0 : -1))
            if (r6 == 0) goto L_0x03ca
            int r6 = (r2 > r22 ? 1 : (r2 == r22 ? 0 : -1))
            if (r6 != 0) goto L_0x01b6
            goto L_0x03ca
        L_0x01b6:
            r6 = 108(0x6c, double:5.34E-322)
            int r20 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r20 != 0) goto L_0x03bb
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            java.lang.String r7 = "BMP v. 4.x"
            r6.put(r14, r7)
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.redMask = r7
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.greenMask = r7
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.blueMask = r7
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.alphaMask = r7
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            java.io.InputStream r14 = r0.inputStream
            int r14 = r0.readLong(r14)
            java.io.InputStream r13 = r0.inputStream
            int r13 = r0.readLong(r13)
            java.io.InputStream r15 = r0.inputStream
            int r15 = r0.readLong(r15)
            java.io.InputStream r10 = r0.inputStream
            int r10 = r0.readLong(r10)
            java.io.InputStream r9 = r0.inputStream
            int r9 = r0.readLong(r9)
            r33 = r9
            java.io.InputStream r9 = r0.inputStream
            int r9 = r0.readLong(r9)
            r20 = r9
            java.io.InputStream r9 = r0.inputStream
            int r9 = r0.readLong(r9)
            r21 = r9
            java.io.InputStream r9 = r0.inputStream
            int r9 = r0.readLong(r9)
            r22 = r9
            java.io.InputStream r9 = r0.inputStream
            int r9 = r0.readLong(r9)
            r23 = r9
            java.io.InputStream r9 = r0.inputStream
            long r25 = r0.readDWord(r9)
            java.io.InputStream r9 = r0.inputStream
            long r27 = r0.readDWord(r9)
            java.io.InputStream r9 = r0.inputStream
            long r29 = r0.readDWord(r9)
            int r9 = r0.bitsPerPixel
            r31 = r10
            r10 = 1
            if (r9 != r10) goto L_0x024b
            r9 = 10
            r0.imageType = r9
            goto L_0x0299
        L_0x024b:
            r10 = 4
            if (r9 != r10) goto L_0x0253
            r9 = 11
            r0.imageType = r9
            goto L_0x0299
        L_0x0253:
            r10 = 8
            if (r9 != r10) goto L_0x025c
            r9 = 12
            r0.imageType = r9
            goto L_0x0299
        L_0x025c:
            r10 = 16
            if (r9 != r10) goto L_0x0276
            r9 = 13
            r0.imageType = r9
            long r9 = r0.compression
            int r10 = (int) r9
            if (r10 != 0) goto L_0x0299
            r9 = 31744(0x7c00, float:4.4483E-41)
            r0.redMask = r9
            r9 = 992(0x3e0, float:1.39E-42)
            r0.greenMask = r9
            r9 = 31
            r0.blueMask = r9
            goto L_0x0299
        L_0x0276:
            r10 = 24
            if (r9 != r10) goto L_0x027f
            r9 = 14
            r0.imageType = r9
            goto L_0x0299
        L_0x027f:
            r10 = 32
            if (r9 != r10) goto L_0x0299
            r9 = 15
            r0.imageType = r9
            long r9 = r0.compression
            int r10 = (int) r9
            if (r10 != 0) goto L_0x0299
            r9 = 16711680(0xff0000, float:2.3418052E-38)
            r0.redMask = r9
            r9 = 65280(0xff00, float:9.1477E-41)
            r0.greenMask = r9
            r9 = 255(0xff, float:3.57E-43)
            r0.blueMask = r9
        L_0x0299:
            java.util.HashMap<java.lang.String, java.lang.Object> r9 = r0.properties
            int r10 = r0.redMask
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r9.put(r5, r10)
            java.util.HashMap<java.lang.String, java.lang.Object> r5 = r0.properties
            int r9 = r0.greenMask
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            r5.put(r4, r9)
            java.util.HashMap<java.lang.String, java.lang.Object> r4 = r0.properties
            int r5 = r0.blueMask
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4.put(r1, r5)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            int r4 = r0.alphaMask
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r1.put(r8, r4)
            long r4 = r0.bitmapOffset
            r8 = 14
            long r8 = r4 - r8
            long r8 = r8 - r2
            r18 = 4
            long r8 = r8 / r18
            int r1 = (int) r8
            r8 = 4
            int r1 = r1 * 4
            int r9 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r9 != 0) goto L_0x0300
            int r1 = r0.imageType
            switch(r1) {
                case 10: goto L_0x02f1;
                case 11: goto L_0x02e8;
                case 12: goto L_0x02df;
                default: goto L_0x02dd;
            }
        L_0x02dd:
            r1 = 0
            goto L_0x02fc
        L_0x02df:
            r4 = 0
            int r1 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r1 != 0) goto L_0x02f9
            r11 = 256(0x100, double:1.265E-321)
            goto L_0x02f9
        L_0x02e8:
            r4 = 0
            int r1 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r1 != 0) goto L_0x02f9
            r11 = 16
            goto L_0x02f9
        L_0x02f1:
            r4 = 0
            int r1 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r1 != 0) goto L_0x02f9
            r11 = 2
        L_0x02f9:
            int r1 = (int) r11
            int r1 = r1 * 4
        L_0x02fc:
            long r4 = (long) r1
            long r2 = r2 + r4
            r0.bitmapOffset = r2
        L_0x0300:
            r0.readPalette(r1)
            int r1 = (int) r6
            java.lang.String r2 = "color_space"
            if (r1 == 0) goto L_0x0328
            r3 = 1
            if (r1 == r3) goto L_0x031f
            r3 = 2
            if (r1 == r3) goto L_0x0310
            goto L_0x0582
        L_0x0310:
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.String r3 = "LCS_CMYK"
            r1.put(r2, r3)
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Not implemented yet."
            r1.<init>(r2)
            throw r1
        L_0x031f:
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.String r3 = "LCS_sRGB"
            r1.put(r2, r3)
            goto L_0x0582
        L_0x0328:
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.String r3 = "LCS_CALIBRATED_RGB"
            r1.put(r2, r3)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Integer r2 = java.lang.Integer.valueOf(r14)
            java.lang.String r3 = "redX"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Integer r2 = java.lang.Integer.valueOf(r13)
            java.lang.String r3 = "redY"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Integer r2 = java.lang.Integer.valueOf(r15)
            java.lang.String r3 = "redZ"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Integer r2 = java.lang.Integer.valueOf(r31)
            java.lang.String r3 = "greenX"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Integer r2 = java.lang.Integer.valueOf(r33)
            java.lang.String r3 = "greenY"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Integer r2 = java.lang.Integer.valueOf(r20)
            java.lang.String r3 = "greenZ"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Integer r2 = java.lang.Integer.valueOf(r21)
            java.lang.String r3 = "blueX"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Integer r2 = java.lang.Integer.valueOf(r22)
            java.lang.String r3 = "blueY"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Integer r2 = java.lang.Integer.valueOf(r23)
            java.lang.String r3 = "blueZ"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Long r2 = java.lang.Long.valueOf(r25)
            java.lang.String r3 = "gamma_red"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Long r2 = java.lang.Long.valueOf(r27)
            java.lang.String r3 = "gamma_green"
            r1.put(r3, r2)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.Long r2 = java.lang.Long.valueOf(r29)
            java.lang.String r3 = "gamma_blue"
            r1.put(r3, r2)
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Not implemented yet."
            r1.<init>(r2)
            throw r1
        L_0x03bb:
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.String r2 = "BMP v. 5.x"
            r1.put(r14, r2)
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "BMP version 5 not implemented yet."
            r1.<init>(r2)
            throw r1
        L_0x03ca:
            long r9 = r0.compression
            int r7 = (int) r9
            if (r7 == 0) goto L_0x045b
            r9 = 1
            if (r7 == r9) goto L_0x045b
            r9 = 2
            if (r7 == r9) goto L_0x045b
            r9 = 3
            if (r7 != r9) goto L_0x0453
            int r6 = r0.bitsPerPixel
            r7 = 16
            if (r6 != r7) goto L_0x03e3
            r7 = 8
            r0.imageType = r7
            goto L_0x03eb
        L_0x03e3:
            r7 = 32
            if (r6 != r7) goto L_0x03eb
            r6 = 9
            r0.imageType = r6
        L_0x03eb:
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.redMask = r7
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.greenMask = r7
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.blueMask = r7
            int r6 = (r2 > r22 ? 1 : (r2 == r22 ? 0 : -1))
            if (r6 != 0) goto L_0x041c
            java.io.InputStream r2 = r0.inputStream
            long r2 = r0.readDWord(r2)
            int r3 = (int) r2
            r0.alphaMask = r3
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = r0.properties
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2.put(r8, r3)
        L_0x041c:
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = r0.properties
            int r3 = r0.redMask
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2.put(r5, r3)
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = r0.properties
            int r3 = r0.greenMask
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2.put(r4, r3)
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = r0.properties
            int r3 = r0.blueMask
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2.put(r1, r3)
            r1 = 0
            int r3 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r3 == 0) goto L_0x044a
            int r1 = (int) r11
            r2 = 4
            int r1 = r1 * 4
            r0.readPalette(r1)
        L_0x044a:
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.String r2 = "BMP v. 3.x NT"
            r1.put(r14, r2)
            goto L_0x0582
        L_0x0453:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Invalid compression specified in BMP file."
            r1.<init>(r2)
            throw r1
        L_0x045b:
            int r7 = r0.bitsPerPixel
            r9 = 1
            if (r7 != r9) goto L_0x0465
            r9 = 4
            r0.imageType = r9
            goto L_0x04e3
        L_0x0465:
            r9 = 4
            if (r7 != r9) goto L_0x046d
            r6 = 5
            r0.imageType = r6
            goto L_0x04e3
        L_0x046d:
            r9 = 8
            if (r7 != r9) goto L_0x0475
            r10 = 6
            r0.imageType = r10
            goto L_0x04e3
        L_0x0475:
            r6 = 24
            if (r7 != r6) goto L_0x047d
            r6 = 7
            r0.imageType = r6
            goto L_0x04e3
        L_0x047d:
            r6 = 16
            if (r7 != r6) goto L_0x04af
            r0.imageType = r9
            r6 = 31744(0x7c00, float:4.4483E-41)
            r0.redMask = r6
            r7 = 992(0x3e0, float:1.39E-42)
            r0.greenMask = r7
            r7 = 31
            r0.blueMask = r7
            java.util.HashMap<java.lang.String, java.lang.Object> r7 = r0.properties
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r7.put(r5, r6)
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            int r7 = r0.greenMask
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r6.put(r4, r7)
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            int r7 = r0.blueMask
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r6.put(r1, r7)
            goto L_0x04e3
        L_0x04af:
            r6 = 32
            if (r7 != r6) goto L_0x04e3
            r6 = 9
            r0.imageType = r6
            r6 = 16711680(0xff0000, float:2.3418052E-38)
            r0.redMask = r6
            r7 = 65280(0xff00, float:9.1477E-41)
            r0.greenMask = r7
            r7 = 255(0xff, float:3.57E-43)
            r0.blueMask = r7
            java.util.HashMap<java.lang.String, java.lang.Object> r7 = r0.properties
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r7.put(r5, r6)
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            int r7 = r0.greenMask
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r6.put(r4, r7)
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            int r7 = r0.blueMask
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r6.put(r1, r7)
        L_0x04e3:
            int r6 = (r2 > r20 ? 1 : (r2 == r20 ? 0 : -1))
            if (r6 < 0) goto L_0x0523
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.redMask = r7
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.greenMask = r7
            java.io.InputStream r6 = r0.inputStream
            long r6 = r0.readDWord(r6)
            int r7 = (int) r6
            r0.blueMask = r7
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r0.properties
            int r7 = r0.redMask
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r6.put(r5, r7)
            java.util.HashMap<java.lang.String, java.lang.Object> r5 = r0.properties
            int r6 = r0.greenMask
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r5.put(r4, r6)
            java.util.HashMap<java.lang.String, java.lang.Object> r4 = r0.properties
            int r5 = r0.blueMask
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4.put(r1, r5)
        L_0x0523:
            int r1 = (r2 > r22 ? 1 : (r2 == r22 ? 0 : -1))
            if (r1 != 0) goto L_0x0539
            java.io.InputStream r1 = r0.inputStream
            long r4 = r0.readDWord(r1)
            int r1 = (int) r4
            r0.alphaMask = r1
            java.util.HashMap<java.lang.String, java.lang.Object> r4 = r0.properties
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r4.put(r8, r1)
        L_0x0539:
            long r4 = r0.bitmapOffset
            r6 = 14
            long r6 = r4 - r6
            long r6 = r6 - r2
            r8 = 4
            long r6 = r6 / r8
            int r1 = (int) r6
            r6 = 4
            int r1 = r1 * 4
            int r7 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r7 != 0) goto L_0x0578
            int r1 = r0.imageType
            if (r1 == r6) goto L_0x0569
            r4 = 5
            if (r1 == r4) goto L_0x0560
            r4 = 6
            if (r1 == r4) goto L_0x0557
            r1 = 0
            goto L_0x0574
        L_0x0557:
            r4 = 0
            int r1 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r1 != 0) goto L_0x0571
            r11 = 256(0x100, double:1.265E-321)
            goto L_0x0571
        L_0x0560:
            r4 = 0
            int r1 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r1 != 0) goto L_0x0571
            r11 = 16
            goto L_0x0571
        L_0x0569:
            r4 = 0
            int r1 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r1 != 0) goto L_0x0571
            r11 = 2
        L_0x0571:
            int r1 = (int) r11
            int r1 = r1 * 4
        L_0x0574:
            long r4 = (long) r1
            long r2 = r2 + r4
            r0.bitmapOffset = r2
        L_0x0578:
            r0.readPalette(r1)
            java.util.HashMap<java.lang.String, java.lang.Object> r1 = r0.properties
            java.lang.String r2 = "BMP v. 3.x"
            r1.put(r14, r2)
        L_0x0582:
            int r1 = r0.height
            if (r1 <= 0) goto L_0x058b
            r2 = 1
            r0.isBottomUp = r2
            r3 = 0
            goto L_0x0595
        L_0x058b:
            r2 = 1
            r3 = 0
            r0.isBottomUp = r3
            int r1 = java.lang.Math.abs(r1)
            r0.height = r1
        L_0x0595:
            int r1 = r0.bitsPerPixel
            if (r1 == r2) goto L_0x05bc
            r2 = 4
            if (r1 == r2) goto L_0x05bc
            r2 = 8
            if (r1 != r2) goto L_0x05a1
            goto L_0x05bc
        L_0x05a1:
            r2 = 16
            if (r1 != r2) goto L_0x05aa
            r2 = 3
            r0.numBands = r2
            goto L_0x061b
        L_0x05aa:
            r2 = 3
            r3 = 32
            if (r1 != r3) goto L_0x05b9
            int r1 = r0.alphaMask
            if (r1 != 0) goto L_0x05b5
            r4 = 3
            goto L_0x05b6
        L_0x05b5:
            r4 = 4
        L_0x05b6:
            r0.numBands = r4
            goto L_0x061b
        L_0x05b9:
            r0.numBands = r2
            goto L_0x061b
        L_0x05bc:
            r1 = 1
            r0.numBands = r1
            int r2 = r0.imageType
            r4 = 256(0x100, float:3.59E-43)
            if (r2 == 0) goto L_0x05f3
            if (r2 == r1) goto L_0x05f3
            r1 = 2
            if (r2 != r1) goto L_0x05cb
            goto L_0x05f3
        L_0x05cb:
            byte[] r1 = r0.palette
            int r1 = r1.length
            r2 = 4
            int r1 = r1 / r2
            if (r1 <= r4) goto L_0x05d3
            goto L_0x05d4
        L_0x05d3:
            r4 = r1
        L_0x05d4:
            byte[] r1 = new byte[r4]
            byte[] r2 = new byte[r4]
            byte[] r5 = new byte[r4]
        L_0x05da:
            if (r3 >= r4) goto L_0x061b
            int r6 = r3 * 4
            byte[] r7 = r0.palette
            byte r8 = r7[r6]
            r5[r3] = r8
            int r8 = r6 + 1
            byte r8 = r7[r8]
            r2[r3] = r8
            r8 = 2
            int r6 = r6 + r8
            byte r6 = r7[r6]
            r1[r3] = r6
            int r3 = r3 + 1
            goto L_0x05da
        L_0x05f3:
            byte[] r1 = r0.palette
            int r1 = r1.length
            r2 = 3
            int r1 = r1 / r2
            if (r1 <= r4) goto L_0x05fb
            goto L_0x05fc
        L_0x05fb:
            r4 = r1
        L_0x05fc:
            byte[] r1 = new byte[r4]
            byte[] r2 = new byte[r4]
            byte[] r5 = new byte[r4]
        L_0x0602:
            if (r3 >= r4) goto L_0x061b
            int r6 = r3 * 3
            byte[] r7 = r0.palette
            byte r8 = r7[r6]
            r5[r3] = r8
            int r8 = r6 + 1
            byte r8 = r7[r8]
            r2[r3] = r8
            r8 = 2
            int r6 = r6 + r8
            byte r6 = r7[r6]
            r1[r3] = r6
            int r3 = r3 + 1
            goto L_0x0602
        L_0x061b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.BmpImage.process(java.io.InputStream, boolean):void");
    }

    private byte[] getPalette(int i) {
        byte[] bArr = this.palette;
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[((bArr.length / i) * 3)];
        int length = bArr.length / i;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * i;
            int i4 = i2 * 3;
            byte[] bArr3 = this.palette;
            int i5 = i3 + 1;
            bArr2[i4 + 2] = bArr3[i3];
            bArr2[i4 + 1] = bArr3[i5];
            bArr2[i4] = bArr3[i5 + 1];
        }
        return bArr2;
    }

    private Image getImage() throws IOException, BadElementException {
        switch (this.imageType) {
            case 0:
                return read1Bit(3);
            case 1:
                return read4Bit(3);
            case 2:
                return read8Bit(3);
            case 3:
                byte[] bArr = new byte[(this.width * this.height * 3)];
                read24Bit(bArr);
                return new ImgRaw(this.width, this.height, 3, 8, bArr);
            case 4:
                return read1Bit(4);
            case 5:
                int i = (int) this.compression;
                if (i == 0) {
                    return read4Bit(4);
                }
                if (i == 2) {
                    return readRLE4();
                }
                throw new RuntimeException("Invalid compression specified for BMP file.");
            case 6:
                int i2 = (int) this.compression;
                if (i2 == 0) {
                    return read8Bit(4);
                }
                if (i2 == 1) {
                    return readRLE8();
                }
                throw new RuntimeException("Invalid compression specified for BMP file.");
            case 7:
                byte[] bArr2 = new byte[(this.width * this.height * 3)];
                read24Bit(bArr2);
                return new ImgRaw(this.width, this.height, 3, 8, bArr2);
            case 8:
                return read1632Bit(false);
            case 9:
                return read1632Bit(true);
            case 10:
                return read1Bit(4);
            case 11:
                int i3 = (int) this.compression;
                if (i3 == 0) {
                    return read4Bit(4);
                }
                if (i3 == 2) {
                    return readRLE4();
                }
                throw new RuntimeException("Invalid compression specified for BMP file.");
            case 12:
                int i4 = (int) this.compression;
                if (i4 == 0) {
                    return read8Bit(4);
                }
                if (i4 == 1) {
                    return readRLE8();
                }
                throw new RuntimeException("Invalid compression specified for BMP file.");
            case 13:
                return read1632Bit(false);
            case 14:
                byte[] bArr3 = new byte[(this.width * this.height * 3)];
                read24Bit(bArr3);
                return new ImgRaw(this.width, this.height, 3, 8, bArr3);
            case 15:
                return read1632Bit(true);
            default:
                return null;
        }
    }

    private Image indexedModel(byte[] bArr, int i, int i2) throws BadElementException {
        ImgRaw imgRaw = new ImgRaw(this.width, this.height, 1, i, bArr);
        PdfArray pdfArray = new PdfArray();
        pdfArray.add((PdfObject) PdfName.INDEXED);
        pdfArray.add((PdfObject) PdfName.DEVICERGB);
        byte[] palette2 = getPalette(i2);
        pdfArray.add((PdfObject) new PdfNumber((palette2.length / 3) - 1));
        pdfArray.add((PdfObject) new PdfString(palette2));
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.COLORSPACE, pdfArray);
        imgRaw.setAdditional(pdfDictionary);
        return imgRaw;
    }

    private void readPalette(int i) throws IOException {
        if (i != 0) {
            this.palette = new byte[i];
            int i2 = 0;
            while (i2 < i) {
                int read = this.inputStream.read(this.palette, i2, i - i2);
                if (read >= 0) {
                    i2 += read;
                } else {
                    throw new RuntimeException(MessageLocalization.getComposedMessage("incomplete.palette", new Object[0]));
                }
            }
            this.properties.put("palette", this.palette);
        }
    }

    private Image read1Bit(int i) throws IOException, BadElementException {
        int i2 = this.width;
        byte[] bArr = new byte[(((i2 + 7) / 8) * this.height)];
        int ceil = (int) Math.ceil(((double) i2) / 8.0d);
        int i3 = ceil % 4;
        int i4 = 0;
        int i5 = (i3 != 0 ? 4 - i3 : 0) + ceil;
        int i6 = this.height * i5;
        byte[] bArr2 = new byte[i6];
        int i7 = 0;
        while (i7 < i6) {
            i7 += this.inputStream.read(bArr2, i7, i6 - i7);
        }
        if (this.isBottomUp) {
            while (i4 < this.height) {
                int i8 = i4 + 1;
                System.arraycopy(bArr2, i6 - (i8 * i5), bArr, i4 * ceil, ceil);
                i4 = i8;
            }
        } else {
            while (i4 < this.height) {
                System.arraycopy(bArr2, i4 * i5, bArr, i4 * ceil, ceil);
                i4++;
            }
        }
        return indexedModel(bArr, 1, i);
    }

    private Image read4Bit(int i) throws IOException, BadElementException {
        int i2 = this.width;
        byte[] bArr = new byte[(((i2 + 1) / 2) * this.height)];
        int ceil = (int) Math.ceil(((double) i2) / 2.0d);
        int i3 = ceil % 4;
        int i4 = 0;
        int i5 = (i3 != 0 ? 4 - i3 : 0) + ceil;
        int i6 = this.height * i5;
        byte[] bArr2 = new byte[i6];
        int i7 = 0;
        while (i7 < i6) {
            i7 += this.inputStream.read(bArr2, i7, i6 - i7);
        }
        if (this.isBottomUp) {
            while (i4 < this.height) {
                int i8 = i4 + 1;
                System.arraycopy(bArr2, i6 - (i8 * i5), bArr, i4 * ceil, ceil);
                i4 = i8;
            }
        } else {
            while (i4 < this.height) {
                System.arraycopy(bArr2, i4 * i5, bArr, i4 * ceil, ceil);
                i4++;
            }
        }
        return indexedModel(bArr, 4, i);
    }

    private Image read8Bit(int i) throws IOException, BadElementException {
        int i2 = this.width;
        byte[] bArr = new byte[(this.height * i2)];
        int i3 = i2 * 8;
        int i4 = 0;
        int ceil = i3 % 32 != 0 ? (int) Math.ceil(((double) ((((i3 / 32) + 1) * 32) - i3)) / 8.0d) : 0;
        int i5 = (this.width + ceil) * this.height;
        byte[] bArr2 = new byte[i5];
        int i6 = 0;
        while (i6 < i5) {
            i6 += this.inputStream.read(bArr2, i6, i5 - i6);
        }
        if (this.isBottomUp) {
            while (i4 < this.height) {
                int i7 = i4 + 1;
                int i8 = this.width;
                System.arraycopy(bArr2, i5 - ((i8 + ceil) * i7), bArr, i4 * i8, i8);
                i4 = i7;
            }
        } else {
            while (i4 < this.height) {
                int i9 = this.width;
                System.arraycopy(bArr2, (i9 + ceil) * i4, bArr, i4 * i9, i9);
                i4++;
            }
        }
        return indexedModel(bArr, 8, i);
    }

    private void read24Bit(byte[] bArr) {
        int i = this.width * 24;
        int ceil = i % 32 != 0 ? (int) Math.ceil(((double) ((((i / 32) + 1) * 32) - i)) / 8.0d) : 0;
        int i2 = (((this.width * 3) + 3) / 4) * 4 * this.height;
        byte[] bArr2 = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            try {
                int read = this.inputStream.read(bArr2, i3, i2 - i3);
                if (read < 0) {
                    break;
                }
                i3 += read;
            } catch (IOException e) {
                throw new ExceptionConverter(e);
            }
        }
        if (this.isBottomUp) {
            int i4 = ((this.width * this.height) * 3) - 1;
            int i5 = -ceil;
            int i6 = 0;
            while (i6 < this.height) {
                i6++;
                int i7 = (i4 - ((this.width * i6) * 3)) + 1;
                i5 += ceil;
                for (int i8 = 0; i8 < this.width; i8++) {
                    int i9 = i5 + 1;
                    bArr[i7 + 2] = bArr2[i5];
                    int i10 = i9 + 1;
                    bArr[i7 + 1] = bArr2[i9];
                    i5 = i10 + 1;
                    bArr[i7] = bArr2[i10];
                    i7 += 3;
                }
            }
            return;
        }
        int i11 = -ceil;
        int i12 = 0;
        for (int i13 = 0; i13 < this.height; i13++) {
            i11 += ceil;
            for (int i14 = 0; i14 < this.width; i14++) {
                int i15 = i11 + 1;
                bArr[i12 + 2] = bArr2[i11];
                int i16 = i15 + 1;
                bArr[i12 + 1] = bArr2[i15];
                i11 = i16 + 1;
                bArr[i12] = bArr2[i16];
                i12 += 3;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.itextpdf.text.Image read1632Bit(boolean r21) throws java.io.IOException, com.itextpdf.text.BadElementException {
        /*
            r20 = this;
            r0 = r20
            int r1 = r0.redMask
            int r1 = r0.findMask(r1)
            int r2 = r0.redMask
            int r2 = r0.findShift(r2)
            int r3 = r1 + 1
            int r4 = r0.greenMask
            int r4 = r0.findMask(r4)
            int r5 = r0.greenMask
            int r5 = r0.findShift(r5)
            int r6 = r4 + 1
            int r7 = r0.blueMask
            int r7 = r0.findMask(r7)
            int r8 = r0.blueMask
            int r8 = r0.findShift(r8)
            int r9 = r7 + 1
            int r10 = r0.width
            int r11 = r0.height
            int r11 = r11 * r10
            int r11 = r11 * 3
            byte[] r11 = new byte[r11]
            if (r21 != 0) goto L_0x004f
            int r10 = r10 * 16
            int r13 = r10 % 32
            if (r13 == 0) goto L_0x004f
            int r13 = r10 / 32
            int r13 = r13 + 1
            int r13 = r13 * 32
            int r13 = r13 - r10
            double r13 = (double) r13
            r15 = 4620693217682128896(0x4020000000000000, double:8.0)
            double r13 = r13 / r15
            double r13 = java.lang.Math.ceil(r13)
            int r10 = (int) r13
            goto L_0x0050
        L_0x004f:
            r10 = 0
        L_0x0050:
            long r13 = r0.imageSize
            int r14 = (int) r13
            boolean r13 = r0.isBottomUp
            if (r13 == 0) goto L_0x00bb
            int r13 = r0.height
            int r13 = r13 + -1
        L_0x005b:
            if (r13 < 0) goto L_0x0111
            int r14 = r0.width
            int r14 = r14 * 3
            int r14 = r14 * r13
            r15 = 0
        L_0x0064:
            int r12 = r0.width
            if (r15 >= r12) goto L_0x00a7
            if (r21 == 0) goto L_0x0074
            java.io.InputStream r12 = r0.inputStream
            r17 = r13
            long r12 = r0.readDWord(r12)
            int r13 = (int) r12
            goto L_0x007c
        L_0x0074:
            r17 = r13
            java.io.InputStream r12 = r0.inputStream
            int r13 = r0.readWord(r12)
        L_0x007c:
            int r12 = r14 + 1
            int r18 = r13 >>> r2
            r19 = r2
            r2 = r18 & r1
            int r2 = r2 * 256
            int r2 = r2 / r3
            byte r2 = (byte) r2
            r11[r14] = r2
            int r2 = r12 + 1
            int r14 = r13 >>> r5
            r14 = r14 & r4
            int r14 = r14 * 256
            int r14 = r14 / r6
            byte r14 = (byte) r14
            r11[r12] = r14
            int r14 = r2 + 1
            int r12 = r13 >>> r8
            r12 = r12 & r7
            int r12 = r12 * 256
            int r12 = r12 / r9
            byte r12 = (byte) r12
            r11[r2] = r12
            int r15 = r15 + 1
            r13 = r17
            r2 = r19
            goto L_0x0064
        L_0x00a7:
            r19 = r2
            r17 = r13
            r2 = 0
        L_0x00ac:
            if (r2 >= r10) goto L_0x00b6
            java.io.InputStream r12 = r0.inputStream
            r12.read()
            int r2 = r2 + 1
            goto L_0x00ac
        L_0x00b6:
            int r13 = r17 + -1
            r2 = r19
            goto L_0x005b
        L_0x00bb:
            r19 = r2
            r2 = 0
            r12 = 0
        L_0x00bf:
            int r13 = r0.height
            if (r2 >= r13) goto L_0x0111
            r13 = 0
        L_0x00c4:
            int r14 = r0.width
            if (r13 >= r14) goto L_0x0101
            if (r21 == 0) goto L_0x00d2
            java.io.InputStream r14 = r0.inputStream
            long r14 = r0.readDWord(r14)
            int r15 = (int) r14
            goto L_0x00d8
        L_0x00d2:
            java.io.InputStream r14 = r0.inputStream
            int r15 = r0.readWord(r14)
        L_0x00d8:
            int r14 = r12 + 1
            int r17 = r15 >>> r19
            r18 = r2
            r2 = r17 & r1
            int r2 = r2 * 256
            int r2 = r2 / r3
            byte r2 = (byte) r2
            r11[r12] = r2
            int r2 = r14 + 1
            int r12 = r15 >>> r5
            r12 = r12 & r4
            int r12 = r12 * 256
            int r12 = r12 / r6
            byte r12 = (byte) r12
            r11[r14] = r12
            int r12 = r2 + 1
            int r14 = r15 >>> r8
            r14 = r14 & r7
            int r14 = r14 * 256
            int r14 = r14 / r9
            byte r14 = (byte) r14
            r11[r2] = r14
            int r13 = r13 + 1
            r2 = r18
            goto L_0x00c4
        L_0x0101:
            r18 = r2
            r2 = 0
        L_0x0104:
            if (r2 >= r10) goto L_0x010e
            java.io.InputStream r13 = r0.inputStream
            r13.read()
            int r2 = r2 + 1
            goto L_0x0104
        L_0x010e:
            int r2 = r18 + 1
            goto L_0x00bf
        L_0x0111:
            com.itextpdf.text.ImgRaw r1 = new com.itextpdf.text.ImgRaw
            int r13 = r0.width
            int r14 = r0.height
            r15 = 3
            r16 = 8
            r12 = r1
            r17 = r11
            r12.<init>(r13, r14, r15, r16, r17)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.BmpImage.read1632Bit(boolean):com.itextpdf.text.Image");
    }

    private Image readRLE8() throws IOException, BadElementException {
        int i = (int) this.imageSize;
        if (i == 0) {
            i = (int) (this.bitmapFileSize - this.bitmapOffset);
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        int i3 = 0;
        while (i3 < i) {
            i3 += this.inputStream.read(bArr, i3, i - i3);
        }
        byte[] decodeRLE = decodeRLE(true, bArr);
        int i4 = this.width;
        int i5 = this.height * i4;
        if (this.isBottomUp) {
            byte[] bArr2 = new byte[decodeRLE.length];
            while (i2 < this.height) {
                int i6 = i2 + 1;
                System.arraycopy(decodeRLE, i5 - (i6 * i4), bArr2, i2 * i4, i4);
                i2 = i6;
            }
            decodeRLE = bArr2;
        }
        return indexedModel(decodeRLE, 8, 4);
    }

    private Image readRLE4() throws IOException, BadElementException {
        int i = (int) this.imageSize;
        if (i == 0) {
            i = (int) (this.bitmapFileSize - this.bitmapOffset);
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            i2 += this.inputStream.read(bArr, i2, i - i2);
        }
        byte[] decodeRLE = decodeRLE(false, bArr);
        if (this.isBottomUp) {
            int i3 = this.width;
            int i4 = this.height;
            byte[] bArr2 = new byte[(i3 * i4)];
            int i5 = 0;
            for (int i6 = i4 - 1; i6 >= 0; i6--) {
                int i7 = this.width;
                int i8 = i6 * i7;
                int i9 = i7 + i5;
                while (i5 != i9) {
                    bArr2[i5] = decodeRLE[i8];
                    i5++;
                    i8++;
                }
            }
            decodeRLE = bArr2;
        }
        int i10 = (this.width + 1) / 2;
        byte[] bArr3 = new byte[(this.height * i10)];
        int i11 = 0;
        int i12 = 0;
        for (int i13 = 0; i13 < this.height; i13++) {
            for (int i14 = 0; i14 < this.width; i14++) {
                if ((i14 & 1) == 0) {
                    bArr3[(i14 / 2) + i12] = (byte) (decodeRLE[i11] << 4);
                    i11++;
                } else {
                    int i15 = (i14 / 2) + i12;
                    bArr3[i15] = (byte) (((byte) (decodeRLE[i11] & BidiOrder.f518B)) | bArr3[i15]);
                    i11++;
                }
            }
            i12 += i10;
        }
        return indexedModel(bArr3, 4, 4);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] decodeRLE(boolean r17, byte[] r18) {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            int r2 = r0.width
            int r3 = r0.height
            int r2 = r2 * r3
            byte[] r2 = new byte[r2]
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
        L_0x0011:
            int r8 = r0.height     // Catch:{ RuntimeException -> 0x00c7 }
            if (r4 >= r8) goto L_0x00c7
            int r8 = r1.length     // Catch:{ RuntimeException -> 0x00c7 }
            if (r5 >= r8) goto L_0x00c7
            int r8 = r5 + 1
            byte r5 = r1[r5]     // Catch:{ RuntimeException -> 0x00c7 }
            r5 = r5 & 255(0xff, float:3.57E-43)
            r9 = 1
            if (r5 == 0) goto L_0x004f
            int r10 = r8 + 1
            byte r8 = r1[r8]     // Catch:{ RuntimeException -> 0x00c7 }
            r8 = r8 & 255(0xff, float:3.57E-43)
            if (r17 == 0) goto L_0x0035
            r9 = r5
        L_0x002a:
            if (r9 == 0) goto L_0x004c
            int r11 = r7 + 1
            byte r12 = (byte) r8     // Catch:{ RuntimeException -> 0x00c7 }
            r2[r7] = r12     // Catch:{ RuntimeException -> 0x00c7 }
            int r9 = r9 + -1
            r7 = r11
            goto L_0x002a
        L_0x0035:
            r11 = 0
        L_0x0036:
            if (r11 >= r5) goto L_0x004c
            int r12 = r7 + 1
            r13 = r11 & 1
            if (r13 != r9) goto L_0x0041
            r13 = r8 & 15
            goto L_0x0045
        L_0x0041:
            int r13 = r8 >>> 4
            r13 = r13 & 15
        L_0x0045:
            byte r13 = (byte) r13     // Catch:{ RuntimeException -> 0x00c7 }
            r2[r7] = r13     // Catch:{ RuntimeException -> 0x00c7 }
            int r11 = r11 + 1
            r7 = r12
            goto L_0x0036
        L_0x004c:
            int r6 = r6 + r5
            r5 = r10
            goto L_0x0011
        L_0x004f:
            int r5 = r8 + 1
            byte r8 = r1[r8]     // Catch:{ RuntimeException -> 0x00c7 }
            r8 = r8 & 255(0xff, float:3.57E-43)
            if (r8 != r9) goto L_0x0059
            goto L_0x00c7
        L_0x0059:
            if (r8 == 0) goto L_0x00bd
            r10 = 2
            if (r8 == r10) goto L_0x00a8
            if (r17 == 0) goto L_0x0073
            r11 = r8
        L_0x0061:
            if (r11 == 0) goto L_0x0096
            int r12 = r7 + 1
            int r13 = r5 + 1
            byte r5 = r1[r5]     // Catch:{ RuntimeException -> 0x00c7 }
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte r5 = (byte) r5     // Catch:{ RuntimeException -> 0x00c7 }
            r2[r7] = r5     // Catch:{ RuntimeException -> 0x00c7 }
            int r11 = r11 + -1
            r7 = r12
            r5 = r13
            goto L_0x0061
        L_0x0073:
            r11 = 0
            r12 = 0
        L_0x0075:
            if (r11 >= r8) goto L_0x0096
            r13 = r11 & 1
            if (r13 != 0) goto L_0x0084
            int r12 = r5 + 1
            byte r5 = r1[r5]     // Catch:{ RuntimeException -> 0x00c7 }
            r5 = r5 & 255(0xff, float:3.57E-43)
            r15 = r12
            r12 = r5
            r5 = r15
        L_0x0084:
            int r14 = r7 + 1
            if (r13 != r9) goto L_0x008b
            r13 = r12 & 15
            goto L_0x008f
        L_0x008b:
            int r13 = r12 >>> 4
            r13 = r13 & 15
        L_0x008f:
            byte r13 = (byte) r13     // Catch:{ RuntimeException -> 0x00c7 }
            r2[r7] = r13     // Catch:{ RuntimeException -> 0x00c7 }
            int r11 = r11 + 1
            r7 = r14
            goto L_0x0075
        L_0x0096:
            int r6 = r6 + r8
            if (r17 == 0) goto L_0x009e
            r8 = r8 & 1
            if (r8 != r9) goto L_0x0011
            goto L_0x00a4
        L_0x009e:
            r8 = r8 & 3
            if (r8 == r9) goto L_0x00a4
            if (r8 != r10) goto L_0x0011
        L_0x00a4:
            int r5 = r5 + 1
            goto L_0x0011
        L_0x00a8:
            int r7 = r5 + 1
            byte r5 = r1[r5]     // Catch:{ RuntimeException -> 0x00c7 }
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r6 = r6 + r5
            int r5 = r7 + 1
            byte r7 = r1[r7]     // Catch:{ RuntimeException -> 0x00c7 }
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r4 = r4 + r7
            int r7 = r0.width     // Catch:{ RuntimeException -> 0x00c7 }
            int r7 = r7 * r4
            int r7 = r7 + r6
            goto L_0x0011
        L_0x00bd:
            int r4 = r4 + 1
            int r6 = r0.width     // Catch:{ RuntimeException -> 0x00c7 }
            int r6 = r6 * r4
            r7 = r6
            r6 = 0
            goto L_0x0011
        L_0x00c7:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.BmpImage.decodeRLE(boolean, byte[]):byte[]");
    }

    private int readUnsignedByte(InputStream inputStream2) throws IOException {
        return inputStream2.read() & 255;
    }

    private int readUnsignedShort(InputStream inputStream2) throws IOException {
        return ((readUnsignedByte(inputStream2) << 8) | readUnsignedByte(inputStream2)) & 65535;
    }

    private int readShort(InputStream inputStream2) throws IOException {
        return (readUnsignedByte(inputStream2) << 8) | readUnsignedByte(inputStream2);
    }

    private int readWord(InputStream inputStream2) throws IOException {
        return readUnsignedShort(inputStream2);
    }

    private long readUnsignedInt(InputStream inputStream2) throws IOException {
        int readUnsignedByte = readUnsignedByte(inputStream2);
        int readUnsignedByte2 = readUnsignedByte(inputStream2);
        return ((long) ((readUnsignedByte(inputStream2) << 24) | (readUnsignedByte(inputStream2) << 16) | (readUnsignedByte2 << 8) | readUnsignedByte)) & -1;
    }

    private int readInt(InputStream inputStream2) throws IOException {
        int readUnsignedByte = readUnsignedByte(inputStream2);
        int readUnsignedByte2 = readUnsignedByte(inputStream2);
        return (readUnsignedByte(inputStream2) << 24) | (readUnsignedByte(inputStream2) << 16) | (readUnsignedByte2 << 8) | readUnsignedByte;
    }

    private long readDWord(InputStream inputStream2) throws IOException {
        return readUnsignedInt(inputStream2);
    }

    private int readLong(InputStream inputStream2) throws IOException {
        return readInt(inputStream2);
    }
}
