package com.itextpdf.text.pdf.security;

import java.security.cert.X509Certificate;

public class VerificationOK {
    protected X509Certificate certificate;
    protected String message;
    protected Class<? extends CertificateVerifier> verifierClass;

    public VerificationOK(X509Certificate x509Certificate, Class<? extends CertificateVerifier> cls, String str) {
        this.certificate = x509Certificate;
        this.verifierClass = cls;
        this.message = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        X509Certificate x509Certificate = this.certificate;
        if (x509Certificate != null) {
            sb.append(x509Certificate.getSubjectDN().getName());
            sb.append(" verified with ");
        }
        sb.append(this.verifierClass.getName());
        sb.append(": ");
        sb.append(this.message);
        return sb.toString();
    }
}
