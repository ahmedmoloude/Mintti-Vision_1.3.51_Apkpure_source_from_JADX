package com.itextpdf.text.pdf;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.interfaces.PdfEncryptionSettings;
import com.itextpdf.text.pdf.interfaces.PdfViewerPreferences;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;

@Deprecated
public class PdfCopyFields implements PdfViewerPreferences, PdfEncryptionSettings {

    /* renamed from: fc */
    private PdfCopyFieldsImp f538fc;

    public PdfCopyFields(OutputStream outputStream) throws DocumentException {
        this.f538fc = new PdfCopyFieldsImp(outputStream);
    }

    public PdfCopyFields(OutputStream outputStream, char c) throws DocumentException {
        this.f538fc = new PdfCopyFieldsImp(outputStream, c);
    }

    public void addDocument(PdfReader pdfReader) throws DocumentException, IOException {
        this.f538fc.addDocument(pdfReader);
    }

    public void addDocument(PdfReader pdfReader, List<Integer> list) throws DocumentException, IOException {
        this.f538fc.addDocument(pdfReader, list);
    }

    public void addDocument(PdfReader pdfReader, String str) throws DocumentException, IOException {
        this.f538fc.addDocument(pdfReader, SequenceList.expand(str, pdfReader.getNumberOfPages()));
    }

    public void setEncryption(byte[] bArr, byte[] bArr2, int i, boolean z) throws DocumentException {
        this.f538fc.setEncryption(bArr, bArr2, i, z ? 1 : 0);
    }

    public void setEncryption(boolean z, String str, String str2, int i) throws DocumentException {
        setEncryption(DocWriter.getISOBytes(str), DocWriter.getISOBytes(str2), i, z);
    }

    public void close() {
        this.f538fc.close();
    }

    public void open() {
        this.f538fc.openDoc();
    }

    public void addJavaScript(String str) {
        this.f538fc.addJavaScript(str, !PdfEncodings.isPdfDocEncoding(str));
    }

    public void setOutlines(List<HashMap<String, Object>> list) {
        this.f538fc.setOutlines(list);
    }

    public PdfWriter getWriter() {
        return this.f538fc;
    }

    public boolean isFullCompression() {
        return this.f538fc.isFullCompression();
    }

    public void setFullCompression() throws DocumentException {
        this.f538fc.setFullCompression();
    }

    public void setEncryption(byte[] bArr, byte[] bArr2, int i, int i2) throws DocumentException {
        this.f538fc.setEncryption(bArr, bArr2, i, i2);
    }

    public void addViewerPreference(PdfName pdfName, PdfObject pdfObject) {
        this.f538fc.addViewerPreference(pdfName, pdfObject);
    }

    public void setViewerPreferences(int i) {
        this.f538fc.setViewerPreferences(i);
    }

    public void setEncryption(Certificate[] certificateArr, int[] iArr, int i) throws DocumentException {
        this.f538fc.setEncryption(certificateArr, iArr, i);
    }
}
