package p035me.yokeyword.fragmentation;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentationMagician;
import java.util.List;

/* renamed from: me.yokeyword.fragmentation.SupportHelper */
public class SupportHelper {
    private static final long SHOW_SPACE = 200;

    private SupportHelper() {
    }

    public static void showSoftInput(final View view) {
        if (view != null && view.getContext() != null) {
            final InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
            view.requestFocus();
            view.postDelayed(new Runnable() {
                public void run() {
                    inputMethodManager.showSoftInput(view, 2);
                }
            }, SHOW_SPACE);
        }
    }

    public static void hideSoftInput(View view) {
        if (view != null && view.getContext() != null) {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showFragmentStackHierarchyView(ISupportActivity iSupportActivity) {
        iSupportActivity.getSupportDelegate().showFragmentStackHierarchyView();
    }

    public static void logFragmentStackHierarchy(ISupportActivity iSupportActivity, String str) {
        iSupportActivity.getSupportDelegate().logFragmentStackHierarchy(str);
    }

    public static ISupportFragment getTopFragment(FragmentManager fragmentManager) {
        return getTopFragment(fragmentManager, 0);
    }

    public static ISupportFragment getTopFragment(FragmentManager fragmentManager, int i) {
        List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(fragmentManager);
        if (activeFragments == null) {
            return null;
        }
        for (int size = activeFragments.size() - 1; size >= 0; size--) {
            Fragment fragment = activeFragments.get(size);
            if (fragment instanceof ISupportFragment) {
                ISupportFragment iSupportFragment = (ISupportFragment) fragment;
                if (i == 0 || i == iSupportFragment.getSupportDelegate().mContainerId) {
                    return iSupportFragment;
                }
            }
        }
        return null;
    }

    public static ISupportFragment getPreFragment(Fragment fragment) {
        List<Fragment> activeFragments;
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager == null || (activeFragments = FragmentationMagician.getActiveFragments(fragmentManager)) == null) {
            return null;
        }
        for (int indexOf = activeFragments.indexOf(fragment) - 1; indexOf >= 0; indexOf--) {
            Fragment fragment2 = activeFragments.get(indexOf);
            if (fragment2 instanceof ISupportFragment) {
                return (ISupportFragment) fragment2;
            }
        }
        return null;
    }

    public static <T extends ISupportFragment> T findFragment(FragmentManager fragmentManager, Class<T> cls) {
        return findStackFragment(cls, (String) null, fragmentManager);
    }

    public static <T extends ISupportFragment> T findFragment(FragmentManager fragmentManager, String str) {
        return findStackFragment((Class) null, str, fragmentManager);
    }

    public static ISupportFragment getActiveFragment(FragmentManager fragmentManager) {
        return getActiveFragment(fragmentManager, (ISupportFragment) null);
    }

    static <T extends ISupportFragment> T findStackFragment(Class<T> cls, String str, FragmentManager fragmentManager) {
        T t = null;
        if (str == null) {
            List activeFragments = FragmentationMagician.getActiveFragments(fragmentManager);
            if (activeFragments != null) {
                int size = activeFragments.size() - 1;
                while (true) {
                    if (size < 0) {
                        break;
                    }
                    T t2 = (Fragment) activeFragments.get(size);
                    if ((t2 instanceof ISupportFragment) && t2.getClass().getName().equals(cls.getName())) {
                        t = t2;
                        break;
                    }
                    size--;
                }
            } else {
                return null;
            }
        } else {
            T findFragmentByTag = fragmentManager.findFragmentByTag(str);
            if (findFragmentByTag == null) {
                return null;
            }
            t = findFragmentByTag;
        }
        return (ISupportFragment) t;
    }

    private static ISupportFragment getActiveFragment(FragmentManager fragmentManager, ISupportFragment iSupportFragment) {
        List<Fragment> activeFragments = FragmentationMagician.getActiveFragments(fragmentManager);
        if (activeFragments == null) {
            return iSupportFragment;
        }
        for (int size = activeFragments.size() - 1; size >= 0; size--) {
            Fragment fragment = activeFragments.get(size);
            if ((fragment instanceof ISupportFragment) && fragment.isResumed() && !fragment.isHidden() && fragment.getUserVisibleHint()) {
                return getActiveFragment(fragment.getChildFragmentManager(), (ISupportFragment) fragment);
            }
        }
        return iSupportFragment;
    }

    public static ISupportFragment getBackStackTopFragment(FragmentManager fragmentManager) {
        return getBackStackTopFragment(fragmentManager, 0);
    }

    public static ISupportFragment getBackStackTopFragment(FragmentManager fragmentManager, int i) {
        for (int backStackEntryCount = fragmentManager.getBackStackEntryCount() - 1; backStackEntryCount >= 0; backStackEntryCount--) {
            Fragment findFragmentByTag = fragmentManager.findFragmentByTag(fragmentManager.getBackStackEntryAt(backStackEntryCount).getName());
            if (findFragmentByTag instanceof ISupportFragment) {
                ISupportFragment iSupportFragment = (ISupportFragment) findFragmentByTag;
                if (i == 0 || i == iSupportFragment.getSupportDelegate().mContainerId) {
                    return iSupportFragment;
                }
            }
        }
        return null;
    }

    static <T extends ISupportFragment> T findBackStackFragment(Class<T> cls, String str, FragmentManager fragmentManager) {
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        if (str == null) {
            str = cls.getName();
        }
        for (int i = backStackEntryCount - 1; i >= 0; i--) {
            FragmentManager.BackStackEntry backStackEntryAt = fragmentManager.getBackStackEntryAt(i);
            if (str.equals(backStackEntryAt.getName())) {
                T findFragmentByTag = fragmentManager.findFragmentByTag(backStackEntryAt.getName());
                if (findFragmentByTag instanceof ISupportFragment) {
                    return (ISupportFragment) findFragmentByTag;
                }
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
        if (r3 < r1) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
        r3 = -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.util.List<androidx.fragment.app.Fragment> getWillPopFragments(androidx.fragment.app.FragmentManager r6, java.lang.String r7, boolean r8) {
        /*
            androidx.fragment.app.Fragment r7 = r6.findFragmentByTag(r7)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r6 = androidx.fragment.app.FragmentationMagician.getActiveFragments(r6)
            if (r6 != 0) goto L_0x0010
            return r0
        L_0x0010:
            int r1 = r6.size()
            int r2 = r1 + -1
            r3 = r2
        L_0x0017:
            r4 = -1
            if (r3 < 0) goto L_0x002b
            java.lang.Object r5 = r6.get(r3)
            if (r7 != r5) goto L_0x0028
            if (r8 == 0) goto L_0x0023
            goto L_0x002c
        L_0x0023:
            int r3 = r3 + 1
            if (r3 >= r1) goto L_0x002b
            goto L_0x002c
        L_0x0028:
            int r3 = r3 + -1
            goto L_0x0017
        L_0x002b:
            r3 = -1
        L_0x002c:
            if (r3 != r4) goto L_0x002f
            return r0
        L_0x002f:
            if (r2 < r3) goto L_0x0045
            java.lang.Object r7 = r6.get(r2)
            androidx.fragment.app.Fragment r7 = (androidx.fragment.app.Fragment) r7
            if (r7 == 0) goto L_0x0042
            android.view.View r8 = r7.getView()
            if (r8 == 0) goto L_0x0042
            r0.add(r7)
        L_0x0042:
            int r2 = r2 + -1
            goto L_0x002f
        L_0x0045:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p035me.yokeyword.fragmentation.SupportHelper.getWillPopFragments(androidx.fragment.app.FragmentManager, java.lang.String, boolean):java.util.List");
    }
}
