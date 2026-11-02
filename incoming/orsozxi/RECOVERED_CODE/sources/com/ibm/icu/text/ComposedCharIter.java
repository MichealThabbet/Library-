package com.ibm.icu.text;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;

@Deprecated
/* loaded from: classes2.dex */
public final class ComposedCharIter {

    @Deprecated
    public static final char DONE = 65535;
    private int curChar;
    private String decompBuf;
    private final Normalizer2Impl n2impl;
    private int nextChar;

    @Deprecated
    public ComposedCharIter() {
        this(false, 0);
    }

    @Deprecated
    public ComposedCharIter(boolean z, int i) {
        this.curChar = 0;
        this.nextChar = -1;
        if (z) {
            this.n2impl = Norm2AllModes.getNFKCInstance().impl;
        } else {
            this.n2impl = Norm2AllModes.getNFCInstance().impl;
        }
    }

    @Deprecated
    public boolean hasNext() {
        if (this.nextChar == -1) {
            findNextChar();
        }
        return this.nextChar != -1;
    }

    @Deprecated
    public char next() {
        if (this.nextChar == -1) {
            findNextChar();
        }
        int i = this.nextChar;
        this.curChar = i;
        this.nextChar = -1;
        return (char) i;
    }

    @Deprecated
    public String decomposition() {
        String str = this.decompBuf;
        return str != null ? str : "";
    }

    private void findNextChar() {
        int i = this.curChar + 1;
        this.decompBuf = null;
        while (true) {
            if (i >= 65535) {
                i = -1;
                break;
            }
            String decomposition = this.n2impl.getDecomposition(i);
            this.decompBuf = decomposition;
            if (decomposition != null) {
                break;
            } else {
                i++;
            }
        }
        this.nextChar = i;
    }
}
