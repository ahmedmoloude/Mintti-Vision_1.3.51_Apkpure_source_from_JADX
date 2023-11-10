package com.itextpdf.text.pdf;

import java.security.cert.Certificate;

public class PdfPublicKeyRecipient {
    private Certificate certificate = null;
    protected byte[] cms = null;
    private int permission = 0;

    public PdfPublicKeyRecipient(Certificate certificate2, int i) {
        this.certificate = certificate2;
        this.permission = i;
    }

    public Certificate getCertificate() {
        return this.certificate;
    }

    public int getPermission() {
        return this.permission;
    }

    /* access modifiers changed from: protected */
    public void setCms(byte[] bArr) {
        this.cms = bArr;
    }

    /* access modifiers changed from: protected */
    public byte[] getCms() {
        return this.cms;
    }
}
