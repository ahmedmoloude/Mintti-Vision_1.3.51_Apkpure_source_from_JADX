package com.itextpdf.text.pdf;

import com.itextpdf.text.pdf.interfaces.IPdfStructureElement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PdfStructureTreeRoot extends PdfDictionary implements IPdfStructureElement {
    private PdfDictionary classMap = null;
    protected HashMap<PdfName, PdfObject> classes = null;
    private HashMap<String, PdfObject> idTreeMap;
    private HashMap<Integer, PdfIndirectReference> numTree = null;
    private HashMap<Integer, PdfObject> parentTree = new HashMap<>();
    private PdfIndirectReference reference;
    private PdfWriter writer;

    PdfStructureTreeRoot(PdfWriter pdfWriter) {
        super(PdfName.STRUCTTREEROOT);
        this.writer = pdfWriter;
        this.reference = pdfWriter.getPdfIndirectReference();
    }

    private void createNumTree() throws IOException {
        if (this.numTree == null) {
            this.numTree = new HashMap<>();
            for (Integer next : this.parentTree.keySet()) {
                PdfObject pdfObject = this.parentTree.get(next);
                if (pdfObject.isArray()) {
                    this.numTree.put(next, this.writer.addToBody((PdfArray) pdfObject).getIndirectReference());
                } else if (pdfObject instanceof PdfIndirectReference) {
                    this.numTree.put(next, (PdfIndirectReference) pdfObject);
                }
            }
        }
    }

    public void mapRole(PdfName pdfName, PdfName pdfName2) {
        PdfDictionary pdfDictionary = (PdfDictionary) get(PdfName.ROLEMAP);
        if (pdfDictionary == null) {
            pdfDictionary = new PdfDictionary();
            put(PdfName.ROLEMAP, pdfDictionary);
        }
        pdfDictionary.put(pdfName, pdfName2);
    }

    public void mapClass(PdfName pdfName, PdfObject pdfObject) {
        if (this.classMap == null) {
            this.classMap = new PdfDictionary();
            this.classes = new HashMap<>();
        }
        this.classes.put(pdfName, pdfObject);
    }

    /* access modifiers changed from: package-private */
    public void putIDTree(String str, PdfObject pdfObject) {
        if (this.idTreeMap == null) {
            this.idTreeMap = new HashMap<>();
        }
        this.idTreeMap.put(str, pdfObject);
    }

    public PdfObject getMappedClass(PdfName pdfName) {
        HashMap<PdfName, PdfObject> hashMap = this.classes;
        if (hashMap == null) {
            return null;
        }
        return hashMap.get(pdfName);
    }

    public PdfWriter getWriter() {
        return this.writer;
    }

    public HashMap<Integer, PdfIndirectReference> getNumTree() throws IOException {
        if (this.numTree == null) {
            createNumTree();
        }
        return this.numTree;
    }

    public PdfIndirectReference getReference() {
        return this.reference;
    }

    /* access modifiers changed from: package-private */
    public void setPageMark(int i, PdfIndirectReference pdfIndirectReference) {
        Integer valueOf = Integer.valueOf(i);
        PdfArray pdfArray = (PdfArray) this.parentTree.get(valueOf);
        if (pdfArray == null) {
            pdfArray = new PdfArray();
            this.parentTree.put(valueOf, pdfArray);
        }
        pdfArray.add((PdfObject) pdfIndirectReference);
    }

    /* access modifiers changed from: package-private */
    public void setAnnotationMark(int i, PdfIndirectReference pdfIndirectReference) {
        this.parentTree.put(Integer.valueOf(i), pdfIndirectReference);
    }

    /* access modifiers changed from: package-private */
    public void buildTree() throws IOException {
        createNumTree();
        PdfDictionary writeTree = PdfNumberTree.writeTree(this.numTree, this.writer);
        if (writeTree != null) {
            put(PdfName.PARENTTREE, this.writer.addToBody(writeTree).getIndirectReference());
        }
        if (this.classMap != null && !this.classes.isEmpty()) {
            for (Map.Entry next : this.classes.entrySet()) {
                PdfObject pdfObject = (PdfObject) next.getValue();
                if (pdfObject.isDictionary()) {
                    this.classMap.put((PdfName) next.getKey(), this.writer.addToBody(pdfObject).getIndirectReference());
                } else if (pdfObject.isArray()) {
                    PdfArray pdfArray = new PdfArray();
                    PdfArray pdfArray2 = (PdfArray) pdfObject;
                    for (int i = 0; i < pdfArray2.size(); i++) {
                        if (pdfArray2.getPdfObject(i).isDictionary()) {
                            pdfArray.add((PdfObject) this.writer.addToBody(pdfArray2.getAsDict(i)).getIndirectReference());
                        }
                    }
                    this.classMap.put((PdfName) next.getKey(), pdfArray);
                }
            }
            put(PdfName.CLASSMAP, this.writer.addToBody(this.classMap).getIndirectReference());
        }
        HashMap<String, PdfObject> hashMap = this.idTreeMap;
        if (hashMap != null && !hashMap.isEmpty()) {
            put(PdfName.IDTREE, PdfNameTree.writeTree(this.idTreeMap, this.writer));
        }
        this.writer.addToBody((PdfObject) this, this.reference);
    }

    public PdfObject getAttribute(PdfName pdfName) {
        PdfDictionary asDict = getAsDict(PdfName.f545A);
        if (asDict == null || !asDict.contains(pdfName)) {
            return null;
        }
        return asDict.get(pdfName);
    }

    public void setAttribute(PdfName pdfName, PdfObject pdfObject) {
        PdfDictionary asDict = getAsDict(PdfName.f545A);
        if (asDict == null) {
            asDict = new PdfDictionary();
            put(PdfName.f545A, asDict);
        }
        asDict.put(pdfName, pdfObject);
    }
}
