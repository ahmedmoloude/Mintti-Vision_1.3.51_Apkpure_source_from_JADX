package org.ahocorasick.trie;

public class MatchToken extends Token {
    private Emit emit;

    public boolean isMatch() {
        return true;
    }

    public MatchToken(String str, Emit emit2) {
        super(str);
        this.emit = emit2;
    }

    public Emit getEmit() {
        return this.emit;
    }
}
