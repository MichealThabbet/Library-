package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacterEnums;
import org.apache.poi.hssf.record.formula.AreaErrPtg;
import org.apache.poi.hssf.record.formula.MemFuncPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.RefErrorPtg;
import org.apache.poi.hssf.record.formula.RefPtg;

/* loaded from: classes2.dex */
abstract class CharsetRecog_2022 extends CharsetRecognizer {
    CharsetRecog_2022() {
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    int match(byte[] r12, int r13, byte[][] r14) {
        /*
            r11 = this;
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
        L5:
            if (r1 >= r13) goto L44
            r5 = r12[r1]
            r6 = 27
            r7 = 1
            if (r5 != r6) goto L34
            r5 = 0
        Lf:
            int r6 = r14.length
            if (r5 >= r6) goto L32
            r6 = r14[r5]
            int r8 = r13 - r1
            int r9 = r6.length
            if (r8 >= r9) goto L1a
            goto L26
        L1a:
            r8 = 1
        L1b:
            int r9 = r6.length
            if (r8 >= r9) goto L2c
            r9 = r6[r8]
            int r10 = r1 + r8
            r10 = r12[r10]
            if (r9 == r10) goto L29
        L26:
            int r5 = r5 + 1
            goto Lf
        L29:
            int r8 = r8 + 1
            goto L1b
        L2c:
            int r2 = r2 + 1
            int r5 = r6.length
            int r5 = r5 - r7
            int r1 = r1 + r5
            goto L42
        L32:
            int r3 = r3 + 1
        L34:
            r5 = r12[r1]
            r6 = 14
            if (r5 == r6) goto L40
            r5 = r12[r1]
            r6 = 15
            if (r5 != r6) goto L42
        L40:
            int r4 = r4 + 1
        L42:
            int r1 = r1 + r7
            goto L5
        L44:
            if (r2 != 0) goto L47
            return r0
        L47:
            int r12 = r2 * 100
            int r13 = r3 * 100
            int r12 = r12 - r13
            int r3 = r3 + r2
            int r12 = r12 / r3
            int r2 = r2 + r4
            r13 = 5
            if (r2 >= r13) goto L56
            int r13 = r13 - r2
            int r13 = r13 * 10
            int r12 = r12 - r13
        L56:
            if (r12 >= 0) goto L59
            goto L5a
        L59:
            r0 = r12
        L5a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.CharsetRecog_2022.match(byte[], int, byte[][]):int");
    }

    static class CharsetRecog_2022JP extends CharsetRecog_2022 {
        private byte[][] escapeSequences = {new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, 40, 67}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, 40, 68}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, Ptg.CLASS_ARRAY}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, 65}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, 66}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 38, Ptg.CLASS_ARRAY}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 40, 66}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 40, 72}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 40, 73}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 40, 74}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 46, 65}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 46, 70}};

        @Override // com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "ISO-2022-JP";
        }

        CharsetRecog_2022JP() {
        }

        @Override // com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            int iMatch = match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            if (iMatch == 0) {
                return null;
            }
            return new CharsetMatch(charsetDetector, this, iMatch);
        }
    }

    static class CharsetRecog_2022KR extends CharsetRecog_2022 {
        private byte[][] escapeSequences = {new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, MemFuncPtg.sid, 67}};

        @Override // com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "ISO-2022-KR";
        }

        CharsetRecog_2022KR() {
        }

        @Override // com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            int iMatch = match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            if (iMatch == 0) {
                return null;
            }
            return new CharsetMatch(charsetDetector, this, iMatch);
        }
    }

    static class CharsetRecog_2022CN extends CharsetRecog_2022 {
        private byte[][] escapeSequences = {new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, MemFuncPtg.sid, 65}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, MemFuncPtg.sid, 71}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, RefErrorPtg.sid, 72}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, MemFuncPtg.sid, 69}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, AreaErrPtg.sid, 73}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, AreaErrPtg.sid, 74}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, AreaErrPtg.sid, 75}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, AreaErrPtg.sid, 76}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, RefPtg.sid, AreaErrPtg.sid, 77}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 78}, new byte[]{UCharacterEnums.ECharacterCategory.OTHER_SYMBOL, 79}};

        @Override // com.ibm.icu.text.CharsetRecognizer
        String getName() {
            return "ISO-2022-CN";
        }

        CharsetRecog_2022CN() {
        }

        @Override // com.ibm.icu.text.CharsetRecognizer
        CharsetMatch match(CharsetDetector charsetDetector) {
            int iMatch = match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            if (iMatch == 0) {
                return null;
            }
            return new CharsetMatch(charsetDetector, this, iMatch);
        }
    }
}
