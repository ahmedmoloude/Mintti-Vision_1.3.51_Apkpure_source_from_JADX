package org.ahocorasick.trie;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class State {
    private final int depth;
    private Set<String> emits;
    private State failure;
    private final State rootState;
    private Map<Character, State> success;

    public State() {
        this(0);
    }

    public State(int i) {
        this.success = new HashMap();
        State state = null;
        this.failure = null;
        this.emits = null;
        this.depth = i;
        this.rootState = i == 0 ? this : state;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000c, code lost:
        r3 = r1.rootState;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.ahocorasick.trie.State nextState(java.lang.Character r2, boolean r3) {
        /*
            r1 = this;
            java.util.Map<java.lang.Character, org.ahocorasick.trie.State> r0 = r1.success
            java.lang.Object r2 = r0.get(r2)
            org.ahocorasick.trie.State r2 = (org.ahocorasick.trie.State) r2
            if (r3 != 0) goto L_0x0011
            if (r2 != 0) goto L_0x0011
            org.ahocorasick.trie.State r3 = r1.rootState
            if (r3 == 0) goto L_0x0011
            r2 = r3
        L_0x0011:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.ahocorasick.trie.State.nextState(java.lang.Character, boolean):org.ahocorasick.trie.State");
    }

    public State nextState(Character ch) {
        return nextState(ch, false);
    }

    public State nextStateIgnoreRootState(Character ch) {
        return nextState(ch, true);
    }

    public State addState(Character ch) {
        State nextStateIgnoreRootState = nextStateIgnoreRootState(ch);
        if (nextStateIgnoreRootState != null) {
            return nextStateIgnoreRootState;
        }
        State state = new State(this.depth + 1);
        this.success.put(ch, state);
        return state;
    }

    public int getDepth() {
        return this.depth;
    }

    public void addEmit(String str) {
        if (this.emits == null) {
            this.emits = new TreeSet();
        }
        this.emits.add(str);
    }

    public void addEmit(Collection<String> collection) {
        for (String addEmit : collection) {
            addEmit(addEmit);
        }
    }

    public Collection<String> emit() {
        Set<String> set = this.emits;
        return set == null ? Collections.emptyList() : set;
    }

    public State failure() {
        return this.failure;
    }

    public void setFailure(State state) {
        this.failure = state;
    }

    public Collection<State> getStates() {
        return this.success.values();
    }

    public Collection<Character> getTransitions() {
        return this.success.keySet();
    }
}
