package com.ibm.icu.util;

import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public class UResourceBundleIterator {
    private UResourceBundle bundle;
    private int index = 0;
    private int size;

    public UResourceBundleIterator(UResourceBundle uResourceBundle) {
        this.size = 0;
        this.bundle = uResourceBundle;
        this.size = uResourceBundle.getSize();
    }

    public UResourceBundle next() throws NoSuchElementException {
        int i = this.index;
        if (i < this.size) {
            UResourceBundle uResourceBundle = this.bundle;
            this.index = i + 1;
            return uResourceBundle.get(i);
        }
        throw new NoSuchElementException();
    }

    public String nextString() throws UResourceTypeMismatchException, NoSuchElementException {
        int i = this.index;
        if (i < this.size) {
            UResourceBundle uResourceBundle = this.bundle;
            this.index = i + 1;
            return uResourceBundle.getString(i);
        }
        throw new NoSuchElementException();
    }

    public void reset() {
        this.index = 0;
    }

    public boolean hasNext() {
        return this.index < this.size;
    }
}
