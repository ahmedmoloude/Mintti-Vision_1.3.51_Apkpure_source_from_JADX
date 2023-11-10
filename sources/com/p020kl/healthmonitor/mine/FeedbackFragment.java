package com.p020kl.healthmonitor.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.OnClick;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.FeedbackEntity;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.UserInfoTableManager;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.utils.FileUtils;
import com.p020kl.commonbase.utils.LoggerUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.views.BaseBottomItemDialog;
import com.p020kl.healthmonitor.C1624R;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableOnSubscribe;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.schedulers.Schedulers;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/* renamed from: com.kl.healthmonitor.mine.FeedbackFragment */
public class FeedbackFragment extends BaseFragmentWhiteToolbar implements BaseBottomItemDialog.OnOptionClick {
    @BindView(3101)
    Button btnSubmit;
    @BindView(3227)
    EditText etContactWay;
    @BindView(3238)
    EditText etSuggestion;
    /* access modifiers changed from: private */
    public Disposable feedbackDis;
    /* access modifiers changed from: private */
    public File file;
    /* access modifiers changed from: private */
    public View.OnClickListener imgClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
            FeedbackFragment.this.llImgContainer.removeViewAt(((ViewGroup) relativeLayout.getParent()).indexOfChild(relativeLayout));
            int indexOf = FeedbackFragment.this.layoutArrayList.indexOf(relativeLayout);
            File file = (File) FeedbackFragment.this.imgList.get(indexOf);
            if (file != null && file.exists()) {
                file.delete();
            }
            FeedbackFragment.this.imgList.remove(indexOf);
            FeedbackFragment.this.layoutArrayList.remove(relativeLayout);
            if (FeedbackFragment.this.llImgContainer.getChildCount() < FeedbackFragment.this.maxImgLength) {
                FeedbackFragment.this.ivAddImage.setVisibility(0);
            }
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<File> imgList = new ArrayList<>();
    private Uri imgUri;
    @BindView(3331)
    ImageView ivAddImage;
    /* access modifiers changed from: private */
    public ArrayList<RelativeLayout> layoutArrayList = new ArrayList<>();
    @BindView(3388)
    LinearLayout llImgContainer;
    private BaseBottomItemDialog mSelectDialog = null;
    private File mTempFile;
    private ArrayList<File> mTempFileList = new ArrayList<>();
    /* access modifiers changed from: private */
    public int maxImgLength = 3;

    public boolean isShowBack() {
        return true;
    }

    public void onBindView(Bundle bundle, View view) {
    }

    public static FeedbackFragment newInstance() {
        Bundle bundle = new Bundle();
        FeedbackFragment feedbackFragment = new FeedbackFragment();
        feedbackFragment.setArguments(bundle);
        return feedbackFragment;
    }

    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.feedback);
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_problem_feedback);
    }

    @OnClick({3331})
    public void onAddImage() {
        new RxPermissions((Fragment) this).request("android.permission.CAMERA").subscribe(new Consumer<Boolean>() {
            public void accept(Boolean bool) throws Exception {
                if (bool.booleanValue()) {
                    FeedbackFragment.this.showSelectDialog();
                } else {
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.permission_denied));
                }
            }
        });
    }

    public void showSelectDialog() {
        if (this.mSelectDialog == null) {
            BaseBottomItemDialog baseBottomItemDialog = new BaseBottomItemDialog(getContext(), Arrays.asList(getResources().getStringArray(C1624R.array.face_option)));
            this.mSelectDialog = baseBottomItemDialog;
            baseBottomItemDialog.setOnOptionClick(this);
        }
        this.mSelectDialog.show();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 12) {
            if (i2 == -1 && intent != null && intent.getData() != null) {
                Uri data = intent.getData();
                showProgressDialog(StringUtils.getString(C1624R.string.geting_images), false);
                saveInInnerStroe(data);
            }
        } else if (i == 13 && i2 == -1) {
            setImageView(new FileType(1, this.mTempFile.getAbsolutePath()));
        }
    }

    public void setImageView(FileType fileType) {
        String str = "";
        try {
            if (fileType.getFileType() == 0) {
                str = FileUtils.getPath(getContext(), fileType.getUri());
            } else if (fileType.getFileType() == 1) {
                str = fileType.getFilePath();
            }
            Log.d("filePath", str);
            LayoutInflater.from(getContext()).inflate(C1624R.layout.childview_problem_layout, this.llImgContainer);
            Luban.with(getContext()).load(str).ignoreBy(200).setTargetDir(getContext().getExternalCacheDir().getAbsolutePath()).filter(new CompressionPredicate() {
                public boolean apply(String str) {
                    return !TextUtils.isEmpty(str) && !str.toLowerCase().endsWith(".gif");
                }
            }).setCompressListener(new OnCompressListener() {
                public void onStart() {
                }

                public void onSuccess(File file) {
                    FeedbackFragment.this.disProgressDialog();
                    LoggerUtil.m112d(file.getAbsolutePath() + file.length());
                    Bitmap decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath());
                    RelativeLayout relativeLayout = (RelativeLayout) FeedbackFragment.this.llImgContainer.getChildAt(FeedbackFragment.this.llImgContainer.getChildCount() - 1);
                    ((ImageView) relativeLayout.getChildAt(1)).setOnClickListener(FeedbackFragment.this.imgClickListener);
                    ((ImageView) relativeLayout.getChildAt(0)).setImageBitmap(decodeFile);
                    FeedbackFragment.this.layoutArrayList.add(relativeLayout);
                    FeedbackFragment.this.imgList.add(file);
                    if (FeedbackFragment.this.llImgContainer.getChildCount() == FeedbackFragment.this.maxImgLength) {
                        FeedbackFragment.this.ivAddImage.setVisibility(8);
                    }
                }

                public void onError(Throwable th) {
                    LoggerUtil.m112d(th.getMessage());
                }
            }).launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({3101})
    public void onBtnSubmitClicked() {
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        String trim = this.etSuggestion.getText().toString().trim();
        String trim2 = this.etContactWay.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            showHint(Integer.valueOf(C1624R.string.input_problems_suggestions));
            return;
        }
        if (TextUtils.isEmpty(trim2)) {
            trim2 = UserInfoTableManager.queryUserInfo(SpManager.getUserId()).getAccount();
        }
        feedbackEntity.setFeedbackText(trim);
        feedbackEntity.setContactWay(trim2);
        showProgressDialog(StringUtils.getString(C1624R.string.uploading), false);
        uploadFeedback(feedbackEntity);
    }

    private void uploadFeedback(FeedbackEntity feedbackEntity) {
        RestClient.uploadFeedback(feedbackEntity, (List<File>) this.imgList).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseResult<Object>>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = FeedbackFragment.this.feedbackDis = disposable;
            }

            public void onNext(ResponseResult<Object> responseResult) {
                if (responseResult.getStatus() == 200) {
                    ToastUtil.showToast((Context) FeedbackFragment.this.requireActivity(), (int) C1624R.string.feedback_success);
                    FeedbackFragment.this.pop();
                } else {
                    LoggerUtil.m112d(responseResult.getMsg());
                    ToastUtil.showToast((Context) FeedbackFragment.this.requireActivity(), (int) C1624R.string.feedback_success);
                    FeedbackFragment.this.pop();
                }
                FeedbackFragment.this.disProgressDialog();
            }

            public void onError(Throwable th) {
                FeedbackFragment.this.showHint(th.getMessage());
                FeedbackFragment.this.disProgressDialog();
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if (!this.imgList.isEmpty()) {
            Iterator<File> it = this.imgList.iterator();
            while (it.hasNext()) {
                File next = it.next();
                if (next != null && next.exists()) {
                    next.delete();
                }
            }
        }
        if (!this.mTempFileList.isEmpty()) {
            Iterator<File> it2 = this.mTempFileList.iterator();
            while (it2.hasNext()) {
                File next2 = it2.next();
                if (next2 != null && next2.exists()) {
                    next2.delete();
                }
            }
        }
        Disposable disposable = this.feedbackDis;
        if (disposable != null && !disposable.isDisposed()) {
            this.feedbackDis.dispose();
        }
    }

    public void onOptionClick(BaseBottomItemDialog baseBottomItemDialog, String str, int i) {
        if (i == 0) {
            Intent intent = new Intent("android.intent.action.PICK");
            intent.setType("image/*");
            startActivityForResult(intent, 12);
        } else {
            FragmentActivity activity = getActivity();
            File createTempFile = FileUtils.createTempFile(activity, System.currentTimeMillis() + ".jpg");
            this.mTempFile = createTempFile;
            this.mTempFileList.add(createTempFile);
            Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
            if (Build.VERSION.SDK_INT >= 24) {
                intent2.setFlags(2);
                Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.kl.healthmonitor", this.mTempFile);
                this.imgUri = uriForFile;
                intent2.putExtra("output", uriForFile);
            } else {
                Uri fromFile = Uri.fromFile(this.mTempFile);
                this.imgUri = fromFile;
                intent2.putExtra("output", fromFile);
            }
            intent2.addFlags(1);
            startActivityForResult(intent2, 13);
        }
        baseBottomItemDialog.cancel();
    }

    private void saveInInnerStroe(final Uri uri) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            /* JADX WARNING: Removed duplicated region for block: B:31:0x006e A[SYNTHETIC, Splitter:B:31:0x006e] */
            /* JADX WARNING: Removed duplicated region for block: B:36:0x0078 A[SYNTHETIC, Splitter:B:36:0x0078] */
            /* JADX WARNING: Removed duplicated region for block: B:43:0x0085 A[SYNTHETIC, Splitter:B:43:0x0085] */
            /* JADX WARNING: Removed duplicated region for block: B:48:0x008f A[SYNTHETIC, Splitter:B:48:0x008f] */
            /* JADX WARNING: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void subscribe(p028io.reactivex.ObservableEmitter<java.lang.Boolean> r8) throws java.lang.Exception {
                /*
                    r7 = this;
                    r0 = 0
                    r1 = 0
                    com.kl.healthmonitor.mine.FeedbackFragment r2 = com.p020kl.healthmonitor.mine.FeedbackFragment.this     // Catch:{ Exception -> 0x005e, all -> 0x005b }
                    android.content.Context r3 = r2.getContext()     // Catch:{ Exception -> 0x005e, all -> 0x005b }
                    java.lang.String r4 = "feed.png"
                    java.io.File r3 = com.p020kl.commonbase.utils.FileUtils.createCropFaceTempFile(r3, r4)     // Catch:{ Exception -> 0x005e, all -> 0x005b }
                    java.io.File unused = r2.file = r3     // Catch:{ Exception -> 0x005e, all -> 0x005b }
                    com.kl.healthmonitor.mine.FeedbackFragment r2 = com.p020kl.healthmonitor.mine.FeedbackFragment.this     // Catch:{ Exception -> 0x005e, all -> 0x005b }
                    android.content.Context r2 = r2.getContext()     // Catch:{ Exception -> 0x005e, all -> 0x005b }
                    android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ Exception -> 0x005e, all -> 0x005b }
                    android.net.Uri r3 = r3     // Catch:{ Exception -> 0x005e, all -> 0x005b }
                    java.io.InputStream r2 = r2.openInputStream(r3)     // Catch:{ Exception -> 0x005e, all -> 0x005b }
                    java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
                    com.kl.healthmonitor.mine.FeedbackFragment r4 = com.p020kl.healthmonitor.mine.FeedbackFragment.this     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
                    java.io.File r4 = r4.file     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
                    r3.<init>(r4)     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
                    r1 = 1024(0x400, float:1.435E-42)
                    byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0051 }
                L_0x0030:
                    r4 = -1
                    int r5 = r2.read(r1)     // Catch:{ Exception -> 0x0051 }
                    if (r4 == r5) goto L_0x003b
                    r3.write(r1, r0, r5)     // Catch:{ Exception -> 0x0051 }
                    goto L_0x0030
                L_0x003b:
                    r1 = 1
                    java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ Exception -> 0x0051 }
                    r8.onNext(r1)     // Catch:{ Exception -> 0x0051 }
                    if (r2 == 0) goto L_0x004d
                    r2.close()     // Catch:{ IOException -> 0x0049 }
                    goto L_0x004d
                L_0x0049:
                    r8 = move-exception
                    r8.printStackTrace()
                L_0x004d:
                    r3.close()     // Catch:{ IOException -> 0x007c }
                    goto L_0x0080
                L_0x0051:
                    r1 = move-exception
                    goto L_0x0062
                L_0x0053:
                    r8 = move-exception
                    r3 = r1
                    goto L_0x0082
                L_0x0056:
                    r3 = move-exception
                    r6 = r3
                    r3 = r1
                    r1 = r6
                    goto L_0x0062
                L_0x005b:
                    r8 = move-exception
                    r3 = r1
                    goto L_0x0083
                L_0x005e:
                    r2 = move-exception
                    r3 = r1
                    r1 = r2
                    r2 = r3
                L_0x0062:
                    java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ all -> 0x0081 }
                    r8.onNext(r0)     // Catch:{ all -> 0x0081 }
                    r1.printStackTrace()     // Catch:{ all -> 0x0081 }
                    if (r2 == 0) goto L_0x0076
                    r2.close()     // Catch:{ IOException -> 0x0072 }
                    goto L_0x0076
                L_0x0072:
                    r8 = move-exception
                    r8.printStackTrace()
                L_0x0076:
                    if (r3 == 0) goto L_0x0080
                    r3.close()     // Catch:{ IOException -> 0x007c }
                    goto L_0x0080
                L_0x007c:
                    r8 = move-exception
                    r8.printStackTrace()
                L_0x0080:
                    return
                L_0x0081:
                    r8 = move-exception
                L_0x0082:
                    r1 = r2
                L_0x0083:
                    if (r1 == 0) goto L_0x008d
                    r1.close()     // Catch:{ IOException -> 0x0089 }
                    goto L_0x008d
                L_0x0089:
                    r0 = move-exception
                    r0.printStackTrace()
                L_0x008d:
                    if (r3 == 0) goto L_0x0097
                    r3.close()     // Catch:{ IOException -> 0x0093 }
                    goto L_0x0097
                L_0x0093:
                    r0 = move-exception
                    r0.printStackTrace()
                L_0x0097:
                    throw r8
                */
                throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.mine.FeedbackFragment.C17978.subscribe(io.reactivex.ObservableEmitter):void");
            }
        }).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            public void accept(Boolean bool) throws Exception {
                if (bool.booleanValue()) {
                    FeedbackFragment feedbackFragment = FeedbackFragment.this;
                    feedbackFragment.setImageView(new FileType(1, feedbackFragment.file.getAbsolutePath()));
                    return;
                }
                FeedbackFragment.this.disProgressDialog();
                Log.d("wenjian", "文件压缩失败" + FeedbackFragment.this.file.length());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                FeedbackFragment.this.disProgressDialog();
                Log.d("wenjian", "文件以及写入失败" + th.getMessage());
            }
        });
    }

    /* renamed from: com.kl.healthmonitor.mine.FeedbackFragment$FileType */
    private class FileType {
        private String filePath;
        private int fileType;
        private Uri uri;

        private FileType(int i, Uri uri2) {
            this.uri = uri2;
            this.fileType = i;
        }

        private FileType(int i, String str) {
            this.filePath = str;
            this.fileType = i;
        }

        public void setUri(Uri uri2) {
            this.uri = uri2;
        }

        public void setFilePath(String str) {
            this.filePath = str;
        }

        public void setFileType(int i) {
            this.fileType = i;
        }

        /* access modifiers changed from: private */
        public Uri getUri() {
            return this.uri;
        }

        /* access modifiers changed from: private */
        public String getFilePath() {
            return this.filePath;
        }

        /* access modifiers changed from: private */
        public int getFileType() {
            return this.fileType;
        }
    }
}
