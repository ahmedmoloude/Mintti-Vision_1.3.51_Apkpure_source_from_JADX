package com.itextpdf.text.pdf.security;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.LtvVerification;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPResp;

public class LtvVerifier extends RootStoreVerifier {
    protected static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) LtvVerifier.class);
    protected PdfDictionary dss;
    protected AcroFields fields;
    protected boolean latestRevision = true;
    protected LtvVerification.CertificateOption option = LtvVerification.CertificateOption.SIGNING_CERTIFICATE;
    protected PdfPKCS7 pkcs7;
    protected PdfReader reader;
    protected Date signDate;
    protected String signatureName;
    protected boolean verifyRootCertificate = true;

    public LtvVerifier(PdfReader pdfReader) throws GeneralSecurityException {
        super((CertificateVerifier) null);
        this.reader = pdfReader;
        AcroFields acroFields = pdfReader.getAcroFields();
        this.fields = acroFields;
        ArrayList<String> signatureNames = acroFields.getSignatureNames();
        this.signatureName = signatureNames.get(signatureNames.size() - 1);
        this.signDate = new Date();
        PdfPKCS7 coversWholeDocument = coversWholeDocument();
        this.pkcs7 = coversWholeDocument;
        Logger logger = LOGGER;
        Object[] objArr = new Object[2];
        objArr[0] = coversWholeDocument.isTsp() ? "document-level timestamp " : "";
        objArr[1] = this.signatureName;
        logger.info(String.format("Checking %ssignature %s", objArr));
    }

    public void setVerifier(CertificateVerifier certificateVerifier) {
        this.verifier = certificateVerifier;
    }

    public void setCertificateOption(LtvVerification.CertificateOption certificateOption) {
        this.option = certificateOption;
    }

    public void setVerifyRootCertificate(boolean z) {
        this.verifyRootCertificate = z;
    }

    /* access modifiers changed from: protected */
    public PdfPKCS7 coversWholeDocument() throws GeneralSecurityException {
        PdfPKCS7 verifySignature = this.fields.verifySignature(this.signatureName);
        if (this.fields.signatureCoversWholeDocument(this.signatureName)) {
            Logger logger = LOGGER;
            logger.info("The timestamp covers whole document.");
            if (verifySignature.verify()) {
                logger.info("The signed document has not been modified.");
                return verifySignature;
            }
            throw new VerificationException((Certificate) null, "The document was altered after the final signature was applied.");
        }
        throw new VerificationException((Certificate) null, "Signature doesn't cover whole document.");
    }

    public List<VerificationOK> verify(List<VerificationOK> list) throws IOException, GeneralSecurityException {
        if (list == null) {
            list = new ArrayList<>();
        }
        while (this.pkcs7 != null) {
            list.addAll(verifySignature());
        }
        return list;
    }

    public List<VerificationOK> verifySignature() throws GeneralSecurityException, IOException {
        LOGGER.info("Verifying signature.");
        ArrayList arrayList = new ArrayList();
        Certificate[] signCertificateChain = this.pkcs7.getSignCertificateChain();
        verifyChain(signCertificateChain);
        int length = LtvVerification.CertificateOption.WHOLE_CHAIN.equals(this.option) ? signCertificateChain.length : 1;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            X509Certificate x509Certificate = (X509Certificate) signCertificateChain[i];
            X509Certificate x509Certificate2 = null;
            if (i2 < signCertificateChain.length) {
                x509Certificate2 = (X509Certificate) signCertificateChain[i2];
            }
            LOGGER.info(x509Certificate.getSubjectDN().getName());
            List<VerificationOK> verify = verify(x509Certificate, x509Certificate2, this.signDate);
            if (verify.size() == 0) {
                try {
                    x509Certificate.verify(x509Certificate.getPublicKey());
                    if (this.latestRevision && signCertificateChain.length > 1) {
                        verify.add(new VerificationOK(x509Certificate, getClass(), "Root certificate in final revision"));
                    }
                    if (verify.size() == 0) {
                        if (this.verifyRootCertificate) {
                            throw new GeneralSecurityException();
                        }
                    }
                    if (signCertificateChain.length > 1) {
                        verify.add(new VerificationOK(x509Certificate, getClass(), "Root certificate passed without checking"));
                    }
                } catch (GeneralSecurityException unused) {
                    throw new VerificationException(x509Certificate, "Couldn't verify with CRL or OCSP or trusted anchor");
                }
            }
            arrayList.addAll(verify);
            i = i2;
        }
        switchToPreviousRevision();
        return arrayList;
    }

    public void verifyChain(Certificate[] certificateArr) throws GeneralSecurityException {
        for (int i = 0; i < certificateArr.length; i++) {
            certificateArr[i].checkValidity(this.signDate);
            if (i > 0) {
                certificateArr[i - 1].verify(certificateArr[i].getPublicKey());
            }
        }
        Logger logger = LOGGER;
        logger.info("All certificates are valid on " + this.signDate.toString());
    }

    public List<VerificationOK> verify(X509Certificate x509Certificate, X509Certificate x509Certificate2, Date date) throws GeneralSecurityException, IOException {
        RootStoreVerifier rootStoreVerifier = new RootStoreVerifier(this.verifier);
        rootStoreVerifier.setRootStore(this.rootStore);
        CRLVerifier cRLVerifier = new CRLVerifier(rootStoreVerifier, getCRLsFromDSS());
        cRLVerifier.setRootStore(this.rootStore);
        boolean z = false;
        cRLVerifier.setOnlineCheckingAllowed(this.latestRevision || this.onlineCheckingAllowed);
        OCSPVerifier oCSPVerifier = new OCSPVerifier(cRLVerifier, getOCSPResponsesFromDSS());
        oCSPVerifier.setRootStore(this.rootStore);
        if (this.latestRevision || this.onlineCheckingAllowed) {
            z = true;
        }
        oCSPVerifier.setOnlineCheckingAllowed(z);
        return oCSPVerifier.verify(x509Certificate, x509Certificate2, date);
    }

    public void switchToPreviousRevision() throws IOException, GeneralSecurityException {
        Logger logger = LOGGER;
        logger.info("Switching to previous revision.");
        this.latestRevision = false;
        this.dss = this.reader.getCatalog().getAsDict(PdfName.DSS);
        Calendar timeStampDate = this.pkcs7.getTimeStampDate();
        if (timeStampDate == null) {
            timeStampDate = this.pkcs7.getSignDate();
        }
        this.signDate = timeStampDate.getTime();
        ArrayList<String> signatureNames = this.fields.getSignatureNames();
        if (signatureNames.size() > 1) {
            this.signatureName = signatureNames.get(signatureNames.size() - 2);
            PdfReader pdfReader = new PdfReader(this.fields.extractRevision(this.signatureName));
            this.reader = pdfReader;
            AcroFields acroFields = pdfReader.getAcroFields();
            this.fields = acroFields;
            ArrayList<String> signatureNames2 = acroFields.getSignatureNames();
            this.signatureName = signatureNames2.get(signatureNames2.size() - 1);
            PdfPKCS7 coversWholeDocument = coversWholeDocument();
            this.pkcs7 = coversWholeDocument;
            Object[] objArr = new Object[2];
            objArr[0] = coversWholeDocument.isTsp() ? "document-level timestamp " : "";
            objArr[1] = this.signatureName;
            logger.info(String.format("Checking %ssignature %s", objArr));
            return;
        }
        logger.info("No signatures in revision");
        this.pkcs7 = null;
    }

    public List<X509CRL> getCRLsFromDSS() throws GeneralSecurityException, IOException {
        PdfArray asArray;
        ArrayList arrayList = new ArrayList();
        PdfDictionary pdfDictionary = this.dss;
        if (pdfDictionary == null || (asArray = pdfDictionary.getAsArray(PdfName.CRLS)) == null) {
            return arrayList;
        }
        CertificateFactory instance = CertificateFactory.getInstance("X.509");
        for (int i = 0; i < asArray.size(); i++) {
            arrayList.add((X509CRL) instance.generateCRL(new ByteArrayInputStream(PdfReader.getStreamBytes((PRStream) asArray.getAsStream(i)))));
        }
        return arrayList;
    }

    public List<BasicOCSPResp> getOCSPResponsesFromDSS() throws IOException, GeneralSecurityException {
        PdfArray asArray;
        ArrayList arrayList = new ArrayList();
        PdfDictionary pdfDictionary = this.dss;
        if (pdfDictionary == null || (asArray = pdfDictionary.getAsArray(PdfName.OCSPS)) == null) {
            return arrayList;
        }
        for (int i = 0; i < asArray.size(); i++) {
            OCSPResp oCSPResp = new OCSPResp(PdfReader.getStreamBytes((PRStream) asArray.getAsStream(i)));
            if (oCSPResp.getStatus() == 0) {
                try {
                    arrayList.add((BasicOCSPResp) oCSPResp.getResponseObject());
                } catch (OCSPException e) {
                    throw new GeneralSecurityException(e);
                }
            }
        }
        return arrayList;
    }
}
