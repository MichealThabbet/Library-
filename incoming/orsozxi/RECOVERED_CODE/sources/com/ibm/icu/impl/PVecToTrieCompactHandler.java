package com.ibm.icu.impl;

import com.ibm.icu.impl.PropsVectors;

/* loaded from: classes2.dex */
public class PVecToTrieCompactHandler implements PropsVectors.CompactHandler {
    public IntTrieBuilder builder;
    public int initialValue;

    @Override // com.ibm.icu.impl.PropsVectors.CompactHandler
    public void setRowIndexForErrorValue(int i) {
    }

    @Override // com.ibm.icu.impl.PropsVectors.CompactHandler
    public void setRowIndexForInitialValue(int i) {
        this.initialValue = i;
    }

    @Override // com.ibm.icu.impl.PropsVectors.CompactHandler
    public void setRowIndexForRange(int i, int i2, int i3) {
        this.builder.setRange(i, i2 + 1, i3, true);
    }

    @Override // com.ibm.icu.impl.PropsVectors.CompactHandler
    public void startRealValues(int i) {
        if (i > 65535) {
            throw new IndexOutOfBoundsException();
        }
        int i2 = this.initialValue;
        this.builder = new IntTrieBuilder(null, 100000, i2, i2, false);
    }
}
