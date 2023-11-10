package com.p020kl.healthmonitor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.itextpdf.text.html.HtmlTags;
import com.p020kl.commonbase.utils.SizeUtils;
import com.p020kl.healthmonitor.views.PdfChartView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.ViewActivity */
public class ViewActivity extends AppCompatActivity {
    private CardView cardView;
    private PdfChartView chartView;
    private PdfChartView chartView2;
    private HorizontalScrollView scrollView;
    TextView textView;

    /* renamed from: v */
    View f899v;
    private List<String> xValue = new ArrayList();
    private List<Float> yValue = new ArrayList();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1624R.layout.activity_view);
        this.chartView = (PdfChartView) findViewById(C1624R.C1628id.pdf_chart_view);
        this.scrollView = (HorizontalScrollView) findViewById(C1624R.C1628id.scrollview);
        this.cardView = (CardView) findViewById(C1624R.C1628id.cv_card_broke2);
        for (int i = 0; i < 10; i++) {
            this.xValue.add("20:30 08-10");
            this.yValue.add(Float.valueOf(((float) i) * 5.0f));
        }
        View inflate = LayoutInflater.from(this).inflate(C1624R.layout.pdf_view, (ViewGroup) null);
        this.f899v = inflate;
        PdfChartView pdfChartView = (PdfChartView) inflate.findViewById(C1624R.C1628id.pdf_chart_view);
        this.chartView2 = pdfChartView;
        pdfChartView.setValue(this.xValue, this.yValue, -1);
        this.chartView.setValue(this.xValue, this.yValue, -1);
        findViewById(C1624R.C1628id.buttons).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ViewActivity.this.layoutView();
                ViewActivity.this.getAndSaveBitmap();
            }
        });
    }

    /* access modifiers changed from: private */
    public void layoutView() {
        this.f899v.layout(0, 0, SizeUtils.dp2px(670.0f), SizeUtils.dp2px(200.0f));
        this.f899v.measure(View.MeasureSpec.makeMeasureSpec(SizeUtils.dp2px(670.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(SizeUtils.dp2px(200.0f), 1073741824));
        View view = this.f899v;
        view.layout(0, 0, view.getMeasuredWidth(), this.f899v.getMeasuredHeight());
    }

    /* access modifiers changed from: private */
    public void getAndSaveBitmap() {
        Bitmap createBitmap = Bitmap.createBitmap(this.f899v.getWidth(), this.f899v.getHeight(), Bitmap.Config.ARGB_8888);
        this.f899v.draw(new Canvas(createBitmap));
        saveBitmap(createBitmap);
    }

    public Bitmap getViewBp(View view) {
        if (view == null) {
            return null;
        }
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(view.getHeight(), 1073741824));
            view.layout((int) view.getX(), (int) view.getY(), ((int) view.getX()) + view.getMeasuredWidth(), ((int) view.getY()) + view.getMeasuredHeight());
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return createBitmap;
    }

    public Bitmap getBitmapByView(HorizontalScrollView horizontalScrollView) {
        int i = 0;
        for (int i2 = 0; i2 < horizontalScrollView.getChildCount(); i2++) {
            i += horizontalScrollView.getChildAt(i2).getWidth();
            horizontalScrollView.getChildAt(i2).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, horizontalScrollView.getHeight(), Bitmap.Config.RGB_565);
        horizontalScrollView.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public void saveBitmap(Bitmap bitmap) {
        Log.d("hehe", "保存图片");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(getExternalFilesDir(HtmlTags.IMG).getAbsolutePath() + "/img2.png"));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception unused) {
        }
    }

    public Bitmap compressImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int i = 100;
        while (byteArrayOutputStream.toByteArray().length / 1024 > 100) {
            byteArrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
            i -= 10;
        }
        return BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), (Rect) null, (BitmapFactory.Options) null);
    }

    public String savePic(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(HtmlTags.IMG).getAbsolutePath() + "/img2.png");
        if (!file.isDirectory()) {
            try {
                file.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
