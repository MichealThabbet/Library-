package com.huawei.hms.common.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import net.fortuna.ical4j.model.PropertyList;

/* loaded from: classes2.dex */
public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        return freezeIterable(arrayList);
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> iterable) {
        PropertyList propertyList = (ArrayList<T>) new ArrayList();
        Iterator<E> it2 = iterable.iterator();
        while (it2.hasNext()) {
            propertyList.add((PropertyList) it2.next().freeze());
        }
        return propertyList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] eArr) {
        return freezeIterable(Arrays.asList(eArr));
    }
}
