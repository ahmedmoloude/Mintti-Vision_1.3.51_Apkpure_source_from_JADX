package com.p020kl.commonbase.views.countrypicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import com.p020kl.commonbase.C1544R;
import java.util.ArrayList;
import java.util.Arrays;

/* renamed from: com.kl.commonbase.views.countrypicker.SideBar */
public class SideBar extends View {
    private int cellHeight;
    private int cellWidth;
    private int currentIndex;
    public final ArrayList<String> indexes;
    private int letterColor;
    private int letterSize;
    private OnLetterChangeListener onLetterChangeListener;
    private Paint paint;
    private int selectColor;
    private float textHeight;

    /* renamed from: com.kl.commonbase.views.countrypicker.SideBar$OnLetterChangeListener */
    public interface OnLetterChangeListener {
        void onLetterChange(String str);

        void onReset();
    }

    public SideBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public SideBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SideBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ArrayList<String> arrayList = new ArrayList<>();
        this.indexes = arrayList;
        this.currentIndex = -1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1544R.styleable.SideBar, i, 0);
        this.letterColor = obtainStyledAttributes.getColor(C1544R.styleable.SideBar_letterColor, ViewCompat.MEASURED_STATE_MASK);
        this.selectColor = obtainStyledAttributes.getColor(C1544R.styleable.SideBar_selectColor, -16711681);
        this.letterSize = obtainStyledAttributes.getDimensionPixelSize(C1544R.styleable.SideBar_letterSize, 24);
        obtainStyledAttributes.recycle();
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setAntiAlias(true);
        Paint.FontMetrics fontMetrics = this.paint.getFontMetrics();
        this.textHeight = (float) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent));
        arrayList.addAll(Arrays.asList(new String[]{ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D", ExifInterface.LONGITUDE_EAST, "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", "Y", "Z"}));
    }

    public void addIndex(String str, int i) {
        this.indexes.add(i, str);
        invalidate();
    }

    public void removeIndex(String str) {
        this.indexes.remove(str);
        invalidate();
    }

    public void setLetterSize(int i) {
        if (this.letterSize != i) {
            this.letterSize = i;
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.cellWidth = getMeasuredWidth();
        this.cellHeight = getMeasuredHeight() / this.indexes.size();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setTextSize((float) this.letterSize);
        for (int i = 0; i < this.indexes.size(); i++) {
            String str = this.indexes.get(i);
            float measureText = (((float) this.cellWidth) - this.paint.measureText(str)) * 0.5f;
            int i2 = this.cellHeight;
            float f = ((((float) i2) + this.textHeight) * 0.5f) + ((float) (i2 * i));
            if (i == this.currentIndex) {
                this.paint.setColor(this.selectColor);
            } else {
                this.paint.setColor(this.letterColor);
            }
            canvas.drawText(str, measureText, f, this.paint);
        }
    }

    public OnLetterChangeListener getOnLetterChangeListener() {
        return this.onLetterChangeListener;
    }

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener2) {
        this.onLetterChangeListener = onLetterChangeListener2;
    }

    public String getLetter(int i) {
        return (i < 0 || i >= this.indexes.size()) ? "" : this.indexes.get(i);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        OnLetterChangeListener onLetterChangeListener2;
        OnLetterChangeListener onLetterChangeListener3;
        int action = motionEvent.getAction();
        if (action == 0) {
            int y = ((int) motionEvent.getY()) / this.cellHeight;
            this.currentIndex = y;
            if (y >= 0 && y <= this.indexes.size() - 1 && (onLetterChangeListener2 = this.onLetterChangeListener) != null) {
                onLetterChangeListener2.onLetterChange(this.indexes.get(this.currentIndex));
            }
            invalidate();
        } else if (action == 1) {
            this.currentIndex = -1;
            invalidate();
            OnLetterChangeListener onLetterChangeListener4 = this.onLetterChangeListener;
            if (onLetterChangeListener4 != null) {
                onLetterChangeListener4.onReset();
            }
        } else if (action == 2) {
            int y2 = ((int) motionEvent.getY()) / this.cellHeight;
            this.currentIndex = y2;
            if (y2 >= 0 && y2 <= this.indexes.size() - 1 && (onLetterChangeListener3 = this.onLetterChangeListener) != null) {
                onLetterChangeListener3.onLetterChange(this.indexes.get(this.currentIndex));
            }
            invalidate();
        }
        return true;
    }
}
