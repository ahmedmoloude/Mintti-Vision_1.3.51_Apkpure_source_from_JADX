package com.itextpdf.text;

import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.util.HashMap;

public class ListBody implements IAccessibleElement {
    protected HashMap<PdfName, PdfObject> accessibleAttributes = null;

    /* renamed from: id */
    private AccessibleElementId f452id = null;
    protected ListItem parentItem = null;
    protected PdfName role = PdfName.LBODY;

    public boolean isInline() {
        return false;
    }

    protected ListBody(ListItem listItem) {
        this.parentItem = listItem;
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
        if (this.f452id == null) {
            this.f452id = new AccessibleElementId();
        }
        return this.f452id;
    }

    public void setId(AccessibleElementId accessibleElementId) {
        this.f452id = accessibleElementId;
    }
}
