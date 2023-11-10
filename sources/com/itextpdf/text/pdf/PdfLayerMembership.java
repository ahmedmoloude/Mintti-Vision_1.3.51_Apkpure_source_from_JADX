package com.itextpdf.text.pdf;

import java.util.Collection;
import java.util.HashSet;

public class PdfLayerMembership extends PdfDictionary implements PdfOCG {
    public static final PdfName ALLOFF = new PdfName("AllOff");
    public static final PdfName ALLON = new PdfName("AllOn");
    public static final PdfName ANYOFF = new PdfName("AnyOff");
    public static final PdfName ANYON = new PdfName("AnyOn");
    HashSet<PdfLayer> layers = new HashSet<>();
    PdfArray members = new PdfArray();
    PdfIndirectReference ref;

    public PdfObject getPdfObject() {
        return this;
    }

    public PdfLayerMembership(PdfWriter pdfWriter) {
        super(PdfName.OCMD);
        put(PdfName.OCGS, this.members);
        this.ref = pdfWriter.getPdfIndirectReference();
    }

    public PdfIndirectReference getRef() {
        return this.ref;
    }

    public void addMember(PdfLayer pdfLayer) {
        if (!this.layers.contains(pdfLayer)) {
            this.members.add((PdfObject) pdfLayer.getRef());
            this.layers.add(pdfLayer);
        }
    }

    public Collection<PdfLayer> getLayers() {
        return this.layers;
    }

    public void setVisibilityPolicy(PdfName pdfName) {
        put(PdfName.f623P, pdfName);
    }

    public void setVisibilityExpression(PdfVisibilityExpression pdfVisibilityExpression) {
        put(PdfName.f666VE, pdfVisibilityExpression);
    }
}
