package com.p020kl.healthmonitor.guide;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.gyf.immersionbar.ImmersionBar;
import com.p020kl.healthmonitor.C1624R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.guide.BaseGuideFragment */
public abstract class BaseGuideFragment extends DialogFragment {
    /* access modifiers changed from: private */
    public TextView dot1;
    /* access modifiers changed from: private */
    public TextView dot2;
    /* access modifiers changed from: private */
    public TextView dot3;
    /* access modifiers changed from: private */
    public TextView dot4;
    /* access modifiers changed from: private */
    public TextView dot5;
    /* access modifiers changed from: private */
    public TextView dot6;
    /* access modifiers changed from: private */
    public TextView dot7;
    /* access modifiers changed from: private */
    public TextView dot8;
    protected List<Fragment> fragments = new ArrayList();
    protected BasePageFragment page1;
    protected BasePageFragment page2;
    protected BasePageFragment page3;
    protected BasePageFragment page4;
    protected BasePageFragment page5;
    protected BasePageFragment page6;
    protected BasePageFragment page7;
    protected BasePageFragment page8;
    protected ViewPager2 vpGuide;

    /* access modifiers changed from: protected */
    public abstract void initPage();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, 2131886571);
        initPage();
    }

    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(-1, -1);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(requireContext().getResources().getColor(C1624R.C1626color.transparent)));
        ImmersionBar.with((DialogFragment) this).statusBarDarkFont(true).init();
    }

    public void onPause() {
        super.onPause();
        Log.d("EcgGuideFragment", "onPause");
        ImmersionBar.with((Activity) requireActivity()).statusBarDarkFont(true).statusBarColor((int) C1624R.C1626color.white).init();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(C1624R.layout.guide_fragment, viewGroup, false);
    }

    public void show(FragmentManager fragmentManager, String str) {
        try {
            fragmentManager.beginTransaction().remove(this).commit();
            super.show(fragmentManager, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.vpGuide = (ViewPager2) view.findViewById(C1624R.C1628id.vp_guide);
        this.dot1 = (TextView) view.findViewById(C1624R.C1628id.dot_1);
        this.dot2 = (TextView) view.findViewById(C1624R.C1628id.dot_2);
        this.dot3 = (TextView) view.findViewById(C1624R.C1628id.dot_3);
        this.dot4 = (TextView) view.findViewById(C1624R.C1628id.dot_4);
        this.dot5 = (TextView) view.findViewById(C1624R.C1628id.dot_5);
        this.dot6 = (TextView) view.findViewById(C1624R.C1628id.dot_6);
        this.dot7 = (TextView) view.findViewById(C1624R.C1628id.dot_7);
        this.dot8 = (TextView) view.findViewById(C1624R.C1628id.dot_8);
        this.vpGuide.setAdapter(new GuidePageAdapter(this));
        if (this.fragments.size() == 3) {
            this.dot3.setVisibility(0);
        } else if (this.fragments.size() == 4) {
            this.dot3.setVisibility(0);
            this.dot4.setVisibility(0);
        } else if (this.fragments.size() == 8) {
            this.dot3.setVisibility(0);
            this.dot4.setVisibility(0);
            this.dot5.setVisibility(0);
            this.dot6.setVisibility(0);
            this.dot7.setVisibility(0);
            this.dot8.setVisibility(0);
        }
        this.vpGuide.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageScrolled(int i, float f, int i2) {
                if (i == 0) {
                    BaseGuideFragment.this.dot1.setSelected(true);
                    BaseGuideFragment.this.dot2.setSelected(false);
                    BaseGuideFragment.this.dot3.setSelected(false);
                    BaseGuideFragment.this.dot4.setSelected(false);
                    BaseGuideFragment.this.dot5.setSelected(false);
                    BaseGuideFragment.this.dot6.setSelected(false);
                    BaseGuideFragment.this.dot7.setSelected(false);
                    BaseGuideFragment.this.dot8.setSelected(false);
                } else if (i == 1) {
                    BaseGuideFragment.this.dot2.setSelected(true);
                    BaseGuideFragment.this.dot1.setSelected(false);
                    BaseGuideFragment.this.dot3.setSelected(false);
                    BaseGuideFragment.this.dot4.setSelected(false);
                    BaseGuideFragment.this.dot5.setSelected(false);
                    BaseGuideFragment.this.dot6.setSelected(false);
                    BaseGuideFragment.this.dot7.setSelected(false);
                    BaseGuideFragment.this.dot8.setSelected(false);
                } else if (i == 2) {
                    BaseGuideFragment.this.dot1.setSelected(false);
                    BaseGuideFragment.this.dot2.setSelected(false);
                    BaseGuideFragment.this.dot3.setSelected(true);
                    BaseGuideFragment.this.dot4.setSelected(false);
                    BaseGuideFragment.this.dot5.setSelected(false);
                    BaseGuideFragment.this.dot6.setSelected(false);
                    BaseGuideFragment.this.dot7.setSelected(false);
                    BaseGuideFragment.this.dot8.setSelected(false);
                } else if (i == 3) {
                    BaseGuideFragment.this.dot1.setSelected(false);
                    BaseGuideFragment.this.dot2.setSelected(false);
                    BaseGuideFragment.this.dot3.setSelected(false);
                    BaseGuideFragment.this.dot4.setSelected(true);
                    BaseGuideFragment.this.dot5.setSelected(false);
                    BaseGuideFragment.this.dot6.setSelected(false);
                    BaseGuideFragment.this.dot7.setSelected(false);
                    BaseGuideFragment.this.dot8.setSelected(false);
                } else if (i == 4) {
                    BaseGuideFragment.this.dot1.setSelected(false);
                    BaseGuideFragment.this.dot2.setSelected(false);
                    BaseGuideFragment.this.dot3.setSelected(false);
                    BaseGuideFragment.this.dot4.setSelected(false);
                    BaseGuideFragment.this.dot5.setSelected(true);
                    BaseGuideFragment.this.dot6.setSelected(false);
                    BaseGuideFragment.this.dot7.setSelected(false);
                    BaseGuideFragment.this.dot8.setSelected(false);
                } else if (i == 5) {
                    BaseGuideFragment.this.dot1.setSelected(false);
                    BaseGuideFragment.this.dot2.setSelected(false);
                    BaseGuideFragment.this.dot3.setSelected(false);
                    BaseGuideFragment.this.dot4.setSelected(false);
                    BaseGuideFragment.this.dot5.setSelected(false);
                    BaseGuideFragment.this.dot6.setSelected(true);
                    BaseGuideFragment.this.dot7.setSelected(false);
                    BaseGuideFragment.this.dot8.setSelected(false);
                } else if (i == 6) {
                    BaseGuideFragment.this.dot1.setSelected(false);
                    BaseGuideFragment.this.dot2.setSelected(false);
                    BaseGuideFragment.this.dot3.setSelected(false);
                    BaseGuideFragment.this.dot4.setSelected(false);
                    BaseGuideFragment.this.dot5.setSelected(false);
                    BaseGuideFragment.this.dot6.setSelected(false);
                    BaseGuideFragment.this.dot7.setSelected(true);
                    BaseGuideFragment.this.dot8.setSelected(false);
                } else if (i == 7) {
                    BaseGuideFragment.this.dot1.setSelected(false);
                    BaseGuideFragment.this.dot2.setSelected(false);
                    BaseGuideFragment.this.dot3.setSelected(false);
                    BaseGuideFragment.this.dot4.setSelected(false);
                    BaseGuideFragment.this.dot5.setSelected(false);
                    BaseGuideFragment.this.dot6.setSelected(false);
                    BaseGuideFragment.this.dot7.setSelected(false);
                    BaseGuideFragment.this.dot8.setSelected(true);
                }
            }
        });
        setCurrentPage(0);
        this.page1.setOnNextClickListener(new OnNextClickListener() {
            public void onNextClick() {
                BaseGuideFragment.this.handlePage1Next();
            }
        });
        this.page2.setOnNextClickListener(new OnNextClickListener() {
            public void onNextClick() {
                BaseGuideFragment.this.handlePage2Next();
            }
        });
        BasePageFragment basePageFragment = this.page3;
        if (basePageFragment != null) {
            basePageFragment.setOnNextClickListener(new OnNextClickListener() {
                public void onNextClick() {
                    BaseGuideFragment.this.handlePage3Next();
                }
            });
        }
        BasePageFragment basePageFragment2 = this.page4;
        if (basePageFragment2 != null) {
            basePageFragment2.setOnNextClickListener(new OnNextClickListener() {
                public void onNextClick() {
                    BaseGuideFragment.this.handlePage4Next();
                }
            });
        }
        BasePageFragment basePageFragment3 = this.page5;
        if (basePageFragment3 != null) {
            basePageFragment3.setOnNextClickListener(new OnNextClickListener() {
                public void onNextClick() {
                    BaseGuideFragment.this.handlePage5Next();
                }
            });
        }
        BasePageFragment basePageFragment4 = this.page6;
        if (basePageFragment4 != null) {
            basePageFragment4.setOnNextClickListener(new OnNextClickListener() {
                public void onNextClick() {
                    BaseGuideFragment.this.handlePage6Next();
                }
            });
        }
        BasePageFragment basePageFragment5 = this.page7;
        if (basePageFragment5 != null) {
            basePageFragment5.setOnNextClickListener(new OnNextClickListener() {
                public void onNextClick() {
                    BaseGuideFragment.this.handlePage7Next();
                }
            });
        }
        BasePageFragment basePageFragment6 = this.page8;
        if (basePageFragment6 != null) {
            basePageFragment6.setOnNextClickListener(new OnNextClickListener() {
                public void onNextClick() {
                    BaseGuideFragment.this.handlePage8Next();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void handlePage1Next() {
        setCurrentPage(1);
    }

    /* access modifiers changed from: protected */
    public void handlePage2Next() {
        setCurrentPage(2);
    }

    /* access modifiers changed from: protected */
    public void handlePage3Next() {
        setCurrentPage(3);
    }

    /* access modifiers changed from: protected */
    public void handlePage4Next() {
        setCurrentPage(4);
    }

    /* access modifiers changed from: protected */
    public void handlePage5Next() {
        setCurrentPage(5);
    }

    /* access modifiers changed from: protected */
    public void handlePage6Next() {
        setCurrentPage(6);
    }

    /* access modifiers changed from: protected */
    public void handlePage7Next() {
        setCurrentPage(7);
    }

    /* access modifiers changed from: protected */
    public void handlePage8Next() {
        setCurrentPage(8);
    }

    /* access modifiers changed from: protected */
    public void setCurrentPage(int i) {
        this.vpGuide.setCurrentItem(i);
    }

    /* renamed from: com.kl.healthmonitor.guide.BaseGuideFragment$GuidePageAdapter */
    private class GuidePageAdapter extends FragmentStateAdapter {
        public GuidePageAdapter(Fragment fragment) {
            super(fragment);
        }

        public Fragment createFragment(int i) {
            return BaseGuideFragment.this.fragments.get(i);
        }

        public int getItemCount() {
            return BaseGuideFragment.this.fragments.size();
        }
    }
}
