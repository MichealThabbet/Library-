package com.ibm.icu.text;

/* loaded from: classes2.dex */
class CharsetRecog_UTF8 extends CharsetRecognizer {
    @Override // com.ibm.icu.text.CharsetRecognizer
    String getName() {
        return "UTF-8";
    }

    CharsetRecog_UTF8() {
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0074  */
    @Override // com.ibm.icu.text.CharsetRecognizer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    com.ibm.icu.text.CharsetMatch match(com.ibm.icu.text.CharsetDetector r14) {
        /*
            r13 = this;
            byte[] r0 = r14.fRawInput
            int r1 = r14.fRawLength
            r2 = 2
            r3 = 3
            r4 = 0
            r5 = 1
            if (r1 < r3) goto L24
            r1 = r0[r4]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r6 = 239(0xef, float:3.35E-43)
            if (r1 != r6) goto L24
            r1 = r0[r5]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r6 = 187(0xbb, float:2.62E-43)
            if (r1 != r6) goto L24
            r1 = r0[r2]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r6 = 191(0xbf, float:2.68E-43)
            if (r1 != r6) goto L24
            r1 = 1
            goto L25
        L24:
            r1 = 0
        L25:
            r6 = 0
            r7 = 0
            r8 = 0
        L28:
            int r9 = r14.fRawLength
            if (r6 >= r9) goto L63
            r9 = r0[r6]
            r10 = r9 & 128(0x80, float:1.8E-43)
            if (r10 != 0) goto L33
            goto L61
        L33:
            r10 = r9 & 224(0xe0, float:3.14E-43)
            r11 = 192(0xc0, float:2.69E-43)
            if (r10 != r11) goto L3b
            r9 = 1
            goto L4a
        L3b:
            r10 = r9 & 240(0xf0, float:3.36E-43)
            r12 = 224(0xe0, float:3.14E-43)
            if (r10 != r12) goto L43
            r9 = 2
            goto L4a
        L43:
            r9 = r9 & 248(0xf8, float:3.48E-43)
            r10 = 240(0xf0, float:3.36E-43)
            if (r9 != r10) goto L5f
            r9 = 3
        L4a:
            int r6 = r6 + r5
            int r10 = r14.fRawLength
            if (r6 < r10) goto L50
            goto L61
        L50:
            r10 = r0[r6]
            r10 = r10 & r11
            r12 = 128(0x80, float:1.8E-43)
            if (r10 == r12) goto L58
            goto L5f
        L58:
            int r9 = r9 + (-1)
            if (r9 != 0) goto L4a
            int r8 = r8 + 1
            goto L61
        L5f:
            int r7 = r7 + 1
        L61:
            int r6 = r6 + r5
            goto L28
        L63:
            r0 = 80
            r2 = 100
            if (r1 == 0) goto L6e
            if (r7 != 0) goto L6e
        L6b:
            r4 = 100
            goto L8e
        L6e:
            if (r1 == 0) goto L77
            int r1 = r7 * 10
            if (r8 <= r1) goto L77
        L74:
            r4 = 80
            goto L8e
        L77:
            if (r8 <= r3) goto L7c
            if (r7 != 0) goto L7c
            goto L6b
        L7c:
            if (r8 <= 0) goto L81
            if (r7 != 0) goto L81
            goto L74
        L81:
            if (r8 != 0) goto L88
            if (r7 != 0) goto L88
            r4 = 15
            goto L8e
        L88:
            int r7 = r7 * 10
            if (r8 <= r7) goto L8e
            r4 = 25
        L8e:
            if (r4 != 0) goto L92
            r14 = 0
            goto L98
        L92:
            com.ibm.icu.text.CharsetMatch r0 = new com.ibm.icu.text.CharsetMatch
            r0.<init>(r14, r13, r4)
            r14 = r0
        L98:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.CharsetRecog_UTF8.match(com.ibm.icu.text.CharsetDetector):com.ibm.icu.text.CharsetMatch");
    }
}
