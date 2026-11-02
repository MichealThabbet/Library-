package com.ibm.icu.impl;

import it.orsozoxi.android.orsonotes.R2;

/* loaded from: classes2.dex */
public class CalendarCache {
    private int arraySize;
    private long[] keys;
    private int pIndex = 0;
    private int size = 0;
    private int threshold;
    private long[] values;
    private static final int[] primes = {61, 127, R2.attr.haloRadius, 1021, R2.dimen.sp_day_picker_padding_horizontal, R2.style.Base_Theme_MaterialComponents_Light_Dialog, 8191, 16381, 32749, 65521, 131071, 262139};
    public static long EMPTY = Long.MIN_VALUE;

    public CalendarCache() {
        int i = primes[0];
        this.arraySize = i;
        this.threshold = (i * 3) / 4;
        this.keys = new long[i];
        this.values = new long[i];
        makeArrays(i);
    }

    private void makeArrays(int i) {
        this.keys = new long[i];
        this.values = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.values[i2] = EMPTY;
        }
        this.arraySize = i;
        this.threshold = (int) (i * 0.75d);
        this.size = 0;
    }

    public synchronized long get(long j) {
        return this.values[findIndex(j)];
    }

    public synchronized void put(long j, long j2) {
        if (this.size >= this.threshold) {
            rehash();
        }
        int iFindIndex = findIndex(j);
        this.keys[iFindIndex] = j;
        this.values[iFindIndex] = j2;
        this.size++;
    }

    private final int findIndex(long j) {
        int iHash = hash(j);
        int iHash2 = 0;
        while (this.values[iHash] != EMPTY && this.keys[iHash] != j) {
            if (iHash2 == 0) {
                iHash2 = hash2(j);
            }
            iHash = (iHash + iHash2) % this.arraySize;
        }
        return iHash;
    }

    private void rehash() {
        int i = this.arraySize;
        long[] jArr = this.keys;
        long[] jArr2 = this.values;
        int i2 = this.pIndex;
        int[] iArr = primes;
        if (i2 < iArr.length - 1) {
            int i3 = i2 + 1;
            this.pIndex = i3;
            this.arraySize = iArr[i3];
        } else {
            this.arraySize = (i * 2) + 1;
        }
        this.size = 0;
        makeArrays(this.arraySize);
        for (int i4 = 0; i4 < i; i4++) {
            if (jArr2[i4] != EMPTY) {
                put(jArr[i4], jArr2[i4]);
            }
        }
    }

    private final int hash(long j) {
        int i = this.arraySize;
        int i2 = (int) (((j * 15821) + 1) % i);
        return i2 < 0 ? i2 + i : i2;
    }

    private final int hash2(long j) {
        return (this.arraySize - 2) - ((int) (j % (r0 - 2)));
    }
}
