package com.p020kl.commonbase.views.countrypicker;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.gyf.immersionbar.ImmersionBar;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.base.BaseFragment;
import com.p020kl.commonbase.views.countrypicker.CountryPickerFragment;
import com.p020kl.commonbase.views.countrypicker.PyAdapter;
import com.p020kl.commonbase.views.countrypicker.SideBar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.kl.commonbase.views.countrypicker.CountryPickerFragment */
public class CountryPickerFragment extends BaseFragment {
    public static final String KEY_COUNTRY = "country";
    public static final int REQUEST_COUNTRY_CODE = 10;
    /* access modifiers changed from: private */
    public ArrayList<Country> allCountries = new ArrayList<>();
    @BindView(2977)
    EditText etSearch;
    @BindView(3185)
    RecyclerView rvPick;
    /* access modifiers changed from: private */
    public ArrayList<Country> selectedCountries = new ArrayList<>();
    @BindView(3216)
    SideBar side;
    @BindView(3307)
    TextView tvLetter;

    public boolean isShowBack() {
        return true;
    }

    public Object setLayout() {
        return Integer.valueOf(C1544R.layout.common_fragment_country_picker);
    }

    public Object setToolbarTitle() {
        return Integer.valueOf(C1544R.string.common_country_region);
    }

    /* access modifiers changed from: protected */
    public int setTitleBar() {
        return C1544R.C1548id.toolbar;
    }

    public void initImmersionBar() {
        ImmersionBar.with((Fragment) this).init();
    }

    public void onBindView(Bundle bundle, View view) {
        this.allCountries.clear();
        this.allCountries.addAll(Country.getAll(getActivity(), (ExceptionCallback) null));
        this.selectedCountries.clear();
        this.selectedCountries.addAll(this.allCountries);
        final CAdapter cAdapter = new CAdapter(this.selectedCountries);
        this.rvPick.setAdapter(cAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        this.rvPick.setLayoutManager(linearLayoutManager);
        this.rvPick.setAdapter(cAdapter);
        this.rvPick.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        this.etSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                String obj = editable.toString();
                CountryPickerFragment.this.selectedCountries.clear();
                Iterator it = CountryPickerFragment.this.allCountries.iterator();
                while (it.hasNext()) {
                    Country country = (Country) it.next();
                    if (country.name.toLowerCase().contains(obj.toLowerCase())) {
                        CountryPickerFragment.this.selectedCountries.add(country);
                    }
                }
                cAdapter.update(CountryPickerFragment.this.selectedCountries);
            }
        });
        SideBar sideBar = this.side;
        sideBar.addIndex("#", sideBar.indexes.size());
        this.side.setOnLetterChangeListener(new SideBar.OnLetterChangeListener() {
            public void onLetterChange(String str) {
                CountryPickerFragment.this.tvLetter.setVisibility(0);
                CountryPickerFragment.this.tvLetter.setText(str);
                int letterPosition = cAdapter.getLetterPosition(str);
                if (letterPosition != -1) {
                    linearLayoutManager.scrollToPositionWithOffset(letterPosition, 0);
                }
            }

            public void onReset() {
                CountryPickerFragment.this.tvLetter.setVisibility(8);
            }
        });
    }

    /* renamed from: com.kl.commonbase.views.countrypicker.CountryPickerFragment$CAdapter */
    class CAdapter extends PyAdapter<RecyclerView.ViewHolder> {
        public CAdapter(List<? extends PyEntity> list) {
            super(list);
        }

        public RecyclerView.ViewHolder onCreateLetterHolder(ViewGroup viewGroup, int i) {
            return new LetterHolder(CountryPickerFragment.this.getLayoutInflater().inflate(C1544R.layout.item_letter, viewGroup, false));
        }

        public RecyclerView.ViewHolder onCreateHolder(ViewGroup viewGroup, int i) {
            return new C1619VH(CountryPickerFragment.this.getLayoutInflater().inflate(C1544R.layout.item_country_large_padding, viewGroup, false));
        }

        public void onBindHolder(RecyclerView.ViewHolder viewHolder, PyEntity pyEntity, int i) {
            C1619VH vh = (C1619VH) viewHolder;
            Country country = (Country) pyEntity;
            vh.tvName.setText(country.name);
            TextView textView = vh.tvCode;
            textView.setText("+" + country.code);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener(country) {
                public final /* synthetic */ Country f$1;

                {
                    this.f$1 = r2;
                }

                public final void onClick(View view) {
                    CountryPickerFragment.CAdapter.this.lambda$onBindHolder$5$CountryPickerFragment$CAdapter(this.f$1, view);
                }
            });
        }

        public /* synthetic */ void lambda$onBindHolder$5$CountryPickerFragment$CAdapter(Country country, View view) {
            Bundle bundle = new Bundle();
            bundle.putString(CountryPickerFragment.KEY_COUNTRY, country.toJson());
            CountryPickerFragment.this.setFragmentResult(-1, bundle);
            CountryPickerFragment.this.pop();
        }

        public void onBindLetterHolder(RecyclerView.ViewHolder viewHolder, PyAdapter.LetterEntity letterEntity, int i) {
            ((LetterHolder) viewHolder).textView.setText(letterEntity.letter.toUpperCase());
        }
    }
}
