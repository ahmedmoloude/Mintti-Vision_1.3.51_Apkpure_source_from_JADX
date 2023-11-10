package com.p020kl.commonbase.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.utils.SizeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* renamed from: com.kl.commonbase.views.PickerScrollView */
public class PickerScrollView extends View {
    public static final float MARGIN_ALPHA = 2.8f;
    public static final float SPEED = 2.0f;
    public static final String TAG = "PickerScrollView:";
    private Context context;
    private boolean isInit = false;
    private int mColorText = 10066329;
    private int mCurrentSelected;
    private List<String> mDataList;
    private float mLastDownY;
    private float mMaxTextAlpha = 255.0f;
    private float mMaxTextSize = 20.0f;
    private float mMinTextAlpha = 120.0f;
    private float mMinTextSize = 10.0f;
    /* access modifiers changed from: private */
    public float mMoveLen = 0.0f;
    private Paint mPaint;
    private onSelectListener mSelectListener;
    /* access modifiers changed from: private */
    public MyTimerTask mTask;
    private int mViewHeight;
    private int mViewWidth;
    private float textSize;
    private Timer timer;
    Handler updateHandler = new Handler() {
        public void handleMessage(Message message) {
            if (Math.abs(PickerScrollView.this.mMoveLen) < 2.0f) {
                float unused = PickerScrollView.this.mMoveLen = 0.0f;
                if (PickerScrollView.this.mTask != null) {
                    PickerScrollView.this.mTask.cancel();
                    MyTimerTask unused2 = PickerScrollView.this.mTask = null;
                    PickerScrollView.this.performSelect();
                }
            } else {
                PickerScrollView pickerScrollView = PickerScrollView.this;
                float unused3 = pickerScrollView.mMoveLen = pickerScrollView.mMoveLen - ((PickerScrollView.this.mMoveLen / Math.abs(PickerScrollView.this.mMoveLen)) * 2.0f);
            }
            PickerScrollView.this.invalidate();
        }
    };

    /* renamed from: com.kl.commonbase.views.PickerScrollView$onSelectListener */
    public interface onSelectListener {
        void onSelect(String str);
    }

    public PickerScrollView(Context context2) {
        super(context2);
        init(context2);
    }

    public PickerScrollView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        init(context2);
    }

    public void setOnSelectListener(onSelectListener onselectlistener) {
        this.mSelectListener = onselectlistener;
    }

    /* access modifiers changed from: private */
    public void performSelect() {
        onSelectListener onselectlistener = this.mSelectListener;
        if (onselectlistener != null) {
            onselectlistener.onSelect(this.mDataList.get(this.mCurrentSelected));
        }
    }

    public void setData(List<String> list) {
        this.mDataList = list;
        this.mCurrentSelected = list.size() / 2;
        invalidate();
    }

    public void setSelected(int i) {
        this.mCurrentSelected = i;
        int size = (this.mDataList.size() / 2) - this.mCurrentSelected;
        int i2 = 0;
        if (size < 0) {
            while (i2 < (-size)) {
                moveHeadToTail();
                this.mCurrentSelected--;
                i2++;
            }
        } else if (size > 0) {
            while (i2 < size) {
                moveTailToHead();
                this.mCurrentSelected++;
                i2++;
            }
        }
        onSelectListener onselectlistener = this.mSelectListener;
        if (onselectlistener != null) {
            onselectlistener.onSelect(this.mDataList.get(this.mCurrentSelected));
        }
        invalidate();
    }

    public void setSelected(String str) {
        for (int i = 0; i < this.mDataList.size(); i++) {
            if (this.mDataList.get(i).equals(str)) {
                setSelected(i);
                return;
            }
        }
    }

    private void moveHeadToTail() {
        this.mDataList.remove(0);
        this.mDataList.add(this.mDataList.get(0));
    }

    private void moveTailToHead() {
        List<String> list = this.mDataList;
        List<String> list2 = this.mDataList;
        list2.remove(list2.size() - 1);
        this.mDataList.add(0, list.get(list.size() - 1));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mViewHeight = getMeasuredHeight();
        this.mViewWidth = getMeasuredWidth();
        float sp2px = (float) SizeUtils.sp2px(this.context, 25.0f);
        this.mMaxTextSize = sp2px;
        this.mMinTextSize = sp2px / 2.0f;
        this.isInit = true;
        invalidate();
    }

    private void init(Context context2) {
        this.context = context2;
        this.timer = new Timer();
        this.mDataList = new ArrayList();
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setColor(this.mColorText);
        this.textSize = (float) SizeUtils.sp2px(context2, 17.0f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isInit) {
            drawData(canvas);
        }
    }

    private void drawData(Canvas canvas) {
        float parabola = parabola(((float) this.mViewHeight) / 4.0f, this.mMoveLen);
        float f = this.mMaxTextSize;
        float f2 = this.mMinTextSize;
        this.mPaint.setTextSize(((f - f2) * parabola) + f2);
        Paint paint = this.mPaint;
        float f3 = this.mMaxTextAlpha;
        float f4 = this.mMinTextAlpha;
        paint.setAlpha((int) (((f3 - f4) * parabola) + f4));
        float f5 = (float) ((((double) this.mViewHeight) / 2.0d) + ((double) this.mMoveLen));
        Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
        float f6 = (float) (((double) f5) - ((((double) fontMetricsInt.bottom) / 2.0d) + (((double) fontMetricsInt.top) / 2.0d)));
        String str = this.mDataList.get(this.mCurrentSelected);
        this.mPaint.setColor(getResources().getColor(C1544R.C1546color.colorPrimary));
        canvas.drawText(str, (float) (((double) this.mViewWidth) / 2.0d), f6, this.mPaint);
        this.mPaint.setColor(this.mColorText);
        for (int i = 1; this.mCurrentSelected - i >= 0; i++) {
            drawOtherText(canvas, i, -1);
        }
        for (int i2 = 1; this.mCurrentSelected + i2 < this.mDataList.size(); i2++) {
            drawOtherText(canvas, i2, 1);
        }
    }

    private void drawOtherText(Canvas canvas, int i, int i2) {
        float f = (float) i2;
        float f2 = (this.mMinTextSize * 2.8f * ((float) i)) + (this.mMoveLen * f);
        float parabola = parabola(((float) this.mViewHeight) / 4.0f, f2);
        float f3 = this.mMaxTextSize;
        float f4 = this.mMinTextSize;
        this.mPaint.setTextSize(((f3 - f4) * parabola) + f4);
        Paint paint = this.mPaint;
        float f5 = this.mMaxTextAlpha;
        float f6 = this.mMinTextAlpha;
        paint.setAlpha((int) (((f5 - f6) * parabola) + f6));
        Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
        canvas.drawText(this.mDataList.get(this.mCurrentSelected + (i2 * i)), (float) (((double) this.mViewWidth) / 2.0d), (float) (((double) ((float) ((((double) this.mViewHeight) / 2.0d) + ((double) (f * f2))))) - ((((double) fontMetricsInt.bottom) / 2.0d) + (((double) fontMetricsInt.top) / 2.0d))), this.mPaint);
    }

    private float parabola(float f, float f2) {
        float pow = (float) (1.0d - Math.pow((double) (f2 / f), 2.0d));
        if (pow < 0.0f) {
            return 0.0f;
        }
        return pow;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            doDown(motionEvent);
        } else if (actionMasked == 1) {
            doUp(motionEvent);
        } else if (actionMasked == 2) {
            doMove(motionEvent);
        }
        return true;
    }

    private void doDown(MotionEvent motionEvent) {
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        this.mLastDownY = motionEvent.getY();
    }

    private void doMove(MotionEvent motionEvent) {
        float y = this.mMoveLen + (motionEvent.getY() - this.mLastDownY);
        this.mMoveLen = y;
        float f = this.mMinTextSize;
        if (y > (f * 2.8f) / 2.0f) {
            moveTailToHead();
            this.mMoveLen -= this.mMinTextSize * 2.8f;
        } else if (y < (f * -2.8f) / 2.0f) {
            moveHeadToTail();
            this.mMoveLen += this.mMinTextSize * 2.8f;
        }
        this.mLastDownY = motionEvent.getY();
        invalidate();
    }

    private void doUp(MotionEvent motionEvent) {
        if (((double) Math.abs(this.mMoveLen)) < 1.0E-4d) {
            this.mMoveLen = 0.0f;
            return;
        }
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        MyTimerTask myTimerTask2 = new MyTimerTask(this.updateHandler);
        this.mTask = myTimerTask2;
        this.timer.schedule(myTimerTask2, 0, 10);
    }

    /* renamed from: com.kl.commonbase.views.PickerScrollView$MyTimerTask */
    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler2) {
            this.handler = handler2;
        }

        public void run() {
            Handler handler2 = this.handler;
            handler2.sendMessage(handler2.obtainMessage());
        }
    }
}
