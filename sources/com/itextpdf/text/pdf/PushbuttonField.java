package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.IOException;

public class PushbuttonField extends BaseField {
    public static final int LAYOUT_ICON_LEFT_LABEL_RIGHT = 5;
    public static final int LAYOUT_ICON_ONLY = 2;
    public static final int LAYOUT_ICON_TOP_LABEL_BOTTOM = 3;
    public static final int LAYOUT_LABEL_LEFT_ICON_RIGHT = 6;
    public static final int LAYOUT_LABEL_ONLY = 1;
    public static final int LAYOUT_LABEL_OVER_ICON = 7;
    public static final int LAYOUT_LABEL_TOP_ICON_BOTTOM = 4;
    public static final int SCALE_ICON_ALWAYS = 1;
    public static final int SCALE_ICON_IS_TOO_BIG = 3;
    public static final int SCALE_ICON_IS_TOO_SMALL = 4;
    public static final int SCALE_ICON_NEVER = 2;
    private boolean iconFitToBounds;
    private float iconHorizontalAdjustment = 0.5f;
    private PRIndirectReference iconReference;
    private float iconVerticalAdjustment = 0.5f;
    private Image image;
    private int layout = 1;
    private boolean proportionalIcon = true;
    private int scaleIcon = 1;
    private PdfTemplate template;

    /* renamed from: tp */
    private PdfTemplate f690tp;

    public PushbuttonField(PdfWriter pdfWriter, Rectangle rectangle, String str) {
        super(pdfWriter, rectangle, str);
    }

    public int getLayout() {
        return this.layout;
    }

    public void setLayout(int i) {
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("layout.out.of.bounds", new Object[0]));
        }
        this.layout = i;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image2) {
        this.image = image2;
        this.template = null;
    }

    public PdfTemplate getTemplate() {
        return this.template;
    }

    public void setTemplate(PdfTemplate pdfTemplate) {
        this.template = pdfTemplate;
        this.image = null;
    }

    public int getScaleIcon() {
        return this.scaleIcon;
    }

    public void setScaleIcon(int i) {
        if (i < 1 || i > 4) {
            i = 1;
        }
        this.scaleIcon = i;
    }

    public boolean isProportionalIcon() {
        return this.proportionalIcon;
    }

    public void setProportionalIcon(boolean z) {
        this.proportionalIcon = z;
    }

    public float getIconVerticalAdjustment() {
        return this.iconVerticalAdjustment;
    }

    public void setIconVerticalAdjustment(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        this.iconVerticalAdjustment = f;
    }

    public float getIconHorizontalAdjustment() {
        return this.iconHorizontalAdjustment;
    }

    public void setIconHorizontalAdjustment(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        this.iconHorizontalAdjustment = f;
    }

    private float calculateFontSize(float f, float f2) throws IOException, DocumentException {
        BaseFont realFont = getRealFont();
        float f3 = this.fontSize;
        if (f3 != 0.0f) {
            return f3;
        }
        float widthPoint = realFont.getWidthPoint(this.text, 1.0f);
        float min = Math.min(widthPoint == 0.0f ? 12.0f : f / widthPoint, f2 / (1.0f - realFont.getFontDescriptor(3, 1.0f)));
        if (min < 4.0f) {
            return 4.0f;
        }
        return min;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0265, code lost:
        if (r6 == 7) goto L_0x026f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0268, code lost:
        if (r6 != 2) goto L_0x026b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x026b, code lost:
        r14 = r19;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x026f, code lost:
        r3 = new com.itextpdf.text.Rectangle(r11.getLeft() + r4, r11.getBottom() + r4, r11.getRight() - r4, r11.getTop() - r4);
        r14 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x009c, code lost:
        if (r0.iconReference == null) goto L_0x0145;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0145, code lost:
        r6 = 1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x038b  */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x0492  */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x049d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.PdfAppearance getAppearance() throws java.io.IOException, com.itextpdf.text.DocumentException {
        /*
            r31 = this;
            r0 = r31
            com.itextpdf.text.pdf.PdfAppearance r10 = r31.getBorderAppearance()
            com.itextpdf.text.Rectangle r11 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Rectangle r1 = r10.getBoundingBox()
            r11.<init>((com.itextpdf.text.Rectangle) r1)
            java.lang.String r1 = r0.text
            r2 = 1
            if (r1 == 0) goto L_0x001c
            java.lang.String r1 = r0.text
            int r1 = r1.length()
            if (r1 != 0) goto L_0x002e
        L_0x001c:
            int r1 = r0.layout
            if (r1 == r2) goto L_0x04d8
            com.itextpdf.text.Image r1 = r0.image
            if (r1 != 0) goto L_0x002e
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.template
            if (r1 != 0) goto L_0x002e
            com.itextpdf.text.pdf.PRIndirectReference r1 = r0.iconReference
            if (r1 != 0) goto L_0x002e
            goto L_0x04d8
        L_0x002e:
            int r1 = r0.layout
            r3 = 2
            if (r1 != r3) goto L_0x0040
            com.itextpdf.text.Image r1 = r0.image
            if (r1 != 0) goto L_0x0040
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.template
            if (r1 != 0) goto L_0x0040
            com.itextpdf.text.pdf.PRIndirectReference r1 = r0.iconReference
            if (r1 != 0) goto L_0x0040
            return r10
        L_0x0040:
            com.itextpdf.text.pdf.BaseFont r12 = r31.getRealFont()
            int r1 = r0.borderStyle
            r5 = 3
            if (r1 == r3) goto L_0x0050
            int r1 = r0.borderStyle
            if (r1 != r5) goto L_0x004e
            goto L_0x0050
        L_0x004e:
            r1 = 0
            goto L_0x0051
        L_0x0050:
            r1 = 1
        L_0x0051:
            r11.getHeight()
            float r6 = r0.borderWidth
            float r6 = r0.borderWidth
            r7 = 1073741824(0x40000000, float:2.0)
            if (r1 == 0) goto L_0x0060
            float r8 = r0.borderWidth
            float r6 = r6 * r7
        L_0x0060:
            if (r1 == 0) goto L_0x0067
            float r1 = r0.borderWidth
            float r1 = r1 * r7
            goto L_0x0069
        L_0x0067:
            float r1 = r0.borderWidth
        L_0x0069:
            r8 = 1065353216(0x3f800000, float:1.0)
            float r1 = java.lang.Math.max(r1, r8)
            float r13 = java.lang.Math.min(r6, r1)
            r1 = 0
            r0.f690tp = r1
            float r9 = r0.fontSize
            float r14 = r11.getWidth()
            float r15 = r13 * r7
            float r14 = r14 - r15
            float r14 = r14 - r7
            float r16 = r11.getHeight()
            float r1 = r16 - r15
            boolean r4 = r0.iconFitToBounds
            r18 = 0
            if (r4 == 0) goto L_0x008e
            r4 = 0
            goto L_0x0090
        L_0x008e:
            float r4 = r13 + r8
        L_0x0090:
            int r6 = r0.layout
            com.itextpdf.text.Image r3 = r0.image
            if (r3 != 0) goto L_0x00a0
            com.itextpdf.text.pdf.PdfTemplate r3 = r0.template
            if (r3 != 0) goto L_0x00a0
            com.itextpdf.text.pdf.PRIndirectReference r3 = r0.iconReference
            if (r3 != 0) goto L_0x00a0
            goto L_0x0145
        L_0x00a0:
            r3 = 1082130432(0x40800000, float:4.0)
            r20 = 1051931443(0x3eb33333, float:0.35)
            switch(r6) {
                case 1: goto L_0x022f;
                case 2: goto L_0x0261;
                case 3: goto L_0x01d6;
                case 4: goto L_0x0179;
                case 5: goto L_0x010f;
                case 6: goto L_0x00ae;
                case 7: goto L_0x022f;
                default: goto L_0x00a8;
            }
        L_0x00a8:
            r1 = 0
            r3 = 0
            r14 = 2143289344(0x7fc00000, float:NaN)
            goto L_0x028a
        L_0x00ae:
            java.lang.String r6 = r0.text
            if (r6 == 0) goto L_0x022b
            java.lang.String r6 = r0.text
            int r6 = r6.length()
            if (r6 == 0) goto L_0x022b
            int r6 = (r14 > r18 ? 1 : (r14 == r18 ? 0 : -1))
            if (r6 <= 0) goto L_0x022b
            int r6 = (r1 > r18 ? 1 : (r1 == r18 ? 0 : -1))
            if (r6 > 0) goto L_0x00c4
            goto L_0x022b
        L_0x00c4:
            float r6 = r11.getWidth()
            float r6 = r6 * r20
            float r6 = r6 - r13
            int r9 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1))
            if (r9 <= 0) goto L_0x00d5
            float r3 = r0.calculateFontSize(r14, r6)
            r9 = r3
            goto L_0x00d7
        L_0x00d5:
            r9 = 1082130432(0x40800000, float:4.0)
        L_0x00d7:
            java.lang.String r3 = r0.text
            float r3 = r12.getWidthPoint((java.lang.String) r3, (float) r9)
            int r3 = (r3 > r14 ? 1 : (r3 == r14 ? 0 : -1))
            if (r3 < 0) goto L_0x00e4
            float r9 = r0.fontSize
            goto L_0x0145
        L_0x00e4:
            float r6 = r13 + r8
            float r1 = r11.getHeight()
            float r3 = r12.getFontDescriptor(r2, r9)
            float r1 = r1 - r3
            float r1 = r1 / r7
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            java.lang.String r7 = r0.text
            float r7 = r12.getWidthPoint((java.lang.String) r7, (float) r9)
            float r7 = r7 + r6
            float r14 = r11.getBottom()
            float r14 = r14 + r4
            float r19 = r11.getRight()
            float r5 = r19 - r4
            float r19 = r11.getTop()
            float r4 = r19 - r4
            r3.<init>(r7, r14, r5, r4)
            goto L_0x0229
        L_0x010f:
            java.lang.String r5 = r0.text
            if (r5 == 0) goto L_0x022b
            java.lang.String r5 = r0.text
            int r5 = r5.length()
            if (r5 == 0) goto L_0x022b
            int r5 = (r14 > r18 ? 1 : (r14 == r18 ? 0 : -1))
            if (r5 <= 0) goto L_0x022b
            int r5 = (r1 > r18 ? 1 : (r1 == r18 ? 0 : -1))
            if (r5 > 0) goto L_0x0125
            goto L_0x022b
        L_0x0125:
            float r5 = r11.getWidth()
            float r5 = r5 * r20
            float r5 = r5 - r13
            int r6 = (r5 > r18 ? 1 : (r5 == r18 ? 0 : -1))
            if (r6 <= 0) goto L_0x0136
            float r3 = r0.calculateFontSize(r14, r5)
            r9 = r3
            goto L_0x0138
        L_0x0136:
            r9 = 1082130432(0x40800000, float:4.0)
        L_0x0138:
            java.lang.String r3 = r0.text
            float r3 = r12.getWidthPoint((java.lang.String) r3, (float) r9)
            int r3 = (r3 > r14 ? 1 : (r3 == r14 ? 0 : -1))
            if (r3 < 0) goto L_0x0148
            float r9 = r0.fontSize
            r5 = 3
        L_0x0145:
            r6 = 1
            goto L_0x00a0
        L_0x0148:
            float r1 = r11.getWidth()
            java.lang.String r3 = r0.text
            float r3 = r12.getWidthPoint((java.lang.String) r3, (float) r9)
            float r1 = r1 - r3
            float r1 = r1 - r13
            float r6 = r1 - r8
            float r1 = r11.getHeight()
            float r3 = r12.getFontDescriptor(r2, r9)
            float r1 = r1 - r3
            float r1 = r1 / r7
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            float r5 = r11.getLeft()
            float r5 = r5 + r4
            float r7 = r11.getBottom()
            float r7 = r7 + r4
            float r14 = r6 - r8
            float r19 = r11.getTop()
            float r4 = r19 - r4
            r3.<init>(r5, r7, r14, r4)
            goto L_0x0229
        L_0x0179:
            java.lang.String r5 = r0.text
            if (r5 == 0) goto L_0x022b
            java.lang.String r5 = r0.text
            int r5 = r5.length()
            if (r5 == 0) goto L_0x022b
            int r5 = (r14 > r18 ? 1 : (r14 == r18 ? 0 : -1))
            if (r5 <= 0) goto L_0x022b
            int r5 = (r1 > r18 ? 1 : (r1 == r18 ? 0 : -1))
            if (r5 > 0) goto L_0x018f
            goto L_0x022b
        L_0x018f:
            float r1 = r11.getHeight()
            float r1 = r1 * r20
            float r1 = r1 - r13
            int r5 = (r1 > r18 ? 1 : (r1 == r18 ? 0 : -1))
            if (r5 <= 0) goto L_0x01a0
            float r1 = r0.calculateFontSize(r14, r1)
            r9 = r1
            goto L_0x01a2
        L_0x01a0:
            r9 = 1082130432(0x40800000, float:4.0)
        L_0x01a2:
            float r1 = r11.getWidth()
            java.lang.String r3 = r0.text
            float r3 = r12.getWidthPoint((java.lang.String) r3, (float) r9)
            float r1 = r1 - r3
            float r6 = r1 / r7
            float r1 = r11.getHeight()
            float r1 = r1 - r13
            float r1 = r1 - r9
            int r3 = (r1 > r13 ? 1 : (r1 == r13 ? 0 : -1))
            if (r3 >= 0) goto L_0x01ba
            r1 = r13
        L_0x01ba:
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            float r5 = r11.getLeft()
            float r5 = r5 + r4
            float r7 = r11.getBottom()
            float r7 = r7 + r4
            float r14 = r11.getRight()
            float r14 = r14 - r4
            r4 = 3
            float r19 = r12.getFontDescriptor(r4, r9)
            float r4 = r1 + r19
            r3.<init>(r5, r7, r14, r4)
            goto L_0x0229
        L_0x01d6:
            java.lang.String r5 = r0.text
            if (r5 == 0) goto L_0x022b
            java.lang.String r5 = r0.text
            int r5 = r5.length()
            if (r5 == 0) goto L_0x022b
            int r5 = (r14 > r18 ? 1 : (r14 == r18 ? 0 : -1))
            if (r5 <= 0) goto L_0x022b
            int r5 = (r1 > r18 ? 1 : (r1 == r18 ? 0 : -1))
            if (r5 > 0) goto L_0x01eb
            goto L_0x022b
        L_0x01eb:
            float r1 = r11.getHeight()
            float r1 = r1 * r20
            float r1 = r1 - r13
            int r5 = (r1 > r18 ? 1 : (r1 == r18 ? 0 : -1))
            if (r5 <= 0) goto L_0x01fc
            float r1 = r0.calculateFontSize(r14, r1)
            r9 = r1
            goto L_0x01fe
        L_0x01fc:
            r9 = 1082130432(0x40800000, float:4.0)
        L_0x01fe:
            float r1 = r11.getWidth()
            java.lang.String r3 = r0.text
            float r3 = r12.getWidthPoint((java.lang.String) r3, (float) r9)
            float r1 = r1 - r3
            float r6 = r1 / r7
            r1 = 3
            float r3 = r12.getFontDescriptor(r1, r9)
            float r1 = r13 - r3
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            float r5 = r11.getLeft()
            float r5 = r5 + r4
            float r7 = r1 + r9
            float r14 = r11.getRight()
            float r14 = r14 - r4
            float r19 = r11.getTop()
            float r4 = r19 - r4
            r3.<init>(r5, r7, r14, r4)
        L_0x0229:
            r14 = r6
            goto L_0x028a
        L_0x022b:
            r5 = 3
            r6 = 2
            goto L_0x00a0
        L_0x022f:
            java.lang.String r3 = r0.text
            if (r3 == 0) goto L_0x0261
            java.lang.String r3 = r0.text
            int r3 = r3.length()
            if (r3 <= 0) goto L_0x0261
            int r3 = (r14 > r18 ? 1 : (r14 == r18 ? 0 : -1))
            if (r3 <= 0) goto L_0x0261
            int r3 = (r1 > r18 ? 1 : (r1 == r18 ? 0 : -1))
            if (r3 <= 0) goto L_0x0261
            float r9 = r0.calculateFontSize(r14, r1)
            float r1 = r11.getWidth()
            java.lang.String r3 = r0.text
            float r3 = r12.getWidthPoint((java.lang.String) r3, (float) r9)
            float r1 = r1 - r3
            float r1 = r1 / r7
            float r3 = r11.getHeight()
            float r5 = r12.getFontDescriptor(r2, r9)
            float r3 = r3 - r5
            float r3 = r3 / r7
            r19 = r1
            r1 = r3
            goto L_0x0264
        L_0x0261:
            r1 = 0
            r19 = 2143289344(0x7fc00000, float:NaN)
        L_0x0264:
            r3 = 7
            if (r6 == r3) goto L_0x026f
            r3 = 2
            if (r6 != r3) goto L_0x026b
            goto L_0x026f
        L_0x026b:
            r14 = r19
            r3 = 0
            goto L_0x028a
        L_0x026f:
            com.itextpdf.text.Rectangle r3 = new com.itextpdf.text.Rectangle
            float r5 = r11.getLeft()
            float r5 = r5 + r4
            float r6 = r11.getBottom()
            float r6 = r6 + r4
            float r7 = r11.getRight()
            float r7 = r7 - r4
            float r14 = r11.getTop()
            float r14 = r14 - r4
            r3.<init>(r5, r6, r7, r14)
            r14 = r19
        L_0x028a:
            float r4 = r11.getBottom()
            float r4 = r4 + r13
            int r4 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r4 >= 0) goto L_0x0298
            float r1 = r11.getBottom()
            float r1 = r1 + r13
        L_0x0298:
            r7 = r1
            if (r3 == 0) goto L_0x02ac
            float r1 = r3.getWidth()
            int r1 = (r1 > r18 ? 1 : (r1 == r18 ? 0 : -1))
            if (r1 <= 0) goto L_0x02ab
            float r1 = r3.getHeight()
            int r1 = (r1 > r18 ? 1 : (r1 == r18 ? 0 : -1))
            if (r1 > 0) goto L_0x02ac
        L_0x02ab:
            r3 = 0
        L_0x02ac:
            if (r3 == 0) goto L_0x0384
            com.itextpdf.text.Image r1 = r0.image
            if (r1 == 0) goto L_0x0304
            com.itextpdf.text.pdf.PdfTemplate r1 = new com.itextpdf.text.pdf.PdfTemplate
            com.itextpdf.text.pdf.PdfWriter r4 = r0.writer
            r1.<init>(r4)
            r0.f690tp = r1
            com.itextpdf.text.Rectangle r4 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.Image r5 = r0.image
            r4.<init>((com.itextpdf.text.Rectangle) r5)
            r1.setBoundingBox(r4)
            com.itextpdf.text.pdf.PdfWriter r1 = r0.writer
            com.itextpdf.text.pdf.PdfTemplate r4 = r0.f690tp
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.FRM
            r1.addDirectTemplateSimple(r4, r5)
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.f690tp
            com.itextpdf.text.Image r4 = r0.image
            float r23 = r4.getWidth()
            r24 = 0
            r25 = 0
            com.itextpdf.text.Image r5 = r0.image
            float r26 = r5.getHeight()
            r27 = 0
            r28 = 0
            r21 = r1
            r22 = r4
            r21.addImage((com.itextpdf.text.Image) r22, (float) r23, (float) r24, (float) r25, (float) r26, (float) r27, (float) r28)
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.f690tp
            com.itextpdf.text.Rectangle r1 = r1.getBoundingBox()
            float r1 = r1.getWidth()
            com.itextpdf.text.pdf.PdfTemplate r4 = r0.f690tp
            com.itextpdf.text.Rectangle r4 = r4.getBoundingBox()
            float r4 = r4.getHeight()
        L_0x02ff:
            r2 = 0
        L_0x0300:
            r16 = 1
            goto L_0x0389
        L_0x0304:
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.template
            if (r1 == 0) goto L_0x035c
            com.itextpdf.text.pdf.PdfTemplate r1 = new com.itextpdf.text.pdf.PdfTemplate
            com.itextpdf.text.pdf.PdfWriter r4 = r0.writer
            r1.<init>(r4)
            r0.f690tp = r1
            com.itextpdf.text.Rectangle r4 = new com.itextpdf.text.Rectangle
            com.itextpdf.text.pdf.PdfTemplate r5 = r0.template
            float r5 = r5.getWidth()
            com.itextpdf.text.pdf.PdfTemplate r6 = r0.template
            float r6 = r6.getHeight()
            r4.<init>(r5, r6)
            r1.setBoundingBox(r4)
            com.itextpdf.text.pdf.PdfWriter r1 = r0.writer
            com.itextpdf.text.pdf.PdfTemplate r4 = r0.f690tp
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.FRM
            r1.addDirectTemplateSimple(r4, r5)
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.f690tp
            com.itextpdf.text.pdf.PdfTemplate r4 = r0.template
            com.itextpdf.text.Rectangle r5 = r4.getBoundingBox()
            float r5 = r5.getLeft()
            com.itextpdf.text.pdf.PdfTemplate r6 = r0.template
            com.itextpdf.text.Rectangle r6 = r6.getBoundingBox()
            float r6 = r6.getBottom()
            r1.addTemplate((com.itextpdf.text.pdf.PdfTemplate) r4, (float) r5, (float) r6)
            com.itextpdf.text.pdf.PdfTemplate r1 = r0.f690tp
            com.itextpdf.text.Rectangle r1 = r1.getBoundingBox()
            float r1 = r1.getWidth()
            com.itextpdf.text.pdf.PdfTemplate r4 = r0.f690tp
            com.itextpdf.text.Rectangle r4 = r4.getBoundingBox()
            float r4 = r4.getHeight()
            goto L_0x02ff
        L_0x035c:
            com.itextpdf.text.pdf.PRIndirectReference r1 = r0.iconReference
            if (r1 == 0) goto L_0x0384
            com.itextpdf.text.pdf.PdfObject r1 = com.itextpdf.text.pdf.PdfReader.getPdfObject((com.itextpdf.text.pdf.PdfObject) r1)
            com.itextpdf.text.pdf.PdfDictionary r1 = (com.itextpdf.text.pdf.PdfDictionary) r1
            if (r1 == 0) goto L_0x0384
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.BBOX
            com.itextpdf.text.pdf.PdfArray r4 = r1.getAsArray(r4)
            com.itextpdf.text.Rectangle r4 = com.itextpdf.text.pdf.PdfReader.getNormalizedRectangle(r4)
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.MATRIX
            com.itextpdf.text.pdf.PdfArray r1 = r1.getAsArray(r5)
            float r5 = r4.getWidth()
            float r4 = r4.getHeight()
            r2 = r1
            r1 = r5
            goto L_0x0300
        L_0x0384:
            r1 = 0
            r2 = 0
            r4 = 0
            r16 = 0
        L_0x0389:
            if (r16 == 0) goto L_0x0492
            float r5 = r3.getWidth()
            float r5 = r5 / r1
            float r6 = r3.getHeight()
            float r6 = r6 / r4
            boolean r8 = r0.proportionalIcon
            r17 = r7
            if (r8 == 0) goto L_0x03c8
            int r8 = r0.scaleIcon
            r7 = 2
            if (r8 == r7) goto L_0x03c1
            r7 = 3
            if (r8 == r7) goto L_0x03b6
            r7 = 4
            if (r8 == r7) goto L_0x03ab
            float r8 = java.lang.Math.min(r5, r6)
            goto L_0x03c5
        L_0x03ab:
            float r5 = java.lang.Math.min(r5, r6)
            r7 = 1065353216(0x3f800000, float:1.0)
            float r8 = java.lang.Math.max(r5, r7)
            goto L_0x03c5
        L_0x03b6:
            r7 = 1065353216(0x3f800000, float:1.0)
            float r5 = java.lang.Math.min(r5, r6)
            float r8 = java.lang.Math.min(r5, r7)
            goto L_0x03c5
        L_0x03c1:
            r7 = 1065353216(0x3f800000, float:1.0)
            r8 = 1065353216(0x3f800000, float:1.0)
        L_0x03c5:
            r5 = r8
            r7 = r5
            goto L_0x03f1
        L_0x03c8:
            r7 = 1065353216(0x3f800000, float:1.0)
            int r8 = r0.scaleIcon
            r7 = 2
            if (r8 == r7) goto L_0x03ed
            r7 = 3
            if (r8 == r7) goto L_0x03e2
            r7 = 4
            if (r8 == r7) goto L_0x03d7
        L_0x03d5:
            r7 = r6
            goto L_0x03f1
        L_0x03d7:
            r7 = 1065353216(0x3f800000, float:1.0)
            float r5 = java.lang.Math.max(r5, r7)
            float r6 = java.lang.Math.max(r6, r7)
            goto L_0x03d5
        L_0x03e2:
            r7 = 1065353216(0x3f800000, float:1.0)
            float r5 = java.lang.Math.min(r5, r7)
            float r6 = java.lang.Math.min(r6, r7)
            goto L_0x03d5
        L_0x03ed:
            r7 = 1065353216(0x3f800000, float:1.0)
            r5 = 1065353216(0x3f800000, float:1.0)
        L_0x03f1:
            float r6 = r3.getLeft()
            float r8 = r3.getWidth()
            float r1 = r1 * r5
            float r8 = r8 - r1
            float r1 = r0.iconHorizontalAdjustment
            float r8 = r8 * r1
            float r8 = r8 + r6
            float r1 = r3.getBottom()
            float r6 = r3.getHeight()
            float r4 = r4 * r7
            float r6 = r6 - r4
            float r4 = r0.iconVerticalAdjustment
            float r6 = r6 * r4
            float r16 = r1 + r6
            r10.saveState()
            float r1 = r3.getLeft()
            float r4 = r3.getBottom()
            float r6 = r3.getWidth()
            float r3 = r3.getHeight()
            r10.rectangle((float) r1, (float) r4, (float) r6, (float) r3)
            r10.clip()
            r10.newPath()
            com.itextpdf.text.pdf.PdfTemplate r3 = r0.f690tp
            if (r3 == 0) goto L_0x0445
            r4 = 0
            r6 = 0
            r1 = r10
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r7
            r29 = r17
            r7 = r8
            r8 = r16
            r1.addTemplate((com.itextpdf.text.pdf.PdfTemplate) r2, (float) r3, (float) r4, (float) r5, (float) r6, (float) r7, (float) r8)
            r17 = r12
            r12 = r9
            goto L_0x048e
        L_0x0445:
            r29 = r17
            if (r2 == 0) goto L_0x0471
            int r1 = r2.size()
            r3 = 6
            if (r1 != r3) goto L_0x0471
            r1 = 4
            com.itextpdf.text.pdf.PdfNumber r1 = r2.getAsNumber(r1)
            if (r1 == 0) goto L_0x045c
            float r1 = r1.floatValue()
            goto L_0x045d
        L_0x045c:
            r1 = 0
        L_0x045d:
            r3 = 5
            com.itextpdf.text.pdf.PdfNumber r2 = r2.getAsNumber(r3)
            if (r2 == 0) goto L_0x046f
            float r18 = r2.floatValue()
            r30 = r18
            r18 = r1
            r1 = r30
            goto L_0x0472
        L_0x046f:
            r18 = r1
        L_0x0471:
            r1 = 0
        L_0x0472:
            com.itextpdf.text.pdf.PRIndirectReference r2 = r0.iconReference
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.FRM
            r6 = 0
            r17 = 0
            float r18 = r18 * r5
            float r8 = r8 - r18
            float r1 = r1 * r7
            float r16 = r16 - r1
            r1 = r10
            r4 = r5
            r5 = r6
            r6 = r17
            r17 = r12
            r12 = r9
            r9 = r16
            r1.addTemplateReference((com.itextpdf.text.pdf.PdfIndirectReference) r2, (com.itextpdf.text.pdf.PdfName) r3, (float) r4, (float) r5, (float) r6, (float) r7, (float) r8, (float) r9)
        L_0x048e:
            r10.restoreState()
            goto L_0x0497
        L_0x0492:
            r29 = r7
            r17 = r12
            r12 = r9
        L_0x0497:
            boolean r1 = java.lang.Float.isNaN(r14)
            if (r1 != 0) goto L_0x04d8
            r10.saveState()
            float r1 = r11.getWidth()
            float r1 = r1 - r15
            float r2 = r11.getHeight()
            float r2 = r2 - r15
            r10.rectangle((float) r13, (float) r13, (float) r1, (float) r2)
            r10.clip()
            r10.newPath()
            com.itextpdf.text.BaseColor r1 = r0.textColor
            if (r1 != 0) goto L_0x04bb
            r10.resetGrayFill()
            goto L_0x04c0
        L_0x04bb:
            com.itextpdf.text.BaseColor r1 = r0.textColor
            r10.setColorFill(r1)
        L_0x04c0:
            r10.beginText()
            r1 = r17
            r10.setFontAndSize(r1, r12)
            r1 = r29
            r10.setTextMatrix(r14, r1)
            java.lang.String r1 = r0.text
            r10.showText((java.lang.String) r1)
            r10.endText()
            r10.restoreState()
        L_0x04d8:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PushbuttonField.getAppearance():com.itextpdf.text.pdf.PdfAppearance");
    }

    public PdfFormField getField() throws IOException, DocumentException {
        PdfFormField createPushButton = PdfFormField.createPushButton(this.writer);
        createPushButton.setWidget(this.box, PdfAnnotation.HIGHLIGHT_INVERT);
        if (this.fieldName != null) {
            createPushButton.setFieldName(this.fieldName);
            if ((this.options & 1) != 0) {
                createPushButton.setFieldFlags(1);
            }
            if ((this.options & 2) != 0) {
                createPushButton.setFieldFlags(2);
            }
        }
        if (this.text != null) {
            createPushButton.setMKNormalCaption(this.text);
        }
        if (this.rotation != 0) {
            createPushButton.setMKRotation(this.rotation);
        }
        createPushButton.setBorderStyle(new PdfBorderDictionary(this.borderWidth, this.borderStyle, new PdfDashPattern(3.0f)));
        PdfAppearance appearance = getAppearance();
        createPushButton.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, appearance);
        PdfAppearance pdfAppearance = (PdfAppearance) appearance.getDuplicate();
        pdfAppearance.setFontAndSize(getRealFont(), this.fontSize);
        if (this.textColor == null) {
            pdfAppearance.setGrayFill(0.0f);
        } else {
            pdfAppearance.setColorFill(this.textColor);
        }
        createPushButton.setDefaultAppearanceString(pdfAppearance);
        if (this.borderColor != null) {
            createPushButton.setMKBorderColor(this.borderColor);
        }
        if (this.backgroundColor != null) {
            createPushButton.setMKBackgroundColor(this.backgroundColor);
        }
        int i = this.visibility;
        if (i == 1) {
            createPushButton.setFlags(6);
        } else if (i != 2) {
            if (i != 3) {
                createPushButton.setFlags(4);
            } else {
                createPushButton.setFlags(36);
            }
        }
        PdfTemplate pdfTemplate = this.f690tp;
        if (pdfTemplate != null) {
            createPushButton.setMKNormalIcon(pdfTemplate);
        }
        createPushButton.setMKTextPosition(this.layout - 1);
        PdfName pdfName = PdfName.f545A;
        int i2 = this.scaleIcon;
        if (i2 == 3) {
            pdfName = PdfName.f551B;
        } else if (i2 == 4) {
            pdfName = PdfName.f642S;
        } else if (i2 == 2) {
            pdfName = PdfName.f610N;
        }
        createPushButton.setMKIconFit(pdfName, this.proportionalIcon ? PdfName.f623P : PdfName.f545A, this.iconHorizontalAdjustment, this.iconVerticalAdjustment, this.iconFitToBounds);
        return createPushButton;
    }

    public boolean isIconFitToBounds() {
        return this.iconFitToBounds;
    }

    public void setIconFitToBounds(boolean z) {
        this.iconFitToBounds = z;
    }

    public PRIndirectReference getIconReference() {
        return this.iconReference;
    }

    public void setIconReference(PRIndirectReference pRIndirectReference) {
        this.iconReference = pRIndirectReference;
    }
}
