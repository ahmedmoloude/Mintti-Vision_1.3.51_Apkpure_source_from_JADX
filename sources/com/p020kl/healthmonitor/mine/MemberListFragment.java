package com.p020kl.healthmonitor.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.MemberEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BGTableManager;
import com.p020kl.commonbase.data.p021db.manager.BPTableManager;
import com.p020kl.commonbase.data.p021db.manager.BTTableManager;
import com.p020kl.commonbase.data.p021db.manager.ECGTableManager;
import com.p020kl.commonbase.data.p021db.manager.Spo2hTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UserDesc;
import com.p020kl.commonbase.net.utils.NetworkUtils;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.views.BaseBottomItemDialog;
import com.p020kl.commonbase.views.CommonSelectDialog;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.adapter.MemberAdapter;
import com.p020kl.healthmonitor.views.NormalDataView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableEmitter;
import p028io.reactivex.ObservableOnSubscribe;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.mine.MemberListFragment */
public class MemberListFragment extends BaseFragmentWhiteToolbar implements MemberAdapter.OnMemberItemClickListener, BaseBottomItemDialog.OnOptionClick {
    @BindView(3064)
    Button addMember;
    private BaseBottomItemDialog bottomItemDialogSet;
    /* access modifiers changed from: private */
    public int curPosition;
    /* access modifiers changed from: private */
    public Disposable disposable;
    /* access modifiers changed from: private */
    public String mainUserFace;
    /* access modifiers changed from: private */
    public String mainUserId;
    /* access modifiers changed from: private */
    public String mainUserName;
    /* access modifiers changed from: private */
    public MemberAdapter memberAdapter;
    /* access modifiers changed from: private */
    public volatile String memberId;
    private String[] memberInfo;
    /* access modifiers changed from: private */
    public List<MemberEntity> memberList = new ArrayList();
    @BindView(3502)
    NormalDataView normalDataView;
    @BindView(3581)
    RecyclerView rvMember;

    /* access modifiers changed from: protected */
    public boolean isEventBusRegister() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public static MemberListFragment newInstance() {
        Bundle bundle = new Bundle();
        MemberListFragment memberListFragment = new MemberListFragment();
        memberListFragment.setArguments(bundle);
        return memberListFragment;
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return StringUtils.getString(C1624R.string.member);
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_member_list);
    }

    public void onBindView(Bundle bundle, View view) {
        Log.d("onBindView", "重新执行");
        MemberAdapter memberAdapter2 = new MemberAdapter(C1624R.layout.member_item, this.memberList);
        this.memberAdapter = memberAdapter2;
        memberAdapter2.setOnMemberItemClickListener(this);
        this.rvMember.setAdapter(this.memberAdapter);
        this.rvMember.setLayoutManager(new LinearLayoutManager(getContext()));
        BaseBottomItemDialog baseBottomItemDialog = new BaseBottomItemDialog(getContext(), Arrays.asList(getResources().getStringArray(C1624R.array.member_set)));
        this.bottomItemDialogSet = baseBottomItemDialog;
        baseBottomItemDialog.setOnOptionClick(this);
        initData();
    }

    private void initData() {
        showProgressDialog(StringUtils.getString(C1624R.string.loading_member), false);
        this.mainUserId = BaseApplication.getInstance().getUserId();
        String[] memberInfo2 = SpManager.getMemberInfo();
        this.memberInfo = memberInfo2;
        this.memberId = memberInfo2[0];
        loadMainUserDesc();
    }

    /* access modifiers changed from: private */
    public void getMemberList() {
        RestClient.getMember().subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<List<MemberEntity>>>() {
            public void accept(ResponseResult<List<MemberEntity>> responseResult) throws Exception {
                if (responseResult.getStatus() != 200) {
                    MemberListFragment.this.normalDataView.setVisibility(0);
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_member_list));
                    MemberListFragment.this.disProgressDialog();
                    return;
                }
                MemberListFragment.this.normalDataView.setVisibility(4);
                MemberListFragment.this.memberList.addAll(responseResult.getData());
                int i = 0;
                while (true) {
                    if (i >= MemberListFragment.this.memberList.size()) {
                        break;
                    } else if (MemberListFragment.this.memberId.equals("")) {
                        MemberListFragment.this.memberAdapter.isGreenText(0);
                        break;
                    } else if (Objects.equals(((MemberEntity) MemberListFragment.this.memberList.get(i)).getId(), MemberListFragment.this.memberId)) {
                        MemberListFragment.this.memberAdapter.isGreenText(i);
                        break;
                    } else {
                        i++;
                    }
                }
                MemberListFragment.this.memberAdapter.notifyDataSetChanged();
                MemberListFragment.this.disProgressDialog();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                MemberListFragment.this.disProgressDialog();
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_member_list));
            }
        });
    }

    private void loadMainUserDesc() {
        RestClient.getUserDesc().subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<UserDesc>>() {
            public void accept(ResponseResult<UserDesc> responseResult) throws Exception {
                if (responseResult.getStatus() == 200) {
                    UserDesc data = responseResult.getData();
                    MemberEntity memberEntity = new MemberEntity();
                    String unused = MemberListFragment.this.mainUserFace = data.getFace();
                    String unused2 = MemberListFragment.this.mainUserName = data.getNickname();
                    memberEntity.setId(MemberListFragment.this.mainUserId);
                    memberEntity.setUid(MemberListFragment.this.mainUserId);
                    memberEntity.setFaceUrl(MemberListFragment.this.mainUserFace);
                    memberEntity.setName(MemberListFragment.this.mainUserName);
                    MemberListFragment.this.memberList.add(memberEntity);
                    MemberListFragment.this.memberAdapter.notifyDataSetChanged();
                    MemberListFragment.this.getMemberList();
                } else if (responseResult.getStatus() == 202) {
                    MemberListFragment.this.disProgressDialog();
                } else {
                    Log.d("net_err", "网络错误");
                    ToastUtil.showLongToast(StringUtils.getString(C1624R.string.net_err));
                    MemberListFragment.this.disProgressDialog();
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                Log.d("net_err", "网络错误" + th.getMessage());
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.net_err));
                MemberListFragment.this.disProgressDialog();
            }
        });
    }

    @OnClick({3064})
    public void onClick(View view) {
        if (view.getId() == C1624R.C1628id.bt_add_member) {
            if (!NetworkUtils.isNetworkConnected()) {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.no_netword));
            } else if (this.mainUserId.equals(this.memberId) || this.memberId.equals("")) {
                start(AddMemberFragment.newInstance());
            } else {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.only_primary_add));
            }
        }
    }

    public void memberItemClick(int i) {
        this.curPosition = i;
        this.bottomItemDialogSet.show();
    }

    public void onOptionClick(BaseBottomItemDialog baseBottomItemDialog, String str, int i) {
        if (i == 0) {
            switchMember();
            baseBottomItemDialog.dismiss();
        } else if (i == 1) {
            deleteMember(this.curPosition);
            baseBottomItemDialog.dismiss();
        }
    }

    private void switchMember() {
        showProgressDialog(StringUtils.getString(C1624R.string.switching_users), false);
        MemberEntity memberEntity = this.memberList.get(this.curPosition);
        Log.d("memberID:", memberEntity.getId() + ":" + this.memberList.get(0).getId());
        final String id = memberEntity.getId();
        final String[] strArr = {id, memberEntity.getFaceUrl(), memberEntity.getName()};
        RestClient.switchMember(id).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                if (responseResult.getStatus() == 200) {
                    String unused = MemberListFragment.this.memberId = id;
                    Log.d("member0:", MemberListFragment.this.memberId);
                    MemberListFragment.this.memberAdapter.isGreenText(MemberListFragment.this.curPosition);
                    MemberListFragment.this.memberAdapter.notifyDataSetChanged();
                    SpManager.setMemberInfo(strArr);
                    EventBusUtil.sendEvent(new Event(Constants.SWITCH_MEMBER));
                    Log.d("switch_success", "切换用户成功");
                    MemberListFragment.this.disProgressDialog();
                    return;
                }
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_switch_users));
                MemberListFragment.this.disProgressDialog();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_switch_users));
                MemberListFragment.this.disProgressDialog();
            }
        });
    }

    private void deleteMember(final int i) {
        String str = this.memberId;
        Log.d("memberID", str + "主用户id:" + this.mainUserId);
        if (this.mainUserId.equals(str) || str.equals("")) {
            final String id = this.memberList.get(i).getId();
            if (id.equals(this.mainUserId)) {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.primary_cannot_deleted));
            } else {
                new CommonSelectDialog.Builder(getActivity()).setWidth((int) getResources().getDimension(C1624R.dimen.dp_251)).setHeight((int) getResources().getDimension(C1624R.dimen.dp_101)).setTitle(StringUtils.getString(C1624R.string.deleted_member)).setOnClickListener(new CommonSelectDialog.OnClickListener() {
                    public void onClick(CommonSelectDialog commonSelectDialog, boolean z) {
                        if (z) {
                            MemberListFragment.this.confirmDeleteMember(i, id);
                        }
                        commonSelectDialog.dismiss();
                    }
                }).build().show();
            }
        } else {
            ToastUtil.showLongToast(StringUtils.getString(C1624R.string.only_primary_delete));
        }
    }

    /* access modifiers changed from: private */
    public void confirmDeleteMember(final int i, final String str) {
        showProgressDialog(StringUtils.getString(C1624R.string.deleting_members), false);
        RestClient.deleteMember(str).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<Object>>() {
            public void accept(ResponseResult<Object> responseResult) throws Exception {
                if (responseResult.getStatus() == 200) {
                    MemberListFragment.this.memberList.remove(i);
                    MemberListFragment.this.memberAdapter.notifyDataSetChanged();
                    MemberListFragment.this.deleteMemberNativeData(str);
                    return;
                }
                Log.d("delete_fail", "删除用户失败");
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.user_deletion_failed));
                MemberListFragment.this.disProgressDialog();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                Log.d("delete_fail", "删除用户失败" + th.getMessage());
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.user_deletion_failed));
                MemberListFragment.this.disProgressDialog();
            }
        });
    }

    /* access modifiers changed from: private */
    public void deleteMemberNativeData(final String str) {
        Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                BTTableManager.deleteAllByMemberId(str);
                observableEmitter.onNext(Constants.f839BT);
                BPTableManager.deleteAllByMemberId(str);
                observableEmitter.onNext(Constants.f838BP);
                Spo2hTableManager.deleteAllByMemberId(str);
                observableEmitter.onNext("Spo2");
                BGTableManager.deleteAllByMemberId(str);
                observableEmitter.onNext(Constants.f837BG);
                ECGTableManager.deleteAllByMemberId(str);
                observableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            public void onSubscribe(Disposable disposable) {
                Disposable unused = MemberListFragment.this.disposable = disposable;
            }

            public void onNext(String str) {
                Log.d("deleteSuccess", "删除数据成功" + str);
            }

            public void onError(Throwable th) {
                Log.d("deleteSuccess", "删除数据失败" + th.getMessage());
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.failed_delete_data));
                MemberListFragment.this.disProgressDialog();
            }

            public void onComplete() {
                Log.d("deleteSuccess", "删除数据成功");
                MemberListFragment.this.disProgressDialog();
            }
        });
    }

    public void onEventBus(Event event) {
        if (event != null && event.getData() != null && event.getData().equals(Constants.Add_MEMBER)) {
            this.memberList.clear();
            MemberAdapter memberAdapter2 = this.memberAdapter;
            if (memberAdapter2 != null) {
                memberAdapter2.notifyDataSetChanged();
            }
            initData();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        Disposable disposable2 = this.disposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.disposable.dispose();
        }
    }
}
