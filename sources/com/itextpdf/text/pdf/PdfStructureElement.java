package com.itextpdf.text.pdf;

import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListBody;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.ListLabel;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import com.itextpdf.text.pdf.interfaces.IPdfStructureElement;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;

public class PdfStructureElement extends PdfDictionary implements IPdfStructureElement {
    private AccessibleElementId elementId;
    private transient PdfStructureElement parent;
    private PdfIndirectReference reference;
    private PdfName structureType;

    /* renamed from: top  reason: collision with root package name */
    private transient PdfStructureTreeRoot f2171top;

    private void writeAttributes(Document document) {
    }

    private void writeAttributes(ListBody listBody) {
    }

    private void writeAttributes(PdfPTableBody pdfPTableBody) {
    }

    private void writeAttributes(PdfPTableFooter pdfPTableFooter) {
    }

    public PdfStructureElement(PdfStructureElement pdfStructureElement, PdfName pdfName) {
        this.f2171top = pdfStructureElement.f2171top;
        init(pdfStructureElement, pdfName);
        this.parent = pdfStructureElement;
        put(PdfName.f623P, pdfStructureElement.reference);
        put(PdfName.TYPE, PdfName.STRUCTELEM);
    }

    public PdfStructureElement(PdfStructureTreeRoot pdfStructureTreeRoot, PdfName pdfName) {
        this.f2171top = pdfStructureTreeRoot;
        init(pdfStructureTreeRoot, pdfName);
        put(PdfName.f623P, pdfStructureTreeRoot.getReference());
        put(PdfName.TYPE, PdfName.STRUCTELEM);
    }

    protected PdfStructureElement(PdfDictionary pdfDictionary, PdfName pdfName, AccessibleElementId accessibleElementId) {
        this.elementId = accessibleElementId;
        if (pdfDictionary instanceof PdfStructureElement) {
            PdfStructureElement pdfStructureElement = (PdfStructureElement) pdfDictionary;
            this.f2171top = pdfStructureElement.f2171top;
            init(pdfDictionary, pdfName);
            this.parent = pdfStructureElement;
            put(PdfName.f623P, pdfStructureElement.reference);
            put(PdfName.TYPE, PdfName.STRUCTELEM);
        } else if (pdfDictionary instanceof PdfStructureTreeRoot) {
            PdfStructureTreeRoot pdfStructureTreeRoot = (PdfStructureTreeRoot) pdfDictionary;
            this.f2171top = pdfStructureTreeRoot;
            init(pdfDictionary, pdfName);
            put(PdfName.f623P, pdfStructureTreeRoot.getReference());
            put(PdfName.TYPE, PdfName.STRUCTELEM);
        }
    }

    public PdfName getStructureType() {
        return this.structureType;
    }

    private void init(PdfDictionary pdfDictionary, PdfName pdfName) {
        PdfArray pdfArray;
        PdfDictionary asDict;
        if (!this.f2171top.getWriter().getStandardStructElems().contains(pdfName)) {
            PdfDictionary asDict2 = this.f2171top.getAsDict(PdfName.ROLEMAP);
            if (asDict2 == null || !asDict2.contains(pdfName)) {
                throw new ExceptionConverter(new DocumentException(MessageLocalization.getComposedMessage("unknown.structure.element.role.1", pdfName.toString())));
            }
            this.structureType = asDict2.getAsName(pdfName);
        } else {
            this.structureType = pdfName;
        }
        PdfObject pdfObject = pdfDictionary.get(PdfName.f605K);
        if (pdfObject == null) {
            pdfArray = new PdfArray();
            pdfDictionary.put(PdfName.f605K, pdfArray);
        } else if (pdfObject instanceof PdfArray) {
            pdfArray = (PdfArray) pdfObject;
        } else {
            PdfArray pdfArray2 = new PdfArray();
            pdfArray2.add(pdfObject);
            pdfDictionary.put(PdfName.f605K, pdfArray2);
            pdfArray = pdfArray2;
        }
        if (pdfArray.size() > 0) {
            if (pdfArray.getAsNumber(0) != null) {
                pdfArray.remove(0);
            }
            if (pdfArray.size() > 0 && (asDict = pdfArray.getAsDict(0)) != null && PdfName.MCR.equals(asDict.getAsName(PdfName.TYPE))) {
                pdfArray.remove(0);
            }
        }
        put(PdfName.f642S, pdfName);
        PdfIndirectReference pdfIndirectReference = this.f2171top.getWriter().getPdfIndirectReference();
        this.reference = pdfIndirectReference;
        pdfArray.add((PdfObject) pdfIndirectReference);
    }

    public PdfDictionary getParent() {
        return getParent(false);
    }

    public PdfDictionary getParent(boolean z) {
        PdfStructureElement pdfStructureElement = this.parent;
        return (pdfStructureElement != null || !z) ? pdfStructureElement : this.f2171top;
    }

    /* access modifiers changed from: package-private */
    public void setPageMark(int i, int i2) {
        if (i2 >= 0) {
            put(PdfName.f605K, new PdfNumber(i2));
        }
        this.f2171top.setPageMark(i, this.reference);
    }

    /* access modifiers changed from: package-private */
    public void setAnnotation(PdfAnnotation pdfAnnotation, PdfIndirectReference pdfIndirectReference) {
        PdfArray asArray = getAsArray(PdfName.f605K);
        if (asArray == null) {
            asArray = new PdfArray();
            PdfObject pdfObject = get(PdfName.f605K);
            if (pdfObject != null) {
                asArray.add(pdfObject);
            }
            put(PdfName.f605K, asArray);
        }
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.TYPE, PdfName.OBJR);
        pdfDictionary.put(PdfName.OBJ, pdfAnnotation.getIndirectReference());
        if (pdfAnnotation.getRole() == PdfName.FORM) {
            pdfDictionary.put(PdfName.f626PG, pdfIndirectReference);
        }
        asArray.add((PdfObject) pdfDictionary);
    }

    public PdfIndirectReference getReference() {
        return this.reference;
    }

    public PdfObject getAttribute(PdfName pdfName) {
        PdfDictionary asDict = getAsDict(PdfName.f545A);
        if (asDict != null && asDict.contains(pdfName)) {
            return asDict.get(pdfName);
        }
        PdfDictionary parent2 = getParent();
        if (parent2 instanceof PdfStructureElement) {
            return ((PdfStructureElement) parent2).getAttribute(pdfName);
        }
        if (parent2 instanceof PdfStructureTreeRoot) {
            return ((PdfStructureTreeRoot) parent2).getAttribute(pdfName);
        }
        return new PdfNull();
    }

    public void setAttribute(PdfName pdfName, PdfObject pdfObject) {
        PdfDictionary asDict = getAsDict(PdfName.f545A);
        if (asDict == null) {
            asDict = new PdfDictionary();
            put(PdfName.f545A, asDict);
        }
        asDict.put(pdfName, pdfObject);
    }

    public void writeAttributes(IAccessibleElement iAccessibleElement) {
        if (iAccessibleElement instanceof ListItem) {
            writeAttributes((ListItem) iAccessibleElement);
        } else if (iAccessibleElement instanceof Paragraph) {
            writeAttributes((Paragraph) iAccessibleElement);
        } else if (iAccessibleElement instanceof Chunk) {
            writeAttributes((Chunk) iAccessibleElement);
        } else if (iAccessibleElement instanceof Image) {
            writeAttributes((Image) iAccessibleElement);
        } else if (iAccessibleElement instanceof List) {
            writeAttributes((List) iAccessibleElement);
        } else if (iAccessibleElement instanceof ListLabel) {
            writeAttributes((ListLabel) iAccessibleElement);
        } else if (iAccessibleElement instanceof ListBody) {
            writeAttributes((ListBody) iAccessibleElement);
        } else if (iAccessibleElement instanceof PdfPTable) {
            writeAttributes((PdfPTable) iAccessibleElement);
        } else if (iAccessibleElement instanceof PdfPRow) {
            writeAttributes((PdfPRow) iAccessibleElement);
        } else if (iAccessibleElement instanceof PdfPHeaderCell) {
            writeAttributes((PdfPHeaderCell) iAccessibleElement);
        } else if (iAccessibleElement instanceof PdfPCell) {
            writeAttributes((PdfPCell) iAccessibleElement);
        } else if (iAccessibleElement instanceof PdfPTableHeader) {
            writeAttributes((PdfPTableHeader) iAccessibleElement);
        } else if (iAccessibleElement instanceof PdfPTableFooter) {
            writeAttributes((PdfPTableFooter) iAccessibleElement);
        } else if (iAccessibleElement instanceof PdfPTableBody) {
            writeAttributes((PdfPTableBody) iAccessibleElement);
        } else if (iAccessibleElement instanceof PdfDiv) {
            writeAttributes((PdfDiv) iAccessibleElement);
        } else if (iAccessibleElement instanceof PdfTemplate) {
            writeAttributes((PdfTemplate) iAccessibleElement);
        } else if (iAccessibleElement instanceof Document) {
            writeAttributes((Document) iAccessibleElement);
        }
        if (iAccessibleElement.getAccessibleAttributes() != null) {
            for (PdfName next : iAccessibleElement.getAccessibleAttributes().keySet()) {
                if (next.equals(PdfName.f600ID)) {
                    PdfObject accessibleAttribute = iAccessibleElement.getAccessibleAttribute(next);
                    put(next, accessibleAttribute);
                    this.f2171top.putIDTree(accessibleAttribute.toString(), getReference());
                } else if (next.equals(PdfName.LANG) || next.equals(PdfName.ALT) || next.equals(PdfName.ACTUALTEXT) || next.equals(PdfName.f578E) || next.equals(PdfName.f647T)) {
                    put(next, iAccessibleElement.getAccessibleAttribute(next));
                } else {
                    setAttribute(next, iAccessibleElement.getAccessibleAttribute(next));
                }
            }
        }
    }

    private void writeAttributes(Chunk chunk) {
        if (chunk == null) {
            return;
        }
        if (chunk.getImage() != null) {
            writeAttributes(chunk.getImage());
            return;
        }
        HashMap<String, Object> attributes = chunk.getAttributes();
        if (attributes != null) {
            setAttribute(PdfName.f617O, PdfName.LAYOUT);
            if (attributes.containsKey(Chunk.UNDERLINE)) {
                setAttribute(PdfName.TEXTDECORATIONTYPE, PdfName.UNDERLINE);
            }
            if (attributes.containsKey(Chunk.BACKGROUND)) {
                BaseColor baseColor = (BaseColor) ((Object[]) attributes.get(Chunk.BACKGROUND))[0];
                setAttribute(PdfName.BACKGROUNDCOLOR, new PdfArray(new float[]{((float) baseColor.getRed()) / 255.0f, ((float) baseColor.getGreen()) / 255.0f, ((float) baseColor.getBlue()) / 255.0f}));
            }
            IPdfStructureElement iPdfStructureElement = (IPdfStructureElement) getParent(true);
            PdfObject parentAttribute = getParentAttribute(iPdfStructureElement, PdfName.COLOR);
            if (!(chunk.getFont() == null || chunk.getFont().getColor() == null)) {
                setColorAttribute(chunk.getFont().getColor(), parentAttribute, PdfName.COLOR);
            }
            PdfObject parentAttribute2 = getParentAttribute(iPdfStructureElement, PdfName.TEXTDECORATIONTHICKNESS);
            PdfObject parentAttribute3 = getParentAttribute(iPdfStructureElement, PdfName.TEXTDECORATIONCOLOR);
            if (attributes.containsKey(Chunk.UNDERLINE)) {
                Object[][] objArr = (Object[][]) attributes.get(Chunk.UNDERLINE);
                Object[] objArr2 = objArr[objArr.length - 1];
                BaseColor baseColor2 = (BaseColor) objArr2[0];
                float f = ((float[]) objArr2[1])[0];
                if (!(parentAttribute2 instanceof PdfNumber)) {
                    setAttribute(PdfName.TEXTDECORATIONTHICKNESS, new PdfNumber(f));
                } else if (Float.compare(f, ((PdfNumber) parentAttribute2).floatValue()) != 0) {
                    setAttribute(PdfName.TEXTDECORATIONTHICKNESS, new PdfNumber(f));
                }
                if (baseColor2 != null) {
                    setColorAttribute(baseColor2, parentAttribute3, PdfName.TEXTDECORATIONCOLOR);
                }
            }
            if (attributes.containsKey(Chunk.LINEHEIGHT)) {
                float floatValue = ((Float) attributes.get(Chunk.LINEHEIGHT)).floatValue();
                PdfObject parentAttribute4 = getParentAttribute(iPdfStructureElement, PdfName.LINEHEIGHT);
                if (!(parentAttribute4 instanceof PdfNumber)) {
                    setAttribute(PdfName.LINEHEIGHT, new PdfNumber(floatValue));
                } else if (Float.compare(((PdfNumber) parentAttribute4).floatValue(), floatValue) != 0) {
                    setAttribute(PdfName.LINEHEIGHT, new PdfNumber(floatValue));
                }
            }
        }
    }

    private void writeAttributes(Image image) {
        if (image != null) {
            setAttribute(PdfName.f617O, PdfName.LAYOUT);
            if (image.getWidth() > 0.0f) {
                setAttribute(PdfName.WIDTH, new PdfNumber(image.getWidth()));
            }
            if (image.getHeight() > 0.0f) {
                setAttribute(PdfName.HEIGHT, new PdfNumber(image.getHeight()));
            }
            setAttribute(PdfName.BBOX, new PdfRectangle((Rectangle) image, image.getRotation()));
            if (image.getBackgroundColor() != null) {
                BaseColor backgroundColor = image.getBackgroundColor();
                setAttribute(PdfName.BACKGROUNDCOLOR, new PdfArray(new float[]{((float) backgroundColor.getRed()) / 255.0f, ((float) backgroundColor.getGreen()) / 255.0f, ((float) backgroundColor.getBlue()) / 255.0f}));
            }
        }
    }

    private void writeAttributes(PdfTemplate pdfTemplate) {
        if (pdfTemplate != null) {
            setAttribute(PdfName.f617O, PdfName.LAYOUT);
            if (pdfTemplate.getWidth() > 0.0f) {
                setAttribute(PdfName.WIDTH, new PdfNumber(pdfTemplate.getWidth()));
            }
            if (pdfTemplate.getHeight() > 0.0f) {
                setAttribute(PdfName.HEIGHT, new PdfNumber(pdfTemplate.getHeight()));
            }
            setAttribute(PdfName.BBOX, new PdfRectangle(pdfTemplate.getBoundingBox()));
        }
    }

    private void writeAttributes(Paragraph paragraph) {
        if (paragraph != null) {
            setAttribute(PdfName.f617O, PdfName.LAYOUT);
            if (Float.compare(paragraph.getSpacingBefore(), 0.0f) != 0) {
                setAttribute(PdfName.SPACEBEFORE, new PdfNumber(paragraph.getSpacingBefore()));
            }
            if (Float.compare(paragraph.getSpacingAfter(), 0.0f) != 0) {
                setAttribute(PdfName.SPACEAFTER, new PdfNumber(paragraph.getSpacingAfter()));
            }
            boolean z = true;
            IPdfStructureElement iPdfStructureElement = (IPdfStructureElement) getParent(true);
            PdfObject parentAttribute = getParentAttribute(iPdfStructureElement, PdfName.COLOR);
            if (!(paragraph.getFont() == null || paragraph.getFont().getColor() == null)) {
                setColorAttribute(paragraph.getFont().getColor(), parentAttribute, PdfName.COLOR);
            }
            PdfObject parentAttribute2 = getParentAttribute(iPdfStructureElement, PdfName.TEXTINDENT);
            if (Float.compare(paragraph.getFirstLineIndent(), 0.0f) != 0) {
                if ((parentAttribute2 instanceof PdfNumber) && Float.compare(((PdfNumber) parentAttribute2).floatValue(), new Float(paragraph.getFirstLineIndent()).floatValue()) == 0) {
                    z = false;
                }
                if (z) {
                    setAttribute(PdfName.TEXTINDENT, new PdfNumber(paragraph.getFirstLineIndent()));
                }
            }
            PdfObject parentAttribute3 = getParentAttribute(iPdfStructureElement, PdfName.STARTINDENT);
            if (parentAttribute3 instanceof PdfNumber) {
                if (Float.compare(((PdfNumber) parentAttribute3).floatValue(), paragraph.getIndentationLeft()) != 0) {
                    setAttribute(PdfName.STARTINDENT, new PdfNumber(paragraph.getIndentationLeft()));
                }
            } else if (Math.abs(paragraph.getIndentationLeft()) > Float.MIN_VALUE) {
                setAttribute(PdfName.STARTINDENT, new PdfNumber(paragraph.getIndentationLeft()));
            }
            PdfObject parentAttribute4 = getParentAttribute(iPdfStructureElement, PdfName.ENDINDENT);
            if (parentAttribute4 instanceof PdfNumber) {
                if (Float.compare(((PdfNumber) parentAttribute4).floatValue(), paragraph.getIndentationRight()) != 0) {
                    setAttribute(PdfName.ENDINDENT, new PdfNumber(paragraph.getIndentationRight()));
                }
            } else if (Float.compare(paragraph.getIndentationRight(), 0.0f) != 0) {
                setAttribute(PdfName.ENDINDENT, new PdfNumber(paragraph.getIndentationRight()));
            }
            setTextAlignAttribute(paragraph.getAlignment());
        }
    }

    private void writeAttributes(List list) {
        if (list != null) {
            setAttribute(PdfName.f617O, PdfName.LIST);
            if (list.isAutoindent()) {
                if (list.isNumbered()) {
                    if (!list.isLettered()) {
                        setAttribute(PdfName.LISTNUMBERING, PdfName.DECIMAL);
                    } else if (list.isLowercase()) {
                        setAttribute(PdfName.LISTNUMBERING, PdfName.LOWERROMAN);
                    } else {
                        setAttribute(PdfName.LISTNUMBERING, PdfName.UPPERROMAN);
                    }
                } else if (list.isLettered()) {
                    if (list.isLowercase()) {
                        setAttribute(PdfName.LISTNUMBERING, PdfName.LOWERALPHA);
                    } else {
                        setAttribute(PdfName.LISTNUMBERING, PdfName.UPPERALPHA);
                    }
                }
            }
            PdfObject parentAttribute = getParentAttribute(this.parent, PdfName.STARTINDENT);
            if (parentAttribute instanceof PdfNumber) {
                if (Float.compare(((PdfNumber) parentAttribute).floatValue(), list.getIndentationLeft()) != 0) {
                    setAttribute(PdfName.STARTINDENT, new PdfNumber(list.getIndentationLeft()));
                }
            } else if (Math.abs(list.getIndentationLeft()) > Float.MIN_VALUE) {
                setAttribute(PdfName.STARTINDENT, new PdfNumber(list.getIndentationLeft()));
            }
            PdfObject parentAttribute2 = getParentAttribute(this.parent, PdfName.ENDINDENT);
            if (parentAttribute2 instanceof PdfNumber) {
                if (Float.compare(((PdfNumber) parentAttribute2).floatValue(), list.getIndentationRight()) != 0) {
                    setAttribute(PdfName.ENDINDENT, new PdfNumber(list.getIndentationRight()));
                }
            } else if (Float.compare(list.getIndentationRight(), 0.0f) != 0) {
                setAttribute(PdfName.ENDINDENT, new PdfNumber(list.getIndentationRight()));
            }
        }
    }

    private void writeAttributes(ListItem listItem) {
        if (listItem != null) {
            PdfObject parentAttribute = getParentAttribute(this.parent, PdfName.STARTINDENT);
            if (parentAttribute instanceof PdfNumber) {
                if (Float.compare(((PdfNumber) parentAttribute).floatValue(), listItem.getIndentationLeft()) != 0) {
                    setAttribute(PdfName.STARTINDENT, new PdfNumber(listItem.getIndentationLeft()));
                }
            } else if (Math.abs(listItem.getIndentationLeft()) > Float.MIN_VALUE) {
                setAttribute(PdfName.STARTINDENT, new PdfNumber(listItem.getIndentationLeft()));
            }
            PdfObject parentAttribute2 = getParentAttribute(this.parent, PdfName.ENDINDENT);
            if (parentAttribute2 instanceof PdfNumber) {
                if (Float.compare(((PdfNumber) parentAttribute2).floatValue(), listItem.getIndentationRight()) != 0) {
                    setAttribute(PdfName.ENDINDENT, new PdfNumber(listItem.getIndentationRight()));
                }
            } else if (Float.compare(listItem.getIndentationRight(), 0.0f) != 0) {
                setAttribute(PdfName.ENDINDENT, new PdfNumber(listItem.getIndentationRight()));
            }
        }
    }

    private void writeAttributes(ListLabel listLabel) {
        if (listLabel != null) {
            PdfObject parentAttribute = getParentAttribute(this.parent, PdfName.STARTINDENT);
            if (parentAttribute instanceof PdfNumber) {
                if (Float.compare(((PdfNumber) parentAttribute).floatValue(), listLabel.getIndentation()) != 0) {
                    setAttribute(PdfName.STARTINDENT, new PdfNumber(listLabel.getIndentation()));
                }
            } else if (Math.abs(listLabel.getIndentation()) > Float.MIN_VALUE) {
                setAttribute(PdfName.STARTINDENT, new PdfNumber(listLabel.getIndentation()));
            }
        }
    }

    private void writeAttributes(PdfPTable pdfPTable) {
        if (pdfPTable != null) {
            setAttribute(PdfName.f617O, PdfName.TABLE);
            if (Float.compare(pdfPTable.getSpacingBefore(), 0.0f) != 0) {
                setAttribute(PdfName.SPACEBEFORE, new PdfNumber(pdfPTable.getSpacingBefore()));
            }
            if (Float.compare(pdfPTable.getSpacingAfter(), 0.0f) != 0) {
                setAttribute(PdfName.SPACEAFTER, new PdfNumber(pdfPTable.getSpacingAfter()));
            }
            if (pdfPTable.getTotalHeight() > 0.0f) {
                setAttribute(PdfName.HEIGHT, new PdfNumber(pdfPTable.getTotalHeight()));
            }
            if (pdfPTable.getTotalWidth() > 0.0f) {
                setAttribute(PdfName.WIDTH, new PdfNumber(pdfPTable.getTotalWidth()));
            }
        }
    }

    private void writeAttributes(PdfPRow pdfPRow) {
        if (pdfPRow != null) {
            setAttribute(PdfName.f617O, PdfName.TABLE);
        }
    }

    private void writeAttributes(PdfPCell pdfPCell) {
        if (pdfPCell != null) {
            setAttribute(PdfName.f617O, PdfName.TABLE);
            if (pdfPCell.getColspan() != 1) {
                setAttribute(PdfName.COLSPAN, new PdfNumber(pdfPCell.getColspan()));
            }
            if (pdfPCell.getRowspan() != 1) {
                setAttribute(PdfName.ROWSPAN, new PdfNumber(pdfPCell.getRowspan()));
            }
            if (pdfPCell.getHeaders() != null) {
                PdfArray pdfArray = new PdfArray();
                Iterator<PdfPHeaderCell> it = pdfPCell.getHeaders().iterator();
                while (it.hasNext()) {
                    PdfPHeaderCell next = it.next();
                    if (next.getName() != null) {
                        pdfArray.add((PdfObject) new PdfString(next.getName()));
                    }
                }
                if (!pdfArray.isEmpty()) {
                    setAttribute(PdfName.HEADERS, pdfArray);
                }
            }
            if (pdfPCell.getCalculatedHeight() > 0.0f) {
                setAttribute(PdfName.HEIGHT, new PdfNumber(pdfPCell.getCalculatedHeight()));
            }
            if (pdfPCell.getWidth() > 0.0f) {
                setAttribute(PdfName.WIDTH, new PdfNumber(pdfPCell.getWidth()));
            }
            if (pdfPCell.getBackgroundColor() != null) {
                BaseColor backgroundColor = pdfPCell.getBackgroundColor();
                setAttribute(PdfName.BACKGROUNDCOLOR, new PdfArray(new float[]{((float) backgroundColor.getRed()) / 255.0f, ((float) backgroundColor.getGreen()) / 255.0f, ((float) backgroundColor.getBlue()) / 255.0f}));
            }
        }
    }

    private void writeAttributes(PdfPHeaderCell pdfPHeaderCell) {
        if (pdfPHeaderCell != null) {
            if (pdfPHeaderCell.getScope() != 0) {
                int scope = pdfPHeaderCell.getScope();
                if (scope == 1) {
                    setAttribute(PdfName.SCOPE, PdfName.ROW);
                } else if (scope == 2) {
                    setAttribute(PdfName.SCOPE, PdfName.COLUMN);
                } else if (scope == 3) {
                    setAttribute(PdfName.SCOPE, PdfName.BOTH);
                }
            }
            if (pdfPHeaderCell.getName() != null) {
                setAttribute(PdfName.NAME, new PdfName(pdfPHeaderCell.getName()));
            }
            writeAttributes((PdfPCell) pdfPHeaderCell);
        }
    }

    private void writeAttributes(PdfPTableHeader pdfPTableHeader) {
        if (pdfPTableHeader != null) {
            setAttribute(PdfName.f617O, PdfName.TABLE);
        }
    }

    private void writeAttributes(PdfDiv pdfDiv) {
        if (pdfDiv != null) {
            if (pdfDiv.getBackgroundColor() != null) {
                setColorAttribute(pdfDiv.getBackgroundColor(), (PdfObject) null, PdfName.BACKGROUNDCOLOR);
            }
            setTextAlignAttribute(pdfDiv.getTextAlignment());
        }
    }

    private boolean colorsEqual(PdfArray pdfArray, float[] fArr) {
        return Float.compare(fArr[0], pdfArray.getAsNumber(0).floatValue()) == 0 && Float.compare(fArr[1], pdfArray.getAsNumber(1).floatValue()) == 0 && Float.compare(fArr[2], pdfArray.getAsNumber(2).floatValue()) == 0;
    }

    private void setColorAttribute(BaseColor baseColor, PdfObject pdfObject, PdfName pdfName) {
        float[] fArr = {((float) baseColor.getRed()) / 255.0f, ((float) baseColor.getGreen()) / 255.0f, ((float) baseColor.getBlue()) / 255.0f};
        if (pdfObject == null || !(pdfObject instanceof PdfArray)) {
            setAttribute(pdfName, new PdfArray(fArr));
        } else if (colorsEqual((PdfArray) pdfObject, fArr)) {
            setAttribute(pdfName, new PdfArray(fArr));
        } else {
            setAttribute(pdfName, new PdfArray(fArr));
        }
    }

    private void setTextAlignAttribute(int i) {
        PdfName pdfName;
        if (i == 0) {
            pdfName = PdfName.START;
        } else if (i == 1) {
            pdfName = PdfName.CENTER;
        } else if (i != 2) {
            pdfName = i != 3 ? null : PdfName.JUSTIFY;
        } else {
            pdfName = PdfName.END;
        }
        PdfObject parentAttribute = getParentAttribute(this.parent, PdfName.TEXTALIGN);
        if (parentAttribute instanceof PdfName) {
            PdfName pdfName2 = (PdfName) parentAttribute;
            if (pdfName != null && !pdfName2.equals(pdfName)) {
                setAttribute(PdfName.TEXTALIGN, pdfName);
            }
        } else if (pdfName != null && !PdfName.START.equals(pdfName)) {
            setAttribute(PdfName.TEXTALIGN, pdfName);
        }
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        PdfWriter.checkPdfIsoConformance(pdfWriter, 16, this);
        super.toPdf(pdfWriter, outputStream);
    }

    private PdfObject getParentAttribute(IPdfStructureElement iPdfStructureElement, PdfName pdfName) {
        if (iPdfStructureElement == null) {
            return null;
        }
        return iPdfStructureElement.getAttribute(pdfName);
    }

    /* access modifiers changed from: protected */
    public void setStructureTreeRoot(PdfStructureTreeRoot pdfStructureTreeRoot) {
        this.f2171top = pdfStructureTreeRoot;
    }

    /* access modifiers changed from: protected */
    public void setStructureElementParent(PdfStructureElement pdfStructureElement) {
        this.parent = pdfStructureElement;
    }

    /* access modifiers changed from: protected */
    public AccessibleElementId getElementId() {
        return this.elementId;
    }
}
