package com.ibm.icu.util;

/* loaded from: classes2.dex */
public interface RangeValueIterator {

    public static class Element {
        public int limit;
        public int start;
        public int value;
    }

    boolean next(Element element);

    void reset();
}
