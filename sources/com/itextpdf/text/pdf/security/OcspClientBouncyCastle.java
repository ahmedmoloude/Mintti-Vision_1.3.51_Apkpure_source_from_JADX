package com.itextpdf.text.pdf.security;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.log.Level;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.p018io.StreamUtil;
import com.itextpdf.text.pdf.PdfEncryption;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPReqBuilder;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.SingleResp;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.ocsp.RevokedStatus;
import org.bouncycastle.operator.OperatorException;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class OcspClientBouncyCastle implements OcspClient {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) OcspClientBouncyCastle.class);
    private final OCSPVerifier verifier;

    @Deprecated
    public OcspClientBouncyCastle() {
        this.verifier = null;
    }

    public OcspClientBouncyCastle(OCSPVerifier oCSPVerifier) {
        this.verifier = oCSPVerifier;
    }

    public BasicOCSPResp getBasicOCSPResp(X509Certificate x509Certificate, X509Certificate x509Certificate2, String str) {
        try {
            OCSPResp ocspResponse = getOcspResponse(x509Certificate, x509Certificate2, str);
            if (ocspResponse == null || ocspResponse.getStatus() != 0) {
                return null;
            }
            BasicOCSPResp basicOCSPResp = (BasicOCSPResp) ocspResponse.getResponseObject();
            OCSPVerifier oCSPVerifier = this.verifier;
            if (oCSPVerifier != null) {
                oCSPVerifier.isValidResponse(basicOCSPResp, x509Certificate2);
            }
            return basicOCSPResp;
        } catch (Exception e) {
            Logger logger = LOGGER;
            if (logger.isLogging(Level.ERROR)) {
                logger.error(e.getMessage());
            }
            return null;
        }
    }

    public byte[] getEncoded(X509Certificate x509Certificate, X509Certificate x509Certificate2, String str) {
        try {
            BasicOCSPResp basicOCSPResp = getBasicOCSPResp(x509Certificate, x509Certificate2, str);
            if (basicOCSPResp == null) {
                return null;
            }
            SingleResp[] responses = basicOCSPResp.getResponses();
            if (responses.length != 1) {
                return null;
            }
            CertificateStatus certStatus = responses[0].getCertStatus();
            if (certStatus == CertificateStatus.GOOD) {
                return basicOCSPResp.getEncoded();
            }
            if (certStatus instanceof RevokedStatus) {
                throw new IOException(MessageLocalization.getComposedMessage("ocsp.status.is.revoked", new Object[0]));
            }
            throw new IOException(MessageLocalization.getComposedMessage("ocsp.status.is.unknown", new Object[0]));
        } catch (Exception e) {
            Logger logger = LOGGER;
            if (!logger.isLogging(Level.ERROR)) {
                return null;
            }
            logger.error(e.getMessage());
            return null;
        }
    }

    private static OCSPReq generateOCSPRequest(X509Certificate x509Certificate, BigInteger bigInteger) throws OCSPException, IOException, OperatorException, CertificateEncodingException {
        Security.addProvider(new BouncyCastleProvider());
        CertificateID certificateID = new CertificateID(new JcaDigestCalculatorProviderBuilder().build().get(CertificateID.HASH_SHA1), new JcaX509CertificateHolder(x509Certificate), bigInteger);
        OCSPReqBuilder oCSPReqBuilder = new OCSPReqBuilder();
        oCSPReqBuilder.addRequest(certificateID);
        oCSPReqBuilder.setRequestExtensions(new Extensions(new Extension[]{new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, false, new DEROctetString(new DEROctetString(PdfEncryption.createDocumentId()).getEncoded()))}));
        return oCSPReqBuilder.build();
    }

    private OCSPResp getOcspResponse(X509Certificate x509Certificate, X509Certificate x509Certificate2, String str) throws GeneralSecurityException, OCSPException, IOException, OperatorException {
        if (x509Certificate == null || x509Certificate2 == null) {
            return null;
        }
        if (str == null) {
            str = CertificateUtil.getOCSPURL(x509Certificate);
        }
        if (str == null) {
            return null;
        }
        Logger logger = LOGGER;
        logger.info("Getting OCSP from " + str);
        byte[] encoded = generateOCSPRequest(x509Certificate2, x509Certificate.getSerialNumber()).getEncoded();
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestProperty("Content-Type", "application/ocsp-request");
        httpURLConnection.setRequestProperty("Accept", "application/ocsp-response");
        httpURLConnection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(httpURLConnection.getOutputStream()));
        dataOutputStream.write(encoded);
        dataOutputStream.flush();
        dataOutputStream.close();
        if (httpURLConnection.getResponseCode() / 100 == 2) {
            return new OCSPResp(StreamUtil.inputStreamToArray((InputStream) httpURLConnection.getContent()));
        }
        throw new IOException(MessageLocalization.getComposedMessage("invalid.http.response.1", httpURLConnection.getResponseCode()));
    }
}
