package com.itextpdf.text.pdf;

public class PdfPHeaderCell extends PdfPCell {
    public static final int BOTH = 3;
    public static final int COLUMN = 2;
    public static final int NONE = 0;
    public static final int ROW = 1;
    protected String name = null;
    protected int scope = 0;

    public PdfPHeaderCell() {
        this.role = PdfName.f650TH;
    }

    public PdfPHeaderCell(PdfPHeaderCell pdfPHeaderCell) {
        super((PdfPCell) pdfPHeaderCell);
        this.role = pdfPHeaderCell.role;
        this.scope = pdfPHeaderCell.scope;
        this.name = pdfPHeaderCell.getName();
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public PdfName getRole() {
        return this.role;
    }

    public void setRole(PdfName pdfName) {
        this.role = pdfName;
    }

    public void setScope(int i) {
        this.scope = i;
    }

    public int getScope() {
        return this.scope;
    }
}
