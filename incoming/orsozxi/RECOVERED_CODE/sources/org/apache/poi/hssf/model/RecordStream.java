package org.apache.poi.hssf.model;

import java.util.List;
import org.apache.poi.hssf.record.Record;

/* loaded from: classes3.dex */
public final class RecordStream {
    private int _countRead;
    private final int _endIx;
    private final List _list;
    private int _nextIndex;

    public RecordStream(List list, int i, int i2) {
        this._list = list;
        this._nextIndex = i;
        this._endIx = i2;
        this._countRead = 0;
    }

    public RecordStream(List list, int i) {
        this(list, i, list.size());
    }

    public boolean hasNext() {
        return this._nextIndex < this._endIx;
    }

    public Record getNext() {
        if (!hasNext()) {
            throw new RuntimeException("Attempt to read past end of record stream");
        }
        this._countRead++;
        List list = this._list;
        int i = this._nextIndex;
        this._nextIndex = i + 1;
        return (Record) list.get(i);
    }

    public Class peekNextClass() {
        if (hasNext()) {
            return this._list.get(this._nextIndex).getClass();
        }
        return null;
    }

    public int peekNextSid() {
        if (hasNext()) {
            return ((Record) this._list.get(this._nextIndex)).getSid();
        }
        return -1;
    }

    public int getCountRead() {
        return this._countRead;
    }
}
