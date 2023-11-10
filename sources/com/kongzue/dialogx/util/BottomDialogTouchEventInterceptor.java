package com.kongzue.dialogx.util;

import android.content.res.Resources;
import android.view.View;
import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.interfaces.ScrollController;

public class BottomDialogTouchEventInterceptor {
    /* access modifiers changed from: private */
    public float bkgOldY;
    /* access modifiers changed from: private */
    public float bkgTouchDownY;
    /* access modifiers changed from: private */
    public boolean isBkgTouched = false;
    private int oldMode;
    /* access modifiers changed from: private */
    public float scrolledY;

    public BottomDialogTouchEventInterceptor(BottomDialog bottomDialog, BottomDialog.DialogImpl dialogImpl) {
        refresh(bottomDialog, dialogImpl);
    }

    public void refresh(BottomDialog bottomDialog, final BottomDialog.DialogImpl dialogImpl) {
        if (bottomDialog != null && dialogImpl != null && dialogImpl.bkg != null && dialogImpl.scrollView != null) {
            if (bottomDialog.isAllowInterceptTouch()) {
                dialogImpl.bkg.setOnTouchListener(new View.OnTouchListener() {
                    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000e, code lost:
                        if (r8 != 3) goto L_0x01b9;
                     */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public boolean onTouch(android.view.View r8, android.view.MotionEvent r9) {
                        /*
                            r7 = this;
                            int r8 = r9.getAction()
                            r0 = 0
                            r1 = 1
                            if (r8 == 0) goto L_0x019e
                            r2 = 2
                            if (r8 == r1) goto L_0x00c2
                            if (r8 == r2) goto L_0x0012
                            r9 = 3
                            if (r8 == r9) goto L_0x00c2
                            goto L_0x01b9
                        L_0x0012:
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r8 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            boolean r8 = r8.isBkgTouched
                            if (r8 == 0) goto L_0x01b9
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            android.widget.RelativeLayout r8 = r8.boxBkg
                            float r8 = r8.getY()
                            float r2 = r9.getY()
                            float r8 = r8 + r2
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r2 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            float r2 = r2.bkgTouchDownY
                            float r8 = r8 - r2
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r2 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r2 = r2.scrollView
                            boolean r2 = r2.isCanScroll()
                            if (r2 == 0) goto L_0x0097
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r2 = r3
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r2 = r2.boxRoot
                            android.graphics.Rect r2 = r2.getUnsafePlace()
                            int r2 = r2.top
                            float r2 = (float) r2
                            int r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
                            if (r2 <= 0) goto L_0x0074
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r2 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r2 = r2.scrollView
                            int r2 = r2.getScrollDistance()
                            if (r2 != 0) goto L_0x0069
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r9 = r9.scrollView
                            boolean r9 = r9 instanceof com.kongzue.dialogx.interfaces.ScrollController
                            if (r9 == 0) goto L_0x0060
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r9 = r9.scrollView
                            r9.lockScroll(r1)
                        L_0x0060:
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            android.widget.RelativeLayout r9 = r9.boxBkg
                            r9.setY(r8)
                            goto L_0x01b9
                        L_0x0069:
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r8 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            float r9 = r9.getY()
                            float unused = r8.bkgTouchDownY = r9
                            goto L_0x01b9
                        L_0x0074:
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r8 = r8.scrollView
                            boolean r8 = r8 instanceof com.kongzue.dialogx.interfaces.ScrollController
                            if (r8 == 0) goto L_0x0083
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r8 = r8.scrollView
                            r8.lockScroll(r0)
                        L_0x0083:
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            android.widget.RelativeLayout r8 = r8.boxBkg
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r9 = r9.boxRoot
                            android.graphics.Rect r9 = r9.getUnsafePlace()
                            int r9 = r9.top
                            float r9 = (float) r9
                            r8.setY(r9)
                            goto L_0x01b9
                        L_0x0097:
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r9 = r9.boxRoot
                            android.graphics.Rect r9 = r9.getUnsafePlace()
                            int r9 = r9.top
                            float r9 = (float) r9
                            int r9 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
                            if (r9 <= 0) goto L_0x00ae
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            android.widget.RelativeLayout r9 = r9.boxBkg
                            r9.setY(r8)
                            return r1
                        L_0x00ae:
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            android.widget.RelativeLayout r8 = r8.boxBkg
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r9 = r9.boxRoot
                            android.graphics.Rect r9 = r9.getUnsafePlace()
                            int r9 = r9.top
                            float r9 = (float) r9
                            r8.setY(r9)
                            goto L_0x01b9
                        L_0x00c2:
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r8 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r9 = r9.scrollView
                            int r9 = r9.getScrollDistance()
                            float r9 = (float) r9
                            float unused = r8.scrolledY = r9
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r8 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            boolean unused = r8.isBkgTouched = r0
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r8 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            float r8 = r8.bkgOldY
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r9 = r9.boxRoot
                            android.graphics.Rect r9 = r9.getUnsafePlace()
                            int r9 = r9.top
                            float r9 = (float) r9
                            r3 = 300(0x12c, double:1.48E-321)
                            java.lang.String r5 = "y"
                            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
                            if (r8 != 0) goto L_0x0148
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            android.widget.RelativeLayout r8 = r8.boxBkg
                            float r8 = r8.getY()
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r9 = r9.boxRoot
                            android.graphics.Rect r9 = r9.getUnsafePlace()
                            int r9 = r9.top
                            float r9 = (float) r9
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r6 = r3
                            float r6 = r6.bkgEnterAimY
                            float r9 = r9 + r6
                            int r6 = com.kongzue.dialogx.DialogX.touchSlideTriggerThreshold
                            float r6 = (float) r6
                            float r9 = r9 + r6
                            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
                            if (r8 <= 0) goto L_0x0115
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            r8.preDismiss()
                            goto L_0x01b9
                        L_0x0115:
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            android.widget.RelativeLayout r8 = r8.boxBkg
                            float r8 = r8.getY()
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            float r9 = r9.bkgOldY
                            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
                            if (r8 == 0) goto L_0x01b9
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            android.widget.RelativeLayout r8 = r8.boxBkg
                            float[] r9 = new float[r2]
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r2 = r3
                            android.widget.RelativeLayout r2 = r2.boxBkg
                            float r2 = r2.getY()
                            r9[r0] = r2
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r2 = r3
                            float r2 = r2.bkgEnterAimY
                            r9[r1] = r2
                            android.animation.ObjectAnimator r8 = android.animation.ObjectAnimator.ofFloat(r8, r5, r9)
                            r8.setDuration(r3)
                            r8.start()
                            goto L_0x01b9
                        L_0x0148:
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            android.widget.RelativeLayout r8 = r8.boxBkg
                            float r8 = r8.getY()
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            float r9 = r9.bkgOldY
                            int r6 = com.kongzue.dialogx.DialogX.touchSlideTriggerThreshold
                            float r6 = (float) r6
                            float r9 = r9 + r6
                            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
                            if (r8 <= 0) goto L_0x0164
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            r8.preDismiss()
                            goto L_0x01b9
                        L_0x0164:
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            android.widget.RelativeLayout r8 = r8.boxBkg
                            float r8 = r8.getY()
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            float r9 = r9.bkgOldY
                            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
                            if (r8 == 0) goto L_0x01b9
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r8 = r3
                            android.widget.RelativeLayout r8 = r8.boxBkg
                            float[] r9 = new float[r2]
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r2 = r3
                            android.widget.RelativeLayout r2 = r2.boxBkg
                            float r2 = r2.getY()
                            r9[r0] = r2
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r2 = r3
                            com.kongzue.dialogx.util.views.DialogXBaseRelativeLayout r2 = r2.boxRoot
                            android.graphics.Rect r2 = r2.getUnsafePlace()
                            int r2 = r2.top
                            float r2 = (float) r2
                            r9[r1] = r2
                            android.animation.ObjectAnimator r8 = android.animation.ObjectAnimator.ofFloat(r8, r5, r9)
                            r8.setDuration(r3)
                            r8.start()
                            goto L_0x01b9
                        L_0x019e:
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r8 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            float r9 = r9.getY()
                            float unused = r8.bkgTouchDownY = r9
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r8 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            boolean unused = r8.isBkgTouched = r1
                            com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor r8 = com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.this
                            com.kongzue.dialogx.dialogs.BottomDialog$DialogImpl r9 = r3
                            android.widget.RelativeLayout r9 = r9.boxBkg
                            float r9 = r9.getY()
                            float unused = r8.bkgOldY = r9
                        L_0x01b9:
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.util.BottomDialogTouchEventInterceptor.C21211.onTouch(android.view.View, android.view.MotionEvent):boolean");
                    }
                });
                return;
            }
            if (dialogImpl.scrollView instanceof ScrollController) {
                dialogImpl.scrollView.lockScroll(false);
            }
            dialogImpl.bkg.setOnTouchListener((View.OnTouchListener) null);
        }
    }

    private int dip2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }
}
