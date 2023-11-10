package com.itextpdf.text.pdf;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocWriter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PdfAnnotation extends PdfDictionary implements IAccessibleElement {
    public static final PdfName AA_BLUR = PdfName.f554BL;
    public static final PdfName AA_DOWN = PdfName.f567D;
    public static final PdfName AA_ENTER = PdfName.f578E;
    public static final PdfName AA_EXIT = PdfName.f674X;
    public static final PdfName AA_FOCUS = PdfName.f586FO;
    public static final PdfName AA_JS_CHANGE = PdfName.f664V;
    public static final PdfName AA_JS_FORMAT = PdfName.f580F;
    public static final PdfName AA_JS_KEY = PdfName.f605K;
    public static final PdfName AA_JS_OTHER_CHANGE = PdfName.f557C;
    public static final PdfName AA_UP = PdfName.f660U;
    public static final PdfName APPEARANCE_DOWN = PdfName.f567D;
    public static final PdfName APPEARANCE_NORMAL = PdfName.f610N;
    public static final PdfName APPEARANCE_ROLLOVER = PdfName.f632R;
    public static final int FLAGS_HIDDEN = 2;
    public static final int FLAGS_INVISIBLE = 1;
    public static final int FLAGS_LOCKED = 128;
    public static final int FLAGS_LOCKEDCONTENTS = 512;
    public static final int FLAGS_NOROTATE = 16;
    public static final int FLAGS_NOVIEW = 32;
    public static final int FLAGS_NOZOOM = 8;
    public static final int FLAGS_PRINT = 4;
    public static final int FLAGS_READONLY = 64;
    public static final int FLAGS_TOGGLENOVIEW = 256;
    public static final PdfName HIGHLIGHT_INVERT = PdfName.f598I;
    public static final PdfName HIGHLIGHT_NONE = PdfName.f610N;
    public static final PdfName HIGHLIGHT_OUTLINE = PdfName.f617O;
    public static final PdfName HIGHLIGHT_PUSH = PdfName.f623P;
    public static final PdfName HIGHLIGHT_TOGGLE = PdfName.f647T;
    public static final int MARKUP_HIGHLIGHT = 0;
    public static final int MARKUP_SQUIGGLY = 3;
    public static final int MARKUP_STRIKEOUT = 2;
    public static final int MARKUP_UNDERLINE = 1;
    protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
    protected boolean annotation = true;
    protected boolean form = false;

    /* renamed from: id */
    private AccessibleElementId f535id = null;
    private int placeInPage = -1;
    protected PdfIndirectReference reference;
    protected PdfName role = null;
    protected HashSet<PdfTemplate> templates;
    protected boolean used = false;
    protected PdfWriter writer;

    public boolean isInline() {
        return false;
    }

    public PdfAnnotation(PdfWriter pdfWriter, Rectangle rectangle) {
        this.writer = pdfWriter;
        if (rectangle != null) {
            put(PdfName.RECT, new PdfRectangle(rectangle));
        }
    }

    public PdfAnnotation(PdfWriter pdfWriter, float f, float f2, float f3, float f4, PdfString pdfString, PdfString pdfString2) {
        this.writer = pdfWriter;
        put(PdfName.SUBTYPE, PdfName.TEXT);
        put(PdfName.f647T, pdfString);
        put(PdfName.RECT, new PdfRectangle(f, f2, f3, f4));
        put(PdfName.CONTENTS, pdfString2);
    }

    public PdfAnnotation(PdfWriter pdfWriter, float f, float f2, float f3, float f4, PdfAction pdfAction) {
        this.writer = pdfWriter;
        put(PdfName.SUBTYPE, PdfName.LINK);
        put(PdfName.RECT, new PdfRectangle(f, f2, f3, f4));
        put(PdfName.f545A, pdfAction);
        put(PdfName.BORDER, new PdfBorderArray(0.0f, 0.0f, 0.0f));
        put(PdfName.f557C, new PdfColor(0, 0, 255));
    }

    public static PdfAnnotation createScreen(PdfWriter pdfWriter, Rectangle rectangle, String str, PdfFileSpecification pdfFileSpecification, String str2, boolean z) throws IOException {
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.SCREEN);
        createAnnotation.put(PdfName.f580F, new PdfNumber(4));
        createAnnotation.put(PdfName.TYPE, PdfName.ANNOT);
        createAnnotation.setPage();
        PdfIndirectReference indirectReference = pdfWriter.addToBody(PdfAction.rendition(str, pdfFileSpecification, str2, createAnnotation.getIndirectReference())).getIndirectReference();
        if (z) {
            PdfDictionary pdfDictionary = new PdfDictionary();
            pdfDictionary.put(new PdfName("PV"), indirectReference);
            createAnnotation.put(PdfName.f546AA, pdfDictionary);
        }
        createAnnotation.put(PdfName.f545A, indirectReference);
        return createAnnotation;
    }

    public PdfIndirectReference getIndirectReference() {
        if (this.reference == null) {
            this.reference = this.writer.getPdfIndirectReference();
        }
        return this.reference;
    }

    public static PdfAnnotation createText(PdfWriter pdfWriter, Rectangle rectangle, String str, String str2, boolean z, String str3) {
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.TEXT);
        if (str != null) {
            createAnnotation.put(PdfName.f647T, new PdfString(str, PdfObject.TEXT_UNICODE));
        }
        if (str2 != null) {
            createAnnotation.put(PdfName.CONTENTS, new PdfString(str2, PdfObject.TEXT_UNICODE));
        }
        if (z) {
            createAnnotation.put(PdfName.OPEN, PdfBoolean.PDFTRUE);
        }
        if (str3 != null) {
            createAnnotation.put(PdfName.NAME, new PdfName(str3));
        }
        return createAnnotation;
    }

    protected static PdfAnnotation createLink(PdfWriter pdfWriter, Rectangle rectangle, PdfName pdfName) {
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.LINK);
        if (!pdfName.equals(HIGHLIGHT_INVERT)) {
            createAnnotation.put(PdfName.f589H, pdfName);
        }
        return createAnnotation;
    }

    public static PdfAnnotation createLink(PdfWriter pdfWriter, Rectangle rectangle, PdfName pdfName, PdfAction pdfAction) {
        PdfAnnotation createLink = createLink(pdfWriter, rectangle, pdfName);
        createLink.putEx(PdfName.f545A, pdfAction);
        return createLink;
    }

    public static PdfAnnotation createLink(PdfWriter pdfWriter, Rectangle rectangle, PdfName pdfName, String str) {
        PdfAnnotation createLink = createLink(pdfWriter, rectangle, pdfName);
        createLink.put(PdfName.DEST, new PdfString(str, PdfObject.TEXT_UNICODE));
        return createLink;
    }

    public static PdfAnnotation createLink(PdfWriter pdfWriter, Rectangle rectangle, PdfName pdfName, int i, PdfDestination pdfDestination) {
        PdfAnnotation createLink = createLink(pdfWriter, rectangle, pdfName);
        PdfIndirectReference pageReference = pdfWriter.getPageReference(i);
        PdfDestination pdfDestination2 = new PdfDestination(pdfDestination);
        pdfDestination2.addPage(pageReference);
        createLink.put(PdfName.DEST, pdfDestination2);
        return createLink;
    }

    public static PdfAnnotation createFreeText(PdfWriter pdfWriter, Rectangle rectangle, String str, PdfContentByte pdfContentByte) {
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.FREETEXT);
        createAnnotation.put(PdfName.CONTENTS, new PdfString(str, PdfObject.TEXT_UNICODE));
        createAnnotation.setDefaultAppearanceString(pdfContentByte);
        return createAnnotation;
    }

    public static PdfAnnotation createLine(PdfWriter pdfWriter, Rectangle rectangle, String str, float f, float f2, float f3, float f4) {
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.LINE);
        createAnnotation.put(PdfName.CONTENTS, new PdfString(str, PdfObject.TEXT_UNICODE));
        PdfArray pdfArray = new PdfArray((PdfObject) new PdfNumber(f));
        pdfArray.add((PdfObject) new PdfNumber(f2));
        pdfArray.add((PdfObject) new PdfNumber(f3));
        pdfArray.add((PdfObject) new PdfNumber(f4));
        createAnnotation.put(PdfName.f606L, pdfArray);
        return createAnnotation;
    }

    public static PdfAnnotation createSquareCircle(PdfWriter pdfWriter, Rectangle rectangle, String str, boolean z) {
        PdfAnnotation pdfAnnotation;
        if (z) {
            pdfAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.SQUARE);
        } else {
            pdfAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.CIRCLE);
        }
        pdfAnnotation.put(PdfName.CONTENTS, new PdfString(str, PdfObject.TEXT_UNICODE));
        return pdfAnnotation;
    }

    public static PdfAnnotation createMarkup(PdfWriter pdfWriter, Rectangle rectangle, String str, int i, float[] fArr) {
        PdfName pdfName = PdfName.HIGHLIGHT;
        if (i == 1) {
            pdfName = PdfName.UNDERLINE;
        } else if (i == 2) {
            pdfName = PdfName.STRIKEOUT;
        } else if (i == 3) {
            pdfName = PdfName.SQUIGGLY;
        }
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, pdfName);
        createAnnotation.put(PdfName.CONTENTS, new PdfString(str, PdfObject.TEXT_UNICODE));
        PdfArray pdfArray = new PdfArray();
        for (float pdfNumber : fArr) {
            pdfArray.add((PdfObject) new PdfNumber(pdfNumber));
        }
        createAnnotation.put(PdfName.QUADPOINTS, pdfArray);
        return createAnnotation;
    }

    public static PdfAnnotation createStamp(PdfWriter pdfWriter, Rectangle rectangle, String str, String str2) {
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.STAMP);
        createAnnotation.put(PdfName.CONTENTS, new PdfString(str, PdfObject.TEXT_UNICODE));
        createAnnotation.put(PdfName.NAME, new PdfName(str2));
        return createAnnotation;
    }

    public static PdfAnnotation createInk(PdfWriter pdfWriter, Rectangle rectangle, String str, float[][] fArr) {
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.INK);
        createAnnotation.put(PdfName.CONTENTS, new PdfString(str, PdfObject.TEXT_UNICODE));
        PdfArray pdfArray = new PdfArray();
        for (float[] fArr2 : fArr) {
            PdfArray pdfArray2 = new PdfArray();
            for (float pdfNumber : fArr2) {
                pdfArray2.add((PdfObject) new PdfNumber(pdfNumber));
            }
            pdfArray.add((PdfObject) pdfArray2);
        }
        createAnnotation.put(PdfName.INKLIST, pdfArray);
        return createAnnotation;
    }

    public static PdfAnnotation createFileAttachment(PdfWriter pdfWriter, Rectangle rectangle, String str, byte[] bArr, String str2, String str3) throws IOException {
        return createFileAttachment(pdfWriter, rectangle, str, PdfFileSpecification.fileEmbedded(pdfWriter, str2, str3, bArr));
    }

    public static PdfAnnotation createFileAttachment(PdfWriter pdfWriter, Rectangle rectangle, String str, PdfFileSpecification pdfFileSpecification) throws IOException {
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.FILEATTACHMENT);
        if (str != null) {
            createAnnotation.put(PdfName.CONTENTS, new PdfString(str, PdfObject.TEXT_UNICODE));
        }
        createAnnotation.put(PdfName.f587FS, pdfFileSpecification.getReference());
        return createAnnotation;
    }

    public static PdfAnnotation createPopup(PdfWriter pdfWriter, Rectangle rectangle, String str, boolean z) {
        PdfAnnotation createAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.POPUP);
        if (str != null) {
            createAnnotation.put(PdfName.CONTENTS, new PdfString(str, PdfObject.TEXT_UNICODE));
        }
        if (z) {
            createAnnotation.put(PdfName.OPEN, PdfBoolean.PDFTRUE);
        }
        return createAnnotation;
    }

    public static PdfAnnotation createPolygonPolyline(PdfWriter pdfWriter, Rectangle rectangle, String str, boolean z, PdfArray pdfArray) {
        PdfAnnotation pdfAnnotation;
        if (z) {
            pdfAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.POLYGON);
        } else {
            pdfAnnotation = pdfWriter.createAnnotation(rectangle, PdfName.POLYLINE);
        }
        pdfAnnotation.put(PdfName.CONTENTS, new PdfString(str, PdfObject.TEXT_UNICODE));
        pdfAnnotation.put(PdfName.VERTICES, new PdfArray(pdfArray));
        return pdfAnnotation;
    }

    public void setDefaultAppearanceString(PdfContentByte pdfContentByte) {
        byte[] byteArray = pdfContentByte.getInternalBuffer().toByteArray();
        int length = byteArray.length;
        for (int i = 0; i < length; i++) {
            if (byteArray[i] == 10) {
                byteArray[i] = DocWriter.SPACE;
            }
        }
        put(PdfName.f568DA, new PdfString(byteArray));
    }

    public void setFlags(int i) {
        if (i == 0) {
            remove(PdfName.f580F);
        } else {
            put(PdfName.f580F, new PdfNumber(i));
        }
    }

    public void setBorder(PdfBorderArray pdfBorderArray) {
        put(PdfName.BORDER, pdfBorderArray);
    }

    public void setBorderStyle(PdfBorderDictionary pdfBorderDictionary) {
        put(PdfName.f556BS, pdfBorderDictionary);
    }

    public void setHighlighting(PdfName pdfName) {
        if (pdfName.equals(HIGHLIGHT_INVERT)) {
            remove(PdfName.f589H);
        } else {
            put(PdfName.f589H, pdfName);
        }
    }

    public void setAppearance(PdfName pdfName, PdfTemplate pdfTemplate) {
        PdfDictionary pdfDictionary = (PdfDictionary) get(PdfName.f549AP);
        if (pdfDictionary == null) {
            pdfDictionary = new PdfDictionary();
        }
        pdfDictionary.put(pdfName, pdfTemplate.getIndirectReference());
        put(PdfName.f549AP, pdfDictionary);
        if (this.form) {
            if (this.templates == null) {
                this.templates = new HashSet<>();
            }
            this.templates.add(pdfTemplate);
        }
    }

    public void setAppearance(PdfName pdfName, String str, PdfTemplate pdfTemplate) {
        PdfDictionary pdfDictionary;
        PdfDictionary pdfDictionary2 = (PdfDictionary) get(PdfName.f549AP);
        if (pdfDictionary2 == null) {
            pdfDictionary2 = new PdfDictionary();
        }
        PdfObject pdfObject = pdfDictionary2.get(pdfName);
        if (pdfObject == null || !pdfObject.isDictionary()) {
            pdfDictionary = new PdfDictionary();
        } else {
            pdfDictionary = (PdfDictionary) pdfObject;
        }
        pdfDictionary.put(new PdfName(str), pdfTemplate.getIndirectReference());
        pdfDictionary2.put(pdfName, pdfDictionary);
        put(PdfName.f549AP, pdfDictionary2);
        if (this.form) {
            if (this.templates == null) {
                this.templates = new HashSet<>();
            }
            this.templates.add(pdfTemplate);
        }
    }

    public void setAppearanceState(String str) {
        if (str == null) {
            remove(PdfName.f550AS);
        } else {
            put(PdfName.f550AS, new PdfName(str));
        }
    }

    public void setColor(BaseColor baseColor) {
        put(PdfName.f557C, new PdfColor(baseColor));
    }

    public void setTitle(String str) {
        if (str == null) {
            remove(PdfName.f647T);
        } else {
            put(PdfName.f647T, new PdfString(str, PdfObject.TEXT_UNICODE));
        }
    }

    public void setPopup(PdfAnnotation pdfAnnotation) {
        put(PdfName.POPUP, pdfAnnotation.getIndirectReference());
        pdfAnnotation.put(PdfName.PARENT, getIndirectReference());
    }

    public void setAction(PdfAction pdfAction) {
        put(PdfName.f545A, pdfAction);
    }

    public void setAdditionalActions(PdfName pdfName, PdfAction pdfAction) {
        PdfDictionary pdfDictionary;
        PdfObject pdfObject = get(PdfName.f546AA);
        if (pdfObject == null || !pdfObject.isDictionary()) {
            pdfDictionary = new PdfDictionary();
        } else {
            pdfDictionary = (PdfDictionary) pdfObject;
        }
        pdfDictionary.put(pdfName, pdfAction);
        put(PdfName.f546AA, pdfDictionary);
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed() {
        this.used = true;
    }

    public HashSet<PdfTemplate> getTemplates() {
        return this.templates;
    }

    public boolean isForm() {
        return this.form;
    }

    public boolean isAnnotation() {
        return this.annotation;
    }

    public void setPage(int i) {
        put(PdfName.f623P, this.writer.getPageReference(i));
    }

    public void setPage() {
        put(PdfName.f623P, this.writer.getCurrentPage());
    }

    public int getPlaceInPage() {
        return this.placeInPage;
    }

    public void setPlaceInPage(int i) {
        this.placeInPage = i;
    }

    public void setRotate(int i) {
        put(PdfName.ROTATE, new PdfNumber(i));
    }

    /* access modifiers changed from: package-private */
    public PdfDictionary getMK() {
        PdfDictionary pdfDictionary = (PdfDictionary) get(PdfName.f609MK);
        if (pdfDictionary != null) {
            return pdfDictionary;
        }
        PdfDictionary pdfDictionary2 = new PdfDictionary();
        put(PdfName.f609MK, pdfDictionary2);
        return pdfDictionary2;
    }

    public void setMKRotation(int i) {
        getMK().put(PdfName.f632R, new PdfNumber(i));
    }

    public static PdfArray getMKColor(BaseColor baseColor) {
        PdfArray pdfArray = new PdfArray();
        int type = ExtendedColor.getType(baseColor);
        if (type == 1) {
            pdfArray.add((PdfObject) new PdfNumber(((GrayColor) baseColor).getGray()));
        } else if (type == 2) {
            CMYKColor cMYKColor = (CMYKColor) baseColor;
            pdfArray.add((PdfObject) new PdfNumber(cMYKColor.getCyan()));
            pdfArray.add((PdfObject) new PdfNumber(cMYKColor.getMagenta()));
            pdfArray.add((PdfObject) new PdfNumber(cMYKColor.getYellow()));
            pdfArray.add((PdfObject) new PdfNumber(cMYKColor.getBlack()));
        } else if (type == 3 || type == 4 || type == 5) {
            throw new RuntimeException(MessageLocalization.getComposedMessage("separations.patterns.and.shadings.are.not.allowed.in.mk.dictionary", new Object[0]));
        } else {
            pdfArray.add((PdfObject) new PdfNumber(((float) baseColor.getRed()) / 255.0f));
            pdfArray.add((PdfObject) new PdfNumber(((float) baseColor.getGreen()) / 255.0f));
            pdfArray.add((PdfObject) new PdfNumber(((float) baseColor.getBlue()) / 255.0f));
        }
        return pdfArray;
    }

    public void setMKBorderColor(BaseColor baseColor) {
        if (baseColor == null) {
            getMK().remove(PdfName.f552BC);
        } else {
            getMK().put(PdfName.f552BC, getMKColor(baseColor));
        }
    }

    public void setMKBackgroundColor(BaseColor baseColor) {
        if (baseColor == null) {
            getMK().remove(PdfName.f553BG);
        } else {
            getMK().put(PdfName.f553BG, getMKColor(baseColor));
        }
    }

    public void setMKNormalCaption(String str) {
        getMK().put(PdfName.f560CA, new PdfString(str, PdfObject.TEXT_UNICODE));
    }

    public void setMKRolloverCaption(String str) {
        getMK().put(PdfName.f634RC, new PdfString(str, PdfObject.TEXT_UNICODE));
    }

    public void setMKAlternateCaption(String str) {
        getMK().put(PdfName.f547AC, new PdfString(str, PdfObject.TEXT_UNICODE));
    }

    public void setMKNormalIcon(PdfTemplate pdfTemplate) {
        getMK().put(PdfName.f598I, pdfTemplate.getIndirectReference());
    }

    public void setMKRolloverIcon(PdfTemplate pdfTemplate) {
        getMK().put(PdfName.f636RI, pdfTemplate.getIndirectReference());
    }

    public void setMKAlternateIcon(PdfTemplate pdfTemplate) {
        getMK().put(PdfName.f603IX, pdfTemplate.getIndirectReference());
    }

    public void setMKIconFit(PdfName pdfName, PdfName pdfName2, float f, float f2, boolean z) {
        PdfDictionary pdfDictionary = new PdfDictionary();
        if (!pdfName.equals(PdfName.f545A)) {
            pdfDictionary.put(PdfName.f646SW, pdfName);
        }
        if (!pdfName2.equals(PdfName.f623P)) {
            pdfDictionary.put(PdfName.f642S, pdfName2);
        }
        if (!(f == 0.5f && f2 == 0.5f)) {
            PdfArray pdfArray = new PdfArray((PdfObject) new PdfNumber(f));
            pdfArray.add((PdfObject) new PdfNumber(f2));
            pdfDictionary.put(PdfName.f545A, pdfArray);
        }
        if (z) {
            pdfDictionary.put(PdfName.f581FB, PdfBoolean.PDFTRUE);
        }
        getMK().put(PdfName.f601IF, pdfDictionary);
    }

    public void setMKTextPosition(int i) {
        getMK().put(PdfName.f654TP, new PdfNumber(i));
    }

    public void setLayer(PdfOCG pdfOCG) {
        put(PdfName.f618OC, pdfOCG.getRef());
    }

    public void setName(String str) {
        put(PdfName.f616NM, new PdfString(str));
    }

    public void applyCTM(AffineTransform affineTransform) {
        PdfRectangle pdfRectangle;
        PdfArray asArray = getAsArray(PdfName.RECT);
        if (asArray != null) {
            if (asArray.size() == 4) {
                pdfRectangle = new PdfRectangle(asArray.getAsNumber(0).floatValue(), asArray.getAsNumber(1).floatValue(), asArray.getAsNumber(2).floatValue(), asArray.getAsNumber(3).floatValue());
            } else {
                pdfRectangle = new PdfRectangle(asArray.getAsNumber(0).floatValue(), asArray.getAsNumber(1).floatValue());
            }
            put(PdfName.RECT, pdfRectangle.transform(affineTransform));
        }
    }

    public static class PdfImportedLink {
        PdfArray destination = null;
        float llx;
        float lly;
        int newPage = 0;
        HashMap<PdfName, PdfObject> parameters;
        PdfArray rect;
        float urx;
        float ury;

        PdfImportedLink(PdfDictionary pdfDictionary) {
            HashMap<PdfName, PdfObject> hashMap = new HashMap<>();
            this.parameters = hashMap;
            hashMap.putAll(pdfDictionary.hashMap);
            try {
                PdfArray pdfArray = (PdfArray) this.parameters.remove(PdfName.DEST);
                this.destination = pdfArray;
                if (pdfArray != null) {
                    this.destination = new PdfArray(pdfArray);
                }
                PdfArray pdfArray2 = (PdfArray) this.parameters.remove(PdfName.RECT);
                this.llx = pdfArray2.getAsNumber(0).floatValue();
                this.lly = pdfArray2.getAsNumber(1).floatValue();
                this.urx = pdfArray2.getAsNumber(2).floatValue();
                this.ury = pdfArray2.getAsNumber(3).floatValue();
                this.rect = new PdfArray(pdfArray2);
            } catch (ClassCastException unused) {
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("you.have.to.consolidate.the.named.destinations.of.your.reader", new Object[0]));
            }
        }

        public Map<PdfName, PdfObject> getParameters() {
            return new HashMap(this.parameters);
        }

        public PdfArray getRect() {
            return new PdfArray(this.rect);
        }

        public boolean isInternal() {
            return this.destination != null;
        }

        public int getDestinationPage() {
            if (!isInternal()) {
                return 0;
            }
            PRIndirectReference pRIndirectReference = (PRIndirectReference) this.destination.getAsIndirectObject(0);
            PdfReader reader = pRIndirectReference.getReader();
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                PRIndirectReference pageOrigRef = reader.getPageOrigRef(i);
                if (pageOrigRef.getGeneration() == pRIndirectReference.getGeneration() && pageOrigRef.getNumber() == pRIndirectReference.getNumber()) {
                    return i;
                }
            }
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("page.not.found", new Object[0]));
        }

        public void setDestinationPage(int i) {
            if (isInternal()) {
                this.newPage = i;
                return;
            }
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("cannot.change.destination.of.external.link", new Object[0]));
        }

        public void transformDestination(float f, float f2, float f3, float f4, float f5, float f6) {
            if (!isInternal()) {
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("cannot.change.destination.of.external.link", new Object[0]));
            } else if (this.destination.getAsName(1).equals(PdfName.XYZ)) {
                float floatValue = this.destination.getAsNumber(2).floatValue();
                float floatValue2 = this.destination.getAsNumber(3).floatValue();
                this.destination.set(2, new PdfNumber((f * floatValue) + (f3 * floatValue2) + f5));
                this.destination.set(3, new PdfNumber((floatValue * f2) + (floatValue2 * f4) + f6));
            }
        }

        public void transformRect(float f, float f2, float f3, float f4, float f5, float f6) {
            float f7 = this.llx;
            float f8 = this.lly;
            this.llx = (f7 * f) + (f8 * f3) + f5;
            this.lly = (f7 * f2) + (f8 * f4) + f6;
            float f9 = this.urx;
            float f10 = this.ury;
            this.urx = (f * f9) + (f3 * f10) + f5;
            this.ury = (f9 * f2) + (f10 * f4) + f6;
        }

        public PdfAnnotation createAnnotation(PdfWriter pdfWriter) {
            PdfAnnotation createAnnotation = pdfWriter.createAnnotation(new Rectangle(this.llx, this.lly, this.urx, this.ury), (PdfName) null);
            int i = this.newPage;
            if (i != 0) {
                this.destination.set(0, pdfWriter.getPageReference(i));
            }
            if (this.destination != null) {
                createAnnotation.put(PdfName.DEST, this.destination);
            }
            createAnnotation.hashMap.putAll(this.parameters);
            return createAnnotation;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer("Imported link: location [");
            stringBuffer.append(this.llx);
            stringBuffer.append(' ');
            stringBuffer.append(this.lly);
            stringBuffer.append(' ');
            stringBuffer.append(this.urx);
            stringBuffer.append(' ');
            stringBuffer.append(this.ury);
            stringBuffer.append("] destination ");
            stringBuffer.append(this.destination);
            stringBuffer.append(" parameters ");
            stringBuffer.append(this.parameters);
            HashMap<PdfName, PdfObject> hashMap = this.parameters;
            if (hashMap != null) {
                appendDictionary(stringBuffer, hashMap);
            }
            return stringBuffer.toString();
        }

        private void appendDictionary(StringBuffer stringBuffer, HashMap<PdfName, PdfObject> hashMap) {
            stringBuffer.append(" <<");
            for (Map.Entry next : hashMap.entrySet()) {
                stringBuffer.append(next.getKey());
                stringBuffer.append(":");
                if (next.getValue() instanceof PdfDictionary) {
                    appendDictionary(stringBuffer, ((PdfDictionary) next.getValue()).hashMap);
                } else {
                    stringBuffer.append(next.getValue());
                }
                stringBuffer.append(" ");
            }
            stringBuffer.append(">> ");
        }
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        PdfWriter.checkPdfIsoConformance(pdfWriter, 13, this);
        super.toPdf(pdfWriter, outputStream);
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
        if (this.f535id == null) {
            this.f535id = new AccessibleElementId();
        }
        return this.f535id;
    }

    public void setId(AccessibleElementId accessibleElementId) {
        this.f535id = accessibleElementId;
    }
}
