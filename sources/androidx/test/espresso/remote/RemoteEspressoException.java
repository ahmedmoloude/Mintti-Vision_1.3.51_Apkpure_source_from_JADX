package androidx.test.espresso.remote;

import androidx.test.espresso.EspressoException;

public class RemoteEspressoException extends RuntimeException implements EspressoException {
    public RemoteEspressoException(String str) {
        super(str);
    }

    public RemoteEspressoException(String str, Throwable th) {
        super(str, th);
    }
}
