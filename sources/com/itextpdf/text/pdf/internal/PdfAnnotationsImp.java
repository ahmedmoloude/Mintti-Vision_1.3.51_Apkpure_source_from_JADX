package com.itextpdf.text.pdf.internal;

import com.itextpdf.text.Annotation;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAcroForm;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfRectangle;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class PdfAnnotationsImp {
    protected PdfAcroForm acroForm;
    protected ArrayList<PdfAnnotation> annotations = new ArrayList<>();
    protected ArrayList<PdfAnnotation> delayedAnnotations = new ArrayList<>();

    public PdfAnnotationsImp(PdfWriter pdfWriter) {
        this.acroForm = new PdfAcroForm(pdfWriter);
    }

    public boolean hasValidAcroForm() {
        return this.acroForm.isValid();
    }

    public PdfAcroForm getAcroForm() {
        return this.acroForm;
    }

    public void setSigFlags(int i) {
        this.acroForm.setSigFlags(i);
    }

    public void addCalculationOrder(PdfFormField pdfFormField) {
        this.acroForm.addCalculationOrder(pdfFormField);
    }

    public void addAnnotation(PdfAnnotation pdfAnnotation) {
        if (pdfAnnotation.isForm()) {
            PdfFormField pdfFormField = (PdfFormField) pdfAnnotation;
            if (pdfFormField.getParent() == null) {
                addFormFieldRaw(pdfFormField);
                return;
            }
            return;
        }
        this.annotations.add(pdfAnnotation);
    }

    public void addPlainAnnotation(PdfAnnotation pdfAnnotation) {
        this.annotations.add(pdfAnnotation);
    }

    /* access modifiers changed from: package-private */
    public void addFormFieldRaw(PdfFormField pdfFormField) {
        this.annotations.add(pdfFormField);
        ArrayList<PdfFormField> kids = pdfFormField.getKids();
        if (kids != null) {
            for (int i = 0; i < kids.size(); i++) {
                PdfFormField pdfFormField2 = kids.get(i);
                if (!pdfFormField2.isUsed()) {
                    addFormFieldRaw(pdfFormField2);
                }
            }
        }
    }

    public boolean hasUnusedAnnotations() {
        return !this.annotations.isEmpty();
    }

    public void resetAnnotations() {
        this.annotations = this.delayedAnnotations;
        this.delayedAnnotations = new ArrayList<>();
    }

    public PdfArray rotateAnnotations(PdfWriter pdfWriter, Rectangle rectangle) {
        PdfRectangle pdfRectangle;
        HashSet<PdfTemplate> templates;
        PdfArray pdfArray = new PdfArray();
        int rotation = rectangle.getRotation() % 360;
        int currentPageNumber = pdfWriter.getCurrentPageNumber();
        for (int i = 0; i < this.annotations.size(); i++) {
            PdfAnnotation pdfAnnotation = this.annotations.get(i);
            if (pdfAnnotation.getPlaceInPage() > currentPageNumber) {
                this.delayedAnnotations.add(pdfAnnotation);
            } else {
                if (pdfAnnotation.isForm()) {
                    if (!pdfAnnotation.isUsed() && (templates = pdfAnnotation.getTemplates()) != null) {
                        this.acroForm.addFieldTemplates(templates);
                    }
                    PdfFormField pdfFormField = (PdfFormField) pdfAnnotation;
                    if (pdfFormField.getParent() == null) {
                        this.acroForm.addDocumentField(pdfFormField.getIndirectReference());
                    }
                }
                if (pdfAnnotation.isAnnotation()) {
                    pdfArray.add((PdfObject) pdfAnnotation.getIndirectReference());
                    if (!pdfAnnotation.isUsed()) {
                        PdfArray asArray = pdfAnnotation.getAsArray(PdfName.RECT);
                        if (asArray.size() == 4) {
                            pdfRectangle = new PdfRectangle(asArray.getAsNumber(0).floatValue(), asArray.getAsNumber(1).floatValue(), asArray.getAsNumber(2).floatValue(), asArray.getAsNumber(3).floatValue());
                        } else {
                            pdfRectangle = new PdfRectangle(asArray.getAsNumber(0).floatValue(), asArray.getAsNumber(1).floatValue());
                        }
                        if (rotation == 90) {
                            pdfAnnotation.put(PdfName.RECT, new PdfRectangle(rectangle.getTop() - pdfRectangle.bottom(), pdfRectangle.left(), rectangle.getTop() - pdfRectangle.top(), pdfRectangle.right()));
                        } else if (rotation == 180) {
                            pdfAnnotation.put(PdfName.RECT, new PdfRectangle(rectangle.getRight() - pdfRectangle.left(), rectangle.getTop() - pdfRectangle.bottom(), rectangle.getRight() - pdfRectangle.right(), rectangle.getTop() - pdfRectangle.top()));
                        } else if (rotation == 270) {
                            pdfAnnotation.put(PdfName.RECT, new PdfRectangle(pdfRectangle.bottom(), rectangle.getRight() - pdfRectangle.left(), pdfRectangle.top(), rectangle.getRight() - pdfRectangle.right()));
                        }
                    }
                }
                if (!pdfAnnotation.isUsed()) {
                    pdfAnnotation.setUsed();
                    try {
                        pdfWriter.addToBody((PdfObject) pdfAnnotation, pdfAnnotation.getIndirectReference());
                    } catch (IOException e) {
                        throw new ExceptionConverter(e);
                    }
                }
            }
        }
        return pdfArray;
    }

    public static PdfAnnotation convertAnnotation(PdfWriter pdfWriter, Annotation annotation, Rectangle rectangle) throws IOException {
        PdfFileSpecification pdfFileSpecification;
        switch (annotation.annotationType()) {
            case 1:
                return pdfWriter.createAnnotation(annotation.llx(), annotation.lly(), annotation.urx(), annotation.ury(), new PdfAction((URL) annotation.attributes().get(Annotation.URL)), (PdfName) null);
            case 2:
                return pdfWriter.createAnnotation(annotation.llx(), annotation.lly(), annotation.urx(), annotation.ury(), new PdfAction((String) annotation.attributes().get(Annotation.FILE)), (PdfName) null);
            case 3:
                return pdfWriter.createAnnotation(annotation.llx(), annotation.lly(), annotation.urx(), annotation.ury(), new PdfAction((String) annotation.attributes().get(Annotation.FILE), (String) annotation.attributes().get(Annotation.DESTINATION)), (PdfName) null);
            case 4:
                return pdfWriter.createAnnotation(annotation.llx(), annotation.lly(), annotation.urx(), annotation.ury(), new PdfAction((String) annotation.attributes().get(Annotation.FILE), ((Integer) annotation.attributes().get(Annotation.PAGE)).intValue()), (PdfName) null);
            case 5:
                return pdfWriter.createAnnotation(annotation.llx(), annotation.lly(), annotation.urx(), annotation.ury(), new PdfAction(((Integer) annotation.attributes().get(Annotation.NAMED)).intValue()), (PdfName) null);
            case 6:
                return pdfWriter.createAnnotation(annotation.llx(), annotation.lly(), annotation.urx(), annotation.ury(), new PdfAction((String) annotation.attributes().get(Annotation.APPLICATION), (String) annotation.attributes().get(Annotation.PARAMETERS), (String) annotation.attributes().get(Annotation.OPERATION), (String) annotation.attributes().get(Annotation.DEFAULTDIR)), (PdfName) null);
            case 7:
                boolean[] zArr = (boolean[]) annotation.attributes().get(Annotation.PARAMETERS);
                String str = (String) annotation.attributes().get(Annotation.FILE);
                String str2 = (String) annotation.attributes().get(Annotation.MIMETYPE);
                if (zArr[0]) {
                    pdfFileSpecification = PdfFileSpecification.fileEmbedded(pdfWriter, str, str, (byte[]) null);
                } else {
                    pdfFileSpecification = PdfFileSpecification.fileExtern(pdfWriter, str);
                }
                return PdfAnnotation.createScreen(pdfWriter, new Rectangle(annotation.llx(), annotation.lly(), annotation.urx(), annotation.ury()), str, pdfFileSpecification, str2, zArr[1]);
            default:
                return pdfWriter.createAnnotation(rectangle.getLeft(), rectangle.getBottom(), rectangle.getRight(), rectangle.getTop(), new PdfString(annotation.title(), PdfObject.TEXT_UNICODE), new PdfString(annotation.content(), PdfObject.TEXT_UNICODE), (PdfName) null);
        }
    }
}
