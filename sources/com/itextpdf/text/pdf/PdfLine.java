package com.itextpdf.text.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.TabStop;
import java.util.ArrayList;
import java.util.Iterator;

public class PdfLine {
    protected int alignment;
    protected float height;
    protected boolean isRTL = false;
    protected float left;
    protected ArrayList<PdfChunk> line;
    protected ListItem listItem = null;
    protected boolean newlineSplit = false;
    protected float originalWidth;
    protected float tabPosition = Float.NaN;
    protected TabStop tabStop = null;
    protected float tabStopAnchorPosition = Float.NaN;
    protected float width;

    PdfLine(float f, float f2, int i, float f3) {
        this.left = f;
        float f4 = f2 - f;
        this.width = f4;
        this.originalWidth = f4;
        this.alignment = i;
        this.height = f3;
        this.line = new ArrayList<>();
    }

    PdfLine(float f, float f2, float f3, int i, boolean z, ArrayList<PdfChunk> arrayList, boolean z2) {
        this.left = f;
        this.originalWidth = f2;
        this.width = f3;
        this.alignment = i;
        this.line = arrayList;
        this.newlineSplit = z;
        this.isRTL = z2;
    }

    /* access modifiers changed from: package-private */
    public PdfChunk add(PdfChunk pdfChunk, float f) {
        if (pdfChunk != null && !pdfChunk.toString().equals("") && !pdfChunk.toString().equals(" ") && (this.height < f || this.line.isEmpty())) {
            this.height = f;
        }
        return add(pdfChunk);
    }

    /* access modifiers changed from: package-private */
    public PdfChunk add(PdfChunk pdfChunk) {
        if (pdfChunk == null || pdfChunk.toString().equals("")) {
            return null;
        }
        PdfChunk split = pdfChunk.split(this.width);
        this.newlineSplit = pdfChunk.isNewlineSplit() || split == null;
        if (pdfChunk.isTab()) {
            Object[] objArr = (Object[]) pdfChunk.getAttribute(Chunk.TAB);
            if (pdfChunk.isAttribute(Chunk.TABSETTINGS)) {
                boolean booleanValue = ((Boolean) objArr[1]).booleanValue();
                if (booleanValue && this.line.isEmpty()) {
                    return null;
                }
                flush();
                this.tabStopAnchorPosition = Float.NaN;
                TabStop tabStop2 = PdfChunk.getTabStop(pdfChunk, this.originalWidth - this.width);
                this.tabStop = tabStop2;
                float position = tabStop2.getPosition();
                float f = this.originalWidth;
                if (position > f) {
                    if (!booleanValue) {
                        if (((double) Math.abs(f - this.width)) < 0.001d) {
                            addToLine(pdfChunk);
                        }
                        this.width = 0.0f;
                        return pdfChunk;
                    }
                    pdfChunk = null;
                    this.width = 0.0f;
                    return pdfChunk;
                }
                pdfChunk.setTabStop(this.tabStop);
                if (this.isRTL || this.tabStop.getAlignment() != TabStop.Alignment.LEFT) {
                    this.tabPosition = this.originalWidth - this.width;
                } else {
                    this.width = this.originalWidth - this.tabStop.getPosition();
                    this.tabStop = null;
                    this.tabPosition = Float.NaN;
                }
                addToLine(pdfChunk);
                return split;
            }
            Float valueOf = Float.valueOf(((Float) objArr[1]).floatValue());
            if (((Boolean) objArr[2]).booleanValue() && valueOf.floatValue() < this.originalWidth - this.width) {
                return pdfChunk;
            }
            pdfChunk.adjustLeft(this.left);
            this.width = this.originalWidth - valueOf.floatValue();
            addToLine(pdfChunk);
            return split;
        } else if (pdfChunk.length() > 0 || pdfChunk.isImage()) {
            if (split != null) {
                pdfChunk.trimLastSpace();
            }
            this.width -= pdfChunk.width();
            addToLine(pdfChunk);
            return split;
        } else if (this.line.size() < 1) {
            PdfChunk truncate = split.truncate(this.width);
            this.width -= split.width();
            if (split.length() > 0) {
                addToLine(split);
                return truncate;
            }
            if (truncate != null) {
                addToLine(truncate);
            }
            return null;
        } else {
            float f2 = this.width;
            ArrayList<PdfChunk> arrayList = this.line;
            this.width = f2 + arrayList.get(arrayList.size() - 1).trimLastSpace();
            return split;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        r0 = r4.toString();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void addToLine(com.itextpdf.text.pdf.PdfChunk r4) {
        /*
            r3 = this;
            boolean r0 = r4.changeLeading
            if (r0 == 0) goto L_0x002e
            boolean r0 = r4.isImage()
            if (r0 == 0) goto L_0x0022
            com.itextpdf.text.Image r0 = r4.getImage()
            float r1 = r4.getImageHeight()
            float r2 = r4.getImageOffsetY()
            float r1 = r1 + r2
            float r2 = r0.getBorderWidthTop()
            float r1 = r1 + r2
            float r0 = r0.getSpacingBefore()
            float r1 = r1 + r0
            goto L_0x0026
        L_0x0022:
            float r1 = r4.getLeading()
        L_0x0026:
            float r0 = r3.height
            int r0 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x002e
            r3.height = r1
        L_0x002e:
            com.itextpdf.text.TabStop r0 = r3.tabStop
            if (r0 == 0) goto L_0x0067
            com.itextpdf.text.TabStop$Alignment r0 = r0.getAlignment()
            com.itextpdf.text.TabStop$Alignment r1 = com.itextpdf.text.TabStop.Alignment.ANCHOR
            if (r0 != r1) goto L_0x0067
            float r0 = r3.tabStopAnchorPosition
            boolean r0 = java.lang.Float.isNaN(r0)
            if (r0 == 0) goto L_0x0067
            java.lang.String r0 = r4.toString()
            com.itextpdf.text.TabStop r1 = r3.tabStop
            char r1 = r1.getAnchorChar()
            int r1 = r0.indexOf(r1)
            r2 = -1
            if (r1 == r2) goto L_0x0067
            int r2 = r0.length()
            java.lang.String r0 = r0.substring(r1, r2)
            float r0 = r4.width(r0)
            float r1 = r3.originalWidth
            float r2 = r3.width
            float r1 = r1 - r2
            float r1 = r1 - r0
            r3.tabStopAnchorPosition = r1
        L_0x0067:
            java.util.ArrayList<com.itextpdf.text.pdf.PdfChunk> r0 = r3.line
            r0.add(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfLine.addToLine(com.itextpdf.text.pdf.PdfChunk):void");
    }

    public int size() {
        return this.line.size();
    }

    public Iterator<PdfChunk> iterator() {
        return this.line.iterator();
    }

    /* access modifiers changed from: package-private */
    public float height() {
        return this.height;
    }

    /* access modifiers changed from: package-private */
    public float indentLeft() {
        if (this.isRTL) {
            int i = this.alignment;
            if (i == 1) {
                return this.left + (this.width / 2.0f);
            }
            if (i == 2) {
                return this.left;
            }
            if (i != 3) {
                return this.left + this.width;
            }
            return this.left + (hasToBeJustified() ? 0.0f : this.width);
        }
        if (getSeparatorCount() <= 0) {
            int i2 = this.alignment;
            if (i2 == 1) {
                return this.left + (this.width / 2.0f);
            }
            if (i2 == 2) {
                return this.left + this.width;
            }
        }
        return this.left;
    }

    public boolean hasToBeJustified() {
        int i = this.alignment;
        return ((i == 3 && !this.newlineSplit) || i == 8) && this.width != 0.0f;
    }

    public void resetAlignment() {
        if (this.alignment == 3) {
            this.alignment = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void setExtraIndent(float f) {
        this.left += f;
        this.width -= f;
        this.originalWidth -= f;
    }

    /* access modifiers changed from: package-private */
    public float widthLeft() {
        return this.width;
    }

    /* access modifiers changed from: package-private */
    public int numberOfSpaces() {
        Iterator<PdfChunk> it = this.line.iterator();
        int i = 0;
        while (it.hasNext()) {
            String pdfChunk = it.next().toString();
            int length = pdfChunk.length();
            for (int i2 = 0; i2 < length; i2++) {
                if (pdfChunk.charAt(i2) == ' ') {
                    i++;
                }
            }
        }
        return i;
    }

    public void setListItem(ListItem listItem2) {
        this.listItem = listItem2;
    }

    public Chunk listSymbol() {
        ListItem listItem2 = this.listItem;
        if (listItem2 != null) {
            return listItem2.getListSymbol();
        }
        return null;
    }

    public float listIndent() {
        ListItem listItem2 = this.listItem;
        if (listItem2 != null) {
            return listItem2.getIndentationLeft();
        }
        return 0.0f;
    }

    public ListItem listItem() {
        return this.listItem;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<PdfChunk> it = this.line.iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next().toString());
        }
        return stringBuffer.toString();
    }

    public int getLineLengthUtf32() {
        Iterator<PdfChunk> it = this.line.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().lengthUtf32();
        }
        return i;
    }

    public boolean isNewlineSplit() {
        return this.newlineSplit && this.alignment != 8;
    }

    public int getLastStrokeChunk() {
        int size = this.line.size() - 1;
        while (size >= 0 && !this.line.get(size).isStroked()) {
            size--;
        }
        return size;
    }

    public PdfChunk getChunk(int i) {
        if (i < 0 || i >= this.line.size()) {
            return null;
        }
        return this.line.get(i);
    }

    public float getOriginalWidth() {
        return this.originalWidth;
    }

    /* access modifiers changed from: package-private */
    public float[] getMaxSize(float f, float f2) {
        float f3 = -10000.0f;
        float f4 = 0.0f;
        for (int i = 0; i < this.line.size(); i++) {
            PdfChunk pdfChunk = this.line.get(i);
            if (pdfChunk.isImage()) {
                Image image = pdfChunk.getImage();
                if (pdfChunk.changeLeading()) {
                    f3 = Math.max(pdfChunk.getImageHeight() + pdfChunk.getImageOffsetY() + image.getSpacingBefore(), f3);
                }
            } else if (pdfChunk.changeLeading()) {
                f4 = Math.max(pdfChunk.getLeading(), f4);
            } else {
                f4 = Math.max((pdfChunk.font().size() * f2) + f, f4);
            }
        }
        float[] fArr = new float[2];
        if (f4 > 0.0f) {
            f = f4;
        }
        fArr[0] = f;
        fArr[1] = f3;
        return fArr;
    }

    /* access modifiers changed from: package-private */
    public boolean isRTL() {
        return this.isRTL;
    }

    /* access modifiers changed from: package-private */
    public int getSeparatorCount() {
        Iterator<PdfChunk> it = this.line.iterator();
        int i = 0;
        while (it.hasNext()) {
            PdfChunk next = it.next();
            if (next.isTab()) {
                if (!next.isAttribute(Chunk.TABSETTINGS)) {
                    return -1;
                }
            } else if (next.isHorizontalSeparator()) {
                i++;
            }
        }
        return i;
    }

    public float getWidthCorrected(float f, float f2) {
        float f3 = 0.0f;
        for (int i = 0; i < this.line.size(); i++) {
            f3 += this.line.get(i).getWidthCorrected(f, f2);
        }
        return f3;
    }

    public float getAscender() {
        float f = 0.0f;
        for (int i = 0; i < this.line.size(); i++) {
            PdfChunk pdfChunk = this.line.get(i);
            if (pdfChunk.isImage()) {
                f = Math.max(f, pdfChunk.getImageHeight() + pdfChunk.getImageOffsetY());
            } else {
                PdfFont font = pdfChunk.font();
                float textRise = pdfChunk.getTextRise();
                if (textRise <= 0.0f) {
                    textRise = 0.0f;
                }
                f = Math.max(f, textRise + font.getFont().getFontDescriptor(1, font.size()));
            }
        }
        return f;
    }

    public float getDescender() {
        float f = 0.0f;
        for (int i = 0; i < this.line.size(); i++) {
            PdfChunk pdfChunk = this.line.get(i);
            if (pdfChunk.isImage()) {
                f = Math.min(f, pdfChunk.getImageOffsetY());
            } else {
                PdfFont font = pdfChunk.font();
                float textRise = pdfChunk.getTextRise();
                if (textRise >= 0.0f) {
                    textRise = 0.0f;
                }
                f = Math.min(f, textRise + font.getFont().getFontDescriptor(3, font.size()));
            }
        }
        return f;
    }

    public void flush() {
        TabStop tabStop2 = this.tabStop;
        if (tabStop2 != null) {
            float f = this.originalWidth;
            float f2 = this.width;
            float f3 = this.tabPosition;
            float position = tabStop2.getPosition(f3, f - f2, this.tabStopAnchorPosition);
            float f4 = this.originalWidth;
            float f5 = (f4 - position) - ((f - f2) - f3);
            this.width = f5;
            if (f5 < 0.0f) {
                position += f5;
            }
            if (!this.isRTL) {
                this.tabStop.setPosition(position);
            } else {
                this.tabStop.setPosition((f4 - f5) - this.tabPosition);
            }
            this.tabStop = null;
            this.tabPosition = Float.NaN;
        }
    }
}
