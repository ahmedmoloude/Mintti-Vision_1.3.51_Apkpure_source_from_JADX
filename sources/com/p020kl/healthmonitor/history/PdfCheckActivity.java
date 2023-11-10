package com.p020kl.healthmonitor.history;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.gyf.immersionbar.ImmersionBar;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import java.io.File;

/* renamed from: com.kl.healthmonitor.history.PdfCheckActivity */
public class PdfCheckActivity extends AppCompatActivity {
    private File file;
    private PDFView pdfView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1624R.layout.activity_pdf_check);
        initView();
        findViewById(C1624R.C1628id.ll_pdf_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PdfCheckActivity.this.finish();
            }
        });
        findViewById(C1624R.C1628id.ll_pdf_share).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PdfCheckActivity.this.sharePdf();
            }
        });
    }

    /* access modifiers changed from: private */
    public void sharePdf() {
        File file2 = this.file;
        if (file2 == null || !file2.exists()) {
            Toast.makeText(this, StringUtils.getString(C1624R.string.shared_not_exist), 0).show();
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        if (Build.VERSION.SDK_INT >= 24) {
            intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, "com.kl.healthmonitor", this.file));
            intent.addFlags(1);
        } else {
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(this.file));
        }
        intent.setType("application/pdf");
        intent.setFlags(268435456);
        intent.addFlags(1);
        startActivity(Intent.createChooser(intent, StringUtils.getString(C1624R.string.share_files)));
    }

    private void initView() {
        String stringExtra = getIntent().getStringExtra(DublinCoreProperties.TYPE);
        this.pdfView = (PDFView) findViewById(C1624R.C1628id.pdfView2);
        ImmersionBar.with((Activity) this).fitsSystemWindows(true).statusBarColor((int) C1624R.C1626color.black).init();
        File file2 = new File(Constants.PDF_ROTE_PATH + "/" + stringExtra + StringUtils.getString(C1624R.string.information_report) + ".pdf");
        this.file = file2;
        this.pdfView.fromFile(file2).defaultPage(0).enableSwipe(true).enableAnnotationRendering(true).onError($$Lambda$PdfCheckActivity$qdCdoDpjk4tbxZCMu6Csjm1vYU.INSTANCE).scrollHandle(new DefaultScrollHandle(this)).enableAntialiasing(true).load();
    }

    static /* synthetic */ void lambda$initView$0(Throwable th) {
        ToastUtil.showLongToast("解析错误" + th.getMessage());
        Log.d("PdfCheckActivity", "解析错误" + th.getMessage());
    }
}
