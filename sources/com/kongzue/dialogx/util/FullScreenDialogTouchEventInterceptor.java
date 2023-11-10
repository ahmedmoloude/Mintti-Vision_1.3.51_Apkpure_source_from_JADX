package com.kongzue.dialogx.util;

import android.content.res.Resources;
import android.view.View;
import com.kongzue.dialogx.dialogs.FullScreenDialog;
import com.kongzue.dialogx.interfaces.ScrollController;

public class FullScreenDialogTouchEventInterceptor {
    /* access modifiers changed from: private */
    public float bkgOldY;
    /* access modifiers changed from: private */
    public float bkgTouchDownY;
    /* access modifiers changed from: private */
    public boolean isBkgTouched = false;

    public FullScreenDialogTouchEventInterceptor(FullScreenDialog fullScreenDialog, FullScreenDialog.DialogImpl dialogImpl) {
        refresh(fullScreenDialog, dialogImpl);
    }

    public void refresh(FullScreenDialog fullScreenDialog, final FullScreenDialog.DialogImpl dialogImpl) {
        if (fullScreenDialog != null && dialogImpl != null && dialogImpl.bkg != null) {
            if (fullScreenDialog.isAllowInterceptTouch()) {
                View view = dialogImpl.boxCustom;
                if (dialogImpl.scrollView != null) {
                    view = dialogImpl.bkg;
                }
                view.setOnTouchListener(new View.OnTouchListener() {
                    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000f, code lost:
                        if (r9 != 3) goto L_0x019f;
                     */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public boolean onTouch(android.view.View r9, android.view.MotionEvent r10) {
                        /*
                            r8 = this;
                            int r9 = r10.getAction()
                            r0 = 0
                            r1 = 1
                            if (r9 == 0) goto L_0x0184
                            r2 = 2
                            r3 = 0
                            if (r9 == r1) goto L_0x0097
                            if (r9 == r2) goto L_0x0013
                            r10 = 3
                            if (r9 == r10) goto L_0x0097
                            goto L_0x019f
                        L_0x0013:
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            boolean r9 = r9.isBkgTouched
                            if (r9 == 0) goto L_0x019f
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            float r9 = r9.getY()
                            float r2 = r10.getY()
                            float r9 = r9 + r2
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r2 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            float r2 = r2.bkgTouchDownY
                            float r9 = r9 - r2
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r2 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r2 = r2.scrollView
                            if (r2 == 0) goto L_0x0088
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r2 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r2 = r2.scrollView
                            boolean r2 = r2.isCanScroll()
                            if (r2 == 0) goto L_0x0088
                            int r2 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                            if (r2 <= 0) goto L_0x0070
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r2 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r2 = r2.scrollView
                            int r2 = r2.getScrollDistance()
                            if (r2 != 0) goto L_0x0065
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r10 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r10 = r10.scrollView
                            boolean r10 = r10 instanceof com.kongzue.dialogx.interfaces.ScrollController
                            if (r10 == 0) goto L_0x005c
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r10 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r10 = r10.scrollView
                            r10.lockScroll(r1)
                        L_0x005c:
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r10 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r10 = r10.bkg
                            r10.setY(r9)
                            goto L_0x019f
                        L_0x0065:
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            float r10 = r10.getY()
                            float unused = r9.bkgTouchDownY = r10
                            goto L_0x019f
                        L_0x0070:
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r9 = r9.scrollView
                            boolean r9 = r9 instanceof com.kongzue.dialogx.interfaces.ScrollController
                            if (r9 == 0) goto L_0x007f
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.interfaces.ScrollController r9 = r9.scrollView
                            r9.lockScroll(r0)
                        L_0x007f:
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            r9.setY(r3)
                            goto L_0x019f
                        L_0x0088:
                            int r10 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                            if (r10 >= 0) goto L_0x008d
                            goto L_0x008e
                        L_0x008d:
                            r3 = r9
                        L_0x008e:
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            r9.setY(r3)
                            goto L_0x019f
                        L_0x0097:
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            boolean unused = r9.isBkgTouched = r0
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            float r9 = r9.bkgOldY
                            r4 = 300(0x12c, double:1.48E-321)
                            java.lang.String r10 = "y"
                            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                            if (r9 != 0) goto L_0x0114
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            float r9 = r9.getY()
                            int r6 = com.kongzue.dialogx.DialogX.touchSlideTriggerThreshold
                            float r6 = (float) r6
                            int r9 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
                            if (r9 >= 0) goto L_0x00d7
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            float[] r2 = new float[r2]
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r6 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r6 = r6.bkg
                            float r6 = r6.getY()
                            r2[r0] = r6
                            r2[r1] = r3
                            android.animation.ObjectAnimator r9 = android.animation.ObjectAnimator.ofFloat(r9, r10, r2)
                            r9.setDuration(r4)
                            r9.start()
                            goto L_0x019f
                        L_0x00d7:
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            float r9 = r9.getY()
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r3 = r3
                            float r3 = r3.bkgEnterAimY
                            int r6 = com.kongzue.dialogx.DialogX.touchSlideTriggerThreshold
                            float r6 = (float) r6
                            float r3 = r3 + r6
                            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                            if (r9 <= 0) goto L_0x00f2
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            r9.preDismiss()
                            goto L_0x019f
                        L_0x00f2:
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            float[] r2 = new float[r2]
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r3 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r3 = r3.bkg
                            float r3 = r3.getY()
                            r2[r0] = r3
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r3 = r3
                            float r3 = r3.bkgEnterAimY
                            r2[r1] = r3
                            android.animation.ObjectAnimator r9 = android.animation.ObjectAnimator.ofFloat(r9, r10, r2)
                            r9.setDuration(r4)
                            r9.start()
                            goto L_0x019f
                        L_0x0114:
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            float r9 = r9.getY()
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r6 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            float r6 = r6.bkgOldY
                            int r7 = com.kongzue.dialogx.DialogX.touchSlideTriggerThreshold
                            float r7 = (float) r7
                            float r6 = r6 - r7
                            int r9 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
                            if (r9 >= 0) goto L_0x0147
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            float[] r2 = new float[r2]
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r6 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r6 = r6.bkg
                            float r6 = r6.getY()
                            r2[r0] = r6
                            r2[r1] = r3
                            android.animation.ObjectAnimator r9 = android.animation.ObjectAnimator.ofFloat(r9, r10, r2)
                            r9.setDuration(r4)
                            r9.start()
                            goto L_0x019f
                        L_0x0147:
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            float r9 = r9.getY()
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r3 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            float r3 = r3.bkgOldY
                            int r6 = com.kongzue.dialogx.DialogX.touchSlideTriggerThreshold
                            float r6 = (float) r6
                            float r3 = r3 + r6
                            int r9 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                            if (r9 <= 0) goto L_0x0163
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            r9.preDismiss()
                            goto L_0x019f
                        L_0x0163:
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r9 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r9 = r9.bkg
                            float[] r2 = new float[r2]
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r3 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r3 = r3.bkg
                            float r3 = r3.getY()
                            r2[r0] = r3
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r3 = r3
                            float r3 = r3.bkgEnterAimY
                            r2[r1] = r3
                            android.animation.ObjectAnimator r9 = android.animation.ObjectAnimator.ofFloat(r9, r10, r2)
                            r9.setDuration(r4)
                            r9.start()
                            goto L_0x019f
                        L_0x0184:
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            float r10 = r10.getY()
                            float unused = r9.bkgTouchDownY = r10
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            boolean unused = r9.isBkgTouched = r1
                            com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor r9 = com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.this
                            com.kongzue.dialogx.dialogs.FullScreenDialog$DialogImpl r10 = r3
                            com.kongzue.dialogx.util.views.MaxRelativeLayout r10 = r10.bkg
                            float r10 = r10.getY()
                            float unused = r9.bkgOldY = r10
                        L_0x019f:
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.kongzue.dialogx.util.FullScreenDialogTouchEventInterceptor.C21231.onTouch(android.view.View, android.view.MotionEvent):boolean");
                    }
                });
                return;
            }
            View view2 = dialogImpl.boxCustom;
            if (dialogImpl.scrollView != null) {
                view2 = dialogImpl.bkg;
            }
            if (dialogImpl.scrollView instanceof ScrollController) {
                dialogImpl.scrollView.lockScroll(false);
            }
            view2.setOnTouchListener((View.OnTouchListener) null);
        }
    }

    private int dip2px(float f) {
        return (int) ((f * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }
}
