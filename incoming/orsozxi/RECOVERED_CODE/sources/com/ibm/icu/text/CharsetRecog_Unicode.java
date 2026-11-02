package com.ibm.icu.text;

/* loaded from: classes2.dex */
abstract class CharsetRecog_Unicode extends CharsetRecognizer {
    static int adjustConfidence(int i, int i2) {
        if (i == 0) {
            i2 -= 10;
        } else if ((i >= 32 && i <= 255) || i == 10) {
            i2 += 10;
        }
        if (i2 < 0) {
            return 0;
        }
        if (i2 > 100) {
            return 100;
        }
        return i2;
    }

    static int codeUnit16FromBytes(byte b, byte b2) {
        return ((b & 255) << 8) | (b2 & 255);
    }

    @Override // com.ibm.icu.text.CharsetRecognizer
    abstract String getName();

    @Override // com.ibm.icu.text.CharsetRecognizer
    abstract CharsetMatch match(CharsetDetector charsetDetector);

    CharsetRecog_Unicode() {
    }

    static class CharsetRecog_UTF_16_BE extends CharsetRecog_Unicode {
        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "UTF-16BE";
        }

        CharsetRecog_UTF_16_BE() {
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            byte[] bArr = charsetDetector.fRawInput;
            int iMin = Math.min(bArr.length, 30);
            int iAdjustConfidence = 10;
            int i = 0;
            while (true) {
                if (i >= iMin - 1) {
                    break;
                }
                int iCodeUnit16FromBytes = codeUnit16FromBytes(bArr[i], bArr[i + 1]);
                if (i == 0 && iCodeUnit16FromBytes == 65279) {
                    iAdjustConfidence = 100;
                    break;
                }
                iAdjustConfidence = adjustConfidence(iCodeUnit16FromBytes, iAdjustConfidence);
                if (iAdjustConfidence == 0 || iAdjustConfidence == 100) {
                    break;
                }
                i += 2;
            }
            int i2 = (iMin >= 4 || iAdjustConfidence >= 100) ? iAdjustConfidence : 0;
            if (i2 > 0) {
                return new CharsetMatch(charsetDetector, this, i2);
            }
            return null;
        }
    }

    static class CharsetRecog_UTF_16_LE extends CharsetRecog_Unicode {
        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "UTF-16LE";
        }

        CharsetRecog_UTF_16_LE() {
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            byte[] bArr = charsetDetector.fRawInput;
            int iMin = Math.min(bArr.length, 30);
            int iAdjustConfidence = 10;
            int i = 0;
            while (true) {
                if (i >= iMin - 1) {
                    break;
                }
                int iCodeUnit16FromBytes = codeUnit16FromBytes(bArr[i + 1], bArr[i]);
                if (i == 0 && iCodeUnit16FromBytes == 65279) {
                    iAdjustConfidence = 100;
                    break;
                }
                iAdjustConfidence = adjustConfidence(iCodeUnit16FromBytes, iAdjustConfidence);
                if (iAdjustConfidence == 0 || iAdjustConfidence == 100) {
                    break;
                }
                i += 2;
            }
            int i2 = (iMin >= 4 || iAdjustConfidence >= 100) ? iAdjustConfidence : 0;
            if (i2 > 0) {
                return new CharsetMatch(charsetDetector, this, i2);
            }
            return null;
        }
    }

    static abstract class CharsetRecog_UTF_32 extends CharsetRecog_Unicode {
        abstract int getChar(byte[] bArr, int i);

        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        abstract String getName();

        CharsetRecog_UTF_32() {
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x0044  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x004d  */
        @Override // com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        com.ibm.icu.text.CharsetMatch match(com.ibm.icu.text.CharsetDetector r11) {
            /*
                r10 = this;
                byte[] r0 = r11.fRawInput
                int r1 = r11.fRawLength
                int r1 = r1 / 4
                int r1 = r1 * 4
                r2 = 0
                if (r1 != 0) goto Lc
                return r2
            Lc:
                r3 = 0
                int r4 = r10.getChar(r0, r3)
                r5 = 65279(0xfeff, float:9.1475E-41)
                if (r4 != r5) goto L18
                r4 = 1
                goto L19
            L18:
                r4 = 0
            L19:
                r5 = 0
                r6 = 0
                r7 = 0
            L1c:
                if (r5 >= r1) goto L3c
                int r8 = r10.getChar(r0, r5)
                if (r8 < 0) goto L37
                r9 = 1114111(0x10ffff, float:1.561202E-39)
                if (r8 >= r9) goto L37
                r9 = 55296(0xd800, float:7.7486E-41)
                if (r8 < r9) goto L34
                r9 = 57343(0xdfff, float:8.0355E-41)
                if (r8 > r9) goto L34
                goto L37
            L34:
                int r7 = r7 + 1
                goto L39
            L37:
                int r6 = r6 + 1
            L39:
                int r5 = r5 + 4
                goto L1c
            L3c:
                r0 = 80
                r1 = 100
                if (r4 == 0) goto L47
                if (r6 != 0) goto L47
            L44:
                r3 = 100
                goto L61
            L47:
                if (r4 == 0) goto L50
                int r4 = r6 * 10
                if (r7 <= r4) goto L50
            L4d:
                r3 = 80
                goto L61
            L50:
                r4 = 3
                if (r7 <= r4) goto L56
                if (r6 != 0) goto L56
                goto L44
            L56:
                if (r7 <= 0) goto L5b
                if (r6 != 0) goto L5b
                goto L4d
            L5b:
                int r6 = r6 * 10
                if (r7 <= r6) goto L61
                r3 = 25
            L61:
                if (r3 != 0) goto L64
                goto L69
            L64:
                com.ibm.icu.text.CharsetMatch r2 = new com.ibm.icu.text.CharsetMatch
                r2.<init>(r11, r10, r3)
            L69:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.CharsetRecog_Unicode.CharsetRecog_UTF_32.match(com.ibm.icu.text.CharsetDetector):com.ibm.icu.text.CharsetMatch");
        }
    }

    static class CharsetRecog_UTF_32_BE extends CharsetRecog_UTF_32 {
        @Override // com.ibm.icu.text.CharsetRecog_Unicode.CharsetRecog_UTF_32, com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "UTF-32BE";
        }

        CharsetRecog_UTF_32_BE() {
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode.CharsetRecog_UTF_32
        int getChar(byte[] bArr, int i) {
            return (bArr[i + 3] & 255) | ((bArr[i + 0] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
        }
    }

    static class CharsetRecog_UTF_32_LE extends CharsetRecog_UTF_32 {
        @Override // com.ibm.icu.text.CharsetRecog_Unicode.CharsetRecog_UTF_32, com.ibm.icu.text.CharsetRecog_Unicode, com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "UTF-32LE";
        }

        CharsetRecog_UTF_32_LE() {
        }

        @Override // com.ibm.icu.text.CharsetRecog_Unicode.CharsetRecog_UTF_32
        int getChar(byte[] bArr, int i) {
            return (bArr[i + 0] & 255) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 1] & 255) << 8);
        }
    }
}
