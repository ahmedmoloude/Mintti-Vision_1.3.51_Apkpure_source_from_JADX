package com.itextpdf.text.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.bouncycastle.asn1.cms.EnvelopedData;
import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.bouncycastle.asn1.cms.KeyTransRecipientInfo;
import org.bouncycastle.asn1.cms.OriginatorInfo;
import org.bouncycastle.asn1.cms.RecipientIdentifier;
import org.bouncycastle.asn1.cms.RecipientInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.TBSCertificateStructure;

public class PdfPublicKeySecurityHandler {
    static final int SEED_LENGTH = 20;
    private ArrayList<PdfPublicKeyRecipient> recipients = null;
    private byte[] seed = new byte[20];

    public PdfPublicKeySecurityHandler() {
        try {
            KeyGenerator instance = KeyGenerator.getInstance("AES");
            instance.init(192, new SecureRandom());
            System.arraycopy(instance.generateKey().getEncoded(), 0, this.seed, 0, 20);
        } catch (NoSuchAlgorithmException unused) {
            this.seed = SecureRandom.getSeed(20);
        }
        this.recipients = new ArrayList<>();
    }

    public void addRecipient(PdfPublicKeyRecipient pdfPublicKeyRecipient) {
        this.recipients.add(pdfPublicKeyRecipient);
    }

    /* access modifiers changed from: protected */
    public byte[] getSeed() {
        return (byte[]) this.seed.clone();
    }

    public int getRecipientsSize() {
        return this.recipients.size();
    }

    public byte[] getEncodedRecipient(int i) throws IOException, GeneralSecurityException {
        PdfPublicKeyRecipient pdfPublicKeyRecipient = this.recipients.get(i);
        byte[] cms = pdfPublicKeyRecipient.getCms();
        if (cms != null) {
            return cms;
        }
        Certificate certificate = pdfPublicKeyRecipient.getCertificate();
        int permission = ((pdfPublicKeyRecipient.getPermission() | -3904) & -4) + 1;
        byte[] bArr = new byte[24];
        System.arraycopy(this.seed, 0, bArr, 0, 20);
        bArr[20] = (byte) (permission >> 24);
        bArr[21] = (byte) (permission >> 16);
        bArr[22] = (byte) (permission >> 8);
        bArr[23] = (byte) permission;
        ASN1Primitive createDERForRecipient = createDERForRecipient(bArr, (X509Certificate) certificate);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new DEROutputStream(byteArrayOutputStream).writeObject(createDERForRecipient);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        pdfPublicKeyRecipient.setCms(byteArray);
        return byteArray;
    }

    public PdfArray getEncodedRecipients() throws IOException, GeneralSecurityException {
        PdfArray pdfArray = new PdfArray();
        for (int i = 0; i < this.recipients.size(); i++) {
            try {
                pdfArray.add((PdfObject) new PdfLiteral(StringUtils.escapeString(getEncodedRecipient(i))));
            } catch (IOException | GeneralSecurityException unused) {
                pdfArray = null;
            }
        }
        return pdfArray;
    }

    private ASN1Primitive createDERForRecipient(byte[] bArr, X509Certificate x509Certificate) throws IOException, GeneralSecurityException {
        AlgorithmParameters generateParameters = AlgorithmParameterGenerator.getInstance("1.2.840.113549.3.2").generateParameters();
        ASN1Primitive readObject = new ASN1InputStream(new ByteArrayInputStream(generateParameters.getEncoded("ASN.1"))).readObject();
        KeyGenerator instance = KeyGenerator.getInstance("1.2.840.113549.3.2");
        instance.init(128);
        SecretKey generateKey = instance.generateKey();
        Cipher instance2 = Cipher.getInstance("1.2.840.113549.3.2");
        instance2.init(1, generateKey, generateParameters);
        DEROctetString dEROctetString = new DEROctetString(instance2.doFinal(bArr));
        return new ContentInfo(PKCSObjectIdentifiers.envelopedData, new EnvelopedData((OriginatorInfo) null, new DERSet(new RecipientInfo(computeRecipientInfo(x509Certificate, generateKey.getEncoded()))), new EncryptedContentInfo(PKCSObjectIdentifiers.data, new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.2.840.113549.3.2"), readObject), dEROctetString), (ASN1Set) null)).toASN1Primitive();
    }

    private KeyTransRecipientInfo computeRecipientInfo(X509Certificate x509Certificate, byte[] bArr) throws GeneralSecurityException, IOException {
        TBSCertificateStructure instance = TBSCertificateStructure.getInstance(new ASN1InputStream(new ByteArrayInputStream(x509Certificate.getTBSCertificate())).readObject());
        AlgorithmIdentifier algorithm = instance.getSubjectPublicKeyInfo().getAlgorithm();
        IssuerAndSerialNumber issuerAndSerialNumber = new IssuerAndSerialNumber(instance.getIssuer(), instance.getSerialNumber().getValue());
        Cipher instance2 = Cipher.getInstance(algorithm.getAlgorithm().getId());
        try {
            instance2.init(1, x509Certificate);
        } catch (InvalidKeyException unused) {
            instance2.init(1, x509Certificate.getPublicKey());
        }
        return new KeyTransRecipientInfo(new RecipientIdentifier(issuerAndSerialNumber), algorithm, new DEROctetString(instance2.doFinal(bArr)));
    }
}
