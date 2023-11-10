package com.itextpdf.text.pdf.codec;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.ImgRaw;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfString;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import kotlin.UByte;

public class GifImage {
    protected static final int MaxStackSize = 4096;
    protected int bgColor;
    protected int bgIndex;
    protected byte[] block;
    protected int blockSize;
    protected int delay;
    protected int dispose;
    protected ArrayList<GifFrame> frames;
    protected byte[] fromData;
    protected URL fromUrl;
    protected boolean gctFlag;
    protected int height;

    /* renamed from: ih */
    protected int f701ih;

    /* renamed from: in */
    protected DataInputStream f702in;
    protected boolean interlace;

    /* renamed from: iw */
    protected int f703iw;

    /* renamed from: ix */
    protected int f704ix;

    /* renamed from: iy */
    protected int f705iy;
    protected boolean lctFlag;
    protected int lctSize;
    protected int m_bpc;
    protected byte[] m_curr_table;
    protected int m_gbpc;
    protected byte[] m_global_table;
    protected int m_line_stride;
    protected byte[] m_local_table;
    protected byte[] m_out;
    protected int pixelAspect;
    protected byte[] pixelStack;
    protected byte[] pixels;
    protected short[] prefix;
    protected byte[] suffix;
    protected int transIndex;
    protected boolean transparency;
    protected int width;

    protected static int newBpc(int i) {
        if (!(i == 1 || i == 2)) {
            if (i == 3) {
                return 4;
            }
            if (i != 4) {
                return 8;
            }
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public void resetFrame() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GifImage(java.net.URL r7) throws java.io.IOException {
        /*
            r6 = this;
            r6.<init>()
            r0 = 256(0x100, float:3.59E-43)
            byte[] r0 = new byte[r0]
            r6.block = r0
            r0 = 0
            r6.blockSize = r0
            r6.dispose = r0
            r6.transparency = r0
            r6.delay = r0
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r6.frames = r1
            r6.fromUrl = r7
            java.io.InputStream r7 = r7.openStream()     // Catch:{ all -> 0x0053 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0051 }
            r1.<init>()     // Catch:{ all -> 0x0051 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x0051 }
        L_0x0028:
            int r3 = r7.read(r2)     // Catch:{ all -> 0x0051 }
            r4 = -1
            if (r3 == r4) goto L_0x0033
            r1.write(r2, r0, r3)     // Catch:{ all -> 0x0051 }
            goto L_0x0028
        L_0x0033:
            r7.close()     // Catch:{ all -> 0x0051 }
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0051 }
            byte[] r2 = r1.toByteArray()     // Catch:{ all -> 0x0051 }
            r0.<init>(r2)     // Catch:{ all -> 0x0051 }
            r1.flush()     // Catch:{ all -> 0x004c }
            r1.close()     // Catch:{ all -> 0x004c }
            r6.process(r0)     // Catch:{ all -> 0x004c }
            r0.close()
            return
        L_0x004c:
            r7 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x0055
        L_0x0051:
            r0 = move-exception
            goto L_0x0055
        L_0x0053:
            r0 = move-exception
            r7 = 0
        L_0x0055:
            if (r7 == 0) goto L_0x005a
            r7.close()
        L_0x005a:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.GifImage.<init>(java.net.URL):void");
    }

    public GifImage(String str) throws IOException {
        this(Utilities.toURL(str));
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GifImage(byte[] r3) throws java.io.IOException {
        /*
            r2 = this;
            r2.<init>()
            r0 = 256(0x100, float:3.59E-43)
            byte[] r0 = new byte[r0]
            r2.block = r0
            r0 = 0
            r2.blockSize = r0
            r2.dispose = r0
            r2.transparency = r0
            r2.delay = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2.frames = r0
            r2.fromData = r3
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x002b }
            r1.<init>(r3)     // Catch:{ all -> 0x002b }
            r2.process(r1)     // Catch:{ all -> 0x0028 }
            r1.close()
            return
        L_0x0028:
            r3 = move-exception
            r0 = r1
            goto L_0x002c
        L_0x002b:
            r3 = move-exception
        L_0x002c:
            if (r0 == 0) goto L_0x0031
            r0.close()
        L_0x0031:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.GifImage.<init>(byte[]):void");
    }

    public GifImage(InputStream inputStream) throws IOException {
        this.block = new byte[256];
        this.blockSize = 0;
        this.dispose = 0;
        this.transparency = false;
        this.delay = 0;
        this.frames = new ArrayList<>();
        process(inputStream);
    }

    public int getFrameCount() {
        return this.frames.size();
    }

    public Image getImage(int i) {
        return this.frames.get(i - 1).image;
    }

    public int[] getFramePosition(int i) {
        GifFrame gifFrame = this.frames.get(i - 1);
        return new int[]{gifFrame.f706ix, gifFrame.f707iy};
    }

    public int[] getLogicalScreen() {
        return new int[]{this.width, this.height};
    }

    /* access modifiers changed from: package-private */
    public void process(InputStream inputStream) throws IOException {
        this.f702in = new DataInputStream(new BufferedInputStream(inputStream));
        readHeader();
        readContents();
        if (this.frames.isEmpty()) {
            throw new IOException(MessageLocalization.getComposedMessage("the.file.does.not.contain.any.valid.image", new Object[0]));
        }
    }

    /* access modifiers changed from: protected */
    public void readHeader() throws IOException {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 6; i++) {
            sb.append((char) this.f702in.read());
        }
        if (sb.toString().startsWith("GIF8")) {
            readLSD();
            if (this.gctFlag) {
                this.m_global_table = readColorTable(this.m_gbpc);
                return;
            }
            return;
        }
        throw new IOException(MessageLocalization.getComposedMessage("gif.signature.nor.found", new Object[0]));
    }

    /* access modifiers changed from: protected */
    public void readLSD() throws IOException {
        this.width = readShort();
        this.height = readShort();
        int read = this.f702in.read();
        this.gctFlag = (read & 128) != 0;
        this.m_gbpc = (read & 7) + 1;
        this.bgIndex = this.f702in.read();
        this.pixelAspect = this.f702in.read();
    }

    /* access modifiers changed from: protected */
    public int readShort() throws IOException {
        return this.f702in.read() | (this.f702in.read() << 8);
    }

    /* access modifiers changed from: protected */
    public int readBlock() throws IOException {
        int read = this.f702in.read();
        this.blockSize = read;
        if (read <= 0) {
            this.blockSize = 0;
            return 0;
        }
        int read2 = this.f702in.read(this.block, 0, read);
        this.blockSize = read2;
        return read2;
    }

    /* access modifiers changed from: protected */
    public byte[] readColorTable(int i) throws IOException {
        byte[] bArr = new byte[((1 << newBpc(i)) * 3)];
        this.f702in.readFully(bArr, 0, (1 << i) * 3);
        return bArr;
    }

    /* access modifiers changed from: protected */
    public void readContents() throws IOException {
        boolean z = false;
        while (!z) {
            int read = this.f702in.read();
            if (read == 33) {
                int read2 = this.f702in.read();
                if (read2 == 249) {
                    readGraphicControlExt();
                } else if (read2 != 255) {
                    skip();
                } else {
                    readBlock();
                    skip();
                }
            } else if (read != 44) {
                z = true;
            } else {
                readImage();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void readImage() throws IOException {
        this.f704ix = readShort();
        this.f705iy = readShort();
        this.f703iw = readShort();
        this.f701ih = readShort();
        int read = this.f702in.read();
        this.lctFlag = (read & 128) != 0;
        this.interlace = (read & 64) != 0;
        int i = read & 7;
        this.lctSize = 2 << i;
        this.m_bpc = newBpc(this.m_gbpc);
        if (this.lctFlag) {
            int i2 = i + 1;
            this.m_curr_table = readColorTable(i2);
            this.m_bpc = newBpc(i2);
        } else {
            this.m_curr_table = this.m_global_table;
        }
        if (this.transparency && this.transIndex >= this.m_curr_table.length / 3) {
            this.transparency = false;
        }
        if (this.transparency && this.m_bpc == 1) {
            byte[] bArr = new byte[12];
            System.arraycopy(this.m_curr_table, 0, bArr, 0, 6);
            this.m_curr_table = bArr;
            this.m_bpc = 2;
        }
        if (!decodeImageData()) {
            skip();
        }
        try {
            ImgRaw imgRaw = new ImgRaw(this.f703iw, this.f701ih, 1, this.m_bpc, this.m_out);
            PdfArray pdfArray = new PdfArray();
            pdfArray.add((PdfObject) PdfName.INDEXED);
            pdfArray.add((PdfObject) PdfName.DEVICERGB);
            pdfArray.add((PdfObject) new PdfNumber((this.m_curr_table.length / 3) - 1));
            pdfArray.add((PdfObject) new PdfString(this.m_curr_table));
            PdfDictionary pdfDictionary = new PdfDictionary();
            pdfDictionary.put(PdfName.COLORSPACE, pdfArray);
            imgRaw.setAdditional(pdfDictionary);
            if (this.transparency) {
                int i3 = this.transIndex;
                imgRaw.setTransparency(new int[]{i3, i3});
            }
            imgRaw.setOriginalType(3);
            imgRaw.setOriginalData(this.fromData);
            imgRaw.setUrl(this.fromUrl);
            GifFrame gifFrame = new GifFrame();
            gifFrame.image = imgRaw;
            gifFrame.f706ix = this.f704ix;
            gifFrame.f707iy = this.f705iy;
            this.frames.add(gifFrame);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: protected */
    public boolean decodeImageData() throws IOException {
        int i;
        int i2;
        int i3;
        byte b;
        int i4;
        byte b2;
        short s;
        int i5 = this.f703iw;
        int i6 = this.f701ih;
        int i7 = i5 * i6;
        if (this.prefix == null) {
            this.prefix = new short[4096];
        }
        if (this.suffix == null) {
            this.suffix = new byte[4096];
        }
        if (this.pixelStack == null) {
            this.pixelStack = new byte[4097];
        }
        int i8 = 8;
        int i9 = ((i5 * this.m_bpc) + 7) / 8;
        this.m_line_stride = i9;
        this.m_out = new byte[(i9 * i6)];
        boolean z = true;
        if (!this.interlace) {
            i8 = 1;
        }
        int read = this.f702in.read();
        int i10 = 1 << read;
        int i11 = i10 + 1;
        int i12 = i10 + 2;
        int i13 = read + 1;
        int i14 = (1 << i13) - 1;
        for (int i15 = 0; i15 < i10; i15++) {
            this.prefix[i15] = 0;
            this.suffix[i15] = (byte) i15;
        }
        int i16 = i13;
        int i17 = i12;
        int i18 = i14;
        byte b3 = -1;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        int i23 = 0;
        int i24 = 0;
        byte b4 = 0;
        int i25 = 1;
        int i26 = 0;
        int i27 = 0;
        while (i19 < i7) {
            if (i20 == 0) {
                if (i21 >= i16) {
                    byte b5 = i22 & i18;
                    i22 >>= i16;
                    i21 -= i16;
                    if (b5 > i17 || b5 == i11) {
                        break;
                    } else if (b5 == i10) {
                        i16 = i13;
                        i17 = i12;
                        i18 = i14;
                        z = true;
                        b3 = -1;
                    } else if (b3 == -1) {
                        this.pixelStack[i20] = this.suffix[b5];
                        b3 = b5;
                        b4 = b3;
                        i20++;
                        i13 = i13;
                        z = true;
                    } else {
                        i2 = i13;
                        if (b5 == i17) {
                            b2 = b5;
                            this.pixelStack[i20] = (byte) b4;
                            s = b3;
                            i20++;
                        } else {
                            b2 = b5;
                            s = b2;
                        }
                        while (s > i10) {
                            this.pixelStack[i20] = this.suffix[s];
                            s = this.prefix[s];
                            i20++;
                            i7 = i7;
                        }
                        i3 = i7;
                        byte[] bArr = this.suffix;
                        b = bArr[s] & UByte.MAX_VALUE;
                        if (i17 >= 4096) {
                            break;
                        }
                        int i28 = i20 + 1;
                        i = i10;
                        byte b6 = (byte) b;
                        this.pixelStack[i20] = b6;
                        this.prefix[i17] = (short) b3;
                        bArr[i17] = b6;
                        i17++;
                        if ((i17 & i18) == 0 && i17 < 4096) {
                            i16++;
                            i18 += i17;
                        }
                        i20 = i28;
                        b3 = b2;
                    }
                } else {
                    if (i23 == 0) {
                        i23 = readBlock();
                        if (i23 <= 0) {
                            return z;
                        }
                        i24 = 0;
                    }
                    i22 += (this.block[i24] & UByte.MAX_VALUE) << i21;
                    i21 += 8;
                    i24++;
                    i23--;
                }
            } else {
                i2 = i13;
                i = i10;
                byte b7 = b4;
                i3 = i7;
                b = b7;
            }
            i20--;
            i19++;
            int i29 = i26;
            int i30 = i27;
            setPixel(i30, i29, this.pixelStack[i20]);
            int i31 = i30 + 1;
            if (i31 >= this.f703iw) {
                int i32 = i29 + i8;
                int i33 = this.f701ih;
                if (i32 < i33) {
                    i26 = i32;
                } else if (this.interlace) {
                    do {
                        int i34 = i25 + 1;
                        i4 = 4;
                        if (i34 != 2) {
                            if (i34 == 3) {
                                i8 = 4;
                                i4 = 2;
                            } else if (i34 != 4) {
                                i4 = this.f701ih - 1;
                                i8 = 0;
                            } else {
                                i8 = 2;
                                i4 = 1;
                            }
                        }
                        i25 = i34;
                    } while (i4 >= this.f701ih);
                    i26 = i4;
                } else {
                    i26 = i33 - 1;
                    i7 = i3;
                    i10 = i;
                    z = true;
                    i8 = 0;
                    i27 = 0;
                }
                i7 = i3;
                i10 = i;
                z = true;
                i27 = 0;
            } else {
                i27 = i31;
                i26 = i29;
                i7 = i3;
                i10 = i;
                z = true;
            }
            b4 = b;
            i13 = i2;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void setPixel(int i, int i2, int i3) {
        int i4 = this.m_bpc;
        if (i4 == 8) {
            this.m_out[i + (this.f703iw * i2)] = (byte) i3;
            return;
        }
        int i5 = (this.m_line_stride * i2) + (i / (8 / i4));
        byte[] bArr = this.m_out;
        bArr[i5] = (byte) ((i3 << ((8 - ((i % (8 / i4)) * i4)) - i4)) | bArr[i5]);
    }

    /* access modifiers changed from: protected */
    public void readGraphicControlExt() throws IOException {
        this.f702in.read();
        int read = this.f702in.read();
        int i = (read & 28) >> 2;
        this.dispose = i;
        boolean z = true;
        if (i == 0) {
            this.dispose = 1;
        }
        if ((read & 1) == 0) {
            z = false;
        }
        this.transparency = z;
        this.delay = readShort() * 10;
        this.transIndex = this.f702in.read();
        this.f702in.read();
    }

    /* access modifiers changed from: protected */
    public void skip() throws IOException {
        do {
            readBlock();
        } while (this.blockSize > 0);
    }

    static class GifFrame {
        Image image;

        /* renamed from: ix */
        int f706ix;

        /* renamed from: iy */
        int f707iy;

        GifFrame() {
        }
    }
}
