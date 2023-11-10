package com.itextpdf.text.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class PdfAcroForm extends PdfDictionary {
    private PdfArray calculationOrder = new PdfArray();
    private PdfArray documentFields = new PdfArray();
    private HashSet<PdfTemplate> fieldTemplates = new HashSet<>();
    private int sigFlags = 0;
    private PdfWriter writer;

    public PdfAcroForm(PdfWriter pdfWriter) {
        this.writer = pdfWriter;
    }

    public void setNeedAppearances(boolean z) {
        put(PdfName.NEEDAPPEARANCES, new PdfBoolean(z));
    }

    public void addFieldTemplates(HashSet<PdfTemplate> hashSet) {
        this.fieldTemplates.addAll(hashSet);
    }

    public void addDocumentField(PdfIndirectReference pdfIndirectReference) {
        this.documentFields.add((PdfObject) pdfIndirectReference);
    }

    public boolean isValid() {
        if (this.documentFields.size() == 0) {
            return false;
        }
        put(PdfName.FIELDS, this.documentFields);
        if (this.sigFlags != 0) {
            put(PdfName.SIGFLAGS, new PdfNumber(this.sigFlags));
        }
        if (this.calculationOrder.size() > 0) {
            put(PdfName.f565CO, this.calculationOrder);
        }
        if (this.fieldTemplates.isEmpty()) {
            return true;
        }
        PdfDictionary pdfDictionary = new PdfDictionary();
        Iterator<PdfTemplate> it = this.fieldTemplates.iterator();
        while (it.hasNext()) {
            PdfFormField.mergeResources(pdfDictionary, (PdfDictionary) it.next().getResources());
        }
        put(PdfName.f574DR, pdfDictionary);
        put(PdfName.f568DA, new PdfString("/Helv 0 Tf 0 g "));
        PdfDictionary pdfDictionary2 = (PdfDictionary) pdfDictionary.get(PdfName.FONT);
        if (pdfDictionary2 != null) {
            this.writer.eliminateFontSubset(pdfDictionary2);
        }
        return true;
    }

    public void addCalculationOrder(PdfFormField pdfFormField) {
        this.calculationOrder.add((PdfObject) pdfFormField.getIndirectReference());
    }

    public void setSigFlags(int i) {
        this.sigFlags = i | this.sigFlags;
    }

    public void addFormField(PdfFormField pdfFormField) {
        this.writer.addAnnotation(pdfFormField);
    }

    public PdfFormField addHtmlPostButton(String str, String str2, String str3, String str4, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        String str5 = str4;
        PdfFormField pdfFormField = new PdfFormField(this.writer, f2, f3, f4, f5, PdfAction.createSubmitForm(str4, (Object[]) null, 4));
        String str6 = str;
        String str7 = str3;
        setButtonParams(pdfFormField, 65536, str, str3);
        drawButton(pdfFormField, str2, baseFont, f, f2, f3, f4, f5);
        addFormField(pdfFormField);
        return pdfFormField;
    }

    public PdfFormField addResetButton(String str, String str2, String str3, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        PdfFormField pdfFormField = new PdfFormField(this.writer, f2, f3, f4, f5, PdfAction.createResetForm((Object[]) null, 0));
        String str4 = str;
        String str5 = str3;
        setButtonParams(pdfFormField, 65536, str, str3);
        drawButton(pdfFormField, str2, baseFont, f, f2, f3, f4, f5);
        addFormField(pdfFormField);
        return pdfFormField;
    }

    public PdfFormField addMap(String str, String str2, String str3, PdfContentByte pdfContentByte, float f, float f2, float f3, float f4) {
        String str4 = str3;
        PdfFormField pdfFormField = new PdfFormField(this.writer, f, f2, f3, f4, PdfAction.createSubmitForm(str3, (Object[]) null, 20));
        String str5 = str;
        setButtonParams(pdfFormField, 65536, str, (String) null);
        PdfAppearance createAppearance = PdfAppearance.createAppearance(this.writer, f3 - f, f4 - f2);
        PdfContentByte pdfContentByte2 = pdfContentByte;
        createAppearance.add(pdfContentByte);
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, createAppearance);
        addFormField(pdfFormField);
        return pdfFormField;
    }

    public void setButtonParams(PdfFormField pdfFormField, int i, String str, String str2) {
        pdfFormField.setButton(i);
        pdfFormField.setFlags(4);
        pdfFormField.setPage();
        pdfFormField.setFieldName(str);
        if (str2 != null) {
            pdfFormField.setValueAsString(str2);
        }
    }

    public void drawButton(PdfFormField pdfFormField, String str, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        PdfAppearance createAppearance = PdfAppearance.createAppearance(this.writer, f6, f7);
        createAppearance.drawButton(0.0f, 0.0f, f6, f7, str, baseFont, f);
        PdfFormField pdfFormField2 = pdfFormField;
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, createAppearance);
    }

    public PdfFormField addHiddenField(String str, String str2) {
        PdfFormField createEmpty = PdfFormField.createEmpty(this.writer);
        createEmpty.setFieldName(str);
        createEmpty.setValueAsName(str2);
        addFormField(createEmpty);
        return createEmpty;
    }

    public PdfFormField addSingleLineTextField(String str, String str2, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        PdfFormField createTextField = PdfFormField.createTextField(this.writer, false, false, 0);
        PdfFormField pdfFormField = createTextField;
        String str3 = str2;
        setTextFieldParams(pdfFormField, str3, str, f2, f3, f4, f5);
        drawSingleLineOfText(pdfFormField, str3, baseFont, f, f2, f3, f4, f5);
        addFormField(createTextField);
        return createTextField;
    }

    public PdfFormField addMultiLineTextField(String str, String str2, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        PdfFormField createTextField = PdfFormField.createTextField(this.writer, true, false, 0);
        PdfFormField pdfFormField = createTextField;
        String str3 = str2;
        setTextFieldParams(pdfFormField, str3, str, f2, f3, f4, f5);
        drawMultiLineOfText(pdfFormField, str3, baseFont, f, f2, f3, f4, f5);
        addFormField(createTextField);
        return createTextField;
    }

    public PdfFormField addSingleLinePasswordField(String str, String str2, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        PdfFormField createTextField = PdfFormField.createTextField(this.writer, false, true, 0);
        PdfFormField pdfFormField = createTextField;
        String str3 = str2;
        setTextFieldParams(pdfFormField, str3, str, f2, f3, f4, f5);
        drawSingleLineOfText(pdfFormField, str3, baseFont, f, f2, f3, f4, f5);
        addFormField(createTextField);
        return createTextField;
    }

    public void setTextFieldParams(PdfFormField pdfFormField, String str, String str2, float f, float f2, float f3, float f4) {
        pdfFormField.setWidget(new Rectangle(f, f2, f3, f4), PdfAnnotation.HIGHLIGHT_INVERT);
        pdfFormField.setValueAsString(str);
        pdfFormField.setDefaultValueAsString(str);
        pdfFormField.setFieldName(str2);
        pdfFormField.setFlags(4);
        pdfFormField.setPage();
    }

    public void drawSingleLineOfText(PdfFormField pdfFormField, String str, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        PdfAppearance createAppearance = PdfAppearance.createAppearance(this.writer, f6, f7);
        PdfAppearance pdfAppearance = (PdfAppearance) createAppearance.getDuplicate();
        pdfAppearance.setFontAndSize(baseFont, f);
        pdfAppearance.resetRGBColorFill();
        pdfFormField.setDefaultAppearanceString(pdfAppearance);
        createAppearance.drawTextField(0.0f, 0.0f, f6, f7);
        createAppearance.beginVariableText();
        createAppearance.saveState();
        createAppearance.rectangle(3.0f, 3.0f, f6 - 6.0f, f7 - 6.0f);
        createAppearance.clip();
        createAppearance.newPath();
        createAppearance.beginText();
        createAppearance.setFontAndSize(baseFont, f);
        createAppearance.resetRGBColorFill();
        createAppearance.setTextMatrix(4.0f, (f7 / 2.0f) - (f * 0.3f));
        createAppearance.showText(str);
        createAppearance.endText();
        createAppearance.restoreState();
        createAppearance.endVariableText();
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, createAppearance);
    }

    public void drawMultiLineOfText(PdfFormField pdfFormField, String str, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        PdfAppearance createAppearance = PdfAppearance.createAppearance(this.writer, f6, f7);
        PdfAppearance pdfAppearance = (PdfAppearance) createAppearance.getDuplicate();
        pdfAppearance.setFontAndSize(baseFont, f);
        pdfAppearance.resetRGBColorFill();
        pdfFormField.setDefaultAppearanceString(pdfAppearance);
        createAppearance.drawTextField(0.0f, 0.0f, f6, f7);
        createAppearance.beginVariableText();
        createAppearance.saveState();
        createAppearance.rectangle(3.0f, 3.0f, f6 - 6.0f, f7 - 6.0f);
        createAppearance.clip();
        createAppearance.newPath();
        createAppearance.beginText();
        createAppearance.setFontAndSize(baseFont, f);
        createAppearance.resetRGBColorFill();
        createAppearance.setTextMatrix(4.0f, 5.0f);
        StringTokenizer stringTokenizer = new StringTokenizer(str, "\n");
        while (stringTokenizer.hasMoreTokens()) {
            f7 -= 1.2f * f;
            createAppearance.showTextAligned(0, stringTokenizer.nextToken(), 3.0f, f7, 0.0f);
        }
        createAppearance.endText();
        createAppearance.restoreState();
        createAppearance.endVariableText();
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, createAppearance);
    }

    public PdfFormField addCheckBox(String str, String str2, boolean z, float f, float f2, float f3, float f4) {
        PdfFormField createCheckBox = PdfFormField.createCheckBox(this.writer);
        PdfFormField pdfFormField = createCheckBox;
        setCheckBoxParams(pdfFormField, str, str2, z, f, f2, f3, f4);
        drawCheckBoxAppearences(pdfFormField, str2, f, f2, f3, f4);
        addFormField(createCheckBox);
        return createCheckBox;
    }

    public void setCheckBoxParams(PdfFormField pdfFormField, String str, String str2, boolean z, float f, float f2, float f3, float f4) {
        pdfFormField.setWidget(new Rectangle(f, f2, f3, f4), PdfAnnotation.HIGHLIGHT_TOGGLE);
        pdfFormField.setFieldName(str);
        if (z) {
            pdfFormField.setValueAsName(str2);
            pdfFormField.setAppearanceState(str2);
        } else {
            pdfFormField.setValueAsName("Off");
            pdfFormField.setAppearanceState("Off");
        }
        pdfFormField.setFlags(4);
        pdfFormField.setPage();
        pdfFormField.setBorderStyle(new PdfBorderDictionary(1.0f, 0));
    }

    public void drawCheckBoxAppearences(PdfFormField pdfFormField, String str, float f, float f2, float f3, float f4) {
        try {
            BaseFont createFont = BaseFont.createFont("ZapfDingbats", "Cp1252", false);
            float f5 = f4 - f2;
            float f6 = f3 - f;
            PdfAppearance createAppearance = PdfAppearance.createAppearance(this.writer, f6, f5);
            PdfAppearance pdfAppearance = (PdfAppearance) createAppearance.getDuplicate();
            pdfAppearance.setFontAndSize(createFont, f5);
            pdfAppearance.resetRGBColorFill();
            pdfFormField.setDefaultAppearanceString(pdfAppearance);
            createAppearance.drawTextField(0.0f, 0.0f, f6, f5);
            createAppearance.saveState();
            createAppearance.resetRGBColorFill();
            createAppearance.beginText();
            createAppearance.setFontAndSize(createFont, f5);
            PdfAppearance pdfAppearance2 = createAppearance;
            pdfAppearance2.showTextAligned(1, "4", f6 / 2.0f, (f5 / 2.0f) - (0.3f * f5), 0.0f);
            createAppearance.endText();
            createAppearance.restoreState();
            pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, str, createAppearance);
            PdfAppearance createAppearance2 = PdfAppearance.createAppearance(this.writer, f6, f5);
            createAppearance2.drawTextField(0.0f, 0.0f, f6, f5);
            pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", createAppearance2);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public PdfFormField getRadioGroup(String str, String str2, boolean z) {
        PdfFormField createRadioButton = PdfFormField.createRadioButton(this.writer, z);
        createRadioButton.setFieldName(str);
        createRadioButton.setValueAsName(str2);
        return createRadioButton;
    }

    public void addRadioGroup(PdfFormField pdfFormField) {
        addFormField(pdfFormField);
    }

    public PdfFormField addRadioButton(PdfFormField pdfFormField, String str, float f, float f2, float f3, float f4) {
        PdfFormField createEmpty = PdfFormField.createEmpty(this.writer);
        createEmpty.setWidget(new Rectangle(f, f2, f3, f4), PdfAnnotation.HIGHLIGHT_TOGGLE);
        if (((PdfName) pdfFormField.get(PdfName.f664V)).toString().substring(1).equals(str)) {
            createEmpty.setAppearanceState(str);
        } else {
            createEmpty.setAppearanceState("Off");
        }
        drawRadioAppearences(createEmpty, str, f, f2, f3, f4);
        pdfFormField.addKid(createEmpty);
        return createEmpty;
    }

    public void drawRadioAppearences(PdfFormField pdfFormField, String str, float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        PdfAppearance createAppearance = PdfAppearance.createAppearance(this.writer, f5, f6);
        float f7 = f5;
        float f8 = f6;
        createAppearance.drawRadioField(0.0f, 0.0f, f7, f8, true);
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, str, createAppearance);
        PdfAppearance createAppearance2 = PdfAppearance.createAppearance(this.writer, f5, f6);
        createAppearance2.drawRadioField(0.0f, 0.0f, f7, f8, false);
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", createAppearance2);
    }

    public PdfFormField addSelectList(String str, String[] strArr, String str2, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        PdfFormField createList = PdfFormField.createList(this.writer, strArr, 0);
        setChoiceParams(createList, str, str2, f2, f3, f4, f5);
        StringBuffer stringBuffer = new StringBuffer();
        for (String append : strArr) {
            stringBuffer.append(append);
            stringBuffer.append(10);
        }
        drawMultiLineOfText(createList, stringBuffer.toString(), baseFont, f, f2, f3, f4, f5);
        addFormField(createList);
        return createList;
    }

    public PdfFormField addSelectList(String str, String[][] strArr, String str2, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        PdfFormField createList = PdfFormField.createList(this.writer, strArr, 0);
        setChoiceParams(createList, str, str2, f2, f3, f4, f5);
        StringBuffer stringBuffer = new StringBuffer();
        for (String[] strArr2 : strArr) {
            stringBuffer.append(strArr2[1]);
            stringBuffer.append(10);
        }
        drawMultiLineOfText(createList, stringBuffer.toString(), baseFont, f, f2, f3, f4, f5);
        addFormField(createList);
        return createList;
    }

    public PdfFormField addComboBox(String str, String[] strArr, String str2, boolean z, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        String[] strArr2 = strArr;
        PdfFormField createCombo = PdfFormField.createCombo(this.writer, z, strArr, 0);
        setChoiceParams(createCombo, str, str2, f2, f3, f4, f5);
        drawSingleLineOfText(createCombo, str2 == null ? strArr2[0] : str2, baseFont, f, f2, f3, f4, f5);
        addFormField(createCombo);
        return createCombo;
    }

    public PdfFormField addComboBox(String str, String[][] strArr, String str2, boolean z, BaseFont baseFont, float f, float f2, float f3, float f4, float f5) {
        String str3;
        String[][] strArr2 = strArr;
        PdfFormField createCombo = PdfFormField.createCombo(this.writer, z, strArr, 0);
        setChoiceParams(createCombo, str, str2, f2, f3, f4, f5);
        int length = strArr2.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                str3 = null;
                break;
            }
            String[] strArr3 = strArr2[i];
            String str4 = str2;
            if (strArr3[0].equals(str2)) {
                str3 = strArr3[1];
                break;
            }
            i++;
        }
        if (str3 == null) {
            str3 = strArr2[0][1];
        }
        drawSingleLineOfText(createCombo, str3, baseFont, f, f2, f3, f4, f5);
        addFormField(createCombo);
        return createCombo;
    }

    public void setChoiceParams(PdfFormField pdfFormField, String str, String str2, float f, float f2, float f3, float f4) {
        pdfFormField.setWidget(new Rectangle(f, f2, f3, f4), PdfAnnotation.HIGHLIGHT_INVERT);
        if (str2 != null) {
            pdfFormField.setValueAsString(str2);
            pdfFormField.setDefaultValueAsString(str2);
        }
        pdfFormField.setFieldName(str);
        pdfFormField.setFlags(4);
        pdfFormField.setPage();
        pdfFormField.setBorderStyle(new PdfBorderDictionary(2.0f, 0));
    }

    public PdfFormField addSignature(String str, float f, float f2, float f3, float f4) {
        PdfFormField createSignature = PdfFormField.createSignature(this.writer);
        PdfFormField pdfFormField = createSignature;
        setSignatureParams(pdfFormField, str, f, f2, f3, f4);
        drawSignatureAppearences(pdfFormField, f, f2, f3, f4);
        addFormField(createSignature);
        return createSignature;
    }

    public void setSignatureParams(PdfFormField pdfFormField, String str, float f, float f2, float f3, float f4) {
        pdfFormField.setWidget(new Rectangle(f, f2, f3, f4), PdfAnnotation.HIGHLIGHT_INVERT);
        pdfFormField.setFieldName(str);
        pdfFormField.setFlags(4);
        pdfFormField.setPage();
        pdfFormField.setMKBorderColor(BaseColor.BLACK);
        pdfFormField.setMKBackgroundColor(BaseColor.WHITE);
    }

    public void drawSignatureAppearences(PdfFormField pdfFormField, float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        PdfAppearance createAppearance = PdfAppearance.createAppearance(this.writer, f5, f6);
        createAppearance.setGrayFill(1.0f);
        createAppearance.rectangle(0.0f, 0.0f, f5, f6);
        createAppearance.fill();
        createAppearance.setGrayStroke(0.0f);
        createAppearance.setLineWidth(1.0f);
        createAppearance.rectangle(0.5f, 0.5f, f5 - 0.5f, f6 - 0.5f);
        createAppearance.closePathStroke();
        createAppearance.saveState();
        createAppearance.rectangle(1.0f, 1.0f, f5 - 2.0f, f6 - 2.0f);
        createAppearance.clip();
        createAppearance.newPath();
        createAppearance.restoreState();
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, createAppearance);
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        PdfWriter.checkPdfIsoConformance(pdfWriter, 15, this);
        super.toPdf(pdfWriter, outputStream);
    }
}
