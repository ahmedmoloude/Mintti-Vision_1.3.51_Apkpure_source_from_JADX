package com.p020kl.commonbase.views.countrypicker;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.p020kl.commonbase.C1544R;

/* renamed from: com.kl.commonbase.views.countrypicker.CountryPickerFragment_ViewBinding */
public class CountryPickerFragment_ViewBinding implements Unbinder {
    private CountryPickerFragment target;

    public CountryPickerFragment_ViewBinding(CountryPickerFragment countryPickerFragment, View view) {
        this.target = countryPickerFragment;
        countryPickerFragment.rvPick = (RecyclerView) Utils.findRequiredViewAsType(view, C1544R.C1548id.rv_pick, "field 'rvPick'", RecyclerView.class);
        countryPickerFragment.side = (SideBar) Utils.findRequiredViewAsType(view, C1544R.C1548id.side, "field 'side'", SideBar.class);
        countryPickerFragment.etSearch = (EditText) Utils.findRequiredViewAsType(view, C1544R.C1548id.et_search, "field 'etSearch'", EditText.class);
        countryPickerFragment.tvLetter = (TextView) Utils.findRequiredViewAsType(view, C1544R.C1548id.tv_letter, "field 'tvLetter'", TextView.class);
    }

    public void unbind() {
        CountryPickerFragment countryPickerFragment = this.target;
        if (countryPickerFragment != null) {
            this.target = null;
            countryPickerFragment.rvPick = null;
            countryPickerFragment.side = null;
            countryPickerFragment.etSearch = null;
            countryPickerFragment.tvLetter = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
