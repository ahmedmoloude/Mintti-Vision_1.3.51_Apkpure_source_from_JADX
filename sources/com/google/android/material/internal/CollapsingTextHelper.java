package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.StaticLayoutBuilderCompat;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.TextAppearance;

public final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private static final Paint DEBUG_DRAW_PAINT;
    private static final String ELLIPSIS_NORMAL = "…";
    private static final float FADE_MODE_THRESHOLD_FRACTION_RELATIVE = 0.5f;
    private static final String TAG = "CollapsingTextHelper";
    private static final boolean USE_SCALING_TEXTURE = (Build.VERSION.SDK_INT < 18);
    private boolean boundsChanged;
    private final Rect collapsedBounds;
    private float collapsedDrawX;
    private float collapsedDrawY;
    private CancelableFontCallback collapsedFontCallback;
    private float collapsedLetterSpacing;
    private ColorStateList collapsedShadowColor;
    private float collapsedShadowDx;
    private float collapsedShadowDy;
    private float collapsedShadowRadius;
    private float collapsedTextBlend;
    private ColorStateList collapsedTextColor;
    private int collapsedTextGravity = 16;
    private float collapsedTextSize = 15.0f;
    private Typeface collapsedTypeface;
    private final RectF currentBounds;
    private float currentDrawX;
    private float currentDrawY;
    private int currentOffsetY;
    private float currentTextSize;
    private Typeface currentTypeface;
    private boolean drawTitle;
    private final Rect expandedBounds;
    private float expandedDrawX;
    private float expandedDrawY;
    private float expandedFirstLineDrawX;
    private CancelableFontCallback expandedFontCallback;
    private float expandedFraction;
    private float expandedLetterSpacing;
    private ColorStateList expandedShadowColor;
    private float expandedShadowDx;
    private float expandedShadowDy;
    private float expandedShadowRadius;
    private float expandedTextBlend;
    private ColorStateList expandedTextColor;
    private int expandedTextGravity = 16;
    private float expandedTextSize = 15.0f;
    private Bitmap expandedTitleTexture;
    private Typeface expandedTypeface;
    private boolean fadeModeEnabled;
    private float fadeModeStartFraction;
    private float fadeModeThresholdFraction;
    private int hyphenationFrequency = StaticLayoutBuilderCompat.DEFAULT_HYPHENATION_FREQUENCY;
    private boolean isRtl;
    private boolean isRtlTextDirectionHeuristicsEnabled = true;
    private float lineSpacingAdd = 0.0f;
    private float lineSpacingMultiplier = 1.0f;
    private int maxLines = 1;
    private TimeInterpolator positionInterpolator;
    private float scale;
    private int[] state;
    private CharSequence text;
    private StaticLayout textLayout;
    private final TextPaint textPaint;
    private TimeInterpolator textSizeInterpolator;
    private CharSequence textToDraw;
    private CharSequence textToDrawCollapsed;
    private Paint texturePaint;
    private final TextPaint tmpPaint;
    private boolean useTexture;
    private final View view;

    static {
        Paint paint = null;
        DEBUG_DRAW_PAINT = paint;
        if (paint != null) {
            paint.setAntiAlias(true);
            paint.setColor(-65281);
        }
    }

    public CollapsingTextHelper(View view2) {
        this.view = view2;
        TextPaint textPaint2 = new TextPaint(129);
        this.textPaint = textPaint2;
        this.tmpPaint = new TextPaint(textPaint2);
        this.collapsedBounds = new Rect();
        this.expandedBounds = new Rect();
        this.currentBounds = new RectF();
        this.fadeModeThresholdFraction = calculateFadeModeThresholdFraction();
    }

    public void setTextSizeInterpolator(TimeInterpolator timeInterpolator) {
        this.textSizeInterpolator = timeInterpolator;
        recalculate();
    }

    public void setPositionInterpolator(TimeInterpolator timeInterpolator) {
        this.positionInterpolator = timeInterpolator;
        recalculate();
    }

    public void setExpandedTextSize(float f) {
        if (this.expandedTextSize != f) {
            this.expandedTextSize = f;
            recalculate();
        }
    }

    public void setCollapsedTextSize(float f) {
        if (this.collapsedTextSize != f) {
            this.collapsedTextSize = f;
            recalculate();
        }
    }

    public void setCollapsedTextColor(ColorStateList colorStateList) {
        if (this.collapsedTextColor != colorStateList) {
            this.collapsedTextColor = colorStateList;
            recalculate();
        }
    }

    public void setExpandedTextColor(ColorStateList colorStateList) {
        if (this.expandedTextColor != colorStateList) {
            this.expandedTextColor = colorStateList;
            recalculate();
        }
    }

    public void setExpandedBounds(int i, int i2, int i3, int i4) {
        if (!rectEquals(this.expandedBounds, i, i2, i3, i4)) {
            this.expandedBounds.set(i, i2, i3, i4);
            this.boundsChanged = true;
            onBoundsChanged();
        }
    }

    public void setExpandedBounds(Rect rect) {
        setExpandedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setCollapsedBounds(int i, int i2, int i3, int i4) {
        if (!rectEquals(this.collapsedBounds, i, i2, i3, i4)) {
            this.collapsedBounds.set(i, i2, i3, i4);
            this.boundsChanged = true;
            onBoundsChanged();
        }
    }

    public void setCollapsedBounds(Rect rect) {
        setCollapsedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void getCollapsedTextActualBounds(RectF rectF, int i, int i2) {
        this.isRtl = calculateIsRtl(this.text);
        rectF.left = getCollapsedTextLeftBound(i, i2);
        rectF.top = (float) this.collapsedBounds.top;
        rectF.right = getCollapsedTextRightBound(rectF, i, i2);
        rectF.bottom = ((float) this.collapsedBounds.top) + getCollapsedTextHeight();
    }

    private float getCollapsedTextLeftBound(int i, int i2) {
        if (i2 == 17 || (i2 & 7) == 1) {
            return (((float) i) / 2.0f) - (calculateCollapsedTextWidth() / 2.0f);
        }
        return ((i2 & GravityCompat.END) == 8388613 || (i2 & 5) == 5) ? this.isRtl ? (float) this.collapsedBounds.left : ((float) this.collapsedBounds.right) - calculateCollapsedTextWidth() : this.isRtl ? ((float) this.collapsedBounds.right) - calculateCollapsedTextWidth() : (float) this.collapsedBounds.left;
    }

    private float getCollapsedTextRightBound(RectF rectF, int i, int i2) {
        if (i2 == 17 || (i2 & 7) == 1) {
            return (((float) i) / 2.0f) + (calculateCollapsedTextWidth() / 2.0f);
        }
        return ((i2 & GravityCompat.END) == 8388613 || (i2 & 5) == 5) ? this.isRtl ? rectF.left + calculateCollapsedTextWidth() : (float) this.collapsedBounds.right : this.isRtl ? (float) this.collapsedBounds.right : rectF.left + calculateCollapsedTextWidth();
    }

    public float calculateCollapsedTextWidth() {
        if (this.text == null) {
            return 0.0f;
        }
        getTextPaintCollapsed(this.tmpPaint);
        TextPaint textPaint2 = this.tmpPaint;
        CharSequence charSequence = this.text;
        return textPaint2.measureText(charSequence, 0, charSequence.length());
    }

    public float getExpandedTextHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getExpandedTextFullHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return (-this.tmpPaint.ascent()) + this.tmpPaint.descent();
    }

    public float getCollapsedTextHeight() {
        getTextPaintCollapsed(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public void setCurrentOffsetY(int i) {
        this.currentOffsetY = i;
    }

    public void setFadeModeStartFraction(float f) {
        this.fadeModeStartFraction = f;
        this.fadeModeThresholdFraction = calculateFadeModeThresholdFraction();
    }

    private float calculateFadeModeThresholdFraction() {
        float f = this.fadeModeStartFraction;
        return f + ((1.0f - f) * 0.5f);
    }

    public void setFadeModeEnabled(boolean z) {
        this.fadeModeEnabled = z;
    }

    private void getTextPaintExpanded(TextPaint textPaint2) {
        textPaint2.setTextSize(this.expandedTextSize);
        textPaint2.setTypeface(this.expandedTypeface);
        if (Build.VERSION.SDK_INT >= 21) {
            textPaint2.setLetterSpacing(this.expandedLetterSpacing);
        }
    }

    private void getTextPaintCollapsed(TextPaint textPaint2) {
        textPaint2.setTextSize(this.collapsedTextSize);
        textPaint2.setTypeface(this.collapsedTypeface);
        if (Build.VERSION.SDK_INT >= 21) {
            textPaint2.setLetterSpacing(this.collapsedLetterSpacing);
        }
    }

    /* access modifiers changed from: package-private */
    public void onBoundsChanged() {
        this.drawTitle = this.collapsedBounds.width() > 0 && this.collapsedBounds.height() > 0 && this.expandedBounds.width() > 0 && this.expandedBounds.height() > 0;
    }

    public void setExpandedTextGravity(int i) {
        if (this.expandedTextGravity != i) {
            this.expandedTextGravity = i;
            recalculate();
        }
    }

    public int getExpandedTextGravity() {
        return this.expandedTextGravity;
    }

    public void setCollapsedTextGravity(int i) {
        if (this.collapsedTextGravity != i) {
            this.collapsedTextGravity = i;
            recalculate();
        }
    }

    public int getCollapsedTextGravity() {
        return this.collapsedTextGravity;
    }

    public void setCollapsedTextAppearance(int i) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), i);
        if (textAppearance.textColor != null) {
            this.collapsedTextColor = textAppearance.textColor;
        }
        if (textAppearance.textSize != 0.0f) {
            this.collapsedTextSize = textAppearance.textSize;
        }
        if (textAppearance.shadowColor != null) {
            this.collapsedShadowColor = textAppearance.shadowColor;
        }
        this.collapsedShadowDx = textAppearance.shadowDx;
        this.collapsedShadowDy = textAppearance.shadowDy;
        this.collapsedShadowRadius = textAppearance.shadowRadius;
        this.collapsedLetterSpacing = textAppearance.letterSpacing;
        CancelableFontCallback cancelableFontCallback = this.collapsedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        this.collapsedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() {
            public void apply(Typeface typeface) {
                CollapsingTextHelper.this.setCollapsedTypeface(typeface);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.collapsedFontCallback);
        recalculate();
    }

    public void setExpandedTextAppearance(int i) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), i);
        if (textAppearance.textColor != null) {
            this.expandedTextColor = textAppearance.textColor;
        }
        if (textAppearance.textSize != 0.0f) {
            this.expandedTextSize = textAppearance.textSize;
        }
        if (textAppearance.shadowColor != null) {
            this.expandedShadowColor = textAppearance.shadowColor;
        }
        this.expandedShadowDx = textAppearance.shadowDx;
        this.expandedShadowDy = textAppearance.shadowDy;
        this.expandedShadowRadius = textAppearance.shadowRadius;
        this.expandedLetterSpacing = textAppearance.letterSpacing;
        CancelableFontCallback cancelableFontCallback = this.expandedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        this.expandedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() {
            public void apply(Typeface typeface) {
                CollapsingTextHelper.this.setExpandedTypeface(typeface);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.expandedFontCallback);
        recalculate();
    }

    public void setCollapsedTypeface(Typeface typeface) {
        if (setCollapsedTypefaceInternal(typeface)) {
            recalculate();
        }
    }

    public void setExpandedTypeface(Typeface typeface) {
        if (setExpandedTypefaceInternal(typeface)) {
            recalculate();
        }
    }

    public void setTypefaces(Typeface typeface) {
        boolean collapsedTypefaceInternal = setCollapsedTypefaceInternal(typeface);
        boolean expandedTypefaceInternal = setExpandedTypefaceInternal(typeface);
        if (collapsedTypefaceInternal || expandedTypefaceInternal) {
            recalculate();
        }
    }

    private boolean setCollapsedTypefaceInternal(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.collapsedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.collapsedTypeface == typeface) {
            return false;
        }
        this.collapsedTypeface = typeface;
        return true;
    }

    private boolean setExpandedTypefaceInternal(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.expandedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.expandedTypeface == typeface) {
            return false;
        }
        this.expandedTypeface = typeface;
        return true;
    }

    public Typeface getCollapsedTypeface() {
        Typeface typeface = this.collapsedTypeface;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    public Typeface getExpandedTypeface() {
        Typeface typeface = this.expandedTypeface;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    public void setExpansionFraction(float f) {
        float clamp = MathUtils.clamp(f, 0.0f, 1.0f);
        if (clamp != this.expandedFraction) {
            this.expandedFraction = clamp;
            calculateCurrentOffsets();
        }
    }

    public final boolean setState(int[] iArr) {
        this.state = iArr;
        if (!isStateful()) {
            return false;
        }
        recalculate();
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r1.expandedTextColor;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isStateful() {
        /*
            r1 = this;
            android.content.res.ColorStateList r0 = r1.collapsedTextColor
            if (r0 == 0) goto L_0x000a
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x0014
        L_0x000a:
            android.content.res.ColorStateList r0 = r1.expandedTextColor
            if (r0 == 0) goto L_0x0016
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x0016
        L_0x0014:
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.internal.CollapsingTextHelper.isStateful():boolean");
    }

    public float getFadeModeThresholdFraction() {
        return this.fadeModeThresholdFraction;
    }

    public float getExpansionFraction() {
        return this.expandedFraction;
    }

    public float getCollapsedTextSize() {
        return this.collapsedTextSize;
    }

    public float getExpandedTextSize() {
        return this.expandedTextSize;
    }

    public void setRtlTextDirectionHeuristicsEnabled(boolean z) {
        this.isRtlTextDirectionHeuristicsEnabled = z;
    }

    public boolean isRtlTextDirectionHeuristicsEnabled() {
        return this.isRtlTextDirectionHeuristicsEnabled;
    }

    private void calculateCurrentOffsets() {
        calculateOffsets(this.expandedFraction);
    }

    private void calculateOffsets(float f) {
        float f2;
        interpolateBounds(f);
        if (!this.fadeModeEnabled) {
            this.currentDrawX = lerp(this.expandedDrawX, this.collapsedDrawX, f, this.positionInterpolator);
            this.currentDrawY = lerp(this.expandedDrawY, this.collapsedDrawY, f, this.positionInterpolator);
            setInterpolatedTextSize(lerp(this.expandedTextSize, this.collapsedTextSize, f, this.textSizeInterpolator));
            f2 = f;
        } else if (f < this.fadeModeThresholdFraction) {
            this.currentDrawX = this.expandedDrawX;
            this.currentDrawY = this.expandedDrawY;
            setInterpolatedTextSize(this.expandedTextSize);
            f2 = 0.0f;
        } else {
            this.currentDrawX = this.collapsedDrawX;
            this.currentDrawY = this.collapsedDrawY - ((float) Math.max(0, this.currentOffsetY));
            setInterpolatedTextSize(this.collapsedTextSize);
            f2 = 1.0f;
        }
        setCollapsedTextBlend(1.0f - lerp(0.0f, 1.0f, 1.0f - f, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        setExpandedTextBlend(lerp(1.0f, 0.0f, f, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        if (this.collapsedTextColor != this.expandedTextColor) {
            this.textPaint.setColor(blendColors(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), f2));
        } else {
            this.textPaint.setColor(getCurrentCollapsedTextColor());
        }
        if (Build.VERSION.SDK_INT >= 21) {
            float f3 = this.collapsedLetterSpacing;
            float f4 = this.expandedLetterSpacing;
            if (f3 != f4) {
                this.textPaint.setLetterSpacing(lerp(f4, f3, f, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
            } else {
                this.textPaint.setLetterSpacing(f3);
            }
        }
        this.textPaint.setShadowLayer(lerp(this.expandedShadowRadius, this.collapsedShadowRadius, f, (TimeInterpolator) null), lerp(this.expandedShadowDx, this.collapsedShadowDx, f, (TimeInterpolator) null), lerp(this.expandedShadowDy, this.collapsedShadowDy, f, (TimeInterpolator) null), blendColors(getCurrentColor(this.expandedShadowColor), getCurrentColor(this.collapsedShadowColor), f));
        if (this.fadeModeEnabled) {
            this.textPaint.setAlpha((int) (calculateFadeModeTextAlpha(f) * 255.0f));
        }
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private float calculateFadeModeTextAlpha(float f) {
        float f2 = this.fadeModeThresholdFraction;
        if (f <= f2) {
            return AnimationUtils.lerp(1.0f, 0.0f, this.fadeModeStartFraction, f2, f);
        }
        return AnimationUtils.lerp(0.0f, 1.0f, f2, 1.0f, f);
    }

    private int getCurrentExpandedTextColor() {
        return getCurrentColor(this.expandedTextColor);
    }

    public int getCurrentCollapsedTextColor() {
        return getCurrentColor(this.collapsedTextColor);
    }

    private int getCurrentColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return 0;
        }
        int[] iArr = this.state;
        if (iArr != null) {
            return colorStateList.getColorForState(iArr, 0);
        }
        return colorStateList.getDefaultColor();
    }

    private void calculateBaseOffsets(boolean z) {
        StaticLayout staticLayout;
        float f = this.currentTextSize;
        calculateUsingTextSize(this.collapsedTextSize, z);
        CharSequence charSequence = this.textToDraw;
        if (!(charSequence == null || (staticLayout = this.textLayout) == null)) {
            this.textToDrawCollapsed = TextUtils.ellipsize(charSequence, this.textPaint, (float) staticLayout.getWidth(), TextUtils.TruncateAt.END);
        }
        CharSequence charSequence2 = this.textToDrawCollapsed;
        float f2 = 0.0f;
        float measureText = charSequence2 != null ? this.textPaint.measureText(charSequence2, 0, charSequence2.length()) : 0.0f;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(this.collapsedTextGravity, this.isRtl ? 1 : 0);
        int i = absoluteGravity & 112;
        if (i == 48) {
            this.collapsedDrawY = (float) this.collapsedBounds.top;
        } else if (i != 80) {
            this.collapsedDrawY = ((float) this.collapsedBounds.centerY()) - ((this.textPaint.descent() - this.textPaint.ascent()) / 2.0f);
        } else {
            this.collapsedDrawY = ((float) this.collapsedBounds.bottom) + this.textPaint.ascent();
        }
        int i2 = absoluteGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i2 == 1) {
            this.collapsedDrawX = ((float) this.collapsedBounds.centerX()) - (measureText / 2.0f);
        } else if (i2 != 5) {
            this.collapsedDrawX = (float) this.collapsedBounds.left;
        } else {
            this.collapsedDrawX = ((float) this.collapsedBounds.right) - measureText;
        }
        calculateUsingTextSize(this.expandedTextSize, z);
        StaticLayout staticLayout2 = this.textLayout;
        float height = staticLayout2 != null ? (float) staticLayout2.getHeight() : 0.0f;
        CharSequence charSequence3 = this.textToDraw;
        float measureText2 = charSequence3 != null ? this.textPaint.measureText(charSequence3, 0, charSequence3.length()) : 0.0f;
        StaticLayout staticLayout3 = this.textLayout;
        if (staticLayout3 != null && this.maxLines > 1) {
            measureText2 = (float) staticLayout3.getWidth();
        }
        StaticLayout staticLayout4 = this.textLayout;
        if (staticLayout4 != null) {
            f2 = this.maxLines > 1 ? (float) staticLayout4.getLineStart(0) : staticLayout4.getLineLeft(0);
        }
        this.expandedFirstLineDrawX = f2;
        int absoluteGravity2 = GravityCompat.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0);
        int i3 = absoluteGravity2 & 112;
        if (i3 == 48) {
            this.expandedDrawY = (float) this.expandedBounds.top;
        } else if (i3 != 80) {
            this.expandedDrawY = ((float) this.expandedBounds.centerY()) - (height / 2.0f);
        } else {
            this.expandedDrawY = (((float) this.expandedBounds.bottom) - height) + this.textPaint.descent();
        }
        int i4 = absoluteGravity2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i4 == 1) {
            this.expandedDrawX = ((float) this.expandedBounds.centerX()) - (measureText2 / 2.0f);
        } else if (i4 != 5) {
            this.expandedDrawX = (float) this.expandedBounds.left;
        } else {
            this.expandedDrawX = ((float) this.expandedBounds.right) - measureText2;
        }
        clearTexture();
        setInterpolatedTextSize(f);
    }

    private void interpolateBounds(float f) {
        if (this.fadeModeEnabled) {
            this.currentBounds.set(f < this.fadeModeThresholdFraction ? this.expandedBounds : this.collapsedBounds);
            return;
        }
        this.currentBounds.left = lerp((float) this.expandedBounds.left, (float) this.collapsedBounds.left, f, this.positionInterpolator);
        this.currentBounds.top = lerp(this.expandedDrawY, this.collapsedDrawY, f, this.positionInterpolator);
        this.currentBounds.right = lerp((float) this.expandedBounds.right, (float) this.collapsedBounds.right, f, this.positionInterpolator);
        this.currentBounds.bottom = lerp((float) this.expandedBounds.bottom, (float) this.collapsedBounds.bottom, f, this.positionInterpolator);
    }

    private void setCollapsedTextBlend(float f) {
        this.collapsedTextBlend = f;
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private void setExpandedTextBlend(float f) {
        this.expandedTextBlend = f;
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    public void draw(Canvas canvas) {
        int save = canvas.save();
        if (this.textToDraw != null && this.drawTitle) {
            boolean z = true;
            float lineStart = (this.currentDrawX + (this.maxLines > 1 ? (float) this.textLayout.getLineStart(0) : this.textLayout.getLineLeft(0))) - (this.expandedFirstLineDrawX * 2.0f);
            this.textPaint.setTextSize(this.currentTextSize);
            float f = this.currentDrawX;
            float f2 = this.currentDrawY;
            if (!this.useTexture || this.expandedTitleTexture == null) {
                z = false;
            }
            float f3 = this.scale;
            if (f3 != 1.0f && !this.fadeModeEnabled) {
                canvas.scale(f3, f3, f, f2);
            }
            if (z) {
                canvas.drawBitmap(this.expandedTitleTexture, f, f2, this.texturePaint);
                canvas.restoreToCount(save);
                return;
            }
            if (!shouldDrawMultiline() || (this.fadeModeEnabled && this.expandedFraction <= this.fadeModeThresholdFraction)) {
                canvas.translate(f, f2);
                this.textLayout.draw(canvas);
            } else {
                drawMultilineTransition(canvas, lineStart, f2);
            }
            canvas.restoreToCount(save);
        }
    }

    private boolean shouldDrawMultiline() {
        return this.maxLines > 1 && (!this.isRtl || this.fadeModeEnabled) && !this.useTexture;
    }

    private void drawMultilineTransition(Canvas canvas, float f, float f2) {
        int alpha = this.textPaint.getAlpha();
        canvas.translate(f, f2);
        float f3 = (float) alpha;
        this.textPaint.setAlpha((int) (this.expandedTextBlend * f3));
        this.textLayout.draw(canvas);
        this.textPaint.setAlpha((int) (this.collapsedTextBlend * f3));
        int lineBaseline = this.textLayout.getLineBaseline(0);
        CharSequence charSequence = this.textToDrawCollapsed;
        float f4 = (float) lineBaseline;
        canvas.drawText(charSequence, 0, charSequence.length(), 0.0f, f4, this.textPaint);
        if (!this.fadeModeEnabled) {
            String trim = this.textToDrawCollapsed.toString().trim();
            if (trim.endsWith(ELLIPSIS_NORMAL)) {
                trim = trim.substring(0, trim.length() - 1);
            }
            String str = trim;
            this.textPaint.setAlpha(alpha);
            canvas.drawText(str, 0, Math.min(this.textLayout.getLineEnd(0), str.length()), 0.0f, f4, this.textPaint);
        }
    }

    private boolean calculateIsRtl(CharSequence charSequence) {
        boolean isDefaultIsRtl = isDefaultIsRtl();
        return this.isRtlTextDirectionHeuristicsEnabled ? isTextDirectionHeuristicsIsRtl(charSequence, isDefaultIsRtl) : isDefaultIsRtl;
    }

    private boolean isDefaultIsRtl() {
        return ViewCompat.getLayoutDirection(this.view) == 1;
    }

    private boolean isTextDirectionHeuristicsIsRtl(CharSequence charSequence, boolean z) {
        return (z ? TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL : TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR).isRtl(charSequence, 0, charSequence.length());
    }

    private void setInterpolatedTextSize(float f) {
        calculateUsingTextSize(f);
        boolean z = USE_SCALING_TEXTURE && this.scale != 1.0f;
        this.useTexture = z;
        if (z) {
            ensureExpandedTexture();
        }
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private void calculateUsingTextSize(float f) {
        calculateUsingTextSize(f, false);
    }

    private void calculateUsingTextSize(float f, boolean z) {
        boolean z2;
        float f2;
        boolean z3;
        if (this.text != null) {
            float width = (float) this.collapsedBounds.width();
            float width2 = (float) this.expandedBounds.width();
            boolean z4 = false;
            int i = 1;
            if (isClose(f, this.collapsedTextSize)) {
                f2 = this.collapsedTextSize;
                this.scale = 1.0f;
                Typeface typeface = this.currentTypeface;
                Typeface typeface2 = this.collapsedTypeface;
                if (typeface != typeface2) {
                    this.currentTypeface = typeface2;
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                float f3 = this.expandedTextSize;
                Typeface typeface3 = this.currentTypeface;
                Typeface typeface4 = this.expandedTypeface;
                if (typeface3 != typeface4) {
                    this.currentTypeface = typeface4;
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (isClose(f, f3)) {
                    this.scale = 1.0f;
                } else {
                    this.scale = f / this.expandedTextSize;
                }
                float f4 = this.collapsedTextSize / this.expandedTextSize;
                width = (!z && width2 * f4 > width) ? Math.min(width / f4, width2) : width2;
                f2 = f3;
                z2 = z3;
            }
            if (width > 0.0f) {
                z2 = this.currentTextSize != f2 || this.boundsChanged || z2;
                this.currentTextSize = f2;
                this.boundsChanged = false;
            }
            if (this.textToDraw == null || z2) {
                this.textPaint.setTextSize(this.currentTextSize);
                this.textPaint.setTypeface(this.currentTypeface);
                TextPaint textPaint2 = this.textPaint;
                if (this.scale != 1.0f) {
                    z4 = true;
                }
                textPaint2.setLinearText(z4);
                this.isRtl = calculateIsRtl(this.text);
                if (shouldDrawMultiline()) {
                    i = this.maxLines;
                }
                StaticLayout createStaticLayout = createStaticLayout(i, width, this.isRtl);
                this.textLayout = createStaticLayout;
                this.textToDraw = createStaticLayout.getText();
            }
        }
    }

    private StaticLayout createStaticLayout(int i, float f, boolean z) {
        StaticLayout staticLayout;
        try {
            staticLayout = StaticLayoutBuilderCompat.obtain(this.text, this.textPaint, (int) f).setEllipsize(TextUtils.TruncateAt.END).setIsRtl(z).setAlignment(Layout.Alignment.ALIGN_NORMAL).setIncludePad(false).setMaxLines(i).setLineSpacing(this.lineSpacingAdd, this.lineSpacingMultiplier).setHyphenationFrequency(this.hyphenationFrequency).build();
        } catch (StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException e) {
            Log.e(TAG, e.getCause().getMessage(), e);
            staticLayout = null;
        }
        return (StaticLayout) Preconditions.checkNotNull(staticLayout);
    }

    private void ensureExpandedTexture() {
        if (this.expandedTitleTexture == null && !this.expandedBounds.isEmpty() && !TextUtils.isEmpty(this.textToDraw)) {
            calculateOffsets(0.0f);
            int width = this.textLayout.getWidth();
            int height = this.textLayout.getHeight();
            if (width > 0 && height > 0) {
                this.expandedTitleTexture = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                this.textLayout.draw(new Canvas(this.expandedTitleTexture));
                if (this.texturePaint == null) {
                    this.texturePaint = new Paint(3);
                }
            }
        }
    }

    public void recalculate() {
        recalculate(false);
    }

    public void recalculate(boolean z) {
        if ((this.view.getHeight() > 0 && this.view.getWidth() > 0) || z) {
            calculateBaseOffsets(z);
            calculateCurrentOffsets();
        }
    }

    public void setText(CharSequence charSequence) {
        if (charSequence == null || !TextUtils.equals(this.text, charSequence)) {
            this.text = charSequence;
            this.textToDraw = null;
            clearTexture();
            recalculate();
        }
    }

    public CharSequence getText() {
        return this.text;
    }

    private void clearTexture() {
        Bitmap bitmap = this.expandedTitleTexture;
        if (bitmap != null) {
            bitmap.recycle();
            this.expandedTitleTexture = null;
        }
    }

    public void setMaxLines(int i) {
        if (i != this.maxLines) {
            this.maxLines = i;
            clearTexture();
            recalculate();
        }
    }

    public int getMaxLines() {
        return this.maxLines;
    }

    public int getLineCount() {
        StaticLayout staticLayout = this.textLayout;
        if (staticLayout != null) {
            return staticLayout.getLineCount();
        }
        return 0;
    }

    public void setLineSpacingAdd(float f) {
        this.lineSpacingAdd = f;
    }

    public float getLineSpacingAdd() {
        return this.textLayout.getSpacingAdd();
    }

    public void setLineSpacingMultiplier(float f) {
        this.lineSpacingMultiplier = f;
    }

    public float getLineSpacingMultiplier() {
        return this.textLayout.getSpacingMultiplier();
    }

    public void setHyphenationFrequency(int i) {
        this.hyphenationFrequency = i;
    }

    public int getHyphenationFrequency() {
        return this.hyphenationFrequency;
    }

    private static boolean isClose(float f, float f2) {
        return Math.abs(f - f2) < 0.001f;
    }

    public ColorStateList getExpandedTextColor() {
        return this.expandedTextColor;
    }

    public ColorStateList getCollapsedTextColor() {
        return this.collapsedTextColor;
    }

    private static int blendColors(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.argb((int) ((((float) Color.alpha(i)) * f2) + (((float) Color.alpha(i2)) * f)), (int) ((((float) Color.red(i)) * f2) + (((float) Color.red(i2)) * f)), (int) ((((float) Color.green(i)) * f2) + (((float) Color.green(i2)) * f)), (int) ((((float) Color.blue(i)) * f2) + (((float) Color.blue(i2)) * f)));
    }

    private static float lerp(float f, float f2, float f3, TimeInterpolator timeInterpolator) {
        if (timeInterpolator != null) {
            f3 = timeInterpolator.getInterpolation(f3);
        }
        return AnimationUtils.lerp(f, f2, f3);
    }

    private static boolean rectEquals(Rect rect, int i, int i2, int i3, int i4) {
        return rect.left == i && rect.top == i2 && rect.right == i3 && rect.bottom == i4;
    }
}
