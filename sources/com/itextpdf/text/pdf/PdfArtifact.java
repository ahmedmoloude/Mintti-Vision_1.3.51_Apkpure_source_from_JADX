package com.itextpdf.text.pdf;

import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class PdfArtifact implements IAccessibleElement {
    private static final HashSet<String> allowedArtifactTypes = new HashSet<>(Arrays.asList(new String[]{"Pagination", "Layout", "Page", "Background"}));
    protected HashMap<PdfName, PdfObject> accessibleAttributes = null;

    /* renamed from: id */
    protected AccessibleElementId f536id = new AccessibleElementId();
    protected PdfName role = PdfName.ARTIFACT;

    public enum ArtifactType {
        PAGINATION,
        LAYOUT,
        PAGE,
        BACKGROUND
    }

    public boolean isInline() {
        return true;
    }

    public void setRole(PdfName pdfName) {
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

    public AccessibleElementId getId() {
        return this.f536id;
    }

    public void setId(AccessibleElementId accessibleElementId) {
        this.f536id = accessibleElementId;
    }

    public PdfString getType() {
        HashMap<PdfName, PdfObject> hashMap = this.accessibleAttributes;
        if (hashMap == null) {
            return null;
        }
        return (PdfString) hashMap.get(PdfName.TYPE);
    }

    public void setType(PdfString pdfString) {
        if (allowedArtifactTypes.contains(pdfString.toString())) {
            setAccessibleAttribute(PdfName.TYPE, pdfString);
        } else {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.artifact.type.1.is.invalid", pdfString));
        }
    }

    /* renamed from: com.itextpdf.text.pdf.PdfArtifact$1 */
    static /* synthetic */ class C15181 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$pdf$PdfArtifact$ArtifactType;

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        static {
            /*
                com.itextpdf.text.pdf.PdfArtifact$ArtifactType[] r0 = com.itextpdf.text.pdf.PdfArtifact.ArtifactType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$itextpdf$text$pdf$PdfArtifact$ArtifactType = r0
                com.itextpdf.text.pdf.PdfArtifact$ArtifactType r1 = com.itextpdf.text.pdf.PdfArtifact.ArtifactType.BACKGROUND     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PdfArtifact$ArtifactType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.PdfArtifact$ArtifactType r1 = com.itextpdf.text.pdf.PdfArtifact.ArtifactType.LAYOUT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PdfArtifact$ArtifactType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.pdf.PdfArtifact$ArtifactType r1 = com.itextpdf.text.pdf.PdfArtifact.ArtifactType.PAGE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PdfArtifact$ArtifactType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.itextpdf.text.pdf.PdfArtifact$ArtifactType r1 = com.itextpdf.text.pdf.PdfArtifact.ArtifactType.PAGINATION     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfArtifact.C15181.<clinit>():void");
        }
    }

    public void setType(ArtifactType artifactType) {
        PdfString pdfString;
        int i = C15181.$SwitchMap$com$itextpdf$text$pdf$PdfArtifact$ArtifactType[artifactType.ordinal()];
        if (i == 1) {
            pdfString = new PdfString("Background");
        } else if (i == 2) {
            pdfString = new PdfString("Layout");
        } else if (i != 3) {
            pdfString = i != 4 ? null : new PdfString("Pagination");
        } else {
            pdfString = new PdfString("Page");
        }
        setAccessibleAttribute(PdfName.TYPE, pdfString);
    }

    public PdfArray getBBox() {
        HashMap<PdfName, PdfObject> hashMap = this.accessibleAttributes;
        if (hashMap == null) {
            return null;
        }
        return (PdfArray) hashMap.get(PdfName.BBOX);
    }

    public void setBBox(PdfArray pdfArray) {
        setAccessibleAttribute(PdfName.BBOX, pdfArray);
    }

    public PdfArray getAttached() {
        HashMap<PdfName, PdfObject> hashMap = this.accessibleAttributes;
        if (hashMap == null) {
            return null;
        }
        return (PdfArray) hashMap.get(PdfName.ATTACHED);
    }

    public void setAttached(PdfArray pdfArray) {
        setAccessibleAttribute(PdfName.ATTACHED, pdfArray);
    }
}
