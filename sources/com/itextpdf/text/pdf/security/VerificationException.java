package com.itextpdf.text.pdf.security;

import java.security.GeneralSecurityException;

public class VerificationException extends GeneralSecurityException {
    private static final long serialVersionUID = 2978604513926438256L;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public VerificationException(java.security.cert.Certificate r3, java.lang.String r4) {
        /*
            r2 = this;
            r0 = 2
            java.lang.Object[] r0 = new java.lang.Object[r0]
            if (r3 != 0) goto L_0x0008
            java.lang.String r3 = "Unknown"
            goto L_0x0012
        L_0x0008:
            java.security.cert.X509Certificate r3 = (java.security.cert.X509Certificate) r3
            java.security.Principal r3 = r3.getSubjectDN()
            java.lang.String r3 = r3.getName()
        L_0x0012:
            r1 = 0
            r0[r1] = r3
            r3 = 1
            r0[r3] = r4
            java.lang.String r3 = "Certificate %s failed: %s"
            java.lang.String r3 = java.lang.String.format(r3, r0)
            r2.<init>(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.security.VerificationException.<init>(java.security.cert.Certificate, java.lang.String):void");
    }
}
