package com.itextpdf.text.pdf;

import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.util.ArrayList;
import java.util.HashMap;

public class PdfPTableBody implements IAccessibleElement {
    protected HashMap<PdfName, PdfObject> accessibleAttributes = null;

    /* renamed from: id */
    protected AccessibleElementId f683id = new AccessibleElementId();
    protected PdfName role = PdfName.TBODY;
    protected ArrayList<PdfPRow> rows = null;

    public boolean isInline() {
        return false;
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
        return this.f683id;
    }

    public void setId(AccessibleElementId accessibleElementId) {
        this.f683id = accessibleElementId;
    }
}
