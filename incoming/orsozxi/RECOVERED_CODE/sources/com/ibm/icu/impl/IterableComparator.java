package com.ibm.icu.impl;

import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class IterableComparator<T> implements Comparator<Iterable<T>> {
    private static final IterableComparator NOCOMPARATOR = new IterableComparator();
    private final Comparator<T> comparator;
    private final int shorterFirst;

    public IterableComparator() {
        this(null, true);
    }

    public IterableComparator(Comparator<T> comparator) {
        this(comparator, true);
    }

    public IterableComparator(Comparator<T> comparator, boolean z) {
        this.comparator = comparator;
        this.shorterFirst = z ? 1 : -1;
    }

    @Override // java.util.Comparator
    public int compare(Iterable<T> iterable, Iterable<T> iterable2) {
        if (iterable == null) {
            if (iterable2 == null) {
                return 0;
            }
            return -this.shorterFirst;
        }
        if (iterable2 == null) {
            return this.shorterFirst;
        }
        Iterator<T> it2 = iterable2.iterator();
        for (T t : iterable) {
            if (!it2.hasNext()) {
                return this.shorterFirst;
            }
            T next = it2.next();
            Comparator<T> comparator = this.comparator;
            int iCompare = comparator != null ? comparator.compare(t, next) : ((Comparable) t).compareTo(next);
            if (iCompare != 0) {
                return iCompare;
            }
        }
        if (it2.hasNext()) {
            return -this.shorterFirst;
        }
        return 0;
    }

    public static <T> int compareIterables(Iterable<T> iterable, Iterable<T> iterable2) {
        return NOCOMPARATOR.compare((Iterable) iterable, (Iterable) iterable2);
    }
}
