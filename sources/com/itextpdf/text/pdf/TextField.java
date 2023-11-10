package com.itextpdf.text.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TextField extends BaseField {
    private String[] choiceExports;
    private ArrayList<Integer> choiceSelections = new ArrayList<>();
    private String[] choices;
    private String defaultText;
    private BaseFont extensionFont;
    private float extraMarginLeft;
    private float extraMarginTop;
    private ArrayList<BaseFont> substitutionFonts;
    private int topFirst;
    private int visibleTopChoice = -1;

    public TextField(PdfWriter pdfWriter, Rectangle rectangle, String str) {
        super(pdfWriter, rectangle, str);
    }

    private static boolean checkRTL(String str) {
        if (!(str == null || str.length() == 0)) {
            char[] charArray = str.toCharArray();
            for (char c : charArray) {
                if (c >= 1424 && c < 1920) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void changeFontSize(Phrase phrase, float f) {
        for (int i = 0; i < phrase.size(); i++) {
            ((Chunk) phrase.get(i)).getFont().setSize(f);
        }
    }

    private Phrase composePhrase(String str, BaseFont baseFont, BaseColor baseColor, float f) {
        ArrayList<BaseFont> arrayList;
        if (this.extensionFont == null && ((arrayList = this.substitutionFonts) == null || arrayList.isEmpty())) {
            return new Phrase(new Chunk(str, new Font(baseFont, f, 0, baseColor)));
        }
        FontSelector fontSelector = new FontSelector();
        fontSelector.addFont(new Font(baseFont, f, 0, baseColor));
        BaseFont baseFont2 = this.extensionFont;
        if (baseFont2 != null) {
            fontSelector.addFont(new Font(baseFont2, f, 0, baseColor));
        }
        if (this.substitutionFonts != null) {
            for (int i = 0; i < this.substitutionFonts.size(); i++) {
                fontSelector.addFont(new Font(this.substitutionFonts.get(i), f, 0, baseColor));
            }
        }
        return fontSelector.process(str);
    }

    public static String removeCRLF(String str) {
        if (str.indexOf(10) < 0 && str.indexOf(13) < 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer(charArray.length);
        int i = 0;
        while (i < charArray.length) {
            char c = charArray[i];
            if (c == 10) {
                stringBuffer.append(' ');
            } else if (c == 13) {
                stringBuffer.append(' ');
                if (i < charArray.length - 1) {
                    int i2 = i + 1;
                    if (charArray[i2] == 10) {
                        i = i2;
                    }
                }
            } else {
                stringBuffer.append(c);
            }
            i++;
        }
        return stringBuffer.toString();
    }

    public static String obfuscatePassword(String str) {
        char[] cArr = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            cArr[i] = '*';
        }
        return new String(cArr);
    }

    public PdfAppearance getAppearance() throws IOException, DocumentException {
        String str;
        float f;
        int i;
        float min;
        PdfAppearance borderAppearance = getBorderAppearance();
        borderAppearance.beginVariableText();
        if (this.text == null || this.text.length() == 0) {
            borderAppearance.endVariableText();
            return borderAppearance;
        }
        boolean z = this.borderStyle == 2 || this.borderStyle == 3;
        float height = (this.box.getHeight() - (this.borderWidth * 2.0f)) - this.extraMarginTop;
        float f2 = this.borderWidth;
        if (z) {
            height -= this.borderWidth * 2.0f;
            f2 *= 2.0f;
        }
        float max = Math.max(f2, 1.0f);
        float min2 = Math.min(f2, max);
        borderAppearance.saveState();
        float f3 = min2 * 2.0f;
        borderAppearance.rectangle(min2, min2, this.box.getWidth() - f3, this.box.getHeight() - f3);
        borderAppearance.clip();
        borderAppearance.newPath();
        if ((this.options & 8192) != 0) {
            str = obfuscatePassword(this.text);
        } else if ((this.options & 4096) == 0) {
            str = removeCRLF(this.text);
        } else {
            str = this.text;
        }
        BaseFont realFont = getRealFont();
        BaseColor baseColor = this.textColor == null ? GrayColor.GRAYBLACK : this.textColor;
        int i2 = checkRTL(str) ? 2 : 1;
        float f4 = this.fontSize;
        Phrase composePhrase = composePhrase(str, realFont, baseColor, f4);
        if ((this.options & 4096) != 0) {
            float width = (this.box.getWidth() - (max * 4.0f)) - this.extraMarginLeft;
            float fontDescriptor = realFont.getFontDescriptor(8, 1.0f) - realFont.getFontDescriptor(6, 1.0f);
            ColumnText columnText = new ColumnText((PdfContentByte) null);
            if (f4 == 0.0f) {
                f4 = height / fontDescriptor;
                if (f4 > 4.0f) {
                    if (f4 > 12.0f) {
                        f4 = 12.0f;
                    }
                    float max2 = Math.max((f4 - 4.0f) / 10.0f, 0.2f);
                    columnText.setSimpleColumn(0.0f, -height, width, 0.0f);
                    columnText.setAlignment(this.alignment);
                    columnText.setRunDirection(i2);
                    while (f4 > 4.0f) {
                        columnText.setYLine(0.0f);
                        changeFontSize(composePhrase, f4);
                        columnText.setText(composePhrase);
                        columnText.setLeading(fontDescriptor * f4);
                        if ((columnText.mo20966go(true) & 2) == 0) {
                            break;
                        }
                        f4 -= max2;
                    }
                }
                if (f4 < 4.0f) {
                    f4 = 4.0f;
                }
            }
            changeFontSize(composePhrase, f4);
            columnText.setCanvas(borderAppearance);
            float f5 = fontDescriptor * f4;
            float fontDescriptor2 = (height + max) - realFont.getFontDescriptor(8, f4);
            float f6 = max * 2.0f;
            columnText.setSimpleColumn(this.extraMarginLeft + f6, -20000.0f, this.box.getWidth() - f6, fontDescriptor2 + f5);
            columnText.setLeading(f5);
            columnText.setAlignment(this.alignment);
            columnText.setRunDirection(i2);
            columnText.setText(composePhrase);
            columnText.mo20965go();
        } else {
            if (f4 == 0.0f) {
                float fontDescriptor3 = height / (realFont.getFontDescriptor(7, 1.0f) - realFont.getFontDescriptor(6, 1.0f));
                changeFontSize(composePhrase, 1.0f);
                float width2 = ColumnText.getWidth(composePhrase, i2, 0);
                if (width2 == 0.0f) {
                    min = fontDescriptor3;
                } else {
                    min = Math.min(fontDescriptor3, ((this.box.getWidth() - this.extraMarginLeft) - (max * 4.0f)) / width2);
                }
                if (f4 < 4.0f) {
                    f4 = 4.0f;
                }
            }
            changeFontSize(composePhrase, f4);
            float height2 = (((this.box.getHeight() - f3) - realFont.getFontDescriptor(1, f4)) / 2.0f) + min2;
            if (height2 < min2) {
                height2 = min2;
            }
            if (height2 - min2 < (-realFont.getFontDescriptor(3, f4))) {
                height2 = Math.min((-realFont.getFontDescriptor(3, f4)) + min2, Math.max(height2, (this.box.getHeight() - min2) - realFont.getFontDescriptor(1, f4)));
            }
            if ((this.options & 16777216) == 0 || this.maxCharacterLength <= 0) {
                int i3 = this.alignment;
                if (i3 != 1) {
                    f = i3 != 2 ? this.extraMarginLeft + (max * 2.0f) : (this.extraMarginLeft + this.box.getWidth()) - (max * 2.0f);
                } else {
                    f = this.extraMarginLeft + (this.box.getWidth() / 2.0f);
                }
                ColumnText.showTextAligned(borderAppearance, this.alignment, composePhrase, f, height2 - this.extraMarginTop, 0.0f, i2, 0);
            } else {
                int min3 = Math.min(this.maxCharacterLength, str.length());
                if (this.alignment == 2) {
                    i = this.maxCharacterLength - min3;
                } else {
                    i = this.alignment == 1 ? (this.maxCharacterLength - min3) / 2 : 0;
                }
                float width3 = (this.box.getWidth() - this.extraMarginLeft) / ((float) this.maxCharacterLength);
                float f7 = (width3 / 2.0f) + (((float) i) * width3);
                if (this.textColor == null) {
                    borderAppearance.setGrayFill(0.0f);
                } else {
                    borderAppearance.setColorFill(this.textColor);
                }
                borderAppearance.beginText();
                for (int i4 = 0; i4 < composePhrase.size(); i4++) {
                    Chunk chunk = (Chunk) composePhrase.get(i4);
                    BaseFont baseFont = chunk.getFont().getBaseFont();
                    borderAppearance.setFontAndSize(baseFont, f4);
                    StringBuffer append = chunk.append("");
                    int i5 = 0;
                    while (i5 < append.length()) {
                        int i6 = i5 + 1;
                        String substring = append.substring(i5, i6);
                        borderAppearance.setTextMatrix((this.extraMarginLeft + f7) - (baseFont.getWidthPoint(substring, f4) / 2.0f), height2 - this.extraMarginTop);
                        borderAppearance.showText(substring);
                        f7 += width3;
                        i5 = i6;
                    }
                }
                borderAppearance.endText();
            }
        }
        borderAppearance.restoreState();
        borderAppearance.endVariableText();
        return borderAppearance;
    }

    /* access modifiers changed from: package-private */
    public PdfAppearance getListAppearance() throws IOException, DocumentException {
        PdfAppearance borderAppearance = getBorderAppearance();
        String[] strArr = this.choices;
        if (!(strArr == null || strArr.length == 0)) {
            borderAppearance.beginVariableText();
            int topChoice = getTopChoice();
            BaseFont realFont = getRealFont();
            float f = this.fontSize;
            float f2 = f == 0.0f ? 12.0f : f;
            boolean z = this.borderStyle == 2 || this.borderStyle == 3;
            float height = this.box.getHeight() - (this.borderWidth * 2.0f);
            float f3 = this.borderWidth;
            if (z) {
                height -= this.borderWidth * 2.0f;
                f3 *= 2.0f;
            }
            float fontDescriptor = realFont.getFontDescriptor(8, f2) - realFont.getFontDescriptor(6, f2);
            int i = ((int) (height / fontDescriptor)) + 1 + topChoice;
            String[] strArr2 = this.choices;
            if (i > strArr2.length) {
                i = strArr2.length;
            }
            int i2 = i;
            this.topFirst = topChoice;
            borderAppearance.saveState();
            float f4 = f3 * 2.0f;
            borderAppearance.rectangle(f3, f3, this.box.getWidth() - f4, this.box.getHeight() - f4);
            borderAppearance.clip();
            borderAppearance.newPath();
            BaseColor baseColor = this.textColor == null ? GrayColor.GRAYBLACK : this.textColor;
            borderAppearance.setColorFill(new BaseColor(10, 36, 106));
            for (int i3 = 0; i3 < this.choiceSelections.size(); i3++) {
                int intValue = this.choiceSelections.get(i3).intValue();
                if (intValue >= topChoice && intValue <= i2) {
                    borderAppearance.rectangle(f3, (f3 + height) - (((float) ((intValue - topChoice) + 1)) * fontDescriptor), this.box.getWidth() - f4, fontDescriptor);
                    borderAppearance.fill();
                }
            }
            int i4 = topChoice;
            float fontDescriptor2 = (f3 + height) - realFont.getFontDescriptor(8, f2);
            while (i4 < i2) {
                String str = this.choices[i4];
                ColumnText.showTextAligned(borderAppearance, 0, composePhrase(removeCRLF(str), realFont, this.choiceSelections.contains(Integer.valueOf(i4)) ? GrayColor.GRAYWHITE : baseColor, f2), f4, fontDescriptor2, 0.0f, checkRTL(str) ? 2 : 1, 0);
                i4++;
                fontDescriptor2 -= fontDescriptor;
            }
            borderAppearance.restoreState();
            borderAppearance.endVariableText();
        }
        return borderAppearance;
    }

    public PdfFormField getTextField() throws IOException, DocumentException {
        if (this.maxCharacterLength <= 0) {
            this.options &= -16777217;
        }
        if ((this.options & 16777216) != 0) {
            this.options &= -4097;
        }
        PdfFormField createTextField = PdfFormField.createTextField(this.writer, false, false, this.maxCharacterLength);
        createTextField.setWidget(this.box, PdfAnnotation.HIGHLIGHT_INVERT);
        int i = this.alignment;
        if (i == 1) {
            createTextField.setQuadding(1);
        } else if (i == 2) {
            createTextField.setQuadding(2);
        }
        if (this.rotation != 0) {
            createTextField.setMKRotation(this.rotation);
        }
        if (this.fieldName != null) {
            createTextField.setFieldName(this.fieldName);
            if (!"".equals(this.text)) {
                createTextField.setValueAsString(this.text);
            }
            String str = this.defaultText;
            if (str != null) {
                createTextField.setDefaultValueAsString(str);
            }
            if ((this.options & 1) != 0) {
                createTextField.setFieldFlags(1);
            }
            if ((this.options & 2) != 0) {
                createTextField.setFieldFlags(2);
            }
            if ((this.options & 4096) != 0) {
                createTextField.setFieldFlags(4096);
            }
            if ((this.options & 8388608) != 0) {
                createTextField.setFieldFlags(8388608);
            }
            if ((this.options & 8192) != 0) {
                createTextField.setFieldFlags(8192);
            }
            if ((this.options & 1048576) != 0) {
                createTextField.setFieldFlags(1048576);
            }
            if ((this.options & 4194304) != 0) {
                createTextField.setFieldFlags(4194304);
            }
            if ((this.options & 16777216) != 0) {
                createTextField.setFieldFlags(16777216);
            }
        }
        createTextField.setBorderStyle(new PdfBorderDictionary(this.borderWidth, this.borderStyle, new PdfDashPattern(3.0f)));
        PdfAppearance appearance = getAppearance();
        createTextField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, appearance);
        PdfAppearance pdfAppearance = (PdfAppearance) appearance.getDuplicate();
        pdfAppearance.setFontAndSize(getRealFont(), this.fontSize);
        if (this.textColor == null) {
            pdfAppearance.setGrayFill(0.0f);
        } else {
            pdfAppearance.setColorFill(this.textColor);
        }
        createTextField.setDefaultAppearanceString(pdfAppearance);
        if (this.borderColor != null) {
            createTextField.setMKBorderColor(this.borderColor);
        }
        if (this.backgroundColor != null) {
            createTextField.setMKBackgroundColor(this.backgroundColor);
        }
        int i2 = this.visibility;
        if (i2 == 1) {
            createTextField.setFlags(6);
        } else if (i2 != 2) {
            if (i2 != 3) {
                createTextField.setFlags(4);
            } else {
                createTextField.setFlags(36);
            }
        }
        return createTextField;
    }

    public PdfFormField getComboField() throws IOException, DocumentException {
        return getChoiceField(false);
    }

    public PdfFormField getListField() throws IOException, DocumentException {
        return getChoiceField(true);
    }

    private int getTopChoice() {
        Integer num;
        ArrayList<Integer> arrayList = this.choiceSelections;
        if (arrayList == null || arrayList.size() == 0 || (num = this.choiceSelections.get(0)) == null || this.choices == null) {
            return 0;
        }
        int i = this.visibleTopChoice;
        if (i != -1) {
            return i;
        }
        return Math.max(0, Math.min(num.intValue(), this.choices.length));
    }

    /* access modifiers changed from: protected */
    public PdfFormField getChoiceField(boolean z) throws IOException, DocumentException {
        String[][] strArr;
        PdfFormField pdfFormField;
        PdfAppearance pdfAppearance;
        PdfFormField createCombo;
        this.options &= -16781313;
        String[] strArr2 = this.choices;
        if (strArr2 == null) {
            strArr2 = new String[0];
        }
        int topChoice = getTopChoice();
        if (strArr2.length > 0 && topChoice >= 0) {
            this.text = strArr2[topChoice];
        }
        if (this.text == null) {
            this.text = "";
        }
        String[][] strArr3 = null;
        if (this.choiceExports == null) {
            if (z) {
                pdfFormField = PdfFormField.createList(this.writer, strArr2, topChoice);
            } else {
                pdfFormField = PdfFormField.createCombo(this.writer, (262144 & this.options) != 0, strArr2, topChoice);
            }
            strArr = strArr3;
        } else {
            int length = strArr2.length;
            int[] iArr = new int[2];
            iArr[1] = 2;
            iArr[0] = length;
            String[][] strArr4 = (String[][]) Array.newInstance(String.class, iArr);
            for (int i = 0; i < strArr4.length; i++) {
                String[] strArr5 = strArr4[i];
                String[] strArr6 = strArr4[i];
                String str = strArr2[i];
                strArr6[1] = str;
                strArr5[0] = str;
            }
            int min = Math.min(strArr2.length, this.choiceExports.length);
            for (int i2 = 0; i2 < min; i2++) {
                String[] strArr7 = this.choiceExports;
                if (strArr7[i2] != null) {
                    strArr4[i2][0] = strArr7[i2];
                }
            }
            if (z) {
                createCombo = PdfFormField.createList(this.writer, strArr4, topChoice);
            } else {
                createCombo = PdfFormField.createCombo(this.writer, (262144 & this.options) != 0, strArr4, topChoice);
            }
            PdfFormField pdfFormField2 = createCombo;
            strArr = strArr4;
            pdfFormField = pdfFormField2;
        }
        pdfFormField.setWidget(this.box, PdfAnnotation.HIGHLIGHT_INVERT);
        if (this.rotation != 0) {
            pdfFormField.setMKRotation(this.rotation);
        }
        if (this.fieldName != null) {
            pdfFormField.setFieldName(this.fieldName);
            if (strArr2.length > 0) {
                if (strArr != null) {
                    if (this.choiceSelections.size() < 2) {
                        pdfFormField.setValueAsString(strArr[topChoice][0]);
                        pdfFormField.setDefaultValueAsString(strArr[topChoice][0]);
                    } else {
                        writeMultipleValues(pdfFormField, strArr);
                    }
                } else if (this.choiceSelections.size() < 2) {
                    pdfFormField.setValueAsString(this.text);
                    pdfFormField.setDefaultValueAsString(this.text);
                } else {
                    writeMultipleValues(pdfFormField, strArr3);
                }
            }
            if ((this.options & 1) != 0) {
                pdfFormField.setFieldFlags(1);
            }
            if ((this.options & 2) != 0) {
                pdfFormField.setFieldFlags(2);
            }
            if ((this.options & 4194304) != 0) {
                pdfFormField.setFieldFlags(4194304);
            }
            if ((this.options & 2097152) != 0) {
                pdfFormField.setFieldFlags(2097152);
            }
        }
        pdfFormField.setBorderStyle(new PdfBorderDictionary(this.borderWidth, this.borderStyle, new PdfDashPattern(3.0f)));
        if (z) {
            pdfAppearance = getListAppearance();
            if (this.topFirst > 0) {
                pdfFormField.put(PdfName.f651TI, new PdfNumber(this.topFirst));
            }
        } else {
            pdfAppearance = getAppearance();
        }
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, pdfAppearance);
        PdfAppearance pdfAppearance2 = (PdfAppearance) pdfAppearance.getDuplicate();
        pdfAppearance2.setFontAndSize(getRealFont(), this.fontSize);
        if (this.textColor == null) {
            pdfAppearance2.setGrayFill(0.0f);
        } else {
            pdfAppearance2.setColorFill(this.textColor);
        }
        pdfFormField.setDefaultAppearanceString(pdfAppearance2);
        if (this.borderColor != null) {
            pdfFormField.setMKBorderColor(this.borderColor);
        }
        if (this.backgroundColor != null) {
            pdfFormField.setMKBackgroundColor(this.backgroundColor);
        }
        int i3 = this.visibility;
        if (i3 == 1) {
            pdfFormField.setFlags(6);
        } else if (i3 != 2) {
            if (i3 != 3) {
                pdfFormField.setFlags(4);
            } else {
                pdfFormField.setFlags(36);
            }
        }
        return pdfFormField;
    }

    private void writeMultipleValues(PdfFormField pdfFormField, String[][] strArr) {
        PdfArray pdfArray = new PdfArray();
        PdfArray pdfArray2 = new PdfArray();
        for (int i = 0; i < this.choiceSelections.size(); i++) {
            int intValue = this.choiceSelections.get(i).intValue();
            pdfArray.add((PdfObject) new PdfNumber(intValue));
            if (strArr != null) {
                pdfArray2.add((PdfObject) new PdfString(strArr[intValue][0]));
            } else {
                String[] strArr2 = this.choices;
                if (strArr2 != null) {
                    pdfArray2.add((PdfObject) new PdfString(strArr2[intValue]));
                }
            }
        }
        pdfFormField.put(PdfName.f664V, pdfArray2);
        pdfFormField.put(PdfName.f598I, pdfArray);
    }

    public String getDefaultText() {
        return this.defaultText;
    }

    public void setDefaultText(String str) {
        this.defaultText = str;
    }

    public String[] getChoices() {
        return this.choices;
    }

    public void setChoices(String[] strArr) {
        this.choices = strArr;
    }

    public String[] getChoiceExports() {
        return this.choiceExports;
    }

    public void setChoiceExports(String[] strArr) {
        this.choiceExports = strArr;
    }

    public int getChoiceSelection() {
        return getTopChoice();
    }

    public ArrayList<Integer> getChoiceSelections() {
        return this.choiceSelections;
    }

    public void setVisibleTopChoice(int i) {
        String[] strArr;
        if (i >= 0 && (strArr = this.choices) != null && i < strArr.length) {
            this.visibleTopChoice = i;
        }
    }

    public int getVisibleTopChoice() {
        return this.visibleTopChoice;
    }

    public void setChoiceSelection(int i) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        this.choiceSelections = arrayList;
        arrayList.add(Integer.valueOf(i));
    }

    public void addChoiceSelection(int i) {
        if ((this.options & 2097152) != 0) {
            this.choiceSelections.add(Integer.valueOf(i));
        }
    }

    public void setChoiceSelections(ArrayList<Integer> arrayList) {
        if (arrayList != null) {
            ArrayList<Integer> arrayList2 = new ArrayList<>(arrayList);
            this.choiceSelections = arrayList2;
            if (arrayList2.size() > 1 && (this.options & 2097152) == 0) {
                while (this.choiceSelections.size() > 1) {
                    this.choiceSelections.remove(1);
                }
                return;
            }
            return;
        }
        this.choiceSelections.clear();
    }

    /* access modifiers changed from: package-private */
    public int getTopFirst() {
        return this.topFirst;
    }

    public void setExtraMargin(float f, float f2) {
        this.extraMarginLeft = f;
        this.extraMarginTop = f2;
    }

    public ArrayList<BaseFont> getSubstitutionFonts() {
        return this.substitutionFonts;
    }

    public void setSubstitutionFonts(ArrayList<BaseFont> arrayList) {
        this.substitutionFonts = arrayList;
    }

    public BaseFont getExtensionFont() {
        return this.extensionFont;
    }

    public void setExtensionFont(BaseFont baseFont) {
        this.extensionFont = baseFont;
    }
}
