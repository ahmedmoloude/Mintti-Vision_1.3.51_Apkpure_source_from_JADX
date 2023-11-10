package com.itextpdf.text.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PdfDictionary extends PdfObject {
    public static final PdfName CATALOG = PdfName.CATALOG;
    public static final PdfName FONT = PdfName.FONT;
    public static final PdfName OUTLINES = PdfName.OUTLINES;
    public static final PdfName PAGE = PdfName.PAGE;
    public static final PdfName PAGES = PdfName.PAGES;
    private PdfName dictionaryType;
    protected HashMap<PdfName, PdfObject> hashMap;

    public PdfDictionary() {
        super(6);
        this.dictionaryType = null;
        this.hashMap = new HashMap<>();
    }

    public PdfDictionary(int i) {
        super(6);
        this.dictionaryType = null;
        this.hashMap = new HashMap<>(i);
    }

    public PdfDictionary(PdfName pdfName) {
        this();
        this.dictionaryType = pdfName;
        put(PdfName.TYPE, this.dictionaryType);
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        PdfWriter.checkPdfIsoConformance(pdfWriter, 11, this);
        outputStream.write(60);
        outputStream.write(60);
        for (Map.Entry next : this.hashMap.entrySet()) {
            ((PdfName) next.getKey()).toPdf(pdfWriter, outputStream);
            PdfObject pdfObject = (PdfObject) next.getValue();
            int type = pdfObject.type();
            if (!(type == 5 || type == 6 || type == 4 || type == 3)) {
                outputStream.write(32);
            }
            pdfObject.toPdf(pdfWriter, outputStream);
        }
        outputStream.write(62);
        outputStream.write(62);
    }

    public String toString() {
        if (get(PdfName.TYPE) == null) {
            return "Dictionary";
        }
        return "Dictionary of type: " + get(PdfName.TYPE);
    }

    public void put(PdfName pdfName, PdfObject pdfObject) {
        if (pdfObject == null || pdfObject.isNull()) {
            this.hashMap.remove(pdfName);
        } else {
            this.hashMap.put(pdfName, pdfObject);
        }
    }

    public void putEx(PdfName pdfName, PdfObject pdfObject) {
        if (pdfObject != null) {
            put(pdfName, pdfObject);
        }
    }

    public void putAll(PdfDictionary pdfDictionary) {
        this.hashMap.putAll(pdfDictionary.hashMap);
    }

    public void remove(PdfName pdfName) {
        this.hashMap.remove(pdfName);
    }

    public void clear() {
        this.hashMap.clear();
    }

    public PdfObject get(PdfName pdfName) {
        return this.hashMap.get(pdfName);
    }

    public PdfObject getDirectObject(PdfName pdfName) {
        return PdfReader.getPdfObject(get(pdfName));
    }

    public Set<PdfName> getKeys() {
        return this.hashMap.keySet();
    }

    public int size() {
        return this.hashMap.size();
    }

    public boolean contains(PdfName pdfName) {
        return this.hashMap.containsKey(pdfName);
    }

    public boolean isFont() {
        return checkType(FONT);
    }

    public boolean isPage() {
        return checkType(PAGE);
    }

    public boolean isPages() {
        return checkType(PAGES);
    }

    public boolean isCatalog() {
        return checkType(CATALOG);
    }

    public boolean isOutlineTree() {
        return checkType(OUTLINES);
    }

    public boolean checkType(PdfName pdfName) {
        if (pdfName == null) {
            return false;
        }
        if (this.dictionaryType == null) {
            this.dictionaryType = getAsName(PdfName.TYPE);
        }
        return pdfName.equals(this.dictionaryType);
    }

    public void merge(PdfDictionary pdfDictionary) {
        this.hashMap.putAll(pdfDictionary.hashMap);
    }

    public void mergeDifferent(PdfDictionary pdfDictionary) {
        for (PdfName next : pdfDictionary.hashMap.keySet()) {
            if (!this.hashMap.containsKey(next)) {
                this.hashMap.put(next, pdfDictionary.hashMap.get(next));
            }
        }
    }

    public PdfDictionary getAsDict(PdfName pdfName) {
        PdfObject directObject = getDirectObject(pdfName);
        if (directObject == null || !directObject.isDictionary()) {
            return null;
        }
        return (PdfDictionary) directObject;
    }

    public PdfArray getAsArray(PdfName pdfName) {
        PdfObject directObject = getDirectObject(pdfName);
        if (directObject == null || !directObject.isArray()) {
            return null;
        }
        return (PdfArray) directObject;
    }

    public PdfStream getAsStream(PdfName pdfName) {
        PdfObject directObject = getDirectObject(pdfName);
        if (directObject == null || !directObject.isStream()) {
            return null;
        }
        return (PdfStream) directObject;
    }

    public PdfString getAsString(PdfName pdfName) {
        PdfObject directObject = getDirectObject(pdfName);
        if (directObject == null || !directObject.isString()) {
            return null;
        }
        return (PdfString) directObject;
    }

    public PdfNumber getAsNumber(PdfName pdfName) {
        PdfObject directObject = getDirectObject(pdfName);
        if (directObject == null || !directObject.isNumber()) {
            return null;
        }
        return (PdfNumber) directObject;
    }

    public PdfName getAsName(PdfName pdfName) {
        PdfObject directObject = getDirectObject(pdfName);
        if (directObject == null || !directObject.isName()) {
            return null;
        }
        return (PdfName) directObject;
    }

    public PdfBoolean getAsBoolean(PdfName pdfName) {
        PdfObject directObject = getDirectObject(pdfName);
        if (directObject == null || !directObject.isBoolean()) {
            return null;
        }
        return (PdfBoolean) directObject;
    }

    public PdfIndirectReference getAsIndirectObject(PdfName pdfName) {
        PdfObject pdfObject = get(pdfName);
        if (pdfObject == null || !pdfObject.isIndirect()) {
            return null;
        }
        return (PdfIndirectReference) pdfObject;
    }
}
