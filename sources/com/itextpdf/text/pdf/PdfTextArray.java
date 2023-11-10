package com.itextpdf.text.pdf;

import java.util.ArrayList;

public class PdfTextArray {
    ArrayList<Object> arrayList = new ArrayList<>();
    private Float lastNum;
    private String lastStr;

    public PdfTextArray(String str) {
        add(str);
    }

    public PdfTextArray() {
    }

    public void add(PdfNumber pdfNumber) {
        add((float) pdfNumber.doubleValue());
    }

    public void add(float f) {
        if (f != 0.0f) {
            if (this.lastNum != null) {
                Float f2 = new Float(f + this.lastNum.floatValue());
                this.lastNum = f2;
                if (f2.floatValue() != 0.0f) {
                    replaceLast(this.lastNum);
                } else {
                    ArrayList<Object> arrayList2 = this.arrayList;
                    arrayList2.remove(arrayList2.size() - 1);
                }
            } else {
                Float f3 = new Float(f);
                this.lastNum = f3;
                this.arrayList.add(f3);
            }
            this.lastStr = null;
        }
    }

    public void add(String str) {
        if (str.length() > 0) {
            if (this.lastStr != null) {
                String str2 = this.lastStr + str;
                this.lastStr = str2;
                replaceLast(str2);
            } else {
                this.lastStr = str;
                this.arrayList.add(str);
            }
            this.lastNum = null;
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<Object> getArrayList() {
        return this.arrayList;
    }

    private void replaceLast(Object obj) {
        ArrayList<Object> arrayList2 = this.arrayList;
        arrayList2.set(arrayList2.size() - 1, obj);
    }
}
