package com.itextpdf.text.pdf;

import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.util.HashMap;

public class PdfPRow implements IAccessibleElement {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final float BOTTOM_LIMIT = -1.07374182E9f;
    public static final float RIGHT_LIMIT = 20000.0f;
    private final Logger LOGGER;
    protected HashMap<PdfName, PdfObject> accessibleAttributes;
    protected boolean adjusted;
    protected boolean calculated;
    private int[] canvasesPos;
    protected PdfPCell[] cells;
    protected float[] extraHeights;

    /* renamed from: id */
    protected AccessibleElementId f681id;
    protected float maxHeight;
    public boolean mayNotBreak;
    protected PdfName role;
    protected float[] widths;

    public boolean isInline() {
        return false;
    }

    public PdfPRow(PdfPCell[] pdfPCellArr) {
        this(pdfPCellArr, (PdfPRow) null);
    }

    public PdfPRow(PdfPCell[] pdfPCellArr, PdfPRow pdfPRow) {
        this.LOGGER = LoggerFactory.getLogger((Class<?>) PdfPRow.class);
        this.mayNotBreak = false;
        this.maxHeight = 0.0f;
        this.calculated = false;
        this.adjusted = false;
        this.role = PdfName.f655TR;
        this.accessibleAttributes = null;
        this.f681id = new AccessibleElementId();
        this.cells = pdfPCellArr;
        this.widths = new float[pdfPCellArr.length];
        initExtraHeights();
        if (pdfPRow != null) {
            this.f681id = pdfPRow.f681id;
            this.role = pdfPRow.role;
            if (pdfPRow.accessibleAttributes != null) {
                this.accessibleAttributes = new HashMap<>(pdfPRow.accessibleAttributes);
            }
        }
    }

    public PdfPRow(PdfPRow pdfPRow) {
        PdfPCell[] pdfPCellArr;
        this.LOGGER = LoggerFactory.getLogger((Class<?>) PdfPRow.class);
        this.mayNotBreak = false;
        this.maxHeight = 0.0f;
        this.calculated = false;
        this.adjusted = false;
        this.role = PdfName.f655TR;
        this.accessibleAttributes = null;
        this.f681id = new AccessibleElementId();
        this.mayNotBreak = pdfPRow.mayNotBreak;
        this.maxHeight = pdfPRow.maxHeight;
        this.calculated = pdfPRow.calculated;
        this.cells = new PdfPCell[pdfPRow.cells.length];
        int i = 0;
        while (true) {
            pdfPCellArr = this.cells;
            if (i >= pdfPCellArr.length) {
                break;
            }
            PdfPCell[] pdfPCellArr2 = pdfPRow.cells;
            if (pdfPCellArr2[i] != null) {
                if (pdfPCellArr2[i] instanceof PdfPHeaderCell) {
                    pdfPCellArr[i] = new PdfPHeaderCell((PdfPHeaderCell) pdfPCellArr2[i]);
                } else {
                    pdfPCellArr[i] = new PdfPCell(pdfPCellArr2[i]);
                }
            }
            i++;
        }
        float[] fArr = new float[pdfPCellArr.length];
        this.widths = fArr;
        System.arraycopy(pdfPRow.widths, 0, fArr, 0, pdfPCellArr.length);
        initExtraHeights();
        this.f681id = pdfPRow.f681id;
        this.role = pdfPRow.role;
        if (pdfPRow.accessibleAttributes != null) {
            this.accessibleAttributes = new HashMap<>(pdfPRow.accessibleAttributes);
        }
    }

    public boolean setWidths(float[] fArr) {
        int length = fArr.length;
        PdfPCell[] pdfPCellArr = this.cells;
        int i = 0;
        if (length != pdfPCellArr.length) {
            return false;
        }
        System.arraycopy(fArr, 0, this.widths, 0, pdfPCellArr.length);
        this.calculated = false;
        float f = 0.0f;
        while (i < fArr.length) {
            PdfPCell pdfPCell = this.cells[i];
            if (pdfPCell == null) {
                f += fArr[i];
            } else {
                pdfPCell.setLeft(f);
                int colspan = pdfPCell.getColspan() + i;
                while (i < colspan) {
                    f += fArr[i];
                    i++;
                }
                i--;
                pdfPCell.setRight(f);
                pdfPCell.setTop(0.0f);
            }
            i++;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void initExtraHeights() {
        this.extraHeights = new float[this.cells.length];
        int i = 0;
        while (true) {
            float[] fArr = this.extraHeights;
            if (i < fArr.length) {
                fArr[i] = 0.0f;
                i++;
            } else {
                return;
            }
        }
    }

    public void setExtraHeight(int i, float f) {
        if (i >= 0 && i < this.cells.length) {
            this.extraHeights[i] = f;
        }
    }

    /* access modifiers changed from: protected */
    public void calculateHeights() {
        float f;
        this.maxHeight = 0.0f;
        this.LOGGER.info("calculateHeights");
        int i = 0;
        while (true) {
            PdfPCell[] pdfPCellArr = this.cells;
            if (i < pdfPCellArr.length) {
                PdfPCell pdfPCell = pdfPCellArr[i];
                if (pdfPCell != null) {
                    if (pdfPCell.hasCalculatedHeight()) {
                        f = pdfPCell.getCalculatedHeight();
                    } else {
                        f = pdfPCell.getMaxHeight();
                    }
                    if (f > this.maxHeight && pdfPCell.getRowspan() == 1) {
                        this.maxHeight = f;
                    }
                }
                i++;
            } else {
                this.calculated = true;
                return;
            }
        }
    }

    public void setMayNotBreak(boolean z) {
        this.mayNotBreak = z;
    }

    public boolean isMayNotBreak() {
        return this.mayNotBreak;
    }

    public void writeBorderAndBackground(float f, float f2, float f3, PdfPCell pdfPCell, PdfContentByte[] pdfContentByteArr) {
        BaseColor backgroundColor = pdfPCell.getBackgroundColor();
        if (backgroundColor != null || pdfPCell.hasBorders()) {
            float right = pdfPCell.getRight() + f;
            float top2 = pdfPCell.getTop() + f2;
            float left = pdfPCell.getLeft() + f;
            float f4 = top2 - f3;
            if (backgroundColor != null) {
                PdfContentByte pdfContentByte = pdfContentByteArr[1];
                pdfContentByte.setColorFill(backgroundColor);
                pdfContentByte.rectangle(left, f4, right - left, top2 - f4);
                pdfContentByte.fill();
            }
            if (pdfPCell.hasBorders()) {
                Rectangle rectangle = new Rectangle(left, f4, right, top2);
                rectangle.cloneNonPositionParameters(pdfPCell);
                rectangle.setBackgroundColor((BaseColor) null);
                pdfContentByteArr[2].rectangle(rectangle);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void saveAndRotateCanvases(PdfContentByte[] pdfContentByteArr, float f, float f2, float f3, float f4, float f5, float f6) {
        if (this.canvasesPos == null) {
            this.canvasesPos = new int[8];
        }
        for (int i = 0; i < 4; i++) {
            ByteBuffer internalBuffer = pdfContentByteArr[i].getInternalBuffer();
            int i2 = i * 2;
            this.canvasesPos[i2] = internalBuffer.size();
            pdfContentByteArr[i].saveState();
            pdfContentByteArr[i].concatCTM(f, f2, f3, f4, f5, f6);
            this.canvasesPos[i2 + 1] = internalBuffer.size();
        }
    }

    /* access modifiers changed from: protected */
    public void restoreCanvases(PdfContentByte[] pdfContentByteArr) {
        for (int i = 0; i < 4; i++) {
            ByteBuffer internalBuffer = pdfContentByteArr[i].getInternalBuffer();
            int size = internalBuffer.size();
            pdfContentByteArr[i].restoreState();
            int[] iArr = this.canvasesPos;
            int i2 = i * 2;
            if (size == iArr[i2 + 1]) {
                internalBuffer.setSize(iArr[i2]);
            }
        }
    }

    public static float setColumn(ColumnText columnText, float f, float f2, float f3, float f4) {
        if (f > f3) {
            f3 = f;
        }
        if (f2 > f4) {
            f4 = f2;
        }
        columnText.setSimpleColumn(f, f2, f3, f4);
        return f4;
    }

    public void writeCells(int i, int i2, float f, float f2, PdfContentByte[] pdfContentByteArr, boolean z) {
        int i3;
        int i4;
        ColumnText columnText;
        float f3;
        float f4;
        float f5;
        float f6;
        ColumnText columnText2;
        boolean z2;
        float f7;
        float f8;
        float f9;
        int i5 = i2;
        PdfContentByte[] pdfContentByteArr2 = pdfContentByteArr;
        if (!this.calculated) {
            calculateHeights();
        }
        if (i5 < 0) {
            i3 = this.cells.length;
        } else {
            i3 = Math.min(i5, this.cells.length);
        }
        int i6 = i < 0 ? 0 : i;
        if (i6 < i3) {
            int i7 = i6;
            float f10 = f;
            while (i7 >= 0 && this.cells[i7] == null) {
                if (i7 > 0) {
                    f10 -= this.widths[i7 - 1];
                }
                i7--;
            }
            if (i7 < 0) {
                i7 = 0;
            }
            PdfPCell[] pdfPCellArr = this.cells;
            if (pdfPCellArr[i7] != null) {
                f10 -= pdfPCellArr[i7].getLeft();
            }
            float f11 = f10;
            char c = 3;
            if (isTagged(pdfContentByteArr2[3])) {
                pdfContentByteArr2[3].openMCBlock(this);
            }
            int i8 = i7;
            while (i8 < i3) {
                PdfPCell pdfPCell = this.cells[i8];
                if (pdfPCell == null) {
                    i4 = i8;
                } else {
                    if (isTagged(pdfContentByteArr2[c])) {
                        pdfContentByteArr2[c].openMCBlock(pdfPCell);
                    }
                    float f12 = this.maxHeight + this.extraHeights[i8];
                    writeBorderAndBackground(f11, f2, f12, pdfPCell, pdfContentByteArr);
                    Image image = pdfPCell.getImage();
                    float top2 = (pdfPCell.getTop() + f2) - pdfPCell.getEffectivePaddingTop();
                    if (pdfPCell.getHeight() <= f12) {
                        int verticalAlignment = pdfPCell.getVerticalAlignment();
                        if (verticalAlignment == 5) {
                            f9 = pdfPCell.getTop() + f2 + ((pdfPCell.getHeight() - f12) / 2.0f);
                            f8 = pdfPCell.getEffectivePaddingTop();
                        } else if (verticalAlignment == 6) {
                            f9 = ((pdfPCell.getTop() + f2) - f12) + pdfPCell.getHeight();
                            f8 = pdfPCell.getEffectivePaddingTop();
                        }
                        top2 = f9 - f8;
                    }
                    if (image != null) {
                        if (pdfPCell.getRotation() != 0) {
                            image = Image.getInstance(image);
                            i4 = i8;
                            image.setRotation(image.getImageRotation() + ((float) ((((double) pdfPCell.getRotation()) * 3.141592653589793d) / 180.0d)));
                        } else {
                            i4 = i8;
                        }
                        if (pdfPCell.getHeight() <= f12) {
                            z2 = false;
                        } else if (image.isScaleToFitHeight()) {
                            image.scalePercent(100.0f);
                            image.scalePercent((((f12 - pdfPCell.getEffectivePaddingTop()) - pdfPCell.getEffectivePaddingBottom()) / image.getScaledHeight()) * 100.0f);
                            z2 = true;
                        }
                        float left = pdfPCell.getLeft() + f11 + pdfPCell.getEffectivePaddingLeft();
                        if (z2) {
                            int horizontalAlignment = pdfPCell.getHorizontalAlignment();
                            if (horizontalAlignment != 1) {
                                if (horizontalAlignment == 2) {
                                    f7 = ((pdfPCell.getRight() + f11) - pdfPCell.getEffectivePaddingRight()) - image.getScaledWidth();
                                }
                                top2 = (pdfPCell.getTop() + f2) - pdfPCell.getEffectivePaddingTop();
                            } else {
                                f7 = (((((pdfPCell.getLeft() + pdfPCell.getEffectivePaddingLeft()) + pdfPCell.getRight()) - pdfPCell.getEffectivePaddingRight()) - image.getScaledWidth()) / 2.0f) + f11;
                            }
                            left = f7;
                            top2 = (pdfPCell.getTop() + f2) - pdfPCell.getEffectivePaddingTop();
                        }
                        image.setAbsolutePosition(left, top2 - image.getScaledHeight());
                        try {
                            if (isTagged(pdfContentByteArr2[3])) {
                                pdfContentByteArr2[3].openMCBlock(image);
                            }
                            pdfContentByteArr2[3].addImage(image);
                            if (isTagged(pdfContentByteArr2[3])) {
                                pdfContentByteArr2[3].closeMCBlock(image);
                            }
                        } catch (DocumentException e) {
                            throw new ExceptionConverter(e);
                        }
                    } else {
                        i4 = i8;
                        if (pdfPCell.getRotation() == 90 || pdfPCell.getRotation() == 270) {
                            float effectivePaddingTop = (f12 - pdfPCell.getEffectivePaddingTop()) - pdfPCell.getEffectivePaddingBottom();
                            float width = (pdfPCell.getWidth() - pdfPCell.getEffectivePaddingLeft()) - pdfPCell.getEffectivePaddingRight();
                            ColumnText duplicate = ColumnText.duplicate(pdfPCell.getColumn());
                            duplicate.setCanvases(pdfContentByteArr2);
                            duplicate.setSimpleColumn(0.0f, 0.0f, 0.001f + effectivePaddingTop, -width);
                            try {
                                duplicate.mo20966go(true);
                                float f13 = -duplicate.getYLine();
                                if (effectivePaddingTop <= 0.0f || width <= 0.0f) {
                                    f13 = 0.0f;
                                }
                                if (f13 > 0.0f) {
                                    if (pdfPCell.isUseDescender()) {
                                        f13 -= duplicate.getDescender();
                                    }
                                    if (z) {
                                        columnText = ColumnText.duplicate(pdfPCell.getColumn());
                                    } else {
                                        columnText = pdfPCell.getColumn();
                                    }
                                    ColumnText columnText3 = columnText;
                                    columnText3.setCanvases(pdfContentByteArr2);
                                    columnText3.setSimpleColumn(-0.003f, -0.001f, effectivePaddingTop + 0.003f, f13);
                                    if (pdfPCell.getRotation() == 90) {
                                        float top3 = ((pdfPCell.getTop() + f2) - f12) + pdfPCell.getEffectivePaddingBottom();
                                        int verticalAlignment2 = pdfPCell.getVerticalAlignment();
                                        if (verticalAlignment2 != 5) {
                                            f6 = verticalAlignment2 != 6 ? pdfPCell.getLeft() + f11 + pdfPCell.getEffectivePaddingLeft() + f13 : ((pdfPCell.getLeft() + f11) + pdfPCell.getWidth()) - pdfPCell.getEffectivePaddingRight();
                                        } else {
                                            f6 = pdfPCell.getLeft() + f11 + ((((pdfPCell.getWidth() + pdfPCell.getEffectivePaddingLeft()) - pdfPCell.getEffectivePaddingRight()) + f13) / 2.0f);
                                        }
                                        saveAndRotateCanvases(pdfContentByteArr, 0.0f, 1.0f, -1.0f, 0.0f, f6, top3);
                                    } else {
                                        float top4 = (pdfPCell.getTop() + f2) - pdfPCell.getEffectivePaddingTop();
                                        int verticalAlignment3 = pdfPCell.getVerticalAlignment();
                                        if (verticalAlignment3 == 5) {
                                            f5 = pdfPCell.getLeft() + f11;
                                            f4 = (((pdfPCell.getWidth() + pdfPCell.getEffectivePaddingLeft()) - pdfPCell.getEffectivePaddingRight()) - f13) / 2.0f;
                                        } else if (verticalAlignment3 != 6) {
                                            f3 = (((pdfPCell.getLeft() + f11) + pdfPCell.getWidth()) - pdfPCell.getEffectivePaddingRight()) - f13;
                                            saveAndRotateCanvases(pdfContentByteArr, 0.0f, -1.0f, 1.0f, 0.0f, f3, top4);
                                        } else {
                                            f5 = pdfPCell.getLeft() + f11;
                                            f4 = pdfPCell.getEffectivePaddingLeft();
                                        }
                                        f3 = f5 + f4;
                                        saveAndRotateCanvases(pdfContentByteArr, 0.0f, -1.0f, 1.0f, 0.0f, f3, top4);
                                    }
                                    try {
                                        columnText3.mo20965go();
                                        restoreCanvases(pdfContentByteArr2);
                                    } catch (DocumentException e2) {
                                        throw new ExceptionConverter(e2);
                                    } catch (Throwable th) {
                                        restoreCanvases(pdfContentByteArr2);
                                        throw th;
                                    }
                                }
                            } catch (DocumentException e3) {
                                throw new ExceptionConverter(e3);
                            }
                        } else {
                            float fixedHeight = pdfPCell.getFixedHeight();
                            float right = (pdfPCell.getRight() + f11) - pdfPCell.getEffectivePaddingRight();
                            float left2 = pdfPCell.getLeft() + f11 + pdfPCell.getEffectivePaddingLeft();
                            if (pdfPCell.isNoWrap()) {
                                int horizontalAlignment2 = pdfPCell.getHorizontalAlignment();
                                if (horizontalAlignment2 == 1) {
                                    right += 10000.0f;
                                    left2 -= 10000.0f;
                                } else if (horizontalAlignment2 == 2 ? pdfPCell.getRotation() != 180 : pdfPCell.getRotation() == 180) {
                                    left2 -= 20000.0f;
                                } else {
                                    right += 20000.0f;
                                }
                            }
                            if (z) {
                                columnText2 = ColumnText.duplicate(pdfPCell.getColumn());
                            } else {
                                columnText2 = pdfPCell.getColumn();
                            }
                            ColumnText columnText4 = columnText2;
                            columnText4.setCanvases(pdfContentByteArr2);
                            float effectivePaddingTop2 = top2 - ((f12 - pdfPCell.getEffectivePaddingTop()) - pdfPCell.getEffectivePaddingBottom());
                            if (fixedHeight > 0.0f && pdfPCell.getHeight() > f12) {
                                top2 = (pdfPCell.getTop() + f2) - pdfPCell.getEffectivePaddingTop();
                                effectivePaddingTop2 = pdfPCell.getEffectivePaddingBottom() + ((pdfPCell.getTop() + f2) - f12);
                            }
                            if ((top2 > effectivePaddingTop2 || columnText4.zeroHeightElement()) && left2 < right) {
                                columnText4.setSimpleColumn(left2, effectivePaddingTop2 - 0.001f, right, top2);
                                if (pdfPCell.getRotation() == 180) {
                                    saveAndRotateCanvases(pdfContentByteArr, -1.0f, 0.0f, 0.0f, -1.0f, left2 + right, (((f2 + f2) - f12) + pdfPCell.getEffectivePaddingBottom()) - pdfPCell.getEffectivePaddingTop());
                                }
                                try {
                                    columnText4.mo20965go();
                                    if (pdfPCell.getRotation() == 180) {
                                        restoreCanvases(pdfContentByteArr2);
                                    }
                                } catch (DocumentException e4) {
                                    throw new ExceptionConverter(e4);
                                } catch (Throwable th2) {
                                    if (pdfPCell.getRotation() == 180) {
                                        restoreCanvases(pdfContentByteArr2);
                                    }
                                    throw th2;
                                }
                            }
                        }
                    }
                    PdfPCellEvent cellEvent = pdfPCell.getCellEvent();
                    if (cellEvent != null) {
                        cellEvent.cellLayout(pdfPCell, new Rectangle(pdfPCell.getLeft() + f11, (pdfPCell.getTop() + f2) - f12, pdfPCell.getRight() + f11, pdfPCell.getTop() + f2), pdfContentByteArr2);
                    }
                    if (isTagged(pdfContentByteArr2[3])) {
                        pdfContentByteArr2[3].closeMCBlock(pdfPCell);
                    }
                }
                i8 = i4 + 1;
                c = 3;
            }
            if (isTagged(pdfContentByteArr2[3])) {
                pdfContentByteArr2[3].closeMCBlock(this);
            }
        }
    }

    public boolean isCalculated() {
        return this.calculated;
    }

    public float getMaxHeights() {
        if (!this.calculated) {
            calculateHeights();
        }
        return this.maxHeight;
    }

    public void setMaxHeights(float f) {
        this.maxHeight = f;
    }

    /* access modifiers changed from: package-private */
    public float[] getEventWidth(float f, float[] fArr) {
        int i = 1;
        int i2 = 0;
        int i3 = 1;
        while (true) {
            PdfPCell[] pdfPCellArr = this.cells;
            if (i2 < pdfPCellArr.length) {
                if (pdfPCellArr[i2] == null) {
                    while (true) {
                        PdfPCell[] pdfPCellArr2 = this.cells;
                        if (i2 >= pdfPCellArr2.length || pdfPCellArr2[i2] != null) {
                            break;
                        }
                        i3++;
                        i2++;
                    }
                } else {
                    i3++;
                    i2 += pdfPCellArr[i2].getColspan();
                }
            } else {
                break;
            }
        }
        float[] fArr2 = new float[i3];
        fArr2[0] = f;
        int i4 = 0;
        while (true) {
            PdfPCell[] pdfPCellArr3 = this.cells;
            if (i4 >= pdfPCellArr3.length || i >= i3) {
                return fArr2;
            }
            if (pdfPCellArr3[i4] == null) {
                fArr2[i] = fArr2[i - 1];
                while (true) {
                    PdfPCell[] pdfPCellArr4 = this.cells;
                    if (i4 >= pdfPCellArr4.length || pdfPCellArr4[i4] != null) {
                        break;
                    }
                    fArr2[i] = fArr2[i] + fArr[i4];
                    i4++;
                }
            } else {
                int colspan = pdfPCellArr3[i4].getColspan();
                fArr2[i] = fArr2[i - 1];
                int i5 = 0;
                while (i5 < colspan && i4 < fArr.length) {
                    fArr2[i] = fArr2[i] + fArr[i4];
                    i5++;
                    i4++;
                }
            }
            i++;
        }
        return fArr2;
    }

    public void copyRowContent(PdfPTable pdfPTable, int i) {
        if (pdfPTable != null) {
            for (int i2 = 0; i2 < this.cells.length; i2++) {
                PdfPCell pdfPCell = pdfPTable.getRow(i).getCells()[i2];
                int i3 = i;
                while (pdfPCell == null && i3 > 0) {
                    i3--;
                    pdfPCell = pdfPTable.getRow(i3).getCells()[i2];
                }
                PdfPCell[] pdfPCellArr = this.cells;
                if (!(pdfPCellArr[i2] == null || pdfPCell == null)) {
                    pdfPCellArr[i2].setColumn(pdfPCell.getColumn());
                    this.calculated = false;
                }
            }
        }
    }

    public PdfPRow splitRow(PdfPTable pdfPTable, int i, float f) {
        float[] fArr;
        float f2;
        PdfPTable pdfPTable2 = pdfPTable;
        int i2 = i;
        float f3 = f;
        this.LOGGER.info(String.format("Splitting row %s available height: %s", new Object[]{Integer.valueOf(i), Float.valueOf(f)}));
        PdfPCell[] pdfPCellArr = this.cells;
        PdfPCell[] pdfPCellArr2 = new PdfPCell[pdfPCellArr.length];
        float[] fArr2 = new float[pdfPCellArr.length];
        float[] fArr3 = new float[pdfPCellArr.length];
        float[] fArr4 = new float[pdfPCellArr.length];
        int i3 = 0;
        boolean z = true;
        while (true) {
            PdfPCell[] pdfPCellArr3 = this.cells;
            if (i3 >= pdfPCellArr3.length) {
                break;
            }
            PdfPCell pdfPCell = pdfPCellArr3[i3];
            if (pdfPCell == null) {
                if (pdfPTable2.rowSpanAbove(i2, i3)) {
                    int i4 = i2;
                    while (true) {
                        i4--;
                        if (!pdfPTable2.rowSpanAbove(i4, i3)) {
                            break;
                        }
                        pdfPTable2.getRow(i4).getMaxHeights();
                    }
                    PdfPRow row = pdfPTable2.getRow(i4);
                    if (!(row == null || row.getCells()[i3] == null)) {
                        pdfPCellArr2[i3] = new PdfPCell(row.getCells()[i3]);
                        pdfPCellArr2[i3].setColumn((ColumnText) null);
                        pdfPCellArr2[i3].setRowspan((row.getCells()[i3].getRowspan() - i2) + i4);
                        z = false;
                    }
                }
                fArr = fArr4;
            } else {
                fArr2[i3] = pdfPCell.getCalculatedHeight();
                fArr3[i3] = pdfPCell.getFixedHeight();
                fArr4[i3] = pdfPCell.getMinimumHeight();
                Image image = pdfPCell.getImage();
                PdfPCell pdfPCell2 = new PdfPCell(pdfPCell);
                if (image != null) {
                    float effectivePaddingBottom = pdfPCell.getEffectivePaddingBottom() + pdfPCell.getEffectivePaddingTop() + 2.0f;
                    if ((image.isScaleToFitHeight() || image.getScaledHeight() + effectivePaddingBottom < f3) && f3 > effectivePaddingBottom) {
                        pdfPCell2.setPhrase((Phrase) null);
                        z = false;
                    }
                    fArr = fArr4;
                } else {
                    ColumnText duplicate = ColumnText.duplicate(pdfPCell.getColumn());
                    float left = pdfPCell.getLeft() + pdfPCell.getEffectivePaddingLeft();
                    float top2 = (pdfPCell.getTop() + pdfPCell.getEffectivePaddingBottom()) - f3;
                    float right = pdfPCell.getRight() - pdfPCell.getEffectivePaddingRight();
                    float top3 = pdfPCell.getTop() - pdfPCell.getEffectivePaddingTop();
                    int rotation = pdfPCell.getRotation();
                    fArr = fArr4;
                    if (rotation == 90 || rotation == 270) {
                        f2 = setColumn(duplicate, top2, left, top3, right);
                    } else {
                        float f4 = top2 + 1.0E-5f;
                        if (pdfPCell.isNoWrap()) {
                            right = 20000.0f;
                        }
                        f2 = setColumn(duplicate, left, f4, right, top3);
                    }
                    try {
                        int go = duplicate.mo20966go(true);
                        boolean z2 = duplicate.getYLine() == f2;
                        if (z2) {
                            pdfPCell2.setColumn(ColumnText.duplicate(pdfPCell.getColumn()));
                            duplicate.setFilledWidth(0.0f);
                        } else if ((go & 1) == 0) {
                            pdfPCell2.setColumn(duplicate);
                            duplicate.setFilledWidth(0.0f);
                        } else {
                            pdfPCell2.setPhrase((Phrase) null);
                        }
                        z = z && z2;
                    } catch (DocumentException e) {
                        throw new ExceptionConverter(e);
                    }
                }
                pdfPCellArr2[i3] = pdfPCell2;
                pdfPCell.setCalculatedHeight(f3);
            }
            i3++;
            pdfPTable2 = pdfPTable;
            i2 = i;
            fArr4 = fArr;
        }
        float[] fArr5 = fArr4;
        if (z) {
            int i5 = 0;
            while (true) {
                PdfPCell[] pdfPCellArr4 = this.cells;
                if (i5 >= pdfPCellArr4.length) {
                    return null;
                }
                PdfPCell pdfPCell3 = pdfPCellArr4[i5];
                if (pdfPCell3 != null) {
                    pdfPCell3.setCalculatedHeight(fArr2[i5]);
                    if (fArr3[i5] > 0.0f) {
                        pdfPCell3.setFixedHeight(fArr3[i5]);
                    } else {
                        pdfPCell3.setMinimumHeight(fArr5[i5]);
                    }
                }
                i5++;
            }
        } else {
            calculateHeights();
            PdfPRow pdfPRow = new PdfPRow(pdfPCellArr2, this);
            pdfPRow.widths = (float[]) this.widths.clone();
            return pdfPRow;
        }
    }

    public float getMaxRowHeightsWithoutCalculating() {
        return this.maxHeight;
    }

    public void setFinalMaxHeights(float f) {
        setMaxHeights(f);
        this.calculated = true;
    }

    public void splitRowspans(PdfPTable pdfPTable, int i, PdfPTable pdfPTable2, int i2) {
        if (pdfPTable != null && pdfPTable2 != null) {
            int i3 = 0;
            while (true) {
                PdfPCell[] pdfPCellArr = this.cells;
                if (i3 >= pdfPCellArr.length) {
                    return;
                }
                if (pdfPCellArr[i3] == null) {
                    int cellStartRowIndex = pdfPTable.getCellStartRowIndex(i, i3);
                    int cellStartRowIndex2 = pdfPTable2.getCellStartRowIndex(i2, i3);
                    PdfPCell pdfPCell = pdfPTable.getRow(cellStartRowIndex).getCells()[i3];
                    PdfPCell pdfPCell2 = pdfPTable2.getRow(cellStartRowIndex2).getCells()[i3];
                    if (pdfPCell != null) {
                        this.cells[i3] = new PdfPCell(pdfPCell2);
                        int i4 = (i2 - cellStartRowIndex2) + 1;
                        this.cells[i3].setRowspan(pdfPCell2.getRowspan() - i4);
                        pdfPCell.setRowspan(i4);
                        this.calculated = false;
                    }
                    i3++;
                } else {
                    i3 += pdfPCellArr[i3].getColspan();
                }
            }
        }
    }

    public PdfPCell[] getCells() {
        return this.cells;
    }

    public boolean hasRowspan() {
        int i = 0;
        while (true) {
            PdfPCell[] pdfPCellArr = this.cells;
            if (i >= pdfPCellArr.length) {
                return false;
            }
            if (pdfPCellArr[i] != null && pdfPCellArr[i].getRowspan() > 1) {
                return true;
            }
            i++;
        }
    }

    public boolean isAdjusted() {
        return this.adjusted;
    }

    public void setAdjusted(boolean z) {
        this.adjusted = z;
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
        return this.f681id;
    }

    public void setId(AccessibleElementId accessibleElementId) {
        this.f681id = accessibleElementId;
    }

    private static boolean isTagged(PdfContentByte pdfContentByte) {
        return (pdfContentByte == null || pdfContentByte.writer == null || !pdfContentByte.writer.isTagged()) ? false : true;
    }
}
