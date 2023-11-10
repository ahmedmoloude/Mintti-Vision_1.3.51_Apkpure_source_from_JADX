package com.itextpdf.text.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PdfArray extends PdfObject implements Iterable<PdfObject> {
    protected ArrayList<PdfObject> arrayList;

    public PdfArray() {
        super(5);
        this.arrayList = new ArrayList<>();
    }

    public PdfArray(int i) {
        super(5);
        this.arrayList = new ArrayList<>(i);
    }

    public PdfArray(PdfObject pdfObject) {
        super(5);
        ArrayList<PdfObject> arrayList2 = new ArrayList<>();
        this.arrayList = arrayList2;
        arrayList2.add(pdfObject);
    }

    public PdfArray(float[] fArr) {
        super(5);
        this.arrayList = new ArrayList<>();
        add(fArr);
    }

    public PdfArray(int[] iArr) {
        super(5);
        this.arrayList = new ArrayList<>();
        add(iArr);
    }

    public PdfArray(List<PdfObject> list) {
        this();
        for (PdfObject add : list) {
            add(add);
        }
    }

    public PdfArray(PdfArray pdfArray) {
        super(5);
        this.arrayList = new ArrayList<>(pdfArray.arrayList);
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        PdfWriter.checkPdfIsoConformance(pdfWriter, 11, this);
        outputStream.write(91);
        Iterator<PdfObject> it = this.arrayList.iterator();
        if (it.hasNext()) {
            PdfObject next = it.next();
            if (next == null) {
                next = PdfNull.PDFNULL;
            }
            next.toPdf(pdfWriter, outputStream);
        }
        while (it.hasNext()) {
            PdfObject next2 = it.next();
            if (next2 == null) {
                next2 = PdfNull.PDFNULL;
            }
            int type = next2.type();
            if (!(type == 5 || type == 6 || type == 4 || type == 3)) {
                outputStream.write(32);
            }
            next2.toPdf(pdfWriter, outputStream);
        }
        outputStream.write(93);
    }

    public String toString() {
        return this.arrayList.toString();
    }

    public PdfObject set(int i, PdfObject pdfObject) {
        return this.arrayList.set(i, pdfObject);
    }

    public PdfObject remove(int i) {
        return this.arrayList.remove(i);
    }

    @Deprecated
    public ArrayList<PdfObject> getArrayList() {
        return this.arrayList;
    }

    public int size() {
        return this.arrayList.size();
    }

    public boolean isEmpty() {
        return this.arrayList.isEmpty();
    }

    public boolean add(PdfObject pdfObject) {
        return this.arrayList.add(pdfObject);
    }

    public boolean add(float[] fArr) {
        for (float pdfNumber : fArr) {
            this.arrayList.add(new PdfNumber(pdfNumber));
        }
        return true;
    }

    public boolean add(int[] iArr) {
        for (int pdfNumber : iArr) {
            this.arrayList.add(new PdfNumber(pdfNumber));
        }
        return true;
    }

    public void add(int i, PdfObject pdfObject) {
        this.arrayList.add(i, pdfObject);
    }

    public void addFirst(PdfObject pdfObject) {
        this.arrayList.add(0, pdfObject);
    }

    public boolean contains(PdfObject pdfObject) {
        return this.arrayList.contains(pdfObject);
    }

    public ListIterator<PdfObject> listIterator() {
        return this.arrayList.listIterator();
    }

    public PdfObject getPdfObject(int i) {
        return this.arrayList.get(i);
    }

    public PdfObject getDirectObject(int i) {
        return PdfReader.getPdfObject(getPdfObject(i));
    }

    public PdfDictionary getAsDict(int i) {
        PdfObject directObject = getDirectObject(i);
        if (directObject == null || !directObject.isDictionary()) {
            return null;
        }
        return (PdfDictionary) directObject;
    }

    public PdfArray getAsArray(int i) {
        PdfObject directObject = getDirectObject(i);
        if (directObject == null || !directObject.isArray()) {
            return null;
        }
        return (PdfArray) directObject;
    }

    public PdfStream getAsStream(int i) {
        PdfObject directObject = getDirectObject(i);
        if (directObject == null || !directObject.isStream()) {
            return null;
        }
        return (PdfStream) directObject;
    }

    public PdfString getAsString(int i) {
        PdfObject directObject = getDirectObject(i);
        if (directObject == null || !directObject.isString()) {
            return null;
        }
        return (PdfString) directObject;
    }

    public PdfNumber getAsNumber(int i) {
        PdfObject directObject = getDirectObject(i);
        if (directObject == null || !directObject.isNumber()) {
            return null;
        }
        return (PdfNumber) directObject;
    }

    public PdfName getAsName(int i) {
        PdfObject directObject = getDirectObject(i);
        if (directObject == null || !directObject.isName()) {
            return null;
        }
        return (PdfName) directObject;
    }

    public PdfBoolean getAsBoolean(int i) {
        PdfObject directObject = getDirectObject(i);
        if (directObject == null || !directObject.isBoolean()) {
            return null;
        }
        return (PdfBoolean) directObject;
    }

    public PdfIndirectReference getAsIndirectObject(int i) {
        PdfObject pdfObject = getPdfObject(i);
        if (pdfObject instanceof PdfIndirectReference) {
            return (PdfIndirectReference) pdfObject;
        }
        return null;
    }

    public Iterator<PdfObject> iterator() {
        return this.arrayList.iterator();
    }

    public long[] asLongArray() {
        int size = size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = getAsNumber(i).longValue();
        }
        return jArr;
    }

    public double[] asDoubleArray() {
        int size = size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = getAsNumber(i).doubleValue();
        }
        return dArr;
    }
}
