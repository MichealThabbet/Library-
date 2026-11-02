package org.apache.poi.hssf.eventusermodel.dummyrecord;

/* loaded from: classes3.dex */
public final class LastCellOfRowDummyRecord extends DummyRecordBase {
    private int lastColumnNumber;
    private int row;

    @Override // org.apache.poi.hssf.eventusermodel.dummyrecord.DummyRecordBase, org.apache.poi.hssf.record.RecordBase
    public /* bridge */ /* synthetic */ int serialize(int i, byte[] bArr) {
        return super.serialize(i, bArr);
    }

    public LastCellOfRowDummyRecord(int i, int i2) {
        this.row = i;
        this.lastColumnNumber = i2;
    }

    public int getRow() {
        return this.row;
    }

    public int getLastColumnNumber() {
        return this.lastColumnNumber;
    }
}
