package com.itextpdf.text.pdf;

public class PdfSigLockDictionary extends PdfDictionary {

    public enum LockAction {
        ALL(PdfName.ALL),
        INCLUDE(PdfName.INCLUDE),
        EXCLUDE(PdfName.EXCLUDE);
        
        private PdfName name;

        private LockAction(PdfName pdfName) {
            this.name = pdfName;
        }

        public PdfName getValue() {
            return this.name;
        }
    }

    public enum LockPermissions {
        NO_CHANGES_ALLOWED(1),
        FORM_FILLING(2),
        FORM_FILLING_AND_ANNOTATION(3);
        
        private PdfNumber number;

        private LockPermissions(int i) {
            this.number = new PdfNumber(i);
        }

        public PdfNumber getValue() {
            return this.number;
        }
    }

    public PdfSigLockDictionary() {
        super(PdfName.SIGFIELDLOCK);
        put(PdfName.ACTION, LockAction.ALL.getValue());
    }

    public PdfSigLockDictionary(LockPermissions lockPermissions) {
        this();
        put(PdfName.f623P, lockPermissions.getValue());
    }

    public PdfSigLockDictionary(LockAction lockAction, String... strArr) {
        this(lockAction, (LockPermissions) null, strArr);
    }

    public PdfSigLockDictionary(LockAction lockAction, LockPermissions lockPermissions, String... strArr) {
        super(PdfName.SIGFIELDLOCK);
        put(PdfName.ACTION, lockAction.getValue());
        if (lockPermissions != null) {
            put(PdfName.f623P, lockPermissions.getValue());
        }
        PdfArray pdfArray = new PdfArray();
        for (String pdfString : strArr) {
            pdfArray.add((PdfObject) new PdfString(pdfString));
        }
        put(PdfName.FIELDS, pdfArray);
    }
}
