package com.itextpdf.text.pdf;

import com.itextpdf.text.pdf.security.PdfSignatureBuildProperties;

public class PdfSignature extends PdfDictionary {
    public PdfSignature(PdfName pdfName, PdfName pdfName2) {
        super(PdfName.SIG);
        put(PdfName.FILTER, pdfName);
        put(PdfName.SUBFILTER, pdfName2);
    }

    public void setByteRange(int[] iArr) {
        PdfArray pdfArray = new PdfArray();
        for (int pdfNumber : iArr) {
            pdfArray.add((PdfObject) new PdfNumber(pdfNumber));
        }
        put(PdfName.BYTERANGE, pdfArray);
    }

    public void setContents(byte[] bArr) {
        put(PdfName.CONTENTS, new PdfString(bArr).setHexWriting(true));
    }

    public void setCert(byte[] bArr) {
        put(PdfName.CERT, new PdfString(bArr));
    }

    public void setName(String str) {
        put(PdfName.NAME, new PdfString(str, PdfObject.TEXT_UNICODE));
    }

    public void setDate(PdfDate pdfDate) {
        put(PdfName.f608M, pdfDate);
    }

    public void setLocation(String str) {
        put(PdfName.LOCATION, new PdfString(str, PdfObject.TEXT_UNICODE));
    }

    public void setReason(String str) {
        put(PdfName.REASON, new PdfString(str, PdfObject.TEXT_UNICODE));
    }

    public void setSignatureCreator(String str) {
        if (str != null) {
            getPdfSignatureBuildProperties().setSignatureCreator(str);
        }
    }

    /* access modifiers changed from: package-private */
    public PdfSignatureBuildProperties getPdfSignatureBuildProperties() {
        PdfSignatureBuildProperties pdfSignatureBuildProperties = (PdfSignatureBuildProperties) getAsDict(PdfName.PROP_BUILD);
        if (pdfSignatureBuildProperties != null) {
            return pdfSignatureBuildProperties;
        }
        PdfSignatureBuildProperties pdfSignatureBuildProperties2 = new PdfSignatureBuildProperties();
        put(PdfName.PROP_BUILD, pdfSignatureBuildProperties2);
        return pdfSignatureBuildProperties2;
    }

    public void setContact(String str) {
        put(PdfName.CONTACTINFO, new PdfString(str, PdfObject.TEXT_UNICODE));
    }
}
