package com.itextpdf.text.pdf.security;

import com.itextpdf.text.Utilities;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDeveloperExtension;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfIndirectReference;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfStream;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;

public class LtvVerification {
    private Logger LOGGER = LoggerFactory.getLogger((Class<?>) LtvVerification.class);
    private AcroFields acroFields;
    private PdfReader reader;
    private PdfStamper stp;
    private boolean used = false;
    private Map<PdfName, ValidationData> validated = new HashMap();
    private PdfWriter writer;

    public enum CertificateInclusion {
        YES,
        NO
    }

    public enum CertificateOption {
        SIGNING_CERTIFICATE,
        WHOLE_CHAIN
    }

    public enum Level {
        OCSP,
        CRL,
        OCSP_CRL,
        OCSP_OPTIONAL_CRL
    }

    public LtvVerification(PdfStamper pdfStamper) {
        this.stp = pdfStamper;
        this.writer = pdfStamper.getWriter();
        this.reader = pdfStamper.getReader();
        this.acroFields = pdfStamper.getAcroFields();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v10, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v12, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v13, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean addVerification(java.lang.String r18, com.itextpdf.text.pdf.security.OcspClient r19, com.itextpdf.text.pdf.security.CrlClient r20, com.itextpdf.text.pdf.security.LtvVerification.CertificateOption r21, com.itextpdf.text.pdf.security.LtvVerification.Level r22, com.itextpdf.text.pdf.security.LtvVerification.CertificateInclusion r23) throws java.io.IOException, java.security.GeneralSecurityException {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r22
            boolean r5 = r0.used
            r6 = 0
            if (r5 != 0) goto L_0x0112
            com.itextpdf.text.pdf.AcroFields r5 = r0.acroFields
            com.itextpdf.text.pdf.security.PdfPKCS7 r5 = r5.verifySignature(r1)
            com.itextpdf.text.log.Logger r7 = r0.LOGGER
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Adding verification for "
            r8.append(r9)
            r8.append(r1)
            java.lang.String r8 = r8.toString()
            r7.info(r8)
            java.security.cert.Certificate[] r7 = r5.getCertificates()
            java.security.cert.X509Certificate r5 = r5.getSigningCertificate()
            com.itextpdf.text.pdf.security.LtvVerification$ValidationData r8 = new com.itextpdf.text.pdf.security.LtvVerification$ValidationData
            r9 = 0
            r8.<init>()
            r10 = 0
        L_0x003a:
            int r11 = r7.length
            if (r10 >= r11) goto L_0x00f6
            r11 = r7[r10]
            java.security.cert.X509Certificate r11 = (java.security.cert.X509Certificate) r11
            com.itextpdf.text.log.Logger r13 = r0.LOGGER
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "Certificate: "
            r14.append(r15)
            java.security.Principal r15 = r11.getSubjectDN()
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            r13.info(r14)
            com.itextpdf.text.pdf.security.LtvVerification$CertificateOption r13 = com.itextpdf.text.pdf.security.LtvVerification.CertificateOption.SIGNING_CERTIFICATE
            r14 = r21
            if (r14 != r13) goto L_0x006b
            boolean r13 = r11.equals(r5)
            if (r13 != 0) goto L_0x006b
            r12 = r23
            goto L_0x00f1
        L_0x006b:
            if (r2 == 0) goto L_0x008c
            com.itextpdf.text.pdf.security.LtvVerification$Level r13 = com.itextpdf.text.pdf.security.LtvVerification.Level.CRL
            if (r4 == r13) goto L_0x008c
            java.security.cert.X509Certificate r13 = r0.getParent(r11, r7)
            byte[] r13 = r2.getEncoded(r11, r13, r9)
            if (r13 == 0) goto L_0x008d
            java.util.List<byte[]> r15 = r8.ocsps
            byte[] r12 = buildOCSPResponse(r13)
            r15.add(r12)
            com.itextpdf.text.log.Logger r12 = r0.LOGGER
            java.lang.String r15 = "OCSP added"
            r12.info(r15)
            goto L_0x008d
        L_0x008c:
            r13 = r9
        L_0x008d:
            if (r3 == 0) goto L_0x00e2
            com.itextpdf.text.pdf.security.LtvVerification$Level r12 = com.itextpdf.text.pdf.security.LtvVerification.Level.CRL
            if (r4 == r12) goto L_0x009d
            com.itextpdf.text.pdf.security.LtvVerification$Level r12 = com.itextpdf.text.pdf.security.LtvVerification.Level.OCSP_CRL
            if (r4 == r12) goto L_0x009d
            com.itextpdf.text.pdf.security.LtvVerification$Level r12 = com.itextpdf.text.pdf.security.LtvVerification.Level.OCSP_OPTIONAL_CRL
            if (r4 != r12) goto L_0x00e2
            if (r13 != 0) goto L_0x00e2
        L_0x009d:
            java.util.Collection r12 = r3.getEncoded(r11, r9)
            if (r12 == 0) goto L_0x00e2
            java.util.Iterator r12 = r12.iterator()
        L_0x00a7:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x00e2
            java.lang.Object r13 = r12.next()
            byte[] r13 = (byte[]) r13
            java.util.List<byte[]> r15 = r8.crls
            java.util.Iterator r15 = r15.iterator()
        L_0x00b9:
            boolean r16 = r15.hasNext()
            if (r16 == 0) goto L_0x00d1
            java.lang.Object r16 = r15.next()
            r9 = r16
            byte[] r9 = (byte[]) r9
            boolean r9 = java.util.Arrays.equals(r9, r13)
            if (r9 == 0) goto L_0x00cf
            r9 = 1
            goto L_0x00d2
        L_0x00cf:
            r9 = 0
            goto L_0x00b9
        L_0x00d1:
            r9 = 0
        L_0x00d2:
            if (r9 != 0) goto L_0x00e0
            java.util.List<byte[]> r9 = r8.crls
            r9.add(r13)
            com.itextpdf.text.log.Logger r9 = r0.LOGGER
            java.lang.String r13 = "CRL added"
            r9.info(r13)
        L_0x00e0:
            r9 = 0
            goto L_0x00a7
        L_0x00e2:
            com.itextpdf.text.pdf.security.LtvVerification$CertificateInclusion r9 = com.itextpdf.text.pdf.security.LtvVerification.CertificateInclusion.YES
            r12 = r23
            if (r12 != r9) goto L_0x00f1
            java.util.List<byte[]> r9 = r8.certs
            byte[] r11 = r11.getEncoded()
            r9.add(r11)
        L_0x00f1:
            int r10 = r10 + 1
            r9 = 0
            goto L_0x003a
        L_0x00f6:
            java.util.List<byte[]> r2 = r8.crls
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0107
            java.util.List<byte[]> r2 = r8.ocsps
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0107
            return r6
        L_0x0107:
            java.util.Map<com.itextpdf.text.pdf.PdfName, com.itextpdf.text.pdf.security.LtvVerification$ValidationData> r2 = r0.validated
            com.itextpdf.text.pdf.PdfName r1 = r17.getSignatureHashKey(r18)
            r2.put(r1, r8)
            r1 = 1
            return r1
        L_0x0112:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.Object[] r2 = new java.lang.Object[r6]
            java.lang.String r3 = "verification.already.output"
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r2)
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.security.LtvVerification.addVerification(java.lang.String, com.itextpdf.text.pdf.security.OcspClient, com.itextpdf.text.pdf.security.CrlClient, com.itextpdf.text.pdf.security.LtvVerification$CertificateOption, com.itextpdf.text.pdf.security.LtvVerification$Level, com.itextpdf.text.pdf.security.LtvVerification$CertificateInclusion):boolean");
    }

    private X509Certificate getParent(X509Certificate x509Certificate, Certificate[] certificateArr) {
        for (X509Certificate x509Certificate2 : certificateArr) {
            if (x509Certificate.getIssuerDN().equals(x509Certificate2.getSubjectDN())) {
                try {
                    x509Certificate.verify(x509Certificate2.getPublicKey());
                    return x509Certificate2;
                } catch (Exception unused) {
                    continue;
                }
            }
        }
        return null;
    }

    public boolean addVerification(String str, Collection<byte[]> collection, Collection<byte[]> collection2, Collection<byte[]> collection3) throws IOException, GeneralSecurityException {
        if (!this.used) {
            ValidationData validationData = new ValidationData();
            if (collection != null) {
                for (byte[] buildOCSPResponse : collection) {
                    validationData.ocsps.add(buildOCSPResponse(buildOCSPResponse));
                }
            }
            if (collection2 != null) {
                for (byte[] add : collection2) {
                    validationData.crls.add(add);
                }
            }
            if (collection3 != null) {
                for (byte[] add2 : collection3) {
                    validationData.certs.add(add2);
                }
            }
            this.validated.put(getSignatureHashKey(str), validationData);
            return true;
        }
        throw new IllegalStateException(MessageLocalization.getComposedMessage("verification.already.output", new Object[0]));
    }

    private static byte[] buildOCSPResponse(byte[] bArr) throws IOException {
        DEROctetString dEROctetString = new DEROctetString(bArr);
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(OCSPObjectIdentifiers.id_pkix_ocsp_basic);
        aSN1EncodableVector.add(dEROctetString);
        ASN1Enumerated aSN1Enumerated = new ASN1Enumerated(0);
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        aSN1EncodableVector2.add(aSN1Enumerated);
        aSN1EncodableVector2.add(new DERTaggedObject(true, 0, new DERSequence(aSN1EncodableVector)));
        return new DERSequence(aSN1EncodableVector2).getEncoded();
    }

    private PdfName getSignatureHashKey(String str) throws NoSuchAlgorithmException, IOException {
        PdfDictionary signatureDictionary = this.acroFields.getSignatureDictionary(str);
        byte[] originalBytes = signatureDictionary.getAsString(PdfName.CONTENTS).getOriginalBytes();
        if (PdfName.ETSI_RFC3161.equals(PdfReader.getPdfObject(signatureDictionary.get(PdfName.SUBFILTER)))) {
            originalBytes = new ASN1InputStream(new ByteArrayInputStream(originalBytes)).readObject().getEncoded();
        }
        return new PdfName(Utilities.convertToHex(hashBytesSha1(originalBytes)));
    }

    private static byte[] hashBytesSha1(byte[] bArr) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(SecurityConstants.SHA1).digest(bArr);
    }

    public void merge() throws IOException {
        if (!this.used && !this.validated.isEmpty()) {
            this.used = true;
            if (this.reader.getCatalog().get(PdfName.DSS) == null) {
                createDss();
            } else {
                updateDss();
            }
        }
    }

    private void updateDss() throws IOException {
        PdfDictionary asDict;
        PdfDictionary catalog = this.reader.getCatalog();
        this.stp.markUsed(catalog);
        PdfDictionary asDict2 = catalog.getAsDict(PdfName.DSS);
        PdfArray asArray = asDict2.getAsArray(PdfName.OCSPS);
        PdfArray asArray2 = asDict2.getAsArray(PdfName.CRLS);
        PdfArray asArray3 = asDict2.getAsArray(PdfName.CERTS);
        asDict2.remove(PdfName.OCSPS);
        asDict2.remove(PdfName.CRLS);
        asDict2.remove(PdfName.CERTS);
        PdfDictionary asDict3 = asDict2.getAsDict(PdfName.VRI);
        if (asDict3 != null) {
            for (PdfName next : asDict3.getKeys()) {
                if (this.validated.containsKey(next) && (asDict = asDict3.getAsDict(next)) != null) {
                    deleteOldReferences(asArray, asDict.getAsArray(PdfName.OCSP));
                    deleteOldReferences(asArray2, asDict.getAsArray(PdfName.CRL));
                    deleteOldReferences(asArray3, asDict.getAsArray(PdfName.CERT));
                }
            }
        }
        if (asArray == null) {
            asArray = new PdfArray();
        }
        outputDss(asDict2, asDict3, asArray, asArray2 == null ? new PdfArray() : asArray2, asArray3 == null ? new PdfArray() : asArray3);
    }

    private static void deleteOldReferences(PdfArray pdfArray, PdfArray pdfArray2) {
        if (pdfArray != null && pdfArray2 != null) {
            Iterator<PdfObject> it = pdfArray2.iterator();
            while (it.hasNext()) {
                PdfObject next = it.next();
                if (next.isIndirect()) {
                    PRIndirectReference pRIndirectReference = (PRIndirectReference) next;
                    int i = 0;
                    while (i < pdfArray.size()) {
                        PdfObject pdfObject = pdfArray.getPdfObject(i);
                        if (pdfObject.isIndirect() && pRIndirectReference.getNumber() == ((PRIndirectReference) pdfObject).getNumber()) {
                            pdfArray.remove(i);
                            i--;
                        }
                        i++;
                    }
                }
            }
        }
    }

    private void createDss() throws IOException {
        outputDss(new PdfDictionary(), new PdfDictionary(), new PdfArray(), new PdfArray(), new PdfArray());
    }

    private void outputDss(PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2, PdfArray pdfArray, PdfArray pdfArray2, PdfArray pdfArray3) throws IOException {
        PdfDictionary pdfDictionary3 = pdfDictionary;
        PdfDictionary pdfDictionary4 = pdfDictionary2;
        PdfArray pdfArray4 = pdfArray;
        PdfArray pdfArray5 = pdfArray2;
        PdfArray pdfArray6 = pdfArray3;
        this.writer.addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL5);
        PdfDictionary catalog = this.reader.getCatalog();
        this.stp.markUsed(catalog);
        Iterator<PdfName> it = this.validated.keySet().iterator();
        while (it.hasNext()) {
            PdfName next = it.next();
            PdfArray pdfArray7 = new PdfArray();
            PdfArray pdfArray8 = new PdfArray();
            PdfArray pdfArray9 = new PdfArray();
            PdfDictionary pdfDictionary5 = new PdfDictionary();
            for (byte[] pdfStream : this.validated.get(next).crls) {
                PdfStream pdfStream2 = new PdfStream(pdfStream);
                pdfStream2.flateCompress();
                Iterator<PdfName> it2 = it;
                PdfIndirectReference indirectReference = this.writer.addToBody((PdfObject) pdfStream2, false).getIndirectReference();
                pdfArray8.add((PdfObject) indirectReference);
                pdfArray5.add((PdfObject) indirectReference);
                it = it2;
            }
            Iterator<PdfName> it3 = it;
            for (byte[] pdfStream3 : this.validated.get(next).ocsps) {
                PdfStream pdfStream4 = new PdfStream(pdfStream3);
                pdfStream4.flateCompress();
                PdfIndirectReference indirectReference2 = this.writer.addToBody((PdfObject) pdfStream4, false).getIndirectReference();
                pdfArray7.add((PdfObject) indirectReference2);
                pdfArray4.add((PdfObject) indirectReference2);
            }
            for (byte[] pdfStream5 : this.validated.get(next).certs) {
                PdfStream pdfStream6 = new PdfStream(pdfStream5);
                pdfStream6.flateCompress();
                PdfIndirectReference indirectReference3 = this.writer.addToBody((PdfObject) pdfStream6, false).getIndirectReference();
                pdfArray9.add((PdfObject) indirectReference3);
                pdfArray6.add((PdfObject) indirectReference3);
            }
            if (pdfArray7.size() > 0) {
                pdfDictionary5.put(PdfName.OCSP, this.writer.addToBody((PdfObject) pdfArray7, false).getIndirectReference());
            }
            if (pdfArray8.size() > 0) {
                pdfDictionary5.put(PdfName.CRL, this.writer.addToBody((PdfObject) pdfArray8, false).getIndirectReference());
            }
            if (pdfArray9.size() > 0) {
                pdfDictionary5.put(PdfName.CERT, this.writer.addToBody((PdfObject) pdfArray9, false).getIndirectReference());
            }
            pdfDictionary4.put(next, this.writer.addToBody((PdfObject) pdfDictionary5, false).getIndirectReference());
            it = it3;
        }
        pdfDictionary3.put(PdfName.VRI, this.writer.addToBody((PdfObject) pdfDictionary4, false).getIndirectReference());
        if (pdfArray.size() > 0) {
            pdfDictionary3.put(PdfName.OCSPS, this.writer.addToBody((PdfObject) pdfArray4, false).getIndirectReference());
        }
        if (pdfArray2.size() > 0) {
            pdfDictionary3.put(PdfName.CRLS, this.writer.addToBody((PdfObject) pdfArray5, false).getIndirectReference());
        }
        if (pdfArray3.size() > 0) {
            pdfDictionary3.put(PdfName.CERTS, this.writer.addToBody((PdfObject) pdfArray6, false).getIndirectReference());
        }
        catalog.put(PdfName.DSS, this.writer.addToBody((PdfObject) pdfDictionary3, false).getIndirectReference());
    }

    private static class ValidationData {
        public List<byte[]> certs;
        public List<byte[]> crls;
        public List<byte[]> ocsps;

        private ValidationData() {
            this.crls = new ArrayList();
            this.ocsps = new ArrayList();
            this.certs = new ArrayList();
        }
    }
}
