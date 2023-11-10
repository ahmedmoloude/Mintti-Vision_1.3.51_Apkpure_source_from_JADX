package com.github.promeg.pinyinhelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

final class Engine {
    static final EmitComparator EMIT_COMPARATOR = new EmitComparator();

    private Engine() {
    }

    static String toPinyin(String str, Trie trie, List<PinyinDict> list, String str2, SegmentationSelector segmentationSelector) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (trie == null || segmentationSelector == null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < str.length(); i++) {
                stringBuffer.append(Pinyin.toPinyin(str.charAt(i)));
                if (i != str.length() - 1) {
                    stringBuffer.append(str2);
                }
            }
            return stringBuffer.toString();
        }
        List<Emit> select = segmentationSelector.select(trie.parseText(str));
        Collections.sort(select, EMIT_COMPARATOR);
        StringBuffer stringBuffer2 = new StringBuffer();
        int i2 = 0;
        int i3 = 0;
        while (i2 < str.length()) {
            if (i3 >= select.size() || i2 != select.get(i3).getStart()) {
                stringBuffer2.append(Pinyin.toPinyin(str.charAt(i2)));
                i2++;
            } else {
                String[] pinyinFromDict = pinyinFromDict(select.get(i3).getKeyword(), list);
                for (int i4 = 0; i4 < pinyinFromDict.length; i4++) {
                    stringBuffer2.append(pinyinFromDict[i4].toUpperCase());
                    if (i4 != pinyinFromDict.length - 1) {
                        stringBuffer2.append(str2);
                    }
                }
                i2 += select.get(i3).size();
                i3++;
            }
            if (i2 != str.length()) {
                stringBuffer2.append(str2);
            }
        }
        return stringBuffer2.toString();
    }

    static String[] pinyinFromDict(String str, List<PinyinDict> list) {
        if (list != null) {
            for (PinyinDict next : list) {
                if (next != null && next.words() != null && next.words().contains(str)) {
                    return next.toPinyin(str);
                }
            }
        }
        throw new IllegalArgumentException("No pinyin dict contains word: " + str);
    }

    static final class EmitComparator implements Comparator<Emit> {
        EmitComparator() {
        }

        public int compare(Emit emit, Emit emit2) {
            if (emit.getStart() == emit2.getStart()) {
                if (emit.size() < emit2.size()) {
                    return 1;
                }
                if (emit.size() == emit2.size()) {
                    return 0;
                }
                return -1;
            } else if (emit.getStart() < emit2.getStart()) {
                return -1;
            } else {
                if (emit.getStart() == emit2.getStart()) {
                    return 0;
                }
                return 1;
            }
        }
    }
}
