package com.p020kl.commonbase.base;

import android.app.Activity;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* renamed from: com.kl.commonbase.base.ActivityManager */
public class ActivityManager {
    private Set<Activity> activitySet = new HashSet();

    public void addActivity(Activity activity) {
        if (activity != null) {
            this.activitySet.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            this.activitySet.remove(activity);
        }
    }

    public void finishAllActivity() {
        for (Activity finish : this.activitySet) {
            finish.finish();
        }
    }

    public void finishActivity(String str) {
        Activity next;
        Set<Activity> set = this.activitySet;
        if (set != null) {
            Iterator<Activity> it = set.iterator();
            while (it.hasNext() && (next = it.next()) != null) {
                if (next.getComponentName().getClassName().equals(str)) {
                    finishActivity(next);
                    return;
                }
            }
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            this.activitySet.remove(activity);
        }
    }

    public void finishAllActivity(Class cls) {
        Activity next;
        Set<Activity> set = this.activitySet;
        if (set != null) {
            Iterator<Activity> it = set.iterator();
            while (it.hasNext() && (next = it.next()) != null && !next.getComponentName().getClassName().equals(cls.getName())) {
                finishActivity(next);
            }
            this.activitySet.clear();
        }
    }
}
