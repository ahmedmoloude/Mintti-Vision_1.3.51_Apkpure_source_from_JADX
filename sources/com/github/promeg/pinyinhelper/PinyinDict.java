package com.github.promeg.pinyinhelper;

import java.util.Set;

public interface PinyinDict {
    String[] toPinyin(String str);

    Set<String> words();
}
