package androidx.test.internal.runner.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class RunnerFileIO implements RunnerIO {
    public InputStream openInputStream(String str) throws FileNotFoundException {
        return new FileInputStream(str);
    }

    public OutputStream openOutputStream(String str) throws FileNotFoundException {
        return new FileOutputStream(str);
    }
}
