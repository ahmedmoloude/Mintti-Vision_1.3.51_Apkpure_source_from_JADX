package com.p020kl.healthmonitor.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import butterknife.BindView;
import butterknife.OnClick;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.event.BgUnitChanged;
import com.p020kl.commonbase.event.TemperatureUnitChanged;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.views.BaseBottomItemDialog;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.UserItemView;
import java.util.Arrays;

/* renamed from: com.kl.healthmonitor.mine.SettingFragment */
public class SettingFragment extends BaseFragmentWhiteToolbar implements BaseBottomItemDialog.OnOptionClick {
    private BaseBottomItemDialog bottomItemDialogBgUnit;
    private BaseBottomItemDialog bottomItemDialogGain;
    private BaseBottomItemDialog bottomItemDialogPaperSpeed;
    private BaseBottomItemDialog bottomItemDialogTemp;
    @BindView(3590)
    Switch sbWarn;
    @BindView(2131297158)
    UserItemView uivBgUnit;
    @BindView(3843)
    UserItemView uivSetGain;
    @BindView(3844)
    UserItemView uivSetTemp;
    @BindView(3845)
    UserItemView uivSetTime;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public static SettingFragment newInstance() {
        Bundle bundle = new Bundle();
        SettingFragment settingFragment = new SettingFragment();
        settingFragment.setArguments(bundle);
        return settingFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_setting);
    }

    public void onBindView(Bundle bundle, View view) {
        String[] stringArray = getResources().getStringArray(C1624R.array.temperature_unit);
        BaseBottomItemDialog baseBottomItemDialog = new BaseBottomItemDialog(getContext(), Arrays.asList(stringArray));
        this.bottomItemDialogTemp = baseBottomItemDialog;
        baseBottomItemDialog.setOnOptionClick(this);
        String[] stringArray2 = getResources().getStringArray(C1624R.array.bg_unit);
        BaseBottomItemDialog baseBottomItemDialog2 = new BaseBottomItemDialog(getContext(), Arrays.asList(stringArray2));
        this.bottomItemDialogBgUnit = baseBottomItemDialog2;
        baseBottomItemDialog2.setOnOptionClick(this);
        String[] stringArray3 = getResources().getStringArray(C1624R.array.ecg_pager_speed);
        BaseBottomItemDialog baseBottomItemDialog3 = new BaseBottomItemDialog(getContext(), Arrays.asList(stringArray3));
        this.bottomItemDialogPaperSpeed = baseBottomItemDialog3;
        baseBottomItemDialog3.setOnOptionClick(this);
        String[] stringArray4 = getResources().getStringArray(C1624R.array.ecg_gain);
        BaseBottomItemDialog baseBottomItemDialog4 = new BaseBottomItemDialog(getContext(), Arrays.asList(stringArray4));
        this.bottomItemDialogGain = baseBottomItemDialog4;
        baseBottomItemDialog4.setOnOptionClick(this);
        if (SpManager.getTemperaTrueUnit() == 0) {
            this.uivSetTemp.setItemUnit(stringArray[0]);
        } else {
            this.uivSetTemp.setItemUnit(stringArray[1]);
        }
        if (SpManager.getBgUnit() == 0) {
            this.uivBgUnit.setItemUnit(stringArray2[0]);
        } else {
            this.uivBgUnit.setItemUnit(stringArray2[1]);
        }
        if (SpManager.getPaperSpeed() == 0) {
            this.uivSetTime.setItemUnit(stringArray3[0]);
        } else {
            this.uivSetTime.setItemUnit(stringArray3[1]);
        }
        float gain = SpManager.getGain();
        if (gain == 0.5f) {
            this.uivSetGain.setItemUnit(stringArray4[0]);
        } else if (gain == 1.0f) {
            this.uivSetGain.setItemUnit(stringArray4[1]);
        } else {
            this.uivSetGain.setItemUnit(stringArray4[2]);
        }
        this.sbWarn.setChecked(SpManager.getIsWarn());
        this.sbWarn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SpManager.setIsWarn(z);
            }
        });
    }

    @OnClick({3844, 3845, 3843, 2131297158})
    public void onClick(View view) {
        int id = view.getId();
        if (id != C1624R.C1628id.uiv_bg_unit) {
            switch (id) {
                case C1624R.C1628id.uiv_set_gain:
                    this.bottomItemDialogGain.show();
                    return;
                case C1624R.C1628id.uiv_set_temperature:
                    this.bottomItemDialogTemp.show();
                    return;
                case C1624R.C1628id.uiv_set_time:
                    this.bottomItemDialogPaperSpeed.show();
                    return;
                default:
                    return;
            }
        } else {
            this.bottomItemDialogBgUnit.show();
        }
    }

    public void onOptionClick(BaseBottomItemDialog baseBottomItemDialog, String str, int i) {
        if (this.bottomItemDialogTemp == baseBottomItemDialog) {
            this.uivSetTemp.setItemUnit(str);
            SpManager.setTemperaTrueUnit(i);
            EventBusUtil.sendEvent(new TemperatureUnitChanged(Integer.valueOf(i)));
        } else if (this.bottomItemDialogPaperSpeed == baseBottomItemDialog) {
            this.uivSetTime.setItemUnit(str);
            SpManager.setPaperSpeed(i);
        } else if (this.bottomItemDialogBgUnit == baseBottomItemDialog) {
            this.uivBgUnit.setItemUnit(str);
            SpManager.setBgUnit(i);
            EventBusUtil.sendEvent(new BgUnitChanged(Integer.valueOf(i)));
        } else {
            this.uivSetGain.setItemUnit(str);
            if (i == 0) {
                SpManager.setGain(0.5f);
            } else if (i == 1) {
                SpManager.setGain(1.0f);
            } else if (i == 2) {
                SpManager.setGain(2.0f);
            }
        }
        baseBottomItemDialog.cancel();
    }
}
