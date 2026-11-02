package org.apache.poi.hssf.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class LazilyConcatenatedByteArray {
    private final List<byte[]> arrays = new ArrayList(1);

    public void clear() {
        this.arrays.clear();
    }

    public void concatenate(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("array cannot be null");
        }
        this.arrays.add(bArr);
    }

    public byte[] toArray() {
        if (this.arrays.isEmpty()) {
            return null;
        }
        if (this.arrays.size() > 1) {
            Iterator<byte[]> it2 = this.arrays.iterator();
            int length = 0;
            while (it2.hasNext()) {
                length += it2.next().length;
            }
            byte[] bArr = new byte[length];
            int length2 = 0;
            for (byte[] bArr2 : this.arrays) {
                System.arraycopy(bArr2, 0, bArr, length2, bArr2.length);
                length2 += bArr2.length;
            }
            this.arrays.clear();
            this.arrays.add(bArr);
        }
        return this.arrays.get(0);
    }
}
