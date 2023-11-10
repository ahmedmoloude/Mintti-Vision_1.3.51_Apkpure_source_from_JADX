package androidx.test.espresso.remote;

import androidx.test.espresso.EspressoException;

public class RemoteProtocolException extends RuntimeException implements EspressoException {
    public RemoteProtocolException(String str) {
        super(str);
    }

    public RemoteProtocolException(String str, Throwable th) {
        super(str, th);
    }
}
