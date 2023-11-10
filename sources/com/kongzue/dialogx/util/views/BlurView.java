package com.kongzue.dialogx.util.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import androidx.core.view.ViewCompat;
import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsicBlur;
import com.kongzue.dialogx.C1903R;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.interfaces.BaseDialog;

public class BlurView extends View {
    public static boolean DEBUGMODE = false;
    private static int RENDERING_COUNT = 0;
    /* access modifiers changed from: private */
    public static boolean supportRenderScript = false;
    Paint cutPaint;
    private boolean isInit = false;
    /* access modifiers changed from: private */
    public Bitmap mBitmapToBlur;
    private Allocation mBlurInput;
    private Allocation mBlurOutput;
    private float mBlurRadius = 35.0f;
    private ScriptIntrinsicBlur mBlurScript;
    /* access modifiers changed from: private */
    public Bitmap mBlurredBitmap;
    /* access modifiers changed from: private */
    public Canvas mBlurringCanvas;
    private Path mBoundPath = null;
    /* access modifiers changed from: private */
    public View mDecorView;
    /* access modifiers changed from: private */
    public boolean mDifferentRoot;
    private boolean mDirty;
    private float mDownsampleFactor = 4.0f;
    /* access modifiers changed from: private */
    public boolean mIsRendering;
    /* access modifiers changed from: private */
    public int mOverlayColor = -1;
    private Paint mPaint;
    /* access modifiers changed from: private */
    public float mRadius = 0.0f;
    private final Rect mRectDst = new Rect();
    private RectF mRectF;
    private final Rect mRectSrc = new Rect();
    private RenderScript mRenderScript;
    private Bitmap mRoundBitmap;
    private Canvas mTmpCanvas;
    Paint overlayPaint;
    private final ViewTreeObserver.OnPreDrawListener preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        public boolean onPreDraw() {
            int[] iArr = new int[2];
            Bitmap access$100 = BlurView.this.mBlurredBitmap;
            View access$200 = BlurView.this.mDecorView;
            if (access$200 != null && BlurView.this.isShown() && BlurView.this.prepare()) {
                boolean z = BlurView.this.mBlurredBitmap != access$100;
                access$200.getLocationOnScreen(iArr);
                BlurView.this.getLocationOnScreen(iArr);
                int i = (-iArr[0]) + iArr[0];
                int i2 = (-iArr[1]) + iArr[1];
                BlurView.this.mBitmapToBlur.eraseColor(BlurView.this.mOverlayColor & ViewCompat.MEASURED_SIZE_MASK);
                int save = BlurView.this.mBlurringCanvas.save();
                boolean unused = BlurView.this.mIsRendering = true;
                BlurView.access$708();
                try {
                    BlurView.this.mBlurringCanvas.scale((((float) BlurView.this.mBitmapToBlur.getWidth()) * 1.0f) / ((float) BlurView.this.getWidth()), (((float) BlurView.this.mBitmapToBlur.getHeight()) * 1.0f) / ((float) BlurView.this.getHeight()));
                    BlurView.this.mBlurringCanvas.translate((float) (-i), (float) (-i2));
                    if (access$200.getBackground() != null) {
                        access$200.getBackground().draw(BlurView.this.mBlurringCanvas);
                    }
                    access$200.draw(BlurView.this.mBlurringCanvas);
                } catch (Exception e) {
                    if (BlurView.isDebug()) {
                        e.printStackTrace();
                    }
                } catch (Throwable th) {
                    boolean unused2 = BlurView.this.mIsRendering = false;
                    BlurView.access$710();
                    BlurView.this.mBlurringCanvas.restoreToCount(save);
                    throw th;
                }
                boolean unused3 = BlurView.this.mIsRendering = false;
                BlurView.access$710();
                BlurView.this.mBlurringCanvas.restoreToCount(save);
                BlurView blurView = BlurView.this;
                blurView.blur(blurView.mBitmapToBlur, BlurView.this.mBlurredBitmap);
                if (z || BlurView.this.mDifferentRoot) {
                    BlurView.this.invalidate();
                }
            }
            return true;
        }
    };
    private boolean useBlur = true;

    static /* synthetic */ int access$708() {
        int i = RENDERING_COUNT;
        RENDERING_COUNT = i + 1;
        return i;
    }

    static /* synthetic */ int access$710() {
        int i = RENDERING_COUNT;
        RENDERING_COUNT = i - 1;
        return i;
    }

    public BlurView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public BlurView(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public BlurView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (!this.isInit) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1903R.styleable.RealtimeBlurView);
            this.mBlurRadius = obtainStyledAttributes.getDimension(C1903R.styleable.RealtimeBlurView_realtimeBlurRadius, TypedValue.applyDimension(1, 35.0f, context.getResources().getDisplayMetrics()));
            this.mDownsampleFactor = obtainStyledAttributes.getFloat(C1903R.styleable.RealtimeBlurView_realtimeDownsampleFactor, 4.0f);
            this.mOverlayColor = obtainStyledAttributes.getColor(C1903R.styleable.RealtimeBlurView_realtimeOverlayColor, ViewCompat.MEASURED_SIZE_MASK);
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            this.mRectF = new RectF();
            Paint paint2 = new Paint();
            this.cutPaint = paint2;
            paint2.setAntiAlias(true);
            this.cutPaint.setColor(this.mOverlayColor);
            Paint paint3 = new Paint();
            this.overlayPaint = paint3;
            paint3.setAntiAlias(true);
            this.mRadius = obtainStyledAttributes.getDimension(C1903R.styleable.RealtimeBlurView_radius, TypedValue.applyDimension(1, 15.0f, context.getResources().getDisplayMetrics()));
            obtainStyledAttributes.recycle();
            this.isInit = true;
            if (!isCompatMode()) {
                setOutlineProvider(new ViewOutlineProvider() {
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), BlurView.this.mRadius);
                    }
                });
                setClipToOutline(true);
            }
        }
    }

    private boolean isCompatMode() {
        return Build.VERSION.SDK_INT < 21;
    }

    public void setBlurRadius(float f) {
        if (this.mBlurRadius != f) {
            this.mBlurRadius = f;
            this.mDirty = true;
            invalidate();
        }
    }

    public void setRadiusPx(float f) {
        if (this.mRadius != f) {
            this.mRadius = f;
            this.mDirty = true;
            invalidate();
        }
    }

    public void setDownsampleFactor(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Downsample factor must be greater than 0.");
        } else if (this.mDownsampleFactor != f) {
            this.mDownsampleFactor = f;
            this.mDirty = true;
            releaseBitmap();
            invalidate();
        }
    }

    public void setOverlayColor(int i) {
        if (this.mOverlayColor != i) {
            this.mOverlayColor = i;
            invalidate();
        }
    }

    private void releaseBitmap() {
        Allocation allocation = this.mBlurInput;
        if (allocation != null) {
            allocation.destroy();
            this.mBlurInput = null;
        }
        Allocation allocation2 = this.mBlurOutput;
        if (allocation2 != null) {
            allocation2.destroy();
            this.mBlurOutput = null;
        }
        Bitmap bitmap = this.mBitmapToBlur;
        if (bitmap != null) {
            bitmap.recycle();
            this.mBitmapToBlur = null;
        }
        Bitmap bitmap2 = this.mBlurredBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.mBlurredBitmap = null;
        }
    }

    private void releaseScript() {
        RenderScript renderScript = this.mRenderScript;
        if (renderScript != null) {
            renderScript.destroy();
            this.mRenderScript = null;
        }
        ScriptIntrinsicBlur scriptIntrinsicBlur = this.mBlurScript;
        if (scriptIntrinsicBlur != null) {
            scriptIntrinsicBlur.destroy();
            this.mBlurScript = null;
        }
    }

    /* access modifiers changed from: protected */
    public void release() {
        releaseBitmap();
        releaseScript();
    }

    /* access modifiers changed from: protected */
    public boolean prepare() {
        Bitmap bitmap;
        if (this.mBlurRadius == 0.0f) {
            release();
            return false;
        }
        float f = this.mDownsampleFactor;
        if ((this.mDirty || this.mRenderScript == null) && supportRenderScript && this.useBlur) {
            if (this.mRenderScript == null) {
                try {
                    RenderScript create = RenderScript.create(getContext());
                    this.mRenderScript = create;
                    this.mBlurScript = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
                } catch (Exception e) {
                    supportRenderScript = false;
                    if (isDebug()) {
                        e.printStackTrace();
                    }
                }
            }
            this.mDirty = false;
            float f2 = this.mBlurRadius / f;
            if (f2 > 25.0f) {
                f = (f * f2) / 25.0f;
                f2 = 25.0f;
            }
            ScriptIntrinsicBlur scriptIntrinsicBlur = this.mBlurScript;
            if (scriptIntrinsicBlur != null) {
                scriptIntrinsicBlur.setRadius(f2);
            }
        }
        int width = getWidth();
        int height = getHeight();
        int max = Math.max(1, (int) (((float) width) / f));
        int max2 = Math.max(1, (int) (((float) height) / f));
        if (this.mBlurringCanvas == null || (bitmap = this.mBlurredBitmap) == null || bitmap.getWidth() != max || this.mBlurredBitmap.getHeight() != max2) {
            releaseBitmap();
            try {
                Bitmap createBitmap = Bitmap.createBitmap(max, max2, Bitmap.Config.ARGB_8888);
                this.mBitmapToBlur = createBitmap;
                if (createBitmap == null) {
                    releaseBitmap();
                    return false;
                }
                this.mBlurringCanvas = new Canvas(this.mBitmapToBlur);
                if (supportRenderScript) {
                    if (this.useBlur) {
                        Allocation createFromBitmap = Allocation.createFromBitmap(this.mRenderScript, this.mBitmapToBlur, Allocation.MipmapControl.MIPMAP_NONE, 1);
                        this.mBlurInput = createFromBitmap;
                        this.mBlurOutput = Allocation.createTyped(this.mRenderScript, createFromBitmap.getType());
                        Bitmap createBitmap2 = Bitmap.createBitmap(max, max2, Bitmap.Config.ARGB_8888);
                        this.mBlurredBitmap = createBitmap2;
                        if (createBitmap2 == null) {
                            releaseBitmap();
                            return false;
                        }
                    }
                }
                releaseBitmap();
                return false;
            } catch (Exception e2) {
                if (isDebug()) {
                    e2.printStackTrace();
                }
                releaseBitmap();
                return false;
            } catch (Throwable unused) {
                releaseBitmap();
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void blur(Bitmap bitmap, Bitmap bitmap2) {
        this.mBlurInput.copyFrom(bitmap);
        this.mBlurScript.setInput(this.mBlurInput);
        this.mBlurScript.forEach(this.mBlurOutput);
        this.mBlurOutput.copyTo(bitmap2);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        boolean z = true;
        if (BaseDialog.getRootFrameLayout() != null && BaseDialog.getRootFrameLayout().getChildCount() >= 1) {
            this.mDecorView = BaseDialog.getRootFrameLayout().getChildAt(0);
        }
        if (this.mDecorView != null) {
            log("mDecorView is ok.");
            this.mDecorView.getViewTreeObserver().addOnPreDrawListener(this.preDrawListener);
            if (this.mDecorView.getRootView() == getRootView()) {
                z = false;
            }
            this.mDifferentRoot = z;
            if (z) {
                this.mDecorView.postInvalidate();
                return;
            }
            return;
        }
        log("mDecorView is NULL.");
        this.mDifferentRoot = false;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        View view = this.mDecorView;
        if (view != null) {
            view.getViewTreeObserver().removeOnPreDrawListener(this.preDrawListener);
        }
        release();
        super.onDetachedFromWindow();
    }

    public void draw(Canvas canvas) {
        if (!this.useBlur || !supportRenderScript) {
            this.mRectF.right = (float) getWidth();
            this.mRectF.bottom = (float) getHeight();
            this.overlayPaint.setColor((!supportRenderScript || !this.useBlur) ? removeAlphaColor(this.mOverlayColor) : this.mOverlayColor);
            RectF rectF = this.mRectF;
            float f = this.mRadius;
            canvas.drawRoundRect(rectF, f, f, this.overlayPaint);
        } else if (!this.mIsRendering && RENDERING_COUNT <= 0) {
            super.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isCompatMode()) {
            drawBlurredBitmapCompat(canvas);
        } else {
            drawBlurredBitmap(canvas, this.mBlurredBitmap);
        }
    }

    private void drawBlurredBitmapCompat(Canvas canvas) {
        if (this.mBlurredBitmap != null) {
            this.mRectDst.right = getWidth();
            this.mRectDst.bottom = getHeight();
            if (getWidth() > 0 && getHeight() > 0) {
                Bitmap roundedCornerBitmap = getRoundedCornerBitmap(resizeImage(this.mBlurredBitmap, getWidth(), getHeight()), this.mRectDst);
                if (roundedCornerBitmap != null) {
                    canvas.drawBitmap(roundedCornerBitmap, 0.0f, 0.0f, (Paint) null);
                    return;
                }
                Bitmap drawOverlyColor = drawOverlyColor(Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888));
                if (drawOverlyColor != null) {
                    canvas.drawBitmap(drawOverlyColor, 0.0f, 0.0f, (Paint) null);
                    return;
                }
                return;
            }
            return;
        }
        Bitmap drawOverlyColor2 = drawOverlyColor(Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888));
        if (drawOverlyColor2 != null) {
            canvas.drawBitmap(drawOverlyColor2, 0.0f, 0.0f, (Paint) null);
        }
    }

    /* access modifiers changed from: protected */
    public void drawBlurredBitmap(Canvas canvas, Bitmap bitmap) {
        if (bitmap != null) {
            this.mRectSrc.right = bitmap.getWidth();
            this.mRectSrc.bottom = bitmap.getHeight();
            this.mRectDst.right = getWidth();
            this.mRectDst.bottom = getHeight();
            canvas.drawBitmap(bitmap, this.mRectSrc, this.mRectDst, (Paint) null);
            canvas.drawColor((!supportRenderScript || !this.useBlur) ? removeAlphaColor(this.mOverlayColor) : this.mOverlayColor);
            return;
        }
        Bitmap drawOverlyColor = drawOverlyColor(Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888));
        if (drawOverlyColor != null) {
            canvas.drawBitmap(drawOverlyColor, 0.0f, 0.0f, (Paint) null);
        }
    }

    private Bitmap getRoundedCornerBitmap(Bitmap bitmap, Rect rect) {
        Bitmap drawOverlyColor = drawOverlyColor(resizeImage(bitmap, rect.width(), rect.height()));
        if (drawOverlyColor == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawOverlyColor.getWidth(), drawOverlyColor.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        BitmapShader bitmapShader = new BitmapShader(drawOverlyColor, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        RectF rectF = new RectF(rect);
        float f = this.mRadius;
        canvas.drawRoundRect(rectF, f, f, paint);
        return createBitmap;
    }

    private Bitmap drawOverlyColor(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect();
        rect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rect, rect, this.overlayPaint);
        canvas.drawColor((!supportRenderScript || !this.useBlur) ? removeAlphaColor(this.mOverlayColor) : this.mOverlayColor);
        return createBitmap;
    }

    private Bitmap resizeImage(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public boolean isUseBlur() {
        return this.useBlur;
    }

    public BlurView setUseBlur(boolean z) {
        this.useBlur = z;
        invalidate();
        return this;
    }

    private static int replaceAlphaColor(int i, int i2) {
        return Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
    }

    private static int removeAlphaColor(int i) {
        return Color.argb(255, Color.red(i), Color.green(i), Color.blue(i));
    }

    static {
        new Thread() {
            public void run() {
                try {
                    BlurView.class.getClassLoader().loadClass(RenderScript.class.getCanonicalName());
                    boolean unused = BlurView.supportRenderScript = true;
                } catch (Throwable th) {
                    if (BlurView.isDebug()) {
                        th.printStackTrace();
                    }
                    boolean unused2 = BlurView.supportRenderScript = false;
                }
            }
        }.start();
    }

    static boolean isDebug() {
        return DEBUGMODE && DialogX.DEBUGMODE;
    }

    private static void log(Object obj) {
        if (isDebug()) {
            Log.i(">>>", obj.toString());
        }
    }

    public static void error(Object obj) {
        if (isDebug()) {
            Log.e(">>>", obj.toString());
        }
    }
}
