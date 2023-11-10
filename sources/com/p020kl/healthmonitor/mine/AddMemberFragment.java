package com.p020kl.healthmonitor.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.MemberEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import java.util.List;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.mine.AddMemberFragment */
public class AddMemberFragment extends BaseFragmentWhiteToolbar {
    @BindView(3231)
    EditText edName;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public void onBindView(Bundle bundle, View view) {
    }

    public static AddMemberFragment newInstance() {
        Bundle bundle = new Bundle();
        AddMemberFragment addMemberFragment = new AddMemberFragment();
        addMemberFragment.setArguments(bundle);
        return addMemberFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_add_member);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return StringUtils.getString(C1624R.string.member_info);
    }

    @OnClick({3063})
    public void onClick(View view) {
        if (view.getId() == C1624R.C1628id.bt_add) {
            String trim = this.edName.getText().toString().trim();
            if (trim.equals("")) {
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.enter_member_name));
            } else {
                addNetMember(trim);
            }
        }
    }

    private void addNetMember(String str) {
        showProgressDialog(StringUtils.getString(C1624R.string.adding_members), false);
        RestClient.addMember(str).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseResult<List<MemberEntity>>>() {
            public void accept(ResponseResult<List<MemberEntity>> responseResult) throws Exception {
                if (responseResult.getStatus() == 200) {
                    EventBusUtil.sendEvent(new Event(Constants.Add_MEMBER));
                    AddMemberFragment.this.disProgressDialog();
                    AddMemberFragment.this.pop();
                    return;
                }
                AddMemberFragment.this.disProgressDialog();
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.fail_add));
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                AddMemberFragment.this.disProgressDialog();
                ToastUtil.showLongToast(StringUtils.getString(C1624R.string.fail_add));
            }
        });
    }
}
