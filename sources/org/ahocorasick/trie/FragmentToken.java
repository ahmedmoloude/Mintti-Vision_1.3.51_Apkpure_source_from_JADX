package org.ahocorasick.trie;

public class FragmentToken extends Token {
    public Emit getEmit() {
        return null;
    }

    public boolean isMatch() {
        return false;
    }

    public FragmentToken(String str) {
        super(str);
    }
}
