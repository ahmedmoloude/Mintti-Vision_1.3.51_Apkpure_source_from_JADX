package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.IOException;
import java.util.ArrayList;

public class PdfLayer extends PdfDictionary implements PdfOCG {
    protected ArrayList<PdfLayer> children;

    /* renamed from: on */
    private boolean f544on = true;
    private boolean onPanel = true;
    protected PdfLayer parent;
    protected PdfIndirectReference ref;
    protected String title;

    public PdfObject getPdfObject() {
        return this;
    }

    PdfLayer(String str) {
        this.title = str;
    }

    public static PdfLayer createTitle(String str, PdfWriter pdfWriter) {
        if (str != null) {
            PdfLayer pdfLayer = new PdfLayer(str);
            pdfWriter.registerLayer(pdfLayer);
            return pdfLayer;
        }
        throw new NullPointerException(MessageLocalization.getComposedMessage("title.cannot.be.null", new Object[0]));
    }

    public PdfLayer(String str, PdfWriter pdfWriter) throws IOException {
        super(PdfName.OCG);
        setName(str);
        if (pdfWriter instanceof PdfStamperImp) {
            this.ref = pdfWriter.addToBody(this).getIndirectReference();
        } else {
            this.ref = pdfWriter.getPdfIndirectReference();
        }
        pdfWriter.registerLayer(this);
    }

    /* access modifiers changed from: package-private */
    public String getTitle() {
        return this.title;
    }

    public void addChild(PdfLayer pdfLayer) {
        if (pdfLayer.parent == null) {
            pdfLayer.parent = this;
            if (this.children == null) {
                this.children = new ArrayList<>();
            }
            this.children.add(pdfLayer);
            return;
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.layer.1.already.has.a.parent", pdfLayer.getAsString(PdfName.NAME).toUnicodeString()));
    }

    public PdfLayer getParent() {
        return this.parent;
    }

    public ArrayList<PdfLayer> getChildren() {
        return this.children;
    }

    public PdfIndirectReference getRef() {
        return this.ref;
    }

    /* access modifiers changed from: package-private */
    public void setRef(PdfIndirectReference pdfIndirectReference) {
        this.ref = pdfIndirectReference;
    }

    public void setName(String str) {
        put(PdfName.NAME, new PdfString(str, PdfObject.TEXT_UNICODE));
    }

    public boolean isOn() {
        return this.f544on;
    }

    public void setOn(boolean z) {
        this.f544on = z;
    }

    private PdfDictionary getUsage() {
        PdfDictionary asDict = getAsDict(PdfName.USAGE);
        if (asDict != null) {
            return asDict;
        }
        PdfDictionary pdfDictionary = new PdfDictionary();
        put(PdfName.USAGE, pdfDictionary);
        return pdfDictionary;
    }

    public void setCreatorInfo(String str, String str2) {
        PdfDictionary usage = getUsage();
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.CREATOR, new PdfString(str, PdfObject.TEXT_UNICODE));
        pdfDictionary.put(PdfName.SUBTYPE, new PdfName(str2));
        usage.put(PdfName.CREATORINFO, pdfDictionary);
    }

    public void setLanguage(String str, boolean z) {
        PdfDictionary usage = getUsage();
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.LANG, new PdfString(str, PdfObject.TEXT_UNICODE));
        if (z) {
            pdfDictionary.put(PdfName.PREFERRED, PdfName.f620ON);
        }
        usage.put(PdfName.LANGUAGE, pdfDictionary);
    }

    public void setExport(boolean z) {
        PdfDictionary usage = getUsage();
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.EXPORTSTATE, z ? PdfName.f620ON : PdfName.OFF);
        usage.put(PdfName.EXPORT, pdfDictionary);
    }

    public void setZoom(float f, float f2) {
        if (f > 0.0f || f2 >= 0.0f) {
            PdfDictionary usage = getUsage();
            PdfDictionary pdfDictionary = new PdfDictionary();
            if (f > 0.0f) {
                pdfDictionary.put(PdfName.MIN_LOWER_CASE, new PdfNumber(f));
            }
            if (f2 >= 0.0f) {
                pdfDictionary.put(PdfName.MAX_LOWER_CASE, new PdfNumber(f2));
            }
            usage.put(PdfName.ZOOM, pdfDictionary);
        }
    }

    public void setPrint(String str, boolean z) {
        PdfDictionary usage = getUsage();
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.SUBTYPE, new PdfName(str));
        pdfDictionary.put(PdfName.PRINTSTATE, z ? PdfName.f620ON : PdfName.OFF);
        usage.put(PdfName.PRINT, pdfDictionary);
    }

    public void setView(boolean z) {
        PdfDictionary usage = getUsage();
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.VIEWSTATE, z ? PdfName.f620ON : PdfName.OFF);
        usage.put(PdfName.VIEW, pdfDictionary);
    }

    public void setPageElement(String str) {
        PdfDictionary usage = getUsage();
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.SUBTYPE, new PdfName(str));
        usage.put(PdfName.PAGEELEMENT, pdfDictionary);
    }

    public void setUser(String str, String... strArr) {
        PdfDictionary usage = getUsage();
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.TYPE, new PdfName(str));
        PdfArray pdfArray = new PdfArray();
        for (String pdfString : strArr) {
            pdfArray.add((PdfObject) new PdfString(pdfString, PdfObject.TEXT_UNICODE));
        }
        usage.put(PdfName.NAME, pdfArray);
        usage.put(PdfName.USER, pdfDictionary);
    }

    public boolean isOnPanel() {
        return this.onPanel;
    }

    public void setOnPanel(boolean z) {
        this.onPanel = z;
    }
}
