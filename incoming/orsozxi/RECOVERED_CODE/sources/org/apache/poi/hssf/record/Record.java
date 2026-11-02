package org.apache.poi.hssf.record;

import java.io.ByteArrayInputStream;

/* loaded from: classes3.dex */
public abstract class Record extends RecordBase {
    public abstract short getSid();

    protected Record() {
    }

    public final byte[] serialize() {
        byte[] bArr = new byte[getRecordSize()];
        serialize(0, bArr);
        return bArr;
    }

    public String toString() {
        return super.toString();
    }

    public Object clone() {
        throw new RuntimeException("The class " + getClass().getName() + " needs to define a clone method");
    }

    public Record cloneViaReserialise() throws RecordFormatException {
        RecordInputStream recordInputStream = new RecordInputStream(new ByteArrayInputStream(serialize()));
        recordInputStream.nextRecord();
        Record[] recordArrCreateRecord = RecordFactory.createRecord(recordInputStream);
        if (recordArrCreateRecord.length != 1) {
            throw new IllegalStateException("Re-serialised a record to clone it, but got " + recordArrCreateRecord.length + " records back!");
        }
        return recordArrCreateRecord[0];
    }
}
