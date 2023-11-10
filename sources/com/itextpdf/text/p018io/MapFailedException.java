package com.itextpdf.text.p018io;

import java.io.IOException;

/* renamed from: com.itextpdf.text.io.MapFailedException */
public class MapFailedException extends IOException {
    public MapFailedException(IOException iOException) {
        super(iOException.getMessage());
        initCause(iOException);
    }
}
