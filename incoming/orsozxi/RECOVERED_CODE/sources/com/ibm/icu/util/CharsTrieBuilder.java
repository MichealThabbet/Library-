package com.ibm.icu.util;

import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.util.StringTrieBuilder;
import java.nio.CharBuffer;

/* loaded from: classes2.dex */
public final class CharsTrieBuilder extends StringTrieBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private char[] chars;
    private int charsLength;
    private final char[] intUnits = new char[3];

    @Override // com.ibm.icu.util.StringTrieBuilder
    @Deprecated
    protected int getMaxBranchLinearSubNodeLength() {
        return 5;
    }

    @Override // com.ibm.icu.util.StringTrieBuilder
    @Deprecated
    protected int getMaxLinearMatchLength() {
        return 16;
    }

    @Override // com.ibm.icu.util.StringTrieBuilder
    @Deprecated
    protected int getMinLinearMatch() {
        return 48;
    }

    @Override // com.ibm.icu.util.StringTrieBuilder
    @Deprecated
    protected boolean matchNodesCanHaveValues() {
        return true;
    }

    public CharsTrieBuilder add(CharSequence charSequence, int i) {
        addImpl(charSequence, i);
        return this;
    }

    public CharsTrie build(StringTrieBuilder.Option option) {
        return new CharsTrie(buildCharSequence(option), 0);
    }

    public CharSequence buildCharSequence(StringTrieBuilder.Option option) {
        buildChars(option);
        char[] cArr = this.chars;
        int length = cArr.length;
        int i = this.charsLength;
        return CharBuffer.wrap(cArr, length - i, i);
    }

    private void buildChars(StringTrieBuilder.Option option) {
        if (this.chars == null) {
            this.chars = new char[1024];
        }
        buildImpl(option);
    }

    public CharsTrieBuilder clear() {
        clearImpl();
        this.chars = null;
        this.charsLength = 0;
        return this;
    }

    private void ensureCapacity(int i) {
        char[] cArr = this.chars;
        if (i > cArr.length) {
            int length = cArr.length;
            do {
                length *= 2;
            } while (length <= i);
            char[] cArr2 = new char[length];
            char[] cArr3 = this.chars;
            int length2 = cArr3.length;
            int i2 = this.charsLength;
            System.arraycopy(cArr3, length2 - i2, cArr2, length - i2, i2);
            this.chars = cArr2;
        }
    }

    @Override // com.ibm.icu.util.StringTrieBuilder
    @Deprecated
    protected int write(int i) {
        int i2 = this.charsLength + 1;
        ensureCapacity(i2);
        this.charsLength = i2;
        char[] cArr = this.chars;
        cArr[cArr.length - i2] = (char) i;
        return i2;
    }

    @Override // com.ibm.icu.util.StringTrieBuilder
    @Deprecated
    protected int write(int i, int i2) {
        int i3 = this.charsLength + i2;
        ensureCapacity(i3);
        this.charsLength = i3;
        int length = this.chars.length - i3;
        while (i2 > 0) {
            this.chars[length] = this.strings.charAt(i);
            i2--;
            length++;
            i++;
        }
        return this.charsLength;
    }

    private int write(char[] cArr, int i) {
        int i2 = this.charsLength + i;
        ensureCapacity(i2);
        this.charsLength = i2;
        char[] cArr2 = this.chars;
        System.arraycopy(cArr, 0, cArr2, cArr2.length - i2, i);
        return this.charsLength;
    }

    @Override // com.ibm.icu.util.StringTrieBuilder
    @Deprecated
    protected int writeValueAndFinal(int i, boolean z) {
        if (i >= 0 && i <= 16383) {
            return write(i | (z ? (char) 32768 : (char) 0));
        }
        int i2 = 2;
        if (i < 0 || i > 1073676287) {
            char[] cArr = this.intUnits;
            cArr[0] = 32767;
            cArr[1] = (char) (i >> 16);
            cArr[2] = (char) i;
            i2 = 3;
        } else {
            char[] cArr2 = this.intUnits;
            cArr2[0] = (char) ((i >> 16) + 16384);
            cArr2[1] = (char) i;
        }
        char[] cArr3 = this.intUnits;
        cArr3[0] = (char) (cArr3[0] | (z ? (char) 32768 : (char) 0));
        return write(this.intUnits, i2);
    }

    @Override // com.ibm.icu.util.StringTrieBuilder
    @Deprecated
    protected int writeValueAndType(boolean z, int i, int i2) {
        if (!z) {
            return write(i2);
        }
        int i3 = 2;
        if (i < 0 || i > 16646143) {
            char[] cArr = this.intUnits;
            cArr[0] = 32704;
            cArr[1] = (char) (i >> 16);
            cArr[2] = (char) i;
            i3 = 3;
        } else if (i <= 255) {
            this.intUnits[0] = (char) ((i + 1) << 6);
            i3 = 1;
        } else {
            char[] cArr2 = this.intUnits;
            cArr2[0] = (char) ((32704 & (i >> 10)) + 16448);
            cArr2[1] = (char) i;
        }
        char[] cArr3 = this.intUnits;
        cArr3[0] = (char) (((char) i2) | cArr3[0]);
        return write(cArr3, i3);
    }

    @Override // com.ibm.icu.util.StringTrieBuilder
    @Deprecated
    protected int writeDeltaTo(int i) {
        int i2 = this.charsLength - i;
        if (i2 <= 64511) {
            return write(i2);
        }
        int i3 = 1;
        if (i2 <= 67043327) {
            this.intUnits[0] = (char) ((i2 >> 16) + Normalizer2Impl.MIN_NORMAL_MAYBE_YES);
        } else {
            char[] cArr = this.intUnits;
            cArr[0] = 65535;
            cArr[1] = (char) (i2 >> 16);
            i3 = 2;
        }
        char[] cArr2 = this.intUnits;
        cArr2[i3] = (char) i2;
        return write(cArr2, i3 + 1);
    }
}
