package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.html.HtmlTags;
import java.io.IOException;

public class RadioCheckField extends BaseField {
    public static final int TYPE_CHECK = 1;
    public static final int TYPE_CIRCLE = 2;
    public static final int TYPE_CROSS = 3;
    public static final int TYPE_DIAMOND = 4;
    public static final int TYPE_SQUARE = 5;
    public static final int TYPE_STAR = 6;
    protected static String[] typeChars = {"4", "l", "8", HtmlTags.f494U, "n", "H"};
    protected int checkType;
    private boolean checked;
    private String onValue;

    public RadioCheckField(PdfWriter pdfWriter, Rectangle rectangle, String str, String str2) {
        super(pdfWriter, rectangle, str);
        setOnValue(str2);
        setCheckType(2);
    }

    public int getCheckType() {
        return this.checkType;
    }

    public void setCheckType(int i) {
        if (i < 1 || i > 6) {
            i = 2;
        }
        this.checkType = i;
        setText(typeChars[i - 1]);
        try {
            setFont(BaseFont.createFont("ZapfDingbats", "Cp1252", false));
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public String getOnValue() {
        return this.onValue;
    }

    public void setOnValue(String str) {
        this.onValue = str;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean z) {
        this.checked = z;
    }

    public PdfAppearance getAppearance(boolean z, boolean z2) throws IOException, DocumentException {
        if (z && this.checkType == 2) {
            return getAppearanceRadioCircle(z2);
        }
        PdfAppearance borderAppearance = getBorderAppearance();
        if (!z2) {
            return borderAppearance;
        }
        BaseFont realFont = getRealFont();
        boolean z3 = this.borderStyle == 2 || this.borderStyle == 3;
        float height = this.box.getHeight() - (this.borderWidth * 2.0f);
        float f = this.borderWidth;
        if (z3) {
            height -= this.borderWidth * 2.0f;
            f *= 2.0f;
        }
        float min = Math.min(f, Math.max(z3 ? this.borderWidth * 2.0f : this.borderWidth, 1.0f));
        float f2 = min * 2.0f;
        float width = this.box.getWidth() - f2;
        float height2 = this.box.getHeight() - f2;
        float f3 = this.fontSize;
        if (f3 == 0.0f) {
            float widthPoint = realFont.getWidthPoint(this.text, 1.0f);
            f3 = Math.min(widthPoint == 0.0f ? 12.0f : width / widthPoint, height / realFont.getFontDescriptor(1, 1.0f));
        }
        borderAppearance.saveState();
        borderAppearance.rectangle(min, min, width, height2);
        borderAppearance.clip();
        borderAppearance.newPath();
        if (this.textColor == null) {
            borderAppearance.resetGrayFill();
        } else {
            borderAppearance.setColorFill(this.textColor);
        }
        borderAppearance.beginText();
        borderAppearance.setFontAndSize(realFont, f3);
        borderAppearance.setTextMatrix((this.box.getWidth() - realFont.getWidthPoint(this.text, f3)) / 2.0f, (this.box.getHeight() - realFont.getAscentPoint(this.text, f3)) / 2.0f);
        borderAppearance.showText(this.text);
        borderAppearance.endText();
        borderAppearance.restoreState();
        return borderAppearance;
    }

    public PdfAppearance getAppearanceRadioCircle(boolean z) {
        PdfAppearance createAppearance = PdfAppearance.createAppearance(this.writer, this.box.getWidth(), this.box.getHeight());
        int i = this.rotation;
        if (i == 90) {
            createAppearance.setMatrix(0.0f, 1.0f, -1.0f, 0.0f, this.box.getHeight(), 0.0f);
        } else if (i == 180) {
            createAppearance.setMatrix(-1.0f, 0.0f, 0.0f, -1.0f, this.box.getWidth(), this.box.getHeight());
        } else if (i == 270) {
            createAppearance.setMatrix(0.0f, -1.0f, 1.0f, 0.0f, 0.0f, this.box.getWidth());
        }
        Rectangle rectangle = new Rectangle(createAppearance.getBoundingBox());
        float width = rectangle.getWidth() / 2.0f;
        float height = rectangle.getHeight() / 2.0f;
        float min = (Math.min(rectangle.getWidth(), rectangle.getHeight()) - this.borderWidth) / 2.0f;
        if (min <= 0.0f) {
            return createAppearance;
        }
        if (this.backgroundColor != null) {
            createAppearance.setColorFill(this.backgroundColor);
            createAppearance.circle(width, height, (this.borderWidth / 2.0f) + min);
            createAppearance.fill();
        }
        if (this.borderWidth > 0.0f && this.borderColor != null) {
            createAppearance.setLineWidth(this.borderWidth);
            createAppearance.setColorStroke(this.borderColor);
            createAppearance.circle(width, height, min);
            createAppearance.stroke();
        }
        if (z) {
            if (this.textColor == null) {
                createAppearance.resetGrayFill();
            } else {
                createAppearance.setColorFill(this.textColor);
            }
            createAppearance.circle(width, height, min / 2.0f);
            createAppearance.fill();
        }
        return createAppearance;
    }

    public PdfFormField getRadioGroup(boolean z, boolean z2) {
        PdfFormField createRadioButton = PdfFormField.createRadioButton(this.writer, z);
        if (z2) {
            createRadioButton.setFieldFlags(33554432);
        }
        createRadioButton.setFieldName(this.fieldName);
        if ((this.options & 1) != 0) {
            createRadioButton.setFieldFlags(1);
        }
        if ((this.options & 2) != 0) {
            createRadioButton.setFieldFlags(2);
        }
        createRadioButton.setValueAsName(this.checked ? this.onValue : "Off");
        return createRadioButton;
    }

    public PdfFormField getRadioField() throws IOException, DocumentException {
        return getField(true);
    }

    public PdfFormField getCheckField() throws IOException, DocumentException {
        return getField(false);
    }

    /* access modifiers changed from: protected */
    public PdfFormField getField(boolean z) throws IOException, DocumentException {
        PdfFormField pdfFormField;
        if (z) {
            pdfFormField = PdfFormField.createEmpty(this.writer);
        } else {
            pdfFormField = PdfFormField.createCheckBox(this.writer);
        }
        pdfFormField.setWidget(this.box, PdfAnnotation.HIGHLIGHT_INVERT);
        String str = "Off";
        if (!z) {
            if ("Yes".equals(this.onValue)) {
                pdfFormField.setFieldName(this.fieldName);
                if ((this.options & 1) != 0) {
                    pdfFormField.setFieldFlags(1);
                }
                if ((this.options & 2) != 0) {
                    pdfFormField.setFieldFlags(2);
                }
                pdfFormField.setValueAsName(this.checked ? this.onValue : str);
                setCheckType(this.checkType);
            } else {
                throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.valid.name.for.checkbox.appearance", this.onValue));
            }
        }
        if (this.text != null) {
            pdfFormField.setMKNormalCaption(this.text);
        }
        if (this.rotation != 0) {
            pdfFormField.setMKRotation(this.rotation);
        }
        pdfFormField.setBorderStyle(new PdfBorderDictionary(this.borderWidth, this.borderStyle, new PdfDashPattern(3.0f)));
        PdfAppearance appearance = getAppearance(z, true);
        PdfAppearance appearance2 = getAppearance(z, false);
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, this.onValue, appearance);
        pdfFormField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, str, appearance2);
        if (this.checked) {
            str = this.onValue;
        }
        pdfFormField.setAppearanceState(str);
        PdfAppearance pdfAppearance = (PdfAppearance) appearance.getDuplicate();
        if (getRealFont() != null) {
            pdfAppearance.setFontAndSize(getRealFont(), this.fontSize);
        }
        if (this.textColor == null) {
            pdfAppearance.setGrayFill(0.0f);
        } else {
            pdfAppearance.setColorFill(this.textColor);
        }
        pdfFormField.setDefaultAppearanceString(pdfAppearance);
        if (this.borderColor != null) {
            pdfFormField.setMKBorderColor(this.borderColor);
        }
        if (this.backgroundColor != null) {
            pdfFormField.setMKBackgroundColor(this.backgroundColor);
        }
        int i = this.visibility;
        if (i == 1) {
            pdfFormField.setFlags(6);
        } else if (i != 2) {
            if (i != 3) {
                pdfFormField.setFlags(4);
            } else {
                pdfFormField.setFlags(36);
            }
        }
        return pdfFormField;
    }
}
