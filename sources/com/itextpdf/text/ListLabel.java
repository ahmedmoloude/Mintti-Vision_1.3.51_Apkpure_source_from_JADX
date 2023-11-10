package com.itextpdf.text;

import com.itextpdf.text.pdf.PdfName;

public class ListLabel extends ListBody {
    protected float indentation = 0.0f;
    protected PdfName role = PdfName.LBL;

    @Deprecated
    public boolean getTagLabelContent() {
        return false;
    }

    public boolean isInline() {
        return true;
    }

    @Deprecated
    public void setTagLabelContent(boolean z) {
    }

    protected ListLabel(ListItem listItem) {
        super(listItem);
    }

    public PdfName getRole() {
        return this.role;
    }

    public void setRole(PdfName pdfName) {
        this.role = pdfName;
    }

    public float getIndentation() {
        return this.indentation;
    }

    public void setIndentation(float f) {
        this.indentation = f;
    }
}
