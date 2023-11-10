package com.p020kl.commonbase.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.kl.commonbase.utils.AnimalUtil */
public class AnimalUtil {
    public static ArrayList<Animator> animators = new ArrayList<>();

    public static void showTranslationAnimal() {
    }

    public static void showRotateAnimal(Context context, View view, int i) {
        Animator loadAnimator = AnimatorInflater.loadAnimator(context, i);
        animators.add(loadAnimator);
        loadAnimator.setTarget(view);
        loadAnimator.start();
    }

    public static void stopRotateAnimal(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (AnimalUtil.animators.size() > 0) {
                    Iterator<Animator> it = AnimalUtil.animators.iterator();
                    while (it.hasNext()) {
                        it.next().cancel();
                    }
                }
            }
        });
    }
}
