package com.itextpdf.text.pdf.security;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfDeveloperExtension;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfString;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;

public class LtvTimestamp {
    public static void timestamp(PdfSignatureAppearance pdfSignatureAppearance, TSAClient tSAClient, String str) throws IOException, DocumentException, GeneralSecurityException {
        int tokenSizeEstimate = tSAClient.getTokenSizeEstimate();
        pdfSignatureAppearance.addDeveloperExtension(PdfDeveloperExtension.ESIC_1_7_EXTENSIONLEVEL5);
        pdfSignatureAppearance.setVisibleSignature(new Rectangle(0.0f, 0.0f, 0.0f, 0.0f), 1, str);
        PdfSignature pdfSignature = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ETSI_RFC3161);
        pdfSignature.put(PdfName.TYPE, PdfName.DOCTIMESTAMP);
        pdfSignatureAppearance.setCryptoDictionary(pdfSignature);
        HashMap hashMap = new HashMap();
        hashMap.put(PdfName.CONTENTS, new Integer((tokenSizeEstimate * 2) + 2));
        pdfSignatureAppearance.preClose(hashMap);
        InputStream rangeStream = pdfSignatureAppearance.getRangeStream();
        MessageDigest messageDigest = tSAClient.getMessageDigest();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = rangeStream.read(bArr);
            if (read <= 0) {
                break;
            }
            messageDigest.update(bArr, 0, read);
        }
        try {
            byte[] timeStampToken = tSAClient.getTimeStampToken(messageDigest.digest());
            if (tokenSizeEstimate + 2 >= timeStampToken.length) {
                byte[] bArr2 = new byte[tokenSizeEstimate];
                System.arraycopy(timeStampToken, 0, bArr2, 0, timeStampToken.length);
                PdfDictionary pdfDictionary = new PdfDictionary();
                pdfDictionary.put(PdfName.CONTENTS, new PdfString(bArr2).setHexWriting(true));
                pdfSignatureAppearance.close(pdfDictionary);
                return;
            }
            throw new IOException("Not enough space");
        } catch (Exception e) {
            throw new GeneralSecurityException(e);
        }
    }
}
