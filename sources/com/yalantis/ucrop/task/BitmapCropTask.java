package com.yalantis.ucrop.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.CropParameters;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.model.ImageState;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import com.yalantis.ucrop.util.FileUtils;
import com.yalantis.ucrop.util.ImageHeaderParser;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class BitmapCropTask extends AsyncTask<Void, Void, Throwable> {
    private static final String TAG = "BitmapCropTask";
    private int cropOffsetX;
    private int cropOffsetY;
    private final Bitmap.CompressFormat mCompressFormat;
    private final int mCompressQuality;
    private final WeakReference<Context> mContext;
    private final BitmapCropCallback mCropCallback;
    private final RectF mCropRect;
    private int mCroppedImageHeight;
    private int mCroppedImageWidth;
    private float mCurrentAngle;
    private final RectF mCurrentImageRect;
    private float mCurrentScale;
    private final ExifInfo mExifInfo;
    private final String mImageInputPath;
    private final String mImageOutputPath;
    private final int mMaxResultImageSizeX;
    private final int mMaxResultImageSizeY;
    private Bitmap mViewBitmap;

    public BitmapCropTask(Context context, Bitmap bitmap, ImageState imageState, CropParameters cropParameters, BitmapCropCallback bitmapCropCallback) {
        this.mContext = new WeakReference<>(context);
        this.mViewBitmap = bitmap;
        this.mCropRect = imageState.getCropRect();
        this.mCurrentImageRect = imageState.getCurrentImageRect();
        this.mCurrentScale = imageState.getCurrentScale();
        this.mCurrentAngle = imageState.getCurrentAngle();
        this.mMaxResultImageSizeX = cropParameters.getMaxResultImageSizeX();
        this.mMaxResultImageSizeY = cropParameters.getMaxResultImageSizeY();
        this.mCompressFormat = cropParameters.getCompressFormat();
        this.mCompressQuality = cropParameters.getCompressQuality();
        this.mImageInputPath = cropParameters.getImageInputPath();
        this.mImageOutputPath = cropParameters.getImageOutputPath();
        this.mExifInfo = cropParameters.getExifInfo();
        this.mCropCallback = bitmapCropCallback;
    }

    /* access modifiers changed from: protected */
    public Throwable doInBackground(Void... voidArr) {
        Bitmap bitmap = this.mViewBitmap;
        if (bitmap == null) {
            return new NullPointerException("ViewBitmap is null");
        }
        if (bitmap.isRecycled()) {
            return new NullPointerException("ViewBitmap is recycled");
        }
        if (this.mCurrentImageRect.isEmpty()) {
            return new NullPointerException("CurrentImageRect is empty");
        }
        try {
            crop();
            this.mViewBitmap = null;
            return null;
        } catch (Throwable th) {
            return th;
        }
    }

    private boolean crop() throws IOException {
        if (this.mMaxResultImageSizeX > 0 && this.mMaxResultImageSizeY > 0) {
            float width = this.mCropRect.width() / this.mCurrentScale;
            float height = this.mCropRect.height() / this.mCurrentScale;
            int i = this.mMaxResultImageSizeX;
            if (width > ((float) i) || height > ((float) this.mMaxResultImageSizeY)) {
                float min = Math.min(((float) i) / width, ((float) this.mMaxResultImageSizeY) / height);
                Bitmap bitmap = this.mViewBitmap;
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) bitmap.getWidth()) * min), Math.round(((float) this.mViewBitmap.getHeight()) * min), false);
                Bitmap bitmap2 = this.mViewBitmap;
                if (bitmap2 != createScaledBitmap) {
                    bitmap2.recycle();
                }
                this.mViewBitmap = createScaledBitmap;
                this.mCurrentScale /= min;
            }
        }
        if (this.mCurrentAngle != 0.0f) {
            Matrix matrix = new Matrix();
            matrix.setRotate(this.mCurrentAngle, (float) (this.mViewBitmap.getWidth() / 2), (float) (this.mViewBitmap.getHeight() / 2));
            Bitmap bitmap3 = this.mViewBitmap;
            Bitmap createBitmap = Bitmap.createBitmap(bitmap3, 0, 0, bitmap3.getWidth(), this.mViewBitmap.getHeight(), matrix, true);
            Bitmap bitmap4 = this.mViewBitmap;
            if (bitmap4 != createBitmap) {
                bitmap4.recycle();
            }
            this.mViewBitmap = createBitmap;
        }
        this.cropOffsetX = Math.round((this.mCropRect.left - this.mCurrentImageRect.left) / this.mCurrentScale);
        this.cropOffsetY = Math.round((this.mCropRect.top - this.mCurrentImageRect.top) / this.mCurrentScale);
        this.mCroppedImageWidth = Math.round(this.mCropRect.width() / this.mCurrentScale);
        int round = Math.round(this.mCropRect.height() / this.mCurrentScale);
        this.mCroppedImageHeight = round;
        boolean shouldCrop = shouldCrop(this.mCroppedImageWidth, round);
        Log.i(TAG, "Should crop: " + shouldCrop);
        if (shouldCrop) {
            ExifInterface exifInterface = new ExifInterface(this.mImageInputPath);
            saveImage(Bitmap.createBitmap(this.mViewBitmap, this.cropOffsetX, this.cropOffsetY, this.mCroppedImageWidth, this.mCroppedImageHeight));
            if (!this.mCompressFormat.equals(Bitmap.CompressFormat.JPEG)) {
                return true;
            }
            ImageHeaderParser.copyExif(exifInterface, this.mCroppedImageWidth, this.mCroppedImageHeight, this.mImageOutputPath);
            return true;
        }
        FileUtils.copyFile(this.mImageInputPath, this.mImageOutputPath);
        return false;
    }

    private void saveImage(Bitmap bitmap) throws FileNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream;
        if (((Context) this.mContext.get()) != null) {
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(this.mImageOutputPath), false);
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        bitmap.compress(this.mCompressFormat, this.mCompressQuality, byteArrayOutputStream);
                        fileOutputStream2.write(byteArrayOutputStream.toByteArray());
                        bitmap.recycle();
                        BitmapLoadUtils.close(fileOutputStream2);
                    } catch (IOException e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        try {
                            Log.e(TAG, e.getLocalizedMessage());
                            BitmapLoadUtils.close(fileOutputStream);
                            BitmapLoadUtils.close(byteArrayOutputStream);
                        } catch (Throwable th) {
                            th = th;
                            BitmapLoadUtils.close(fileOutputStream);
                            BitmapLoadUtils.close(byteArrayOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        BitmapLoadUtils.close(fileOutputStream);
                        BitmapLoadUtils.close(byteArrayOutputStream);
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    byteArrayOutputStream = null;
                    fileOutputStream = fileOutputStream2;
                    Log.e(TAG, e.getLocalizedMessage());
                    BitmapLoadUtils.close(fileOutputStream);
                    BitmapLoadUtils.close(byteArrayOutputStream);
                } catch (Throwable th3) {
                    th = th3;
                    byteArrayOutputStream = null;
                    fileOutputStream = fileOutputStream2;
                    BitmapLoadUtils.close(fileOutputStream);
                    BitmapLoadUtils.close(byteArrayOutputStream);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                byteArrayOutputStream = null;
                Log.e(TAG, e.getLocalizedMessage());
                BitmapLoadUtils.close(fileOutputStream);
                BitmapLoadUtils.close(byteArrayOutputStream);
            } catch (Throwable th4) {
                th = th4;
                byteArrayOutputStream = null;
                BitmapLoadUtils.close(fileOutputStream);
                BitmapLoadUtils.close(byteArrayOutputStream);
                throw th;
            }
            BitmapLoadUtils.close(byteArrayOutputStream);
        }
    }

    private boolean shouldCrop(int i, int i2) {
        int round = Math.round(((float) Math.max(i, i2)) / 1000.0f) + 1;
        if (this.mMaxResultImageSizeX > 0 && this.mMaxResultImageSizeY > 0) {
            return true;
        }
        float f = (float) round;
        if (Math.abs(this.mCropRect.left - this.mCurrentImageRect.left) > f || Math.abs(this.mCropRect.top - this.mCurrentImageRect.top) > f || Math.abs(this.mCropRect.bottom - this.mCurrentImageRect.bottom) > f || Math.abs(this.mCropRect.right - this.mCurrentImageRect.right) > f || this.mCurrentAngle != 0.0f) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Throwable th) {
        BitmapCropCallback bitmapCropCallback = this.mCropCallback;
        if (bitmapCropCallback == null) {
            return;
        }
        if (th == null) {
            this.mCropCallback.onBitmapCropped(Uri.fromFile(new File(this.mImageOutputPath)), this.cropOffsetX, this.cropOffsetY, this.mCroppedImageWidth, this.mCroppedImageHeight);
            return;
        }
        bitmapCropCallback.onCropFailure(th);
    }
}
