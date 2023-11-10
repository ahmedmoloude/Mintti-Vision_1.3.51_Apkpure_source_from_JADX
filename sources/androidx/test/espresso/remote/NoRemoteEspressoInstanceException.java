package androidx.test.espresso.remote;

import androidx.test.espresso.EspressoException;

public final class NoRemoteEspressoInstanceException extends RuntimeException implements EspressoException {
    public NoRemoteEspressoInstanceException(String str) {
        super(str);
    }
}
