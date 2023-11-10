package com.itextpdf.text.pdf;

import java.io.IOException;
import java.io.OutputStream;
import kotlin.text.Typography;

public class PdfString extends PdfObject {
    protected String encoding = PdfObject.TEXT_PDFDOCENCODING;
    protected boolean hexWriting = false;
    protected int objGen = 0;
    protected int objNum = 0;
    protected String originalValue = null;
    protected String value = "";

    public PdfString() {
        super(3);
    }

    public PdfString(String str) {
        super(3);
        this.value = str;
    }

    public PdfString(String str, String str2) {
        super(3);
        this.value = str;
        this.encoding = str2;
    }

    public PdfString(byte[] bArr) {
        super(3);
        this.value = PdfEncodings.convertToString(bArr, (String) null);
        this.encoding = "";
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        PdfWriter.checkPdfIsoConformance(pdfWriter, 11, this);
        byte[] bytes = getBytes();
        PdfEncryption encryption = pdfWriter != null ? pdfWriter.getEncryption() : null;
        if (encryption != null && !encryption.isEmbeddedFilesOnly()) {
            bytes = encryption.encryptByteArray(bytes);
        }
        if (this.hexWriting) {
            ByteBuffer byteBuffer = new ByteBuffer();
            byteBuffer.append((char) Typography.less);
            for (byte appendHex : bytes) {
                byteBuffer.appendHex(appendHex);
            }
            byteBuffer.append((char) Typography.greater);
            outputStream.write(byteBuffer.toByteArray());
            return;
        }
        outputStream.write(StringUtils.escapeString(bytes));
    }

    public String toString() {
        return this.value;
    }

    public byte[] getBytes() {
        if (this.bytes == null) {
            String str = this.encoding;
            if (str == null || !str.equals(PdfObject.TEXT_UNICODE) || !PdfEncodings.isPdfDocEncoding(this.value)) {
                this.bytes = PdfEncodings.convertToBytes(this.value, this.encoding);
            } else {
                this.bytes = PdfEncodings.convertToBytes(this.value, PdfObject.TEXT_PDFDOCENCODING);
            }
        }
        return this.bytes;
    }

    public String toUnicodeString() {
        String str = this.encoding;
        if (str != null && str.length() != 0) {
            return this.value;
        }
        getBytes();
        if (this.bytes.length >= 2 && this.bytes[0] == -2 && this.bytes[1] == -1) {
            return PdfEncodings.convertToString(this.bytes, PdfObject.TEXT_UNICODE);
        }
        return PdfEncodings.convertToString(this.bytes, PdfObject.TEXT_PDFDOCENCODING);
    }

    public String getEncoding() {
        return this.encoding;
    }

    /* access modifiers changed from: package-private */
    public void setObjNum(int i, int i2) {
        this.objNum = i;
        this.objGen = i2;
    }

    /* access modifiers changed from: package-private */
    public void decrypt(PdfReader pdfReader) {
        PdfEncryption decrypt = pdfReader.getDecrypt();
        if (decrypt != null) {
            this.originalValue = this.value;
            decrypt.setHashKey(this.objNum, this.objGen);
            this.bytes = PdfEncodings.convertToBytes(this.value, (String) null);
            this.bytes = decrypt.decryptByteArray(this.bytes);
            this.value = PdfEncodings.convertToString(this.bytes, (String) null);
        }
    }

    public byte[] getOriginalBytes() {
        String str = this.originalValue;
        if (str == null) {
            return getBytes();
        }
        return PdfEncodings.convertToBytes(str, (String) null);
    }

    public PdfString setHexWriting(boolean z) {
        this.hexWriting = z;
        return this;
    }

    public boolean isHexWriting() {
        return this.hexWriting;
    }
}
