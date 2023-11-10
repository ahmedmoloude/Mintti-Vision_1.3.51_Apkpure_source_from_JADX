package com.itextpdf.text.pdf.security;

import java.security.KeyStore;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.tsp.TimeStampToken;

public class CertificateVerification {
    public static String verifyCertificate(X509Certificate x509Certificate, Collection<CRL> collection, Calendar calendar) {
        if (calendar == null) {
            calendar = new GregorianCalendar();
        }
        if (x509Certificate.hasUnsupportedCriticalExtension()) {
            for (String str : x509Certificate.getCriticalExtensionOIDs()) {
                if (!"2.5.29.15".equals(str) || !x509Certificate.getKeyUsage()[0]) {
                    try {
                        if (!"2.5.29.37".equals(str) || !x509Certificate.getExtendedKeyUsage().contains("1.3.6.1.5.5.7.3.8")) {
                            return "Has unsupported critical extension";
                        }
                    } catch (CertificateParsingException unused) {
                        return "Has unsupported critical extension";
                    }
                }
            }
        }
        try {
            x509Certificate.checkValidity(calendar.getTime());
            if (collection == null) {
                return null;
            }
            for (CRL isRevoked : collection) {
                if (isRevoked.isRevoked(x509Certificate)) {
                    return "Certificate revoked";
                }
            }
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static List<VerificationException> verifyCertificates(Certificate[] certificateArr, KeyStore keyStore, Collection<CRL> collection, Calendar calendar) {
        ArrayList arrayList = new ArrayList();
        if (calendar == null) {
            calendar = new GregorianCalendar();
        }
        for (int i = 0; i < certificateArr.length; i++) {
            X509Certificate x509Certificate = certificateArr[i];
            String verifyCertificate = verifyCertificate(x509Certificate, collection, calendar);
            if (verifyCertificate != null) {
                arrayList.add(new VerificationException(x509Certificate, verifyCertificate));
            }
            try {
                Enumeration<String> aliases = keyStore.aliases();
                while (aliases.hasMoreElements()) {
                    try {
                        String nextElement = aliases.nextElement();
                        if (keyStore.isCertificateEntry(nextElement)) {
                            X509Certificate x509Certificate2 = (X509Certificate) keyStore.getCertificate(nextElement);
                            if (verifyCertificate(x509Certificate2, collection, calendar) == null) {
                                x509Certificate.verify(x509Certificate2.getPublicKey());
                                return arrayList;
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
            } catch (Exception unused2) {
            }
            int i2 = 0;
            while (i2 < certificateArr.length) {
                if (i2 != i) {
                    try {
                        x509Certificate.verify(certificateArr[i2].getPublicKey());
                        break;
                    } catch (Exception unused3) {
                        continue;
                    }
                }
                i2++;
            }
            if (i2 == certificateArr.length) {
                arrayList.add(new VerificationException(x509Certificate, "Cannot be verified against the KeyStore or the certificate chain"));
            }
        }
        if (arrayList.size() == 0) {
            arrayList.add(new VerificationException((Certificate) null, "Invalid state. Possible circular certificate chain"));
        }
        return arrayList;
    }

    public static List<VerificationException> verifyCertificates(Certificate[] certificateArr, KeyStore keyStore, Calendar calendar) {
        return verifyCertificates(certificateArr, keyStore, (Collection<CRL>) null, calendar);
    }

    public static boolean verifyOcspCertificates(BasicOCSPResp basicOCSPResp, KeyStore keyStore, String str) {
        if (str == null) {
            str = "BC";
        }
        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                try {
                    String nextElement = aliases.nextElement();
                    if (keyStore.isCertificateEntry(nextElement)) {
                        if (basicOCSPResp.isSignatureValid(new JcaContentVerifierProviderBuilder().setProvider(str).build(((X509Certificate) keyStore.getCertificate(nextElement)).getPublicKey()))) {
                            return true;
                        }
                    }
                } catch (Exception unused) {
                }
            }
            return false;
        } catch (Exception unused2) {
            return false;
        }
    }

    public static boolean verifyTimestampCertificates(TimeStampToken timeStampToken, KeyStore keyStore, String str) {
        if (str == null) {
            str = "BC";
        }
        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                try {
                    String nextElement = aliases.nextElement();
                    if (keyStore.isCertificateEntry(nextElement)) {
                        timeStampToken.isSignatureValid(new JcaSimpleSignerInfoVerifierBuilder().setProvider(str).build((X509Certificate) keyStore.getCertificate(nextElement)));
                        return true;
                    }
                } catch (Exception unused) {
                }
            }
            return false;
        } catch (Exception unused2) {
            return false;
        }
    }
}
