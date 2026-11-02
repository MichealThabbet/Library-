package com.ibm.icu.text;

/* loaded from: classes2.dex */
class CharsDictionaryMatcher extends DictionaryMatcher {
    private CharSequence characters;

    @Override // com.ibm.icu.text.DictionaryMatcher
    public int getType() {
        return 1;
    }

    public CharsDictionaryMatcher(CharSequence charSequence) {
        this.characters = charSequence;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0038 A[PHI: r5
      0x0038: PHI (r5v3 int) = (r5v1 int), (r5v4 int) binds: [B:16:0x0035, B:13:0x0030] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.ibm.icu.text.DictionaryMatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int matches(java.text.CharacterIterator r8, int r9, int[] r10, int[] r11, int r12, int[] r13) {
        /*
            r7 = this;
            com.ibm.icu.text.UCharacterIterator r8 = com.ibm.icu.text.UCharacterIterator.getInstance(r8)
            com.ibm.icu.util.CharsTrie r0 = new com.ibm.icu.util.CharsTrie
            java.lang.CharSequence r1 = r7.characters
            r2 = 0
            r0.<init>(r1, r2)
            int r1 = r8.nextCodePoint()
            r3 = -1
            if (r1 != r3) goto L14
            return r2
        L14:
            com.ibm.icu.util.BytesTrie$Result r1 = r0.firstForCodePoint(r1)
            r4 = 1
            r5 = 0
        L1a:
            boolean r6 = r1.hasValue()
            if (r6 == 0) goto L33
            if (r5 >= r12) goto L2e
            if (r13 == 0) goto L2a
            int r6 = r0.getValue()
            r13[r5] = r6
        L2a:
            r10[r5] = r4
            int r5 = r5 + 1
        L2e:
            com.ibm.icu.util.BytesTrie$Result r6 = com.ibm.icu.util.BytesTrie.Result.FINAL_VALUE
            if (r1 != r6) goto L38
            goto L41
        L33:
            com.ibm.icu.util.BytesTrie$Result r6 = com.ibm.icu.util.BytesTrie.Result.NO_MATCH
            if (r1 != r6) goto L38
            goto L41
        L38:
            if (r4 < r9) goto L3b
            goto L41
        L3b:
            int r1 = r8.nextCodePoint()
            if (r1 != r3) goto L44
        L41:
            r11[r2] = r5
            return r4
        L44:
            int r4 = r4 + 1
            com.ibm.icu.util.BytesTrie$Result r1 = r0.nextForCodePoint(r1)
            goto L1a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.CharsDictionaryMatcher.matches(java.text.CharacterIterator, int, int[], int[], int, int[]):int");
    }
}
