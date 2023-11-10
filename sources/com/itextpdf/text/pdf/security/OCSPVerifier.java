package com.itextpdf.text.pdf.security;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.SingleResp;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcDigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;

public class OCSPVerifier extends RootStoreVerifier {
    protected static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) OCSPVerifier.class);
    protected static final String id_kp_OCSPSigning = "1.3.6.1.5.5.7.3.9";
    protected List<BasicOCSPResp> ocsps;

    public OCSPVerifier(CertificateVerifier certificateVerifier, List<BasicOCSPResp> list) {
        super(certificateVerifier);
        this.ocsps = list;
    }

    public List<VerificationOK> verify(X509Certificate x509Certificate, X509Certificate x509Certificate2, Date date) throws GeneralSecurityException, IOException {
        int i;
        ArrayList arrayList = new ArrayList();
        List<BasicOCSPResp> list = this.ocsps;
        boolean z = false;
        if (list != null) {
            i = 0;
            for (BasicOCSPResp verify : list) {
                if (verify(verify, x509Certificate, x509Certificate2, date)) {
                    i++;
                }
            }
        } else {
            i = 0;
        }
        if (this.onlineCheckingAllowed && i == 0 && verify(getOcspResponse(x509Certificate, x509Certificate2), x509Certificate, x509Certificate2, date)) {
            i++;
            z = true;
        }
        Logger logger = LOGGER;
        logger.info("Valid OCSPs found: " + i);
        if (i > 0) {
            Class<?> cls = getClass();
            StringBuilder sb = new StringBuilder();
            sb.append("Valid OCSPs Found: ");
            sb.append(i);
            sb.append(z ? " (online)" : "");
            arrayList.add(new VerificationOK(x509Certificate, cls, sb.toString()));
        }
        if (this.verifier != null) {
            arrayList.addAll(this.verifier.verify(x509Certificate, x509Certificate2, date));
        }
        return arrayList;
    }

    public boolean verify(BasicOCSPResp basicOCSPResp, X509Certificate x509Certificate, X509Certificate x509Certificate2, Date date) throws GeneralSecurityException, IOException {
        if (basicOCSPResp == null) {
            return false;
        }
        SingleResp[] responses = basicOCSPResp.getResponses();
        for (int i = 0; i < responses.length; i++) {
            if (x509Certificate.getSerialNumber().equals(responses[i].getCertID().getSerialNumber())) {
                if (x509Certificate2 == null) {
                    x509Certificate2 = x509Certificate;
                }
                try {
                    if (!responses[i].getCertID().matchesIssuer(new X509CertificateHolder(x509Certificate2.getEncoded()), new BcDigestCalculatorProvider())) {
                        LOGGER.info("OCSP: Issuers doesn't match.");
                    } else {
                        Date nextUpdate = responses[i].getNextUpdate();
                        if (nextUpdate == null) {
                            nextUpdate = new Date(responses[i].getThisUpdate().getTime() + 180000);
                            LOGGER.info(String.format("No 'next update' for OCSP Response; assuming %s", new Object[]{nextUpdate}));
                        }
                        if (date.after(nextUpdate)) {
                            LOGGER.info(String.format("OCSP no longer valid: %s after %s", new Object[]{date, nextUpdate}));
                        } else if (responses[i].getCertStatus() == CertificateStatus.GOOD) {
                            isValidResponse(basicOCSPResp, x509Certificate2);
                            return true;
                        }
                    }
                } catch (OCSPException unused) {
                    continue;
                }
            }
        }
        return false;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void isValidResponse(org.bouncycastle.cert.ocsp.BasicOCSPResp r10, java.security.cert.X509Certificate r11) throws java.security.GeneralSecurityException, java.io.IOException {
        /*
            r9 = this;
            boolean r0 = r9.isSignatureValid(r10, r11)
            r1 = 0
            if (r0 == 0) goto L_0x0009
            r0 = r11
            goto L_0x000a
        L_0x0009:
            r0 = r1
        L_0x000a:
            if (r0 != 0) goto L_0x0082
            org.bouncycastle.cert.X509CertificateHolder[] r2 = r10.getCerts()
            java.lang.String r3 = "OCSP response could not be verified"
            if (r2 == 0) goto L_0x0049
            org.bouncycastle.cert.X509CertificateHolder[] r2 = r10.getCerts()
            int r4 = r2.length
            r5 = 0
        L_0x001a:
            if (r5 >= r4) goto L_0x0040
            r6 = r2[r5]
            org.bouncycastle.cert.jcajce.JcaX509CertificateConverter r7 = new org.bouncycastle.cert.jcajce.JcaX509CertificateConverter     // Catch:{ Exception -> 0x003d }
            r7.<init>()     // Catch:{ Exception -> 0x003d }
            java.security.cert.X509Certificate r6 = r7.getCertificate(r6)     // Catch:{ Exception -> 0x003d }
            java.util.List r7 = r6.getExtendedKeyUsage()     // Catch:{  }
            if (r7 == 0) goto L_0x003d
            java.lang.String r8 = "1.3.6.1.5.5.7.3.9"
            boolean r7 = r7.contains(r8)     // Catch:{  }
            if (r7 == 0) goto L_0x003d
            boolean r7 = r9.isSignatureValid(r10, r6)     // Catch:{  }
            if (r7 == 0) goto L_0x003d
            r0 = r6
            goto L_0x0040
        L_0x003d:
            int r5 = r5 + 1
            goto L_0x001a
        L_0x0040:
            if (r0 == 0) goto L_0x0043
            goto L_0x0082
        L_0x0043:
            com.itextpdf.text.pdf.security.VerificationException r10 = new com.itextpdf.text.pdf.security.VerificationException
            r10.<init>(r11, r3)
            throw r10
        L_0x0049:
            java.security.KeyStore r2 = r9.rootStore
            if (r2 == 0) goto L_0x0079
            java.security.KeyStore r2 = r9.rootStore     // Catch:{ KeyStoreException -> 0x0078 }
            java.util.Enumeration r2 = r2.aliases()     // Catch:{ KeyStoreException -> 0x0078 }
        L_0x0053:
            boolean r4 = r2.hasMoreElements()     // Catch:{ KeyStoreException -> 0x0078 }
            if (r4 == 0) goto L_0x0079
            java.lang.Object r4 = r2.nextElement()     // Catch:{ KeyStoreException -> 0x0078 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ KeyStoreException -> 0x0078 }
            java.security.KeyStore r5 = r9.rootStore     // Catch:{ GeneralSecurityException -> 0x0053 }
            boolean r5 = r5.isCertificateEntry(r4)     // Catch:{ GeneralSecurityException -> 0x0053 }
            if (r5 != 0) goto L_0x0068
            goto L_0x0053
        L_0x0068:
            java.security.KeyStore r5 = r9.rootStore     // Catch:{ GeneralSecurityException -> 0x0053 }
            java.security.cert.Certificate r4 = r5.getCertificate(r4)     // Catch:{ GeneralSecurityException -> 0x0053 }
            java.security.cert.X509Certificate r4 = (java.security.cert.X509Certificate) r4     // Catch:{ GeneralSecurityException -> 0x0053 }
            boolean r5 = r9.isSignatureValid(r10, r4)     // Catch:{ GeneralSecurityException -> 0x0053 }
            if (r5 == 0) goto L_0x0053
            r0 = r4
            goto L_0x0079
        L_0x0078:
            r0 = r1
        L_0x0079:
            if (r0 == 0) goto L_0x007c
            goto L_0x0082
        L_0x007c:
            com.itextpdf.text.pdf.security.VerificationException r10 = new com.itextpdf.text.pdf.security.VerificationException
            r10.<init>(r11, r3)
            throw r10
        L_0x0082:
            java.security.PublicKey r10 = r11.getPublicKey()
            r0.verify(r10)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers.id_pkix_ocsp_nocheck
            java.lang.String r10 = r10.getId()
            byte[] r10 = r0.getExtensionValue(r10)
            if (r10 != 0) goto L_0x00bb
            java.security.cert.CRL r10 = com.itextpdf.text.pdf.security.CertificateUtil.getCRL((java.security.cert.X509Certificate) r0)     // Catch:{ Exception -> 0x009a }
            goto L_0x009b
        L_0x009a:
            r10 = r1
        L_0x009b:
            if (r10 == 0) goto L_0x00bb
            boolean r2 = r10 instanceof java.security.cert.X509CRL
            if (r2 == 0) goto L_0x00bb
            com.itextpdf.text.pdf.security.CRLVerifier r2 = new com.itextpdf.text.pdf.security.CRLVerifier
            r2.<init>(r1, r1)
            java.security.KeyStore r1 = r9.rootStore
            r2.setRootStore(r1)
            boolean r1 = r9.onlineCheckingAllowed
            r2.setOnlineCheckingAllowed(r1)
            java.security.cert.X509CRL r10 = (java.security.cert.X509CRL) r10
            java.util.Date r1 = new java.util.Date
            r1.<init>()
            r2.verify(r10, r0, r11, r1)
            return
        L_0x00bb:
            r0.checkValidity()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.security.OCSPVerifier.isValidResponse(org.bouncycastle.cert.ocsp.BasicOCSPResp, java.security.cert.X509Certificate):void");
    }

    @Deprecated
    public boolean verifyResponse(BasicOCSPResp basicOCSPResp, X509Certificate x509Certificate) {
        try {
            isValidResponse(basicOCSPResp, x509Certificate);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean isSignatureValid(BasicOCSPResp basicOCSPResp, Certificate certificate) {
        try {
            return basicOCSPResp.isSignatureValid(new JcaContentVerifierProviderBuilder().setProvider("BC").build(certificate.getPublicKey()));
        } catch (OCSPException | OperatorCreationException unused) {
            return false;
        }
    }

    public BasicOCSPResp getOcspResponse(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        BasicOCSPResp basicOCSPResp;
        if ((x509Certificate == null && x509Certificate2 == null) || (basicOCSPResp = new OcspClientBouncyCastle().getBasicOCSPResp(x509Certificate, x509Certificate2, (String) null)) == null) {
            return null;
        }
        SingleResp[] responses = basicOCSPResp.getResponses();
        for (SingleResp certStatus : responses) {
            if (certStatus.getCertStatus() == CertificateStatus.GOOD) {
                return basicOCSPResp;
            }
        }
        return null;
    }
}
