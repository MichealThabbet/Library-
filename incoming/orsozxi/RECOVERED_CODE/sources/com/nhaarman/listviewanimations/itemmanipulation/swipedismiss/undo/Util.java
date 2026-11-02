package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
class Util {
    private Util() {
    }

    static Collection<Integer> processDeletions(Collection<Integer> collection, int[] iArr) {
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            arrayList.add(Integer.valueOf(i));
        }
        return processDeletions(collection, arrayList);
    }

    static Collection<Integer> processDeletions(Collection<Integer> collection, List<Integer> list) {
        ArrayList arrayList = new ArrayList(collection);
        Collections.sort(list, Collections.reverseOrder());
        ArrayList arrayList2 = new ArrayList();
        Iterator<Integer> it2 = list.iterator();
        while (it2.hasNext()) {
            int iIntValue = it2.next().intValue();
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                int iIntValue2 = ((Integer) it3.next()).intValue();
                if (iIntValue2 > iIntValue) {
                    it3.remove();
                    arrayList2.add(Integer.valueOf(iIntValue2 - 1));
                } else if (iIntValue2 == iIntValue) {
                    it3.remove();
                } else {
                    arrayList2.add(Integer.valueOf(iIntValue2));
                }
            }
            arrayList.clear();
            arrayList.addAll(arrayList2);
            arrayList2.clear();
        }
        return arrayList;
    }
}
