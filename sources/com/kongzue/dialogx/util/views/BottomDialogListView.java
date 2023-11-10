package com.kongzue.dialogx.util.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.view.ContextThemeWrapper;
import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.interfaces.BottomMenuListViewTouchEvent;

public class BottomDialogListView extends ListView {
    private BottomMenuListViewTouchEvent bottomMenuListViewTouchEvent;
    private BottomDialog.DialogImpl dialogImpl;
    private int mPosition;
    private int size = 1;
    private float touchDownY;

    public BottomDialogListView(Context context) {
        super(context);
    }

    public BottomDialogListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BottomDialogListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BottomDialogListView(BottomDialog.DialogImpl dialogImpl2, Context context) {
        super(context);
        this.dialogImpl = dialogImpl2;
        setVerticalScrollBarEnabled(false);
    }

    public BottomDialogListView(BottomDialog.DialogImpl dialogImpl2, Context context, int i) {
        super(new ContextThemeWrapper(context, i));
        this.dialogImpl = dialogImpl2;
        setVerticalScrollBarEnabled(false);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }

    private int dip2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked() & 255;
        if (actionMasked == 0) {
            this.touchDownY = motionEvent.getY();
            BottomMenuListViewTouchEvent bottomMenuListViewTouchEvent2 = this.bottomMenuListViewTouchEvent;
            if (bottomMenuListViewTouchEvent2 != null) {
                bottomMenuListViewTouchEvent2.down(motionEvent);
            }
            this.mPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            return super.dispatchTouchEvent(motionEvent);
        } else if (actionMasked == 2) {
            BottomMenuListViewTouchEvent bottomMenuListViewTouchEvent3 = this.bottomMenuListViewTouchEvent;
            if (bottomMenuListViewTouchEvent3 != null) {
                bottomMenuListViewTouchEvent3.move(motionEvent);
            }
            if (Math.abs(this.touchDownY - motionEvent.getY()) <= ((float) dip2px(5.0f))) {
                return true;
            }
            motionEvent.setAction(3);
            dispatchTouchEvent(motionEvent);
            return false;
        } else {
            if (actionMasked == 1 || actionMasked == 3) {
                BottomMenuListViewTouchEvent bottomMenuListViewTouchEvent4 = this.bottomMenuListViewTouchEvent;
                if (bottomMenuListViewTouchEvent4 != null) {
                    bottomMenuListViewTouchEvent4.mo26990up(motionEvent);
                }
                if (pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY()) == this.mPosition) {
                    super.dispatchTouchEvent(motionEvent);
                } else {
                    setPressed(false);
                    invalidate();
                }
            }
            return super.dispatchTouchEvent(motionEvent);
        }
    }

    public BottomMenuListViewTouchEvent getBottomMenuListViewTouchEvent() {
        return this.bottomMenuListViewTouchEvent;
    }

    public void setAdapter(ListAdapter listAdapter) {
        this.size = listAdapter.getCount();
        super.setAdapter(listAdapter);
    }

    public BottomDialogListView setBottomMenuListViewTouchEvent(BottomMenuListViewTouchEvent bottomMenuListViewTouchEvent2) {
        this.bottomMenuListViewTouchEvent = bottomMenuListViewTouchEvent2;
        return this;
    }
}
