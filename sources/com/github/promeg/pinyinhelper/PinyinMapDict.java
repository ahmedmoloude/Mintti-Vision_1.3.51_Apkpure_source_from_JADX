package com.github.promeg.pinyinhelper;

import java.util.Map;
import java.util.Set;

public abstract class PinyinMapDict implements PinyinDict {
    public abstract Map<String, String[]> mapping();

    public Set<String> words() {
        if (mapping() != null) {
            return mapping().keySet();
        }
        return null;
    }

    public String[] toPinyin(String str) {
        if (mapping() != null) {
            return mapping().get(str);
        }
        return null;
    }
}
