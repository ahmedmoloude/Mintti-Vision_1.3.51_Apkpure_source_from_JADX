package com.itextpdf.text.exceptions;

public class InvalidImageException extends RuntimeException {
    private static final long serialVersionUID = -1319471492541702697L;
    private final Throwable cause;

    public InvalidImageException(String str) {
        this(str, (Throwable) null);
    }

    public InvalidImageException(String str, Throwable th) {
        super(str);
        this.cause = th;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
