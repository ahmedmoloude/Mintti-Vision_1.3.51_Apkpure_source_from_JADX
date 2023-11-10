package com.itextpdf.text.p018io;

import com.itextpdf.text.pdf.PdfObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

/* renamed from: com.itextpdf.text.io.TempFileCache */
public class TempFileCache {
    private ByteArrayOutputStream baos;
    private byte[] buf;
    private RandomAccessFile cache;
    private String filename;

    /* renamed from: com.itextpdf.text.io.TempFileCache$ObjectPosition */
    public class ObjectPosition {
        int length;
        long offset;

        ObjectPosition(long j, int i) {
            this.offset = j;
            this.length = i;
        }
    }

    public TempFileCache(String str) throws IOException {
        this.filename = str;
        File parentFile = new File(str).getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        this.cache = new RandomAccessFile(str, "rw");
        this.baos = new ByteArrayOutputStream();
    }

    public ObjectPosition put(PdfObject pdfObject) throws IOException {
        this.baos.reset();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.baos);
        long length = this.cache.length();
        objectOutputStream.writeObject(pdfObject);
        this.cache.seek(length);
        this.cache.write(this.baos.toByteArray());
        return new ObjectPosition(length, (int) (this.cache.length() - length));
    }

    public PdfObject get(ObjectPosition objectPosition) throws IOException, ClassNotFoundException {
        if (objectPosition == null) {
            return null;
        }
        this.cache.seek(objectPosition.offset);
        this.cache.read(getBuffer(objectPosition.length), 0, objectPosition.length);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(getBuffer(objectPosition.length)));
        try {
            return (PdfObject) objectInputStream.readObject();
        } finally {
            objectInputStream.close();
        }
    }

    private byte[] getBuffer(int i) {
        byte[] bArr = this.buf;
        if (bArr == null || bArr.length < i) {
            this.buf = new byte[i];
        }
        return this.buf;
    }

    public void close() throws IOException {
        this.cache.close();
        this.cache = null;
        new File(this.filename).delete();
    }
}
