package org.apache.poi.hssf.record;

/* loaded from: classes3.dex */
public abstract class RecordBase {
    public abstract int getRecordSize();

    public abstract int serialize(int i, byte[] bArr);
}
