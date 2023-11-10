package com.itextpdf.text.pdf.security;

import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import java.util.ArrayList;
import java.util.List;

public class SignaturePermissions {
    boolean annotationsAllowed = true;
    boolean certification = false;
    List<FieldLock> fieldLocks = new ArrayList();
    boolean fillInAllowed = true;

    public class FieldLock {
        PdfName action;
        PdfArray fields;

        public FieldLock(PdfName pdfName, PdfArray pdfArray) {
            this.action = pdfName;
            this.fields = pdfArray;
        }

        public PdfName getAction() {
            return this.action;
        }

        public PdfArray getFields() {
            return this.fields;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.action.toString());
            PdfArray pdfArray = this.fields;
            sb.append(pdfArray == null ? "" : pdfArray.toString());
            return sb.toString();
        }
    }

    public SignaturePermissions(PdfDictionary pdfDictionary, SignaturePermissions signaturePermissions) {
        if (signaturePermissions != null) {
            this.annotationsAllowed &= signaturePermissions.isAnnotationsAllowed();
            this.fillInAllowed &= signaturePermissions.isFillInAllowed();
            this.fieldLocks.addAll(signaturePermissions.getFieldLocks());
        }
        PdfArray asArray = pdfDictionary.getAsArray(PdfName.REFERENCE);
        if (asArray != null) {
            for (int i = 0; i < asArray.size(); i++) {
                PdfDictionary asDict = asArray.getAsDict(i);
                PdfDictionary asDict2 = asDict.getAsDict(PdfName.TRANSFORMPARAMS);
                if (PdfName.DOCMDP.equals(asDict.getAsName(PdfName.TRANSFORMMETHOD))) {
                    this.certification = true;
                }
                PdfName asName = asDict2.getAsName(PdfName.ACTION);
                if (asName != null) {
                    this.fieldLocks.add(new FieldLock(asName, asDict2.getAsArray(PdfName.FIELDS)));
                }
                PdfNumber asNumber = asDict2.getAsNumber(PdfName.f623P);
                if (asNumber != null) {
                    int intValue = asNumber.intValue();
                    if (intValue == 1) {
                        this.fillInAllowed &= false;
                    } else if (intValue != 2) {
                    }
                    this.annotationsAllowed &= false;
                }
            }
        }
    }

    public boolean isCertification() {
        return this.certification;
    }

    public boolean isFillInAllowed() {
        return this.fillInAllowed;
    }

    public boolean isAnnotationsAllowed() {
        return this.annotationsAllowed;
    }

    public List<FieldLock> getFieldLocks() {
        return this.fieldLocks;
    }
}
