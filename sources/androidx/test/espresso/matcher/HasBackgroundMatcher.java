package androidx.test.espresso.matcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import java.util.Arrays;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class HasBackgroundMatcher extends TypeSafeMatcher<View> {
    private static final String TAG = "HasBackgroundMatcher";
    private final int drawableId;

    public HasBackgroundMatcher(int i) {
        this.drawableId = i;
    }

    private static boolean assertDrawable(Drawable drawable, int i, View view) {
        if (drawable == null || !(drawable instanceof BitmapDrawable)) {
            return false;
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(view.getContext().getResources(), i);
            if (Build.VERSION.SDK_INT >= 12) {
                boolean sameAs = ((BitmapDrawable) drawable).getBitmap().sameAs(bitmap);
                if (bitmap != null) {
                    bitmap.recycle();
                }
                return sameAs;
            }
            boolean compareBitmaps = compareBitmaps(((BitmapDrawable) drawable).getBitmap(), bitmap);
            if (bitmap != null) {
                bitmap.recycle();
            }
            return compareBitmaps;
        } catch (OutOfMemoryError e) {
            Log.e(TAG, e.getMessage(), e.getCause());
            if (bitmap != null) {
                bitmap.recycle();
            }
            return false;
        } catch (Throwable th) {
            if (bitmap != null) {
                bitmap.recycle();
            }
            throw th;
        }
    }

    static boolean compareBitmaps(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap.getWidth() != bitmap2.getWidth() || bitmap.getHeight() != bitmap2.getHeight()) {
            return false;
        }
        int[] iArr = new int[(bitmap.getWidth() * bitmap.getHeight())];
        int[] iArr2 = new int[(bitmap2.getWidth() * bitmap2.getHeight())];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        bitmap2.getPixels(iArr2, 0, bitmap2.getWidth(), 0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        return Arrays.equals(iArr, iArr2);
    }

    public void describeTo(Description description) {
        int i = this.drawableId;
        StringBuilder sb = new StringBuilder(44);
        sb.append("has background with drawable ID: ");
        sb.append(i);
        description.appendText(sb.toString());
    }

    /* access modifiers changed from: protected */
    public boolean matchesSafely(View view) {
        return assertDrawable(view.getBackground(), this.drawableId, view);
    }
}
