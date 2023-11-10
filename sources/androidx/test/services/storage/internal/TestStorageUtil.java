package androidx.test.services.storage.internal;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import androidx.test.internal.util.Checks;
import androidx.test.services.storage.TestStorageException;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public final class TestStorageUtil {
    public static InputStream getInputStream(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        Checks.checkNotNull(uri);
        ContentProviderClient contentProviderClient = null;
        try {
            contentProviderClient = makeContentProviderClient(contentResolver, uri);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new ParcelFileDescriptor.AutoCloseInputStream(contentProviderClient.openFile(uri, "r")));
            if (contentProviderClient != null) {
                contentProviderClient.release();
            }
            return bufferedInputStream;
        } catch (RemoteException e) {
            String valueOf = String.valueOf(uri);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 35);
            sb.append("Unable to access content provider: ");
            sb.append(valueOf);
            throw new TestStorageException(sb.toString(), e);
        } catch (Throwable th) {
            if (contentProviderClient != null) {
                contentProviderClient.release();
            }
            throw th;
        }
    }

    public static OutputStream getOutputStream(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        Checks.checkNotNull(uri);
        ContentProviderClient contentProviderClient = null;
        try {
            contentProviderClient = makeContentProviderClient(contentResolver, uri);
            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(contentProviderClient.openFile(uri, "w"));
            if (contentProviderClient != null) {
                contentProviderClient.release();
            }
            return autoCloseOutputStream;
        } catch (RemoteException e) {
            String valueOf = String.valueOf(uri);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 35);
            sb.append("Unable to access content provider: ");
            sb.append(valueOf);
            throw new TestStorageException(sb.toString(), e);
        } catch (Throwable th) {
            if (contentProviderClient != null) {
                contentProviderClient.release();
            }
            throw th;
        }
    }

    private static ContentProviderClient makeContentProviderClient(ContentResolver contentResolver, Uri uri) {
        Checks.checkNotNull(contentResolver);
        ContentProviderClient acquireContentProviderClient = contentResolver.acquireContentProviderClient(uri);
        if (acquireContentProviderClient != null) {
            return acquireContentProviderClient;
        }
        throw new TestStorageException(String.format("No content provider registered for: %s. Are all test services apks installed?", new Object[]{uri}));
    }

    private TestStorageUtil() {
    }
}
