package com.itextpdf.text.pdf;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class PdfNumberTree {
    private static final int leafSize = 64;

    public static <O extends PdfObject> PdfDictionary writeTree(HashMap<Integer, O> hashMap, PdfWriter pdfWriter) throws IOException {
        HashMap<Integer, O> hashMap2 = hashMap;
        PdfWriter pdfWriter2 = pdfWriter;
        if (hashMap.isEmpty()) {
            return null;
        }
        Integer[] numArr = (Integer[]) hashMap.keySet().toArray(new Integer[hashMap.size()]);
        Arrays.sort(numArr);
        if (numArr.length <= 64) {
            PdfDictionary pdfDictionary = new PdfDictionary();
            PdfArray pdfArray = new PdfArray();
            for (int i = 0; i < numArr.length; i++) {
                pdfArray.add((PdfObject) new PdfNumber(numArr[i].intValue()));
                pdfArray.add((PdfObject) hashMap2.get(numArr[i]));
            }
            pdfDictionary.put(PdfName.NUMS, pdfArray);
            return pdfDictionary;
        }
        int length = ((numArr.length + 64) - 1) / 64;
        PdfIndirectReference[] pdfIndirectReferenceArr = new PdfIndirectReference[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 64;
            int min = Math.min(i3 + 64, numArr.length);
            PdfDictionary pdfDictionary2 = new PdfDictionary();
            PdfArray pdfArray2 = new PdfArray();
            pdfArray2.add((PdfObject) new PdfNumber(numArr[i3].intValue()));
            pdfArray2.add((PdfObject) new PdfNumber(numArr[min - 1].intValue()));
            pdfDictionary2.put(PdfName.LIMITS, pdfArray2);
            PdfArray pdfArray3 = new PdfArray();
            while (i3 < min) {
                pdfArray3.add((PdfObject) new PdfNumber(numArr[i3].intValue()));
                pdfArray3.add((PdfObject) hashMap2.get(numArr[i3]));
                i3++;
            }
            pdfDictionary2.put(PdfName.NUMS, pdfArray3);
            pdfIndirectReferenceArr[i2] = pdfWriter2.addToBody(pdfDictionary2).getIndirectReference();
        }
        int i4 = 64;
        while (length > 64) {
            i4 *= 64;
            int length2 = ((numArr.length + i4) - 1) / i4;
            int i5 = 0;
            while (i5 < length2) {
                int i6 = i5 * 64;
                int min2 = Math.min(i6 + 64, length);
                PdfDictionary pdfDictionary3 = new PdfDictionary();
                PdfArray pdfArray4 = new PdfArray();
                pdfArray4.add((PdfObject) new PdfNumber(numArr[i5 * i4].intValue()));
                int i7 = i5 + 1;
                pdfArray4.add((PdfObject) new PdfNumber(numArr[Math.min(i7 * i4, numArr.length) - 1].intValue()));
                pdfDictionary3.put(PdfName.LIMITS, pdfArray4);
                PdfArray pdfArray5 = new PdfArray();
                while (i6 < min2) {
                    pdfArray5.add((PdfObject) pdfIndirectReferenceArr[i6]);
                    i6++;
                }
                pdfDictionary3.put(PdfName.KIDS, pdfArray5);
                pdfIndirectReferenceArr[i5] = pdfWriter2.addToBody(pdfDictionary3).getIndirectReference();
                i5 = i7;
            }
            length = length2;
        }
        PdfArray pdfArray6 = new PdfArray();
        for (int i8 = 0; i8 < length; i8++) {
            pdfArray6.add((PdfObject) pdfIndirectReferenceArr[i8]);
        }
        PdfDictionary pdfDictionary4 = new PdfDictionary();
        pdfDictionary4.put(PdfName.KIDS, pdfArray6);
        return pdfDictionary4;
    }

    private static void iterateItems(PdfDictionary pdfDictionary, HashMap<Integer, PdfObject> hashMap) {
        PdfArray pdfArray = (PdfArray) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.NUMS));
        int i = 0;
        if (pdfArray != null) {
            while (i < pdfArray.size()) {
                int i2 = i + 1;
                hashMap.put(Integer.valueOf(((PdfNumber) PdfReader.getPdfObjectRelease(pdfArray.getPdfObject(i))).intValue()), pdfArray.getPdfObject(i2));
                i = i2 + 1;
            }
            return;
        }
        PdfArray pdfArray2 = (PdfArray) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.KIDS));
        if (pdfArray2 != null) {
            while (i < pdfArray2.size()) {
                iterateItems((PdfDictionary) PdfReader.getPdfObjectRelease(pdfArray2.getPdfObject(i)), hashMap);
                i++;
            }
        }
    }

    public static HashMap<Integer, PdfObject> readTree(PdfDictionary pdfDictionary) {
        HashMap<Integer, PdfObject> hashMap = new HashMap<>();
        if (pdfDictionary != null) {
            iterateItems(pdfDictionary, hashMap);
        }
        return hashMap;
    }
}
