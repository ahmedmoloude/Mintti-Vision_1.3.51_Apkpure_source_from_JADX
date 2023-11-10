package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import java.io.IOException;

public class PdfFunction {
    protected PdfDictionary dictionary;
    protected PdfIndirectReference reference;
    protected PdfWriter writer;

    protected PdfFunction(PdfWriter pdfWriter) {
        this.writer = pdfWriter;
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference getReference() {
        try {
            if (this.reference == null) {
                this.reference = this.writer.addToBody(this.dictionary).getIndirectReference();
            }
            return this.reference;
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    public static PdfFunction type0(PdfWriter pdfWriter, float[] fArr, float[] fArr2, int[] iArr, int i, int i2, float[] fArr3, float[] fArr4, byte[] bArr) {
        PdfFunction pdfFunction = new PdfFunction(pdfWriter);
        PdfStream pdfStream = new PdfStream(bArr);
        pdfFunction.dictionary = pdfStream;
        pdfStream.flateCompress(pdfWriter.getCompressionLevel());
        pdfFunction.dictionary.put(PdfName.FUNCTIONTYPE, new PdfNumber(0));
        pdfFunction.dictionary.put(PdfName.DOMAIN, new PdfArray(fArr));
        pdfFunction.dictionary.put(PdfName.RANGE, new PdfArray(fArr2));
        pdfFunction.dictionary.put(PdfName.SIZE, new PdfArray(iArr));
        pdfFunction.dictionary.put(PdfName.BITSPERSAMPLE, new PdfNumber(i));
        if (i2 != 1) {
            pdfFunction.dictionary.put(PdfName.ORDER, new PdfNumber(i2));
        }
        if (fArr3 != null) {
            pdfFunction.dictionary.put(PdfName.ENCODE, new PdfArray(fArr3));
        }
        if (fArr4 != null) {
            pdfFunction.dictionary.put(PdfName.DECODE, new PdfArray(fArr4));
        }
        return pdfFunction;
    }

    public static PdfFunction type2(PdfWriter pdfWriter, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float f) {
        PdfFunction pdfFunction = new PdfFunction(pdfWriter);
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfFunction.dictionary = pdfDictionary;
        pdfDictionary.put(PdfName.FUNCTIONTYPE, new PdfNumber(2));
        pdfFunction.dictionary.put(PdfName.DOMAIN, new PdfArray(fArr));
        if (fArr2 != null) {
            pdfFunction.dictionary.put(PdfName.RANGE, new PdfArray(fArr2));
        }
        if (fArr3 != null) {
            pdfFunction.dictionary.put(PdfName.f558C0, new PdfArray(fArr3));
        }
        if (fArr4 != null) {
            pdfFunction.dictionary.put(PdfName.f559C1, new PdfArray(fArr4));
        }
        pdfFunction.dictionary.put(PdfName.f610N, new PdfNumber(f));
        return pdfFunction;
    }

    public static PdfFunction type3(PdfWriter pdfWriter, float[] fArr, float[] fArr2, PdfFunction[] pdfFunctionArr, float[] fArr3, float[] fArr4) {
        PdfFunction pdfFunction = new PdfFunction(pdfWriter);
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfFunction.dictionary = pdfDictionary;
        pdfDictionary.put(PdfName.FUNCTIONTYPE, new PdfNumber(3));
        pdfFunction.dictionary.put(PdfName.DOMAIN, new PdfArray(fArr));
        if (fArr2 != null) {
            pdfFunction.dictionary.put(PdfName.RANGE, new PdfArray(fArr2));
        }
        PdfArray pdfArray = new PdfArray();
        for (PdfFunction reference2 : pdfFunctionArr) {
            pdfArray.add((PdfObject) reference2.getReference());
        }
        pdfFunction.dictionary.put(PdfName.FUNCTIONS, pdfArray);
        pdfFunction.dictionary.put(PdfName.BOUNDS, new PdfArray(fArr3));
        pdfFunction.dictionary.put(PdfName.ENCODE, new PdfArray(fArr4));
        return pdfFunction;
    }

    public static PdfFunction type4(PdfWriter pdfWriter, float[] fArr, float[] fArr2, String str) {
        int length = str.length();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        PdfFunction pdfFunction = new PdfFunction(pdfWriter);
        PdfStream pdfStream = new PdfStream(bArr);
        pdfFunction.dictionary = pdfStream;
        pdfStream.flateCompress(pdfWriter.getCompressionLevel());
        pdfFunction.dictionary.put(PdfName.FUNCTIONTYPE, new PdfNumber(4));
        pdfFunction.dictionary.put(PdfName.DOMAIN, new PdfArray(fArr));
        pdfFunction.dictionary.put(PdfName.RANGE, new PdfArray(fArr2));
        return pdfFunction;
    }
}
