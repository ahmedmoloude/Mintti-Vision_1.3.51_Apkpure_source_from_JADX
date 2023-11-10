package com.github.promeg.pinyinhelper;

import java.util.List;
import java.util.TreeSet;
import org.ahocorasick.trie.Trie;

final class Utils {
    private Utils() {
    }

    static Trie dictsToTrie(List<PinyinDict> list) {
        TreeSet<String> treeSet = new TreeSet<>();
        Trie.TrieBuilder builder = Trie.builder();
        if (list == null) {
            return null;
        }
        for (PinyinDict next : list) {
            if (!(next == null || next.words() == null)) {
                treeSet.addAll(next.words());
            }
        }
        if (treeSet.size() <= 0) {
            return null;
        }
        for (String addKeyword : treeSet) {
            builder.addKeyword(addKeyword);
        }
        return builder.build();
    }
}
