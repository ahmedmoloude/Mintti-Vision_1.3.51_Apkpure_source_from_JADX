package com.itextpdf.text.pdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import p028io.jsonwebtoken.JwtParser;

public class PRAcroForm extends PdfDictionary {
    HashMap<String, FieldInformation> fieldByName = new HashMap<>();
    ArrayList<FieldInformation> fields = new ArrayList<>();
    PdfReader reader;
    ArrayList<PdfDictionary> stack = new ArrayList<>();

    public static class FieldInformation {
        String fieldName;
        PdfDictionary info;
        PRIndirectReference ref;

        FieldInformation(String str, PdfDictionary pdfDictionary, PRIndirectReference pRIndirectReference) {
            this.fieldName = str;
            this.info = pdfDictionary;
            this.ref = pRIndirectReference;
        }

        public String getWidgetName() {
            PdfObject pdfObject = this.info.get(PdfName.f616NM);
            if (pdfObject != null) {
                return pdfObject.toString();
            }
            return null;
        }

        public String getName() {
            return this.fieldName;
        }

        public PdfDictionary getInfo() {
            return this.info;
        }

        public PRIndirectReference getRef() {
            return this.ref;
        }
    }

    public PRAcroForm(PdfReader pdfReader) {
        this.reader = pdfReader;
    }

    public int size() {
        return this.fields.size();
    }

    public ArrayList<FieldInformation> getFields() {
        return this.fields;
    }

    public FieldInformation getField(String str) {
        return this.fieldByName.get(str);
    }

    public PRIndirectReference getRefByName(String str) {
        FieldInformation fieldInformation = this.fieldByName.get(str);
        if (fieldInformation == null) {
            return null;
        }
        return fieldInformation.getRef();
    }

    public void readAcroForm(PdfDictionary pdfDictionary) {
        if (pdfDictionary != null) {
            this.hashMap = pdfDictionary.hashMap;
            pushAttrib(pdfDictionary);
            PdfArray pdfArray = (PdfArray) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.FIELDS));
            if (pdfArray != null) {
                iterateFields(pdfArray, (PRIndirectReference) null, (String) null);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void iterateFields(PdfArray pdfArray, PRIndirectReference pRIndirectReference, String str) {
        String str2;
        ListIterator<PdfObject> listIterator = pdfArray.listIterator();
        while (listIterator.hasNext()) {
            PRIndirectReference pRIndirectReference2 = (PRIndirectReference) listIterator.next();
            PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObjectRelease((PdfObject) pRIndirectReference2);
            PdfString pdfString = (PdfString) pdfDictionary.get(PdfName.f647T);
            boolean z = pdfString != null;
            if (!z) {
                pRIndirectReference2 = pRIndirectReference;
                str2 = str;
            } else if (str == null) {
                str2 = pdfString.toString();
            } else {
                str2 = str + JwtParser.SEPARATOR_CHAR + pdfString.toString();
            }
            PdfArray pdfArray2 = (PdfArray) pdfDictionary.get(PdfName.KIDS);
            if (pdfArray2 != null) {
                pushAttrib(pdfDictionary);
                iterateFields(pdfArray2, pRIndirectReference2, str2);
                ArrayList<PdfDictionary> arrayList = this.stack;
                arrayList.remove(arrayList.size() - 1);
            } else if (pRIndirectReference2 != null) {
                ArrayList<PdfDictionary> arrayList2 = this.stack;
                PdfDictionary pdfDictionary2 = arrayList2.get(arrayList2.size() - 1);
                if (z) {
                    pdfDictionary2 = mergeAttrib(pdfDictionary2, pdfDictionary);
                }
                pdfDictionary2.put(PdfName.f647T, new PdfString(str2));
                FieldInformation fieldInformation = new FieldInformation(str2, pdfDictionary2, pRIndirectReference2);
                this.fields.add(fieldInformation);
                this.fieldByName.put(str2, fieldInformation);
            }
        }
    }

    /* access modifiers changed from: protected */
    public PdfDictionary mergeAttrib(PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2) {
        PdfDictionary pdfDictionary3 = new PdfDictionary();
        if (pdfDictionary != null) {
            pdfDictionary3.putAll(pdfDictionary);
        }
        for (PdfName next : pdfDictionary2.getKeys()) {
            if (next.equals(PdfName.f574DR) || next.equals(PdfName.f568DA) || next.equals(PdfName.f631Q) || next.equals(PdfName.f583FF) || next.equals(PdfName.f576DV) || next.equals(PdfName.f664V) || next.equals(PdfName.f588FT) || next.equals(PdfName.f616NM) || next.equals(PdfName.f580F)) {
                pdfDictionary3.put(next, pdfDictionary2.get(next));
            }
        }
        return pdfDictionary3;
    }

    /* access modifiers changed from: protected */
    public void pushAttrib(PdfDictionary pdfDictionary) {
        PdfDictionary pdfDictionary2;
        if (!this.stack.isEmpty()) {
            ArrayList<PdfDictionary> arrayList = this.stack;
            pdfDictionary2 = arrayList.get(arrayList.size() - 1);
        } else {
            pdfDictionary2 = null;
        }
        this.stack.add(mergeAttrib(pdfDictionary2, pdfDictionary));
    }
}
