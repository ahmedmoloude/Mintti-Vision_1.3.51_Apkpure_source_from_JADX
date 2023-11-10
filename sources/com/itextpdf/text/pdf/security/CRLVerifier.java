package com.itextpdf.text.pdf.security;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class CRLVerifier extends RootStoreVerifier {
    protected static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) CRLVerifier.class);
    List<X509CRL> crls;

    public CRLVerifier(CertificateVerifier certificateVerifier, List<X509CRL> list) {
        super(certificateVerifier);
        this.crls = list;
    }

    public List<VerificationOK> verify(X509Certificate x509Certificate, X509Certificate x509Certificate2, Date date) throws GeneralSecurityException, IOException {
        int i;
        ArrayList arrayList = new ArrayList();
        List<X509CRL> list = this.crls;
        boolean z = false;
        if (list != null) {
            i = 0;
            for (X509CRL verify : list) {
                if (verify(verify, x509Certificate, x509Certificate2, date)) {
                    i++;
                }
            }
        } else {
            i = 0;
        }
        if (this.onlineCheckingAllowed && i == 0 && verify(getCRL(x509Certificate, x509Certificate2), x509Certificate, x509Certificate2, date)) {
            i++;
            z = true;
        }
        Logger logger = LOGGER;
        logger.info("Valid CRLs found: " + i);
        if (i > 0) {
            Class<?> cls = getClass();
            StringBuilder sb = new StringBuilder();
            sb.append("Valid CRLs found: ");
            sb.append(i);
            sb.append(z ? " (online)" : "");
            arrayList.add(new VerificationOK(x509Certificate, cls, sb.toString()));
        }
        if (this.verifier != null) {
            arrayList.addAll(this.verifier.verify(x509Certificate, x509Certificate2, date));
        }
        return arrayList;
    }

    public boolean verify(X509CRL x509crl, X509Certificate x509Certificate, X509Certificate x509Certificate2, Date date) throws GeneralSecurityException {
        if (x509crl == null || date == null || !x509crl.getIssuerX500Principal().equals(x509Certificate.getIssuerX500Principal()) || !date.after(x509crl.getThisUpdate()) || !date.before(x509crl.getNextUpdate())) {
            return false;
        }
        if (!isSignatureValid(x509crl, x509Certificate2) || !x509crl.isRevoked(x509Certificate)) {
            return true;
        }
        throw new VerificationException(x509Certificate, "The certificate has been revoked.");
    }

    public X509CRL getCRL(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        try {
            String crlurl = CertificateUtil.getCRLURL(x509Certificate);
            if (crlurl == null) {
                return null;
            }
            Logger logger = LOGGER;
            logger.info("Getting CRL from " + crlurl);
            return (X509CRL) CertificateFactory.getInstance("X.509").generateCRL(new URL(crlurl).openStream());
        } catch (IOException | GeneralSecurityException unused) {
            return null;
        }
    }

    public boolean isSignatureValid(X509CRL x509crl, X509Certificate x509Certificate) {
        if (x509Certificate != null) {
            try {
                x509crl.verify(x509Certificate.getPublicKey());
                return true;
            } catch (GeneralSecurityException unused) {
                LOGGER.warn("CRL not issued by the same authority as the certificate that is being checked");
            }
        }
        if (this.rootStore == null) {
            return false;
        }
        try {
            Enumeration<String> aliases = this.rootStore.aliases();
            while (aliases.hasMoreElements()) {
                String nextElement = aliases.nextElement();
                try {
                    if (this.rootStore.isCertificateEntry(nextElement)) {
                        x509crl.verify(((X509Certificate) this.rootStore.getCertificate(nextElement)).getPublicKey());
                        return true;
                    }
                } catch (GeneralSecurityException unused2) {
                }
            }
        } catch (GeneralSecurityException unused3) {
        }
        return false;
    }
}
