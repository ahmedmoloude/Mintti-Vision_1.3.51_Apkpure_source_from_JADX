package com.p020kl.commonbase.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.p020kl.commonbase.C1544R;
import com.yalantis.ucrop.UCrop;

/* renamed from: com.kl.commonbase.utils.PhotoUtils */
public class PhotoUtils {
    public static void loadImage(Context context, String str, ImageView imageView, Drawable drawable) {
        ((RequestBuilder) Glide.with(context).load(str).placeholder(drawable)).into(imageView);
    }

    public static void loadImage(Context context, String str, ImageView imageView, int i) {
        ((RequestBuilder) Glide.with(context).load(str).placeholder(i)).into(imageView);
    }

    public static void loadImage(Context context, Bitmap bitmap, ImageView imageView, int i) {
        ((RequestBuilder) Glide.with(context).load(bitmap).placeholder(i)).into(imageView);
    }

    public static void clearImage(Context context, ImageView imageView) {
        Glide.with(context).clear((View) imageView);
    }

    public static void cropFace(Activity activity, Fragment fragment, Uri uri, Uri uri2) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(activity.getResources().getColor(C1544R.C1546color.colorPrimary));
        options.setStatusBarColor(activity.getResources().getColor(C1544R.C1546color.colorPrimaryDark));
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setFreeStyleCropEnabled(false);
        options.setHideBottomControls(true);
        options.setToolbarWidgetColor(activity.getResources().getColor(C1544R.C1546color.white));
        UCrop.m1067of(uri, uri2).withMaxResultSize(500, 500).withAspectRatio(1.0f, 1.0f).withOptions(options).start(activity, fragment, 69);
    }
}
