package com.p020kl.commonbase.views.ecg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.p020kl.commonbase.C1544R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.kl.commonbase.views.ecg.ChartView */
public class ChartView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public static final int TIME_IN_FRAME = 30;
    private int allDataSize;
    private float dataSpacing;
    private List<PointF> datas;
    private int delGap;
    private int drawPointCostTime;
    private PointF endPoint;
    private float gain;
    private float gapX;
    private int index;
    private boolean isClearData;
    private boolean isDelEffect;
    private boolean isDraw;
    private boolean isRunning;
    private Paint linePaint;
    private Canvas mCanvas;
    private SurfaceHolder mHolder;
    private float mViewHalfHeight;
    private float mViewWidth;
    private List<Integer> nativeDatas;
    private int pagerSpeed;
    private Path path1;
    private Path path2;
    private int pointIndex;
    private int sampleRate;
    volatile ThreadPoolExecutor singleThreadExecutor;
    private PointF startPoint;

    /* renamed from: t */
    private Thread f850t;
    private float totalLattices;

    /* renamed from: xS */
    private float f851xS;

    private float calcRealMv(int i) {
        return (float) ((((((double) i) * 12.247d) / 9.5d) / 8.0d) / 1000.0d);
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    public ChartView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ChartView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.gapX = 0.2f;
        this.delGap = 50;
        this.index = 0;
        this.isDelEffect = true;
        this.isDraw = false;
        this.nativeDatas = null;
        this.sampleRate = 512;
        this.drawPointCostTime = Math.round(1.953125f);
        this.pagerSpeed = 1;
        this.gain = 1.0f;
        this.isClearData = false;
        SurfaceHolder holder = getHolder();
        this.mHolder = holder;
        holder.addCallback(this);
        setZOrderOnTop(true);
        this.mHolder.setFormat(-3);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
        Paint paint = new Paint();
        this.linePaint = paint;
        paint.setStrokeWidth(5.0f);
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setAntiAlias(true);
        this.linePaint.setColor(getResources().getColor(C1544R.C1546color.colorPrimary));
        this.datas = new ArrayList();
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.isRunning = true;
        Thread thread = new Thread(this);
        this.f850t = thread;
        thread.start();
        startSingleThreadExecutor();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mViewWidth = (float) getWidth();
        this.mViewHalfHeight = ((float) getHeight()) / 2.0f;
        this.f851xS = EcgBackgroundView.f852xS;
        float f = EcgBackgroundView.totalLattices;
        this.totalLattices = f;
        float f2 = ((float) this.sampleRate) / (((float) this.pagerSpeed) * 25.0f);
        this.allDataSize = (int) (f * f2);
        this.dataSpacing = this.f851xS / f2;
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.isRunning = false;
        List<PointF> list = this.datas;
        if (list != null) {
            list.clear();
        }
        SurfaceHolder surfaceHolder2 = this.mHolder;
        if (surfaceHolder2 != null) {
            surfaceHolder2.removeCallback((SurfaceHolder.Callback) null);
        }
        if (this.singleThreadExecutor != null && !this.singleThreadExecutor.isShutdown()) {
            this.singleThreadExecutor.shutdownNow();
        }
        this.index = 0;
    }

    public void run() {
        while (this.isRunning) {
            if (this.isDraw) {
                long currentTimeMillis = System.currentTimeMillis();
                draw();
                this.isDraw = false;
                int currentTimeMillis2 = (int) (System.currentTimeMillis() - currentTimeMillis);
                while (currentTimeMillis2 <= 30) {
                    currentTimeMillis2 = (int) (System.currentTimeMillis() - currentTimeMillis);
                    Thread.yield();
                }
            }
        }
    }

    public synchronized void draw() {
        Canvas canvas;
        SurfaceHolder surfaceHolder;
        try {
            Canvas lockCanvas = this.mHolder.lockCanvas();
            this.mCanvas = lockCanvas;
            if (lockCanvas != null) {
                lockCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
                this.mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
                drawLine();
            }
            canvas = this.mCanvas;
            if (canvas != null) {
                surfaceHolder = this.mHolder;
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        } catch (Exception unused) {
            canvas = this.mCanvas;
            if (canvas != null) {
                surfaceHolder = this.mHolder;
            }
        } catch (Throwable th) {
            Canvas canvas2 = this.mCanvas;
            if (canvas2 != null) {
                this.mHolder.unlockCanvasAndPost(canvas2);
            }
            throw th;
        }
    }

    private void drawLine() {
        int i;
        int i2;
        if (this.datas == null) {
            this.datas = new ArrayList();
        }
        if (this.datas.size() >= 4) {
            PointF pointF = this.startPoint;
            if (pointF == null || this.endPoint == null) {
                this.startPoint = new PointF();
                this.endPoint = new PointF();
            } else {
                pointF.set(0.0f, this.mViewHalfHeight / 2.0f);
                this.endPoint.set(0.0f, this.mViewHalfHeight / 2.0f);
            }
            int size = this.datas.size();
            Path path = this.path1;
            if (path == null) {
                this.path1 = new Path();
            } else {
                path.reset();
            }
            this.path1.moveTo(this.datas.get(1).x, this.datas.get(1).y);
            int i3 = 2;
            while (true) {
                i = this.index;
                if (i3 >= i) {
                    break;
                }
                this.path1.lineTo(this.datas.get(i3).x, this.datas.get(i3).y);
                i3++;
            }
            if (!this.isDelEffect || size < (i2 = this.allDataSize) || i + this.delGap + 2 >= i2) {
                this.mCanvas.drawPath(this.path1, this.linePaint);
                return;
            }
            Path path3 = this.path2;
            if (path3 == null) {
                this.path2 = new Path();
            } else {
                path3.reset();
            }
            this.path2.moveTo(this.datas.get(this.index + this.delGap).x, this.datas.get(this.index + this.delGap).y);
            for (int i4 = this.index + this.delGap + 1; i4 < size - 1; i4++) {
                this.path2.lineTo(this.datas.get(i4).x, this.datas.get(i4).y);
            }
            this.mCanvas.drawPath(this.path1, this.linePaint);
            this.mCanvas.drawPath(this.path2, this.linePaint);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0056, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void addPoint(int r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.isRunning     // Catch:{ all -> 0x0057 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r1)
            return
        L_0x0007:
            r0 = 0
            r1.isClearData = r0     // Catch:{ all -> 0x0057 }
            java.util.List<java.lang.Integer> r0 = r1.nativeDatas     // Catch:{ all -> 0x0057 }
            if (r0 != 0) goto L_0x0015
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0057 }
            r0.<init>()     // Catch:{ all -> 0x0057 }
            r1.nativeDatas = r0     // Catch:{ all -> 0x0057 }
        L_0x0015:
            java.util.List<java.lang.Integer> r0 = r1.nativeDatas     // Catch:{ all -> 0x0057 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0057 }
            r0.add(r2)     // Catch:{ all -> 0x0057 }
            int r2 = r1.sampleRate     // Catch:{ all -> 0x0057 }
            r0 = 200(0xc8, float:2.8E-43)
            if (r2 != r0) goto L_0x003b
            java.util.List<java.lang.Integer> r2 = r1.nativeDatas     // Catch:{ all -> 0x0057 }
            int r2 = r2.size()     // Catch:{ all -> 0x0057 }
            r0 = 64
            if (r2 < r0) goto L_0x0055
            java.util.List<java.lang.Integer> r2 = r1.nativeDatas     // Catch:{ all -> 0x0057 }
            r1.addPointThreadExecutor(r2)     // Catch:{ all -> 0x0057 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0057 }
            r2.<init>()     // Catch:{ all -> 0x0057 }
            r1.nativeDatas = r2     // Catch:{ all -> 0x0057 }
            goto L_0x0055
        L_0x003b:
            r0 = 512(0x200, float:7.175E-43)
            if (r2 != r0) goto L_0x0055
            java.util.List<java.lang.Integer> r2 = r1.nativeDatas     // Catch:{ all -> 0x0057 }
            int r2 = r2.size()     // Catch:{ all -> 0x0057 }
            r0 = 256(0x100, float:3.59E-43)
            if (r2 < r0) goto L_0x0055
            java.util.List<java.lang.Integer> r2 = r1.nativeDatas     // Catch:{ all -> 0x0057 }
            r1.addPointThreadExecutor(r2)     // Catch:{ all -> 0x0057 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0057 }
            r2.<init>()     // Catch:{ all -> 0x0057 }
            r1.nativeDatas = r2     // Catch:{ all -> 0x0057 }
        L_0x0055:
            monitor-exit(r1)
            return
        L_0x0057:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p020kl.commonbase.views.ecg.ChartView.addPoint(int):void");
    }

    public synchronized void addPointThreadExecutor(List<Integer> list) {
        if (this.isRunning) {
            if (list != null) {
                if (this.singleThreadExecutor != null) {
                    if (!this.singleThreadExecutor.isShutdown()) {
                        int size = this.singleThreadExecutor.getQueue().size();
                        if (size >= 5) {
                            Log.e("ChartView", "" + this.singleThreadExecutor.getQueue().size());
                            return;
                        }
                        try {
                            this.singleThreadExecutor.execute(new Runnable(list, size) {
                                public final /* synthetic */ List f$1;
                                public final /* synthetic */ int f$2;

                                {
                                    this.f$1 = r2;
                                    this.f$2 = r3;
                                }

                                public final void run() {
                                    ChartView.this.lambda$addPointThreadExecutor$6$ChartView(this.f$1, this.f$2);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                startSingleThreadExecutor();
                return;
            }
            return;
        }
        return;
        return;
    }

    public /* synthetic */ void lambda$addPointThreadExecutor$6$ChartView(List list, int i) {
        for (int i2 = 0; i2 < list.size() && !this.isClearData; i2++) {
            int i3 = this.sampleRate;
            if (i3 == 512) {
                if (i > 2) {
                    if (i2 % 8 == 0) {
                        SystemClock.sleep((((long) this.drawPointCostTime) * 8) - 2);
                    }
                } else if (i2 % 8 == 0) {
                    SystemClock.sleep((((long) this.drawPointCostTime) * 8) - 1);
                }
            } else if (i3 == 200) {
                if (i > 2) {
                    if (i2 % 2 == 0) {
                        SystemClock.sleep(8);
                    }
                } else if (i2 % 2 == 0) {
                    SystemClock.sleep(10);
                }
            }
            addNativePoint(((Integer) list.get(i2)).intValue());
        }
    }

    public void addNativePoint(int i) {
        if (this.isRunning) {
            if (this.datas == null) {
                this.datas = new ArrayList();
            }
            int i2 = this.sampleRate;
            if (i2 == 512) {
                i = (int) (((double) this.mViewHalfHeight) + (((((((double) i) * 18.3d) / 128.0d) * ((double) this.f851xS)) / 100.0d) * ((double) this.gain)));
            } else if (i2 == 200) {
                i = (int) (this.mViewHalfHeight + (calcRealMv(i) * this.f851xS * this.gain * 10.0f));
            }
            float f = this.mViewHalfHeight;
            if (((float) i) > 2.0f * f) {
                i = ((int) f) * 2;
            } else if (i < 0) {
                i = 0;
            }
            if (this.datas.size() < this.allDataSize) {
                this.datas.add(new PointF(((float) this.index) * this.dataSpacing, (float) i));
            } else {
                this.datas.get(this.index).set(((float) this.index) * this.dataSpacing, (float) i);
            }
            int i3 = this.index + 1;
            this.index = i3;
            if (i3 >= this.allDataSize) {
                this.index = 0;
            }
            this.isDraw = true;
        }
    }

    public void clearDatas() {
        List<PointF> list = this.datas;
        if (list != null) {
            list.clear();
        }
        this.isClearData = true;
        startSingleThreadExecutor();
        this.index = 0;
        draw();
    }

    public void drawView() {
        this.isDraw = true;
    }

    public void setDelGap(int i) {
        this.delGap = i;
    }

    public void setIsDelEffect(boolean z) {
        this.isDelEffect = z;
    }

    /* access modifiers changed from: protected */
    public void startSingleThreadExecutor() {
        if (this.singleThreadExecutor != null && !this.singleThreadExecutor.isShutdown()) {
            this.singleThreadExecutor.shutdownNow();
        }
        this.singleThreadExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(10));
    }

    public void setPagerSpeed(int i) {
        this.pagerSpeed = i;
    }

    public void setGain(float f) {
        int i = this.sampleRate;
        if (i == 512) {
            this.gain = f;
        } else if (i == 200) {
            this.gain = f;
        }
    }

    public float getGain() {
        return this.gain;
    }

    public void setSampleRate(int i) {
        this.sampleRate = i;
        float f = (float) i;
        float f2 = f / (((float) this.pagerSpeed) * 25.0f);
        this.allDataSize = (int) (this.totalLattices * f2);
        this.dataSpacing = this.f851xS / f2;
        this.drawPointCostTime = Math.round(1000.0f / f);
    }
}
