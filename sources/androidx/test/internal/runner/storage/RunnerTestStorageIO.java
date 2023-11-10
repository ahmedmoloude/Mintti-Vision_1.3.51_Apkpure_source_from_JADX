package androidx.test.internal.runner.storage;

import androidx.test.services.storage.internal.InternalTestStorage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public final class RunnerTestStorageIO implements RunnerIO {
    private final InternalTestStorage testStorage = new InternalTestStorage();

    public InputStream openInputStream(String str) throws FileNotFoundException {
        return this.testStorage.openInternalInputStream(str);
    }

    public OutputStream openOutputStream(String str) throws FileNotFoundException {
        return this.testStorage.openInternalOutputStream(str);
    }
}
