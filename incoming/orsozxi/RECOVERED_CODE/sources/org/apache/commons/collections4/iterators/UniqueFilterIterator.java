package org.apache.commons.collections4.iterators;

import java.util.Iterator;
import org.apache.commons.collections4.functors.UniquePredicate;

/* loaded from: classes3.dex */
public class UniqueFilterIterator<E> extends FilterIterator<E> {
    public UniqueFilterIterator(Iterator<? extends E> it2) {
        super(it2, UniquePredicate.uniquePredicate());
    }
}
