package org.ahocorasick.trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import org.ahocorasick.interval.IntervalTree;
import org.ahocorasick.trie.handler.DefaultEmitHandler;
import org.ahocorasick.trie.handler.EmitHandler;

public class Trie {
    private State rootState;
    /* access modifiers changed from: private */
    public TrieConfig trieConfig;

    private Trie(TrieConfig trieConfig2) {
        this.trieConfig = trieConfig2;
        this.rootState = new State();
    }

    /* access modifiers changed from: private */
    public void addKeyword(String str) {
        if (str != null && str.length() != 0) {
            State state = this.rootState;
            for (char valueOf : str.toCharArray()) {
                Character valueOf2 = Character.valueOf(valueOf);
                if (this.trieConfig.isCaseInsensitive()) {
                    valueOf2 = Character.valueOf(Character.toLowerCase(valueOf2.charValue()));
                }
                state = state.addState(valueOf2);
            }
            if (this.trieConfig.isCaseInsensitive()) {
                str = str.toLowerCase();
            }
            state.addEmit(str);
        }
    }

    public Collection<Token> tokenize(String str) {
        ArrayList arrayList = new ArrayList();
        int i = -1;
        for (Emit next : parseText(str)) {
            if (next.getStart() - i > 1) {
                arrayList.add(createFragment(next, str, i));
            }
            arrayList.add(createMatch(next, str));
            i = next.getEnd();
        }
        if (str.length() - i > 1) {
            arrayList.add(createFragment((Emit) null, str, i));
        }
        return arrayList;
    }

    private Token createFragment(Emit emit, String str, int i) {
        return new FragmentToken(str.substring(i + 1, emit == null ? str.length() : emit.getStart()));
    }

    private Token createMatch(Emit emit, String str) {
        return new MatchToken(str.substring(emit.getStart(), emit.getEnd() + 1), emit);
    }

    public Collection<Emit> parseText(CharSequence charSequence) {
        DefaultEmitHandler defaultEmitHandler = new DefaultEmitHandler();
        parseText(charSequence, defaultEmitHandler);
        List<Emit> emits = defaultEmitHandler.getEmits();
        if (this.trieConfig.isOnlyWholeWords()) {
            removePartialMatches(charSequence, emits);
        }
        if (this.trieConfig.isOnlyWholeWordsWhiteSpaceSeparated()) {
            removePartialMatchesWhiteSpaceSeparated(charSequence, emits);
        }
        if (!this.trieConfig.isAllowOverlaps()) {
            new IntervalTree(emits).removeOverlaps(emits);
        }
        return emits;
    }

    public boolean containsMatch(CharSequence charSequence) {
        return firstMatch(charSequence) != null;
    }

    public void parseText(CharSequence charSequence, EmitHandler emitHandler) {
        State state = this.rootState;
        int i = 0;
        while (i < charSequence.length()) {
            Character valueOf = Character.valueOf(charSequence.charAt(i));
            if (this.trieConfig.isCaseInsensitive()) {
                valueOf = Character.valueOf(Character.toLowerCase(valueOf.charValue()));
            }
            state = getState(state, valueOf);
            if (!storeEmits(i, state, emitHandler) || !this.trieConfig.isStopOnHit()) {
                i++;
            } else {
                return;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.ahocorasick.trie.Emit firstMatch(java.lang.CharSequence r7) {
        /*
            r6 = this;
            org.ahocorasick.trie.TrieConfig r0 = r6.trieConfig
            boolean r0 = r0.isAllowOverlaps()
            if (r0 != 0) goto L_0x001f
            java.util.Collection r7 = r6.parseText(r7)
            if (r7 == 0) goto L_0x0083
            boolean r0 = r7.isEmpty()
            if (r0 != 0) goto L_0x0083
            java.util.Iterator r7 = r7.iterator()
            java.lang.Object r7 = r7.next()
            org.ahocorasick.trie.Emit r7 = (org.ahocorasick.trie.Emit) r7
            return r7
        L_0x001f:
            org.ahocorasick.trie.State r0 = r6.rootState
            r1 = 0
        L_0x0022:
            int r2 = r7.length()
            if (r1 >= r2) goto L_0x0083
            char r2 = r7.charAt(r1)
            java.lang.Character r2 = java.lang.Character.valueOf(r2)
            org.ahocorasick.trie.TrieConfig r3 = r6.trieConfig
            boolean r3 = r3.isCaseInsensitive()
            if (r3 == 0) goto L_0x0044
            char r2 = r2.charValue()
            char r2 = java.lang.Character.toLowerCase(r2)
            java.lang.Character r2 = java.lang.Character.valueOf(r2)
        L_0x0044:
            org.ahocorasick.trie.State r0 = r6.getState(r0, r2)
            java.util.Collection r2 = r0.emit()
            if (r2 == 0) goto L_0x0080
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L_0x0080
            java.util.Iterator r2 = r2.iterator()
        L_0x0058:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0080
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            org.ahocorasick.trie.Emit r4 = new org.ahocorasick.trie.Emit
            int r5 = r3.length()
            int r5 = r1 - r5
            int r5 = r5 + 1
            r4.<init>(r5, r1, r3)
            org.ahocorasick.trie.TrieConfig r3 = r6.trieConfig
            boolean r3 = r3.isOnlyWholeWords()
            if (r3 == 0) goto L_0x007f
            boolean r3 = r6.isPartialMatch(r7, r4)
            if (r3 != 0) goto L_0x0058
        L_0x007f:
            return r4
        L_0x0080:
            int r1 = r1 + 1
            goto L_0x0022
        L_0x0083:
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.ahocorasick.trie.Trie.firstMatch(java.lang.CharSequence):org.ahocorasick.trie.Emit");
    }

    private boolean isPartialMatch(CharSequence charSequence, Emit emit) {
        if (emit.getStart() != 0 && Character.isAlphabetic(charSequence.charAt(emit.getStart() - 1))) {
            return true;
        }
        if (emit.getEnd() + 1 == charSequence.length() || !Character.isAlphabetic(charSequence.charAt(emit.getEnd() + 1))) {
            return false;
        }
        return true;
    }

    private void removePartialMatches(CharSequence charSequence, List<Emit> list) {
        ArrayList<Emit> arrayList = new ArrayList<>();
        for (Emit next : list) {
            if (isPartialMatch(charSequence, next)) {
                arrayList.add(next);
            }
        }
        for (Emit remove : arrayList) {
            list.remove(remove);
        }
    }

    private void removePartialMatchesWhiteSpaceSeparated(CharSequence charSequence, List<Emit> list) {
        long length = (long) charSequence.length();
        ArrayList<Emit> arrayList = new ArrayList<>();
        for (Emit next : list) {
            if ((next.getStart() != 0 && !Character.isWhitespace(charSequence.charAt(next.getStart() - 1))) || (((long) (next.getEnd() + 1)) != length && !Character.isWhitespace(charSequence.charAt(next.getEnd() + 1)))) {
                arrayList.add(next);
            }
        }
        for (Emit remove : arrayList) {
            list.remove(remove);
        }
    }

    private State getState(State state, Character ch) {
        State nextState = state.nextState(ch);
        while (nextState == null) {
            state = state.failure();
            nextState = state.nextState(ch);
        }
        return nextState;
    }

    /* access modifiers changed from: private */
    public void constructFailureStates() {
        LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque();
        for (State next : this.rootState.getStates()) {
            next.setFailure(this.rootState);
            linkedBlockingDeque.add(next);
        }
        while (!linkedBlockingDeque.isEmpty()) {
            State state = (State) linkedBlockingDeque.remove();
            for (Character next2 : state.getTransitions()) {
                State nextState = state.nextState(next2);
                linkedBlockingDeque.add(nextState);
                State failure = state.failure();
                while (failure.nextState(next2) == null) {
                    failure = failure.failure();
                }
                State nextState2 = failure.nextState(next2);
                nextState.setFailure(nextState2);
                nextState.addEmit(nextState2.emit());
            }
        }
    }

    private boolean storeEmits(int i, State state, EmitHandler emitHandler) {
        Collection<String> emit = state.emit();
        boolean z = false;
        if (emit != null && !emit.isEmpty()) {
            for (String next : emit) {
                emitHandler.emit(new Emit((i - next.length()) + 1, i, next));
                z = true;
            }
        }
        return z;
    }

    public static TrieBuilder builder() {
        return new TrieBuilder();
    }

    public static class TrieBuilder {
        private Trie trie;
        private TrieConfig trieConfig;

        private TrieBuilder() {
            TrieConfig trieConfig2 = new TrieConfig();
            this.trieConfig = trieConfig2;
            this.trie = new Trie(trieConfig2);
        }

        public TrieBuilder caseInsensitive() {
            this.trieConfig.setCaseInsensitive(true);
            return this;
        }

        public TrieBuilder removeOverlaps() {
            this.trieConfig.setAllowOverlaps(false);
            return this;
        }

        public TrieBuilder onlyWholeWords() {
            this.trieConfig.setOnlyWholeWords(true);
            return this;
        }

        public TrieBuilder onlyWholeWordsWhiteSpaceSeparated() {
            this.trieConfig.setOnlyWholeWordsWhiteSpaceSeparated(true);
            return this;
        }

        public TrieBuilder addKeyword(String str) {
            this.trie.addKeyword(str);
            return this;
        }

        public TrieBuilder stopOnHit() {
            this.trie.trieConfig.setStopOnHit(true);
            return this;
        }

        public Trie build() {
            this.trie.constructFailureStates();
            return this.trie;
        }
    }
}
