package com.p020kl.healthmonitor.navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import com.gyf.immersionbar.ImmersionBar;
import com.itextpdf.text.pdf.PdfBoolean;
import com.mintti.visionsdk.ble.BleManager;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.base.BaseFragment;
import com.p020kl.commonbase.bean.UserInfoEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.constants.NetType;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.UserInfoTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.event.NetworkChangeEvent;
import com.p020kl.commonbase.event.NicknameChangeEvent;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UserDesc;
import com.p020kl.commonbase.net.interceptor.ProgressResponseListener;
import com.p020kl.commonbase.utils.FileUtils;
import com.p020kl.commonbase.utils.PhotoUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.views.BaseBottomItemDialog;
import com.p020kl.commonbase.views.CommonSelectDialog;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.mine.AboutFragment;
import com.p020kl.healthmonitor.mine.FAQFragment;
import com.p020kl.healthmonitor.mine.FeedbackFragment;
import com.p020kl.healthmonitor.mine.LookGuideFragment;
import com.p020kl.healthmonitor.mine.MemberListFragment;
import com.p020kl.healthmonitor.mine.MyDeviceFragment;
import com.p020kl.healthmonitor.mine.SettingFragment;
import com.p020kl.healthmonitor.mine.UserNameFragment;
import com.p020kl.healthmonitor.sign.SignActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p026de.hdodenhof.circleimageview.CircleImageView;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.navigation.MineFragment */
public class MineFragment extends BaseFragment implements BaseBottomItemDialog.OnOptionClick {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public BaseBottomItemDialog faceBottomItemDialog;
    private Bitmap faceImg;
    @BindView(3134)
    CircleImageView mImgFace;
    private File mTempFaceFile;
    /* access modifiers changed from: private */
    public String mainUserId;
    private String memberFace;
    private String memberId;
    /* access modifiers changed from: private */
    public String[] memberInfo;
    @BindView(3787)
    TextView tvUserName;
    private Runnable uploadFace = new Runnable() {
        public void run() {
            File cropFaceFile = FileUtils.getCropFaceFile(MineFragment.this.getContext());
            if (cropFaceFile.exists()) {
                RestClient.uploadFace(cropFaceFile, (ProgressResponseListener) null).subscribe(new Consumer<ResponseResult<String>>() {
                    public void accept(ResponseResult<String> responseResult) throws Exception {
                        if (responseResult.getStatus() != 200) {
                            Log.d("uploadimg", "上传图片" + responseResult.getStatus());
                            ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_upload_avatar));
                            return;
                        }
                        String data = responseResult.getData();
                        Log.d("faceUrl", data);
                        if (MineFragment.this.isMember()) {
                            MineFragment.this.uploadMemberFace(data);
                        } else if (MineFragment.this.userInfoEntity != null) {
                            MineFragment.this.userInfoEntity.setFaceUrl(data);
                            MineFragment.this.userInfoEntity.setModifyTime(System.currentTimeMillis());
                            UserInfoTableManager.updateUserInfo(MineFragment.this.userInfoEntity);
                            MineFragment.this.updateUserInfo(MineFragment.this.userInfoEntity);
                        }
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable th) throws Exception {
                        Log.d("uploadimg", "上传失败");
                        ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_upload_avatar));
                    }
                });
            }
        }
    };
    private File uriFile;
    /* access modifiers changed from: private */
    public UserInfoEntity userInfoEntity;

    /* access modifiers changed from: protected */
    public boolean isEventBusRegister() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isShowRightImg() {
        return true;
    }

    /* access modifiers changed from: protected */
    public int setRightImage() {
        return C1624R.C1627drawable.log_out;
    }

    /* access modifiers changed from: protected */
    public int setTitleBar() {
        return C1624R.C1628id.toolbar;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_mine);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return StringUtils.getString(C1624R.string.mine);
    }

    public void onBindView(Bundle bundle, View view) {
        BaseBottomItemDialog baseBottomItemDialog = new BaseBottomItemDialog(getContext(), Arrays.asList(getResources().getStringArray(C1624R.array.face_option)));
        this.faceBottomItemDialog = baseBottomItemDialog;
        baseBottomItemDialog.setOnOptionClick(this);
        this.userInfoEntity = UserInfoTableManager.queryUserInfo(BaseApplication.getInstance().getUserId());
        loadUserInfo();
        Log.d("userId", SpManager.getUserId());
        Log.d("token2", SpManager.getMemberId());
    }

    private void loadUserInfo() {
        this.mainUserId = SpManager.getUserId();
        this.memberInfo = SpManager.getMemberInfo();
        if (!isMember() || this.memberInfo[0].equals("")) {
            loadMainUserDesc();
        } else {
            refreshMemberInfo();
        }
    }

    private void loadMainUserDesc() {
        RestClient.getUserDesc().subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<UserDesc>>() {
            public void accept(ResponseResult<UserDesc> responseResult) throws Exception {
                if (responseResult.getStatus() == 200) {
                    UserDesc data = responseResult.getData();
                    if (MineFragment.this.userInfoEntity != null) {
                        MineFragment.this.userInfoEntity.setFaceUrl(data.getFace());
                        MineFragment.this.userInfoEntity.setAge(data.getAge());
                        MineFragment.this.userInfoEntity.setBirthday(data.getBirthday());
                        MineFragment.this.userInfoEntity.setGender(data.getGender());
                        MineFragment.this.userInfoEntity.setWeight(data.getWeight());
                        MineFragment.this.userInfoEntity.setHeight(data.getHeight());
                        MineFragment.this.userInfoEntity.setNickName(data.getNickname());
                        MineFragment.this.userInfoEntity.setModifyTime(data.getModifyTime());
                        MineFragment.this.refreshUserInfo();
                        SpManager.setMemberInfo(new String[]{MineFragment.this.mainUserId, data.getFace(), data.getNickname()});
                    }
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                MineFragment.this.refreshUserInfo();
            }
        });
    }

    /* access modifiers changed from: private */
    public void refreshUserInfo() {
        if (this.userInfoEntity != null) {
            PhotoUtils.loadImage(getContext(), this.userInfoEntity.getFaceUrl(), (ImageView) this.mImgFace, (int) C1624R.C1627drawable.face);
            if (TextUtils.isEmpty(this.userInfoEntity.getNickName())) {
                this.tvUserName.setText(C1624R.string.nickname);
            } else {
                this.tvUserName.setText(this.userInfoEntity.getNickName());
            }
        }
    }

    /* access modifiers changed from: private */
    public void refreshMemberInfo() {
        PhotoUtils.loadImage(getContext(), this.memberInfo[1], (ImageView) this.mImgFace, (int) C1624R.C1627drawable.face);
        this.tvUserName.setText(this.memberInfo[2]);
    }

    /* access modifiers changed from: protected */
    public void onRightClicked() {
        onSignOutClicked(getString(C1624R.string.sure_cancel_account));
    }

    @OnClick({3134, 3787, 3840, 3839, 3838, 3841, 2131297159, 2131297157, 3842, 3099})
    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case C1624R.C1628id.btn_sign_out:
                onSignOutClicked(StringUtils.getString(C1624R.string.dialog_confirm_signout));
                return;
            case C1624R.C1628id.civ_my_img:
                new RxPermissions((Fragment) this).request("android.permission.CAMERA").subscribe(new Consumer<Boolean>() {
                    public void accept(Boolean bool) throws Exception {
                        if (!bool.booleanValue()) {
                            ToastUtil.showLongToast(StringUtils.getString(C1624R.string.permission_denied));
                        } else {
                            MineFragment.this.faceBottomItemDialog.show();
                        }
                    }
                });
                return;
            case C1624R.C1628id.tv_my_username:
                if (this.userInfoEntity != null) {
                    ((NavigationFragment) getParentFragment()).start(UserNameFragment.newInstance(this.tvUserName.getText().toString()));
                    return;
                }
                return;
            case C1624R.C1628id.uiv_add_member:
                ((NavigationFragment) getParentFragment()).start(MemberListFragment.newInstance());
                return;
            default:
                switch (id) {
                    case C1624R.C1628id.uiv_guide:
                        ((NavigationFragment) getParentFragment()).start(new LookGuideFragment());
                        return;
                    case C1624R.C1628id.uiv_problem_about:
                        ((NavigationFragment) getParentFragment()).start(AboutFragment.newInstance());
                        return;
                    case C1624R.C1628id.uiv_problem_faq:
                        ((NavigationFragment) getParentFragment()).start(FAQFragment.newInstance());
                        return;
                    case C1624R.C1628id.uiv_problem_feedback:
                        ((NavigationFragment) getParentFragment()).start(FeedbackFragment.newInstance());
                        return;
                    case C1624R.C1628id.uiv_problem_mydevice:
                        ((NavigationFragment) getParentFragment()).start(MyDeviceFragment.newInstance());
                        return;
                    case C1624R.C1628id.uiv_problem_setting:
                        ((NavigationFragment) getParentFragment()).start(SettingFragment.newInstance());
                        return;
                    default:
                        return;
                }
        }
    }

    private void onSignOutClicked(String str) {
        new CommonSelectDialog.Builder(getActivity()).setWidth((int) getResources().getDimension(C1624R.dimen.dp_251)).setHeight((int) getResources().getDimension(C1624R.dimen.dp_101)).setTitle(str).setOnClickListener(new CommonSelectDialog.OnClickListener() {
            public void onClick(CommonSelectDialog commonSelectDialog, boolean z) {
                if (z) {
                    String account = MineFragment.this.userInfoEntity != null ? MineFragment.this.userInfoEntity.getAccount() : "";
                    BleManager.getInstance().disconnect();
                    SpManager.deleteAll();
                    SpManager.setLastAccount(account);
                    BaseApplication.clearToken();
                    BaseApplication.isTokenExpire = true;
                    MineFragment.this.startActivity(new Intent(MineFragment.this.getActivity(), SignActivity.class));
                    MineFragment.this._mActivity.finish();
                }
                commonSelectDialog.dismiss();
            }
        }).build().show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNicknameChange(NicknameChangeEvent nicknameChangeEvent) {
        String str = (String) nicknameChangeEvent.getData();
        if (!TextUtils.isEmpty(str)) {
            this.tvUserName.setText(str);
            if (isMember()) {
                uploadMemberName(str);
                return;
            }
            Log.d("main_name", "上传名称");
            this.userInfoEntity.setNickName(str);
            this.userInfoEntity.setModifyTime(System.currentTimeMillis());
            UserInfoTableManager.updateUserInfo(this.userInfoEntity);
            updateUserInfo(this.userInfoEntity);
        }
    }

    private void uploadMemberName(final String str) {
        RestClient.uploadMemberName(str).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                if (responseResult.getStatus() != 200) {
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_update_name));
                    return;
                }
                SpManager.setMemberName(str);
                MineFragment.this.memberInfo[2] = str;
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_update_name));
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean isMember() {
        String[] strArr = this.memberInfo;
        return strArr != null && !strArr[0].equals("") && !this.mainUserId.equals(this.memberInfo[0]);
    }

    public void onEventBus(Event event) {
        if (event.getData() != null && event.getData().equals(Constants.SWITCH_MEMBER)) {
            loadUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent networkChangeEvent) {
        if (networkChangeEvent.getData() != NetType.NONE) {
            loadMainUserDesc();
        }
    }

    public void initImmersionBar() {
        ImmersionBar.with((Fragment) this).init();
    }

    public void onOptionClick(BaseBottomItemDialog baseBottomItemDialog, String str, int i) {
        String str2 = this.TAG;
        Log.e(str2, "onOptionClick: " + str);
        if (i == 0) {
            Intent intent = new Intent("android.intent.action.PICK", (Uri) null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 12);
        } else {
            createTempFile();
            Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
            if (Build.VERSION.SDK_INT >= 24) {
                intent2.setFlags(2);
                intent2.putExtra("output", FileProvider.getUriForFile(getContext(), "com.kl.healthmonitor", this.mTempFaceFile));
            } else {
                intent2.putExtra("output", Uri.fromFile(this.mTempFaceFile));
            }
            intent2.addFlags(1);
            startActivityForResult(intent2, 13);
        }
        baseBottomItemDialog.cancel();
    }

    private void createTempFile() {
        if (this.mTempFaceFile == null) {
            this.mTempFaceFile = FileUtils.createFaceFile(getContext());
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Bundle extras;
        Log.e("MineFragment", "onActivityResult: " + i + " " + i2);
        if (i == 12) {
            if (i2 != -1) {
                Log.e("MineFragment", "onActivityResult: " + i2);
            } else if (intent != null && intent.getData() != null) {
                cropImg(intent.getData());
            }
        } else if (i == 13) {
            if (i2 != -1) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                cropImg(FileProvider.getUriForFile(getContext(), "com.kl.healthmonitor", this.mTempFaceFile));
            } else {
                cropImg(Uri.fromFile(this.mTempFaceFile));
            }
        } else if (i == 69) {
            if (i2 == -1) {
                setImgFace(UCrop.getOutput(intent));
            } else if (i2 == 96) {
                UCrop.getError(intent);
            }
        } else if (i == 16 && intent != null && (extras = intent.getExtras()) != null) {
            Bitmap bitmap = (Bitmap) extras.getParcelable("data");
            this.faceImg = bitmap;
            this.mImgFace.setImageBitmap(bitmap);
            this.executorService.submit(this.uploadFace);
        }
    }

    private void setImgFace(Uri uri) {
        try {
            Bitmap decodeStream = BitmapFactory.decodeStream(this._mActivity.getContentResolver().openInputStream(uri));
            PhotoUtils.clearImage(getContext(), this.mImgFace);
            this.mImgFace.setImageBitmap(decodeStream);
            this.executorService.submit(this.uploadFace);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void uploadMemberFace(final String str) {
        RestClient.uploadMemberFace(str).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                if (responseResult.getStatus() != 200) {
                    Log.d("upload_member_face_err", "上传头像失败");
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_upload_avatar));
                    return;
                }
                SpManager.setMemberFace(str);
                MineFragment.this.memberInfo[1] = str;
                MineFragment.this.refreshMemberInfo();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_upload_avatar));
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateUserInfo(UserInfoEntity userInfoEntity2) {
        RestClient.updateUserInfo(userInfoEntity2).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                if (responseResult.getStatus() == 200) {
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.update_user_successfully));
                } else {
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_update_user));
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_update_user));
            }
        });
    }

    private void cropImg(Uri uri) {
        this.uriFile = FileUtils.getCropFaceFile(getContext());
        PhotoUtils.cropFace(requireActivity(), this, uri, Uri.fromFile(FileUtils.getCropFaceFile(getContext())));
    }

    private void cropImg2(Uri uri) {
        Log.e("MineFragment", "cropImg2: " + uri);
        this.uriFile = FileUtils.getCropFaceFile(getContext());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(3);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", PdfBoolean.TRUE);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 16);
    }

    public static String bitmap2Path(Bitmap bitmap, String str) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            Log.e("TAG", "", e);
        }
        return str;
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d("mineFragment", "我的界面销毁了");
    }
}
