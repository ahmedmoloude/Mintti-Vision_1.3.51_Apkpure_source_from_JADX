package com.itextpdf.text.pdf.fonts.cmaps;

import com.itextpdf.text.p018io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import java.io.IOException;

public class CidLocationFromByte implements CidLocation {
    private byte[] data;

    public CidLocationFromByte(byte[] bArr) {
        this.data = bArr;
    }

    public PRTokeniser getLocation(String str) throws IOException {
        return new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(this.data)));
    }
}
