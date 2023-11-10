package androidx.test.runner.screenshot;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

final class TakeScreenshotCallable implements Callable<Bitmap> {
    private static final String TAG = "TakeScreenshotCallable";
    private WeakReference<View> viewRef;

    static class Factory {
        Factory() {
        }

        /* access modifiers changed from: package-private */
        public Callable<Bitmap> create(View view) {
            return new TakeScreenshotCallable(view);
        }
    }

    private TakeScreenshotCallable(View view) {
        this.viewRef = new WeakReference<>(view);
    }

    /* JADX INFO: finally extract failed */
    public Bitmap call() {
        ((View) this.viewRef.get()).setDrawingCacheEnabled(true);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(((View) this.viewRef.get()).getDrawingCache());
            ((View) this.viewRef.get()).setDrawingCacheEnabled(false);
            return createBitmap;
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "Out of memory exception while trying to take a screenshot.", e);
            ((View) this.viewRef.get()).setDrawingCacheEnabled(false);
            return null;
        } catch (Throwable th) {
            ((View) this.viewRef.get()).setDrawingCacheEnabled(false);
            throw th;
        }
    }
}
