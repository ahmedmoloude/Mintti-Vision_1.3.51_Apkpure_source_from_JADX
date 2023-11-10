package com.github.promeg.pinyinhelper;

import com.p020kl.healthmonitor.C1633R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.UByte;
import org.ahocorasick.trie.Trie;

public final class Pinyin {
    static List<PinyinDict> mPinyinDicts;
    static SegmentationSelector mSelector;
    static Trie mTrieDict;

    private Pinyin() {
    }

    public static void init(Config config) {
        if (config == null) {
            mPinyinDicts = null;
            mTrieDict = null;
            mSelector = null;
        } else if (config.valid()) {
            mPinyinDicts = Collections.unmodifiableList(config.getPinyinDicts());
            mTrieDict = Utils.dictsToTrie(config.getPinyinDicts());
            mSelector = config.getSelector();
        }
    }

    public static void add(PinyinDict pinyinDict) {
        if (pinyinDict != null && pinyinDict.words() != null && pinyinDict.words().size() != 0) {
            init(new Config(mPinyinDicts).with(pinyinDict));
        }
    }

    public static Config newConfig() {
        return new Config((List) null);
    }

    public static String toPinyin(String str, String str2) {
        return Engine.toPinyin(str, mTrieDict, mPinyinDicts, str2, mSelector);
    }

    public static String toPinyin(char c) {
        if (!isChinese(c)) {
            return String.valueOf(c);
        }
        if (c == 12295) {
            return "LING";
        }
        return PinyinData.PINYIN_TABLE[getPinyinCode(c)];
    }

    public static boolean isChinese(char c) {
        return (19968 <= c && c <= 40869 && getPinyinCode(c) > 0) || 12295 == c;
    }

    private static int getPinyinCode(char c) {
        int i = c - 19968;
        if (i >= 0 && i < 7000) {
            return decodeIndex(PinyinCode1.PINYIN_CODE_PADDING, PinyinCode1.PINYIN_CODE, i);
        }
        if (7000 > i || i >= 14000) {
            return decodeIndex(PinyinCode3.PINYIN_CODE_PADDING, PinyinCode3.PINYIN_CODE, i - 14000);
        }
        return decodeIndex(PinyinCode2.PINYIN_CODE_PADDING, PinyinCode2.PINYIN_CODE, i - C1633R2.styleable.NavigationView_android_maxWidth);
    }

    private static short decodeIndex(byte[] bArr, byte[] bArr2, int i) {
        int i2 = i % 8;
        short s = (short) (bArr2[i] & UByte.MAX_VALUE);
        return (bArr[i / 8] & PinyinData.BIT_MASKS[i2]) != 0 ? (short) (s | 256) : s;
    }

    public static final class Config {
        List<PinyinDict> mPinyinDicts;
        SegmentationSelector mSelector;

        private Config(List<PinyinDict> list) {
            if (list != null) {
                this.mPinyinDicts = new ArrayList(list);
            }
            this.mSelector = new ForwardLongestSelector();
        }

        public Config with(PinyinDict pinyinDict) {
            if (pinyinDict != null) {
                List<PinyinDict> list = this.mPinyinDicts;
                if (list == null) {
                    ArrayList arrayList = new ArrayList();
                    this.mPinyinDicts = arrayList;
                    arrayList.add(pinyinDict);
                } else if (!list.contains(pinyinDict)) {
                    this.mPinyinDicts.add(pinyinDict);
                }
            }
            return this;
        }

        /* access modifiers changed from: package-private */
        public boolean valid() {
            return (getPinyinDicts() == null || getSelector() == null) ? false : true;
        }

        /* access modifiers changed from: package-private */
        public SegmentationSelector getSelector() {
            return this.mSelector;
        }

        /* access modifiers changed from: package-private */
        public List<PinyinDict> getPinyinDicts() {
            return this.mPinyinDicts;
        }
    }
}
