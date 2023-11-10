package com.github.barteksc.pdfviewer;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.github.barteksc.pdfviewer.exception.PageRenderingException;
import com.github.barteksc.pdfviewer.model.PagePart;

class RenderingHandler extends Handler {
    static final int MSG_RENDER_TASK = 1;
    private static final String TAG = "com.github.barteksc.pdfviewer.RenderingHandler";
    /* access modifiers changed from: private */
    public PDFView pdfView;
    private RectF renderBounds = new RectF();
    private Matrix renderMatrix = new Matrix();
    private Rect roundedRenderBounds = new Rect();
    private boolean running = false;

    RenderingHandler(Looper looper, PDFView pDFView) {
        super(looper);
        this.pdfView = pDFView;
    }

    /* access modifiers changed from: package-private */
    public void addRenderingTask(int i, float f, float f2, RectF rectF, boolean z, int i2, boolean z2, boolean z3) {
        sendMessage(obtainMessage(1, new RenderingTask(f, f2, rectF, i, z, i2, z2, z3)));
    }

    public void handleMessage(Message message) {
        try {
            final PagePart proceed = proceed((RenderingTask) message.obj);
            if (proceed == null) {
                return;
            }
            if (this.running) {
                this.pdfView.post(new Runnable() {
                    public void run() {
                        RenderingHandler.this.pdfView.onBitmapRendered(proceed);
                    }
                });
            } else {
                proceed.getRenderedBitmap().recycle();
            }
        } catch (PageRenderingException e) {
            this.pdfView.post(new Runnable() {
                public void run() {
                    RenderingHandler.this.pdfView.onPageError(e);
                }
            });
        }
    }

    private PagePart proceed(RenderingTask renderingTask) throws PageRenderingException {
        PdfFile pdfFile = this.pdfView.pdfFile;
        pdfFile.openPage(renderingTask.page);
        int round = Math.round(renderingTask.width);
        int round2 = Math.round(renderingTask.height);
        if (!(round == 0 || round2 == 0 || pdfFile.pageHasError(renderingTask.page))) {
            try {
                Bitmap createBitmap = Bitmap.createBitmap(round, round2, renderingTask.bestQuality ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                calculateBounds(round, round2, renderingTask.bounds);
                pdfFile.renderPageBitmap(createBitmap, renderingTask.page, this.roundedRenderBounds, renderingTask.annotationRendering);
                return new PagePart(renderingTask.page, createBitmap, renderingTask.bounds, renderingTask.thumbnail, renderingTask.cacheOrder);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Cannot create bitmap", e);
            }
        }
        return null;
    }

    private void calculateBounds(int i, int i2, RectF rectF) {
        this.renderMatrix.reset();
        float f = (float) i;
        float f2 = (float) i2;
        this.renderMatrix.postTranslate((-rectF.left) * f, (-rectF.top) * f2);
        this.renderMatrix.postScale(1.0f / rectF.width(), 1.0f / rectF.height());
        this.renderBounds.set(0.0f, 0.0f, f, f2);
        this.renderMatrix.mapRect(this.renderBounds);
        this.renderBounds.round(this.roundedRenderBounds);
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        this.running = false;
    }

    /* access modifiers changed from: package-private */
    public void start() {
        this.running = true;
    }

    private class RenderingTask {
        boolean annotationRendering;
        boolean bestQuality;
        RectF bounds;
        int cacheOrder;
        float height;
        int page;
        boolean thumbnail;
        float width;

        RenderingTask(float f, float f2, RectF rectF, int i, boolean z, int i2, boolean z2, boolean z3) {
            this.page = i;
            this.width = f;
            this.height = f2;
            this.bounds = rectF;
            this.thumbnail = z;
            this.cacheOrder = i2;
            this.bestQuality = z2;
            this.annotationRendering = z3;
        }
    }
}
