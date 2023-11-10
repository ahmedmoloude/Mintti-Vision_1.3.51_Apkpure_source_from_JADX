package com.p020kl.commonbase.utils;

import android.content.Context;

/* renamed from: com.kl.commonbase.utils.LanguageUtil */
public class LanguageUtil {
    public static boolean isZh(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage().endsWith("zh");
    }
}
