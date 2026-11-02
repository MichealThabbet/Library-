package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;

/* loaded from: classes2.dex */
class BytesDictionaryMatcher extends DictionaryMatcher {
    private final byte[] characters;
    private final int transform;

    @Override // com.ibm.icu.text.DictionaryMatcher
    public int getType() {
        return 0;
    }

    public BytesDictionaryMatcher(byte[] bArr, int i) {
        this.characters = bArr;
        Assert.assrt((2130706432 & i) == 16777216);
        this.transform = i;
    }

    private int transform(int i) {
        if (i == 8205) {
            return 255;
        }
        if (i == 8204) {
            return 254;
        }
        int i2 = i - (this.transform & DictionaryData.TRANSFORM_OFFSET_MASK);
        if (i2 < 0 || 253 < i2) {
            return -1;
        }
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003c A[PHI: r5
      0x003c: PHI (r5v3 int) = (r5v1 int), (r5v4 int) binds: [B:16:0x0039, B:13:0x0034] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.ibm.icu.text.DictionaryMatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int matches(java.text.CharacterIterator r8, int r9, int[] r10, int[] r11, int r12, int[] r13) {
        /*
            r7 = this;
            com.ibm.icu.text.UCharacterIterator r8 = com.ibm.icu.text.UCharacterIterator.getInstance(r8)
            com.ibm.icu.util.BytesTrie r0 = new com.ibm.icu.util.BytesTrie
            byte[] r1 = r7.characters
            r2 = 0
            r0.<init>(r1, r2)
            int r1 = r8.nextCodePoint()
            r3 = -1
            if (r1 != r3) goto L14
            return r2
        L14:
            int r1 = r7.transform(r1)
            com.ibm.icu.util.BytesTrie$Result r1 = r0.first(r1)
            r4 = 1
            r5 = 0
        L1e:
            boolean r6 = r1.hasValue()
            if (r6 == 0) goto L37
            if (r5 >= r12) goto L32
            if (r13 == 0) goto L2e
            int r6 = r0.getValue()
            r13[r5] = r6
        L2e:
            r10[r5] = r4
            int r5 = r5 + 1
        L32:
            com.ibm.icu.util.BytesTrie$Result r6 = com.ibm.icu.util.BytesTrie.Result.FINAL_VALUE
            if (r1 != r6) goto L3c
            goto L45
        L37:
            com.ibm.icu.util.BytesTrie$Result r6 = com.ibm.icu.util.BytesTrie.Result.NO_MATCH
            if (r1 != r6) goto L3c
            goto L45
        L3c:
            if (r4 < r9) goto L3f
            goto L45
        L3f:
            int r1 = r8.nextCodePoint()
            if (r1 != r3) goto L48
        L45:
            r11[r2] = r5
            return r4
        L48:
            int r4 = r4 + 1
            int r1 = r7.transform(r1)
            com.ibm.icu.util.BytesTrie$Result r1 = r0.next(r1)
            goto L1e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.BytesDictionaryMatcher.matches(java.text.CharacterIterator, int, int[], int[], int, int[]):int");
    }
}
