package com.p020kl.healthmonitor.mine;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.event.NicknameChangeEvent;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import java.util.regex.Pattern;

/* renamed from: com.kl.healthmonitor.mine.UserNameFragment */
public class UserNameFragment extends BaseFragmentWhiteToolbar {
    @BindView(2131296397)
    Button btSave;
    @BindView(2131296561)
    EditText etUserName;
    InputFilter inputFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[🀀-🏿]|[🐀-🟿]|[☀-⟿]", 66);

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if (!this.emoji.matcher(charSequence).find()) {
                return null;
            }
            ToastUtil.showToast((Context) UserNameFragment.this.getActivity(), (int) C1624R.string.not_support_emoji);
            return "";
        }
    };
    /* access modifiers changed from: private */
    public String nickName;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public static UserNameFragment newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_USER_INFO, str);
        UserNameFragment userNameFragment = new UserNameFragment();
        userNameFragment.setArguments(bundle);
        return userNameFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_username);
    }

    public void onBindView(Bundle bundle, View view) {
        this.nickName = getArguments().getString(Constants.BUNDLE_USER_INFO);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.change_nickname);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        super.initView(view);
        if (!TextUtils.isEmpty(this.nickName)) {
            this.etUserName.setText(this.nickName);
            this.etUserName.setSelection(this.nickName.length());
        }
        this.etUserName.setFilters(new InputFilter[]{this.inputFilter, new InputFilter.LengthFilter(16)});
        onTextChange();
    }

    private void onTextChange() {
        this.etUserName.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                UserNameFragment.this.btSave.setEnabled(true);
            }

            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable) || editable.toString().equals(UserNameFragment.this.nickName)) {
                    UserNameFragment.this.btSave.setEnabled(false);
                }
            }
        });
    }

    @OnClick({3075})
    public void onSaveClicked() {
        String obj = this.etUserName.getText().toString();
        if (!obj.equals(this.nickName)) {
            EventBusUtil.sendEvent(new NicknameChangeEvent(obj));
            pop();
        }
    }
}
