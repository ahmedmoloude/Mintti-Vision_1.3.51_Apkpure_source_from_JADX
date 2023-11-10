package com.kongzue.dialogx.util.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import com.kongzue.dialogx.impl.ActivityLifecycleImpl;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.util.DialogXFloatingWindowActivity;
import java.lang.ref.WeakReference;

public class ActivityScreenShotImageView extends ImageView {
    public static boolean hideContentView = false;
    private WeakReference<View> contentView;
    float height;
    /* access modifiers changed from: private */
    public boolean inited = false;
    private boolean isScreenshotSuccess;
    float mRadius;
    private int screenHeight;
    private int screenWidth;
    float width;

    public ActivityScreenShotImageView(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public ActivityScreenShotImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public ActivityScreenShotImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(2, (Paint) null);
        }
    }

    public void setRadius(float f) {
        this.mRadius = f;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.width = (float) getWidth();
        this.height = (float) getHeight();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f = this.width;
        float f2 = this.mRadius;
        if (f >= f2 && this.height > f2) {
            if (this.isScreenshotSuccess) {
                canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
            }
            Path path = new Path();
            path.moveTo(this.mRadius, 0.0f);
            path.lineTo(this.width - this.mRadius, 0.0f);
            float f3 = this.width;
            path.quadTo(f3, 0.0f, f3, this.mRadius);
            path.lineTo(this.width, this.height - this.mRadius);
            float f4 = this.width;
            float f5 = this.height;
            path.quadTo(f4, f5, f4 - this.mRadius, f5);
            path.lineTo(this.mRadius, this.height);
            float f6 = this.height;
            path.quadTo(0.0f, f6, 0.0f, f6 - this.mRadius);
            path.lineTo(0.0f, this.mRadius);
            path.quadTo(0.0f, 0.0f, this.mRadius, 0.0f);
            canvas.clipPath(path);
        }
        try {
            canvas.drawColor(-1);
            super.onDraw(canvas);
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (isAttachedToWindow() && !this.isScreenshotSuccess) {
            refreshImage();
        }
    }

    private void refreshImage() {
        if (this.screenWidth != getMeasuredWidth() || this.screenHeight != getMeasuredHeight()) {
            this.screenWidth = getMeasuredWidth();
            this.screenHeight = getMeasuredHeight();
            doScreenshotActivityAndZoom();
        }
    }

    private void doScreenshotActivityAndZoom() {
        final View contentView2 = getContentView();
        if (contentView2 != null) {
            if (!this.inited) {
                drawViewImage(contentView2);
            }
            contentView2.post(new Runnable() {
                public void run() {
                    ActivityScreenShotImageView.this.drawViewImage(contentView2);
                    boolean unused = ActivityScreenShotImageView.this.inited = true;
                }
            });
        }
    }

    private View getContentView() {
        Activity topActivity = ActivityLifecycleImpl.getTopActivity();
        if (topActivity == null) {
            return null;
        }
        if (!(topActivity instanceof DialogXFloatingWindowActivity)) {
            return BaseDialog.getRootFrameLayout();
        }
        DialogXFloatingWindowActivity dialogXFloatingWindowActivity = (DialogXFloatingWindowActivity) topActivity;
        if (dialogXFloatingWindowActivity.isScreenshot()) {
            return (FrameLayout) topActivity.getWindow().getDecorView();
        }
        dialogXFloatingWindowActivity.setScreenshot(true);
        return BaseDialog.getRootFrameLayout();
    }

    /* access modifiers changed from: private */
    public void drawViewImage(View view) {
        if (view.getWidth() != 0 && view.getHeight() != 0) {
            view.buildDrawingCache();
            view.getWindowVisibleDisplayFrame(new Rect());
            view.setDrawingCacheEnabled(true);
            setImageBitmap(Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getWidth(), view.getHeight()));
            view.destroyDrawingCache();
            if (hideContentView) {
                WeakReference<View> weakReference = this.contentView;
                if (!(weakReference == null || weakReference.get() == null)) {
                    ((View) this.contentView.get()).setVisibility(0);
                }
                View childAt = ((ViewGroup) view).getChildAt(0);
                childAt.setVisibility(8);
                this.contentView = new WeakReference<>(childAt);
            }
            this.isScreenshotSuccess = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WeakReference<View> weakReference = this.contentView;
        if (weakReference != null && weakReference.get() != null && hideContentView) {
            ((View) this.contentView.get()).setVisibility(0);
            this.contentView.clear();
        }
    }
}
