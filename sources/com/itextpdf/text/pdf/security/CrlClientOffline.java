package com.itextpdf.text.pdf.security;

import com.itextpdf.text.ExceptionConverter;
import java.security.cert.CRL;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;

public class CrlClientOffline implements CrlClient {
    private ArrayList<byte[]> crls;

    public CrlClientOffline(byte[] bArr) {
        ArrayList<byte[]> arrayList = new ArrayList<>();
        this.crls = arrayList;
        arrayList.add(bArr);
    }

    public CrlClientOffline(CRL crl) {
        ArrayList<byte[]> arrayList = new ArrayList<>();
        this.crls = arrayList;
        try {
            arrayList.add(((X509CRL) crl).getEncoded());
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public Collection<byte[]> getEncoded(X509Certificate x509Certificate, String str) {
        return this.crls;
    }
}
