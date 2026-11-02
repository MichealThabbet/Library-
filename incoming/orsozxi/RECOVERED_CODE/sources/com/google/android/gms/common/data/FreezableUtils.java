package com.google.android.gms.common.data;

import java.util.ArrayList;
import java.util.Iterator;
import net.fortuna.ical4j.model.PropertyList;

/* loaded from: classes.dex */
public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        PropertyList propertyList = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            propertyList.add((PropertyList) arrayList.get(i).freeze());
        }
        return propertyList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] eArr) {
        PropertyList propertyList = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e : eArr) {
            propertyList.add((PropertyList) e.freeze());
        }
        return propertyList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> iterable) {
        PropertyList propertyList = (ArrayList<T>) new ArrayList();
        Iterator<E> it2 = iterable.iterator();
        while (it2.hasNext()) {
            propertyList.add((PropertyList) it2.next().freeze());
        }
        return propertyList;
    }
}
