package com.google.android.gms.common.data;

import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public class SingleRefDataBufferIterator<T> extends DataBufferIterator<T> {
    private T zamg;

    public SingleRefDataBufferIterator(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    @Override // com.google.android.gms.common.data.DataBufferIterator, java.util.Iterator
    public T next() {
        if (!hasNext()) {
            int i = this.zall;
            StringBuilder sb = new StringBuilder(46);
            sb.append("Cannot advance the iterator beyond ");
            sb.append(i);
            throw new NoSuchElementException(sb.toString());
        }
        this.zall++;
        if (this.zall == 0) {
            T t = this.zalk.get(0);
            this.zamg = t;
            if (!(t instanceof DataBufferRef)) {
                String strValueOf = String.valueOf(this.zamg.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 44);
                sb2.append("DataBuffer reference of type ");
                sb2.append(strValueOf);
                sb2.append(" is not movable");
                throw new IllegalStateException(sb2.toString());
            }
        } else {
            ((DataBufferRef) this.zamg).zag(this.zall);
        }
        return this.zamg;
    }
}
