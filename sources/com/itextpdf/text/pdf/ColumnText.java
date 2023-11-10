package com.itextpdf.text.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ColumnText {
    public static final int AR_COMPOSEDTASHKEEL = 4;
    public static final int AR_LIG = 8;
    public static final int AR_NOVOWEL = 1;
    public static final int DIGITS_AN2EN = 64;
    public static final int DIGITS_EN2AN = 32;
    public static final int DIGITS_EN2AN_INIT_AL = 128;
    public static final int DIGITS_EN2AN_INIT_LR = 96;
    public static final int DIGIT_TYPE_AN = 0;
    public static final int DIGIT_TYPE_AN_EXTENDED = 256;
    public static final float GLOBAL_SPACE_CHAR_RATIO = 0.0f;
    protected static final int LINE_STATUS_NOLINE = 2;
    protected static final int LINE_STATUS_OFFLIMITS = 1;
    protected static final int LINE_STATUS_OK = 0;
    public static final int NO_MORE_COLUMN = 2;
    public static final int NO_MORE_TEXT = 1;
    public static final int START_COLUMN = 0;
    private final Logger LOGGER = LoggerFactory.getLogger((Class<?>) ColumnText.class);
    private boolean adjustFirstLine = true;
    protected int alignment = 0;
    private int arabicOptions = 0;
    protected BidiLine bidiLine;
    protected PdfContentByte canvas;
    protected PdfContentByte[] canvases;
    protected boolean composite = false;
    protected ColumnText compositeColumn;
    protected LinkedList<Element> compositeElements;
    protected float currentLeading = 16.0f;
    protected float descender;
    protected float extraParagraphSpace = 0.0f;
    private float filledWidth;
    private float firstLineY;
    private boolean firstLineYDone = false;
    protected float fixedLeading = 16.0f;
    protected float followingIndent = 0.0f;
    private boolean ignoreSpacingBefore = true;
    protected float indent = 0.0f;
    private boolean inheritGraphicState = false;
    protected boolean isWordSplit;
    private boolean lastWasNewline = true;
    protected float lastX;
    protected ArrayList<float[]> leftWall;
    protected float leftX;
    protected int lineStatus;
    private int linesWritten;
    protected int listIdx = 0;
    protected float maxY;
    protected float minY;
    protected float multipliedLeading = 0.0f;
    protected boolean rectangularMode = false;
    protected float rectangularWidth = -1.0f;
    private boolean repeatFirstLineIndent = true;
    protected float rightIndent = 0.0f;
    protected ArrayList<float[]> rightWall;
    protected float rightX;
    protected int rowIdx = 0;
    protected int runDirection = 0;
    private float spaceCharRatio = 0.0f;
    private int splittedRow = -2;
    private boolean useAscender = false;
    protected Phrase waitPhrase;
    protected float yLine;

    public static boolean hasMoreText(int i) {
        return (i & 1) == 0;
    }

    public ColumnText(PdfContentByte pdfContentByte) {
        this.canvas = pdfContentByte;
    }

    public static ColumnText duplicate(ColumnText columnText) {
        ColumnText columnText2 = new ColumnText((PdfContentByte) null);
        columnText2.setACopy(columnText);
        return columnText2;
    }

    public ColumnText setACopy(ColumnText columnText) {
        if (columnText != null) {
            setSimpleVars(columnText);
            if (columnText.bidiLine != null) {
                this.bidiLine = new BidiLine(columnText.bidiLine);
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void setSimpleVars(ColumnText columnText) {
        this.maxY = columnText.maxY;
        this.minY = columnText.minY;
        this.alignment = columnText.alignment;
        this.leftWall = null;
        if (columnText.leftWall != null) {
            this.leftWall = new ArrayList<>(columnText.leftWall);
        }
        this.rightWall = null;
        if (columnText.rightWall != null) {
            this.rightWall = new ArrayList<>(columnText.rightWall);
        }
        this.yLine = columnText.yLine;
        this.currentLeading = columnText.currentLeading;
        this.fixedLeading = columnText.fixedLeading;
        this.multipliedLeading = columnText.multipliedLeading;
        this.canvas = columnText.canvas;
        this.canvases = columnText.canvases;
        this.lineStatus = columnText.lineStatus;
        this.indent = columnText.indent;
        this.followingIndent = columnText.followingIndent;
        this.rightIndent = columnText.rightIndent;
        this.extraParagraphSpace = columnText.extraParagraphSpace;
        this.rectangularWidth = columnText.rectangularWidth;
        this.rectangularMode = columnText.rectangularMode;
        this.spaceCharRatio = columnText.spaceCharRatio;
        this.lastWasNewline = columnText.lastWasNewline;
        this.repeatFirstLineIndent = columnText.repeatFirstLineIndent;
        this.linesWritten = columnText.linesWritten;
        this.arabicOptions = columnText.arabicOptions;
        this.runDirection = columnText.runDirection;
        this.descender = columnText.descender;
        this.composite = columnText.composite;
        this.splittedRow = columnText.splittedRow;
        if (columnText.composite) {
            this.compositeElements = new LinkedList<>();
            Iterator it = columnText.compositeElements.iterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();
                if (element instanceof PdfPTable) {
                    this.compositeElements.add(new PdfPTable((PdfPTable) element));
                } else {
                    this.compositeElements.add(element);
                }
            }
            ColumnText columnText2 = columnText.compositeColumn;
            if (columnText2 != null) {
                this.compositeColumn = duplicate(columnText2);
            }
        }
        this.listIdx = columnText.listIdx;
        this.rowIdx = columnText.rowIdx;
        this.firstLineY = columnText.firstLineY;
        this.leftX = columnText.leftX;
        this.rightX = columnText.rightX;
        this.firstLineYDone = columnText.firstLineYDone;
        this.waitPhrase = columnText.waitPhrase;
        this.useAscender = columnText.useAscender;
        this.filledWidth = columnText.filledWidth;
        this.adjustFirstLine = columnText.adjustFirstLine;
        this.inheritGraphicState = columnText.inheritGraphicState;
        this.ignoreSpacingBefore = columnText.ignoreSpacingBefore;
    }

    private void addWaitingPhrase() {
        if (this.bidiLine == null && this.waitPhrase != null) {
            this.bidiLine = new BidiLine();
            for (Chunk pdfChunk : this.waitPhrase.getChunks()) {
                this.bidiLine.addChunk(new PdfChunk(pdfChunk, (PdfAction) null, this.waitPhrase.getTabSettings()));
            }
            this.waitPhrase = null;
        }
    }

    public void addText(Phrase phrase) {
        if (phrase != null && !this.composite) {
            addWaitingPhrase();
            if (this.bidiLine == null) {
                this.waitPhrase = phrase;
                return;
            }
            for (Chunk pdfChunk : phrase.getChunks()) {
                this.bidiLine.addChunk(new PdfChunk(pdfChunk, (PdfAction) null, phrase.getTabSettings()));
            }
        }
    }

    public void setText(Phrase phrase) {
        this.bidiLine = null;
        this.composite = false;
        this.compositeColumn = null;
        this.compositeElements = null;
        this.listIdx = 0;
        this.rowIdx = 0;
        this.splittedRow = -1;
        this.waitPhrase = phrase;
    }

    public void addText(Chunk chunk) {
        if (chunk != null && !this.composite) {
            addText(new Phrase(chunk));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addElement(com.itextpdf.text.Paragraph r7) {
        /*
            r6 = this;
            if (r7 != 0) goto L_0x0003
            return
        L_0x0003:
            boolean r0 = r7 instanceof com.itextpdf.text.Image
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0071
            com.itextpdf.text.Image r7 = (com.itextpdf.text.Image) r7
            com.itextpdf.text.pdf.PdfPTable r0 = new com.itextpdf.text.pdf.PdfPTable
            r0.<init>((int) r2)
            float r3 = r7.getWidthPercentage()
            r4 = 0
            int r5 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r5 != 0) goto L_0x0024
            float r3 = r7.getScaledWidth()
            r0.setTotalWidth((float) r3)
            r0.setLockedWidth(r2)
            goto L_0x0027
        L_0x0024:
            r0.setWidthPercentage(r3)
        L_0x0027:
            float r3 = r7.getSpacingAfter()
            r0.setSpacingAfter(r3)
            float r3 = r7.getSpacingBefore()
            r0.setSpacingBefore(r3)
            int r3 = r7.getAlignment()
            if (r3 == 0) goto L_0x0046
            r5 = 2
            if (r3 == r5) goto L_0x0042
            r0.setHorizontalAlignment(r2)
            goto L_0x0049
        L_0x0042:
            r0.setHorizontalAlignment(r5)
            goto L_0x0049
        L_0x0046:
            r0.setHorizontalAlignment(r1)
        L_0x0049:
            com.itextpdf.text.pdf.PdfPCell r3 = new com.itextpdf.text.pdf.PdfPCell
            r3.<init>((com.itextpdf.text.Image) r7, (boolean) r2)
            r3.setPadding(r4)
            int r4 = r7.getBorder()
            r3.setBorder(r4)
            com.itextpdf.text.BaseColor r4 = r7.getBorderColor()
            r3.setBorderColor(r4)
            float r4 = r7.getBorderWidth()
            r3.setBorderWidth(r4)
            com.itextpdf.text.BaseColor r7 = r7.getBackgroundColor()
            r3.setBackgroundColor(r7)
            r0.addCell((com.itextpdf.text.pdf.PdfPCell) r3)
            r7 = r0
        L_0x0071:
            int r0 = r7.type()
            r3 = 10
            r4 = 23
            if (r0 != r3) goto L_0x0084
            com.itextpdf.text.Paragraph r0 = new com.itextpdf.text.Paragraph
            com.itextpdf.text.Chunk r7 = (com.itextpdf.text.Chunk) r7
            r0.<init>((com.itextpdf.text.Chunk) r7)
        L_0x0082:
            r7 = r0
            goto L_0x00a0
        L_0x0084:
            int r0 = r7.type()
            r3 = 11
            if (r0 != r3) goto L_0x0094
            com.itextpdf.text.Paragraph r0 = new com.itextpdf.text.Paragraph
            com.itextpdf.text.Phrase r7 = (com.itextpdf.text.Phrase) r7
            r0.<init>((com.itextpdf.text.Phrase) r7)
            goto L_0x0082
        L_0x0094:
            int r0 = r7.type()
            if (r0 != r4) goto L_0x00a0
            r0 = r7
            com.itextpdf.text.pdf.PdfPTable r0 = (com.itextpdf.text.pdf.PdfPTable) r0
            r0.init()
        L_0x00a0:
            int r0 = r7.type()
            r3 = 12
            if (r0 == r3) goto L_0x00d5
            int r0 = r7.type()
            r5 = 14
            if (r0 == r5) goto L_0x00d5
            int r0 = r7.type()
            if (r0 == r4) goto L_0x00d5
            int r0 = r7.type()
            r4 = 55
            if (r0 == r4) goto L_0x00d5
            int r0 = r7.type()
            r4 = 37
            if (r0 != r4) goto L_0x00c7
            goto L_0x00d5
        L_0x00c7:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.Object[] r0 = new java.lang.Object[r1]
            java.lang.String r1 = "element.not.allowed"
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r1, (java.lang.Object[]) r0)
            r7.<init>(r0)
            throw r7
        L_0x00d5:
            boolean r0 = r6.composite
            if (r0 != 0) goto L_0x00e7
            r6.composite = r2
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
            r6.compositeElements = r0
            r0 = 0
            r6.bidiLine = r0
            r6.waitPhrase = r0
        L_0x00e7:
            int r0 = r7.type()
            if (r0 != r3) goto L_0x00f9
            com.itextpdf.text.Paragraph r7 = (com.itextpdf.text.Paragraph) r7
            java.util.LinkedList<com.itextpdf.text.Element> r0 = r6.compositeElements
            java.util.List r7 = r7.breakUp()
            r0.addAll(r7)
            return
        L_0x00f9:
            java.util.LinkedList<com.itextpdf.text.Element> r0 = r6.compositeElements
            r0.add(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.ColumnText.addElement(com.itextpdf.text.Element):void");
    }

    public static boolean isAllowedElement(Element element) {
        int type = element.type();
        if (type == 10 || type == 11 || type == 37 || type == 12 || type == 14 || type == 55 || type == 23 || (element instanceof Image)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public ArrayList<float[]> convertColumn(float[] fArr) {
        if (fArr.length >= 4) {
            ArrayList<float[]> arrayList = new ArrayList<>();
            int i = 0;
            while (i < fArr.length - 2) {
                float f = fArr[i];
                float f2 = fArr[i + 1];
                int i2 = i + 2;
                float f3 = fArr[i2];
                float f4 = fArr[i + 3];
                if (f2 != f4) {
                    float f5 = (f - f3) / (f2 - f4);
                    float[] fArr2 = {Math.min(f2, f4), Math.max(f2, f4), f5, f - (f5 * f2)};
                    arrayList.add(fArr2);
                    this.maxY = Math.max(this.maxY, fArr2[1]);
                    this.minY = Math.min(this.minY, fArr2[0]);
                }
                i = i2;
            }
            if (!arrayList.isEmpty()) {
                return arrayList;
            }
            throw new RuntimeException(MessageLocalization.getComposedMessage("no.valid.column.line.found", new Object[0]));
        }
        throw new RuntimeException(MessageLocalization.getComposedMessage("no.valid.column.line.found", new Object[0]));
    }

    /* access modifiers changed from: protected */
    public float findLimitsPoint(ArrayList<float[]> arrayList) {
        this.lineStatus = 0;
        float f = this.yLine;
        if (f < this.minY || f > this.maxY) {
            this.lineStatus = 1;
            return 0.0f;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            float[] fArr = arrayList.get(i);
            float f2 = this.yLine;
            if (f2 >= fArr[0] && f2 <= fArr[1]) {
                return (fArr[2] * f2) + fArr[3];
            }
        }
        this.lineStatus = 2;
        return 0.0f;
    }

    /* access modifiers changed from: protected */
    public float[] findLimitsOneLine() {
        float findLimitsPoint = findLimitsPoint(this.leftWall);
        int i = this.lineStatus;
        if (i == 1 || i == 2) {
            return null;
        }
        float findLimitsPoint2 = findLimitsPoint(this.rightWall);
        if (this.lineStatus == 2) {
            return null;
        }
        return new float[]{findLimitsPoint, findLimitsPoint2};
    }

    /* access modifiers changed from: protected */
    public float[] findLimitsTwoLines() {
        boolean z = false;
        while (true) {
            if (z && this.currentLeading == 0.0f) {
                return null;
            }
            float[] findLimitsOneLine = findLimitsOneLine();
            int i = this.lineStatus;
            if (i == 1) {
                return null;
            }
            this.yLine -= this.currentLeading;
            if (i != 2) {
                float[] findLimitsOneLine2 = findLimitsOneLine();
                int i2 = this.lineStatus;
                if (i2 == 1) {
                    return null;
                }
                if (i2 == 2) {
                    this.yLine -= this.currentLeading;
                } else if (findLimitsOneLine[0] < findLimitsOneLine2[1] && findLimitsOneLine2[0] < findLimitsOneLine[1]) {
                    return new float[]{findLimitsOneLine[0], findLimitsOneLine[1], findLimitsOneLine2[0], findLimitsOneLine2[1]};
                }
            }
            z = true;
        }
    }

    public void setColumns(float[] fArr, float[] fArr2) {
        this.maxY = -1.0E21f;
        this.minY = 1.0E21f;
        setYLine(Math.max(fArr[1], fArr[fArr.length - 1]));
        this.rightWall = convertColumn(fArr2);
        this.leftWall = convertColumn(fArr);
        this.rectangularWidth = -1.0f;
        this.rectangularMode = false;
    }

    public void setSimpleColumn(Phrase phrase, float f, float f2, float f3, float f4, float f5, int i) {
        addText(phrase);
        setSimpleColumn(f, f2, f3, f4, f5, i);
    }

    public void setSimpleColumn(float f, float f2, float f3, float f4, float f5, int i) {
        setLeading(f5);
        this.alignment = i;
        setSimpleColumn(f, f2, f3, f4);
    }

    public void setSimpleColumn(float f, float f2, float f3, float f4) {
        this.leftX = Math.min(f, f3);
        this.maxY = Math.max(f2, f4);
        this.minY = Math.min(f2, f4);
        float max = Math.max(f, f3);
        this.rightX = max;
        this.yLine = this.maxY;
        float f5 = max - this.leftX;
        this.rectangularWidth = f5;
        if (f5 < 0.0f) {
            this.rectangularWidth = 0.0f;
        }
        this.rectangularMode = true;
    }

    public void setSimpleColumn(Rectangle rectangle) {
        setSimpleColumn(rectangle.getLeft(), rectangle.getBottom(), rectangle.getRight(), rectangle.getTop());
    }

    public void setLeading(float f) {
        this.fixedLeading = f;
        this.multipliedLeading = 0.0f;
    }

    public void setLeading(float f, float f2) {
        this.fixedLeading = f;
        this.multipliedLeading = f2;
    }

    public float getLeading() {
        return this.fixedLeading;
    }

    public float getMultipliedLeading() {
        return this.multipliedLeading;
    }

    public void setYLine(float f) {
        this.yLine = f;
    }

    public float getYLine() {
        return this.yLine;
    }

    public int getRowsDrawn() {
        return this.rowIdx;
    }

    public void setAlignment(int i) {
        this.alignment = i;
    }

    public int getAlignment() {
        return this.alignment;
    }

    public void setIndent(float f) {
        setIndent(f, true);
    }

    public void setIndent(float f, boolean z) {
        this.indent = f;
        this.lastWasNewline = true;
        this.repeatFirstLineIndent = z;
    }

    public float getIndent() {
        return this.indent;
    }

    public void setFollowingIndent(float f) {
        this.followingIndent = f;
        this.lastWasNewline = true;
    }

    public float getFollowingIndent() {
        return this.followingIndent;
    }

    public void setRightIndent(float f) {
        this.rightIndent = f;
        this.lastWasNewline = true;
    }

    public float getRightIndent() {
        return this.rightIndent;
    }

    public float getCurrentLeading() {
        return this.currentLeading;
    }

    public boolean getInheritGraphicState() {
        return this.inheritGraphicState;
    }

    public void setInheritGraphicState(boolean z) {
        this.inheritGraphicState = z;
    }

    public boolean isIgnoreSpacingBefore() {
        return this.ignoreSpacingBefore;
    }

    public void setIgnoreSpacingBefore(boolean z) {
        this.ignoreSpacingBefore = z;
    }

    /* renamed from: go */
    public int mo20965go() throws DocumentException {
        return mo20966go(false);
    }

    /* renamed from: go */
    public int mo20966go(boolean z) throws DocumentException {
        return mo20967go(z, (IAccessibleElement) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e0, code lost:
        r6 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01a9, code lost:
        r0.bidiLine.restore();
        r10 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01da, code lost:
        r10 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01dc, code lost:
        r6 = 1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x0287  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x02cb  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x02ce  */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x0305  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0310  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0312  */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x031d  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0320  */
    /* renamed from: go */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int mo20967go(boolean r31, com.itextpdf.text.pdf.interfaces.IAccessibleElement r32) throws com.itextpdf.text.DocumentException {
        /*
            r30 = this;
            r0 = r30
            r1 = r32
            r2 = 0
            r0.isWordSplit = r2
            boolean r3 = r0.composite
            if (r3 == 0) goto L_0x0010
            int r1 = r30.goComposite(r31)
            return r1
        L_0x0010:
            com.itextpdf.text.pdf.PdfContentByte r3 = r0.canvas
            boolean r3 = isTagged(r3)
            if (r3 == 0) goto L_0x0024
            boolean r3 = r1 instanceof com.itextpdf.text.ListItem
            if (r3 == 0) goto L_0x0024
            r3 = r1
            com.itextpdf.text.ListItem r3 = (com.itextpdf.text.ListItem) r3
            com.itextpdf.text.ListBody r3 = r3.getListBody()
            goto L_0x0025
        L_0x0024:
            r3 = 0
        L_0x0025:
            r30.addWaitingPhrase()
            com.itextpdf.text.pdf.BidiLine r5 = r0.bidiLine
            r6 = 1
            if (r5 != 0) goto L_0x002e
            return r6
        L_0x002e:
            r5 = 0
            r0.descender = r5
            r0.linesWritten = r2
            r0.lastX = r5
            float r7 = r0.spaceCharRatio
            r8 = 2
            java.lang.Object[] r15 = new java.lang.Object[r8]
            java.lang.Float r9 = new java.lang.Float
            r9.<init>(r5)
            r15[r6] = r9
            r9 = 2143289344(0x7fc00000, float:NaN)
            r0.firstLineY = r9
            int r9 = r0.runDirection
            if (r9 == 0) goto L_0x004c
            r25 = r9
            goto L_0x004e
        L_0x004c:
            r25 = 1
        L_0x004e:
            com.itextpdf.text.pdf.PdfContentByte r9 = r0.canvas
            if (r9 == 0) goto L_0x006f
            com.itextpdf.text.pdf.PdfDocument r10 = r9.getPdfDocument()
            com.itextpdf.text.pdf.PdfContentByte r11 = r0.canvas
            boolean r11 = isTagged(r11)
            if (r11 != 0) goto L_0x0067
            com.itextpdf.text.pdf.PdfContentByte r11 = r0.canvas
            boolean r12 = r0.inheritGraphicState
            com.itextpdf.text.pdf.PdfContentByte r11 = r11.getDuplicate(r12)
            goto L_0x0069
        L_0x0067:
            com.itextpdf.text.pdf.PdfContentByte r11 = r0.canvas
        L_0x0069:
            r26 = r9
            r27 = r10
            r14 = r11
            goto L_0x0076
        L_0x006f:
            if (r31 == 0) goto L_0x0335
            r14 = 0
            r26 = 0
            r27 = 0
        L_0x0076:
            r9 = 981668463(0x3a83126f, float:0.001)
            if (r31 != 0) goto L_0x008f
            int r10 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r10 != 0) goto L_0x0088
            com.itextpdf.text.pdf.PdfWriter r7 = r14.getPdfWriter()
            float r7 = r7.getSpaceCharRatio()
            goto L_0x008f
        L_0x0088:
            int r10 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r10 >= 0) goto L_0x008f
            r7 = 981668463(0x3a83126f, float:0.001)
        L_0x008f:
            boolean r9 = r0.rectangularMode
            if (r9 != 0) goto L_0x00ba
            com.itextpdf.text.pdf.BidiLine r9 = r0.bidiLine
            java.util.ArrayList<com.itextpdf.text.pdf.PdfChunk> r9 = r9.chunks
            java.util.Iterator r9 = r9.iterator()
            r10 = 0
        L_0x009c:
            boolean r11 = r9.hasNext()
            if (r11 == 0) goto L_0x00b1
            java.lang.Object r11 = r9.next()
            com.itextpdf.text.pdf.PdfChunk r11 = (com.itextpdf.text.pdf.PdfChunk) r11
            float r11 = r11.height()
            float r10 = java.lang.Math.max(r10, r11)
            goto L_0x009c
        L_0x00b1:
            float r9 = r0.fixedLeading
            float r11 = r0.multipliedLeading
            float r10 = r10 * r11
            float r9 = r9 + r10
            r0.currentLeading = r9
        L_0x00ba:
            r9 = 0
            r10 = 0
            r11 = 0
        L_0x00bd:
            boolean r12 = r0.lastWasNewline
            if (r12 == 0) goto L_0x00c4
            float r12 = r0.indent
            goto L_0x00c6
        L_0x00c4:
            float r12 = r0.followingIndent
        L_0x00c6:
            boolean r13 = r0.rectangularMode
            r16 = 3
            if (r13 == 0) goto L_0x01b2
            float r13 = r0.rectangularWidth
            float r5 = r0.rightIndent
            float r5 = r5 + r12
            int r5 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r5 > 0) goto L_0x00e3
            com.itextpdf.text.pdf.BidiLine r1 = r0.bidiLine
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x00e0
            r6 = 3
            goto L_0x024f
        L_0x00e0:
            r6 = 2
            goto L_0x024f
        L_0x00e3:
            com.itextpdf.text.pdf.BidiLine r5 = r0.bidiLine
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L_0x00ed
            goto L_0x024f
        L_0x00ed:
            com.itextpdf.text.pdf.BidiLine r5 = r0.bidiLine
            float r13 = r0.leftX
            float r4 = r0.rectangularWidth
            float r4 = r4 - r12
            float r8 = r0.rightIndent
            float r18 = r4 - r8
            int r4 = r0.alignment
            int r8 = r0.arabicOptions
            float r6 = r0.minY
            float r2 = r0.yLine
            r28 = r10
            float r10 = r0.descender
            r16 = r5
            r17 = r13
            r19 = r4
            r20 = r25
            r21 = r8
            r22 = r6
            r23 = r2
            r24 = r10
            com.itextpdf.text.pdf.PdfLine r2 = r16.processLine(r17, r18, r19, r20, r21, r22, r23, r24)
            boolean r4 = r0.isWordSplit
            com.itextpdf.text.pdf.BidiLine r5 = r0.bidiLine
            boolean r5 = r5.isWordSplit()
            r4 = r4 | r5
            r0.isWordSplit = r4
            if (r2 != 0) goto L_0x0127
            goto L_0x01da
        L_0x0127:
            float r4 = r0.fixedLeading
            float r5 = r0.multipliedLeading
            float[] r4 = r2.getMaxSize(r4, r5)
            boolean r5 = r30.isUseAscender()
            if (r5 == 0) goto L_0x0144
            float r5 = r0.firstLineY
            boolean r5 = java.lang.Float.isNaN(r5)
            if (r5 == 0) goto L_0x0144
            float r4 = r2.getAscender()
            r0.currentLeading = r4
            goto L_0x0153
        L_0x0144:
            r5 = 0
            r6 = r4[r5]
            r5 = 1
            r4 = r4[r5]
            float r5 = r0.descender
            float r4 = r4 - r5
            float r4 = java.lang.Math.max(r6, r4)
            r0.currentLeading = r4
        L_0x0153:
            float r4 = r0.yLine
            float r5 = r0.maxY
            int r5 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r5 > 0) goto L_0x01a9
            float r5 = r0.currentLeading
            float r6 = r4 - r5
            float r8 = r0.minY
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 >= 0) goto L_0x0166
            goto L_0x01a9
        L_0x0166:
            float r4 = r4 - r5
            r0.yLine = r4
            if (r31 != 0) goto L_0x0189
            if (r9 != 0) goto L_0x0189
            boolean r4 = r2.isRTL
            if (r4 == 0) goto L_0x0182
            com.itextpdf.text.pdf.PdfContentByte r4 = r0.canvas
            boolean r4 = r4.isTagged()
            if (r4 == 0) goto L_0x0182
            com.itextpdf.text.pdf.PdfContentByte r4 = r0.canvas
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.REVERSEDCHARS
            r4.beginMarkedContentSequence((com.itextpdf.text.pdf.PdfName) r5)
            r10 = 1
            goto L_0x0184
        L_0x0182:
            r10 = r28
        L_0x0184:
            r14.beginText()
            r9 = 1
            goto L_0x018b
        L_0x0189:
            r10 = r28
        L_0x018b:
            float r4 = r0.firstLineY
            boolean r4 = java.lang.Float.isNaN(r4)
            if (r4 == 0) goto L_0x0197
            float r4 = r0.yLine
            r0.firstLineY = r4
        L_0x0197:
            float r4 = r0.rectangularWidth
            float r5 = r2.widthLeft()
            float r4 = r4 - r5
            r0.updateFilledWidth(r4)
            float r4 = r0.leftX
            r5 = r9
            r6 = r10
            r29 = r12
            goto L_0x026f
        L_0x01a9:
            com.itextpdf.text.pdf.BidiLine r1 = r0.bidiLine
            r1.restore()
            r10 = r28
            goto L_0x00e0
        L_0x01b2:
            r28 = r10
            float r2 = r0.yLine
            float r4 = r0.currentLeading
            float r2 = r2 - r4
            float[] r4 = r30.findLimitsTwoLines()
            if (r4 != 0) goto L_0x01d0
            com.itextpdf.text.pdf.BidiLine r1 = r0.bidiLine
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x01c9
            r6 = 3
            goto L_0x01ca
        L_0x01c9:
            r6 = 2
        L_0x01ca:
            r0.yLine = r2
            r10 = r28
            goto L_0x024f
        L_0x01d0:
            com.itextpdf.text.pdf.BidiLine r5 = r0.bidiLine
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L_0x01df
            r0.yLine = r2
        L_0x01da:
            r10 = r28
        L_0x01dc:
            r6 = 1
            goto L_0x024f
        L_0x01df:
            r5 = 0
            r6 = r4[r5]
            r5 = 2
            r8 = r4[r5]
            float r6 = java.lang.Math.max(r6, r8)
            r8 = 1
            r10 = r4[r8]
            r4 = r4[r16]
            float r4 = java.lang.Math.min(r10, r4)
            float r4 = r4 - r6
            float r8 = r0.rightIndent
            float r10 = r12 + r8
            int r10 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r10 > 0) goto L_0x0203
            r10 = r28
        L_0x01fd:
            r2 = 0
            r5 = 0
            r6 = 1
            r8 = 2
            goto L_0x00bd
        L_0x0203:
            com.itextpdf.text.pdf.BidiLine r10 = r0.bidiLine
            float r4 = r4 - r12
            float r18 = r4 - r8
            int r4 = r0.alignment
            int r8 = r0.arabicOptions
            float r13 = r0.minY
            float r5 = r0.yLine
            r29 = r12
            float r12 = r0.descender
            r16 = r10
            r17 = r6
            r19 = r4
            r20 = r25
            r21 = r8
            r22 = r13
            r23 = r5
            r24 = r12
            com.itextpdf.text.pdf.PdfLine r4 = r16.processLine(r17, r18, r19, r20, r21, r22, r23, r24)
            if (r31 != 0) goto L_0x0248
            if (r9 != 0) goto L_0x0248
            boolean r5 = r4.isRTL
            if (r5 == 0) goto L_0x0241
            com.itextpdf.text.pdf.PdfContentByte r5 = r0.canvas
            boolean r5 = r5.isTagged()
            if (r5 == 0) goto L_0x0241
            com.itextpdf.text.pdf.PdfContentByte r5 = r0.canvas
            com.itextpdf.text.pdf.PdfName r8 = com.itextpdf.text.pdf.PdfName.REVERSEDCHARS
            r5.beginMarkedContentSequence((com.itextpdf.text.pdf.PdfName) r8)
            r10 = 1
            goto L_0x0243
        L_0x0241:
            r10 = r28
        L_0x0243:
            r14.beginText()
            r9 = 1
            goto L_0x024a
        L_0x0248:
            r10 = r28
        L_0x024a:
            if (r4 != 0) goto L_0x026b
            r0.yLine = r2
            goto L_0x01dc
        L_0x024f:
            if (r9 == 0) goto L_0x026a
            r14.endText()
            com.itextpdf.text.pdf.PdfContentByte r1 = r0.canvas
            if (r1 == r14) goto L_0x025b
            r1.add(r14)
        L_0x025b:
            if (r10 == 0) goto L_0x026a
            com.itextpdf.text.pdf.PdfContentByte r1 = r0.canvas
            boolean r1 = r1.isTagged()
            if (r1 == 0) goto L_0x026a
            com.itextpdf.text.pdf.PdfContentByte r1 = r0.canvas
            r1.endMarkedContentSequence()
        L_0x026a:
            return r6
        L_0x026b:
            r2 = r4
            r4 = r6
            r5 = r9
            r6 = r10
        L_0x026f:
            com.itextpdf.text.pdf.PdfContentByte r8 = r0.canvas
            boolean r8 = isTagged(r8)
            if (r8 == 0) goto L_0x02cb
            boolean r8 = r1 instanceof com.itextpdf.text.ListItem
            if (r8 == 0) goto L_0x02cb
            float r8 = r0.firstLineY
            boolean r8 = java.lang.Float.isNaN(r8)
            if (r8 != 0) goto L_0x02cb
            boolean r8 = r0.firstLineYDone
            if (r8 != 0) goto L_0x02cb
            if (r31 != 0) goto L_0x02c6
            r8 = r1
            com.itextpdf.text.ListItem r8 = (com.itextpdf.text.ListItem) r8
            com.itextpdf.text.ListLabel r9 = r8.getListLabel()
            com.itextpdf.text.pdf.PdfContentByte r10 = r0.canvas
            r10.openMCBlock(r9)
            com.itextpdf.text.Chunk r10 = new com.itextpdf.text.Chunk
            com.itextpdf.text.Chunk r8 = r8.getListSymbol()
            r10.<init>((com.itextpdf.text.Chunk) r8)
            r8 = 0
            r10.setRole(r8)
            com.itextpdf.text.pdf.PdfContentByte r12 = r0.canvas
            r17 = 0
            com.itextpdf.text.Phrase r13 = new com.itextpdf.text.Phrase
            r13.<init>((com.itextpdf.text.Chunk) r10)
            float r10 = r0.leftX
            float r16 = r9.getIndentation()
            float r19 = r10 + r16
            float r10 = r0.firstLineY
            r21 = 0
            r16 = r12
            r18 = r13
            r20 = r10
            showTextAligned(r16, r17, r18, r19, r20, r21)
            com.itextpdf.text.pdf.PdfContentByte r10 = r0.canvas
            r10.closeMCBlock(r9)
            goto L_0x02c7
        L_0x02c6:
            r8 = 0
        L_0x02c7:
            r9 = 1
            r0.firstLineYDone = r9
            goto L_0x02cc
        L_0x02cb:
            r8 = 0
        L_0x02cc:
            if (r31 != 0) goto L_0x0305
            if (r3 == 0) goto L_0x02d6
            com.itextpdf.text.pdf.PdfContentByte r9 = r0.canvas
            r9.openMCBlock(r3)
            r3 = r8
        L_0x02d6:
            r9 = 0
            r15[r9] = r11
            boolean r9 = r2.isRTL()
            if (r9 == 0) goto L_0x02e2
            float r12 = r0.rightIndent
            goto L_0x02e4
        L_0x02e2:
            r12 = r29
        L_0x02e4:
            float r4 = r4 + r12
            float r9 = r2.indentLeft()
            float r4 = r4 + r9
            float r9 = r0.yLine
            r14.setTextMatrix(r4, r9)
            r9 = r27
            r10 = r2
            r11 = r14
            r12 = r26
            r13 = r15
            r4 = r14
            r14 = r7
            float r9 = r9.writeLineToContent(r10, r11, r12, r13, r14)
            r0.lastX = r9
            r9 = 0
            r10 = r15[r9]
            com.itextpdf.text.pdf.PdfFont r10 = (com.itextpdf.text.pdf.PdfFont) r10
            r11 = r10
            goto L_0x0306
        L_0x0305:
            r4 = r14
        L_0x0306:
            boolean r9 = r0.repeatFirstLineIndent
            if (r9 == 0) goto L_0x0312
            boolean r9 = r2.isNewlineSplit()
            if (r9 == 0) goto L_0x0312
            r9 = 1
            goto L_0x0313
        L_0x0312:
            r9 = 0
        L_0x0313:
            r0.lastWasNewline = r9
            float r9 = r0.yLine
            boolean r10 = r2.isNewlineSplit()
            if (r10 == 0) goto L_0x0320
            float r10 = r0.extraParagraphSpace
            goto L_0x0321
        L_0x0320:
            r10 = 0
        L_0x0321:
            float r9 = r9 - r10
            r0.yLine = r9
            int r9 = r0.linesWritten
            r10 = 1
            int r9 = r9 + r10
            r0.linesWritten = r9
            float r2 = r2.getDescender()
            r0.descender = r2
            r14 = r4
            r9 = r5
            r10 = r6
            goto L_0x01fd
        L_0x0335:
            java.lang.NullPointerException r1 = new java.lang.NullPointerException
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "columntext.go.with.simulate.eq.eq.false.and.text.eq.eq.null"
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r2)
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.ColumnText.mo20967go(boolean, com.itextpdf.text.pdf.interfaces.IAccessibleElement):int");
    }

    public boolean isWordSplit() {
        return this.isWordSplit;
    }

    public float getExtraParagraphSpace() {
        return this.extraParagraphSpace;
    }

    public void setExtraParagraphSpace(float f) {
        this.extraParagraphSpace = f;
    }

    public void clearChunks() {
        BidiLine bidiLine2 = this.bidiLine;
        if (bidiLine2 != null) {
            bidiLine2.clearChunks();
        }
    }

    public float getSpaceCharRatio() {
        return this.spaceCharRatio;
    }

    public void setSpaceCharRatio(float f) {
        this.spaceCharRatio = f;
    }

    public void setRunDirection(int i) {
        if (i < 0 || i > 3) {
            throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.run.direction.1", i));
        }
        this.runDirection = i;
    }

    public int getRunDirection() {
        return this.runDirection;
    }

    public int getLinesWritten() {
        return this.linesWritten;
    }

    public float getLastX() {
        return this.lastX;
    }

    public int getArabicOptions() {
        return this.arabicOptions;
    }

    public void setArabicOptions(int i) {
        this.arabicOptions = i;
    }

    public float getDescender() {
        return this.descender;
    }

    public static float getWidth(Phrase phrase, int i, int i2) {
        ColumnText columnText = new ColumnText((PdfContentByte) null);
        columnText.addText(phrase);
        columnText.addWaitingPhrase();
        PdfLine processLine = columnText.bidiLine.processLine(0.0f, 20000.0f, 0, i, i2, 0.0f, 0.0f, 0.0f);
        if (processLine == null) {
            return 0.0f;
        }
        return 20000.0f - processLine.widthLeft();
    }

    public static float getWidth(Phrase phrase) {
        return getWidth(phrase, 1, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0086, code lost:
        if (r3 == 2) goto L_0x008a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0082  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void showTextAligned(com.itextpdf.text.pdf.PdfContentByte r18, int r19, com.itextpdf.text.Phrase r20, float r21, float r22, float r23, int r24, int r25) {
        /*
            r0 = r19
            r1 = r23
            r7 = r24
            r8 = 0
            r9 = 2
            if (r0 == 0) goto L_0x0011
            r2 = 1
            if (r0 == r2) goto L_0x0011
            if (r0 == r9) goto L_0x0011
            r15 = 0
            goto L_0x0012
        L_0x0011:
            r15 = r0
        L_0x0012:
            r18.saveState()
            com.itextpdf.text.pdf.ColumnText r14 = new com.itextpdf.text.pdf.ColumnText
            r13 = r18
            r14.<init>(r13)
            r10 = -1082130432(0xffffffffbf800000, float:-1.0)
            r11 = 1073741824(0x40000000, float:2.0)
            r0 = -962838528(0xffffffffc69c4000, float:-20000.0)
            r2 = 1184645120(0x469c4000, float:20000.0)
            r3 = 0
            if (r15 == 0) goto L_0x0035
            if (r15 == r9) goto L_0x002f
            r12 = -962838528(0xffffffffc69c4000, float:-20000.0)
            goto L_0x0036
        L_0x002f:
            r12 = -962838528(0xffffffffc69c4000, float:-20000.0)
            r16 = 0
            goto L_0x0039
        L_0x0035:
            r12 = 0
        L_0x0036:
            r16 = 1184645120(0x469c4000, float:20000.0)
        L_0x0039:
            int r0 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x0046
            float r12 = r12 + r21
            float r0 = r22 + r10
            float r16 = r16 + r21
            float r1 = r22 + r11
            goto L_0x006d
        L_0x0046:
            double r0 = (double) r1
            r2 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r0 = r0 * r2
            r2 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r0 = r0 / r2
            double r2 = java.lang.Math.cos(r0)
            float r4 = (float) r2
            double r0 = java.lang.Math.sin(r0)
            float r2 = (float) r0
            float r3 = -r2
            r0 = r18
            r1 = r4
            r5 = r21
            r6 = r22
            r0.concatCTM((float) r1, (float) r2, (float) r3, (float) r4, (float) r5, (float) r6)
            r0 = -1082130432(0xffffffffbf800000, float:-1.0)
            r1 = 1073741824(0x40000000, float:2.0)
        L_0x006d:
            r2 = 1073741824(0x40000000, float:2.0)
            r10 = r14
            r11 = r20
            r13 = r0
            r0 = r14
            r14 = r16
            r3 = r15
            r15 = r1
            r16 = r2
            r17 = r3
            r10.setSimpleColumn(r11, r12, r13, r14, r15, r16, r17)
            r1 = 3
            if (r7 != r1) goto L_0x0089
            if (r3 != 0) goto L_0x0086
            r8 = 2
            goto L_0x008a
        L_0x0086:
            if (r3 != r9) goto L_0x0089
            goto L_0x008a
        L_0x0089:
            r8 = r3
        L_0x008a:
            r0.setAlignment(r8)
            r1 = r25
            r0.setArabicOptions(r1)
            r0.setRunDirection(r7)
            r0.mo20965go()     // Catch:{ DocumentException -> 0x009c }
            r18.restoreState()
            return
        L_0x009c:
            r0 = move-exception
            r1 = r0
            com.itextpdf.text.ExceptionConverter r0 = new com.itextpdf.text.ExceptionConverter
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.ColumnText.showTextAligned(com.itextpdf.text.pdf.PdfContentByte, int, com.itextpdf.text.Phrase, float, float, float, int, int):void");
    }

    public static void showTextAligned(PdfContentByte pdfContentByte, int i, Phrase phrase, float f, float f2, float f3) {
        showTextAligned(pdfContentByte, i, phrase, f, f2, f3, 1, 0);
    }

    public static float fitText(Font font, String str, Rectangle rectangle, float f, int i) {
        float f2;
        Font font2 = font;
        String str2 = str;
        int i2 = i;
        if (f <= 0.0f) {
            try {
                char[] charArray = str.toCharArray();
                int i3 = 0;
                int i4 = 0;
                for (int i5 = 0; i5 < charArray.length; i5++) {
                    if (charArray[i5] == 10) {
                        i4++;
                    } else if (charArray[i5] == 13) {
                        i3++;
                    }
                }
                f2 = (Math.abs(rectangle.getHeight()) / ((float) (Math.max(i3, i4) + 1))) - 0.001f;
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
        } else {
            f2 = f;
        }
        font2.setSize(f2);
        Phrase phrase = new Phrase(str2, font2);
        ColumnText columnText = new ColumnText((PdfContentByte) null);
        columnText.setSimpleColumn(phrase, rectangle.getLeft(), rectangle.getBottom(), rectangle.getRight(), rectangle.getTop(), f2, 0);
        columnText.setRunDirection(i2);
        if ((columnText.mo20966go(true) & 1) != 0) {
            return f2;
        }
        float f3 = f2;
        float f4 = 0.0f;
        for (int i6 = 0; i6 < 50; i6++) {
            f2 = (f4 + f3) / 2.0f;
            ColumnText columnText2 = new ColumnText((PdfContentByte) null);
            font2.setSize(f2);
            columnText2.setSimpleColumn(new Phrase(str2, font2), rectangle.getLeft(), rectangle.getBottom(), rectangle.getRight(), rectangle.getTop(), f2, 0);
            columnText2.setRunDirection(i2);
            if ((columnText2.mo20966go(true) & 1) == 0) {
                f3 = f2;
            } else if (f3 - f4 < f2 * 0.1f) {
                return f2;
            } else {
                f4 = f2;
            }
        }
        return f2;
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r6v0 */
    /* JADX WARNING: type inference failed for: r6v1, types: [boolean, int] */
    /* JADX WARNING: type inference failed for: r3v2, types: [boolean, int] */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r6v2 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x03a4, code lost:
        r5 = r0.compositeColumn;
        r0.yLine = r5.yLine;
        r0.linesWritten += r5.linesWritten;
        r0.descender = r5.descender;
        r0.currentLeading = r5.currentLeading;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x03bf, code lost:
        if (isTagged(r0.canvas) != false) goto L_0x0428;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x03c9, code lost:
        if (java.lang.Float.isNaN(r0.compositeColumn.firstLineY) != false) goto L_0x0428;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x03cf, code lost:
        if (r0.compositeColumn.firstLineYDone != false) goto L_0x0428;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x03d1, code lost:
        if (r1 != false) goto L_0x0424;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x03d3, code lost:
        if (r4 == false) goto L_0x0404;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x03d5, code lost:
        showTextAligned(r0.canvas, 2, new com.itextpdf.text.Phrase(r2.getListSymbol()), r0.compositeColumn.lastX + r2.getIndentationLeft(), r0.compositeColumn.firstLineY, 0.0f, r0.runDirection, r0.arabicOptions);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x0404, code lost:
        r5 = r0.canvas;
        r7 = new com.itextpdf.text.Phrase(r2.getListSymbol());
        r8 = r0.compositeColumn;
        showTextAligned(r5, 0, r7, r8.leftX + r9, r8.firstLineY, 0.0f);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0424, code lost:
        r0.compositeColumn.firstLineYDone = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x042a, code lost:
        if ((r11 & 1) == 0) goto L_0x043d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x042c, code lost:
        r0.compositeColumn = null;
        r0.listIdx += r6;
        r0.yLine -= r2.getSpacingAfter();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x043f, code lost:
        if ((r11 & 2) == 0) goto L_0x019d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x0441, code lost:
        return 2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:236:0x051d  */
    /* JADX WARNING: Removed duplicated region for block: B:428:0x08d6  */
    /* JADX WARNING: Removed duplicated region for block: B:429:0x08e0  */
    /* JADX WARNING: Removed duplicated region for block: B:487:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0207 A[LOOP:3: B:90:0x01fa->B:94:0x0207, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int goComposite(boolean r30) throws com.itextpdf.text.DocumentException {
        /*
            r29 = this;
            r0 = r29
            r1 = r30
            com.itextpdf.text.pdf.PdfContentByte r2 = r0.canvas
            if (r2 == 0) goto L_0x000a
            com.itextpdf.text.pdf.PdfDocument r2 = r2.pdf
        L_0x000a:
            boolean r2 = r0.rectangularMode
            r3 = 0
            if (r2 == 0) goto L_0x0925
            r0.linesWritten = r3
            r2 = 0
            r0.descender = r2
            int r4 = r0.runDirection
            r5 = 3
            r6 = 1
            if (r4 != r5) goto L_0x001c
            r4 = 1
            goto L_0x001d
        L_0x001c:
            r4 = 0
        L_0x001d:
            r7 = 1
        L_0x001e:
            java.util.LinkedList<com.itextpdf.text.Element> r8 = r0.compositeElements
            boolean r8 = r8.isEmpty()
            if (r8 == 0) goto L_0x0027
            return r6
        L_0x0027:
            java.util.LinkedList<com.itextpdf.text.Element> r8 = r0.compositeElements
            java.lang.Object r8 = r8.getFirst()
            com.itextpdf.text.Element r8 = (com.itextpdf.text.Element) r8
            int r9 = r8.type()
            r10 = 12
            r11 = 0
            r12 = 2
            if (r9 != r10) goto L_0x01a2
            com.itextpdf.text.Paragraph r8 = (com.itextpdf.text.Paragraph) r8
            r9 = 0
            r10 = 0
        L_0x003d:
            if (r9 >= r12) goto L_0x015c
            float r10 = r0.yLine
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            if (r13 != 0) goto L_0x00b6
            com.itextpdf.text.pdf.ColumnText r13 = new com.itextpdf.text.pdf.ColumnText
            com.itextpdf.text.pdf.PdfContentByte r14 = r0.canvas
            r13.<init>(r14)
            r0.compositeColumn = r13
            int r14 = r8.getAlignment()
            r13.setAlignment(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r8.getIndentationLeft()
            float r15 = r8.getFirstLineIndent()
            float r14 = r14 + r15
            r13.setIndent(r14, r3)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r8.getExtraParagraphSpace()
            r13.setExtraParagraphSpace(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r8.getIndentationLeft()
            r13.setFollowingIndent(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r8.getIndentationRight()
            r13.setRightIndent(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r8.getLeading()
            float r15 = r8.getMultipliedLeading()
            r13.setLeading(r14, r15)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            int r14 = r0.runDirection
            r13.setRunDirection(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            int r14 = r0.arabicOptions
            r13.setArabicOptions(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r0.spaceCharRatio
            r13.setSpaceCharRatio(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            r13.addText((com.itextpdf.text.Phrase) r8)
            if (r7 == 0) goto L_0x00ab
            boolean r13 = r0.adjustFirstLine
            if (r13 != 0) goto L_0x00b4
        L_0x00ab:
            float r13 = r0.yLine
            float r14 = r8.getSpacingBefore()
            float r13 = r13 - r14
            r0.yLine = r13
        L_0x00b4:
            r13 = 1
            goto L_0x00b7
        L_0x00b6:
            r13 = 0
        L_0x00b7:
            com.itextpdf.text.pdf.ColumnText r14 = r0.compositeColumn
            if (r7 != 0) goto L_0x00c1
            float r15 = r0.descender
            int r15 = (r15 > r2 ? 1 : (r15 == r2 ? 0 : -1))
            if (r15 != 0) goto L_0x00c8
        L_0x00c1:
            boolean r15 = r0.adjustFirstLine
            if (r15 == 0) goto L_0x00c8
            boolean r15 = r0.useAscender
            goto L_0x00c9
        L_0x00c8:
            r15 = 0
        L_0x00c9:
            r14.setUseAscender(r15)
            com.itextpdf.text.pdf.ColumnText r14 = r0.compositeColumn
            boolean r15 = r0.inheritGraphicState
            r14.setInheritGraphicState(r15)
            com.itextpdf.text.pdf.ColumnText r14 = r0.compositeColumn
            float r15 = r0.leftX
            r14.leftX = r15
            float r15 = r0.rightX
            r14.rightX = r15
            float r15 = r0.yLine
            r14.yLine = r15
            float r15 = r0.rectangularWidth
            r14.rectangularWidth = r15
            boolean r15 = r0.rectangularMode
            r14.rectangularMode = r15
            float r15 = r0.minY
            r14.minY = r15
            float r15 = r0.maxY
            r14.maxY = r15
            boolean r14 = r8.getKeepTogether()
            if (r14 == 0) goto L_0x0101
            if (r13 == 0) goto L_0x0101
            if (r7 == 0) goto L_0x00ff
            boolean r13 = r0.adjustFirstLine
            if (r13 != 0) goto L_0x0101
        L_0x00ff:
            r13 = 1
            goto L_0x0102
        L_0x0101:
            r13 = 0
        L_0x0102:
            if (r1 != 0) goto L_0x010b
            if (r13 == 0) goto L_0x0109
            if (r9 != 0) goto L_0x0109
            goto L_0x010b
        L_0x0109:
            r14 = 0
            goto L_0x010c
        L_0x010b:
            r14 = 1
        L_0x010c:
            com.itextpdf.text.pdf.PdfContentByte r15 = r0.canvas
            boolean r15 = isTagged(r15)
            if (r15 == 0) goto L_0x011b
            if (r14 != 0) goto L_0x011b
            com.itextpdf.text.pdf.PdfContentByte r15 = r0.canvas
            r15.openMCBlock(r8)
        L_0x011b:
            com.itextpdf.text.pdf.ColumnText r15 = r0.compositeColumn
            int r15 = r15.mo20966go(r14)
            com.itextpdf.text.pdf.PdfContentByte r2 = r0.canvas
            boolean r2 = isTagged(r2)
            if (r2 == 0) goto L_0x0130
            if (r14 != 0) goto L_0x0130
            com.itextpdf.text.pdf.PdfContentByte r2 = r0.canvas
            r2.closeMCBlock(r8)
        L_0x0130:
            com.itextpdf.text.pdf.ColumnText r2 = r0.compositeColumn
            float r2 = r2.getLastX()
            r0.lastX = r2
            com.itextpdf.text.pdf.ColumnText r2 = r0.compositeColumn
            float r2 = r2.filledWidth
            r0.updateFilledWidth(r2)
            r2 = r15 & 1
            if (r2 != 0) goto L_0x014a
            if (r13 == 0) goto L_0x014a
            r0.compositeColumn = r11
            r0.yLine = r10
            return r12
        L_0x014a:
            if (r1 != 0) goto L_0x015b
            if (r13 != 0) goto L_0x014f
            goto L_0x015b
        L_0x014f:
            if (r9 != 0) goto L_0x0155
            r0.compositeColumn = r11
            r0.yLine = r10
        L_0x0155:
            int r9 = r9 + 1
            r10 = r15
            r2 = 0
            goto L_0x003d
        L_0x015b:
            r10 = r15
        L_0x015c:
            com.itextpdf.text.pdf.ColumnText r2 = r0.compositeColumn
            int r2 = r2.getLinesWritten()
            if (r2 <= 0) goto L_0x017e
            com.itextpdf.text.pdf.ColumnText r2 = r0.compositeColumn
            float r7 = r2.yLine
            r0.yLine = r7
            int r7 = r0.linesWritten
            int r9 = r2.linesWritten
            int r7 = r7 + r9
            r0.linesWritten = r7
            float r7 = r2.descender
            r0.descender = r7
            boolean r7 = r0.isWordSplit
            boolean r2 = r2.isWordSplit()
            r2 = r2 | r7
            r0.isWordSplit = r2
        L_0x017e:
            com.itextpdf.text.pdf.ColumnText r2 = r0.compositeColumn
            float r2 = r2.currentLeading
            r0.currentLeading = r2
            r2 = r10 & 1
            if (r2 == 0) goto L_0x0198
            r0.compositeColumn = r11
            java.util.LinkedList<com.itextpdf.text.Element> r2 = r0.compositeElements
            r2.removeFirst()
            float r2 = r0.yLine
            float r7 = r8.getSpacingAfter()
            float r2 = r2 - r7
            r0.yLine = r2
        L_0x0198:
            r2 = r10 & 2
            if (r2 == 0) goto L_0x019d
            return r12
        L_0x019d:
            r3 = 0
            r7 = 0
            r11 = 3
            goto L_0x05d1
        L_0x01a2:
            int r2 = r8.type()
            r9 = 14
            if (r2 != r9) goto L_0x0442
            com.itextpdf.text.List r8 = (com.itextpdf.text.List) r8
            java.util.ArrayList r2 = r8.getItems()
            float r9 = r8.getIndentationLeft()
            java.util.Stack r13 = new java.util.Stack
            r13.<init>()
            r14 = 0
            r15 = 0
        L_0x01bb:
            int r10 = r2.size()
            if (r14 >= r10) goto L_0x022e
            java.lang.Object r10 = r2.get(r14)
            boolean r11 = r10 instanceof com.itextpdf.text.ListItem
            if (r11 == 0) goto L_0x01d4
            int r11 = r0.listIdx
            if (r15 != r11) goto L_0x01d1
            r2 = r10
            com.itextpdf.text.ListItem r2 = (com.itextpdf.text.ListItem) r2
            goto L_0x022f
        L_0x01d1:
            int r15 = r15 + 1
            goto L_0x01fa
        L_0x01d4:
            boolean r11 = r10 instanceof com.itextpdf.text.List
            if (r11 == 0) goto L_0x01fa
            java.lang.Object[] r2 = new java.lang.Object[r5]
            r2[r3] = r8
            java.lang.Integer r8 = java.lang.Integer.valueOf(r14)
            r2[r6] = r8
            java.lang.Float r8 = new java.lang.Float
            r8.<init>(r9)
            r2[r12] = r8
            r13.push(r2)
            com.itextpdf.text.List r10 = (com.itextpdf.text.List) r10
            java.util.ArrayList r2 = r10.getItems()
            float r8 = r10.getIndentationLeft()
            float r9 = r9 + r8
            r8 = r10
            r14 = -1
            goto L_0x022b
        L_0x01fa:
            int r10 = r2.size()
            int r10 = r10 - r6
            if (r14 != r10) goto L_0x022b
            boolean r10 = r13.isEmpty()
            if (r10 != 0) goto L_0x022b
            java.lang.Object r2 = r13.pop()
            java.lang.Object[] r2 = (java.lang.Object[]) r2
            r8 = r2[r3]
            com.itextpdf.text.List r8 = (com.itextpdf.text.List) r8
            java.util.ArrayList r9 = r8.getItems()
            r10 = r2[r6]
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r14 = r10.intValue()
            r2 = r2[r12]
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            r28 = r9
            r9 = r2
            r2 = r28
            goto L_0x01fa
        L_0x022b:
            int r14 = r14 + r6
            r11 = 0
            goto L_0x01bb
        L_0x022e:
            r2 = 0
        L_0x022f:
            r10 = 0
            r11 = 0
        L_0x0231:
            if (r10 >= r12) goto L_0x03a4
            float r11 = r0.yLine
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            if (r13 != 0) goto L_0x02d8
            if (r2 != 0) goto L_0x0244
            r0.listIdx = r3
            java.util.LinkedList<com.itextpdf.text.Element> r2 = r0.compositeElements
            r2.removeFirst()
            goto L_0x045b
        L_0x0244:
            com.itextpdf.text.pdf.ColumnText r13 = new com.itextpdf.text.pdf.ColumnText
            com.itextpdf.text.pdf.PdfContentByte r14 = r0.canvas
            r13.<init>(r14)
            r0.compositeColumn = r13
            if (r7 != 0) goto L_0x0256
            float r14 = r0.descender
            r15 = 0
            int r14 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
            if (r14 != 0) goto L_0x025d
        L_0x0256:
            boolean r14 = r0.adjustFirstLine
            if (r14 == 0) goto L_0x025d
            boolean r14 = r0.useAscender
            goto L_0x025e
        L_0x025d:
            r14 = 0
        L_0x025e:
            r13.setUseAscender(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            boolean r14 = r0.inheritGraphicState
            r13.setInheritGraphicState(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            int r14 = r2.getAlignment()
            r13.setAlignment(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r2.getIndentationLeft()
            float r14 = r14 + r9
            float r15 = r2.getFirstLineIndent()
            float r14 = r14 + r15
            r13.setIndent(r14, r3)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r2.getExtraParagraphSpace()
            r13.setExtraParagraphSpace(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r13.getIndent()
            r13.setFollowingIndent(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r2.getIndentationRight()
            float r15 = r8.getIndentationRight()
            float r14 = r14 + r15
            r13.setRightIndent(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r2.getLeading()
            float r15 = r2.getMultipliedLeading()
            r13.setLeading(r14, r15)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            int r14 = r0.runDirection
            r13.setRunDirection(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            int r14 = r0.arabicOptions
            r13.setArabicOptions(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            float r14 = r0.spaceCharRatio
            r13.setSpaceCharRatio(r14)
            com.itextpdf.text.pdf.ColumnText r13 = r0.compositeColumn
            r13.addText((com.itextpdf.text.Phrase) r2)
            if (r7 == 0) goto L_0x02cd
            boolean r13 = r0.adjustFirstLine
            if (r13 != 0) goto L_0x02d6
        L_0x02cd:
            float r13 = r0.yLine
            float r14 = r2.getSpacingBefore()
            float r13 = r13 - r14
            r0.yLine = r13
        L_0x02d6:
            r13 = 1
            goto L_0x02d9
        L_0x02d8:
            r13 = 0
        L_0x02d9:
            com.itextpdf.text.pdf.ColumnText r14 = r0.compositeColumn
            float r15 = r0.leftX
            r14.leftX = r15
            float r15 = r0.rightX
            r14.rightX = r15
            float r15 = r0.yLine
            r14.yLine = r15
            float r15 = r0.rectangularWidth
            r14.rectangularWidth = r15
            boolean r15 = r0.rectangularMode
            r14.rectangularMode = r15
            float r15 = r0.minY
            r14.minY = r15
            float r15 = r0.maxY
            r14.maxY = r15
            boolean r14 = r2.getKeepTogether()
            if (r14 == 0) goto L_0x0307
            if (r13 == 0) goto L_0x0307
            if (r7 == 0) goto L_0x0305
            boolean r13 = r0.adjustFirstLine
            if (r13 != 0) goto L_0x0307
        L_0x0305:
            r13 = 1
            goto L_0x0308
        L_0x0307:
            r13 = 0
        L_0x0308:
            if (r1 != 0) goto L_0x0311
            if (r13 == 0) goto L_0x030f
            if (r10 != 0) goto L_0x030f
            goto L_0x0311
        L_0x030f:
            r14 = 0
            goto L_0x0312
        L_0x0311:
            r14 = 1
        L_0x0312:
            com.itextpdf.text.pdf.PdfContentByte r15 = r0.canvas
            boolean r15 = isTagged(r15)
            if (r15 == 0) goto L_0x033b
            if (r14 != 0) goto L_0x033b
            com.itextpdf.text.ListLabel r15 = r2.getListLabel()
            r15.setIndentation(r9)
            com.itextpdf.text.ListItem r15 = r8.getFirstItem()
            if (r15 == r2) goto L_0x0331
            com.itextpdf.text.pdf.ColumnText r15 = r0.compositeColumn
            if (r15 == 0) goto L_0x0336
            com.itextpdf.text.pdf.BidiLine r15 = r15.bidiLine
            if (r15 == 0) goto L_0x0336
        L_0x0331:
            com.itextpdf.text.pdf.PdfContentByte r15 = r0.canvas
            r15.openMCBlock(r8)
        L_0x0336:
            com.itextpdf.text.pdf.PdfContentByte r15 = r0.canvas
            r15.openMCBlock(r2)
        L_0x033b:
            com.itextpdf.text.pdf.ColumnText r15 = r0.compositeColumn
            if (r1 != 0) goto L_0x0346
            if (r13 == 0) goto L_0x0344
            if (r10 != 0) goto L_0x0344
            goto L_0x0346
        L_0x0344:
            r5 = 0
            goto L_0x0347
        L_0x0346:
            r5 = 1
        L_0x0347:
            int r5 = r15.mo20967go(r5, r2)
            com.itextpdf.text.pdf.PdfContentByte r15 = r0.canvas
            boolean r15 = isTagged(r15)
            if (r15 == 0) goto L_0x0376
            if (r14 != 0) goto L_0x0376
            com.itextpdf.text.pdf.PdfContentByte r14 = r0.canvas
            com.itextpdf.text.ListBody r15 = r2.getListBody()
            r14.closeMCBlock(r15)
            com.itextpdf.text.pdf.PdfContentByte r14 = r0.canvas
            r14.closeMCBlock(r2)
            com.itextpdf.text.ListItem r14 = r8.getLastItem()
            if (r14 != r2) goto L_0x036d
            r14 = r5 & 1
            if (r14 != 0) goto L_0x0371
        L_0x036d:
            r14 = r5 & 2
            if (r14 == 0) goto L_0x0376
        L_0x0371:
            com.itextpdf.text.pdf.PdfContentByte r14 = r0.canvas
            r14.closeMCBlock(r8)
        L_0x0376:
            com.itextpdf.text.pdf.ColumnText r14 = r0.compositeColumn
            float r14 = r14.getLastX()
            r0.lastX = r14
            com.itextpdf.text.pdf.ColumnText r14 = r0.compositeColumn
            float r14 = r14.filledWidth
            r0.updateFilledWidth(r14)
            r14 = r5 & 1
            if (r14 != 0) goto L_0x0391
            if (r13 == 0) goto L_0x0391
            r14 = 0
            r0.compositeColumn = r14
            r0.yLine = r11
            return r12
        L_0x0391:
            r14 = 0
            if (r1 != 0) goto L_0x03a3
            if (r13 != 0) goto L_0x0397
            goto L_0x03a3
        L_0x0397:
            if (r10 != 0) goto L_0x039d
            r0.compositeColumn = r14
            r0.yLine = r11
        L_0x039d:
            int r10 = r10 + 1
            r11 = r5
            r5 = 3
            goto L_0x0231
        L_0x03a3:
            r11 = r5
        L_0x03a4:
            com.itextpdf.text.pdf.ColumnText r5 = r0.compositeColumn
            float r7 = r5.yLine
            r0.yLine = r7
            int r7 = r0.linesWritten
            int r8 = r5.linesWritten
            int r7 = r7 + r8
            r0.linesWritten = r7
            float r7 = r5.descender
            r0.descender = r7
            float r5 = r5.currentLeading
            r0.currentLeading = r5
            com.itextpdf.text.pdf.PdfContentByte r5 = r0.canvas
            boolean r5 = isTagged(r5)
            if (r5 != 0) goto L_0x0428
            com.itextpdf.text.pdf.ColumnText r5 = r0.compositeColumn
            float r5 = r5.firstLineY
            boolean r5 = java.lang.Float.isNaN(r5)
            if (r5 != 0) goto L_0x0428
            com.itextpdf.text.pdf.ColumnText r5 = r0.compositeColumn
            boolean r5 = r5.firstLineYDone
            if (r5 != 0) goto L_0x0428
            if (r1 != 0) goto L_0x0424
            if (r4 == 0) goto L_0x0404
            com.itextpdf.text.pdf.PdfContentByte r5 = r0.canvas
            r17 = 2
            com.itextpdf.text.Phrase r7 = new com.itextpdf.text.Phrase
            com.itextpdf.text.Chunk r8 = r2.getListSymbol()
            r7.<init>((com.itextpdf.text.Chunk) r8)
            com.itextpdf.text.pdf.ColumnText r8 = r0.compositeColumn
            float r8 = r8.lastX
            float r9 = r2.getIndentationLeft()
            float r19 = r8 + r9
            com.itextpdf.text.pdf.ColumnText r8 = r0.compositeColumn
            float r8 = r8.firstLineY
            r21 = 0
            int r9 = r0.runDirection
            int r10 = r0.arabicOptions
            r16 = r5
            r18 = r7
            r20 = r8
            r22 = r9
            r23 = r10
            showTextAligned(r16, r17, r18, r19, r20, r21, r22, r23)
            goto L_0x0424
        L_0x0404:
            com.itextpdf.text.pdf.PdfContentByte r5 = r0.canvas
            r23 = 0
            com.itextpdf.text.Phrase r7 = new com.itextpdf.text.Phrase
            com.itextpdf.text.Chunk r8 = r2.getListSymbol()
            r7.<init>((com.itextpdf.text.Chunk) r8)
            com.itextpdf.text.pdf.ColumnText r8 = r0.compositeColumn
            float r10 = r8.leftX
            float r25 = r10 + r9
            float r8 = r8.firstLineY
            r27 = 0
            r22 = r5
            r24 = r7
            r26 = r8
            showTextAligned(r22, r23, r24, r25, r26, r27)
        L_0x0424:
            com.itextpdf.text.pdf.ColumnText r5 = r0.compositeColumn
            r5.firstLineYDone = r6
        L_0x0428:
            r5 = r11 & 1
            if (r5 == 0) goto L_0x043d
            r5 = 0
            r0.compositeColumn = r5
            int r5 = r0.listIdx
            int r5 = r5 + r6
            r0.listIdx = r5
            float r5 = r0.yLine
            float r2 = r2.getSpacingAfter()
            float r5 = r5 - r2
            r0.yLine = r5
        L_0x043d:
            r2 = r11 & 2
            if (r2 == 0) goto L_0x019d
            return r12
        L_0x0442:
            int r2 = r8.type()
            r5 = 23
            if (r2 != r5) goto L_0x0884
            com.itextpdf.text.pdf.PdfPTable r8 = (com.itextpdf.text.pdf.PdfPTable) r8
            int r2 = r8.size()
            int r5 = r8.getHeaderRows()
            if (r2 > r5) goto L_0x045f
            java.util.LinkedList<com.itextpdf.text.Element> r2 = r0.compositeElements
            r2.removeFirst()
        L_0x045b:
            r16 = r7
            goto L_0x05cf
        L_0x045f:
            float r2 = r0.yLine
            float r5 = r0.descender
            float r2 = r2 + r5
            int r5 = r0.rowIdx
            if (r5 != 0) goto L_0x0471
            boolean r5 = r0.adjustFirstLine
            if (r5 == 0) goto L_0x0471
            float r5 = r8.spacingBefore()
            float r2 = r2 - r5
        L_0x0471:
            r22 = r2
            float r2 = r0.minY
            int r2 = (r22 > r2 ? 1 : (r22 == r2 ? 0 : -1))
            if (r2 < 0) goto L_0x0882
            float r2 = r0.maxY
            int r2 = (r22 > r2 ? 1 : (r22 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x0481
            goto L_0x0882
        L_0x0481:
            float r2 = r0.leftX
            r5 = 0
            r0.currentLeading = r5
            boolean r5 = r8.isLockedWidth()
            if (r5 == 0) goto L_0x0494
            float r5 = r8.getTotalWidth()
            r0.updateFilledWidth(r5)
            goto L_0x04a2
        L_0x0494:
            float r5 = r0.rectangularWidth
            float r9 = r8.getWidthPercentage()
            float r5 = r5 * r9
            r9 = 1120403456(0x42c80000, float:100.0)
            float r5 = r5 / r9
            r8.setTotalWidth((float) r5)
        L_0x04a2:
            r8.normalizeHeadersFooters()
            int r9 = r8.getHeaderRows()
            int r10 = r8.getFooterRows()
            int r11 = r9 - r10
            float r13 = r8.getFooterHeight()
            float r14 = r8.getHeaderHeight()
            float r14 = r14 - r13
            boolean r15 = r8.isSkipFirstHeader()
            if (r15 == 0) goto L_0x04ce
            int r15 = r0.rowIdx
            if (r15 > r11) goto L_0x04ce
            boolean r15 = r8.isComplete()
            if (r15 != 0) goto L_0x04cc
            int r15 = r0.rowIdx
            if (r15 == r11) goto L_0x04ce
        L_0x04cc:
            r15 = 1
            goto L_0x04cf
        L_0x04ce:
            r15 = 0
        L_0x04cf:
            if (r15 != 0) goto L_0x04d4
            float r14 = r22 - r14
            goto L_0x04d6
        L_0x04d4:
            r14 = r22
        L_0x04d6:
            int r12 = r0.rowIdx
            if (r12 >= r9) goto L_0x04dc
            r0.rowIdx = r9
        L_0x04dc:
            boolean r12 = r8.isSkipLastFooter()
            if (r12 == 0) goto L_0x04ed
            float r12 = r0.minY
            float r12 = r14 - r12
            int r3 = r0.rowIdx
            com.itextpdf.text.pdf.PdfPTable$FittingRows r3 = r8.getFittingRows(r12, r3)
            goto L_0x04ee
        L_0x04ed:
            r3 = 0
        L_0x04ee:
            boolean r12 = r8.isSkipLastFooter()
            if (r12 == 0) goto L_0x0504
            int r12 = r3.lastRow
            int r16 = r8.size()
            r17 = r3
            int r3 = r16 + -1
            if (r12 >= r3) goto L_0x0501
            goto L_0x0504
        L_0x0501:
            r3 = r17
            goto L_0x050f
        L_0x0504:
            float r14 = r14 - r13
            float r3 = r0.minY
            float r3 = r14 - r3
            int r12 = r0.rowIdx
            com.itextpdf.text.pdf.PdfPTable$FittingRows r3 = r8.getFittingRows(r3, r12)
        L_0x050f:
            float r12 = r0.minY
            int r12 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r12 < 0) goto L_0x0880
            float r12 = r0.maxY
            int r12 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r12 <= 0) goto L_0x051d
            goto L_0x0880
        L_0x051d:
            int r12 = r3.lastRow
            int r12 = r12 + r6
            float r6 = r3.height
            float r14 = r14 - r6
            com.itextpdf.text.log.Logger r6 = r0.LOGGER
            r16 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r17 = r14
            java.lang.String r14 = "Want to split at row "
            r7.append(r14)
            r7.append(r12)
            java.lang.String r7 = r7.toString()
            r6.info(r7)
            r6 = r12
        L_0x053e:
            int r7 = r0.rowIdx
            if (r6 <= r7) goto L_0x0555
            int r7 = r8.size()
            if (r6 >= r7) goto L_0x0555
            com.itextpdf.text.pdf.PdfPRow r7 = r8.getRow(r6)
            boolean r7 = r7.isMayNotBreak()
            if (r7 == 0) goto L_0x0555
            int r6 = r6 + -1
            goto L_0x053e
        L_0x0555:
            int r7 = r8.size()
            r14 = 1
            int r7 = r7 - r14
            if (r6 >= r7) goto L_0x0569
            com.itextpdf.text.pdf.PdfPRow r7 = r8.getRow(r6)
            boolean r7 = r7.isMayNotBreak()
            if (r7 != 0) goto L_0x0569
            int r6 = r6 + 1
        L_0x0569:
            int r7 = r0.rowIdx
            if (r6 <= r7) goto L_0x056f
            if (r6 < r12) goto L_0x0581
        L_0x056f:
            if (r6 != r9) goto L_0x0589
            com.itextpdf.text.pdf.PdfPRow r7 = r8.getRow(r9)
            boolean r7 = r7.isMayNotBreak()
            if (r7 == 0) goto L_0x0589
            boolean r7 = r8.isLoopCheck()
            if (r7 == 0) goto L_0x0589
        L_0x0581:
            float r14 = r0.minY
            r7 = 0
            r8.setLoopCheck(r7)
            r12 = r6
            goto L_0x058b
        L_0x0589:
            r14 = r17
        L_0x058b:
            com.itextpdf.text.log.Logger r6 = r0.LOGGER
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r17 = r9
            java.lang.String r9 = "Will split at row "
            r7.append(r9)
            r7.append(r12)
            java.lang.String r7 = r7.toString()
            r6.info(r7)
            boolean r6 = r8.isSplitLate()
            if (r6 == 0) goto L_0x05b0
            if (r12 <= 0) goto L_0x05b0
            int r6 = r12 + -1
            r3.correctLastRowChosen(r8, r6)
        L_0x05b0:
            boolean r6 = r8.isComplete()
            if (r6 != 0) goto L_0x05b7
            float r14 = r14 + r13
        L_0x05b7:
            boolean r6 = r8.isSplitRows()
            if (r6 != 0) goto L_0x05e9
            r6 = -1
            r0.splittedRow = r6
            int r3 = r0.rowIdx
            if (r12 != r3) goto L_0x0678
            int r2 = r8.size()
            if (r12 != r2) goto L_0x05d7
            java.util.LinkedList<com.itextpdf.text.Element> r2 = r0.compositeElements
            r2.removeFirst()
        L_0x05cf:
            r7 = r16
        L_0x05d1:
            r2 = 0
            r3 = 0
            r5 = 3
            r6 = 1
            goto L_0x001e
        L_0x05d7:
            boolean r1 = r8.isComplete()
            if (r1 != 0) goto L_0x05e0
            r1 = 1
            if (r12 == r1) goto L_0x05e7
        L_0x05e0:
            java.util.ArrayList r1 = r8.getRows()
            r1.remove(r12)
        L_0x05e7:
            r1 = 2
            return r1
        L_0x05e9:
            boolean r6 = r8.isSplitLate()
            if (r6 == 0) goto L_0x0608
            int r6 = r0.rowIdx
            if (r6 < r12) goto L_0x0604
            int r6 = r0.splittedRow
            r7 = -2
            if (r6 != r7) goto L_0x0608
            int r6 = r8.getHeaderRows()
            if (r6 == 0) goto L_0x0604
            boolean r6 = r8.isSkipFirstHeader()
            if (r6 == 0) goto L_0x0608
        L_0x0604:
            r3 = -1
            r0.splittedRow = r3
            goto L_0x0678
        L_0x0608:
            int r6 = r8.size()
            if (r12 >= r6) goto L_0x0678
            float r6 = r3.completedRowsHeight
            float r3 = r3.height
            float r6 = r6 - r3
            float r14 = r14 - r6
            float r3 = r0.minY
            float r3 = r14 - r3
            com.itextpdf.text.pdf.PdfPRow r6 = r8.getRow(r12)
            com.itextpdf.text.pdf.PdfPRow r3 = r6.splitRow(r8, r12, r3)
            if (r3 != 0) goto L_0x0632
            com.itextpdf.text.log.Logger r3 = r0.LOGGER
            java.lang.String r6 = "Didn't split row!"
            r3.info(r6)
            r3 = -1
            r0.splittedRow = r3
            int r3 = r0.rowIdx
            if (r3 != r12) goto L_0x0678
            r3 = 2
            return r3
        L_0x0632:
            int r6 = r0.splittedRow
            if (r12 == r6) goto L_0x0657
            int r6 = r12 + 1
            r0.splittedRow = r6
            com.itextpdf.text.pdf.PdfPTable r6 = new com.itextpdf.text.pdf.PdfPTable
            r6.<init>((com.itextpdf.text.pdf.PdfPTable) r8)
            java.util.LinkedList<com.itextpdf.text.Element> r7 = r0.compositeElements
            r8 = 0
            r7.set(r8, r6)
            java.util.ArrayList r7 = r6.getRows()
            r9 = r17
        L_0x064b:
            int r8 = r0.rowIdx
            if (r9 >= r8) goto L_0x0656
            r8 = 0
            r7.set(r9, r8)
            int r9 = r9 + 1
            goto L_0x064b
        L_0x0656:
            r8 = r6
        L_0x0657:
            float r14 = r0.minY
            java.util.ArrayList r6 = r8.getRows()
            int r12 = r12 + 1
            r6.add(r12, r3)
            com.itextpdf.text.log.Logger r3 = r0.LOGGER
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Inserting row at position "
            r6.append(r7)
            r6.append(r12)
            java.lang.String r6 = r6.toString()
            r3.info(r6)
        L_0x0678:
            if (r1 != 0) goto L_0x07fd
            int r3 = r8.getHorizontalAlignment()
            r6 = 1
            if (r3 == r6) goto L_0x0692
            r6 = 2
            if (r3 == r6) goto L_0x068a
            if (r4 == 0) goto L_0x068f
            float r3 = r0.rectangularWidth
        L_0x0688:
            float r3 = r3 - r5
            goto L_0x0698
        L_0x068a:
            if (r4 != 0) goto L_0x068f
            float r3 = r0.rectangularWidth
            goto L_0x0688
        L_0x068f:
            r21 = r2
            goto L_0x069a
        L_0x0692:
            float r3 = r0.rectangularWidth
            float r3 = r3 - r5
            r5 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 / r5
        L_0x0698:
            float r2 = r2 + r3
            goto L_0x068f
        L_0x069a:
            com.itextpdf.text.pdf.PdfPTable r2 = com.itextpdf.text.pdf.PdfPTable.shallowCopy(r8)
            java.util.ArrayList r3 = r2.getRows()
            if (r15 != 0) goto L_0x06bd
            if (r11 <= 0) goto L_0x06bd
            r5 = 0
            java.util.ArrayList r6 = r8.getRows(r5, r11)
            com.itextpdf.text.pdf.PdfContentByte r5 = r0.canvas
            boolean r5 = isTagged(r5)
            if (r5 == 0) goto L_0x06b9
            com.itextpdf.text.pdf.PdfPTableHeader r5 = r2.getHeader()
            r5.rows = r6
        L_0x06b9:
            r3.addAll(r6)
            goto L_0x06c0
        L_0x06bd:
            r2.setHeaderRows(r10)
        L_0x06c0:
            int r5 = r0.rowIdx
            java.util.ArrayList r5 = r8.getRows(r5, r12)
            com.itextpdf.text.pdf.PdfContentByte r6 = r0.canvas
            boolean r6 = isTagged(r6)
            if (r6 == 0) goto L_0x06d4
            com.itextpdf.text.pdf.PdfPTableBody r6 = r2.getBody()
            r6.rows = r5
        L_0x06d4:
            r3.addAll(r5)
            boolean r5 = r8.isSkipLastFooter()
            r6 = 1
            r5 = r5 ^ r6
            int r7 = r8.size()
            if (r12 >= r7) goto L_0x06e9
            r2.setComplete(r6)
            r5 = 1
            r6 = 1
            goto L_0x06ea
        L_0x06e9:
            r6 = 0
        L_0x06ea:
            if (r10 <= 0) goto L_0x070c
            boolean r7 = r2.isComplete()
            if (r7 == 0) goto L_0x070c
            if (r5 == 0) goto L_0x070c
            int r5 = r11 + r10
            java.util.ArrayList r5 = r8.getRows(r11, r5)
            com.itextpdf.text.pdf.PdfContentByte r7 = r0.canvas
            boolean r7 = isTagged(r7)
            if (r7 == 0) goto L_0x0708
            com.itextpdf.text.pdf.PdfPTableFooter r7 = r2.getFooter()
            r7.rows = r5
        L_0x0708:
            r3.addAll(r5)
            goto L_0x070d
        L_0x070c:
            r10 = 0
        L_0x070d:
            int r5 = r3.size()
            if (r5 <= 0) goto L_0x07fb
            int r5 = r3.size()
            r7 = 1
            int r5 = r5 - r7
            int r5 = r5 - r10
            java.lang.Object r3 = r3.get(r5)
            com.itextpdf.text.pdf.PdfPRow r3 = (com.itextpdf.text.pdf.PdfPRow) r3
            boolean r9 = r8.isExtendLastRow(r6)
            if (r9 == 0) goto L_0x0734
            float r9 = r3.getMaxHeights()
            float r10 = r0.minY
            float r14 = r14 - r10
            float r14 = r14 + r9
            r3.setMaxHeights(r14)
            float r14 = r0.minY
            goto L_0x0735
        L_0x0734:
            r9 = 0
        L_0x0735:
            if (r6 == 0) goto L_0x0744
            com.itextpdf.text.pdf.PdfPTableEvent r10 = r8.getTableEvent()
            boolean r11 = r10 instanceof com.itextpdf.text.pdf.PdfPTableEventSplit
            if (r11 == 0) goto L_0x0744
            com.itextpdf.text.pdf.PdfPTableEventSplit r10 = (com.itextpdf.text.pdf.PdfPTableEventSplit) r10
            r10.splitTable(r8)
        L_0x0744:
            com.itextpdf.text.pdf.PdfContentByte[] r10 = r0.canvases
            if (r10 == 0) goto L_0x077e
            r11 = 3
            r10 = r10[r11]
            boolean r10 = isTagged(r10)
            if (r10 == 0) goto L_0x0758
            com.itextpdf.text.pdf.PdfContentByte[] r10 = r0.canvases
            r10 = r10[r11]
            r10.openMCBlock(r8)
        L_0x0758:
            r17 = 0
            r18 = -1
            r19 = 0
            r20 = -1
            com.itextpdf.text.pdf.PdfContentByte[] r10 = r0.canvases
            r24 = 0
            r16 = r2
            r23 = r10
            r16.writeSelectedRows((int) r17, (int) r18, (int) r19, (int) r20, (float) r21, (float) r22, (com.itextpdf.text.pdf.PdfContentByte[]) r23, (boolean) r24)
            com.itextpdf.text.pdf.PdfContentByte[] r10 = r0.canvases
            r11 = 3
            r10 = r10[r11]
            boolean r10 = isTagged(r10)
            if (r10 == 0) goto L_0x07ac
            com.itextpdf.text.pdf.PdfContentByte[] r10 = r0.canvases
            r10 = r10[r11]
            r10.closeMCBlock(r8)
            goto L_0x07ac
        L_0x077e:
            r11 = 3
            com.itextpdf.text.pdf.PdfContentByte r10 = r0.canvas
            boolean r10 = isTagged(r10)
            if (r10 == 0) goto L_0x078c
            com.itextpdf.text.pdf.PdfContentByte r10 = r0.canvas
            r10.openMCBlock(r8)
        L_0x078c:
            r17 = 0
            r18 = -1
            r19 = 0
            r20 = -1
            com.itextpdf.text.pdf.PdfContentByte r10 = r0.canvas
            r24 = 0
            r16 = r2
            r23 = r10
            r16.writeSelectedRows((int) r17, (int) r18, (int) r19, (int) r20, (float) r21, (float) r22, (com.itextpdf.text.pdf.PdfContentByte) r23, (boolean) r24)
            com.itextpdf.text.pdf.PdfContentByte r10 = r0.canvas
            boolean r10 = isTagged(r10)
            if (r10 == 0) goto L_0x07ac
            com.itextpdf.text.pdf.PdfContentByte r10 = r0.canvas
            r10.closeMCBlock(r8)
        L_0x07ac:
            boolean r10 = r8.isComplete()
            if (r10 != 0) goto L_0x07b5
            r8.addNumberOfRowsWritten(r12)
        L_0x07b5:
            int r10 = r0.splittedRow
            if (r10 != r12) goto L_0x07cd
            int r10 = r8.size()
            if (r12 >= r10) goto L_0x07cd
            java.util.ArrayList r10 = r8.getRows()
            java.lang.Object r10 = r10.get(r12)
            com.itextpdf.text.pdf.PdfPRow r10 = (com.itextpdf.text.pdf.PdfPRow) r10
            r10.copyRowContent(r2, r5)
            goto L_0x07de
        L_0x07cd:
            if (r12 <= 0) goto L_0x07de
            int r10 = r8.size()
            if (r12 >= r10) goto L_0x07de
            com.itextpdf.text.pdf.PdfPRow r10 = r8.getRow(r12)
            int r7 = r12 + -1
            r10.splitRowspans(r8, r7, r2, r5)
        L_0x07de:
            boolean r2 = r8.isExtendLastRow(r6)
            if (r2 == 0) goto L_0x07e7
            r3.setMaxHeights(r9)
        L_0x07e7:
            if (r6 == 0) goto L_0x080d
            com.itextpdf.text.pdf.PdfPTableEvent r2 = r8.getTableEvent()
            boolean r3 = r2 instanceof com.itextpdf.text.pdf.PdfPTableEventAfterSplit
            if (r3 == 0) goto L_0x080d
            com.itextpdf.text.pdf.PdfPRow r3 = r8.getRow(r12)
            com.itextpdf.text.pdf.PdfPTableEventAfterSplit r2 = (com.itextpdf.text.pdf.PdfPTableEventAfterSplit) r2
            r2.afterSplitTable(r8, r3, r12)
            goto L_0x080d
        L_0x07fb:
            r11 = 3
            goto L_0x080d
        L_0x07fd:
            r11 = 3
            boolean r2 = r8.isExtendLastRow()
            if (r2 == 0) goto L_0x080d
            float r2 = r0.minY
            r3 = -830472192(0xffffffffce800000, float:-1.07374182E9)
            int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x080d
            r14 = r2
        L_0x080d:
            r0.yLine = r14
            r2 = 0
            r0.descender = r2
            r0.currentLeading = r2
            if (r15 != 0) goto L_0x0821
            boolean r2 = r8.isComplete()
            if (r2 != 0) goto L_0x0821
            float r2 = r0.yLine
            float r2 = r2 + r13
            r0.yLine = r2
        L_0x0821:
            int r2 = r8.size()
            if (r12 >= r2) goto L_0x083a
            float r2 = r8.getRowHeight(r12)
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 > 0) goto L_0x083a
            boolean r2 = r8.hasRowspan(r12)
            if (r2 == 0) goto L_0x0837
            goto L_0x083a
        L_0x0837:
            int r12 = r12 + 1
            goto L_0x0821
        L_0x083a:
            int r2 = r8.size()
            if (r12 < r2) goto L_0x0868
            float r2 = r0.yLine
            float r3 = r8.spacingAfter()
            float r2 = r2 - r3
            float r3 = r0.minY
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 >= 0) goto L_0x0850
            r0.yLine = r3
            goto L_0x0859
        L_0x0850:
            float r2 = r0.yLine
            float r3 = r8.spacingAfter()
            float r2 = r2 - r3
            r0.yLine = r2
        L_0x0859:
            java.util.LinkedList<com.itextpdf.text.Element> r2 = r0.compositeElements
            r2.removeFirst()
            r2 = -1
            r0.splittedRow = r2
            r2 = 0
            r0.rowIdx = r2
            r3 = 0
            r7 = 0
            goto L_0x05d1
        L_0x0868:
            r2 = -1
            int r1 = r0.splittedRow
            if (r1 <= r2) goto L_0x087c
            java.util.ArrayList r1 = r8.getRows()
            int r2 = r0.rowIdx
        L_0x0873:
            if (r2 >= r12) goto L_0x087c
            r3 = 0
            r1.set(r2, r3)
            int r2 = r2 + 1
            goto L_0x0873
        L_0x087c:
            r0.rowIdx = r12
            r1 = 2
            return r1
        L_0x0880:
            r1 = 2
            return r1
        L_0x0882:
            r1 = 2
            return r1
        L_0x0884:
            r16 = r7
            r3 = 0
            r11 = 3
            int r2 = r8.type()
            r5 = 55
            if (r2 != r5) goto L_0x08b9
            if (r1 != 0) goto L_0x08b1
            r17 = r8
            com.itextpdf.text.pdf.draw.DrawInterface r17 = (com.itextpdf.text.pdf.draw.DrawInterface) r17
            com.itextpdf.text.pdf.PdfContentByte r2 = r0.canvas
            float r3 = r0.leftX
            float r5 = r0.minY
            float r6 = r0.rightX
            float r7 = r0.maxY
            float r8 = r0.yLine
            r18 = r2
            r19 = r3
            r20 = r5
            r21 = r6
            r22 = r7
            r23 = r8
            r17.draw(r18, r19, r20, r21, r22, r23)
        L_0x08b1:
            java.util.LinkedList<com.itextpdf.text.Element> r2 = r0.compositeElements
            r2.removeFirst()
            r3 = 0
            goto L_0x05cf
        L_0x08b9:
            int r2 = r8.type()
            r5 = 37
            if (r2 != r5) goto L_0x091d
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
        L_0x08c6:
            r2.add(r8)
            java.util.LinkedList<com.itextpdf.text.Element> r6 = r0.compositeElements
            r6.removeFirst()
            java.util.LinkedList<com.itextpdf.text.Element> r6 = r0.compositeElements
            boolean r6 = r6.isEmpty()
            if (r6 != 0) goto L_0x08e0
            java.util.LinkedList<com.itextpdf.text.Element> r6 = r0.compositeElements
            java.lang.Object r6 = r6.getFirst()
            com.itextpdf.text.Element r6 = (com.itextpdf.text.Element) r6
            r8 = r6
            goto L_0x08e1
        L_0x08e0:
            r8 = r3
        L_0x08e1:
            if (r8 == 0) goto L_0x08e9
            int r6 = r8.type()
            if (r6 == r5) goto L_0x08c6
        L_0x08e9:
            com.itextpdf.text.pdf.FloatLayout r3 = new com.itextpdf.text.pdf.FloatLayout
            boolean r5 = r0.useAscender
            r3.<init>(r2, r5)
            float r5 = r0.leftX
            float r6 = r0.minY
            float r7 = r0.rightX
            float r8 = r0.yLine
            r3.setSimpleColumn(r5, r6, r7, r8)
            com.itextpdf.text.pdf.ColumnText r5 = r3.compositeColumn
            boolean r6 = r29.isIgnoreSpacingBefore()
            r5.setIgnoreSpacingBefore(r6)
            com.itextpdf.text.pdf.PdfContentByte r5 = r0.canvas
            int r5 = r3.layout(r5, r1)
            float r3 = r3.getYLine()
            r0.yLine = r3
            r3 = 0
            r0.descender = r3
            r6 = r5 & 1
            if (r6 != 0) goto L_0x05cf
            java.util.LinkedList<com.itextpdf.text.Element> r1 = r0.compositeElements
            r1.addAll(r2)
            return r5
        L_0x091d:
            r3 = 0
            java.util.LinkedList<com.itextpdf.text.Element> r2 = r0.compositeElements
            r2.removeFirst()
            goto L_0x05cf
        L_0x0925:
            com.itextpdf.text.DocumentException r1 = new com.itextpdf.text.DocumentException
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "irregular.columns.are.not.supported.in.composite.mode"
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r2)
            r1.<init>((java.lang.String) r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.ColumnText.goComposite(boolean):int");
    }

    public PdfContentByte getCanvas() {
        return this.canvas;
    }

    public void setCanvas(PdfContentByte pdfContentByte) {
        this.canvas = pdfContentByte;
        this.canvases = null;
        ColumnText columnText = this.compositeColumn;
        if (columnText != null) {
            columnText.setCanvas(pdfContentByte);
        }
    }

    public void setCanvases(PdfContentByte[] pdfContentByteArr) {
        this.canvases = pdfContentByteArr;
        this.canvas = pdfContentByteArr[3];
        ColumnText columnText = this.compositeColumn;
        if (columnText != null) {
            columnText.setCanvases(pdfContentByteArr);
        }
    }

    public PdfContentByte[] getCanvases() {
        return this.canvases;
    }

    public boolean zeroHeightElement() {
        return this.composite && !this.compositeElements.isEmpty() && this.compositeElements.getFirst().type() == 55;
    }

    public List<Element> getCompositeElements() {
        return this.compositeElements;
    }

    public boolean isUseAscender() {
        return this.useAscender;
    }

    public void setUseAscender(boolean z) {
        this.useAscender = z;
    }

    public float getFilledWidth() {
        return this.filledWidth;
    }

    public void setFilledWidth(float f) {
        this.filledWidth = f;
    }

    public void updateFilledWidth(float f) {
        if (f > this.filledWidth) {
            this.filledWidth = f;
        }
    }

    public boolean isAdjustFirstLine() {
        return this.adjustFirstLine;
    }

    public void setAdjustFirstLine(boolean z) {
        this.adjustFirstLine = z;
    }

    private static boolean isTagged(PdfContentByte pdfContentByte) {
        return (pdfContentByte == null || pdfContentByte.pdf == null || pdfContentByte.writer == null || !pdfContentByte.writer.isTagged()) ? false : true;
    }
}
