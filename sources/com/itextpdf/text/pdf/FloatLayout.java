package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfDiv;
import java.util.ArrayList;
import java.util.List;

public class FloatLayout {
    protected final ColumnText compositeColumn;
    protected final List<Element> content;
    protected float filledWidth;
    protected float floatLeftX;
    protected float floatRightX;
    protected float leftX;
    protected float maxY;
    protected float minY;
    protected float rightX;
    protected final boolean useAscender;
    protected float yLine;

    public float getYLine() {
        return this.yLine;
    }

    public void setYLine(float f) {
        this.yLine = f;
    }

    public float getFilledWidth() {
        return this.filledWidth;
    }

    public void setFilledWidth(float f) {
        this.filledWidth = f;
    }

    public int getRunDirection() {
        return this.compositeColumn.getRunDirection();
    }

    public void setRunDirection(int i) {
        this.compositeColumn.setRunDirection(i);
    }

    public FloatLayout(List<Element> list, boolean z) {
        ColumnText columnText = new ColumnText((PdfContentByte) null);
        this.compositeColumn = columnText;
        columnText.setUseAscender(z);
        this.useAscender = z;
        this.content = list;
    }

    public void setSimpleColumn(float f, float f2, float f3, float f4) {
        this.leftX = Math.min(f, f3);
        this.maxY = Math.max(f2, f4);
        this.minY = Math.min(f2, f4);
        float max = Math.max(f, f3);
        this.rightX = max;
        this.floatLeftX = this.leftX;
        this.floatRightX = max;
        this.yLine = this.maxY;
        this.filledWidth = 0.0f;
    }

    public int layout(PdfContentByte pdfContentByte, boolean z) throws DocumentException {
        PdfDiv pdfDiv;
        this.compositeColumn.setCanvas(pdfContentByte);
        ArrayList arrayList = new ArrayList();
        List arrayList2 = z ? new ArrayList(this.content) : this.content;
        int i = 1;
        while (true) {
            if (arrayList2.isEmpty()) {
                break;
            } else if (arrayList2.get(0) instanceof PdfDiv) {
                pdfDiv = (PdfDiv) arrayList2.get(0);
                if (pdfDiv.getFloatType() == PdfDiv.FloatType.LEFT || pdfDiv.getFloatType() == PdfDiv.FloatType.RIGHT) {
                    arrayList.add(pdfDiv);
                    arrayList2.remove(0);
                } else {
                    if (!arrayList.isEmpty()) {
                        i = floatingLayout(arrayList, z);
                        if ((i & 1) == 0) {
                            break;
                        }
                    }
                    arrayList2.remove(0);
                    i = pdfDiv.layout(pdfContentByte, this.useAscender, true, this.floatLeftX, this.minY, this.floatRightX, this.yLine);
                    if (!pdfDiv.getKeepTogether() || (i & 1) != 0 || (this.compositeColumn.getCanvas().getPdfDocument().currentHeight <= 0.0f && this.yLine == this.maxY)) {
                        if (!z) {
                            pdfContentByte.openMCBlock(pdfDiv);
                            i = pdfDiv.layout(pdfContentByte, this.useAscender, z, this.floatLeftX, this.minY, this.floatRightX, this.yLine);
                            pdfContentByte.closeMCBlock(pdfDiv);
                        }
                        if (pdfDiv.getActualWidth() > this.filledWidth) {
                            this.filledWidth = pdfDiv.getActualWidth();
                        }
                        if ((i & 1) == 0) {
                            arrayList2.add(0, pdfDiv);
                            this.yLine = pdfDiv.getYLine();
                            break;
                        }
                        this.yLine -= pdfDiv.getActualHeight();
                    }
                }
            } else {
                arrayList.add(arrayList2.get(0));
                arrayList2.remove(0);
            }
        }
        arrayList2.add(0, pdfDiv);
        if ((i & 1) != 0 && !arrayList.isEmpty()) {
            i = floatingLayout(arrayList, z);
        }
        arrayList2.addAll(0, arrayList);
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:138:0x0262 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x027e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int floatingLayout(java.util.List<com.itextpdf.text.Element> r26, boolean r27) throws com.itextpdf.text.DocumentException {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r10 = r27
            float r2 = r0.yLine
            com.itextpdf.text.pdf.ColumnText r3 = r0.compositeColumn
            if (r10 == 0) goto L_0x0010
            com.itextpdf.text.pdf.ColumnText r3 = com.itextpdf.text.pdf.ColumnText.duplicate(r3)
        L_0x0010:
            r11 = r3
            float r3 = r0.maxY
            float r4 = r0.yLine
            r13 = 0
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x001c
            r3 = 1
            goto L_0x001d
        L_0x001c:
            r3 = 0
        L_0x001d:
            r15 = r2
            r16 = r3
            r2 = 1
            r8 = 0
            r9 = 0
        L_0x0023:
            boolean r3 = r26.isEmpty()
            if (r3 != 0) goto L_0x030c
            java.lang.Object r2 = r1.get(r13)
            r7 = r2
            com.itextpdf.text.Element r7 = (com.itextpdf.text.Element) r7
            r1.remove(r13)
            boolean r2 = r7 instanceof com.itextpdf.text.pdf.PdfDiv
            if (r2 == 0) goto L_0x0125
            r6 = r7
            com.itextpdf.text.pdf.PdfDiv r6 = (com.itextpdf.text.pdf.PdfDiv) r6
            com.itextpdf.text.pdf.ColumnText r2 = r0.compositeColumn
            com.itextpdf.text.pdf.PdfContentByte r18 = r2.getCanvas()
            boolean r2 = r0.useAscender
            r20 = 1
            float r3 = r0.floatLeftX
            float r4 = r0.minY
            float r5 = r0.floatRightX
            float r12 = r0.yLine
            r17 = r6
            r19 = r2
            r21 = r3
            r22 = r4
            r23 = r5
            r24 = r12
            int r2 = r17.layout(r18, r19, r20, r21, r22, r23, r24)
            r3 = r2 & 1
            if (r3 != 0) goto L_0x0095
            r0.yLine = r15
            float r2 = r0.leftX
            r0.floatLeftX = r2
            float r2 = r0.rightX
            r0.floatRightX = r2
            com.itextpdf.text.pdf.ColumnText r2 = r0.compositeColumn
            com.itextpdf.text.pdf.PdfContentByte r18 = r2.getCanvas()
            boolean r2 = r0.useAscender
            r20 = 1
            float r3 = r0.floatLeftX
            float r4 = r0.minY
            float r5 = r0.floatRightX
            float r12 = r0.yLine
            r17 = r6
            r19 = r2
            r21 = r3
            r22 = r4
            r23 = r5
            r24 = r12
            int r2 = r17.layout(r18, r19, r20, r21, r22, r23, r24)
            r3 = r2 & 1
            if (r3 != 0) goto L_0x0095
            r1.add(r13, r6)
            goto L_0x030e
        L_0x0095:
            com.itextpdf.text.pdf.PdfDiv$FloatType r3 = r6.getFloatType()
            com.itextpdf.text.pdf.PdfDiv$FloatType r4 = com.itextpdf.text.pdf.PdfDiv.FloatType.LEFT
            if (r3 != r4) goto L_0x00d3
            com.itextpdf.text.pdf.ColumnText r2 = r0.compositeColumn
            com.itextpdf.text.pdf.PdfContentByte r3 = r2.getCanvas()
            boolean r4 = r0.useAscender
            float r12 = r0.floatLeftX
            float r5 = r0.minY
            float r2 = r0.floatRightX
            float r14 = r0.yLine
            r18 = r2
            r2 = r6
            r19 = r5
            r5 = r27
            r20 = r6
            r6 = r12
            r12 = r7
            r7 = r19
            r13 = r8
            r8 = r18
            r10 = r9
            r9 = r14
            int r2 = r2.layout(r3, r4, r5, r6, r7, r8, r9)
            float r3 = r0.floatLeftX
            float r4 = r20.getActualWidth()
            float r3 = r3 + r4
            r0.floatLeftX = r3
            float r3 = r20.getActualWidth()
            float r9 = r10 + r3
            goto L_0x0114
        L_0x00d3:
            r20 = r6
            r12 = r7
            r13 = r8
            r10 = r9
            com.itextpdf.text.pdf.PdfDiv$FloatType r3 = r20.getFloatType()
            com.itextpdf.text.pdf.PdfDiv$FloatType r4 = com.itextpdf.text.pdf.PdfDiv.FloatType.RIGHT
            if (r3 != r4) goto L_0x0113
            com.itextpdf.text.pdf.ColumnText r2 = r0.compositeColumn
            com.itextpdf.text.pdf.PdfContentByte r3 = r2.getCanvas()
            boolean r4 = r0.useAscender
            float r2 = r0.floatRightX
            float r5 = r20.getActualWidth()
            float r2 = r2 - r5
            r5 = 1008981770(0x3c23d70a, float:0.01)
            float r6 = r2 - r5
            float r7 = r0.minY
            float r8 = r0.floatRightX
            float r9 = r0.yLine
            r2 = r20
            r5 = r27
            int r2 = r2.layout(r3, r4, r5, r6, r7, r8, r9)
            float r3 = r0.floatRightX
            float r4 = r20.getActualWidth()
            float r3 = r3 - r4
            r0.floatRightX = r3
            float r3 = r20.getActualWidth()
            float r8 = r13 + r3
            r9 = r10
            goto L_0x0115
        L_0x0113:
            r9 = r10
        L_0x0114:
            r8 = r13
        L_0x0115:
            float r3 = r0.yLine
            float r4 = r20.getActualHeight()
            float r3 = r3 - r4
            float r3 = java.lang.Math.min(r15, r3)
            r4 = r27
            r15 = r3
            goto L_0x0281
        L_0x0125:
            r12 = r7
            r13 = r8
            r10 = r9
            float r2 = r0.minY
            r3 = 0
            int r2 = (r2 > r15 ? 1 : (r2 == r15 ? 0 : -1))
            if (r2 <= 0) goto L_0x013d
            r2 = 2
            r4 = 0
            r1.add(r4, r12)
            if (r11 == 0) goto L_0x0139
            r11.setText(r3)
        L_0x0139:
            r9 = r10
            r8 = r13
            goto L_0x030e
        L_0x013d:
            boolean r2 = r12 instanceof com.itextpdf.text.api.Spaceable
            if (r2 == 0) goto L_0x0161
            if (r16 == 0) goto L_0x0155
            boolean r2 = r11.isIgnoreSpacingBefore()
            if (r2 == 0) goto L_0x0155
            r7 = r12
            com.itextpdf.text.api.Spaceable r7 = (com.itextpdf.text.api.Spaceable) r7
            float r2 = r7.getPaddingTop()
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x0161
        L_0x0155:
            float r2 = r0.yLine
            r7 = r12
            com.itextpdf.text.api.Spaceable r7 = (com.itextpdf.text.api.Spaceable) r7
            float r4 = r7.getSpacingBefore()
            float r2 = r2 - r4
            r0.yLine = r2
        L_0x0161:
            r4 = r27
            r14 = r10
            if (r4 == 0) goto L_0x017a
            boolean r2 = r12 instanceof com.itextpdf.text.pdf.PdfPTable
            if (r2 == 0) goto L_0x0176
            com.itextpdf.text.pdf.PdfPTable r2 = new com.itextpdf.text.pdf.PdfPTable
            r7 = r12
            com.itextpdf.text.pdf.PdfPTable r7 = (com.itextpdf.text.pdf.PdfPTable) r7
            r2.<init>((com.itextpdf.text.pdf.PdfPTable) r7)
            r11.addElement(r2)
            goto L_0x017d
        L_0x0176:
            r11.addElement(r12)
            goto L_0x017d
        L_0x017a:
            r11.addElement(r12)
        L_0x017d:
            float r2 = r0.yLine
            int r5 = (r2 > r15 ? 1 : (r2 == r15 ? 0 : -1))
            if (r5 <= 0) goto L_0x018b
            float r5 = r0.floatLeftX
            float r6 = r0.floatRightX
            r11.setSimpleColumn(r5, r2, r6, r15)
            goto L_0x0194
        L_0x018b:
            float r5 = r0.floatLeftX
            float r6 = r0.floatRightX
            float r7 = r0.minY
            r11.setSimpleColumn(r5, r2, r6, r7)
        L_0x0194:
            r2 = 0
            r11.setFilledWidth(r2)
            int r2 = r11.mo20966go(r4)
            float r5 = r0.yLine
            int r5 = (r5 > r15 ? 1 : (r5 == r15 ? 0 : -1))
            if (r5 <= 0) goto L_0x021a
            float r5 = r0.floatLeftX
            float r6 = r0.leftX
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto L_0x01b2
            float r5 = r0.floatRightX
            float r7 = r0.rightX
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x021a
        L_0x01b2:
            r5 = r2 & 1
            if (r5 != 0) goto L_0x021a
            r0.yLine = r15
            r0.floatLeftX = r6
            float r2 = r0.rightX
            r0.floatRightX = r2
            r5 = 0
            int r7 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x01cb
            int r7 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x01cb
            float r2 = r2 - r6
            r0.filledWidth = r2
            goto L_0x01db
        L_0x01cb:
            float r2 = r0.filledWidth
            int r2 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x01d3
            r0.filledWidth = r14
        L_0x01d3:
            float r2 = r0.filledWidth
            int r2 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x01db
            r0.filledWidth = r13
        L_0x01db:
            if (r4 == 0) goto L_0x01ec
            boolean r2 = r12 instanceof com.itextpdf.text.pdf.PdfPTable
            if (r2 == 0) goto L_0x01ec
            com.itextpdf.text.pdf.PdfPTable r2 = new com.itextpdf.text.pdf.PdfPTable
            r7 = r12
            com.itextpdf.text.pdf.PdfPTable r7 = (com.itextpdf.text.pdf.PdfPTable) r7
            r2.<init>((com.itextpdf.text.pdf.PdfPTable) r7)
            r11.addElement(r2)
        L_0x01ec:
            float r2 = r0.floatLeftX
            float r5 = r0.yLine
            float r6 = r0.floatRightX
            float r7 = r0.minY
            r11.setSimpleColumn(r2, r5, r6, r7)
            int r2 = r11.mo20966go(r4)
            float r5 = r11.getYLine()
            float r6 = r11.getDescender()
            float r5 = r5 + r6
            r0.yLine = r5
            float r6 = r11.getFilledWidth()
            float r7 = r0.filledWidth
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 <= 0) goto L_0x0216
            float r6 = r11.getFilledWidth()
            r0.filledWidth = r6
        L_0x0216:
            r15 = r5
            r8 = 0
            r9 = 0
            goto L_0x025e
        L_0x021a:
            r5 = 0
            int r6 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r6 <= 0) goto L_0x0227
            float r6 = r11.getFilledWidth()
            float r8 = r13 + r6
        L_0x0225:
            r9 = r14
            goto L_0x0245
        L_0x0227:
            int r6 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r6 <= 0) goto L_0x0233
            float r5 = r11.getFilledWidth()
            float r9 = r14 + r5
            r8 = r13
            goto L_0x0245
        L_0x0233:
            float r5 = r11.getFilledWidth()
            float r6 = r0.filledWidth
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x0243
            float r5 = r11.getFilledWidth()
            r0.filledWidth = r5
        L_0x0243:
            r8 = r13
            goto L_0x0225
        L_0x0245:
            float r5 = r11.getYLine()
            float r6 = r11.getDescender()
            float r5 = r5 + r6
            float r5 = java.lang.Math.min(r5, r15)
            float r6 = r11.getYLine()
            float r7 = r11.getDescender()
            float r6 = r6 + r7
            r0.yLine = r6
            r15 = r5
        L_0x025e:
            r5 = r2 & 1
            if (r5 != 0) goto L_0x027e
            if (r4 != 0) goto L_0x0275
            java.util.List r3 = r11.getCompositeElements()
            r4 = 0
            r1.addAll(r4, r3)
            java.util.List r1 = r11.getCompositeElements()
            r1.clear()
            goto L_0x030e
        L_0x0275:
            r4 = 0
            r1.add(r4, r12)
            r11.setText(r3)
            goto L_0x030e
        L_0x027e:
            r11.setText(r3)
        L_0x0281:
            boolean r3 = r12 instanceof com.itextpdf.text.Paragraph
            if (r3 == 0) goto L_0x02d3
            r7 = r12
            com.itextpdf.text.Paragraph r7 = (com.itextpdf.text.Paragraph) r7
            java.util.Iterator r5 = r7.iterator()
        L_0x028c:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x02d3
            java.lang.Object r6 = r5.next()
            com.itextpdf.text.Element r6 = (com.itextpdf.text.Element) r6
            boolean r7 = r6 instanceof com.itextpdf.text.WritableDirectElement
            if (r7 == 0) goto L_0x02d0
            com.itextpdf.text.WritableDirectElement r6 = (com.itextpdf.text.WritableDirectElement) r6
            int r7 = r6.getDirectElementType()
            r10 = 1
            if (r7 != r10) goto L_0x02d0
            if (r4 != 0) goto L_0x02d0
            com.itextpdf.text.pdf.ColumnText r7 = r0.compositeColumn
            com.itextpdf.text.pdf.PdfContentByte r7 = r7.getCanvas()
            com.itextpdf.text.pdf.PdfWriter r7 = r7.getPdfWriter()
            com.itextpdf.text.pdf.ColumnText r10 = r0.compositeColumn
            com.itextpdf.text.pdf.PdfContentByte r10 = r10.getCanvas()
            com.itextpdf.text.pdf.PdfDocument r10 = r10.getPdfDocument()
            float r13 = r10.currentHeight
            float r14 = r10.top()
            float r1 = r0.yLine
            float r14 = r14 - r1
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r10.indentation
            float r1 = r1.indentTop
            float r14 = r14 - r1
            r10.currentHeight = r14
            r6.write(r7, r10)
            r10.currentHeight = r13
        L_0x02d0:
            r1 = r26
            goto L_0x028c
        L_0x02d3:
            if (r16 == 0) goto L_0x0302
            java.util.List r1 = r12.getChunks()
            int r1 = r1.size()
            if (r1 != 0) goto L_0x0302
            if (r3 == 0) goto L_0x02fb
            r7 = r12
            com.itextpdf.text.Paragraph r7 = (com.itextpdf.text.Paragraph) r7
            r1 = 0
            java.lang.Object r3 = r7.get(r1)
            com.itextpdf.text.Element r3 = (com.itextpdf.text.Element) r3
            boolean r5 = r3 instanceof com.itextpdf.text.WritableDirectElement
            if (r5 == 0) goto L_0x02f9
            com.itextpdf.text.WritableDirectElement r3 = (com.itextpdf.text.WritableDirectElement) r3
            int r3 = r3.getDirectElementType()
            r5 = 1
            if (r3 == r5) goto L_0x0306
            goto L_0x0304
        L_0x02f9:
            r5 = 1
            goto L_0x0306
        L_0x02fb:
            r1 = 0
            r5 = 1
            boolean r3 = r12 instanceof com.itextpdf.text.api.Spaceable
            if (r3 == 0) goto L_0x0306
            goto L_0x0304
        L_0x0302:
            r1 = 0
            r5 = 1
        L_0x0304:
            r16 = 0
        L_0x0306:
            r1 = r26
            r10 = r4
            r13 = 0
            goto L_0x0023
        L_0x030c:
            r13 = r8
            r14 = r9
        L_0x030e:
            r1 = 0
            int r3 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r3 == 0) goto L_0x031f
            int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x031f
            float r1 = r0.rightX
            float r3 = r0.leftX
            float r1 = r1 - r3
            r0.filledWidth = r1
            goto L_0x032f
        L_0x031f:
            float r1 = r0.filledWidth
            int r1 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0327
            r0.filledWidth = r9
        L_0x0327:
            float r1 = r0.filledWidth
            int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x032f
            r0.filledWidth = r8
        L_0x032f:
            r0.yLine = r15
            float r1 = r0.leftX
            r0.floatLeftX = r1
            float r1 = r0.rightX
            r0.floatRightX = r1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.FloatLayout.floatingLayout(java.util.List, boolean):int");
    }
}
