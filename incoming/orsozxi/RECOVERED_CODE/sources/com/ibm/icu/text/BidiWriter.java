package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import it.piapostolos.android.pinotes.R2;

/* loaded from: classes2.dex */
final class BidiWriter {
    static final char LRM_CHAR = 8206;
    static final int MASK_R_AL = 8194;
    static final char RLM_CHAR = 8207;

    private static boolean IsCombining(int i) {
        return ((1 << i) & R2.attr.fabSize) != 0;
    }

    BidiWriter() {
    }

    private static String doWriteForward(String str, int i) {
        int i2 = i & 10;
        if (i2 == 0) {
            return str;
        }
        int charCount = 0;
        if (i2 == 2) {
            StringBuffer stringBuffer = new StringBuffer(str.length());
            do {
                int iCharAt = UTF16.charAt(str, charCount);
                charCount += UTF16.getCharCount(iCharAt);
                UTF16.append(stringBuffer, UCharacter.getMirror(iCharAt));
            } while (charCount < str.length());
            return stringBuffer.toString();
        }
        if (i2 == 8) {
            StringBuilder sb = new StringBuilder(str.length());
            while (true) {
                int i3 = charCount + 1;
                char cCharAt = str.charAt(charCount);
                if (!Bidi.IsBidiControlChar(cCharAt)) {
                    sb.append(cCharAt);
                }
                if (i3 >= str.length()) {
                    return sb.toString();
                }
                charCount = i3;
            }
        } else {
            StringBuffer stringBuffer2 = new StringBuffer(str.length());
            do {
                int iCharAt2 = UTF16.charAt(str, charCount);
                charCount += UTF16.getCharCount(iCharAt2);
                if (!Bidi.IsBidiControlChar(iCharAt2)) {
                    UTF16.append(stringBuffer2, UCharacter.getMirror(iCharAt2));
                }
            } while (charCount < str.length());
            return stringBuffer2.toString();
        }
    }

    private static String doWriteForward(char[] cArr, int i, int i2, int i3) {
        return doWriteForward(new String(cArr, i, i2 - i), i3);
    }

    static String writeReverse(String str, int i) {
        int iCharAt;
        int charCount;
        StringBuffer stringBuffer = new StringBuffer(str.length());
        int i2 = i & 11;
        if (i2 == 0) {
            int length = str.length();
            while (true) {
                int charCount2 = length - UTF16.getCharCount(UTF16.charAt(str, length - 1));
                stringBuffer.append(str.substring(charCount2, length));
                if (charCount2 <= 0) {
                    break;
                }
                length = charCount2;
            }
        } else if (i2 == 1) {
            int length2 = str.length();
            while (true) {
                int charCount3 = length2;
                do {
                    iCharAt = UTF16.charAt(str, charCount3 - 1);
                    charCount3 -= UTF16.getCharCount(iCharAt);
                    if (charCount3 <= 0) {
                        break;
                    }
                } while (IsCombining(UCharacter.getType(iCharAt)));
                stringBuffer.append(str.substring(charCount3, length2));
                if (charCount3 <= 0) {
                    break;
                }
                length2 = charCount3;
            }
        } else {
            int length3 = str.length();
            while (true) {
                int iCharAt2 = UTF16.charAt(str, length3 - 1);
                int charCount4 = length3 - UTF16.getCharCount(iCharAt2);
                if ((i & 1) != 0) {
                    while (charCount4 > 0 && IsCombining(UCharacter.getType(iCharAt2))) {
                        iCharAt2 = UTF16.charAt(str, charCount4 - 1);
                        charCount4 -= UTF16.getCharCount(iCharAt2);
                    }
                }
                if ((i & 8) == 0 || !Bidi.IsBidiControlChar(iCharAt2)) {
                    if ((i & 2) != 0) {
                        int mirror = UCharacter.getMirror(iCharAt2);
                        UTF16.append(stringBuffer, mirror);
                        charCount = UTF16.getCharCount(mirror) + charCount4;
                    } else {
                        charCount = charCount4;
                    }
                    stringBuffer.append(str.substring(charCount, length3));
                }
                if (charCount4 <= 0) {
                    break;
                }
                length3 = charCount4;
            }
        }
        return stringBuffer.toString();
    }

    static String doWriteReverse(char[] cArr, int i, int i2, int i3) {
        return writeReverse(new String(cArr, i, i2 - i), i3);
    }

    static String writeReordered(Bidi bidi, int i) {
        char[] cArr = bidi.text;
        int iCountRuns = bidi.countRuns();
        if ((bidi.reorderingOptions & 1) != 0) {
            i = (i | 4) & (-9);
        }
        if ((bidi.reorderingOptions & 2) != 0) {
            i = (i | 8) & (-5);
        }
        if (bidi.reorderingMode != 4 && bidi.reorderingMode != 5 && bidi.reorderingMode != 6 && bidi.reorderingMode != 3) {
            i &= -5;
        }
        int i2 = i & 4;
        int i3 = bidi.length;
        if (i2 != 0) {
            i3 *= 2;
        }
        StringBuilder sb = new StringBuilder(i3);
        if ((i & 16) == 0) {
            if (i2 == 0) {
                for (int i4 = 0; i4 < iCountRuns; i4++) {
                    BidiRun visualRun = bidi.getVisualRun(i4);
                    if (visualRun.isEvenRun()) {
                        sb.append(doWriteForward(cArr, visualRun.start, visualRun.limit, i & (-3)));
                    } else {
                        sb.append(doWriteReverse(cArr, visualRun.start, visualRun.limit, i));
                    }
                }
            } else {
                byte[] bArr = bidi.dirProps;
                for (int i5 = 0; i5 < iCountRuns; i5++) {
                    BidiRun visualRun2 = bidi.getVisualRun(i5);
                    int i6 = bidi.runs[i5].insertRemove;
                    if (i6 < 0) {
                        i6 = 0;
                    }
                    if (visualRun2.isEvenRun()) {
                        if (bidi.isInverse() && bArr[visualRun2.start] != 0) {
                            i6 |= 1;
                        }
                        char c = (i6 & 1) != 0 ? LRM_CHAR : (i6 & 4) != 0 ? RLM_CHAR : (char) 0;
                        if (c != 0) {
                            sb.append(c);
                        }
                        sb.append(doWriteForward(cArr, visualRun2.start, visualRun2.limit, i & (-3)));
                        if (bidi.isInverse() && bArr[visualRun2.limit - 1] != 0) {
                            i6 |= 2;
                        }
                        char c2 = (i6 & 2) != 0 ? LRM_CHAR : (i6 & 8) != 0 ? RLM_CHAR : (char) 0;
                        if (c2 != 0) {
                            sb.append(c2);
                        }
                    } else {
                        if (bidi.isInverse() && !bidi.testDirPropFlagAt(8194, visualRun2.limit - 1)) {
                            i6 |= 4;
                        }
                        char c3 = (i6 & 1) != 0 ? LRM_CHAR : (i6 & 4) != 0 ? RLM_CHAR : (char) 0;
                        if (c3 != 0) {
                            sb.append(c3);
                        }
                        sb.append(doWriteReverse(cArr, visualRun2.start, visualRun2.limit, i));
                        if (bidi.isInverse() && (Bidi.DirPropFlag(bArr[visualRun2.start]) & 8194) == 0) {
                            i6 |= 8;
                        }
                        char c4 = (i6 & 2) != 0 ? LRM_CHAR : (i6 & 8) != 0 ? RLM_CHAR : (char) 0;
                        if (c4 != 0) {
                            sb.append(c4);
                        }
                    }
                }
            }
        } else if (i2 != 0) {
            byte[] bArr2 = bidi.dirProps;
            while (true) {
                iCountRuns--;
                if (iCountRuns < 0) {
                    break;
                }
                BidiRun visualRun3 = bidi.getVisualRun(iCountRuns);
                if (visualRun3.isEvenRun()) {
                    if (bArr2[visualRun3.limit - 1] != 0) {
                        sb.append(LRM_CHAR);
                    }
                    sb.append(doWriteReverse(cArr, visualRun3.start, visualRun3.limit, i & (-3)));
                    if (bArr2[visualRun3.start] != 0) {
                        sb.append(LRM_CHAR);
                    }
                } else {
                    if ((Bidi.DirPropFlag(bArr2[visualRun3.start]) & 8194) == 0) {
                        sb.append(RLM_CHAR);
                    }
                    sb.append(doWriteForward(cArr, visualRun3.start, visualRun3.limit, i));
                    if ((Bidi.DirPropFlag(bArr2[visualRun3.limit - 1]) & 8194) == 0) {
                        sb.append(RLM_CHAR);
                    }
                }
            }
        } else {
            while (true) {
                iCountRuns--;
                if (iCountRuns < 0) {
                    break;
                }
                BidiRun visualRun4 = bidi.getVisualRun(iCountRuns);
                if (visualRun4.isEvenRun()) {
                    sb.append(doWriteReverse(cArr, visualRun4.start, visualRun4.limit, i & (-3)));
                } else {
                    sb.append(doWriteForward(cArr, visualRun4.start, visualRun4.limit, i));
                }
            }
        }
        return sb.toString();
    }
}
