package com.itextpdf.text.exceptions;

import java.io.IOException;

public class InvalidPdfException extends IOException {
    private static final long serialVersionUID = -2319614911517026938L;
    private final Throwable cause;

    public InvalidPdfException(String str) {
        this(str, (Throwable) null);
    }

    public InvalidPdfException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
