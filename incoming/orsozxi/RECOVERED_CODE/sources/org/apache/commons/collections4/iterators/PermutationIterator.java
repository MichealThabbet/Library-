package org.apache.commons.collections4.iterators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PermutationIterator<E> implements Iterator<List<E>> {
    private boolean[] direction;
    private int[] keys;
    private List<E> nextPermutation;
    private Map<Integer, E> objectMap;

    public PermutationIterator(Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException("The collection must not be null");
        }
        this.keys = new int[collection.size()];
        boolean[] zArr = new boolean[collection.size()];
        this.direction = zArr;
        Arrays.fill(zArr, false);
        this.objectMap = new HashMap();
        Iterator<? extends E> it2 = collection.iterator();
        int i = 1;
        while (it2.hasNext()) {
            this.objectMap.put(Integer.valueOf(i), it2.next());
            this.keys[i - 1] = i;
            i++;
        }
        this.nextPermutation = new ArrayList(collection);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.nextPermutation != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0035  */
    @Override // java.util.Iterator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<E> next() {
        /*
            r8 = this;
            boolean r0 = r8.hasNext()
            if (r0 == 0) goto L93
            r0 = 0
            r1 = -1
            r2 = 0
            r3 = -1
            r4 = -1
        Lb:
            int[] r5 = r8.keys
            int r6 = r5.length
            r7 = 1
            if (r2 >= r6) goto L41
            boolean[] r6 = r8.direction
            boolean r6 = r6[r2]
            if (r6 == 0) goto L23
            int r6 = r5.length
            int r6 = r6 - r7
            if (r2 >= r6) goto L23
            r6 = r5[r2]
            int r7 = r2 + 1
            r5 = r5[r7]
            if (r6 > r5) goto L35
        L23:
            boolean[] r5 = r8.direction
            boolean r5 = r5[r2]
            if (r5 != 0) goto L3e
            if (r2 <= 0) goto L3e
            int[] r5 = r8.keys
            r6 = r5[r2]
            int r7 = r2 + (-1)
            r5 = r5[r7]
            if (r6 <= r5) goto L3e
        L35:
            int[] r5 = r8.keys
            r6 = r5[r2]
            if (r6 <= r3) goto L3e
            r3 = r5[r2]
            r4 = r2
        L3e:
            int r2 = r2 + 1
            goto Lb
        L41:
            if (r3 != r1) goto L49
            java.util.List<E> r0 = r8.nextPermutation
            r1 = 0
            r8.nextPermutation = r1
            return r0
        L49:
            boolean[] r2 = r8.direction
            boolean r2 = r2[r4]
            if (r2 == 0) goto L50
            r1 = 1
        L50:
            int[] r2 = r8.keys
            r5 = r2[r4]
            int r1 = r1 + r4
            r6 = r2[r1]
            r2[r4] = r6
            r2[r1] = r5
            boolean[] r2 = r8.direction
            boolean r5 = r2[r4]
            boolean r6 = r2[r1]
            r2[r4] = r6
            r2[r1] = r5
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
        L6a:
            int[] r2 = r8.keys
            int r4 = r2.length
            if (r0 >= r4) goto L8e
            r2 = r2[r0]
            if (r2 <= r3) goto L7a
            boolean[] r2 = r8.direction
            boolean r4 = r2[r0]
            r4 = r4 ^ r7
            r2[r0] = r4
        L7a:
            java.util.Map<java.lang.Integer, E> r2 = r8.objectMap
            int[] r4 = r8.keys
            r4 = r4[r0]
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.Object r2 = r2.get(r4)
            r1.add(r2)
            int r0 = r0 + 1
            goto L6a
        L8e:
            java.util.List<E> r0 = r8.nextPermutation
            r8.nextPermutation = r1
            return r0
        L93:
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.collections4.iterators.PermutationIterator.next():java.util.List");
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported");
    }
}
