package com.itextpdf.text.pdf;

import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.events.PdfPCellEventForwarder;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PdfPCell extends Rectangle implements IAccessibleElement {
    protected HashMap<PdfName, PdfObject> accessibleAttributes;
    private float cachedMaxHeight;
    private float calculatedHeight;
    private PdfPCellEvent cellEvent;
    private int colspan;
    private ColumnText column;
    private float fixedHeight;
    protected ArrayList<PdfPHeaderCell> headers;

    /* renamed from: id */
    protected AccessibleElementId f680id;
    private Image image;
    private float minimumHeight;
    private boolean noWrap;
    private float paddingBottom;
    private float paddingLeft;
    private float paddingRight;
    private float paddingTop;
    protected Phrase phrase;
    protected PdfName role;
    private int rotation;
    private int rowspan;
    private PdfPTable table;
    private boolean useBorderPadding;
    private boolean useDescender;
    private int verticalAlignment;

    public boolean isInline() {
        return false;
    }

    public PdfPCell() {
        super(0.0f, 0.0f, 0.0f, 0.0f);
        this.column = new ColumnText((PdfContentByte) null);
        this.verticalAlignment = 4;
        this.paddingLeft = 2.0f;
        this.paddingRight = 2.0f;
        this.paddingTop = 2.0f;
        this.paddingBottom = 2.0f;
        this.fixedHeight = 0.0f;
        this.calculatedHeight = 0.0f;
        this.noWrap = false;
        this.colspan = 1;
        this.rowspan = 1;
        this.useDescender = false;
        this.useBorderPadding = false;
        this.role = PdfName.f649TD;
        this.accessibleAttributes = null;
        this.f680id = new AccessibleElementId();
        this.headers = null;
        this.borderWidth = 0.5f;
        this.border = 15;
        this.column.setLeading(0.0f, 1.0f);
    }

    public PdfPCell(Phrase phrase2) {
        super(0.0f, 0.0f, 0.0f, 0.0f);
        this.column = new ColumnText((PdfContentByte) null);
        this.verticalAlignment = 4;
        this.paddingLeft = 2.0f;
        this.paddingRight = 2.0f;
        this.paddingTop = 2.0f;
        this.paddingBottom = 2.0f;
        this.fixedHeight = 0.0f;
        this.calculatedHeight = 0.0f;
        this.noWrap = false;
        this.colspan = 1;
        this.rowspan = 1;
        this.useDescender = false;
        this.useBorderPadding = false;
        this.role = PdfName.f649TD;
        this.accessibleAttributes = null;
        this.f680id = new AccessibleElementId();
        this.headers = null;
        this.borderWidth = 0.5f;
        this.border = 15;
        ColumnText columnText = this.column;
        this.phrase = phrase2;
        columnText.addText(phrase2);
        this.column.setLeading(0.0f, 1.0f);
    }

    public PdfPCell(Image image2) {
        this(image2, false);
    }

    public PdfPCell(Image image2, boolean z) {
        super(0.0f, 0.0f, 0.0f, 0.0f);
        this.column = new ColumnText((PdfContentByte) null);
        this.verticalAlignment = 4;
        this.paddingLeft = 2.0f;
        this.paddingRight = 2.0f;
        this.paddingTop = 2.0f;
        this.paddingBottom = 2.0f;
        this.fixedHeight = 0.0f;
        this.calculatedHeight = 0.0f;
        this.noWrap = false;
        this.colspan = 1;
        this.rowspan = 1;
        this.useDescender = false;
        this.useBorderPadding = false;
        this.role = PdfName.f649TD;
        this.accessibleAttributes = null;
        this.f680id = new AccessibleElementId();
        this.headers = null;
        this.borderWidth = 0.5f;
        this.border = 15;
        this.column.setLeading(0.0f, 1.0f);
        if (z) {
            this.image = image2;
            setPadding(this.borderWidth / 2.0f);
            return;
        }
        image2.setScaleToFitLineWhenOverflow(false);
        ColumnText columnText = this.column;
        Phrase phrase2 = new Phrase(new Chunk(image2, 0.0f, 0.0f, true));
        this.phrase = phrase2;
        columnText.addText(phrase2);
        setPadding(0.0f);
    }

    public PdfPCell(PdfPTable pdfPTable) {
        this(pdfPTable, (PdfPCell) null);
    }

    public PdfPCell(PdfPTable pdfPTable, PdfPCell pdfPCell) {
        super(0.0f, 0.0f, 0.0f, 0.0f);
        this.column = new ColumnText((PdfContentByte) null);
        this.verticalAlignment = 4;
        this.paddingLeft = 2.0f;
        this.paddingRight = 2.0f;
        this.paddingTop = 2.0f;
        this.paddingBottom = 2.0f;
        this.fixedHeight = 0.0f;
        this.calculatedHeight = 0.0f;
        this.noWrap = false;
        this.colspan = 1;
        this.rowspan = 1;
        this.useDescender = false;
        this.useBorderPadding = false;
        this.role = PdfName.f649TD;
        this.accessibleAttributes = null;
        this.f680id = new AccessibleElementId();
        this.headers = null;
        this.borderWidth = 0.5f;
        this.border = 15;
        this.column.setLeading(0.0f, 1.0f);
        this.table = pdfPTable;
        pdfPTable.setWidthPercentage(100.0f);
        pdfPTable.setExtendLastRow(true);
        this.column.addElement(pdfPTable);
        if (pdfPCell != null) {
            cloneNonPositionParameters(pdfPCell);
            this.verticalAlignment = pdfPCell.verticalAlignment;
            this.paddingLeft = pdfPCell.paddingLeft;
            this.paddingRight = pdfPCell.paddingRight;
            this.paddingTop = pdfPCell.paddingTop;
            this.paddingBottom = pdfPCell.paddingBottom;
            this.colspan = pdfPCell.colspan;
            this.rowspan = pdfPCell.rowspan;
            this.cellEvent = pdfPCell.cellEvent;
            this.useDescender = pdfPCell.useDescender;
            this.useBorderPadding = pdfPCell.useBorderPadding;
            this.rotation = pdfPCell.rotation;
            return;
        }
        setPadding(0.0f);
    }

    public PdfPCell(PdfPCell pdfPCell) {
        super(pdfPCell.llx, pdfPCell.lly, pdfPCell.urx, pdfPCell.ury);
        this.column = new ColumnText((PdfContentByte) null);
        this.verticalAlignment = 4;
        this.paddingLeft = 2.0f;
        this.paddingRight = 2.0f;
        this.paddingTop = 2.0f;
        this.paddingBottom = 2.0f;
        this.fixedHeight = 0.0f;
        this.calculatedHeight = 0.0f;
        this.noWrap = false;
        this.colspan = 1;
        this.rowspan = 1;
        this.useDescender = false;
        this.useBorderPadding = false;
        this.role = PdfName.f649TD;
        this.accessibleAttributes = null;
        this.f680id = new AccessibleElementId();
        this.headers = null;
        cloneNonPositionParameters(pdfPCell);
        this.verticalAlignment = pdfPCell.verticalAlignment;
        this.paddingLeft = pdfPCell.paddingLeft;
        this.paddingRight = pdfPCell.paddingRight;
        this.paddingTop = pdfPCell.paddingTop;
        this.paddingBottom = pdfPCell.paddingBottom;
        this.phrase = pdfPCell.phrase;
        this.fixedHeight = pdfPCell.fixedHeight;
        this.minimumHeight = pdfPCell.minimumHeight;
        this.noWrap = pdfPCell.noWrap;
        this.colspan = pdfPCell.colspan;
        this.rowspan = pdfPCell.rowspan;
        if (pdfPCell.table != null) {
            this.table = new PdfPTable(pdfPCell.table);
        }
        this.image = Image.getInstance(pdfPCell.image);
        this.cellEvent = pdfPCell.cellEvent;
        this.useDescender = pdfPCell.useDescender;
        this.column = ColumnText.duplicate(pdfPCell.column);
        this.useBorderPadding = pdfPCell.useBorderPadding;
        this.rotation = pdfPCell.rotation;
        this.f680id = pdfPCell.f680id;
        this.role = pdfPCell.role;
        if (pdfPCell.accessibleAttributes != null) {
            this.accessibleAttributes = new HashMap<>(pdfPCell.accessibleAttributes);
        }
        this.headers = pdfPCell.headers;
    }

    public void addElement(Element element) {
        if (this.table != null) {
            this.table = null;
            this.column.setText((Phrase) null);
        }
        if (element instanceof PdfPTable) {
            ((PdfPTable) element).setSplitLate(false);
        } else if (element instanceof PdfDiv) {
            Iterator<Element> it = ((PdfDiv) element).getContent().iterator();
            while (it.hasNext()) {
                Element next = it.next();
                if (next instanceof PdfPTable) {
                    ((PdfPTable) next).setSplitLate(false);
                }
            }
        }
        this.column.addElement(element);
    }

    public Phrase getPhrase() {
        return this.phrase;
    }

    public void setPhrase(Phrase phrase2) {
        this.table = null;
        this.image = null;
        ColumnText columnText = this.column;
        this.phrase = phrase2;
        columnText.setText(phrase2);
    }

    public int getHorizontalAlignment() {
        return this.column.getAlignment();
    }

    public void setHorizontalAlignment(int i) {
        this.column.setAlignment(i);
    }

    public int getVerticalAlignment() {
        return this.verticalAlignment;
    }

    public void setVerticalAlignment(int i) {
        PdfPTable pdfPTable = this.table;
        if (pdfPTable != null) {
            pdfPTable.setExtendLastRow(i == 4);
        }
        this.verticalAlignment = i;
    }

    public float getEffectivePaddingLeft() {
        if (!isUseBorderPadding()) {
            return this.paddingLeft;
        }
        return this.paddingLeft + (getBorderWidthLeft() / (isUseVariableBorders() ? 1.0f : 2.0f));
    }

    public float getPaddingLeft() {
        return this.paddingLeft;
    }

    public void setPaddingLeft(float f) {
        this.paddingLeft = f;
    }

    public float getEffectivePaddingRight() {
        if (!isUseBorderPadding()) {
            return this.paddingRight;
        }
        return this.paddingRight + (getBorderWidthRight() / (isUseVariableBorders() ? 1.0f : 2.0f));
    }

    public float getPaddingRight() {
        return this.paddingRight;
    }

    public void setPaddingRight(float f) {
        this.paddingRight = f;
    }

    public float getEffectivePaddingTop() {
        if (!isUseBorderPadding()) {
            return this.paddingTop;
        }
        return this.paddingTop + (getBorderWidthTop() / (isUseVariableBorders() ? 1.0f : 2.0f));
    }

    public float getPaddingTop() {
        return this.paddingTop;
    }

    public void setPaddingTop(float f) {
        this.paddingTop = f;
    }

    public float getEffectivePaddingBottom() {
        if (!isUseBorderPadding()) {
            return this.paddingBottom;
        }
        return this.paddingBottom + (getBorderWidthBottom() / (isUseVariableBorders() ? 1.0f : 2.0f));
    }

    public float getPaddingBottom() {
        return this.paddingBottom;
    }

    public void setPaddingBottom(float f) {
        this.paddingBottom = f;
    }

    public void setPadding(float f) {
        this.paddingBottom = f;
        this.paddingTop = f;
        this.paddingLeft = f;
        this.paddingRight = f;
    }

    public boolean isUseBorderPadding() {
        return this.useBorderPadding;
    }

    public void setUseBorderPadding(boolean z) {
        this.useBorderPadding = z;
    }

    public void setLeading(float f, float f2) {
        this.column.setLeading(f, f2);
    }

    public float getLeading() {
        return this.column.getLeading();
    }

    public float getMultipliedLeading() {
        return this.column.getMultipliedLeading();
    }

    public void setIndent(float f) {
        this.column.setIndent(f);
    }

    public float getIndent() {
        return this.column.getIndent();
    }

    public float getExtraParagraphSpace() {
        return this.column.getExtraParagraphSpace();
    }

    public void setExtraParagraphSpace(float f) {
        this.column.setExtraParagraphSpace(f);
    }

    public void setCalculatedHeight(float f) {
        this.calculatedHeight = f;
    }

    public float getCalculatedHeight() {
        return this.calculatedHeight;
    }

    public boolean hasCalculatedHeight() {
        return getCalculatedHeight() > 0.0f;
    }

    public void setFixedHeight(float f) {
        this.fixedHeight = f;
        this.minimumHeight = 0.0f;
    }

    public float getFixedHeight() {
        return this.fixedHeight;
    }

    public boolean hasFixedHeight() {
        return getFixedHeight() > 0.0f;
    }

    public float getCachedMaxHeight() {
        return this.cachedMaxHeight;
    }

    public boolean hasCachedMaxHeight() {
        return this.cachedMaxHeight > 0.0f;
    }

    public void setMinimumHeight(float f) {
        this.minimumHeight = f;
        this.fixedHeight = 0.0f;
    }

    public float getMinimumHeight() {
        return this.minimumHeight;
    }

    public boolean hasMinimumHeight() {
        return getMinimumHeight() > 0.0f;
    }

    public boolean isNoWrap() {
        return this.noWrap;
    }

    public void setNoWrap(boolean z) {
        this.noWrap = z;
    }

    public PdfPTable getTable() {
        return this.table;
    }

    /* access modifiers changed from: package-private */
    public void setTable(PdfPTable pdfPTable) {
        this.table = pdfPTable;
        this.column.setText((Phrase) null);
        this.image = null;
        if (pdfPTable != null) {
            pdfPTable.setExtendLastRow(this.verticalAlignment == 4);
            this.column.addElement(pdfPTable);
            pdfPTable.setWidthPercentage(100.0f);
        }
    }

    public int getColspan() {
        return this.colspan;
    }

    public void setColspan(int i) {
        this.colspan = i;
    }

    public int getRowspan() {
        return this.rowspan;
    }

    public void setRowspan(int i) {
        this.rowspan = i;
    }

    public void setFollowingIndent(float f) {
        this.column.setFollowingIndent(f);
    }

    public float getFollowingIndent() {
        return this.column.getFollowingIndent();
    }

    public void setRightIndent(float f) {
        this.column.setRightIndent(f);
    }

    public float getRightIndent() {
        return this.column.getRightIndent();
    }

    public float getSpaceCharRatio() {
        return this.column.getSpaceCharRatio();
    }

    public void setSpaceCharRatio(float f) {
        this.column.setSpaceCharRatio(f);
    }

    public void setRunDirection(int i) {
        this.column.setRunDirection(i);
    }

    public int getRunDirection() {
        return this.column.getRunDirection();
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image2) {
        this.column.setText((Phrase) null);
        this.table = null;
        this.image = image2;
    }

    public PdfPCellEvent getCellEvent() {
        return this.cellEvent;
    }

    public void setCellEvent(PdfPCellEvent pdfPCellEvent) {
        if (pdfPCellEvent == null) {
            this.cellEvent = null;
            return;
        }
        PdfPCellEvent pdfPCellEvent2 = this.cellEvent;
        if (pdfPCellEvent2 == null) {
            this.cellEvent = pdfPCellEvent;
        } else if (pdfPCellEvent2 instanceof PdfPCellEventForwarder) {
            ((PdfPCellEventForwarder) pdfPCellEvent2).addCellEvent(pdfPCellEvent);
        } else {
            PdfPCellEventForwarder pdfPCellEventForwarder = new PdfPCellEventForwarder();
            pdfPCellEventForwarder.addCellEvent(this.cellEvent);
            pdfPCellEventForwarder.addCellEvent(pdfPCellEvent);
            this.cellEvent = pdfPCellEventForwarder;
        }
    }

    public int getArabicOptions() {
        return this.column.getArabicOptions();
    }

    public void setArabicOptions(int i) {
        this.column.setArabicOptions(i);
    }

    public boolean isUseAscender() {
        return this.column.isUseAscender();
    }

    public void setUseAscender(boolean z) {
        this.column.setUseAscender(z);
    }

    public boolean isUseDescender() {
        return this.useDescender;
    }

    public void setUseDescender(boolean z) {
        this.useDescender = z;
    }

    public ColumnText getColumn() {
        return this.column;
    }

    public List<Element> getCompositeElements() {
        return getColumn().compositeElements;
    }

    public void setColumn(ColumnText columnText) {
        this.column = columnText;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void setRotation(int i) {
        int i2 = i % 360;
        if (i2 < 0) {
            i2 += 360;
        }
        if (i2 % 90 == 0) {
            this.rotation = i2;
            return;
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("rotation.must.be.a.multiple.of.90", new Object[0]));
    }

    public float getMaxHeight() {
        float f;
        float f2;
        float f3;
        boolean z = getRotation() == 90 || getRotation() == 270;
        Image image2 = getImage();
        float f4 = 0.0f;
        if (image2 != null) {
            image2.scalePercent(100.0f);
            image2.scalePercent(((((getRight() - getEffectivePaddingRight()) - getEffectivePaddingLeft()) - getLeft()) / (z ? image2.getScaledHeight() : image2.getScaledWidth())) * 100.0f);
            setBottom(((getTop() - getEffectivePaddingTop()) - getEffectivePaddingBottom()) - (z ? image2.getScaledWidth() : image2.getScaledHeight()));
        } else if ((!z || !hasFixedHeight()) && getColumn() != null) {
            ColumnText duplicate = ColumnText.duplicate(getColumn());
            float f5 = 20000.0f;
            if (z) {
                f3 = getRight() - getEffectivePaddingRight();
                f = getLeft() + getEffectivePaddingLeft();
                f2 = 0.0f;
            } else {
                if (!isNoWrap()) {
                    f5 = getRight() - getEffectivePaddingRight();
                }
                f3 = getTop() - getEffectivePaddingTop();
                f2 = getLeft() + getEffectivePaddingLeft();
                f = hasCalculatedHeight() ? (getTop() + getEffectivePaddingBottom()) - getCalculatedHeight() : -1.07374182E9f;
            }
            PdfPRow.setColumn(duplicate, f2, f, f5, f3);
            try {
                duplicate.mo20966go(true);
                if (z) {
                    setBottom(((getTop() - getEffectivePaddingTop()) - getEffectivePaddingBottom()) - duplicate.getFilledWidth());
                } else {
                    float yLine = duplicate.getYLine();
                    if (isUseDescender()) {
                        yLine += duplicate.getDescender();
                    }
                    setBottom(yLine - getEffectivePaddingBottom());
                }
            } catch (DocumentException e) {
                throw new ExceptionConverter(e);
            }
        } else {
            setBottom(getTop() - getFixedHeight());
        }
        float height = getHeight();
        if (height != getEffectivePaddingTop() + getEffectivePaddingBottom()) {
            f4 = height;
        }
        if (hasFixedHeight()) {
            f4 = getFixedHeight();
        } else if (hasMinimumHeight() && f4 < getMinimumHeight()) {
            f4 = getMinimumHeight();
        }
        this.cachedMaxHeight = f4;
        return f4;
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
        return this.f680id;
    }

    public void setId(AccessibleElementId accessibleElementId) {
        this.f680id = accessibleElementId;
    }

    public void addHeader(PdfPHeaderCell pdfPHeaderCell) {
        if (this.headers == null) {
            this.headers = new ArrayList<>();
        }
        this.headers.add(pdfPHeaderCell);
    }

    public ArrayList<PdfPHeaderCell> getHeaders() {
        return this.headers;
    }
}
