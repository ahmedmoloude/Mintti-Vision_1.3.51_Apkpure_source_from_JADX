package com.p020kl.commonbase.utils;

import com.github.promeg.pinyinhelper.Pinyin;

/* renamed from: com.kl.commonbase.utils.PinyinUtil */
public class PinyinUtil {
    public static String getPingYin(String str) {
        try {
            return Pinyin.toPinyin(str, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
