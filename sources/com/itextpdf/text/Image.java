package com.itextpdf.text;

import com.itextpdf.awt.FontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.api.Indentable;
import com.itextpdf.text.api.Spaceable;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfIndirectReference;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfOCG;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.CCITTG4Encoder;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import com.itextpdf.text.pdf.interfaces.IAlternateDescription;
import java.awt.Color;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public abstract class Image extends Rectangle implements Indentable, Spaceable, IAccessibleElement, IAlternateDescription {

    /* renamed from: AX */
    public static final int f442AX = 0;

    /* renamed from: AY */
    public static final int f443AY = 1;

    /* renamed from: BX */
    public static final int f444BX = 2;

    /* renamed from: BY */
    public static final int f445BY = 3;

    /* renamed from: CX */
    public static final int f446CX = 4;

    /* renamed from: CY */
    public static final int f447CY = 5;
    public static final int DEFAULT = 0;

    /* renamed from: DX */
    public static final int f448DX = 6;

    /* renamed from: DY */
    public static final int f449DY = 7;
    public static final int LEFT = 0;
    public static final int MIDDLE = 1;
    public static final int ORIGINAL_BMP = 4;
    public static final int ORIGINAL_GIF = 3;
    public static final int ORIGINAL_JBIG2 = 9;
    public static final int ORIGINAL_JPEG = 1;
    public static final int ORIGINAL_JPEG2000 = 8;
    public static final int ORIGINAL_NONE = 0;
    public static final int ORIGINAL_PNG = 2;
    public static final int ORIGINAL_PS = 7;
    public static final int ORIGINAL_TIFF = 5;
    public static final int ORIGINAL_WMF = 6;
    public static final int RIGHT = 2;
    public static final int TEXTWRAP = 4;
    public static final int UNDERLYING = 8;
    static long serialId;
    private float XYRatio = 0.0f;
    protected float absoluteX = Float.NaN;
    protected float absoluteY = Float.NaN;
    protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
    private PdfDictionary additional = null;
    protected int alignment;
    protected String alt;
    protected Annotation annotation = null;
    protected int bpc = 1;
    protected int colorspace = -1;
    protected int colortransform = 1;
    protected int compressionLevel = -1;
    protected boolean deflated = false;
    private PdfIndirectReference directReference;
    protected int dpiX = 0;
    protected int dpiY = 0;

    /* renamed from: id */
    private AccessibleElementId f450id = null;
    protected Image imageMask;
    protected float indentationLeft = 0.0f;
    protected float indentationRight = 0.0f;
    private float initialRotation;
    protected boolean interpolation;
    protected boolean invert = false;
    protected PdfOCG layer;
    protected boolean mask = false;
    protected Long mySerialId = getSerialId();
    protected byte[] originalData;
    protected int originalType = 0;
    protected float paddingTop;
    protected float plainHeight;
    protected float plainWidth;
    protected ICC_Profile profile = null;
    protected byte[] rawData;
    protected PdfName role = PdfName.FIGURE;
    protected float rotationRadians;
    protected boolean scaleToFitHeight = true;
    protected boolean scaleToFitLineWhenOverflow;
    protected float scaledHeight;
    protected float scaledWidth;
    private boolean smask;
    protected float spacingAfter;
    protected float spacingBefore;
    protected PdfTemplate[] template = new PdfTemplate[1];
    protected int[] transparency;
    protected int type;
    protected URL url;
    private float widthPercentage = 100.0f;

    public boolean isInline() {
        return true;
    }

    public boolean isNestable() {
        return true;
    }

    public Image(URL url2) {
        super(0.0f, 0.0f);
        this.url = url2;
        this.alignment = 0;
        this.rotationRadians = 0.0f;
    }

    public static Image getInstance(URL url2) throws BadElementException, MalformedURLException, IOException {
        return getInstance(url2, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:123:0x0167 A[Catch:{ all -> 0x0161, all -> 0x0184 }] */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0188  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0100 A[SYNTHETIC, Splitter:B:80:0x0100] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x010c A[SYNTHETIC, Splitter:B:86:0x010c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.text.Image getInstance(java.net.URL r17, boolean r18) throws com.itextpdf.text.BadElementException, java.net.MalformedURLException, java.io.IOException {
        /*
            r1 = r17
            r2 = r18
            com.itextpdf.text.io.RandomAccessSourceFactory r0 = new com.itextpdf.text.io.RandomAccessSourceFactory
            r0.<init>()
            java.io.InputStream r4 = r17.openStream()     // Catch:{ all -> 0x0184 }
            int r5 = r4.read()     // Catch:{ all -> 0x0181 }
            int r6 = r4.read()     // Catch:{ all -> 0x0181 }
            int r7 = r4.read()     // Catch:{ all -> 0x0181 }
            int r8 = r4.read()     // Catch:{ all -> 0x0181 }
            int r9 = r4.read()     // Catch:{ all -> 0x0181 }
            int r10 = r4.read()     // Catch:{ all -> 0x0181 }
            int r11 = r4.read()     // Catch:{ all -> 0x0181 }
            int r12 = r4.read()     // Catch:{ all -> 0x0181 }
            r4.close()     // Catch:{ all -> 0x0181 }
            r4 = 71
            r13 = 73
            r14 = 1
            if (r5 != r4) goto L_0x0047
            if (r6 != r13) goto L_0x0047
            r4 = 70
            if (r7 != r4) goto L_0x0047
            com.itextpdf.text.pdf.codec.GifImage r0 = new com.itextpdf.text.pdf.codec.GifImage     // Catch:{ all -> 0x0184 }
            r0.<init>((java.net.URL) r1)     // Catch:{ all -> 0x0184 }
            com.itextpdf.text.Image r0 = r0.getImage(r14)     // Catch:{ all -> 0x0184 }
            return r0
        L_0x0047:
            r4 = 255(0xff, float:3.57E-43)
            if (r5 != r4) goto L_0x0055
            r15 = 216(0xd8, float:3.03E-43)
            if (r6 != r15) goto L_0x0055
            com.itextpdf.text.Jpeg r0 = new com.itextpdf.text.Jpeg     // Catch:{ all -> 0x0184 }
            r0.<init>((java.net.URL) r1)     // Catch:{ all -> 0x0184 }
            return r0
        L_0x0055:
            if (r5 != 0) goto L_0x0065
            if (r6 != 0) goto L_0x0065
            if (r7 != 0) goto L_0x0065
            r15 = 12
            if (r8 != r15) goto L_0x0065
            com.itextpdf.text.Jpeg2000 r0 = new com.itextpdf.text.Jpeg2000     // Catch:{ all -> 0x0184 }
            r0.<init>((java.net.URL) r1)     // Catch:{ all -> 0x0184 }
            return r0
        L_0x0065:
            if (r5 != r4) goto L_0x0077
            r15 = 79
            if (r6 != r15) goto L_0x0077
            if (r7 != r4) goto L_0x0077
            r4 = 81
            if (r8 != r4) goto L_0x0077
            com.itextpdf.text.Jpeg2000 r0 = new com.itextpdf.text.Jpeg2000     // Catch:{ all -> 0x0184 }
            r0.<init>((java.net.URL) r1)     // Catch:{ all -> 0x0184 }
            return r0
        L_0x0077:
            int[] r4 = com.itextpdf.text.pdf.codec.PngImage.PNGID     // Catch:{ all -> 0x0184 }
            r15 = 0
            r4 = r4[r15]     // Catch:{ all -> 0x0184 }
            if (r5 != r4) goto L_0x0099
            int[] r4 = com.itextpdf.text.pdf.codec.PngImage.PNGID     // Catch:{ all -> 0x0184 }
            r4 = r4[r14]     // Catch:{ all -> 0x0184 }
            if (r6 != r4) goto L_0x0099
            int[] r4 = com.itextpdf.text.pdf.codec.PngImage.PNGID     // Catch:{ all -> 0x0184 }
            r16 = 2
            r4 = r4[r16]     // Catch:{ all -> 0x0184 }
            if (r7 != r4) goto L_0x0099
            int[] r4 = com.itextpdf.text.pdf.codec.PngImage.PNGID     // Catch:{ all -> 0x0184 }
            r16 = 3
            r4 = r4[r16]     // Catch:{ all -> 0x0184 }
            if (r8 != r4) goto L_0x0099
            com.itextpdf.text.Image r0 = com.itextpdf.text.pdf.codec.PngImage.getImage((java.net.URL) r17)     // Catch:{ all -> 0x0184 }
            return r0
        L_0x0099:
            r4 = 215(0xd7, float:3.01E-43)
            if (r5 != r4) goto L_0x00a7
            r4 = 205(0xcd, float:2.87E-43)
            if (r6 != r4) goto L_0x00a7
            com.itextpdf.text.ImgWMF r0 = new com.itextpdf.text.ImgWMF     // Catch:{ all -> 0x0184 }
            r0.<init>((java.net.URL) r1)     // Catch:{ all -> 0x0184 }
            return r0
        L_0x00a7:
            r4 = 66
            r3 = 77
            if (r5 != r4) goto L_0x00b4
            if (r6 != r3) goto L_0x00b4
            com.itextpdf.text.Image r0 = com.itextpdf.text.pdf.codec.BmpImage.getImage((java.net.URL) r17)     // Catch:{ all -> 0x0184 }
            return r0
        L_0x00b4:
            java.lang.String r15 = "file"
            r4 = 42
            if (r5 != r3) goto L_0x00c0
            if (r6 != r3) goto L_0x00c0
            if (r7 != 0) goto L_0x00c0
            if (r8 == r4) goto L_0x00c8
        L_0x00c0:
            if (r5 != r13) goto L_0x0114
            if (r6 != r13) goto L_0x0114
            if (r7 != r4) goto L_0x0114
            if (r8 != 0) goto L_0x0114
        L_0x00c8:
            java.lang.String r3 = r17.getProtocol()     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
            boolean r3 = r3.equals(r15)     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
            if (r3 == 0) goto L_0x00e4
            java.lang.String r3 = r17.getFile()     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
            java.lang.String r3 = com.itextpdf.text.Utilities.unEscapeURL(r3)     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
            com.itextpdf.text.pdf.RandomAccessFileOrArray r4 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
            com.itextpdf.text.io.RandomAccessSource r0 = r0.createBestSource((java.lang.String) r3)     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
            r4.<init>((com.itextpdf.text.p018io.RandomAccessSource) r0)     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
            goto L_0x00ed
        L_0x00e4:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r4 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
            com.itextpdf.text.io.RandomAccessSource r0 = r0.createSource((java.net.URL) r1)     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
            r4.<init>((com.itextpdf.text.p018io.RandomAccessSource) r0)     // Catch:{ RuntimeException -> 0x00fc, all -> 0x00f9 }
        L_0x00ed:
            com.itextpdf.text.Image r0 = com.itextpdf.text.pdf.codec.TiffImage.getTiffImage(r4, r14)     // Catch:{ RuntimeException -> 0x00f7 }
            r0.url = r1     // Catch:{ RuntimeException -> 0x00f7 }
            r4.close()     // Catch:{ all -> 0x0184 }
            return r0
        L_0x00f7:
            r0 = move-exception
            goto L_0x00fe
        L_0x00f9:
            r0 = move-exception
            r4 = 0
            goto L_0x010e
        L_0x00fc:
            r0 = move-exception
            r4 = 0
        L_0x00fe:
            if (r2 == 0) goto L_0x010c
            com.itextpdf.text.Image r0 = com.itextpdf.text.pdf.codec.TiffImage.getTiffImage((com.itextpdf.text.pdf.RandomAccessFileOrArray) r4, (boolean) r2, (int) r14)     // Catch:{ all -> 0x010d }
            r0.url = r1     // Catch:{ all -> 0x010d }
            if (r4 == 0) goto L_0x010b
            r4.close()     // Catch:{ all -> 0x0184 }
        L_0x010b:
            return r0
        L_0x010c:
            throw r0     // Catch:{ all -> 0x010d }
        L_0x010d:
            r0 = move-exception
        L_0x010e:
            if (r4 == 0) goto L_0x0113
            r4.close()     // Catch:{ all -> 0x0184 }
        L_0x0113:
            throw r0     // Catch:{ all -> 0x0184 }
        L_0x0114:
            r2 = 151(0x97, float:2.12E-43)
            if (r5 != r2) goto L_0x016b
            r2 = 74
            if (r6 != r2) goto L_0x016b
            r2 = 66
            if (r7 != r2) goto L_0x016b
            r2 = 50
            if (r8 != r2) goto L_0x016b
            r2 = 13
            if (r9 != r2) goto L_0x016b
            r2 = 10
            if (r10 != r2) goto L_0x016b
            r3 = 26
            if (r11 != r3) goto L_0x016b
            if (r12 != r2) goto L_0x016b
            java.lang.String r2 = r17.getProtocol()     // Catch:{ all -> 0x0163 }
            boolean r2 = r2.equals(r15)     // Catch:{ all -> 0x0163 }
            if (r2 == 0) goto L_0x014e
            java.lang.String r2 = r17.getFile()     // Catch:{ all -> 0x0163 }
            java.lang.String r2 = com.itextpdf.text.Utilities.unEscapeURL(r2)     // Catch:{ all -> 0x0163 }
            com.itextpdf.text.pdf.RandomAccessFileOrArray r3 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ all -> 0x0163 }
            com.itextpdf.text.io.RandomAccessSource r0 = r0.createBestSource((java.lang.String) r2)     // Catch:{ all -> 0x0163 }
            r3.<init>((com.itextpdf.text.p018io.RandomAccessSource) r0)     // Catch:{ all -> 0x0163 }
            goto L_0x0157
        L_0x014e:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r3 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ all -> 0x0163 }
            com.itextpdf.text.io.RandomAccessSource r0 = r0.createSource((java.net.URL) r1)     // Catch:{ all -> 0x0163 }
            r3.<init>((com.itextpdf.text.p018io.RandomAccessSource) r0)     // Catch:{ all -> 0x0163 }
        L_0x0157:
            com.itextpdf.text.Image r0 = com.itextpdf.text.pdf.codec.JBIG2Image.getJbig2Image(r3, r14)     // Catch:{ all -> 0x0161 }
            r0.url = r1     // Catch:{ all -> 0x0161 }
            r3.close()     // Catch:{ all -> 0x0184 }
            return r0
        L_0x0161:
            r0 = move-exception
            goto L_0x0165
        L_0x0163:
            r0 = move-exception
            r3 = 0
        L_0x0165:
            if (r3 == 0) goto L_0x016a
            r3.close()     // Catch:{ all -> 0x0184 }
        L_0x016a:
            throw r0     // Catch:{ all -> 0x0184 }
        L_0x016b:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0184 }
            java.lang.String r2 = "unknown.image.format"
            java.lang.Object[] r3 = new java.lang.Object[r14]     // Catch:{ all -> 0x0184 }
            java.lang.String r1 = r17.toString()     // Catch:{ all -> 0x0184 }
            r4 = 0
            r3[r4] = r1     // Catch:{ all -> 0x0184 }
            java.lang.String r1 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ all -> 0x0184 }
            r0.<init>(r1)     // Catch:{ all -> 0x0184 }
            throw r0     // Catch:{ all -> 0x0184 }
        L_0x0181:
            r0 = move-exception
            r3 = r4
            goto L_0x0186
        L_0x0184:
            r0 = move-exception
            r3 = 0
        L_0x0186:
            if (r3 == 0) goto L_0x018b
            r3.close()
        L_0x018b:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Image.getInstance(java.net.URL, boolean):com.itextpdf.text.Image");
    }

    public static Image getInstance(String str) throws BadElementException, MalformedURLException, IOException {
        return getInstance(Utilities.toURL(str));
    }

    public static Image getInstance(String str, boolean z) throws IOException, BadElementException {
        return getInstance(Utilities.toURL(str), z);
    }

    public static Image getInstance(byte[] bArr) throws BadElementException, MalformedURLException, IOException {
        return getInstance(bArr, false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: com.itextpdf.text.pdf.RandomAccessFileOrArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.io.ByteArrayInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: com.itextpdf.text.pdf.RandomAccessFileOrArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.io.ByteArrayInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: com.itextpdf.text.pdf.RandomAccessFileOrArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.io.ByteArrayInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.io.ByteArrayInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: com.itextpdf.text.pdf.RandomAccessFileOrArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: com.itextpdf.text.pdf.RandomAccessFileOrArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: com.itextpdf.text.pdf.RandomAccessFileOrArray} */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x014c A[SYNTHETIC, Splitter:B:126:0x014c] */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x016a  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00d5 A[SYNTHETIC, Splitter:B:79:0x00d5] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x00e8 A[SYNTHETIC, Splitter:B:87:0x00e8] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:132:0x0155=Splitter:B:132:0x0155, B:70:0x00c8=Splitter:B:70:0x00c8} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.text.Image getInstance(byte[] r12, boolean r13) throws com.itextpdf.text.BadElementException, java.net.MalformedURLException, java.io.IOException {
        /*
            com.itextpdf.text.io.RandomAccessSourceFactory r0 = new com.itextpdf.text.io.RandomAccessSourceFactory
            r0.<init>()
            r1 = 0
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0167 }
            r2.<init>(r12)     // Catch:{ all -> 0x0167 }
            int r3 = r2.read()     // Catch:{ all -> 0x0164 }
            int r4 = r2.read()     // Catch:{ all -> 0x0164 }
            int r5 = r2.read()     // Catch:{ all -> 0x0164 }
            int r6 = r2.read()     // Catch:{ all -> 0x0164 }
            r2.close()     // Catch:{ all -> 0x0164 }
            r2 = 71
            r7 = 73
            r8 = 1
            if (r3 != r2) goto L_0x0035
            if (r4 != r7) goto L_0x0035
            r2 = 70
            if (r5 != r2) goto L_0x0035
            com.itextpdf.text.pdf.codec.GifImage r13 = new com.itextpdf.text.pdf.codec.GifImage     // Catch:{ all -> 0x0167 }
            r13.<init>((byte[]) r12)     // Catch:{ all -> 0x0167 }
            com.itextpdf.text.Image r12 = r13.getImage(r8)     // Catch:{ all -> 0x0167 }
            return r12
        L_0x0035:
            r2 = 255(0xff, float:3.57E-43)
            if (r3 != r2) goto L_0x0043
            r9 = 216(0xd8, float:3.03E-43)
            if (r4 != r9) goto L_0x0043
            com.itextpdf.text.Jpeg r13 = new com.itextpdf.text.Jpeg     // Catch:{ all -> 0x0167 }
            r13.<init>((byte[]) r12)     // Catch:{ all -> 0x0167 }
            return r13
        L_0x0043:
            if (r3 != 0) goto L_0x0053
            if (r4 != 0) goto L_0x0053
            if (r5 != 0) goto L_0x0053
            r9 = 12
            if (r6 != r9) goto L_0x0053
            com.itextpdf.text.Jpeg2000 r13 = new com.itextpdf.text.Jpeg2000     // Catch:{ all -> 0x0167 }
            r13.<init>((byte[]) r12)     // Catch:{ all -> 0x0167 }
            return r13
        L_0x0053:
            if (r3 != r2) goto L_0x0065
            r9 = 79
            if (r4 != r9) goto L_0x0065
            if (r5 != r2) goto L_0x0065
            r2 = 81
            if (r6 != r2) goto L_0x0065
            com.itextpdf.text.Jpeg2000 r13 = new com.itextpdf.text.Jpeg2000     // Catch:{ all -> 0x0167 }
            r13.<init>((byte[]) r12)     // Catch:{ all -> 0x0167 }
            return r13
        L_0x0065:
            int[] r2 = com.itextpdf.text.pdf.codec.PngImage.PNGID     // Catch:{ all -> 0x0167 }
            r9 = 0
            r2 = r2[r9]     // Catch:{ all -> 0x0167 }
            if (r3 != r2) goto L_0x0085
            int[] r2 = com.itextpdf.text.pdf.codec.PngImage.PNGID     // Catch:{ all -> 0x0167 }
            r2 = r2[r8]     // Catch:{ all -> 0x0167 }
            if (r4 != r2) goto L_0x0085
            int[] r2 = com.itextpdf.text.pdf.codec.PngImage.PNGID     // Catch:{ all -> 0x0167 }
            r10 = 2
            r2 = r2[r10]     // Catch:{ all -> 0x0167 }
            if (r5 != r2) goto L_0x0085
            int[] r2 = com.itextpdf.text.pdf.codec.PngImage.PNGID     // Catch:{ all -> 0x0167 }
            r10 = 3
            r2 = r2[r10]     // Catch:{ all -> 0x0167 }
            if (r6 != r2) goto L_0x0085
            com.itextpdf.text.Image r12 = com.itextpdf.text.pdf.codec.PngImage.getImage((byte[]) r12)     // Catch:{ all -> 0x0167 }
            return r12
        L_0x0085:
            r2 = 215(0xd7, float:3.01E-43)
            if (r3 != r2) goto L_0x0093
            r2 = 205(0xcd, float:2.87E-43)
            if (r4 != r2) goto L_0x0093
            com.itextpdf.text.ImgWMF r13 = new com.itextpdf.text.ImgWMF     // Catch:{ all -> 0x0167 }
            r13.<init>((byte[]) r12)     // Catch:{ all -> 0x0167 }
            return r13
        L_0x0093:
            r2 = 66
            r10 = 77
            if (r3 != r2) goto L_0x00a0
            if (r4 != r10) goto L_0x00a0
            com.itextpdf.text.Image r12 = com.itextpdf.text.pdf.codec.BmpImage.getImage((byte[]) r12)     // Catch:{ all -> 0x0167 }
            return r12
        L_0x00a0:
            r11 = 42
            if (r3 != r10) goto L_0x00aa
            if (r4 != r10) goto L_0x00aa
            if (r5 != 0) goto L_0x00aa
            if (r6 == r11) goto L_0x00b2
        L_0x00aa:
            if (r3 != r7) goto L_0x00f0
            if (r4 != r7) goto L_0x00f0
            if (r5 != r11) goto L_0x00f0
            if (r6 != 0) goto L_0x00f0
        L_0x00b2:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r2 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ RuntimeException -> 0x00d1, all -> 0x00ce }
            com.itextpdf.text.io.RandomAccessSource r0 = r0.createSource((byte[]) r12)     // Catch:{ RuntimeException -> 0x00d1, all -> 0x00ce }
            r2.<init>((com.itextpdf.text.p018io.RandomAccessSource) r0)     // Catch:{ RuntimeException -> 0x00d1, all -> 0x00ce }
            com.itextpdf.text.Image r0 = com.itextpdf.text.pdf.codec.TiffImage.getTiffImage(r2, r8)     // Catch:{ RuntimeException -> 0x00cc }
            byte[] r3 = r0.getOriginalData()     // Catch:{ RuntimeException -> 0x00cc }
            if (r3 != 0) goto L_0x00c8
            r0.setOriginalData(r12)     // Catch:{ RuntimeException -> 0x00cc }
        L_0x00c8:
            r2.close()     // Catch:{ all -> 0x0167 }
            return r0
        L_0x00cc:
            r0 = move-exception
            goto L_0x00d3
        L_0x00ce:
            r12 = move-exception
            r2 = r1
            goto L_0x00ea
        L_0x00d1:
            r0 = move-exception
            r2 = r1
        L_0x00d3:
            if (r13 == 0) goto L_0x00e8
            com.itextpdf.text.Image r13 = com.itextpdf.text.pdf.codec.TiffImage.getTiffImage((com.itextpdf.text.pdf.RandomAccessFileOrArray) r2, (boolean) r13, (int) r8)     // Catch:{ all -> 0x00e9 }
            byte[] r0 = r13.getOriginalData()     // Catch:{ all -> 0x00e9 }
            if (r0 != 0) goto L_0x00e2
            r13.setOriginalData(r12)     // Catch:{ all -> 0x00e9 }
        L_0x00e2:
            if (r2 == 0) goto L_0x00e7
            r2.close()     // Catch:{ all -> 0x0167 }
        L_0x00e7:
            return r13
        L_0x00e8:
            throw r0     // Catch:{ all -> 0x00e9 }
        L_0x00e9:
            r12 = move-exception
        L_0x00ea:
            if (r2 == 0) goto L_0x00ef
            r2.close()     // Catch:{ all -> 0x0167 }
        L_0x00ef:
            throw r12     // Catch:{ all -> 0x0167 }
        L_0x00f0:
            r13 = 151(0x97, float:2.12E-43)
            if (r3 != r13) goto L_0x0155
            r13 = 74
            if (r4 != r13) goto L_0x0155
            if (r5 != r2) goto L_0x0155
            r13 = 50
            if (r6 != r13) goto L_0x0155
            java.io.ByteArrayInputStream r13 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0167 }
            r13.<init>(r12)     // Catch:{ all -> 0x0167 }
            r2 = 4
            r13.skip(r2)     // Catch:{ all -> 0x0152 }
            int r2 = r13.read()     // Catch:{ all -> 0x0152 }
            int r3 = r13.read()     // Catch:{ all -> 0x0152 }
            int r4 = r13.read()     // Catch:{ all -> 0x0152 }
            int r5 = r13.read()     // Catch:{ all -> 0x0152 }
            r13.close()     // Catch:{ all -> 0x0152 }
            r6 = 13
            if (r2 != r6) goto L_0x0150
            r2 = 10
            if (r3 != r2) goto L_0x0150
            r3 = 26
            if (r4 != r3) goto L_0x0150
            if (r5 != r2) goto L_0x0150
            com.itextpdf.text.pdf.RandomAccessFileOrArray r2 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ all -> 0x0149 }
            com.itextpdf.text.io.RandomAccessSource r0 = r0.createSource((byte[]) r12)     // Catch:{ all -> 0x0149 }
            r2.<init>((com.itextpdf.text.p018io.RandomAccessSource) r0)     // Catch:{ all -> 0x0149 }
            com.itextpdf.text.Image r0 = com.itextpdf.text.pdf.codec.JBIG2Image.getJbig2Image(r2, r8)     // Catch:{ all -> 0x0146 }
            byte[] r1 = r0.getOriginalData()     // Catch:{ all -> 0x0146 }
            if (r1 != 0) goto L_0x013f
            r0.setOriginalData(r12)     // Catch:{ all -> 0x0146 }
        L_0x013f:
            r2.close()     // Catch:{ all -> 0x0152 }
            r13.close()
            return r0
        L_0x0146:
            r12 = move-exception
            r1 = r2
            goto L_0x014a
        L_0x0149:
            r12 = move-exception
        L_0x014a:
            if (r1 == 0) goto L_0x014f
            r1.close()     // Catch:{ all -> 0x0152 }
        L_0x014f:
            throw r12     // Catch:{ all -> 0x0152 }
        L_0x0150:
            r1 = r13
            goto L_0x0155
        L_0x0152:
            r12 = move-exception
            r1 = r13
            goto L_0x0168
        L_0x0155:
            java.io.IOException r12 = new java.io.IOException     // Catch:{ all -> 0x0167 }
            java.lang.String r13 = "the.byte.array.is.not.a.recognized.imageformat"
            java.lang.Object[] r0 = new java.lang.Object[r9]     // Catch:{ all -> 0x0167 }
            java.lang.String r13 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r13, (java.lang.Object[]) r0)     // Catch:{ all -> 0x0167 }
            r12.<init>(r13)     // Catch:{ all -> 0x0167 }
            throw r12     // Catch:{ all -> 0x0167 }
        L_0x0164:
            r12 = move-exception
            r1 = r2
            goto L_0x0168
        L_0x0167:
            r12 = move-exception
        L_0x0168:
            if (r1 == 0) goto L_0x016d
            r1.close()
        L_0x016d:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Image.getInstance(byte[], boolean):com.itextpdf.text.Image");
    }

    public static Image getInstance(int i, int i2, int i3, int i4, byte[] bArr) throws BadElementException {
        return getInstance(i, i2, i3, i4, bArr, (int[]) null);
    }

    public static Image getInstance(int i, int i2, byte[] bArr, byte[] bArr2) {
        return new ImgJBIG2(i, i2, bArr, bArr2);
    }

    public static Image getInstance(int i, int i2, boolean z, int i3, int i4, byte[] bArr) throws BadElementException {
        return getInstance(i, i2, z, i3, i4, bArr, (int[]) null);
    }

    public static Image getInstance(int i, int i2, boolean z, int i3, int i4, byte[] bArr, int[] iArr) throws BadElementException {
        if (iArr == null || iArr.length == 2) {
            ImgCCITT imgCCITT = new ImgCCITT(i, i2, z, i3, i4, bArr);
            imgCCITT.transparency = iArr;
            return imgCCITT;
        }
        throw new BadElementException(MessageLocalization.getComposedMessage("transparency.length.must.be.equal.to.2.with.ccitt.images", new Object[0]));
    }

    public static Image getInstance(int i, int i2, int i3, int i4, byte[] bArr, int[] iArr) throws BadElementException {
        if (iArr != null && iArr.length != i3 * 2) {
            throw new BadElementException(MessageLocalization.getComposedMessage("transparency.length.must.be.equal.to.componentes.2", new Object[0]));
        } else if (i3 == 1 && i4 == 1) {
            return getInstance(i, i2, false, 256, 1, CCITTG4Encoder.compress(bArr, i, i2), iArr);
        } else {
            ImgRaw imgRaw = new ImgRaw(i, i2, i3, i4, bArr);
            imgRaw.transparency = iArr;
            return imgRaw;
        }
    }

    public static Image getInstance(PdfTemplate pdfTemplate) throws BadElementException {
        return new ImgTemplate(pdfTemplate);
    }

    public PdfIndirectReference getDirectReference() {
        return this.directReference;
    }

    public void setDirectReference(PdfIndirectReference pdfIndirectReference) {
        this.directReference = pdfIndirectReference;
    }

    public static Image getInstance(PRIndirectReference pRIndirectReference) throws BadElementException {
        Image image;
        PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObjectRelease((PdfObject) pRIndirectReference);
        int intValue = ((PdfNumber) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.WIDTH))).intValue();
        int intValue2 = ((PdfNumber) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.HEIGHT))).intValue();
        PdfObject pdfObject = pdfDictionary.get(PdfName.SMASK);
        if (pdfObject == null || !pdfObject.isIndirect()) {
            PdfObject pdfObject2 = pdfDictionary.get(PdfName.MASK);
            image = (pdfObject2 == null || !pdfObject2.isIndirect() || !(PdfReader.getPdfObjectRelease(pdfObject2) instanceof PdfDictionary)) ? null : getInstance((PRIndirectReference) pdfObject2);
        } else {
            image = getInstance((PRIndirectReference) pdfObject);
        }
        ImgRaw imgRaw = new ImgRaw(intValue, intValue2, 1, 1, (byte[]) null);
        imgRaw.imageMask = image;
        imgRaw.directReference = pRIndirectReference;
        return imgRaw;
    }

    protected Image(Image image) {
        super((Rectangle) image);
        this.type = image.type;
        this.url = image.url;
        this.rawData = image.rawData;
        this.bpc = image.bpc;
        this.template = image.template;
        this.alignment = image.alignment;
        this.alt = image.alt;
        this.absoluteX = image.absoluteX;
        this.absoluteY = image.absoluteY;
        this.plainWidth = image.plainWidth;
        this.plainHeight = image.plainHeight;
        this.scaledWidth = image.scaledWidth;
        this.scaledHeight = image.scaledHeight;
        this.mySerialId = image.mySerialId;
        this.directReference = image.directReference;
        this.rotationRadians = image.rotationRadians;
        this.initialRotation = image.initialRotation;
        this.indentationLeft = image.indentationLeft;
        this.indentationRight = image.indentationRight;
        this.spacingBefore = image.spacingBefore;
        this.spacingAfter = image.spacingAfter;
        this.widthPercentage = image.widthPercentage;
        this.scaleToFitLineWhenOverflow = image.scaleToFitLineWhenOverflow;
        this.scaleToFitHeight = image.scaleToFitHeight;
        this.annotation = image.annotation;
        this.layer = image.layer;
        this.interpolation = image.interpolation;
        this.originalType = image.originalType;
        this.originalData = image.originalData;
        this.deflated = image.deflated;
        this.dpiX = image.dpiX;
        this.dpiY = image.dpiY;
        this.XYRatio = image.XYRatio;
        this.colorspace = image.colorspace;
        this.invert = image.invert;
        this.profile = image.profile;
        this.additional = image.additional;
        this.mask = image.mask;
        this.imageMask = image.imageMask;
        this.smask = image.smask;
        this.transparency = image.transparency;
        this.role = image.role;
        if (image.accessibleAttributes != null) {
            this.accessibleAttributes = new HashMap<>(image.accessibleAttributes);
        }
        setId(image.getId());
    }

    public static Image getInstance(Image image) {
        if (image == null) {
            return null;
        }
        try {
            return (Image) image.getClass().getDeclaredConstructor(new Class[]{Image.class}).newInstance(new Object[]{image});
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public int type() {
        return this.type;
    }

    public boolean isJpeg() {
        return this.type == 32;
    }

    public boolean isImgRaw() {
        return this.type == 34;
    }

    public boolean isImgTemplate() {
        return this.type == 35;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(URL url2) {
        this.url = url2;
    }

    public byte[] getRawData() {
        return this.rawData;
    }

    public int getBpc() {
        return this.bpc;
    }

    public PdfTemplate getTemplateData() {
        return this.template[0];
    }

    public void setTemplateData(PdfTemplate pdfTemplate) {
        this.template[0] = pdfTemplate;
    }

    public int getAlignment() {
        return this.alignment;
    }

    public void setAlignment(int i) {
        this.alignment = i;
    }

    public String getAlt() {
        return this.alt;
    }

    public void setAlt(String str) {
        this.alt = str;
        setAccessibleAttribute(PdfName.ALT, new PdfString(str));
    }

    public void setAbsolutePosition(float f, float f2) {
        this.absoluteX = f;
        this.absoluteY = f2;
    }

    public boolean hasAbsoluteX() {
        return !Float.isNaN(this.absoluteX);
    }

    public float getAbsoluteX() {
        return this.absoluteX;
    }

    public boolean hasAbsoluteY() {
        return !Float.isNaN(this.absoluteY);
    }

    public float getAbsoluteY() {
        return this.absoluteY;
    }

    public float getScaledWidth() {
        return this.scaledWidth;
    }

    public float getScaledHeight() {
        return this.scaledHeight;
    }

    public float getPlainWidth() {
        return this.plainWidth;
    }

    public float getPlainHeight() {
        return this.plainHeight;
    }

    public void scaleAbsolute(Rectangle rectangle) {
        scaleAbsolute(rectangle.getWidth(), rectangle.getHeight());
    }

    public void scaleAbsolute(float f, float f2) {
        this.plainWidth = f;
        this.plainHeight = f2;
        float[] matrix = matrix();
        this.scaledWidth = matrix[6] - matrix[4];
        this.scaledHeight = matrix[7] - matrix[5];
        setWidthPercentage(0.0f);
    }

    public void scaleAbsoluteWidth(float f) {
        this.plainWidth = f;
        float[] matrix = matrix();
        this.scaledWidth = matrix[6] - matrix[4];
        this.scaledHeight = matrix[7] - matrix[5];
        setWidthPercentage(0.0f);
    }

    public void scaleAbsoluteHeight(float f) {
        this.plainHeight = f;
        float[] matrix = matrix();
        this.scaledWidth = matrix[6] - matrix[4];
        this.scaledHeight = matrix[7] - matrix[5];
        setWidthPercentage(0.0f);
    }

    public void scalePercent(float f) {
        scalePercent(f, f);
    }

    public void scalePercent(float f, float f2) {
        this.plainWidth = (getWidth() * f) / 100.0f;
        this.plainHeight = (getHeight() * f2) / 100.0f;
        float[] matrix = matrix();
        this.scaledWidth = matrix[6] - matrix[4];
        this.scaledHeight = matrix[7] - matrix[5];
        setWidthPercentage(0.0f);
    }

    public void scaleToFit(Rectangle rectangle) {
        scaleToFit(rectangle.getWidth(), rectangle.getHeight());
    }

    public void scaleToFit(float f, float f2) {
        scalePercent(100.0f);
        float scaledWidth2 = (f * 100.0f) / getScaledWidth();
        float scaledHeight2 = (f2 * 100.0f) / getScaledHeight();
        if (scaledWidth2 >= scaledHeight2) {
            scaledWidth2 = scaledHeight2;
        }
        scalePercent(scaledWidth2);
        setWidthPercentage(0.0f);
    }

    public float[] matrix() {
        return matrix(1.0f);
    }

    public float[] matrix(float f) {
        float[] fArr = new float[8];
        float cos = (float) Math.cos((double) this.rotationRadians);
        float sin = (float) Math.sin((double) this.rotationRadians);
        float f2 = this.plainWidth;
        fArr[0] = f2 * cos * f;
        fArr[1] = f2 * sin * f;
        float f3 = this.plainHeight;
        fArr[2] = (-f3) * sin * f;
        fArr[3] = f3 * cos * f;
        float f4 = this.rotationRadians;
        if (((double) f4) < 1.5707963267948966d) {
            fArr[4] = fArr[2];
            fArr[5] = 0.0f;
            fArr[6] = fArr[0];
            fArr[7] = fArr[1] + fArr[3];
        } else if (((double) f4) < 3.141592653589793d) {
            fArr[4] = fArr[0] + fArr[2];
            fArr[5] = fArr[3];
            fArr[6] = 0.0f;
            fArr[7] = fArr[1];
        } else if (((double) f4) < 4.71238898038469d) {
            fArr[4] = fArr[0];
            fArr[5] = fArr[1] + fArr[3];
            fArr[6] = fArr[2];
            fArr[7] = 0.0f;
        } else {
            fArr[4] = 0.0f;
            fArr[5] = fArr[1];
            fArr[6] = fArr[0] + fArr[2];
            fArr[7] = fArr[3];
        }
        return fArr;
    }

    protected static synchronized Long getSerialId() {
        Long valueOf;
        synchronized (Image.class) {
            long j = serialId + 1;
            serialId = j;
            valueOf = Long.valueOf(j);
        }
        return valueOf;
    }

    public Long getMySerialId() {
        return this.mySerialId;
    }

    public float getImageRotation() {
        float f = (float) (((double) (this.rotationRadians - this.initialRotation)) % 6.283185307179586d);
        return f < 0.0f ? (float) (((double) f) + 6.283185307179586d) : f;
    }

    public void setRotation(float f) {
        float f2 = (float) (((double) (f + this.initialRotation)) % 6.283185307179586d);
        this.rotationRadians = f2;
        if (f2 < 0.0f) {
            this.rotationRadians = (float) (((double) f2) + 6.283185307179586d);
        }
        float[] matrix = matrix();
        this.scaledWidth = matrix[6] - matrix[4];
        this.scaledHeight = matrix[7] - matrix[5];
    }

    public void setRotationDegrees(float f) {
        setRotation((f / 180.0f) * ((float) 3.141592653589793d));
    }

    public float getInitialRotation() {
        return this.initialRotation;
    }

    public void setInitialRotation(float f) {
        this.initialRotation = f;
        setRotation(this.rotationRadians - this.initialRotation);
    }

    public float getIndentationLeft() {
        return this.indentationLeft;
    }

    public void setIndentationLeft(float f) {
        this.indentationLeft = f;
    }

    public float getIndentationRight() {
        return this.indentationRight;
    }

    public void setIndentationRight(float f) {
        this.indentationRight = f;
    }

    public float getSpacingBefore() {
        return this.spacingBefore;
    }

    public void setSpacingBefore(float f) {
        this.spacingBefore = f;
    }

    public float getSpacingAfter() {
        return this.spacingAfter;
    }

    public void setSpacingAfter(float f) {
        this.spacingAfter = f;
    }

    public float getPaddingTop() {
        return this.paddingTop;
    }

    public void setPaddingTop(float f) {
        this.paddingTop = f;
    }

    public float getWidthPercentage() {
        return this.widthPercentage;
    }

    public void setWidthPercentage(float f) {
        this.widthPercentage = f;
    }

    public boolean isScaleToFitLineWhenOverflow() {
        return this.scaleToFitLineWhenOverflow;
    }

    public void setScaleToFitLineWhenOverflow(boolean z) {
        this.scaleToFitLineWhenOverflow = z;
    }

    public boolean isScaleToFitHeight() {
        return this.scaleToFitHeight;
    }

    public void setScaleToFitHeight(boolean z) {
        this.scaleToFitHeight = z;
    }

    public void setAnnotation(Annotation annotation2) {
        this.annotation = annotation2;
    }

    public Annotation getAnnotation() {
        return this.annotation;
    }

    public PdfOCG getLayer() {
        return this.layer;
    }

    public void setLayer(PdfOCG pdfOCG) {
        this.layer = pdfOCG;
    }

    public boolean isInterpolation() {
        return this.interpolation;
    }

    public void setInterpolation(boolean z) {
        this.interpolation = z;
    }

    public int getOriginalType() {
        return this.originalType;
    }

    public void setOriginalType(int i) {
        this.originalType = i;
    }

    public byte[] getOriginalData() {
        return this.originalData;
    }

    public void setOriginalData(byte[] bArr) {
        this.originalData = bArr;
    }

    public boolean isDeflated() {
        return this.deflated;
    }

    public void setDeflated(boolean z) {
        this.deflated = z;
    }

    public int getDpiX() {
        return this.dpiX;
    }

    public int getDpiY() {
        return this.dpiY;
    }

    public void setDpi(int i, int i2) {
        this.dpiX = i;
        this.dpiY = i2;
    }

    public float getXYRatio() {
        return this.XYRatio;
    }

    public void setXYRatio(float f) {
        this.XYRatio = f;
    }

    public int getColorspace() {
        return this.colorspace;
    }

    public void setColorTransform(int i) {
        this.colortransform = i;
    }

    public int getColorTransform() {
        return this.colortransform;
    }

    public boolean isInverted() {
        return this.invert;
    }

    public void setInverted(boolean z) {
        this.invert = z;
    }

    public void tagICC(ICC_Profile iCC_Profile) {
        this.profile = iCC_Profile;
    }

    public boolean hasICCProfile() {
        return this.profile != null;
    }

    public ICC_Profile getICCProfile() {
        return this.profile;
    }

    public PdfDictionary getAdditional() {
        return this.additional;
    }

    public void setAdditional(PdfDictionary pdfDictionary) {
        this.additional = pdfDictionary;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.itextpdf.text.pdf.PdfArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: com.itextpdf.text.pdf.PdfObject} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.itextpdf.text.pdf.PdfArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: com.itextpdf.text.pdf.PdfArray} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void simplifyColorspace() {
        /*
            r3 = this;
            com.itextpdf.text.pdf.PdfDictionary r0 = r3.additional
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.COLORSPACE
            com.itextpdf.text.pdf.PdfArray r0 = r0.getAsArray(r1)
            if (r0 != 0) goto L_0x000e
            return
        L_0x000e:
            com.itextpdf.text.pdf.PdfObject r1 = r3.simplifyColorspace(r0)
            boolean r2 = r1.isName()
            if (r2 == 0) goto L_0x001a
            r0 = r1
            goto L_0x003c
        L_0x001a:
            r1 = 0
            com.itextpdf.text.pdf.PdfName r1 = r0.getAsName(r1)
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.INDEXED
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x003c
            int r1 = r0.size()
            r2 = 2
            if (r1 < r2) goto L_0x003c
            r1 = 1
            com.itextpdf.text.pdf.PdfArray r2 = r0.getAsArray(r1)
            if (r2 == 0) goto L_0x003c
            com.itextpdf.text.pdf.PdfObject r2 = r3.simplifyColorspace(r2)
            r0.set(r1, r2)
        L_0x003c:
            com.itextpdf.text.pdf.PdfDictionary r1 = r3.additional
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.COLORSPACE
            r1.put(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Image.simplifyColorspace():void");
    }

    private PdfObject simplifyColorspace(PdfArray pdfArray) {
        if (pdfArray == null) {
            return pdfArray;
        }
        PdfName asName = pdfArray.getAsName(0);
        if (PdfName.CALGRAY.equals(asName)) {
            return PdfName.DEVICEGRAY;
        }
        return PdfName.CALRGB.equals(asName) ? PdfName.DEVICERGB : pdfArray;
    }

    public boolean isMask() {
        return this.mask;
    }

    public void makeMask() throws DocumentException {
        if (isMaskCandidate()) {
            this.mask = true;
            return;
        }
        throw new DocumentException(MessageLocalization.getComposedMessage("this.image.can.not.be.an.image.mask", new Object[0]));
    }

    public boolean isMaskCandidate() {
        if ((this.type != 34 || this.bpc <= 255) && this.colorspace != 1) {
            return false;
        }
        return true;
    }

    public Image getImageMask() {
        return this.imageMask;
    }

    public void setImageMask(Image image) throws DocumentException {
        boolean z = false;
        if (this.mask) {
            throw new DocumentException(MessageLocalization.getComposedMessage("an.image.mask.cannot.contain.another.image.mask", new Object[0]));
        } else if (image.mask) {
            this.imageMask = image;
            int i = image.bpc;
            if (i > 1 && i <= 8) {
                z = true;
            }
            this.smask = z;
        } else {
            throw new DocumentException(MessageLocalization.getComposedMessage("the.image.mask.is.not.a.mask.did.you.do.makemask", new Object[0]));
        }
    }

    public boolean isSmask() {
        return this.smask;
    }

    public void setSmask(boolean z) {
        this.smask = z;
    }

    public int[] getTransparency() {
        return this.transparency;
    }

    public void setTransparency(int[] iArr) {
        this.transparency = iArr;
    }

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public void setCompressionLevel(int i) {
        if (i < 0 || i > 9) {
            this.compressionLevel = -1;
        } else {
            this.compressionLevel = i;
        }
    }

    public PdfObject getAccessibleAttribute(PdfName pdfName) {
        HashMap<PdfName, PdfObject> hashMap = this.accessibleAttributes;
        if (hashMap != null) {
            return hashMap.get(pdfName);
        }
        return null;
    }

    public void setAccessibleAttribute(PdfName pdfName, PdfObject pdfObject) {
        if (this.accessibleAttributes == null) {
            this.accessibleAttributes = new HashMap<>();
        }
        this.accessibleAttributes.put(pdfName, pdfObject);
    }

    public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
        return this.accessibleAttributes;
    }

    public PdfName getRole() {
        return this.role;
    }

    public void setRole(PdfName pdfName) {
        this.role = pdfName;
    }

    public AccessibleElementId getId() {
        if (this.f450id == null) {
            this.f450id = new AccessibleElementId();
        }
        return this.f450id;
    }

    public void setId(AccessibleElementId accessibleElementId) {
        this.f450id = accessibleElementId;
    }

    /* JADX WARNING: Removed duplicated region for block: B:112:0x01fb  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.text.Image getInstance(java.awt.Image r18, java.awt.Color r19, boolean r20) throws com.itextpdf.text.BadElementException, java.io.IOException {
        /*
            r0 = r18
            boolean r1 = r0 instanceof java.awt.image.BufferedImage
            r8 = 1
            if (r1 == 0) goto L_0x001e
            r1 = r0
            java.awt.image.BufferedImage r1 = (java.awt.image.BufferedImage) r1
            int r2 = r1.getType()
            r3 = 12
            if (r2 != r3) goto L_0x001e
            java.awt.image.ColorModel r1 = r1.getColorModel()
            int r1 = r1.getPixelSize()
            if (r1 != r8) goto L_0x001e
            r9 = 1
            goto L_0x0020
        L_0x001e:
            r9 = r20
        L_0x0020:
            java.awt.image.PixelGrabber r10 = new java.awt.image.PixelGrabber
            r3 = 0
            r4 = 0
            r5 = -1
            r6 = -1
            r7 = 1
            r1 = r10
            r2 = r18
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r0 = 0
            r10.grabPixels()     // Catch:{ InterruptedException -> 0x0209 }
            int r1 = r10.getStatus()
            r2 = 128(0x80, float:1.794E-43)
            r1 = r1 & r2
            if (r1 != 0) goto L_0x01fb
            int r1 = r10.getWidth()
            int r3 = r10.getHeight()
            java.lang.Object r4 = r10.getPixels()
            int[] r4 = (int[]) r4
            int[] r4 = (int[]) r4
            r5 = 2
            r6 = 250(0xfa, float:3.5E-43)
            r10 = 255(0xff, float:3.57E-43)
            if (r9 == 0) goto L_0x0103
            int r9 = r1 / 8
            r11 = r1 & 7
            if (r11 == 0) goto L_0x0059
            r11 = 1
            goto L_0x005a
        L_0x0059:
            r11 = 0
        L_0x005a:
            int r9 = r9 + r11
            int r9 = r9 * r3
            byte[] r15 = new byte[r9]
            int r9 = r3 * r1
            if (r19 == 0) goto L_0x0077
            int r11 = r19.getRed()
            int r12 = r19.getGreen()
            int r11 = r11 + r12
            int r12 = r19.getBlue()
            int r11 = r11 + r12
            r12 = 384(0x180, float:5.38E-43)
            if (r11 >= r12) goto L_0x0077
            r11 = 0
            goto L_0x0078
        L_0x0077:
            r11 = 1
        L_0x0078:
            if (r19 == 0) goto L_0x00b6
            r5 = 0
            r12 = 0
            r13 = 128(0x80, float:1.794E-43)
            r14 = 0
            r16 = 0
        L_0x0081:
            if (r5 >= r9) goto L_0x00b3
            r17 = r4[r5]
            int r2 = r17 >> 24
            r2 = r2 & r10
            if (r2 >= r6) goto L_0x008d
            if (r11 != r8) goto L_0x0094
            goto L_0x0093
        L_0x008d:
            r2 = r4[r5]
            r2 = r2 & 2184(0x888, float:3.06E-42)
            if (r2 == 0) goto L_0x0094
        L_0x0093:
            r12 = r12 | r13
        L_0x0094:
            int r2 = r13 >> 1
            if (r2 == 0) goto L_0x009f
            int r13 = r14 + 1
            if (r13 < r1) goto L_0x009d
            goto L_0x009f
        L_0x009d:
            r13 = r2
            goto L_0x00a9
        L_0x009f:
            int r2 = r16 + 1
            byte r12 = (byte) r12
            r15[r16] = r12
            r16 = r2
            r12 = 0
            r13 = 128(0x80, float:1.794E-43)
        L_0x00a9:
            int r14 = r14 + 1
            if (r14 < r1) goto L_0x00ae
            r14 = 0
        L_0x00ae:
            int r5 = r5 + 1
            r2 = 128(0x80, float:1.794E-43)
            goto L_0x0081
        L_0x00b3:
            r16 = 0
            goto L_0x00fa
        L_0x00b6:
            r2 = 0
            r6 = 0
            r7 = 0
            r11 = 128(0x80, float:1.794E-43)
            r12 = 0
            r13 = 0
        L_0x00bd:
            if (r2 >= r9) goto L_0x00f8
            if (r7 != 0) goto L_0x00d8
            r14 = r4[r2]
            int r14 = r14 >> 24
            r14 = r14 & r10
            if (r14 != 0) goto L_0x00d8
            int[] r7 = new int[r5]
            r14 = r4[r2]
            r14 = r14 & 2184(0x888, float:3.06E-42)
            if (r14 == 0) goto L_0x00d3
            r14 = 255(0xff, float:3.57E-43)
            goto L_0x00d4
        L_0x00d3:
            r14 = 0
        L_0x00d4:
            r7[r8] = r14
            r7[r0] = r14
        L_0x00d8:
            r14 = r4[r2]
            r14 = r14 & 2184(0x888, float:3.06E-42)
            if (r14 == 0) goto L_0x00df
            r13 = r13 | r11
        L_0x00df:
            int r11 = r11 >> 1
            if (r11 == 0) goto L_0x00e7
            int r14 = r12 + 1
            if (r14 < r1) goto L_0x00f0
        L_0x00e7:
            int r11 = r6 + 1
            byte r13 = (byte) r13
            r15[r6] = r13
            r6 = r11
            r11 = 128(0x80, float:1.794E-43)
            r13 = 0
        L_0x00f0:
            int r12 = r12 + 1
            if (r12 < r1) goto L_0x00f5
            r12 = 0
        L_0x00f5:
            int r2 = r2 + 1
            goto L_0x00bd
        L_0x00f8:
            r16 = r7
        L_0x00fa:
            r13 = 1
            r14 = 1
            r11 = r1
            r12 = r3
            com.itextpdf.text.Image r0 = getInstance((int) r11, (int) r12, (int) r13, (int) r14, (byte[]) r15, (int[]) r16)
            return r0
        L_0x0103:
            int r2 = r1 * r3
            int r9 = r2 * 3
            byte[] r15 = new byte[r9]
            if (r19 == 0) goto L_0x0118
            int r9 = r19.getRed()
            int r11 = r19.getGreen()
            int r12 = r19.getBlue()
            goto L_0x011e
        L_0x0118:
            r9 = 255(0xff, float:3.57E-43)
            r11 = 255(0xff, float:3.57E-43)
            r12 = 255(0xff, float:3.57E-43)
        L_0x011e:
            r14 = 8
            if (r19 == 0) goto L_0x0163
            r5 = 0
        L_0x0123:
            if (r0 >= r2) goto L_0x015d
            r13 = r4[r0]
            int r13 = r13 >> 24
            r13 = r13 & r10
            if (r13 >= r6) goto L_0x013c
            int r13 = r5 + 1
            byte r6 = (byte) r9
            r15[r5] = r6
            int r5 = r13 + 1
            byte r6 = (byte) r11
            r15[r13] = r6
            int r6 = r5 + 1
            byte r13 = (byte) r12
            r15[r5] = r13
            goto L_0x0157
        L_0x013c:
            int r6 = r5 + 1
            r13 = r4[r0]
            int r13 = r13 >> 16
            r13 = r13 & r10
            byte r13 = (byte) r13
            r15[r5] = r13
            int r5 = r6 + 1
            r13 = r4[r0]
            int r13 = r13 >> r14
            r13 = r13 & r10
            byte r13 = (byte) r13
            r15[r6] = r13
            int r6 = r5 + 1
            r13 = r4[r0]
            r13 = r13 & r10
            byte r13 = (byte) r13
            r15[r5] = r13
        L_0x0157:
            r5 = r6
            int r0 = r0 + 1
            r6 = 250(0xfa, float:3.5E-43)
            goto L_0x0123
        L_0x015d:
            r0 = 0
            r7 = 0
            r16 = 8
            goto L_0x01d9
        L_0x0163:
            byte[] r6 = new byte[r2]
            r9 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r16 = 0
        L_0x016b:
            if (r9 >= r2) goto L_0x01d0
            r17 = r4[r9]
            int r7 = r17 >> 24
            r7 = r7 & r10
            byte r7 = (byte) r7
            r6[r9] = r7
            if (r11 != 0) goto L_0x01aa
            if (r7 == 0) goto L_0x017e
            r14 = -1
            if (r7 == r14) goto L_0x017e
        L_0x017c:
            r11 = 1
            goto L_0x01aa
        L_0x017e:
            r14 = 16777215(0xffffff, float:2.3509886E-38)
            if (r12 != 0) goto L_0x01a4
            if (r7 != 0) goto L_0x01aa
            r7 = r4[r9]
            r13 = r7 & r14
            r7 = 6
            int[] r12 = new int[r7]
            int r7 = r13 >> 16
            r7 = r7 & r10
            r12[r8] = r7
            r12[r0] = r7
            int r7 = r13 >> 8
            r7 = r7 & r10
            r14 = 3
            r12[r14] = r7
            r12[r5] = r7
            r7 = 4
            r14 = 5
            r5 = r13 & 255(0xff, float:3.57E-43)
            r12[r14] = r5
            r12[r7] = r5
            goto L_0x01aa
        L_0x01a4:
            r5 = r4[r9]
            r5 = r5 & r14
            if (r5 == r13) goto L_0x01aa
            goto L_0x017c
        L_0x01aa:
            int r5 = r16 + 1
            r7 = r4[r9]
            int r7 = r7 >> 16
            r7 = r7 & r10
            byte r7 = (byte) r7
            r15[r16] = r7
            int r7 = r5 + 1
            r14 = r4[r9]
            r16 = 8
            int r14 = r14 >> 8
            r14 = r14 & r10
            byte r14 = (byte) r14
            r15[r5] = r14
            int r5 = r7 + 1
            r14 = r4[r9]
            r14 = r14 & r10
            byte r14 = (byte) r14
            r15[r7] = r14
            int r9 = r9 + 1
            r16 = r5
            r5 = 2
            r14 = 8
            goto L_0x016b
        L_0x01d0:
            r16 = 8
            if (r11 == 0) goto L_0x01d7
            r7 = r6
            r0 = 0
            goto L_0x01d9
        L_0x01d7:
            r0 = r12
            r7 = 0
        L_0x01d9:
            r13 = 3
            r14 = 8
            r11 = r1
            r12 = r3
            r2 = 8
            r16 = r0
            com.itextpdf.text.Image r0 = getInstance((int) r11, (int) r12, (int) r13, (int) r14, (byte[]) r15, (int[]) r16)
            if (r7 == 0) goto L_0x01fa
            com.itextpdf.text.Image r1 = getInstance(r1, r3, r8, r2, r7)
            r1.makeMask()     // Catch:{ DocumentException -> 0x01f3 }
            r0.setImageMask(r1)     // Catch:{ DocumentException -> 0x01f3 }
            goto L_0x01fa
        L_0x01f3:
            r0 = move-exception
            com.itextpdf.text.ExceptionConverter r1 = new com.itextpdf.text.ExceptionConverter
            r1.<init>(r0)
            throw r1
        L_0x01fa:
            return r0
        L_0x01fb:
            java.io.IOException r1 = new java.io.IOException
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r2 = "java.awt.image.fetch.aborted.or.errored"
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r2, (java.lang.Object[]) r0)
            r1.<init>(r0)
            throw r1
        L_0x0209:
            java.io.IOException r1 = new java.io.IOException
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r2 = "java.awt.image.interrupted.waiting.for.pixels"
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r2, (java.lang.Object[]) r0)
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Image.getInstance(java.awt.Image, java.awt.Color, boolean):com.itextpdf.text.Image");
    }

    public static Image getInstance(java.awt.Image image, Color color) throws BadElementException, IOException {
        return getInstance(image, color, false);
    }

    public static Image getInstance(PdfWriter pdfWriter, java.awt.Image image, float f) throws BadElementException, IOException {
        return getInstance(new PdfContentByte(pdfWriter), image, f);
    }

    public static Image getInstance(PdfContentByte pdfContentByte, java.awt.Image image, float f) throws BadElementException, IOException {
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, -1, -1, true);
        try {
            pixelGrabber.grabPixels();
            if ((pixelGrabber.getStatus() & 128) == 0) {
                float width = (float) pixelGrabber.getWidth();
                float height = (float) pixelGrabber.getHeight();
                PdfTemplate createTemplate = pdfContentByte.createTemplate(width, height);
                PdfGraphics2D pdfGraphics2D = new PdfGraphics2D(createTemplate, width, height, (FontMapper) null, false, true, f);
                pdfGraphics2D.drawImage(image, 0, 0, (ImageObserver) null);
                pdfGraphics2D.dispose();
                return getInstance(createTemplate);
            }
            throw new IOException(MessageLocalization.getComposedMessage("java.awt.image.fetch.aborted.or.errored", new Object[0]));
        } catch (InterruptedException unused) {
            throw new IOException(MessageLocalization.getComposedMessage("java.awt.image.interrupted.waiting.for.pixels", new Object[0]));
        }
    }
}
