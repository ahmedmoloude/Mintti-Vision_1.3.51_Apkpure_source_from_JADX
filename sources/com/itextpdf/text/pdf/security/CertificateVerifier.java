package com.itextpdf.text.pdf.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CertificateVerifier {
    protected boolean onlineCheckingAllowed = true;
    protected CertificateVerifier verifier;

    public CertificateVerifier(CertificateVerifier certificateVerifier) {
        this.verifier = certificateVerifier;
    }

    public void setOnlineCheckingAllowed(boolean z) {
        this.onlineCheckingAllowed = z;
    }

    public List<VerificationOK> verify(X509Certificate x509Certificate, X509Certificate x509Certificate2, Date date) throws GeneralSecurityException, IOException {
        if (date != null) {
            x509Certificate.checkValidity(date);
        }
        if (x509Certificate2 != null) {
            x509Certificate.verify(x509Certificate2.getPublicKey());
        } else {
            x509Certificate.verify(x509Certificate.getPublicKey());
        }
        ArrayList arrayList = new ArrayList();
        CertificateVerifier certificateVerifier = this.verifier;
        if (certificateVerifier != null) {
            arrayList.addAll(certificateVerifier.verify(x509Certificate, x509Certificate2, date));
        }
        return arrayList;
    }
}
