package androidx.test.runner.screenshot;

import android.graphics.Bitmap;
import androidx.test.internal.util.Checks;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class ScreenCapture {
    private static final Bitmap.CompressFormat DEFAULT_FORMAT = Bitmap.CompressFormat.PNG;
    private final Bitmap bitmap;
    private ScreenCaptureProcessor defaultProcessor = new BasicScreenCaptureProcessor();
    private String filename;
    private Bitmap.CompressFormat format;
    private Set<ScreenCaptureProcessor> processorSet = new HashSet();

    ScreenCapture(Bitmap bitmap2) {
        this.bitmap = bitmap2;
        this.format = DEFAULT_FORMAT;
    }

    ScreenCapture(Bitmap bitmap2, ScreenCaptureProcessor screenCaptureProcessor) {
        this.bitmap = bitmap2;
        this.format = DEFAULT_FORMAT;
        this.defaultProcessor = screenCaptureProcessor;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getName() {
        return this.filename;
    }

    public Bitmap.CompressFormat getFormat() {
        return this.format;
    }

    public ScreenCapture setName(String str) {
        this.filename = str;
        return this;
    }

    public ScreenCapture setFormat(Bitmap.CompressFormat compressFormat) {
        this.format = compressFormat;
        return this;
    }

    /* access modifiers changed from: package-private */
    public ScreenCapture setProcessors(Set<ScreenCaptureProcessor> set) {
        this.processorSet = (Set) Checks.checkNotNull(set);
        return this;
    }

    /* access modifiers changed from: package-private */
    public Set<ScreenCaptureProcessor> getProcessors() {
        return this.processorSet;
    }

    public void process() throws IOException {
        process(this.processorSet);
    }

    public void process(Set<ScreenCaptureProcessor> set) throws IOException {
        Checks.checkNotNull(set);
        if (set.isEmpty()) {
            this.defaultProcessor.process(this);
            return;
        }
        for (ScreenCaptureProcessor process : set) {
            process.process(this);
        }
    }

    public int hashCode() {
        Bitmap bitmap2 = this.bitmap;
        int hashCode = bitmap2 != null ? 37 + bitmap2.hashCode() : 1;
        Bitmap.CompressFormat compressFormat = this.format;
        if (compressFormat != null) {
            hashCode = (hashCode * 37) + compressFormat.hashCode();
        }
        String str = this.filename;
        if (str != null) {
            hashCode = (hashCode * 37) + str.hashCode();
        }
        return !this.processorSet.isEmpty() ? (hashCode * 37) + this.processorSet.hashCode() : hashCode;
    }

    public boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof ScreenCapture)) {
            return false;
        }
        ScreenCapture screenCapture = (ScreenCapture) obj;
        if (this.bitmap == null) {
            z = screenCapture.getBitmap() == null;
        } else {
            z = getBitmap().sameAs(screenCapture.getBitmap());
        }
        String str = this.filename;
        if (str == null) {
            z2 = screenCapture.getName() == null;
        } else {
            z2 = str.equals(screenCapture.getName());
        }
        Bitmap.CompressFormat compressFormat = this.format;
        if (compressFormat == null) {
            z3 = screenCapture.getFormat() == null;
        } else {
            z3 = compressFormat.equals(screenCapture.getFormat());
        }
        if (!z || !z2 || !z3 || !this.processorSet.containsAll(screenCapture.getProcessors()) || !screenCapture.getProcessors().containsAll(this.processorSet)) {
            return false;
        }
        return true;
    }
}
