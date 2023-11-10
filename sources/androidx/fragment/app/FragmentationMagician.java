package androidx.fragment.app;

import java.util.List;

public class FragmentationMagician {
    public static boolean isStateSaved(FragmentManager fragmentManager) {
        if (!(fragmentManager instanceof FragmentManagerImpl)) {
            return false;
        }
        try {
            return ((FragmentManagerImpl) fragmentManager).isStateSaved();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager) {
        hookStateSaved(fragmentManager, new Runnable() {
            public void run() {
                fragmentManager.popBackStack();
            }
        });
    }

    public static void popBackStackImmediateAllowingStateLoss(final FragmentManager fragmentManager) {
        hookStateSaved(fragmentManager, new Runnable() {
            public void run() {
                fragmentManager.popBackStackImmediate();
            }
        });
    }

    public static void popBackStackAllowingStateLoss(final FragmentManager fragmentManager, final String str, final int i) {
        hookStateSaved(fragmentManager, new Runnable() {
            public void run() {
                fragmentManager.popBackStack(str, i);
            }
        });
    }

    public static void executePendingTransactionsAllowingStateLoss(final FragmentManager fragmentManager) {
        hookStateSaved(fragmentManager, new Runnable() {
            public void run() {
                fragmentManager.executePendingTransactions();
            }
        });
    }

    public static List<Fragment> getActiveFragments(FragmentManager fragmentManager) {
        return fragmentManager.getFragments();
    }

    private static void hookStateSaved(FragmentManager fragmentManager, Runnable runnable) {
        if (fragmentManager instanceof FragmentManagerImpl) {
            FragmentManagerImpl fragmentManagerImpl = (FragmentManagerImpl) fragmentManager;
            if (isStateSaved(fragmentManager)) {
                boolean z = fragmentManagerImpl.mStateSaved;
                boolean z2 = fragmentManagerImpl.mStopped;
                fragmentManagerImpl.mStateSaved = false;
                fragmentManagerImpl.mStopped = false;
                runnable.run();
                fragmentManagerImpl.mStopped = z2;
                fragmentManagerImpl.mStateSaved = z;
                return;
            }
            runnable.run();
        }
    }
}
