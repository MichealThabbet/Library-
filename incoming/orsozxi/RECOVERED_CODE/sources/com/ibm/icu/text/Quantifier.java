package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;

/* loaded from: classes2.dex */
class Quantifier implements UnicodeMatcher {
    public static final int MAX = Integer.MAX_VALUE;
    private UnicodeMatcher matcher;
    private int maxCount;
    private int minCount;

    public Quantifier(UnicodeMatcher unicodeMatcher, int i, int i2) {
        if (unicodeMatcher == null || i < 0 || i2 < 0 || i > i2) {
            throw new IllegalArgumentException();
        }
        this.matcher = unicodeMatcher;
        this.minCount = i;
        this.maxCount = i2;
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public int matches(Replaceable replaceable, int[] iArr, int i, boolean z) {
        int i2 = iArr[0];
        int i3 = 0;
        while (true) {
            if (i3 >= this.maxCount) {
                break;
            }
            int i4 = iArr[0];
            int iMatches = this.matcher.matches(replaceable, iArr, i, z);
            if (iMatches == 2) {
                i3++;
                if (i4 == iArr[0]) {
                    break;
                }
            } else if (z && iMatches == 1) {
                return 1;
            }
        }
        if (z && iArr[0] == i) {
            return 1;
        }
        if (i3 >= this.minCount) {
            return 2;
        }
        iArr[0] = i2;
        return 0;
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public String toPattern(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.matcher.toPattern(z));
        int i = this.minCount;
        if (i == 0) {
            int i2 = this.maxCount;
            if (i2 == 1) {
                sb.append('?');
                return sb.toString();
            }
            if (i2 == Integer.MAX_VALUE) {
                sb.append('*');
                return sb.toString();
            }
        } else if (i == 1 && this.maxCount == Integer.MAX_VALUE) {
            sb.append('+');
            return sb.toString();
        }
        sb.append('{');
        sb.append(Utility.hex(this.minCount, 1));
        sb.append(',');
        int i3 = this.maxCount;
        if (i3 != Integer.MAX_VALUE) {
            sb.append(Utility.hex(i3, 1));
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public boolean matchesIndexValue(int i) {
        return this.minCount == 0 || this.matcher.matchesIndexValue(i);
    }

    @Override // com.ibm.icu.text.UnicodeMatcher
    public void addMatchSetTo(UnicodeSet unicodeSet) {
        if (this.maxCount > 0) {
            this.matcher.addMatchSetTo(unicodeSet);
        }
    }
}
